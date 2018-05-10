package spring.h2.poc.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class HistoricoProjeto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String descricao;
	
	private String acao;

	/**
	 * Construtor padrão
	 */
	public HistoricoProjeto() {
		super();
	}

	/**
	 * Construtor sobrescrito
	 * 
	 * @param id
	 * @param descrição
	 * @param acao
	 */
	public HistoricoProjeto(Projeto projeto, AcaoEnum acao) {
		super();
		this.descricao = projeto.toString();
		this.setAcao(acao.name());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public enum AcaoEnum {
		SALVAR, ALTERAR, EXCLUIR;
	}
}