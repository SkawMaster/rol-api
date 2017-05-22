/*
 * Copyright 2016-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.esky.role;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.Ordered;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import es.esky.role.http.logging.CustomizableLoggingFilter;

/**
 * Api entry point and main configuration class.
 *
 * @author Cristian Mateos López
 * @since 1.0.0
 */
@SpringBootApplication
@EnableAutoConfiguration
public class Application {
	/**
	 * Api Entry point.
	 *
	 * @param args Application call arguments.
	 */
	public static void main(String... args) {
		SpringApplication.run(Application.class, args);
	}

	/**
	 * Construct a {@link ErrorProperties} with the current server properties.
	 *
	 * @param serverProperties Current server properties.
	 * @return A singleton {@link ErrorProperties} bean.
	 * @since 1.0.0
	 */
	@Bean
	public ErrorProperties errorProperties(ServerProperties serverProperties) {
		return serverProperties.getError();
	}

	/**
	 * Construct a {@link UriComponentsBuilder} with the current http request.
	 *
	 * @return A request scope {@link UriComponentsBuilder} bean.
	 * @since 1.0.0
	 */
	@Bean
	@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public UriComponentsBuilder uriComponentsBuilder() {
		return ServletUriComponentsBuilder.fromCurrentRequest();
	}

	/**
	 * Register the logging request and response system.
	 *
	 * @return A new {@link FilterRegistrationBean} with {@link CustomizableLoggingFilter} in the highest precedence
	 * order.
	 * @since 1.0.0
	 */
	@Bean
	public FilterRegistrationBean filterBean() {
		CustomizableLoggingFilter filter = new CustomizableLoggingFilter();

		FilterRegistrationBean bean = new FilterRegistrationBean(filter);
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);

		return bean;
	}
}
