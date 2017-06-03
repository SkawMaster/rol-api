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

package es.esky.role.users.api.exception;

import javax.validation.constraints.NotNull;

/**
 * Throw when not an user is not found.
 *
 * @author Cristian Mateos López
 * @since 1.0.0
 */
public class UserNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 * Construct a new user not found exception with a specified detail message and cause.
	 *
	 * @param message Detailed error message.
	 * @param cause   Cause of the exception.
	 * @since 1.0.0
	 */
	public UserNotFoundException(@NotNull String message, @NotNull Throwable cause) {
		super(message, cause);
	}

	/**
	 * Construct a new user not found exception with a specified cause.
	 *
	 * @param cause Cause of the exception.
	 * @since 1.0.0
	 */
	public UserNotFoundException(@NotNull Throwable cause) {
		super("User not found", cause);
	}

	/**
	 * Construct a new user not found exception without cause and with a specified message.
	 *
	 * @param message Detailed error message.
	 * @since 1.0.0
	 */
	public UserNotFoundException(@NotNull String message) {
		super(message);
	}

	/**
	 * Construct a new user not found exception without cause and with the default message.
	 *
	 * @since 1.0.0
	 */
	public UserNotFoundException() {
		super("User not found");
	}
}
