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
public class CustomerListPage {

	@FindBy(xpath = "//*[text()='Customer List']")
	private WebElement lblCustomerList;

	@FindBy(xpath = "//button[@id='button-forms-customer-create']")
	private WebElement btnCreateCustomer;

	@FindBy(xpath = "//input[@type='search']")
	private WebElement txtSearch;

	public void validatePage() {

		// Espera que o element esteja presente na tela
		new WebDriverWait(Runner.driver.get(), Duration.ofSeconds(20))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Customer List']")));
	}
	
	public WebElement findValueInTable(String value) {
		return Runner.driver.get().findElement(By.xpath("(//td[text()='" + value + "'])[1]"));
	}
	
	public WebElement findFirstButtonUpdateInTable() {
		return Runner.driver.get().findElement(By.xpath("(//button[@id='button-forms-customer-update'])[1]"));
	}
	
	public WebElement findFirstButtonDeleteInTable() {
		return Runner.driver.get().findElement(By.xpath("(//button[@id='button-customer-delete'])[1]"));
	}
	
	public WebElement findNoMatchingRecordsFound() {
		return Runner.driver.get().findElement(By.xpath("//td[text()='No matching records found']"));
	}
	
	public void acceptAlertDeleteConfirmation() {
		Runner.driver.get().switchTo().alert().accept();
	}

}
