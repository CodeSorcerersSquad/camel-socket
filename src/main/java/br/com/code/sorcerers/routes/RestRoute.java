package br.com.code.sorcerers.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

/**
 * @author victor
 *
 */

@Component
public class RestRoute extends RouteBuilder {


	@Override
	public void configure() throws Exception {

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
		

	}

}
