package org.curso.automacao.modulos.erp.orderservice.helpers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.curso.automacao.modulos.erp.orderservice.impl.helpers.ConnectionHelper;
import org.curso.automacao.modulos.erp.orderservice.security.helpers.AuthenticationToken;
import org.curso.automacao.modulos.erp.orderservice.security.helpers.LoginRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@Component
public class AuthenticationHelper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationHelper.class);
	private static String url;
	
	@Autowired
	private ConnectionHelper connectionHelper;

	@Value("${server.auth.port}")
	private int serverPort;

	@PostConstruct
	public void init() {
		this.url = "http://" + connectionHelper.getHost("user-service")  + ":" + serverPort;
		LOGGER.info("Authentication URL [" + this.url + "]");
	}

	public AuthenticationToken auth(String userName, String userPass) {
		
		AuthenticationToken token = RestAssured
				.given()
					.baseUri(url)
					.contentType(ContentType.JSON)
					.body(new LoginRequest(userName, userPass))
						.log().all()
				.when()
					.post("/auth")
				.then()
					.log().all()
				.assertThat()
					.statusCode(HttpStatus.OK.value())
					.extract()
					.as(AuthenticationToken.class);

		assertTrue(token.getToken() != StringUtils.EMPTY, "Token is valid {" + token.getToken() + "}");
		assertTrue(token.token.startsWith("Bearer"), "Token starts with bearer");
		
		return token;
	
	}
}
