package es.esky.rol.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.not;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContextIT {

    @Autowired
    private ErrorProperties errorProperties;

    @Test
    public void contexLoads() throws Exception {
        assertThat(errorProperties, not(nullValue()));
    }
}
