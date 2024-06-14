package org.curso.automacao.testes.sistemas.pages;

import java.time.Duration;
import org.curso.automacao.testes.sistemas.bdd.Runner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CustomerPage extends PageBase {
	
	@FindBy(xpath = "//*[text()='Customer Form']")
	private WebElement lblCustomerForm;

	@FindBy(xpath = "//input[@id='customerId']")
	private WebElement txtCustomerId;

	@FindBy(xpath = "//input[@id='customerName']")
	private WebElement txtCustomerName;

	@FindBy(xpath = "//input[@id='customerEmail']")
	private WebElement txtCustomerEmail;

	@FindBy(xpath = "//input[@id='customerCompany']")
	private WebElement txtCustomerCompany;

	@FindBy(xpath = "//input[@id='customerSalary']")
	private WebElement txtCustomerSalary;

	@FindBy(xpath = "//input[@id='customerCity']")
	private WebElement txtCustomerCity;

	@FindBy(xpath = "//input[@id='customerState']")
	private WebElement txtCustomerState;

	@FindBy(xpath = "//input[@id='customerAddress']")
	private WebElement txtCustomerAddress;

	@FindBy(xpath = "//select[@id='customerCountry']")
	private WebElement cmbCustomerCountry;

	@FindBy(xpath = "//input[@id='customerZipCode']")
	private WebElement txtCustomerZipCode;

	@FindBy(xpath = "//input[@id='customerPhoneNumber']")
	private WebElement txtPhoneNumber;

	@FindBy(xpath = "//input[@id='customerStatus']")
	private WebElement chkCustomerStatus;

	@FindBy(xpath = "//button[@id=\"submit\"]")
	private WebElement btnSubmit;

	@FindBy(xpath = "//div[@id='alert-success']")
	private WebElement lblCustomerSavedSuccesfully;

	@FindBy(xpath = "//a[text()='Customers']")
	private WebElement lnkCustomers;

	public void fillForm(String name, String email, String company, String salary, String city, String state,
			String address, String country, String zipCode, String phoneNumber, boolean status) {

		if (!name.isEmpty())
			fillText(txtCustomerName, name);
		if (!email.isEmpty())
			fillText(txtCustomerEmail, email);
		if (!company.isEmpty())
			fillText(txtCustomerCompany, company);
		if (!salary.isEmpty())
			fillText(txtCustomerSalary, salary);
		if (!city.isEmpty())
			fillText(txtCustomerCity, city);
		if (!state.isEmpty())
			fillText(txtCustomerState, state);
		if (!address.isEmpty())
			fillText(txtCustomerAddress,address);

		if (!country.isEmpty())
			selectItemOnCombo(cmbCustomerCountry, country);

		if (!zipCode.isEmpty())
			fillText(txtCustomerZipCode, zipCode);
		if (!phoneNumber.isEmpty())
			fillText(txtPhoneNumber, phoneNumber);

		if (status == true) {
			if (!chkCustomerStatus.isSelected())
				click(chkCustomerStatus);
		} else {
			if (chkCustomerStatus.isSelected())
				click(chkCustomerStatus);
		}
	}

	public void clickOnSubmit() {
		click(btnSubmit);
	}
	
	public void clickOnCustomers() {
		click(lnkCustomers);
	}

	public void validatePage() {

		// Espera que o element esteja presente na tela
		new WebDriverWait(Runner.driver.get(), Duration.ofSeconds(20))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Customer Form']")));

	}

}
