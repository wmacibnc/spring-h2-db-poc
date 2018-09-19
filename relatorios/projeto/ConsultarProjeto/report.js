$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("src/test/java/spring/h2/poc/feature/consultaProjeto.feature");
formatter.feature({
  "comments": [
    {
      "line": 1,
      "value": "#language: pt"
    }
  ],
  "line": 4,
  "name": "Consultar Projeto",
  "description": "\nCOMO Usuário DESEJO consultar todos os projetos cadastrados.",
  "id": "consultar-projeto",
  "keyword": "Funcionalidade",
  "tags": [
    {
      "line": 2,
      "name": "@projeto"
    }
  ]
});
formatter.scenario({
  "line": 8,
  "name": "Consultar Projeto",
  "description": "",
  "id": "consultar-projeto;consultar-projeto",
  "type": "scenario",
  "keyword": "Cenario"
});
formatter.step({
  "line": 9,
  "name": "que eu acesse a aplicação",
  "keyword": "Dado "
});
formatter.step({
  "line": 10,
  "name": "informar o nome do projeto \"Stefanini\"",
  "keyword": "E "
});
formatter.step({
  "line": 11,
  "name": "eu clicar em Pesquisar",
  "keyword": "Quando "
});
formatter.step({
  "line": 12,
  "name": "a aplicação deverá apresentar a lista de projetos em ordem crescente",
  "keyword": "Entao "
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.embedding("image/png", "tela01.png");
});