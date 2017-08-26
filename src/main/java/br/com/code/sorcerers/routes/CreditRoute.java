package br.com.code.sorcerers.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

/**
 * @author victor
 *
 */

@Component
public class CreditRoute extends RouteBuilder {


    @Override
    public void configure() throws Exception {

        from("seda:distribuidor").streamCaching()

            .process(exchange -> {
                String body = exchange.getIn().getBody(String.class);
                JSONObject jsonObject = new JSONObject(body);
                exchange.setProperty("jsonObject", jsonObject);
                exchange.getOut().setBody(jsonObject.getInt("score"));
            })

            .choice()
                .when(simple("${body} == {{scoreLow}}"))
                    .setBody(constant("{{creditLow}}"))
                    .process(new ProcessorCredit())
                .when(simple("${body} == {{scoreMedium}}"))
                    .setBody(constant("{{creditMedium}}"))
                    .process(new ProcessorCredit())
                .when(simple("${body} == {{scoreHigh}}"))
                    .setBody(constant("{{creditHigh}}"))
                    .process(new ProcessorCredit())
                .otherwise()
                    .setBody(constant("{\"Message\": \"Score Out\"}"))
            .end()

            .hystrix()
                .hystrixConfiguration()
                    .executionTimeoutInMilliseconds(5000).circuitBreakerSleepWindowInMilliseconds(10000)
                .end()
                //.inOut(RoutesCreation.pathCreation("{{addresses}}"))
                .log("${body}")
            .onFallback()
                .setBody(simple("ERROR!!"))
            .end()

            .to("log:foo")
        .end();

    }


}
