package br.com.code.sorcerers.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

public class RestRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
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
		
		from("direct:teste")
			.setBody(simple("TESTE BODY"))
			.to("log:foo")
		.end();
	}

}
