package com.car;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;



//#022333
@SpringBootApplication
public class CarSaleApplication extends SpringBootServletInitializer {

	
	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder application) {
		return application.sources(CarSaleApplication.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(CarSaleApplication.class, args);
	}
	
	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {

		return (container -> {
			ErrorPage error403Page = new ErrorPage(HttpStatus.FORBIDDEN, "/error1");
			//ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/error1");
			container.addErrorPages(error403Page);
		});
	}
}
