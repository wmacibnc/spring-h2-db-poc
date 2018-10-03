package spring.h2.poc.fila;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import spring.h2.poc.model.Projeto;

@Service
public class ProjetoFilaService {

	@Autowired
	private JmsTemplate jmsTemplate;
	
	public void salvar(Projeto projeto) {
		jmsTemplate.convertAndSend("salvarProjeto", projeto);
	}




}
