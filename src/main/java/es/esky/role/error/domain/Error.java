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

package es.esky.role.error.domain;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Error info entity.
 *
 * @author Cristian Mateos López
 * @since 1.0.0
 */
public class Error implements Serializable {
	private static final long serialVersionUID = 1L;

	private String code;
	private String message;
	private String description;

	/**
	 * Create an instance of an Error.
	 *
	 * @param code        Code of the error.
	 * @param message     Message of the error.
	 * @param description Description of the error.
	 * @since 1.0.0
	 */
	public Error(String code, String message, String description) {
		this.code = code;
		this.message = message;
		this.description = description;
	}

	/**
	 * Get code error identification.
	 *
	 * @return Error identification.
	 * @since 1.0.0
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * Get message error.
	 *
	 * @return Message error.
	 * @since 1.0.0
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * Get description error.
	 *
	 * @return Description error.
	 * @since 1.0.0
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("code", this.code)
				.append("message", this.message)
				.append("description", this.description)
				.toString();
	}
}
