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

package es.esky.role.pagination.advice;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import es.esky.role.pagination.PaginationHeadersBuilder;

/**
 * Intercept {@link Page} responses, save pagination data to headers and return the page content in the response.
 *
 * @author Cristian Mateos López
 * @since 1.0.0
 */
@ControllerAdvice
public class PaginationAdvice implements ResponseBodyAdvice<Object> {
	private final PaginationHeadersBuilder paginationHeadersBuilder;

	/**
	 * Construct a new instance.
	 *
	 * @param paginationHeadersBuilder Builder for pagination headers.
	 * @since 1.0.0
	 */
	@Autowired
	public PaginationAdvice(@NotNull PaginationHeadersBuilder paginationHeadersBuilder) {
		Assert.notNull(paginationHeadersBuilder, "PaginationHeadersBuilder must not be null");
		this.paginationHeadersBuilder = paginationHeadersBuilder;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
								  Class<? extends HttpMessageConverter<?>> selectedConverterType,
								  ServerHttpRequest request, ServerHttpResponse response) {
		Page<?> page = (Page<?>) body;
		this.paginationHeadersBuilder.addPaginationData(response.getHeaders(), page);
		return page.getContent();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return Page.class.isAssignableFrom(returnType.getParameterType());
	}
}
