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

package es.esky.role.error.api;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.esky.role.error.domain.Error;

/**
 * Transform undefined server errors to {@link Error} model.
 *
 * @author Cristian Mateos López
 * @since 1.0.0
 */
@RestController
public class RestfulErrorController implements ErrorController {
	private final ErrorProperties errorProperties;

	/**
	 * Construct the controller with the current server error properties.
	 *
	 * @param errorProperties Api error properties.
	 * @since 1.0.0
	 */
	@Autowired
	public RestfulErrorController(@NotNull ErrorProperties errorProperties) {
		Assert.notNull(errorProperties, "ErrorProperties must not be null");
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
	 * Catch an undefined server error and map it to {@link Error} model.
	 *
	 * @return Error mapped to {@link Error} model.
	 * @since 1.0.0
	 */
	@RequestMapping("${server.error.path:${error.path:/error}}")
	public Error catchError() {
		// TODO: get error info of current request and response.
		return new Error("unauthorized", "Authentication error", "");
	}
}
