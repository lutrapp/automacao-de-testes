package org.curso.automacao.modulos.erp.userservice.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(JwtRequestFilter.class);

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.issuer}")
	private String issuer;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String requestHeader = request.getHeader("Authorization");
		LOGGER.debug("RequestHeaderAuth: {}", requestHeader);

		String token = getJwtFromRequest(requestHeader);

		try {
			if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				JwtAuthInfo authInfo = parseJwtClaims(token);
				boolean isTokenValid = validateToken(authInfo);
				String user = authInfo.getUser();
				if (isTokenValid) {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							user, null, authInfo.getGrantedAuthorities());
					usernamePasswordAuthenticationToken
							.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					LOGGER.info("Authenticated User {} , setting security context", user);
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				} else {
					LOGGER.warn("Token: {} not valid for user: {}", token, user);
				}
				
			}
		}catch (SignatureException e) {
			 LOGGER.info("Error on token validation. Token is corrupted.", e);
			 response.setStatus(HttpStatus.UNAUTHORIZED.value());
			 response.getWriter().write("Token is corrupted.");
			 
		}catch (Exception e) {
			 LOGGER.info("Error on token validation.", e);
	         response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		
		filterChain.doFilter(request, response);
	}

	private boolean validateToken(JwtAuthInfo authInfo) {
		return !authInfo.getExpiryDate().before(new Date());
	}

	private String getJwtFromRequest(String requestHeader) {
		if (requestHeader != null && requestHeader.startsWith("Bearer")) {
			return requestHeader.replace("Bearer ", "").trim();
		} else {
			LOGGER.warn("Couldn't find the bearer so will ignore the header: {}", requestHeader);
			return null;
		}
	}

	private JwtAuthInfo parseJwtClaims(String jwtToken) {

		Jws<Claims> claims = Jwts.parser().requireIssuer(issuer).setSigningKey(secret).parseClaimsJws(jwtToken);

		String user = claims.getBody().getSubject();
		List<LinkedHashMap<String, String>> roles = claims.getBody().get("scope", ArrayList.class);

		List<GrantedAuthority> grantedAuthorities = roles == null || roles.isEmpty() ? Collections.emptyList()
				: roles.stream().flatMap(x -> x.values().stream()).map(SimpleGrantedAuthority::new)
						.collect(Collectors.toList());

		LOGGER.debug("={}", grantedAuthorities);

		return new JwtAuthInfo(user, grantedAuthorities, claims.getBody().getExpiration());
	}

}
