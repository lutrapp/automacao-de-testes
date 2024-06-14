package org.curso.automacao.modulos.erp.userservice.impl.entities.support;

import java.io.Serializable;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils implements Serializable {
	
	@Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.tokenValidity}")
    private long tokenValidity;

    @Value("${jwt.issuer}")
    private String issuer;
    
    public AuthenticationToken generateToken (UserDetails userDetails) {
    	Claims claims = Jwts.claims();
    	claims.put("scope", userDetails.getAuthorities());
    	
    	String value = "Bearer "+ Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setIssuer(issuer)
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidity * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    	
    	AuthenticationToken token = new AuthenticationToken(value);
    	
    	return token;
    }

}
