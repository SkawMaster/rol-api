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

package es.esky.role.integration.http;

import es.esky.role.integration.WorldLifecycle;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * Share users data between steps.
 *
 * @author Cristian Mateos López
 * @since 1.0.0
 */
@Component
public class HttpWorld implements WorldLifecycle {

    private ResponseEntity<String> pageUsersResponse;

    /**
     * {@inheritDoc}
     */
    @Override
    public void before() {
        this.pageUsersResponse = null;
    }

    /**
     * Save a response from API.
     *
     * @param response API Response.
     */
    public void saveResponse(ResponseEntity<String> response) {
        this.pageUsersResponse = response;
    }

    /**
     * Return an saved users page response.
     *
     * @return Saved users page response.
     */
    public ResponseEntity<String> loadResponse() {
        return this.pageUsersResponse;
    }
}
