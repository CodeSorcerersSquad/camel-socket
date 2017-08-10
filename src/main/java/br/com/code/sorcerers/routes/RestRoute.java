package br.com.code.sorcerers.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RestRoute extends RouteBuilder {
	
	@Override
	public void configure() throws Exception {
		
		// Pega a as URIS e separa em um array dividido por virgula
		String[] uris = getContext().resolvePropertyPlaceholders("{{addresses}}").split(",");
		String[] nettyList = new String[uris.length];
		
		// Itera o array de URIS para poder montar os caminhos desejados de socket
		for(int i = 0; i < uris.length; i++) {
			StringBuilder path = new StringBuilder("netty4-http:http://");
			path.append(uris[i]);
			nettyList[i] = path.toString();
			System.out.println(nettyList[i]);
		}
		
		restConfiguration().component("spark-rest")
			// configuracao de contexto, host e porta
			.contextPath("/").host("{{host}}").port("{{port}}")
			// configuracao de binding para efetuar automaticamente bind
			// json para pojo
			.bindingMode(RestBindingMode.off)
			// formatar saida com pretty print
			.dataFormatProperty("prettyPrint", "true")
			// adicao de swagger api-doc
			.apiContextPath("/api-doc/custom-socket").apiProperty("api.title", "Camel Socket")
			.apiProperty("api.version", "1.0.0").apiProperty("cors", "true");
		
		rest()
			.produces("application/json")
			.consumes("application/json")
			.post("/socket")
		.toD("seda:distribuidor");
		
		from("seda:distribuidor").streamCaching()
			
			.process(new Processor() {
				
				@Override
				public void process(Exchange exchange) throws Exception {
					JSONObject jsonObject = new JSONObject(exchange.getIn().getBody(Map.class));
					jsonObject.put("name", "TESTE NOME MUDADO");
					System.out.println("BODY " + exchange.getIn().getBody());
					exchange.getOut().setBody(jsonObject);
				}
			})
			.hystrix()
				.hystrixConfiguration()
		            .executionTimeoutInMilliseconds(5000).circuitBreakerSleepWindowInMilliseconds(10000)
		       .end()
		       //.inOut(nettyList)
		       .log("${body}")
			.onFallback()
				.setBody(simple("ERROR!!"))
			.end()
			
			.to("log:foo")
		.end();
	}

}
