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

package es.esky.role.integration.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java.en.Then;
import es.esky.role.error.domain.ApiError;
import es.esky.role.integration.http.HttpWorld;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.junit.Assert.assertThat;

/**
 * @author Cristian Mateos López
 * @since 1.0.0
 */
@SuppressWarnings("SpringJavaAutowiredMembersInspection")
public class ErrorSteps {

    @Autowired
    private HttpWorld httpWorld;

    @Then("^I should get an error response with the following attributes:$")
    public void i_should_get_an_error_response_with_the_following_attributes(Map<String, String> attributes) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        ApiError error = mapper.readValue(httpWorld.loadResponse().getBody(), ApiError.class);

        for (String key : attributes.keySet()) {
            assertThat(error, hasProperty(key, equalTo(attributes.get(key))));
        }
    }
}
