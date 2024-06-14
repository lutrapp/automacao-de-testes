#language: pt

@env.dev @users
Funcionalidade: Cadastro de Usuários
	Eu como gestor da aplicação quero poder gerenciar os usuários através de uma API

	Contexto: Usuário administrador autenticado com sucesso
		Dado que eu tenha um "admin@automacao.org.br" e "password01" válidos para me autenticar na aplicação via API
  	Quando eu preparar a requisição da API
  	E realizar a chamada dessa requisição na API
  	Então a autenticação deverá ser realizada com sucesso. 
   
  @integration-tests
	Cenário: Listar todos os usuários da aplicação via API
		Dado que eu tenha um token válido para a minha chamada de API
		Quando eu preparar a requisição de obtenção da lista de usuários na API
		E realizar a chamada dessa requisição de obtenção da lista 
		Então visualizarei a lista de usuários preenchida