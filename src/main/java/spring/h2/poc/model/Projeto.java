package spring.h2.poc.model;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import spring.h2.poc.listeners.ProjetoListeners;

@Entity
@EntityListeners(ProjetoListeners.class)
public class Projeto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String nome;
	private String descricao;

	/**
	 * Construtor padrão
	 */
	public Projeto() {
		super();
	}

	/**
	 * Construtor sobrescrito
	 * 
	 * @param id
	 * @param nome
	 * @param descricao
	 */
	public Projeto(Integer id, String nome, String descricao) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
	}

	/**
	 * Construtor sobrescrito
	 * 
	 * @param nome
	 * @param descricao
	 */
	public Projeto(String nome, String descricao) {
		super();
		this.nome = nome;
		this.descricao = descricao;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}
	
	public Boolean verificarNome() {
		return nome != null && !nome.isEmpty() && nome.trim().length() > 0;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Boolean verificarDescricao() {
		return descricao != null && !descricao.isEmpty() && descricao.trim().length() > 0;
	}

	@Override
	public String toString() {
		return "[" + id + " - " + nome + " - " + descricao + "]";
	}
}
