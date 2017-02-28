package es.esky.rol.integration;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import es.esky.rol.users.domain.User;
import es.esky.rol.users.domain.builder.UserBuilder;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

/**
 *
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
public class UsersSteps {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private World world;

    @Before
    public void setup() {
        this.world.setResponse(null);
        this.world.setUserLogged(null);
    }

    @Given("^I am authenticated as ADMIN$")
    public void i_am_authenticate_as_ADMIN() throws Throwable {
        world.setUserLogged(UserBuilder.user().withUsername("ADMIN").withPassword("ADMIN").build());
    }

    @When("^I request users resource$")
    public void i_request_users_resource() throws Throwable {
        User user = world.getUserLogged();
        TestRestTemplate template = restTemplate;

        if (user != null) {
            template = restTemplate.withBasicAuth(user.getUsername(), user.getPassword());
        }

        HttpEntity response = template.exchange("/users", HttpMethod.GET, null, String.class);
        world.setResponse(response);
    }

    @Then("^I should get a response with HTTP status code (\\d+)$")
    public void i_should_get_a_response_with_HTTP_status_code(int statusCode) throws Throwable {
        ResponseEntity response = (ResponseEntity) world.getResponse();
        Assert.assertThat(statusCode, Matchers.equalTo(response.getStatusCodeValue()));
    }
}
