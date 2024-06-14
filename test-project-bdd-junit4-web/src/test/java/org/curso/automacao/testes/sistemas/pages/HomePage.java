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
public class HomePage {

	@FindBy(xpath = "//*[text()='Home']")
	private WebElement lblHome;
	
	@FindBy(xpath = "//span[text()='Customers']/ancestor::a")
	private WebElement lnkCustomers;
	
	@FindBy(xpath ="//i[@class='bi bi-list toggle-sidebar-btn']")
	private WebElement btnMenu;
	
	public void validatePage() {

		// Espera que o element esteja presente na tela
		new WebDriverWait (Runner.driver.get(), Duration.ofSeconds(20))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Home']")));
		
	}
	
	public void showMenu() {
		
		if(!lnkCustomers.isDisplayed())
			btnMenu.click();
		
	}

}
