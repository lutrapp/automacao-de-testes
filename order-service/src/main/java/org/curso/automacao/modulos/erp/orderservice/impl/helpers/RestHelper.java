package org.curso.automacao.modulos.erp.orderservice.impl.helpers;

import org.apache.commons.lang3.StringUtils;
import org.curso.automacao.modulos.erp.orderservice.security.helpers.AuthenticationToken;
import org.curso.automacao.modulos.erp.orderservice.security.helpers.LoginRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestHelper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RestHelper.class);
	
	@Autowired
	private Environment env;
	
	@Autowired
	private ConnectionHelper connectionHelper;
	
	@Value("${service.user.name}")
	private String authUserName;

	@Value("${service.user.pass}")
	private String authUserPass;

	private String authToken;

	public boolean isAuthenticated() {
		return authToken != null && !authToken.isEmpty();
	}

	private void authenticate() {
		if (!isAuthenticated()) {

			String url = StringUtils.EMPTY;
			
			if(connectionHelper.isRunningInsideDocker()) {
				url = env.getProperty("service.users.url");
			} else {
				url = env.getProperty("localhost.service.users.url"); 
			}
			
			LOGGER.info("User service URL: [" + url + "]");

			LoginRequest loginRequest = new LoginRequest(authUserName, authUserPass);
			RestTemplate client = new RestTemplate();

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<LoginRequest> request = new HttpEntity<LoginRequest>(loginRequest, headers);

			AuthenticationToken token = client
					.exchange(url + "/auth", HttpMethod.POST, request, AuthenticationToken.class).getBody();

			if (token != null)
				authToken = token.token;
		}
	}

	public <T, X> X postRestObject(String url, T body, boolean withAutorization, Class<T> clazzIn, Class<X> clazzOut) {
		return postRestObject(url, body, true, withAutorization, clazzIn, clazzOut);
	}

	public <T, X> X getRestObject(String url, T body, boolean withAutorization, Class<T> clazzIn, Class<X> clazzOut) {
		return getRestObject(url, body, true, withAutorization, clazzIn, clazzOut);
	}

	private <T, X> X postRestObject(String url, T body, boolean authenticate, boolean withAutorization,
			Class<T> clazzIn, Class<X> clazzOut) {

		RestTemplate client = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		if (authenticate)
			authenticate();

		if (withAutorization)
			headers.add("Authorization", authToken);

		HttpEntity request;
		
		if(body == null)
			request = new HttpEntity<T>(headers);
		else
			request = new HttpEntity<T>(body, headers);

		X result = client.exchange(url, HttpMethod.POST, request, clazzOut).getBody();
		
		LOGGER.info(result.toString());
		
		return result;
	}

	private <T, X> X getRestObject(String url, T body, boolean authenticate, boolean withAutorization, Class<T> clazzIn,
			Class<X> clazzOut) {

		RestTemplate client = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		if (authenticate)
			authenticate();

		if (withAutorization)
			headers.add("Authorization", authToken);

		HttpEntity request;
		
		if(body == null)
			request = new HttpEntity(headers);
		else
			request = new HttpEntity<T>(body, headers);

		X result = client.exchange(url, HttpMethod.GET, request, clazzOut).getBody();
		
		LOGGER.info(result.toString());
		
		return result;
	}

}
