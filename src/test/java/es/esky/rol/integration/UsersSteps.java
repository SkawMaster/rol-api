package es.esky.rol.integration;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.test.context.ContextConfiguration;

import java.nio.charset.Charset;

/**
 *
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
public class UsersSteps {

    private TestRestTemplate restTemplate;
    private World world;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    public UsersSteps(TestRestTemplate restTemplate, World world) {
        this.restTemplate = restTemplate;
        this.world = world;
    }

    @Before
    public void setup() {
        this.world.setResponse(null);
        this.world.setRequest(null);
    }

    @Given("^I am authenticated as ADMIN$")
    public void i_am_authenticate_as_ADMIN() throws Throwable {
        HttpEntity request = new HttpEntity<>(createHeaders("ADMIN", "ADMIN"));
        world.setRequest(request);
    }

    @When("^I request users resource$")
    public void i_request_users_resource() throws Throwable {
        HttpEntity response = restTemplate.exchange("/users", HttpMethod.GET, world.getRequest(), String.class);
        world.setResponse(response);
    }

    @Then("^I should get a response with HTTP status code (\\d+)$")
    public void i_should_get_a_response_with_HTTP_status_code(int statusCode) throws Throwable {
        ResponseEntity response = (ResponseEntity) world.getResponse();
        Assert.assertThat(statusCode, Matchers.equalTo(response.getStatusCodeValue()));
    }

    private HttpHeaders createHeaders(String username, String password) {
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encode(auth.getBytes(Charset.defaultCharset()));
            String authHeader = "Basic " + new String(encodedAuth);
            set("Authorization", authHeader);
        }};
    }
}
