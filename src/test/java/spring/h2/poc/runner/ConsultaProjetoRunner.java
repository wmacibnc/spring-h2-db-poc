package spring.h2.poc.runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		plugin = {"pretty", "html:relatorios/projeto/ConsultarProjeto"},
		features = "src/test/java/spring/h2/poc/feature/consultaProjeto.feature",
		glue={"steps"},
		monochrome = true
)
public class ConsultaProjetoRunner {

}
