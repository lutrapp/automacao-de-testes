package org.curso.automacao.testes.sistemas.actions;

import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.StringUtils;
import org.curso.automacao.testes.BaseSteps;
import org.curso.automacao.testes.sistemas.pages.CustomerPage;
import org.curso.automacao.testes.sistemas.pages.MasterPageFactory;

import com.github.javafaker.Faker;

public class CustomerActions {
	
	public static String customerName = StringUtils.EMPTY;
	public static String customerId = StringUtils.EMPTY;
	
	public static CustomerPage customerPage() {
		return MasterPageFactory.getPage(CustomerPage.class);
	}
	
	public static void validateCustomerPage() {
		customerPage().validatePage();
		
		// Colher a evidência da tela
		BaseSteps.takeScreenshot();
	}
	
	public static void fillForm() {
		
		Faker faker = Faker.instance();
		
		customerName = faker.name().fullName();
		
		customerPage().fillForm(customerName,
								faker.internet().emailAddress(),
								faker.company().name(),
								String.valueOf(faker.number().numberBetween(5000, 25000)),
								faker.address().city(),
								faker.address().state(),
								faker.address().fullAddress(),
								faker.country().name(),
								faker.address().zipCode(),
								faker.phoneNumber().cellPhone(),
								true);
		
		// Colher a evidência da tela
		BaseSteps.takeScreenshot();
	}
	
	public static void fillFormWithUpdatedName() {
		
		Faker faker = Faker.instance();
		
		customerName = faker.name().fullName();
		
		customerPage().fillForm(customerName,
								StringUtils.EMPTY,
								StringUtils.EMPTY,
								StringUtils.EMPTY,
								StringUtils.EMPTY,
								StringUtils.EMPTY,
								StringUtils.EMPTY,
								StringUtils.EMPTY,
								StringUtils.EMPTY,
								StringUtils.EMPTY,
								true);
		
		// Colher a evidência da tela
		BaseSteps.takeScreenshot();
	}
	
	public static void clickOnSubmit() {
		
		customerPage().clickOnSubmit();
		
		// Colher a evidência da tela
		BaseSteps.takeScreenshot();
		
		customerId = customerPage().getTxtCustomerId().getText();
	}
	
	public static void clickOnCustomers() {
		
		customerPage().clickOnCustomers();
		

		// Colher a evidência da tela
		BaseSteps.takeScreenshot();
	}
	
	
	public static void validateCustomerSavedSuccessfully() {
		
 		assertTrue("Validate if customer saved sucessfully panel is displayed.", customerPage().getLblCustomerSavedSuccesfully().isDisplayed());
	}

}
