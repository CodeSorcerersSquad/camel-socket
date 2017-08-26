package br.com.code.sorcerers.routes;

//import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
//import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * @author victor
 *
 */

@SpringBootApplication
@ImportResource({ "classpath:META-INF/spring/camel-context.xml" })
public class ServiceApplication {

	public static void main(String[] args) {


		SpringApplication.run(ServiceApplication.class, args);
	}
	
	
//	@Bean
//	ServletRegistrationBean servletRegistrationBean() {
//		ServletRegistrationBean servlet = new ServletRegistrationBean(new CamelHttpTransportServlet(), "/rest/*");
//		servlet.setName("CamelServlet");
//		return servlet;
//	}
//
//	@Bean
//	@Order(Ordered.HIGHEST_PRECEDENCE)
//	CharacterEncodingFilter characterEncodingFilter() {
//		CharacterEncodingFilter filter = new CharacterEncodingFilter();
//		filter.setEncoding("UTF-8");
//		filter.setForceEncoding(true);
//
//		return filter;
//	}
	

}
