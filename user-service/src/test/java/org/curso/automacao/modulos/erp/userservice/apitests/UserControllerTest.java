package org.curso.automacao.modulos.erp.userservice.apitests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.curso.automacao.modulos.erp.userservice.impl.User;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;

public class UserControllerTest extends BaseTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserControllerTest .class);
		
	@Test
	public void validateFindAllUsers() { 
		
		List<User> users = List.of(given()
								.baseUri("http://localhost:" + serverPort)
								.header("Authorization", AuthenticationControllerTest.getToken("admin@automacao.org.br", "password01", serverPort).token)
								.contentType(ContentType.JSON)
								.log().all(true)
							.when()
								.get("/api/v1/users/all")
							.then()
								.log().all(true)
								.assertThat()
									.statusCode(HttpStatus.OK.value()).extract().as(User[].class));

		assertNotNull(users, "Validate if users list response is not null.");
		assertTrue(users.size() > 0, "Validate if users list size is greater than zero.");
		
		LOGGER.info("List recovery succeded. Total rows [" + users.size() + "]");
	
	}

}
