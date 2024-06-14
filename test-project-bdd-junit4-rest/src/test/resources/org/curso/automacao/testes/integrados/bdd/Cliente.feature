#language: pt

@env.dev @customers
Funcionalidade: Cadastro de Clientes
	Eu como gestor da aplicação quero poder gerenciar os clientes através de uma API

	Contexto: Usuário administrador autenticado com sucesso
		Dado que eu tenha um "admin@automacao.org.br" e "password01" válidos para me autenticar na aplicação via API
  	Quando eu preparar a requisição da API
  	E realizar a chamada dessa requisição na API
  	Então a autenticação deverá ser realizada com sucesso. 
   
  @integration-tests
  Cenário: Cadastro de clientes da aplicação via API
  	Dado que eu tenha as informações válidas para cadastro de um cliente via API
  	Quando eu preparar a requisição de cadastro do cliente via API
  	E realizar a chamada de cadastro do cliente via API
  	Então o cliente deverá ser cadastrado com sucesso via API
 