package org.curso.automacao.modulos.erp.webapp.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

@Component
public class WebJwtFilter extends OncePerRequestFilter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WebJwtFilter.class);
	
	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.issuer}")
	private String issuer;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
				
		String token = getTokenOnCookie(request);
		LOGGER.debug("Token from session: {}", token);

		if (token != null && !token.isEmpty() && SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
			
			try {
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
			catch (ExpiredJwtException tokenExpired) {
				setTokenOnCookie(request, "");
			}			
		}
		
		filterChain.doFilter(request, response);		
	}
	
	private void setTokenOnCookie(HttpServletRequest request, String value) {
		Cookie[] cookies = request.getCookies();
		
		if(cookies != null) {
			Optional<Cookie> tokenCookie =  (Arrays.stream(cookies)
						 						.filter(c -> c.getName().equalsIgnoreCase("token"))
						 						.findAny());
			
			if(tokenCookie.isPresent() && !tokenCookie.isEmpty()) {
				tokenCookie.get().setValue(value);
			}
		}		
	}

	private String getTokenOnCookie(HttpServletRequest request) {
		
		Cookie[] cookies = request.getCookies();
		
		if(cookies != null) {
			Optional<Cookie> tokenCookie =  (Arrays.stream(cookies)
						 						.filter(c -> c.getName().equalsIgnoreCase("token"))
						 						.findAny());
			
			if(tokenCookie.isPresent() && !tokenCookie.isEmpty()) {
				return tokenCookie.get().getValue().replace("Bearer%20", "");
			}
		}
		
		return null;
	}

	private boolean validateToken(JwtAuthInfo authInfo) {
		return !authInfo.getExpiryDate().before(new Date());
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
