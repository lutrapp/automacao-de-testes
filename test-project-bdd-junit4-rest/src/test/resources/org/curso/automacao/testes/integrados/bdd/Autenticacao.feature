#language: pt

@env.dev @auth
Funcionalidade: Autenticação de Usuário
  Eu como usuaŕio quero poder me autenticar na aplicação utilizando a API de autenticação
  
  @integration-tests
  Esquema do Cenário: Autenticação de Usuários na Aplicação
  	Dado que eu tenha um "<usuário>" e "<senha>" válidos para me autenticar na aplicação via API
  	Quando eu preparar a requisição da API
  	E realizar a chamada dessa requisição na API
  	Então a autenticação deverá ser realizada com sucesso. 
   
		Exemplos: 
      | usuário  										| senha 				|
      | admin@automacao.org.br			| password01		|