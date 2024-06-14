package org.curso.automacao.modulos.erp.userservice.apitests;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.commons.lang3.StringUtils;
import org.curso.automacao.modulos.erp.userservice.impl.entities.support.AuthenticationToken;
import org.curso.automacao.modulos.erp.userservice.impl.entities.support.LoginRequest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;

public class AuthenticationControllerTest extends BaseTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationControllerTest .class);
	
	public static AuthenticationToken getToken(String username, String password, int serverPort) {
		
		LoginRequest loginRequest = new LoginRequest(username, password);
		
		AuthenticationToken token =  given()
										.baseUri("http://dev-user-service:" + serverPort)
										//.baseUri("http://localhost:" + serverPort)
										.contentType(ContentType.JSON)
										.body(loginRequest)
										.log().all(true)
									.when()
										.post("/auth")
									.then()
										.log().all(true)
										.assertThat()
											.statusCode(HttpStatus.OK.value()).extract().as(AuthenticationToken.class);
		
		return token;
		
	}
		
	@Test
	public void validateAuthentication() { 
		
		AuthenticationToken token = getToken("admin@automacao.org.br", "password01", serverPort);
				
		assertNotNull(token, "Validate if token response is not null.");
		assertNotNull(token.token, "Validate if token field is not null.");
		assertNotEquals(StringUtils.EMPTY, token.token, "Validate if token field is not empty.");
		
		LOGGER.info("Authentication succeded. Token generated [" + token.token + "]");
	
	}

}
