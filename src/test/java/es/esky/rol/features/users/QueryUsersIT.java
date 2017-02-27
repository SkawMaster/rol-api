package es.esky.rol.features.users;

import es.esky.rol.arch.domain.ApiError;
import es.esky.rol.users.domain.User;
import es.esky.rol.users.domain.builder.UserBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsArrayContainingInAnyOrder.arrayContainingInAnyOrder;
import static org.hamcrest.collection.IsArrayWithSize.arrayWithSize;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;

/**
 * Integration tests for query users feature.
 *
 * @author Cristian Mateos López
 * @since 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QueryUsersIT {

    private static final String USERS_RESOURCE_ENDPOINT = "/users";
    private static final User ADMIN_DEFAULT_USER = UserBuilder.user().withUsername("ADMIN").withPassword("ADMIN").build();
    private static final int DEFAULT_PAGE_SIZE = 20;
    private static final int TOTAL_USERS = 46;

    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void givenAdminLogged_WhenRequestUsers_ThenReturnOKAndListUsers() {
        ResponseEntity<List<User>> usersResponse = restTemplate
                .withBasicAuth(ADMIN_DEFAULT_USER.getUsername(), ADMIN_DEFAULT_USER.getPassword())
                .exchange(USERS_RESOURCE_ENDPOINT, HttpMethod.GET, null, new TypeListUsers());

        assertThat(usersResponse.getStatusCode(), is(HttpStatus.OK));
        assertThat(usersResponse.getBody(), hasSize(DEFAULT_PAGE_SIZE));
    }

    @Test
    public void givenAdminLogged_WhenRequestUsersWithoutPage_ThenReturnFirstPageDetails() {
        ResponseEntity<List<User>> usersResponse = restTemplate
                .withBasicAuth(ADMIN_DEFAULT_USER.getUsername(), ADMIN_DEFAULT_USER.getPassword())
                .exchange(USERS_RESOURCE_ENDPOINT, HttpMethod.GET, null, new TypeListUsers());

        assertThat(usersResponse.getHeaders().get("X-Total-Count").get(0), is(String.valueOf(TOTAL_USERS)));

        String[] pagesInfo = usersResponse.getHeaders().get(HttpHeaders.LINK).get(0).split(",");
        assertThat(pagesInfo, arrayContainingInAnyOrder(Arrays.asList(containsString("next"), containsString("last"))));
        assertThat(pagesInfo, arrayWithSize(2));
    }

    @Test
    public void givenAdminLogged_WhenRequestUsersFirstPage_ThenReturnFirstPageDetails() {
        ResponseEntity<List<User>> usersResponse = restTemplate
                .withBasicAuth(ADMIN_DEFAULT_USER.getUsername(), ADMIN_DEFAULT_USER.getPassword())
                .exchange(USERS_RESOURCE_ENDPOINT + "?page=0", HttpMethod.GET, null, new TypeListUsers());

        assertThat(usersResponse.getHeaders().get("X-Total-Count").get(0), is(String.valueOf(TOTAL_USERS)));

        String[] pagesInfo = usersResponse.getHeaders().get(HttpHeaders.LINK).get(0).split(",");
        assertThat(pagesInfo, arrayContainingInAnyOrder(Arrays.asList(containsString("next"), containsString("last"))));
        assertThat(pagesInfo, arrayWithSize(2));
    }

    @Test
    public void givenAdminLogged_WhenRequestUsersSecondPage_ThenReturnMiddlePageDetails() {
        ResponseEntity<List<User>> usersResponse = restTemplate
                .withBasicAuth(ADMIN_DEFAULT_USER.getUsername(), ADMIN_DEFAULT_USER.getPassword())
                .exchange(USERS_RESOURCE_ENDPOINT + "?page=1", HttpMethod.GET, null, new TypeListUsers());

        assertThat(usersResponse.getHeaders().get("X-Total-Count").get(0), is(String.valueOf(TOTAL_USERS)));

        String[] pagesInfo = usersResponse.getHeaders().get(HttpHeaders.LINK).get(0).split(",");
        assertThat(pagesInfo, arrayContainingInAnyOrder(Arrays.asList(
                containsString("first"),
                containsString("prev"),
                containsString("next"),
                containsString("last"))));
        assertThat(pagesInfo, arrayWithSize(4));
    }

    @Test
    public void givenAdminLogged_WhenRequestUsersThirdPage_ThenReturnLastPageDetails() {
        ResponseEntity<List<User>> usersResponse = restTemplate
                .withBasicAuth(ADMIN_DEFAULT_USER.getUsername(), ADMIN_DEFAULT_USER.getPassword())
                .exchange(USERS_RESOURCE_ENDPOINT + "?page=2", HttpMethod.GET, null, new TypeListUsers());

        assertThat(usersResponse.getHeaders().get("X-Total-Count").get(0), is(String.valueOf(TOTAL_USERS)));

        String[] pagesInfo = usersResponse.getHeaders().get(HttpHeaders.LINK).get(0).split(",");
        assertThat(pagesInfo, arrayContainingInAnyOrder(Arrays.asList(containsString("prev"), containsString("first"))));
        assertThat(pagesInfo, arrayWithSize(2));
    }

    @Test
    public void givenNoUserLogged_WhenRequestUsers_ThenReturn401Error() {
        ResponseEntity<ApiError> usersResponse = restTemplate
                .exchange(USERS_RESOURCE_ENDPOINT, HttpMethod.GET, null, ApiError.class);

        assertThat(usersResponse.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
    }

//    @Test
//    public void givenNoUserLogged_WhenRequestUsers_ThenReturnUnauthorizedCodeAndMessage() {
//        ResponseEntity<ApiError> usersResponse = restTemplate
//                .exchange(USERS_RESOURCE_ENDPOINT, HttpMethod.GET, null, ApiError.class);
//
//        assertThat(usersResponse.getBody().getCode(), is("user_not_authenticate"));
//        assertThat(usersResponse.getBody().getMessage(), is("Full authentication is required to access this resource"));
//    }

    private class TypeListUsers extends ParameterizedTypeReference<List<User>> {
    }
}
