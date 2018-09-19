#language: pt
@projeto

Funcionalidade: Consultar Projeto
  
  COMO Usuário DESEJO consultar todos os projetos cadastrados.
  
	Cenario: Consultar Projeto  
		Dado eu um Usuário 
		Quando consultar todos os projetos 
		Entao o Sistema deverá apresentar os campos de busca
		Entao os projetos sejam ordenados por ordem alfabética crescente por nome 
		Entao eu os projetos acessados apresentem as opções de #AlterarProjeto, #ExcluirProjeto e #DetalharProjeto.