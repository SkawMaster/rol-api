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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.http.HttpStatus;

/**
 * Data about current response to use for logging.
 *
 * @author Cristian Mateos López
 * @since 1.0.0
 */
public class ResponseLogData {
	private HttpStatus status;
	private String body;

	/**
	 * Create a new immutable instance.
	 *
	 * @param status Status of the response.
	 * @param body   Body of the response.
	 * @since 1.0.0
	 */
	public ResponseLogData(HttpStatus status, String body) {
		this.status = status;
		this.body = body;
	}

	/**
	 * Get the status code of the response.
	 *
	 * @return Response status code.
	 * @since 1.0.0
	 */
	public HttpStatus getStatus() {
		return this.status;
	}

	/**
	 * Get the body of the response.
	 *
	 * @return Response body.
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
				.append("status", this.status)
				.append("body", this.body)
				.toString();
	}
}
