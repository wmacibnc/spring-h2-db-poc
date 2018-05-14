package spring.h2.poc.IT;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import spring.h2.poc.AppConfigIT;
import spring.h2.poc.ConstantesMock;
import spring.h2.poc.controller.ProjetoController;
import spring.h2.poc.model.HistoricoProjeto;
import spring.h2.poc.model.Projeto;
import spring.h2.poc.model.HistoricoProjeto.AcaoEnum;
import spring.h2.poc.repository.HistoricoProjetoRepository;
import spring.h2.poc.service.ProjetoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfigIT.class })
@WebAppConfiguration
@Sql({ "/esquema.sql" }) // opcional
public class ProjetoControllerIT {

	private MockMvc mockMvc;

	public static final String REQUEST_MAPPING = "/projeto/";

	@Autowired
	private ProjetoService projetoService;

	@Autowired
	private HistoricoProjetoRepository historicoProjetoRepository;

	private ObjectMapper mapper = new ObjectMapper();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(new ProjetoController(projetoService)).build();
	}

	@Test
	public void salvar() throws Exception {
		mockMvc
			.perform(put("/projeto/salvar")
				.param("nome", ConstantesMock.NOME_TESTE)
				.param("descricao", ConstantesMock.DESCRICAO_TESTE)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").exists());
		assertTrue(encontrarRegistroHistorico(AcaoEnum.SALVAR, ConstantesMock.DESCRICAO_TESTE));
	}

	@Test
	public void alterar() throws Exception {
		String json = mockMvc
				.perform(post("/projeto/salvar")
					.param("nome", ConstantesMock.NOME_TESTE_ALTERAR)
					.param("descricao", ConstantesMock.DESCRICAO_TESTE)
					.contentType(MediaType.APPLICATION_JSON))
					.andReturn().getResponse().getContentAsString();

		Projeto projeto = mapper.readValue(json, Projeto.class);

		mockMvc
			.perform(get("/projeto/salvar")
				.param("numeroProjeto", projeto.getId().toString())
				.param("nome", ConstantesMock.NOME_TESTE_ALTERAR)
				.param("descricao", ConstantesMock.DESCRICAO_TESTE_ALTERACAO)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").exists());
		assertTrue(encontrarRegistroHistorico(AcaoEnum.ALTERAR, ConstantesMock.DESCRICAO_TESTE_ALTERACAO));
	}

	@Test
	public void excluir() throws Exception {
		String json = mockMvc
						.perform(get("/projeto/salvar")
							.param("nome", ConstantesMock.NOME_TESTE_EXCLUIR)
							.param("descricao", ConstantesMock.DESCRICAO_TESTE)
							.contentType(MediaType.APPLICATION_JSON))
							.andReturn().getResponse().getContentAsString();
		
		Projeto projeto = mapper.readValue(json, Projeto.class);

		String retorno = mockMvc
							.perform(get("/projeto/excluir")
								.param("numeroProjeto", projeto.getId().toString())
								.contentType(MediaType.APPLICATION_JSON))
								.andExpect(status().isOk())
								.andReturn().getResponse().getContentAsString();

		assertEquals("Excluído com sucesso!", retorno);
		assertTrue(encontrarRegistroHistorico(AcaoEnum.EXCLUIR, ConstantesMock.DESCRICAO_TESTE));
	}
	
	@Test
	public void obter() throws Exception {
		String json = mockMvc
				.perform(get("/projeto/salvar")
					.param("nome", ConstantesMock.NOME_TESTE_OBTER)
					.param("descricao", ConstantesMock.DESCRICAO_TESTE)
					.contentType(MediaType.APPLICATION_JSON))
					.andReturn().getResponse().getContentAsString();

		Projeto projeto = mapper.readValue(json, Projeto.class);
		mockMvc
			.perform(get("/projeto/obter/" + projeto.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").exists())
	          	.andExpect(jsonPath("$.nome").isNotEmpty())
	          	.andExpect(jsonPath("$.descricao").value(ConstantesMock.DESCRICAO_TESTE));
	}

	@Test
	@Sql({ "/dados.sql" })
	public void consultar() throws Exception {
			mockMvc
				.perform(get("/projeto/consultar")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$").isNotEmpty());
	}
	
	
	/* Métodos POST e DELETE*/
	@Test
	public void excluir2() throws Exception {
		String json = mockMvc
						.perform(post("/projeto/salvar")
							.param("nome", ConstantesMock.NOME_TESTE_EXCLUIR)
							.param("descricao", ConstantesMock.DESCRICAO_TESTE)
							.contentType(MediaType.APPLICATION_JSON))
							.andReturn().getResponse().getContentAsString();
		
		Projeto projeto = mapper.readValue(json, Projeto.class);

		mockMvc
			.perform(delete("/projeto/excluir2")
				.param("numeroProjeto", projeto.getId().toString())
				.contentType(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk());
		
		assertTrue(encontrarRegistroHistorico(AcaoEnum.EXCLUIR, ConstantesMock.DESCRICAO_TESTE));
	}
	
	@Test
	public void salvar2() throws Exception {
		String jsonInString = mapper.writeValueAsString(new Projeto(ConstantesMock.NOME_TESTE_2, ConstantesMock.DESCRICAO_TESTE));
   	 
		 mockMvc.perform(post("/projeto/salvar2")
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(jsonInString))
    	 		 .andExpect(status().isOk());
		 
		assertTrue(encontrarRegistroHistorico(AcaoEnum.SALVAR, ConstantesMock.DESCRICAO_TESTE));
	}

	
	private Boolean encontrarRegistroHistorico(AcaoEnum acao, String descricao) {
		List<HistoricoProjeto> lista = (List<HistoricoProjeto>) historicoProjetoRepository.findAll();
		return lista.stream().filter(h -> acao.name().equals(h.getAcao()) && h.getDescricao().contains(descricao)).collect(Collectors.toList()).size() > 0;
	}

}
