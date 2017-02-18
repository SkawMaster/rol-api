package es.esky.rol.users.service;

import es.esky.rol.users.api.exception.UserNotFoundException;
import es.esky.rol.users.domain.User;
import es.esky.rol.users.repository.UsersRepository;
import es.esky.rol.users.service.impl.JpaUsersService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

import static es.esky.rol.users.domain.builder.UserBuilder.user;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.internal.verification.VerificationModeFactory.noMoreInteractions;

/**
 * @author Cristian Mateos López
 */
@RunWith(MockitoJUnitRunner.class)
public class JpaUsersServiceTest {

    private final static String USERNAME1 = "NAME_1";
    private final static String PASSWORD1 = "PASS_1";
    private final static String USERNAME2 = "NAME_2";
    private final static String PASSWORD2 = "PASS_2";
    private final static String ENCODED_PASSWORD = "AaA";

    private final static User USER1 = user().withUsername(USERNAME1).withPassword(PASSWORD1).build();
    private final static User USER2 = user().withUsername(USERNAME2).withPassword(PASSWORD2).build();

    private final static List<User> USERS = Arrays.asList(USER1, USER2);

    private final static int PAGE_DEFAULT = 0;
    private final static int SIZE_DEFAULT = 10;

    @InjectMocks
    private JpaUsersService usersService;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void findByCriteria_UsersFound_ReturnUsers() {
        Pageable page = new PageRequest(PAGE_DEFAULT, SIZE_DEFAULT);
        given(usersRepository.findAll(page)).willReturn(new PageImpl<>(USERS, page, USERS.size()));

        Page<User> users = usersService.findByCriteria(page);

        assertThat(users.getTotalElements(), is(2L));
        assertThat(users.getSize(), is(SIZE_DEFAULT));
        assertThat(users.getNumber(), is(PAGE_DEFAULT));
        assertThat(users.getContent(), hasSize(2));
        assertThat(users.getContent(), contains(Arrays.asList(
                allOf(hasProperty("username", is(USERNAME1)), hasProperty("password", is(PASSWORD1))),
                allOf(hasProperty("username", is(USERNAME2)), hasProperty("password", is(PASSWORD2)))
        )));

        then(usersRepository).should(times(1)).findAll(page);
        then(usersRepository).should(noMoreInteractions()).findAll(Mockito.any(Pageable.class));
    }

    @Test
    public void findByUsername_UserFound_ReturnOptionalWithUser() {
        given(usersRepository.findOne(USERNAME1)).willReturn(USER1);

        User user = usersService.findByUsername(USERNAME1);

        assertThat(user, notNullValue());
        assertThat(user, is(USER1));

        then(usersRepository).should(times(1)).findOne(USERNAME1);
        then(usersRepository).should(noMoreInteractions()).findOne(anyString());
    }

    @Test(expected = UserNotFoundException.class)
    public void findByUsername_UserNotFound_ThrowUserNotFoundException() {
        given(usersRepository.findOne(anyString())).willReturn(null);

        usersService.findByUsername(USERNAME1);

        then(usersRepository).should(times(1)).findOne(USERNAME1);
        then(usersRepository).should(noMoreInteractions()).findOne(anyString());
    }

    @Test
    public void deleteByUsername_UserFound_InvokeDeleteEntity() {
        usersService.deleteByUsername(USERNAME1);

        then(usersRepository).should(times(1)).delete(USERNAME1);
        then(usersRepository).should(noMoreInteractions()).delete(anyString());
    }

    @Test(expected = UserNotFoundException.class)
    public void deleteByUsername_UserNotFound_ThrowUserNotFoundException() {
        doThrow(EmptyResultDataAccessException.class).when(usersRepository).delete(anyString());

        usersService.findByUsername(USERNAME1);

        then(usersRepository).should(times(1)).findOne(USERNAME1);
        then(usersRepository).should(noMoreInteractions()).findOne(anyString());
    }

    @Test
    public void save_UserSavedRightfully_ReturnNewUser() {
        given(usersRepository.save(Mockito.any(User.class))).willAnswer(sameUser());
        given(passwordEncoder.encode(PASSWORD1)).willReturn(ENCODED_PASSWORD);

        User user = usersService.save(USER1);

        assertThat(user.getUsername(), is(USERNAME1));
        assertThat(user.getPassword(), is(ENCODED_PASSWORD));

        then(usersRepository).should(times(1)).save(Mockito.any(User.class));
        then(usersRepository).should(noMoreInteractions()).save(Mockito.any(User.class));
    }

    @Test
    public void exist_UserExist_ReturnTrue() {
        given(usersRepository.exists(USERNAME1)).willReturn(Boolean.TRUE);

        boolean expected = usersService.exists(USERNAME1);

        assertThat(expected, is(Boolean.TRUE));
        then(usersRepository).should(times(1)).exists(USERNAME1);
        then(usersRepository).should(noMoreInteractions()).exists(USERNAME1);
    }

    @Test
    public void exist_UserExist_ReturnFalse() {
        given(usersRepository.exists(USERNAME1)).willReturn(Boolean.FALSE);

        boolean expected = usersService.exists(USERNAME1);

        assertThat(expected, is(Boolean.FALSE));
        then(usersRepository).should(times(1)).exists(USERNAME1);
        then(usersRepository).should(noMoreInteractions()).exists(USERNAME1);
    }

    private static Answer<User> sameUser() {
        return invocation -> {
            Object[] args = invocation.getArguments();
            return (User) args[0];
        };
    }
}
