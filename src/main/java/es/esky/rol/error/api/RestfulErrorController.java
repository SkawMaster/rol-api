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

package es.esky.rol.error.api;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.esky.rol.error.domain.ApiError;

/**
 * Transform undefined server errors to {@link ApiError} model.
 *
 * @author Cristian Mateos López
 * @since 1.0.0
 */
@RestController
public class RestfulErrorController implements ErrorController {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestfulErrorController.class);

	private final ErrorProperties errorProperties;

	/**
	 * Construct the controller with the current server error properties.
	 *
	 * @param errorProperties Api error properties.
	 */
	@Autowired
	public RestfulErrorController(@NotNull ErrorProperties errorProperties) {
		Assert.notNull(errorProperties);
		this.errorProperties = errorProperties;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getErrorPath() {
		return this.errorProperties.getPath();
	}

	/**
	 * Catch an undefined server error and map it to {@link ApiError} model.
	 *
	 * @return Error mapped to {@link ApiError} model.
	 */
	@RequestMapping("${server.error.path:${error.path:/error}}")
	public ApiError catchError() {
		// TODO: get error info of current request and response.
		ApiError error = new ApiError();
		error.setCode("unauthorized");
		error.setMessage("Authentication error");

		LOGGER.info("Api error throw with value {}", error);

		return error;
	}
}
