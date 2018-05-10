package spring.h2.poc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.h2.poc.model.HistoricoProjeto;
import spring.h2.poc.model.Projeto;
import spring.h2.poc.model.HistoricoProjeto.AcaoEnum;
import spring.h2.poc.repository.HistoricoProjetoRepository;

@Service
public class HistoricoProjetoService {

	@Autowired
	private HistoricoProjetoRepository historicoProjetoRepository;

	public void salvar(Projeto projeto, AcaoEnum acao) {
		HistoricoProjeto historicoProjeto = new HistoricoProjeto(projeto, acao);
		historicoProjetoRepository.save(historicoProjeto);
	}
	
	/*
	 * Necessário do set para os testes unitários e de integração.
	 * 
	 */
	public void setHistoricoProjetoRepository(HistoricoProjetoRepository historicoProjetoRepository) {
		this.historicoProjetoRepository = historicoProjetoRepository;
	}

}