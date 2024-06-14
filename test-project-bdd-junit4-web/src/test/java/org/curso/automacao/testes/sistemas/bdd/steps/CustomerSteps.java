package org.curso.automacao.testes.sistemas.bdd.steps;

import org.curso.automacao.testes.BaseSteps;
import org.curso.automacao.testes.sistemas.actions.CustomerActions;
import org.curso.automacao.testes.sistemas.actions.CustomerListActions;
import org.curso.automacao.testes.sistemas.actions.HomeActions;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;

public class CustomerSteps extends BaseSteps {

	@Dado("que eu esteja na página de gerenciamento dos clientes")
	public void que_eu_esteja_na_página_de_gerenciamento_dos_clientes() {
		// Abrir o módulo de clientes
		HomeActions.clickOnCustomers();
	
		// Validar se o módulo de clientes está aberto
		CustomerListActions.validateCustomerListPage();	
	}

	@Quando("eu inserir um novo cliente")
	public void eu_inserir_um_novo_cliente() {
		
		// Abrir o formulário de criação de clientes
		CustomerListActions.clickOnCreateNewCustomer();
		
		// Validar se o formulário de criação de clientes foi aberto
		CustomerActions.validateCustomerPage();
		
	}

	@Quando("preencher os dados válidos do cliente")
	public void preencher_os_dados_válidos_do_cliente() {
		
		// Preencher o formulário
		CustomerActions.fillForm();
	}

	@Quando("submeter os dados")
	public void submeter_os_dados() {
		
		// Submeter o formulário
		CustomerActions.clickOnSubmit();
	}

	@Então("o cliente deverá ser inserido")
	public void o_cliente_deverá_ser_inserido() {
		
		// Validar se o cliente foi inserido
		CustomerActions.validateCustomerSavedSuccessfully();
	
	}

	@Quando("eu realizar a busca de um cliente")
	public void eu_realizar_a_busca_de_um_cliente() {

		// Realizar a busca pelo último cliente criado
		CustomerListActions.search(CustomerActions.customerName);
	}

	@Então("o cliente deverá ser retornado na lista")
	public void o_cliente_deverá_ser_retornado_na_lista() {
		
		// Valida se existe na lista
		CustomerListActions.checkValueInResultList(CustomerActions.customerName);
	}

	@Quando("alterar o cliente preenchendo os de dados de alteração")
	public void alterar_o_cliente_preenchendo_os_de_dados_de_alteração() {
		
		// Clicar no botão de atualizar
		CustomerListActions.clickOnUpdateButton();
		
		// Preencher somente o nome com modificação
		CustomerActions.fillFormWithUpdatedName();
		
	}

	@Então("o cliente deverá ser alterado com sucesso")
	public void o_cliente_deverá_ser_alterado_com_sucesso() {
		
		// Validar se o cliente foi inserido
		CustomerActions.validateCustomerSavedSuccessfully();
		
		// Voltar a tela de resultados
		CustomerActions.clickOnCustomers();	
			
		// Realizar a busca pelo último cliente criado
		CustomerListActions.search(CustomerActions.customerName);
		
		// Valida se existe na lista
		CustomerListActions.checkValueInResultList(CustomerActions.customerName);
	}

	@Quando("realizar a exclusão")
	public void realizar_a_exclusão() {
		
		// Deleta o registro encontrado
		CustomerListActions.clickOnDeleteButton();

		// Confirma a exclusão
		CustomerListActions.acceptAlertDeleteConfirmation();
	}

	@Então("o cliente não deverá ser mais encontrado")
	public void o_cliente_não_deverá_ser_mais_encontrado() {
		
		// Realizar a busca pelo último cliente criado
		CustomerListActions.search(CustomerActions.customerName);
		
		// Verifica se nenhum registro foi encontrado
		CustomerListActions.validateNoMatchingRecordsFound();	
	}

}
