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

package es.esky.role.integration.pagination;

import cucumber.api.java.en.Then;
import es.esky.role.integration.http.HttpWorld;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * @author Cristian Mateos López
 * @since 1.0.0
 */
@SuppressWarnings("SpringJavaAutowiredMembersInspection")
public class PaginationSteps {

    @Autowired
    private HttpWorld httpWorld;

    @Then("^I should get pagination total count with value (\\d+)$")
    public void i_should_get_a_response_with_a_header_value(int expectedValue) {
        ResponseEntity response = httpWorld.loadResponse();
        int value = Integer.valueOf(response.getHeaders().get("X-Total-Count").get(0));

        assertThat(value, is(expectedValue));
    }

    @Then("^I should not get pagination links$")
    public void i_should_not_get_pagination_links() {
        ResponseEntity response = httpWorld.loadResponse();
        List<String> links = response.getHeaders().get(HttpHeaders.LINK);

        assertThat(links, nullValue());
    }

    @Then("^I should get pagination links: (.*)$")
    public void i_should_get_pagination_links(List<String> expectedLinks) {
        ResponseEntity response = httpWorld.loadResponse();
        List<String> links = response.getHeaders().get(HttpHeaders.LINK);

        for (String expectedLink : expectedLinks) {
            String expected = "rel=\"" + expectedLink + "\"";
            assertThat(links, hasItem(containsString(expected)));
        }
    }
}
