package com.gf.fifteen;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableNeo4jRepositories
@EnableSwagger2
public class FifteenApplication {
	public static final void main(final String[] args) {
		SpringApplication.run(FifteenApplication.class, args);
	}
	
	@Bean
	@Scope("singleton")
	public FilterRegistrationBean filterRegistrationBean() {
		final FilterRegistrationBean filterBean = new FilterRegistrationBean();
		filterBean.setFilter(new ShallowEtagHeaderFilter());
		filterBean.setUrlPatterns(Arrays.asList("*"));
		return filterBean;
	}
}
