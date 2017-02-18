package es.esky.rol.users.domain.validator;

import es.esky.rol.users.domain.User;
import es.esky.rol.users.service.UsersService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static es.esky.rol.users.domain.builder.UserBuilder.user;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.internal.verification.VerificationModeFactory.noMoreInteractions;

/**
 * @author Cristian Mateos López
 */
@RunWith(MockitoJUnitRunner.class)
public class UniqueValidatorTest {

    private final static String USERNAME1 = "NAME_1";
    private final static String PASSWORD1 = "PASS_1";

    private final static User USER1 = user().withUsername(USERNAME1).withPassword(PASSWORD1).build();

    @InjectMocks
    private UniqueValidator uniqueValidator;

    @Mock
    private UsersService usersService;

    @Test
    public void isValid_UserNotExists_ReturnTrue() throws Exception {
        given(usersService.exists(USERNAME1)).willReturn(Boolean.FALSE);

        boolean expected = uniqueValidator.isValid(USER1, null);

        assertThat(expected, is(Boolean.TRUE));
        then(usersService).should(times(1)).exists(USERNAME1);
        then(usersService).should(noMoreInteractions()).exists(USERNAME1);
    }

    @Test
    public void isValid_UserExists_ReturnFalse() throws Exception {
        given(usersService.exists(USERNAME1)).willReturn(Boolean.TRUE);

        boolean expected = uniqueValidator.isValid(USER1, null);

        assertThat(expected, is(Boolean.FALSE));
        then(usersService).should(times(1)).exists(USERNAME1);
        then(usersService).should(noMoreInteractions()).exists(USERNAME1);
    }
}
