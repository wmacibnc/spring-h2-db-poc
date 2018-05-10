package spring.h2.poc.UT;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import spring.h2.poc.ConstantesMock;
import spring.h2.poc.exception.PocException;
import spring.h2.poc.model.Projeto;
import spring.h2.poc.model.HistoricoProjeto.AcaoEnum;
import spring.h2.poc.repository.HistoricoProjetoRepository;
import spring.h2.poc.service.HistoricoProjetoService;

@RunWith(MockitoJUnitRunner.class)
public class HistoricoProjetoServiceUT {

	public HistoricoProjetoService service = new HistoricoProjetoService();

	@Mock
	private HistoricoProjetoRepository historicoProjetoRepository;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		service.setHistoricoProjetoRepository(historicoProjetoRepository);
	}

	@Test
	public void salvar() throws PocException {
		service.salvar(criarProjeto(ConstantesMock.NUMERO_PROJETO), AcaoEnum.SALVAR);
	}

	private Projeto criarProjeto(Integer numeroProjeto) {
		return new Projeto(numeroProjeto, ConstantesMock.NOME_TESTE, ConstantesMock.DESCRICAO_TESTE);
	}

}
