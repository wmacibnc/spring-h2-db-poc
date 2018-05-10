package spring.h2.poc.listeners;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spring.h2.poc.BeanUtil;
import spring.h2.poc.model.Projeto;
import spring.h2.poc.model.HistoricoProjeto.AcaoEnum;
import spring.h2.poc.service.HistoricoProjetoService;

@Component
public class ProjetoListeners {

	@Autowired
	HistoricoProjetoService historicoProjetoService;
	
	@PostPersist
	public void userPostPersist(Projeto projeto) {
		BeanUtil.autowire(this, this.historicoProjetoService);
		historicoProjetoService.salvar(projeto, AcaoEnum.SALVAR);
	}
	
	@PostUpdate
    public void postUpdate(Projeto projeto) {
		BeanUtil.autowire(this, this.historicoProjetoService);
		historicoProjetoService.salvar(projeto, AcaoEnum.ALTERAR);
    }

    @PostRemove
    public void postDelete(Projeto projeto) {
    	BeanUtil.autowire(this, this.historicoProjetoService);
		historicoProjetoService.salvar(projeto, AcaoEnum.EXCLUIR);
    }
	
}
