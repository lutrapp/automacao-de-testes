package org.curso.automacao.modulos.erp.webapp;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebApplicationController {
	
    @GetMapping(value = "/authorize")
    public String showLoginForm(Model model) {	
    	return "/";    
    }

}
