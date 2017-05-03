package es.esky.role.integration.users;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import es.esky.role.integration.http.HttpWorld;
import es.esky.role.users.domain.User;
import es.esky.role.users.service.UsersService;
import org.hamcrest.beans.HasProperty;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import static es.esky.role.users.domain.builder.UserBuilder.user;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;

/**
 * Users common steps.
 */
@SuppressWarnings("SpringJavaAutowiredMembersInspection")
public class UsersSteps {

    @Autowired
    private HttpWorld httpWorld;

    @Autowired
    private UsersService usersService;

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

    @Then("^I should get a response with a list of (\\d+) users$")
    public void i_should_get_a_response_with_a_list_of_users(int expectedUsers) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, User.class);
        List<User> users = mapper.readValue(httpWorld.loadResponse().getBody(), type);

        assertThat(users, hasSize(expectedUsers));
    }

    @Then("^I should get all users with the following properties: (.*)$")
    public void i_should_get_all_users_with_the_following_properties(List<String> properties) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, User.class);
        List<User> users = mapper.readValue(httpWorld.loadResponse().getBody(), type);

        for (String property : properties) {
            assertThat(users, everyItem(HasProperty.hasProperty(property)));
        }
    }
}
