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

package es.esky.role.http.logging;

import java.net.URI;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

/**
 * Data about current response to use for logging.
 *
 * @author Cristian Mateos López
 * @since 1.0.0
 */
public class RequestLogData {
	private URI uri;
	private HttpMethod method;
	private HttpHeaders headers;
	private String body;

	/**
	 * Create a new immutable instance.
	 *
	 * @param uri     URI of the request.
	 * @param method  Http method of the request.
	 * @param headers Headers of the request.
	 * @param body    Body of the request.
	 * @since 1.0.0
	 */
	public RequestLogData(URI uri, HttpMethod method, HttpHeaders headers, String body) {
		this.uri = uri;
		this.method = method;
		this.headers = headers;
		this.body = body;
	}

	/**
	 * Get the URI of the request.
	 *
	 * @return Request URI.
	 * @since 1.0.0
	 */
	public URI getUri() {
		return this.uri;
	}

	/**
	 * Get the http method of the request.
	 *
	 * @return Request http method.
	 * @since 1.0.0
	 */
	public HttpMethod getMethod() {
		return this.method;
	}

	/**
	 * Get the headers of the request.
	 *
	 * @return Request headers.
	 * @since 1.0.0
	 */
	public HttpHeaders getHeaders() {
		return this.headers;
	}

	/**
	 * Get the body of the request.
	 *
	 * @return Request body.
	 * @since 1.0.0
	 */
	public String getBody() {
		return this.body;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("uri", this.uri)
				.append("method", this.method)
				.append("headers", this.headers)
				.append("body", this.body)
				.toString();
	}
}
