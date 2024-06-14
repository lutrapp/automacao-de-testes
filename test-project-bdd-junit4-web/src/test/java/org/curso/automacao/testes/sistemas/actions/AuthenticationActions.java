package org.curso.automacao.testes.sistemas.actions;

import org.curso.automacao.testes.BaseSteps;
import org.curso.automacao.testes.sistemas.pages.LoginPage;
import org.curso.automacao.testes.sistemas.pages.MasterPageFactory;

public class AuthenticationActions {
	
	private static LoginPage loginPage() {
		return MasterPageFactory.getPage(LoginPage.class);
	}
	
	public static void validateLoginPage() {
		loginPage().validatePage();
		
		// Colher a evidência da tela
		BaseSteps.takeScreenshot();	
	}
	
	public static void insertUserAndPassword(String userName, String password) {
		
		loginPage().getTxtUserName().sendKeys(userName);
		loginPage().getTxtPassword().sendKeys(password);
		
		// Colher a evidência da tela
		BaseSteps.takeScreenshot();
	}
	
	public static void loginConfirm() {
		
		loginPage().getBtnLogin().click();
	}
	
	public static void login(String userName, String password) {
		
		validateLoginPage();
		insertUserAndPassword(userName, password);
		loginConfirm();
		
	}

}
