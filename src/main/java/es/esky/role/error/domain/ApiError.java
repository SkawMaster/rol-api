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
 * Api error data model.
 *
 * <p>The error can contain:
 * <ul>
 * <li>Code: Unique and self explain for find easily.</li>
 * <li>Message: Detailed description of the current error.</li>
 * </ul>
 *
 * @author Cristian Mateos López
 * @since 1.0.0
 */
public class ApiError implements Serializable {
	private static final long serialVersionUID = 1L;

	private String code;
	private String message;

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
	 * Set code error identification.
	 *
	 * @param code Error identification.
	 * @since 1.0.0
	 */
	public void setCode(String code) {
		this.code = code;
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
	 * Set message error.
	 *
	 * @param message Message error.
	 * @since 1.0.0
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("code", this.code)
				.append("message", this.message)
				.toString();
	}
}
