package spring.h2.poc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import spring.h2.poc.exception.PocException;
import spring.h2.poc.model.Projeto;
import spring.h2.poc.service.ProjetoService;

@Controller
@RequestMapping(value = "/projeto")

public class ProjetoController {

	@Autowired
	private ProjetoService projetoService;

	/**
	 * Método responsável por obter um projeto. Retorna um projeto.
	 * 
	 * @param numeroProjeto
	 * @return Projeto
	 * @throws PocException 
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/obter/{numeroProjeto}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Projeto obter(@PathVariable(value = "numeroProjeto") Integer numeroProjeto) throws PocException {
		return projetoService.obter(numeroProjeto);
	}
	
	/**
	 * Método responsável por consultar os projetos por nome e/ou descrição
	 * 
	 * @param nome
	 * @param descricao
	 * @return
	 * @throws PocException
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/consultar", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Projeto> consultar(@RequestParam(value = "nome", required = false) String nome, @RequestParam(value = "descricao", required = false) String descricao) throws PocException {
		return projetoService.consultar(nome, descricao);
	}
	
	/**
	 * Método responsável por salvar um projeto. Deve ser utilizado na inclusão e na
	 * alteração.
	 * 
	 * @param projeto
	 * @return
	 * @throws PocException
	 */
	@RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT}, value = "/salvar", produces = MediaType.APPLICATION_JSON_VALUE)
	public Projeto salvar(@RequestBody Projeto projeto) throws PocException {
		return projetoService.salvar(projeto);
	}
	
	/**
	 * Método responsável por excluir um projeto, retorna uma mensagem de sucesso ou
	 * erro.
	 * 
	 * @param numeroProjeto
	 * @return
	 * @throws PocException
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/excluir", produces = MediaType.APPLICATION_JSON_VALUE)
	public String excluir(@RequestParam(value = "numeroProjeto") Integer numeroProjeto) throws PocException {
		return projetoService.excluir(numeroProjeto);
	}

	@Autowired
	public ProjetoController(ProjetoService projetoService) {
		super();
		this.projetoService = projetoService;
	}

}