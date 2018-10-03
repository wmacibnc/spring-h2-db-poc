package spring.h2.poc.UT;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import spring.h2.poc.ConstantesMock;
import spring.h2.poc.exception.PocException;
import spring.h2.poc.model.Projeto;
import spring.h2.poc.repository.ProjetoRepository;
import spring.h2.poc.service.ProjetoService;

@SuppressWarnings("deprecation")
@RunWith(MockitoJUnitRunner.class)
public class ProjetoServiceUT {

	public ProjetoService service = new ProjetoService();

	@Mock
	ProjetoRepository projetoRepository;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		service.setProjetoRepository(projetoRepository);
		Mockito.when(projetoRepository.findById(ConstantesMock.NUMERO_PROJETO)).thenReturn(criarProjetoOptional(null));
		Mockito.when(projetoRepository.save(Mockito.any(Projeto.class))).thenReturn(criarProjeto(ConstantesMock.NUMERO_PROJETO));
		Mockito.when(projetoRepository.findAll()).thenReturn(criarProjetos());
	}

	@Test
	public void salvar() throws PocException {
		assertNotNull(service.salvar(null, ConstantesMock.NOME_TESTE, ConstantesMock.DESCRICAO_TESTE).getId());
	}

	@Test
	public void alterar() throws PocException {
		Projeto projeto = service.salvar(ConstantesMock.NUMERO_PROJETO, ConstantesMock.NOME_TESTE, ConstantesMock.DESCRICAO_TESTE);
		assertEquals(ConstantesMock.NUMERO_PROJETO, projeto.getId());
	}

	@Test
	public void obter() throws PocException {
		assertNotNull(service.obter(ConstantesMock.NUMERO_PROJETO));
	}

	@Test
	public void excluir() throws PocException {
		assertEquals(service.excluir(ConstantesMock.NUMERO_PROJETO),"Excluído com sucesso!");
	}
	
	@Test
	public void consultar() throws PocException {
		assertNotNull(service.consultar(ConstantesMock.NOME_TESTE,ConstantesMock.DESCRICAO_TESTE));
	}
	
	@Test(expected = PocException.class)
	public void validarNomeProjetoJaExiste() throws PocException {
		Mockito.when(projetoRepository.obterProjetoPorNome(ConstantesMock.NOME_TESTE)).thenReturn(criarProjeto(null));
		service.salvar(null, ConstantesMock.NOME_TESTE, ConstantesMock.DESCRICAO_TESTE);
	}

	@Test(expected = PocException.class)
	public void validarTamanhoDescricao() throws PocException {
		service.salvar(null, ConstantesMock.NOME_TESTE, gerarString(201));
	}

	@Test(expected = PocException.class)
	public void validarDescricaoNaoNulo() throws PocException {
		service.salvar(null, ConstantesMock.NOME_TESTE, null);
	}

	@Test(expected = PocException.class)
	public void validarTamanhoNome() throws PocException {
		service.salvar(null, gerarString(41), ConstantesMock.DESCRICAO_TESTE);
	}

	@Test(expected = PocException.class)
	public void validarNomeNaoNulo() throws PocException {
		service.salvar(null, null, ConstantesMock.DESCRICAO_TESTE);
	}

	@Test(expected = PocException.class)
	public void validarNumeroProjetoExiste() throws PocException {
		Mockito.when(projetoRepository.findById(ConstantesMock.NUMERO_PROJETO)).thenReturn(null);
		service.excluir(ConstantesMock.NUMERO_PROJETO);
	}

	private List<Projeto> criarProjetos() {
		List<Projeto> lista = new ArrayList<>();
		lista.add(criarProjeto(ConstantesMock.NUMERO_PROJETO));
		return lista;
	}
	
	private Optional<Projeto> criarProjetoOptional(Integer numeroProjeto) {
		return Optional.of(new Projeto(numeroProjeto, ConstantesMock.NOME_TESTE, ConstantesMock.DESCRICAO_TESTE));
	}
	
	private Projeto criarProjeto(Integer numeroProjeto) {
		return new Projeto(numeroProjeto, ConstantesMock.NOME_TESTE, ConstantesMock.DESCRICAO_TESTE);
	}

	private String gerarString(Integer tamanho) {
		String letras = "abcdefghijklmnopqrstuvwxyz";
		Random random = new Random();
		StringBuilder saida = new StringBuilder();
		for (int i = 0; i < tamanho; i++) {
			saida.append(letras.charAt(random.nextInt(letras.length())));
		}
		return saida.toString();
	}
}
