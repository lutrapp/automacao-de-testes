#language: pt

@env.dev @auth
Funcionalidade: Autenticação de Usuário
  Como usuário cadastrado
  Eu quero poder me autenticar na aplicação web
	Para poder realizar transações de backoffice
	
	@system-tests
  Esquema do Cenário: Autenticação de Usuários na Aplicação
  	Dado que eu esteja na página de login da aplicação web
  	Quando eu inserir os dados válidos de "<usuário>" e "<senha>" para autenticação na aplicação web
  	E confirmar o login na aplicação web
  	Então serei direcionado para a página principal da aplicação web
  	
		Exemplos: 
      | usuário  										| senha 				|
      | admin@automacao.org.br			| password01		|