package org.curso.automacao.testes.integrados.bdd.steps;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.curso.automacao.testes.BaseSteps;
import org.curso.automacao.testes.helpers.entities.User;
import org.curso.automacao.testes.integrados.bdd.Runner;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UserSteps extends BaseSteps {
	
	private RequestSpecification requestSpecification;
	private Response response;
	
	@Dado("que eu tenha um token válido para a minha chamada de API")
	public void que_eu_tenha_um_token_válido_para_a_minha_chamada_de_api() {

		logInfo("Validate if token [" + Runner.token.get() + "] is not null.");
		assertNotEquals("Validate if token is not null.", Runner.token.get(), StringUtils.EMPTY);

	}
	
	@Quando("eu preparar a requisição de obtenção da lista de usuários na API")
	public void eu_preparar_a_requisição_de_obtenção_da_lista_de_usuários_na_api() throws Exception {
		
		logInfo("Preparing the request specification.");
		requestSpecification = given()
								.baseUri(getUrl("services.users.url"))
								.header("Authorization", Runner.token.get())
								.contentType(ContentType.JSON)
								.log().all(true);
		
	}
	
	@Quando("realizar a chamada dessa requisição de obtenção da lista")
	public void realizar_a_chamada_dessa_requisição_de_obtenção_da_lista() throws Exception {
		
		logInfo("Executing the request to authentication api [" + getUrl("services.users.url") + "/api/v1/users/all]");
			
		response = requestSpecification
					.when()
						.get("/api/v1/users/all");
		
	}
	
	@Então("visualizarei a lista de usuários preenchida")
	public void visualizarei_a_lista_de_usuários_preenchida() {
		
		User[] users = response.then()
					.log()
					.all(true)
					.assertThat()
					.statusCode(HttpStatus.SC_OK).extract().as(User[].class);

		assertNotNull("Validate if users list response is not null.", users);
		assertTrue("Validate if users list size is greater than zero.", users.length > 0);
		
		int i = 0;
		for (User user : users) {
			logInfo("User [" + (++i) + "] => {" + user.toString() + "}");
		}

		logInfo("List recovery succeded. Total rows [" + users.length + "]");
	}

}
