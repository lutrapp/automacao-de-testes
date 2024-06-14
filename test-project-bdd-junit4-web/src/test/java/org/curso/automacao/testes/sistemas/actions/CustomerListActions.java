package org.curso.automacao.testes.sistemas.actions;

import static org.junit.Assert.assertTrue;

import org.curso.automacao.testes.BaseSteps;
import org.curso.automacao.testes.sistemas.pages.CustomerListPage;
import org.curso.automacao.testes.sistemas.pages.MasterPageFactory;

public class CustomerListActions {
	
	public static CustomerListPage customerListPage() {
		return MasterPageFactory.getPage(CustomerListPage.class);
	}
	
	public static void clickOnCreateNewCustomer() {
		customerListPage().getBtnCreateCustomer().click();
	}
	
	public static void validateCustomerListPage() {
		customerListPage().validatePage();
		
		// Colher a evidência da tela
		BaseSteps.takeScreenshot();
	}
	
	public static void search(String value) {
		
		customerListPage().getTxtSearch().sendKeys(value);
		
		// Colher a evidência da tela
		BaseSteps.takeScreenshot();
	}
	
	public static void checkValueInResultList(String value) {

		assertTrue("Validate if customer was found in the result list.", customerListPage().findValueInTable(value).isDisplayed());
	}
	
	public static void clickOnUpdateButton() {
		customerListPage().findFirstButtonUpdateInTable().click();
	}
	
	public static void clickOnDeleteButton() {
		customerListPage().findFirstButtonDeleteInTable().click();
	}
	
	public static void acceptAlertDeleteConfirmation() {
		customerListPage().acceptAlertDeleteConfirmation();
		
		// Colher a evidência da tela
		BaseSteps.takeScreenshot();
	}
	
	public static void validateNoMatchingRecordsFound() {
		
		customerListPage().findNoMatchingRecordsFound();
		
		// Colher a evidência da tela
		BaseSteps.takeScreenshot();
		
	}

}
