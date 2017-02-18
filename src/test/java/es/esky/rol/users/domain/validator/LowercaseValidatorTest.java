package es.esky.rol.users.domain.validator;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * @author Cristian Mateos López
 */
public class LowercaseValidatorTest {

    private final static String STRING_WITH_LOWERCASE = "AaA";
    private final static String STRING_WITHOUT_LOWERCASE = "AAA";

    private final LowercaseValidator validator = new LowercaseValidator();

    @Test
    public void isValid_StringWithUppercase_ReturnTrue() throws Exception {
        boolean expected = validator.isValid(STRING_WITH_LOWERCASE, null);

        assertThat(expected, is(Boolean.TRUE));
    }

    @Test
    public void isValid_StringWithoutUppercase_ReturnFalse() throws Exception {
        boolean expected = validator.isValid(STRING_WITHOUT_LOWERCASE, null);

        assertThat(expected, is(Boolean.FALSE));
    }
}