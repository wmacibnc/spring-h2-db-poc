#language: pt
@projeto

Funcionalidade: Consultar Projeto
  
  COMO Usuário DESEJO consultar todos os projetos cadastrados.
  
	Cenario: Consultar Projeto  
		Dado que eu acesse a aplicação
		E informar o nome do projeto "Stefanini"
		Quando eu clicar em Pesquisar
		Entao a aplicação deverá apresentar a lista de projetos em ordem crescente