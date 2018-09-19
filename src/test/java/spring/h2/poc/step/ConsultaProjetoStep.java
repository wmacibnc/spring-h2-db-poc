package spring.h2.poc.step;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;

public class ConsultaProjetoStep {

	private Scenario scenario;

	@Before
	public void before(Scenario scenario) {
		this.scenario = scenario;
	}

	@After("@bairro")
	public void tearDown(Scenario scenario) {
		System.out.println(scenario.getName());
	}
	
	@Dado("^que eu acesse a aplicação$")
	public void que_eu_acesse_a_aplicação() throws Throwable {
	}

	@Dado("^informar o nome do projeto \"([^\"]*)\"$")
	public void informar_o_nome_do_projeto(String arg1) throws Throwable {

	}

	@Quando("^eu clicar em Pesquisar$")
	public void eu_clicar_em_Pesquisar() throws Throwable {

	}

	@Entao("^a aplicação deverá apresentar a lista de projetos em ordem crescente$")
	public void a_aplicação_deverá_apresentar_a_lista_de_projetos_em_ordem_crescente() throws Throwable {
		
	}

}
