package org.curso.automacao.modulos.erp.customerservice.helpers;

import java.util.Calendar;
import java.util.Date;
import org.curso.automacao.modulos.erp.customerservice.security.JwtAuthInfo;
import org.curso.automacao.modulos.erp.customerservice.security.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthInfoHelper {
	
	@Autowired
	private JwtRequestFilter filter;
	
	public JwtAuthInfo getAuthInfo(String jwtToken) {
		
		JwtAuthInfo authInfo = filter.parseJwtClaims(jwtToken);
		
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		calendar.add(Calendar.YEAR, 5);
		
		authInfo.setExpiryDate(now);

		return authInfo;
	}
}
