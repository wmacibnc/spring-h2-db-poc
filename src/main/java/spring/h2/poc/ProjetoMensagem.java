package spring.h2.poc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import spring.h2.poc.exception.PocException;
import spring.h2.poc.model.Projeto;
import spring.h2.poc.service.ProjetoService;

@Component
public class ProjetoMensagem {

	@Autowired
	private ProjetoService projetoService;
	
    @JmsListener(destination = "salvarProjeto", containerFactory = "myFactory")
    public void mensagemSalvarProjeto(Projeto projeto) throws PocException {
        System.out.println("Mensagem Recebida: " + projeto);
        projetoService.salvar(projeto);
   }
    
    
}