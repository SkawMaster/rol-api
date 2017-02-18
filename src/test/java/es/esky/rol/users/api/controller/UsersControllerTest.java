package es.esky.rol.users.api.controller;

import es.esky.rol.users.api.exception.UserNotFoundException;
import es.esky.rol.users.domain.User;
import es.esky.rol.users.service.UsersService;
import org.jetbrains.annotations.Contract;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static es.esky.rol.users.domain.builder.UserBuilder.user;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.internal.verification.VerificationModeFactory.noMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Cristian Mateos López
 */
@SuppressWarnings("SpringJavaAutowiredMembersInspection")
@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersControllerTest {

    private static final String USERNAME1 = "NAME_1";
    private static final String PASSWORD1 = "PASSWoRD_1";
    private static final String USERNAME2 = "NAME_2";
    private static final String PASSWORD2 = "PASSWoRD_2";
    private static final String USERNAME3 = "NAME_3";
    private static final String PASSWORD3 = "PASSWoRD_3";
    private static final String PASSWORD_TOO_SHORT = "aaa";
    private static final String PASSWORD_TOO_LARGE = "aaaaaaaaaaaaaaaaaaaaa";
    private static final String PASSWORD_WITHOUT_UPPERCASE = "aaaaaaaa";
    private static final String PASSWORD_WITHOUT_LOWERCASE = "AAAAAAAA";

    private static final User USER1 = user().withUsername(USERNAME1).withPassword(PASSWORD1).build();
    private static final User USER2 = user().withUsername(USERNAME2).withPassword(PASSWORD2).build();
    private static final User USER3 = user().withUsername(USERNAME3).withPassword(PASSWORD3).build();

    private static final List<User> USERS = Arrays.asList(USER1, USER2, USER3);

    private static final int PAGE_DEFAULT = 0;
    private static final int SIZE_DEFAULT = 10;

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext applicationContext;

    @MockBean
    private UsersService usersService;

    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
    }

    @Test
    public void findByCriteria_UsersFoundWithoutNextAndPrevPage_ReturnUsersWithoutLinkHeader() throws Exception {
        Pageable page = new PageRequest(PAGE_DEFAULT, SIZE_DEFAULT);

        given(usersService.findByCriteria(Mockito.any(Pageable.class))).willReturn(new PageImpl<>(USERS, page, USERS.size()));

        mvc.perform(get(UsersController.BASE_URL + "?page=" + page.getPageNumber() + "&size=" + page.getPageSize())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$.[0].username", is(USERNAME1)))
                .andExpect(jsonPath("$.[1].username", is(USERNAME2)))
                .andExpect(header().longValue("X-Total-Count", 3))
                .andExpect(header().string("Link", isEmptyOrNullString()));

        then(usersService).should(times(1)).findByCriteria(page);
        then(usersService).should(noMoreInteractions()).findByCriteria(page);
    }

    @Test
    public void findByCriteria_UsersFoundWithNextPage_ReturnUsersWithNextLinkHeader() throws Exception {
        Pageable page = new PageRequest(PAGE_DEFAULT, 1);

        given(usersService.findByCriteria(Mockito.any(Pageable.class))).willReturn(new PageImpl<>(USERS, page, USERS.size()));

        mvc.perform(get(UsersController.BASE_URL + "?page=" + page.getPageNumber() + "&size=" + page.getPageSize())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(USERS.size())))
                .andExpect(jsonPath("$.[0].username", is(USERNAME1)))
                .andExpect(jsonPath("$.[1].username", is(USERNAME2)))
                .andExpect(header().longValue("X-Total-Count", USERS.size()))
                .andExpect(header().string("Link", allOf(containsString("\"next\""), containsString("\"last\""))));

        then(usersService).should(times(1)).findByCriteria(page);
        then(usersService).should(noMoreInteractions()).findByCriteria(page);
    }

    @Test
    public void findByCriteria_UsersFoundWithPrevPage_ReturnUsersWithPrevLinkHeader() throws Exception {
        Pageable page = new PageRequest(2, 1);

        given(usersService.findByCriteria(Mockito.any(Pageable.class))).willReturn(new PageImpl<>(USERS, page, USERS.size()));

        mvc.perform(get(UsersController.BASE_URL + "?page=" + page.getPageNumber() + "&size=" + page.getPageSize())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(USERS.size())))
                .andExpect(jsonPath("$.[0].username", is(USERNAME1)))
                .andExpect(jsonPath("$.[1].username", is(USERNAME2)))
                .andExpect(header().longValue("X-Total-Count", USERS.size()))
                .andExpect(header().string("Link", allOf(containsString("\"prev\""), containsString("\"first\""))));

        then(usersService).should(times(1)).findByCriteria(page);
        then(usersService).should(noMoreInteractions()).findByCriteria(page);
    }

    @Test
    public void findByCriteria_UsersFoundWithPrevAndNextPage_ReturnUsersWithPrevAndNextLinkHeader() throws Exception {
        Pageable page = new PageRequest(1, 1);

        given(usersService.findByCriteria(Mockito.any(Pageable.class))).willReturn(new PageImpl<>(USERS, page, USERS.size()));

        mvc.perform(get(UsersController.BASE_URL + "?page=" + page.getPageNumber() + "&size=" + page.getPageSize())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(USERS.size())))
                .andExpect(jsonPath("$.[0].username", is(USERNAME1)))
                .andExpect(jsonPath("$.[1].username", is(USERNAME2)))
                .andExpect(header().longValue("X-Total-Count", USERS.size()))
                .andExpect(header().string("Link", allOf(
                        containsString("\"next\""),
                        containsString("\"last\""),
                        containsString("\"prev\""),
                        containsString("\"first\""))
                ));

        then(usersService).should(times(1)).findByCriteria(page);
        then(usersService).should(noMoreInteractions()).findByCriteria(page);
    }

    @Test
    public void findByUsername_UserFound_ReturnUser() throws Exception {
        given(usersService.findByUsername(USERNAME1)).willReturn(USER1);

        mvc.perform(get(UsersController.BASE_URL + "/" + USERNAME1)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(USERNAME1)));

        then(usersService).should(times(1)).findByUsername(USERNAME1);
        then(usersService).should(noMoreInteractions()).findByUsername(anyString());
    }

    @Test
    public void findByUsername_UserNotFound_ReturnNotFound() throws Exception {
        given(usersService.findByUsername(anyString())).willThrow(new UserNotFoundException());

        mvc.perform(get(UsersController.BASE_URL + "/" + USERNAME1)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", is(UserNotFoundException.USER_NOT_FOUND_ERROR_CODE)))
                .andExpect(jsonPath("$.message", is(UserNotFoundException.USER_NOT_FOUND_MSG)));

        then(usersService).should(times(1)).findByUsername(anyString());
        then(usersService).should(noMoreInteractions()).findByUsername(anyString());
    }

    @Test
    public void deleteByUsername_UserFound_Return204() throws Exception {
        mvc.perform(delete(UsersController.BASE_URL + "/" + USERNAME1))
                .andExpect(status().isNoContent());

        then(usersService).should(times(1)).deleteByUsername(USERNAME1);
        then(usersService).should(noMoreInteractions()).deleteByUsername(anyString());
    }

    @Test
    public void deleteByUsername_UserNotFound_ReturnNotFound() throws Exception {
        Mockito.doThrow(new UserNotFoundException()).when(usersService).deleteByUsername(anyString());

        mvc.perform(delete(UsersController.BASE_URL + "/" + USERNAME1))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", is(UserNotFoundException.USER_NOT_FOUND_ERROR_CODE)))
                .andExpect(jsonPath("$.message", is(UserNotFoundException.USER_NOT_FOUND_MSG)));

        then(usersService).should(times(1)).deleteByUsername(anyString());
        then(usersService).should(noMoreInteractions()).deleteByUsername(anyString());
    }

    @Test
    public void saveUser_UserValid_Return201WithUserSaved() throws Exception {
        given(usersService.save(Mockito.any(User.class))).willReturn(USER1);

        // Ensure user not exists.
        given(usersService.exists(anyString())).willReturn(Boolean.FALSE);

        mvc.perform(post(UsersController.BASE_URL)
                .content(buildUserRequest())
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", is(USERNAME1)));

        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);

        then(usersService).should(times(1)).save(argumentCaptor.capture());

        User expected = argumentCaptor.getValue();
        Assert.assertThat(expected.getUsername(), is(USERNAME1));
        Assert.assertThat(expected.getPassword(), is(PASSWORD1));

        then(usersService).should(times(1)).exists(anyString());
        then(usersService).should(noMoreInteractions()).save(Mockito.any(User.class));
        then(usersService).should(noMoreInteractions()).exists(anyString());
    }

    @Test
    public void saveUser_PasswordTooShort_Return400WithErrorCode() throws Exception {
        given(usersService.save(Mockito.any(User.class))).willReturn(USER1);

        // Assert user not exists.
        given(usersService.exists(anyString())).willReturn(Boolean.FALSE);

        mvc.perform(post(UsersController.BASE_URL)
                .content(buildUserCustomPasswordRequest(PASSWORD_TOO_SHORT))
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void saveUser_PasswordTooLarge_Return400WithErrorCode() throws Exception {
        given(usersService.save(Mockito.any(User.class))).willReturn(USER1);

        // Assert user not exists.
        given(usersService.exists(anyString())).willReturn(Boolean.FALSE);

        mvc.perform(post(UsersController.BASE_URL)
                .content(buildUserCustomPasswordRequest(PASSWORD_TOO_LARGE))
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void saveUser_PasswordWithoutUppercase_Return400WithErrorCode() throws Exception {
        given(usersService.save(Mockito.any(User.class))).willReturn(USER1);

        // Assert user not exists.
        given(usersService.exists(anyString())).willReturn(Boolean.FALSE);

        mvc.perform(post(UsersController.BASE_URL)
                .content(buildUserCustomPasswordRequest(PASSWORD_WITHOUT_UPPERCASE))
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void saveUser_PasswordWithoutLowercase_Return400WithErrorCode() throws Exception {
        given(usersService.save(Mockito.any(User.class))).willReturn(USER1);

        // Assert user not exists.
        given(usersService.exists(anyString())).willReturn(Boolean.FALSE);

        mvc.perform(post(UsersController.BASE_URL)
                .content(buildUserCustomPasswordRequest(PASSWORD_WITHOUT_LOWERCASE))
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void saveUser_UsernameExist_Return400WithErrorCode() throws Exception {
        given(usersService.save(Mockito.any(User.class))).willReturn(USER1);

        // Assert user not exists.
        given(usersService.exists(anyString())).willReturn(Boolean.TRUE);

        mvc.perform(post(UsersController.BASE_URL)
                .content(buildUserRequest())
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Contract(pure = true)
    private String buildUserRequest() {
        return "{" +
                "\"username\":" + "\"" + USERNAME1 + "\"" + "," +
                "\"password\":" + "\"" + PASSWORD1 + "\"" +
                "}";
    }

    @Contract(pure = true)
    private String buildUserCustomPasswordRequest(String password) {
        return "{" +
                "\"username\":" + "\"" + USERNAME1 + "\"" + "," +
                "\"password\":" + "\"" + password + "\"" +
                "}";
    }
}
