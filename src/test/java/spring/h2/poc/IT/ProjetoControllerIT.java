package spring.h2.poc.IT;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import spring.h2.poc.AppConfigIT;
import spring.h2.poc.ConstantesMock;
import spring.h2.poc.controller.ProjetoController;
import spring.h2.poc.model.HistoricoProjeto;
import spring.h2.poc.model.HistoricoProjeto.AcaoEnum;
import spring.h2.poc.model.Projeto;
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
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProjetoControllerIT.class);

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(new ProjetoController(projetoService)).build();
	}
	
	@Test
	public void salvar() throws Exception {
   	 
		 mockMvc.perform(post("/projeto/salvar")
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(converterParaJson(new Projeto(ConstantesMock.NOME_TESTE, ConstantesMock.DESCRICAO_TESTE))))
    	 		 .andExpect(status().isOk());
		 
		assertTrue(encontrarRegistroHistorico(AcaoEnum.SALVAR, ConstantesMock.DESCRICAO_TESTE));
	}
	

	@Test
	public void alterar() throws Exception {
		Projeto projeto = (Projeto) mockMvc
				.perform(post("/projeto/salvar")
					.contentType(MediaType.APPLICATION_JSON)
					.content(converterParaJson(new Projeto(ConstantesMock.NOME_TESTE_ALTERAR, ConstantesMock.DESCRICAO_TESTE)))
					.accept(MediaType.APPLICATION_JSON))
					.andReturn().getModelAndView().getModel().get("projeto");
		
		mockMvc
			.perform(put("/projeto/salvar")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(new Projeto(projeto.getId(), ConstantesMock.NOME_TESTE_ALTERAR, ConstantesMock.DESCRICAO_TESTE_ALTERACAO)))
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		
		assertTrue(encontrarRegistroHistorico(AcaoEnum.ALTERAR, ConstantesMock.DESCRICAO_TESTE_ALTERACAO));
	}

	@Test
	public void excluir() throws Exception {
		Projeto projeto = (Projeto) mockMvc
				.perform(post("/projeto/salvar")
					.contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(new Projeto(ConstantesMock.NOME_TESTE_EXCLUIR, ConstantesMock.DESCRICAO_TESTE)))
					.accept(MediaType.APPLICATION_JSON))
					.andReturn().getModelAndView().getModel().get("projeto");
		
		mockMvc
			.perform(delete("/projeto/excluir")
				.param("numeroProjeto", projeto.getId().toString())
				.contentType(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk());
		
		assertTrue(encontrarRegistroHistorico(AcaoEnum.EXCLUIR, ConstantesMock.DESCRICAO_TESTE));
	}
	
	@Test
	public void obter() throws Exception {
		Projeto projeto = (Projeto) mockMvc
				.perform(post("/projeto/salvar")
					.contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(new Projeto(ConstantesMock.NOME_TESTE_OBTER, ConstantesMock.DESCRICAO_TESTE)))
					.accept(MediaType.APPLICATION_JSON))
					.andReturn().getModelAndView().getModel().get("projeto");
		
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
	
	// Métodos auxiliares
	
	 /**
	  * Método responsável por encontrar verificar se existe projeto com a ação e a descrição informada
	  * 
	  * @param acao
	  * @param descricao
	  * @return
	  */
	private Boolean encontrarRegistroHistorico(AcaoEnum acao, String descricao) {
		List<HistoricoProjeto> lista = (List<HistoricoProjeto>) historicoProjetoRepository.findAll();
		return lista.stream().filter(h -> acao.name().equals(h.getAcao()) && h.getDescricao().contains(descricao)).collect(Collectors.toList()).size() > 0;
	}
	
	/**
	 * Método reponsável por converter um objeto em json
	 * 
	 * @param projeto
	 * @return
	 */
	private String converterParaJson(Projeto projeto) {
		try {
			return mapper.writeValueAsString(projeto);
		} catch (JsonProcessingException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}

}
