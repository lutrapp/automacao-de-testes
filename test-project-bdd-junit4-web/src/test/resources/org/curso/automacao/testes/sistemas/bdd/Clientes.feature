#language: pt

@env.dev @customers
Funcionalidade: Cadastro de Clientes
  Como usuário cadastrado e autenticado 
  Eu quero gerenciar os clientes da aplicação
  Para que eles possam enviar pedidos

  Contexto: Autenticação de Usuários na Aplicação
    Dado que eu esteja na página de login da aplicação web
    Quando eu inserir os dados válidos de "admin@automacao.org.br" e "password01" para autenticação na aplicação web
    E confirmar o login na aplicação web
    Então serei direcionado para a página principal da aplicação web

  @system-tests
  Cenário: Inserção de um Novo Cliente
    Dado que eu esteja na página de gerenciamento dos clientes
    Quando eu inserir um novo cliente
    E preencher os dados válidos do cliente
    E submeter os dados
    Então o cliente deverá ser inserido
   
  @system-tests
  Cenário: Busca de cliente existente
    Dado que eu esteja na página de gerenciamento dos clientes
    Quando eu realizar a busca de um cliente
    Então o cliente deverá ser retornado na lista
    
	@system-tests
  Cenário: Alteração de cliente existente
    Dado que eu esteja na página de gerenciamento dos clientes
    Quando eu realizar a busca de um cliente
    E o cliente deverá ser retornado na lista
    E alterar o cliente preenchendo os de dados de alteração
    E submeter os dados
    Então o cliente deverá ser alterado com sucesso

  @system-tests
  Cenário: Exclusão de cliente existente
    Dado que eu esteja na página de gerenciamento dos clientes
    Quando eu realizar a busca de um cliente
    E o cliente deverá ser retornado na lista
    E realizar a exclusão
    Então o cliente não deverá ser mais encontrado

