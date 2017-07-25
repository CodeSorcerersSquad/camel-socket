package br.com.code.sorcerers.routes;

import java.io.IOException;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

public class RestRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		// Pega a as URIS e separa em um array dividido por virgula
		String[] uris = getContext().resolvePropertyPlaceholders("{{addresses}}").split(",");
		String[] nettyList = new String[uris.length];
		
		// Itera o array de URIS para poder montar os caminhos desejados de socket
		for(int i = 0; i < uris.length; i++) {
			StringBuilder path = new StringBuilder("netty4-http:http://");
			path.append(uris[i]);//.append("?textline=true&synchronous=").append(String.valueOf(sync)).append("&clientMode=true");
			nettyList[i] = path.toString();
		}
		
		restConfiguration().component("spark-rest")
			// configuracao de contexto, host e porta
			.contextPath("/").host("{{host}}").port("{{port}}")
			// configuracao de binding para efetuar automaticamente bind
			// json para pojo
			.bindingMode(RestBindingMode.json)
			// formatar saida com pretty print
			.dataFormatProperty("prettyPrint", "true")
			// adicao de swagger api-doc
			.apiContextPath("/api-doc/custom-socket").apiProperty("api.title", "Camel Socket")
			.apiProperty("api.version", "1.0.0").apiProperty("cors", "true");
		
		rest()
			.post("/socket")
		.toD("direct:teste");
		
		from("seda:distribuidor")
			.log("${body}")
			.process(new Processor() {
				
				@Override
				public void process(Exchange exchange) throws Exception {
					System.out.println("BODY " + exchange.getIn().getBody());
				}
			})
			.loadBalance()
			.hystrix()
			// TODO
			
			
			
			.inOut(nettyList)
		.end();
	}

}
