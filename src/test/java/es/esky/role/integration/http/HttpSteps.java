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

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import es.esky.role.integration.authentication.AuthenticationWorld;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author Cristian Mateos López
 * @since 1.0.0
 */
@SuppressWarnings("SpringJavaAutowiredMembersInspection")
public class HttpSteps {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AuthenticationWorld authenticationWorld;

    @Autowired
    private HttpWorld httpWorld;

    /**
     * Make an api call to /users.
     */
    @When("^I get resource \"([/\\w]+)\"$")
    public void i_request_resource(String endpoint) {
        TestRestTemplate template = restTemplate;

        if (authenticationWorld.haveCredentials()) {
            template = restTemplate.withBasicAuth(authenticationWorld.getUsername(), authenticationWorld.getPassword());
        }

        ResponseEntity<String> response = template.getForEntity(endpoint, String.class);
        httpWorld.saveResponse(response);
    }

    /**
     * Make an api call to /users.
     */
    @When("^I get resource \"([/\\w]+)\" with page (\\d+)$")
    public void i_request_resource_with_page(String endpoint, int page) {
        TestRestTemplate template = restTemplate;

        if (authenticationWorld.haveCredentials()) {
            template = restTemplate.withBasicAuth(authenticationWorld.getUsername(), authenticationWorld.getPassword());
        }

        Map<String, String> urlVariables = new HashMap<>();
        urlVariables.put("page", String.valueOf(page));

        ResponseEntity<String> response = template.getForEntity(endpoint + "?page={page}", String.class, urlVariables);
        httpWorld.saveResponse(response);
    }

    /**
     * Check if response status code is the expected.
     *
     * @param expectedStatusCode Expected status code.
     */
    @Then("^I should get a status code (\\d+)$")
    public void i_should_get_a_response_with_HTTP_status_code(int expectedStatusCode) {
        ResponseEntity response = httpWorld.loadResponse();
        assertThat(response.getStatusCodeValue(), equalTo(expectedStatusCode));
    }
}
