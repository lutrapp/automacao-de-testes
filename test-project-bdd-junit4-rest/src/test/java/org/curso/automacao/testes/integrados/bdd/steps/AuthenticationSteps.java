package org.curso.automacao.testes.integrados.bdd.steps;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.curso.automacao.testes.BaseSteps;
import org.curso.automacao.testes.helpers.entities.AuthenticationToken;
import org.curso.automacao.testes.helpers.entities.LoginRequest;
import org.curso.automacao.testes.integrados.bdd.Runner;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AuthenticationSteps extends BaseSteps {
	
	private LoginRequest loginRequest; 
	private RequestSpecification requestSpecification;
	private Response response;

	@Dado("que eu tenha um {string} e {string} válidos para me autenticar na aplicação via API")
	public void que_eu_tenha_um_e_válidos_para_me_autenticar_na_aplicação_via_api(String username, String password) {
		
		logInfo("Preparing the body of the request");
		loginRequest = new LoginRequest(username, password);

	}

	@Quando("eu preparar a requisição da API")
	public void eu_preparar_a_requisição_da_api() throws Exception {
		 
		logInfo("Preparing the request specification with body [" + loginRequest + "]");
		requestSpecification = given()
			.baseUri(getUrl("services.users.url"))
			.contentType(ContentType.JSON)
			.body(loginRequest)
			.log().all(true);
		 
	}

	@Quando("realizar a chamada dessa requisição na API")
	public void realizar_a_chamada_dessa_requisição_na_api() throws Exception {

		logInfo("Executing the request to authentication api [" + getUrl("services.users.url") + "/auth]");
		response = requestSpecification
					.when()
						.post("/auth");
	}

	@Então("a autenticação deverá ser realizada com sucesso.")
	public void a_autenticação_deverá_ser_realizada_com_sucesso() {
		
		logInfo("Validating the response from api request.");
		AuthenticationToken token = response.then()
											.log().all(true)
											.assertThat()
												.statusCode(HttpStatus.SC_OK).extract().as(AuthenticationToken.class);

		assertNotNull("Validate if token response is not null.", token);
		assertNotNull("Validate if token field is not null.", token.token);
		assertNotEquals("Validate if token field is not empty.", StringUtils.EMPTY, token.token);
		
		logInfo("Authentication succeded. Token generated [" + token.token + "]");
		
		Runner.token.set(token.token);
		
	}

}
