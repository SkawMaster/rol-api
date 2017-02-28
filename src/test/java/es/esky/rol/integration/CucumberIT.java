package es.esky.rol.integration;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Cucumber integration tests loader.
 *
 * @author Cristian Mateos López
 * @since 1.0.0
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", format = "pretty")
public class CucumberIT {
}
