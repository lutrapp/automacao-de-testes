package org.curso.automacao.testes.sistemas.actions;

import org.curso.automacao.testes.BaseSteps;
import org.curso.automacao.testes.sistemas.pages.HomePage;
import org.curso.automacao.testes.sistemas.pages.MasterPageFactory;

public class HomeActions {
	
	public static HomePage homePage() {
		return MasterPageFactory.getPage(HomePage.class);	
	}
	
	public static void clickOnCustomers() {
		
		// Colher a evidência da tela
		BaseSteps.takeScreenshot();
		
		homePage().showMenu();
		
		// Colher a evidência da tela
		BaseSteps.takeScreenshot();
		
		// Clica no módulo de clientes
		homePage().getLnkCustomers().click();
	}
	
	public static void validateHomePage() {
		homePage().validatePage();
		
		// Colher a evidência da tela
		BaseSteps.takeScreenshot();
	}
	
}
