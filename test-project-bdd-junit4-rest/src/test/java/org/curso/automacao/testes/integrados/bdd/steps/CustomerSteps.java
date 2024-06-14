package org.curso.automacao.testes.integrados.bdd.steps;

import org.curso.automacao.testes.BaseSteps;
import org.curso.automacao.testes.helpers.entities.Customer;
import org.curso.automacao.testes.integrados.actions.CustomerActions;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CustomerSteps extends BaseSteps {

	private Customer customer;
	private RequestSpecification requestSpecification;
	private Response response;

	@Dado("que eu tenha as informações válidas para cadastro de um cliente via API")
	public void que_eu_tenha_as_informações_válidas_para_cadastro_de_um_cliente_via_api() {
		
		logInfo("Prepare data for new customer.");
		customer = CustomerActions.getNewCustomer();
	}

	@Quando("eu preparar a requisição de cadastro do cliente via API")
	public void eu_preparar_a_requisição_de_cadastro_do_cliente_via_api() throws Exception {

		logInfo("Preparing the request specification.");
		requestSpecification = CustomerActions.getNewCustomerRequestSpecification(customer);
	}

	@Quando("realizar a chamada de cadastro do cliente via API")
	public void realizar_a_chamada_de_cadastro_do_cliente_via_api() throws Exception {

		logInfo("Executing the request to authentication api [" + getUrl("services.customer.url")
				+ CustomerActions.CUSTOMER_SAVE_ENDPOINT + "]");
		response = CustomerActions.requestNewCustomer(requestSpecification);
	}

	@Então("o cliente deverá ser cadastrado com sucesso via API")
	public void o_cliente_deverá_ser_cadastrado_com_sucesso_via_api() {

		logInfo("Validate if customer was succesfully created.");
		CustomerActions.validateNewCustomerResponse(customer, response);
	}

}
