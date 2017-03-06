package es.esky.rol.integration.users;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import es.esky.rol.Application;
import es.esky.rol.integration.authentication.AuthenticationWorld;
import es.esky.rol.users.domain.User;
import es.esky.rol.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import static es.esky.rol.users.domain.builder.UserBuilder.user;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsArrayContaining.hasItemInArray;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;

/**
 * Users query common steps.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = Application.class)
public class QueryPageUsersSteps {

    public static final String USERS_ENDPOINT = "/users";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UsersWorld usersWorld;

    @Autowired
    private AuthenticationWorld authenticationWorld;

    @Autowired
    private UsersService usersService;

    /**
     * Initialize worlds for steps execution.
     */
    @Before
    public void setup() {
        usersWorld.reset();
        authenticationWorld.reset();
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");
        jdbcTemplate.execute("TRUNCATE TABLE USERS");
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE");
    }

    @Given("^An user (\\w+) with password (\\w+)$")
    public void an_user_with_password(String username, String password) {
        usersService.save(user().withUsername(username).withPassword(password).build());
    }

    @Given("^Exist (\\d+) generic users$")
    public void generic_users(int number) throws Throwable {
        for (int i = 0; i < number; i++) {
            usersService.save(user().withUsername("USER" + Calendar.getInstance().getTimeInMillis()).withPassword("").build());
        }
    }

    /**
     * Login a user with user and password credentials.
     */
    @Given("^I am authenticated as (\\w+) user with password (\\w+)$")
    public void i_am_authenticate_as(String username, String password) {
        authenticationWorld.saveCredentials(username, password);
    }

    /**
     * Make an api call to /users.
     */
    @When("^I get resource /users$")
    public void i_request_users_resource() {
        TestRestTemplate template = restTemplate;

        if (authenticationWorld.haveCredentials()) {
            template = restTemplate.withBasicAuth(authenticationWorld.getUsername(), authenticationWorld.getPassword());
        }

        ResponseEntity<String> response = template.getForEntity(USERS_ENDPOINT, String.class);
        usersWorld.saveResponse(response);
    }

    /**
     * Check if response status code is the expected.
     *
     * @param expectedStatusCode Expected status code.
     */
    @Then("^I should get a status code (\\d+)$")
    public void i_should_get_a_response_with_HTTP_status_code(int expectedStatusCode) {
        ResponseEntity response = usersWorld.loadResponse();
        assertThat(response.getStatusCodeValue(), equalTo(expectedStatusCode));
    }

    @Then("^I should get a response with a list of (\\d+) users$")
    public void i_should_get_a_response_with_a_list_of_users(int expectedUsers) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, User.class);
        List<User> users = mapper.readValue(usersWorld.loadResponse().getBody(), type);

        assertThat(users, hasSize(expectedUsers));
    }

    @Then("^I should get pagination total count with value (\\d+)$")
    public void i_should_get_a_response_with_a_header_value(int expectedValue) {
        ResponseEntity response = usersWorld.loadResponse();
        int value = Integer.valueOf(response.getHeaders().get("X-Total-Count").get(0));

        assertThat(value, is(expectedValue));
    }

    @Then("^I should get pagination (\\w+) link$")
    public void i_should_get_pagination_link(String link) {
        ResponseEntity response = usersWorld.loadResponse();
        String[] links = response.getHeaders().get(HttpHeaders.LINK).get(0).split(";");

        assertThat(links, hasItemInArray(containsString(link)));
    }
}
