package spring.h2.poc.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.h2.poc.exception.PocException;
import spring.h2.poc.model.Projeto;
import spring.h2.poc.repository.ProjetoRepository;

@Service
public class ProjetoService {

	@Autowired
	private ProjetoRepository projetoRepository;

	public Projeto salvar(Integer numeroProjeto, String nome, String descricao) throws PocException {
		return salvar(new Projeto(numeroProjeto, nome, descricao));
	}
	
	public Projeto salvar(Projeto projeto) throws PocException {
		validar(projeto);
		return projetoRepository.save(projeto);
	}
	
	public Projeto obter(Integer numeroProjeto) throws PocException {
		validarNumeroProjetoExiste(numeroProjeto);
		return projetoRepository.findOne(numeroProjeto);
	}

	public String excluir(Integer numeroProjeto) throws PocException {
		validarNumeroProjetoExiste(numeroProjeto);
		projetoRepository.delete(numeroProjeto);
		return "Excluído com sucesso!";
	}
	
	public List<Projeto> consultar(String nome, String descricao) throws PocException {
		List<Projeto> lista = (List<Projeto>) projetoRepository.findAll();
		if(nome !=null) {
			lista = lista.stream().filter(p-> p.getNome().contains(nome) ).collect(Collectors.toList());			
		}
		if(descricao !=null) {
			lista = lista.stream().filter(p-> p.getDescricao().contains(descricao)).collect(Collectors.toList());			
		}
		return lista;
	}

	private void validar(Projeto projeto) throws PocException {
		validarNomeNaoNulo(projeto);
		validarTamanhoNome(projeto);
		validarDescricaoNaoNulo(projeto);
		validarTamanhoDescricao(projeto);
		validarNomeProjetoJaExiste(projeto);
	}

	private void validarNomeProjetoJaExiste(Projeto projeto) throws PocException {
		if (projeto.getId() == null && projetoRepository.obterProjetoPorNome(projeto.getNome()) != null) {
			throw new PocException("Nome do projeto já existe!");
		}
	}

	private void validarTamanhoDescricao(Projeto projeto) throws PocException {
		if (projeto.getDescricao().length() > 200) {
			throw new PocException("Descrição do projeto deve ter até 200 caracteres");
		}
	}

	private void validarDescricaoNaoNulo(Projeto projeto) throws PocException {
		if (!projeto.verificarDescricao()) {
			throw new PocException("Informe a descrição");
		}
	}

	private void validarTamanhoNome(Projeto projeto) throws PocException {
		if (projeto.getNome().length() > 40) {
			throw new PocException("Nome do projeto deve ter até 40 caracteres");
		}
	}

	private void validarNomeNaoNulo(Projeto projeto) throws PocException {
		if (!projeto.verificarNome()) {
			throw new PocException("Informe o nome do projeto");
		}
	}

	private void validarNumeroProjetoExiste(Integer numeroProjeto) throws PocException {
		if (numeroProjeto == null || projetoRepository.findOne(numeroProjeto) == null) {
			throw new PocException("Não foi encontrado projeto com esse número.");
		}
	}

	/*
	 * Necessário do set para os testes unitários e de integração.
	 * 
	 */
	public void setProjetoRepository(ProjetoRepository projetoRepository) {
		this.projetoRepository = projetoRepository;
	}

}