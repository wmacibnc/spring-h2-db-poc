package spring.h2.poc.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import spring.h2.poc.model.Projeto;

public interface ProjetoRepository extends CrudRepository<Projeto, Integer>{

	@Query("SELECT p FROM Projeto p where p.nome = :nome ")
	Projeto obterProjetoPorNome(@Param("nome") String nome);	
}
