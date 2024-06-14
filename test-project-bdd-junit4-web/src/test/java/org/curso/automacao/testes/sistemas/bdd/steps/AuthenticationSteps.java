package org.curso.automacao.testes.sistemas.bdd.steps;

import org.curso.automacao.testes.BaseSteps;
import org.curso.automacao.testes.sistemas.actions.AuthenticationActions;
import org.curso.automacao.testes.sistemas.actions.CommonActions;
import org.curso.automacao.testes.sistemas.actions.HomeActions;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;

public class AuthenticationSteps extends BaseSteps {
		
	@Dado("que eu esteja na página de login da aplicação web")
	public void que_eu_esteja_na_página_de_login_da_aplicacao_web() throws Exception {
		
		logInfo("Open url [" + getUrl("webapp.url") + "] for autentication.");
		
		// Open (goto) application
		CommonActions.gotoApplication();
		
		// Validando que estou na página de login
		AuthenticationActions.validateLoginPage();		
	}
	
	@Quando("eu inserir os dados válidos de {string} e {string} para autenticação na aplicação web")
	public void eu_inserir_os_dados_válidos_de_autenticação_na_aplicação_web(String userName, String password) {
		
		logInfo("Filling the fiels userName [" + userName + "] and password [" + password + "]");
				
		// Preenchimento efetivamente dos campos
		AuthenticationActions.insertUserAndPassword(userName, password);
	}
	
	@Quando("confirmar o login na aplicação web")
	public void confirmar_o_login_na_aplicação_web() {
		
		logInfo("Click on login button.");
		
		// Clicar no botão login
		AuthenticationActions.loginConfirm();
	}
	
	@Então("serei direcionado para a página principal da aplicação web")
	public void serei_direcionado_para_a_página_principal_da_aplicação_web() {
		
		logInfo("Validate the redirection to home page.");
		
		// Valida se a página inicial foi aberta
		HomeActions.validateHomePage();
	}

}
