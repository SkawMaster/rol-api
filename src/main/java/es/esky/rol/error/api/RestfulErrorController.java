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

import es.esky.rol.error.domain.ApiError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Cristian Mateos López
 * @since 1.0.0
 */
@RestController
public class RestfulErrorController implements ErrorController {

    private final ErrorProperties errorProperties;

    @Autowired
    public RestfulErrorController(ErrorProperties errorProperties) {
        this.errorProperties = errorProperties;
    }

    @Override
    public String getErrorPath() {
        return this.errorProperties.getPath();
    }

    @RequestMapping("${server.error.path:${error.path:/error}}")
    public ApiError error() {
        ApiError error = new ApiError();
        error.setCode("unauthorized");
        error.setMessage("Authentication error");
        return error;
    }
}
