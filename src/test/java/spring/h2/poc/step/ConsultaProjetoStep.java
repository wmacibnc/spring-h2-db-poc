package spring.h2.poc.step;

import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Quando;

public class ConsultaProjetoStep {
	
	private Scenario scenario;
	
	@Before
	public void before(Scenario scenario) {
		this.scenario = scenario;
	}
	
	@Dado("^eu um Usuário$")
	public void eu_um_Usuário() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Quando("^consultar todos os projetos$")
	public void consultar_todos_os_projetos() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}
	
}
