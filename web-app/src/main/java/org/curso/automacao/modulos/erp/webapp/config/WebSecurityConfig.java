package org.curso.automacao.modulos.erp.webapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
	  String[] authWhitelist = {
			  	"/assets/**",
			  	"/forms/**",
			  	"/utils/**",
                "/actuator/**",
                "/v1/api-docs",
                "/swagger-resources",
                "/swagger-resources/**",
                "/swagger-ui.html",
                "/webjars/",
                "/webjars/**",
                "/h2-console/",
                "/h2-console/**"
        };
		  
		http.csrf().disable()
			.authorizeRequests()						
				.antMatchers(authWhitelist).permitAll()
			.anyRequest()
				.authenticated()
			.and()
				.formLogin()
				.loginPage("/login")
				//.loginProcessingUrl("/authorize")
				.defaultSuccessUrl("/index")
				.permitAll()
			.and()
				.logout()
				//.logoutSuccessUrl("/login")
				.deleteCookies("token")
				.clearAuthentication(true)
				.permitAll();
			//.and()
				//.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
	}

}
