package es.esky.rol.users.domain.validator;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Cristian Mateos López
 */
public class UppercaseValidatorTest {

    private final static String STRING_WITH_UPPERCASE = "aAa";
    private final static String STRING_WITHOUT_UPPERCASE = "aaa";

    private final UppercaseValidator validator = new UppercaseValidator();

    @Test
    public void isValid_StringWithUppercase_ReturnTrue() throws Exception {
        boolean expected = validator.isValid(STRING_WITH_UPPERCASE, null);

        assertThat(expected, is(Boolean.TRUE));
    }

    @Test
    public void isValid_StringWithoutUppercase_ReturnFalse() throws Exception {
        boolean expected = validator.isValid(STRING_WITHOUT_UPPERCASE, null);

        assertThat(expected, is(Boolean.FALSE));
    }

}