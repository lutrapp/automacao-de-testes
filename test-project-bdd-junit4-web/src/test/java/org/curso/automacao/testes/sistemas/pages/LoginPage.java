package org.curso.automacao.testes.sistemas.pages;

import static org.junit.Assert.assertEquals;

import org.curso.automacao.testes.sistemas.bdd.Runner;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginPage {
	
	@FindBy(xpath = "//input[@id='yourUsername']")
	private WebElement txtUserName;
	
	@FindBy(xpath = "//input[@id='yourPassword']")
	private WebElement txtPassword;
	
	@FindBy(xpath = "//button[@id='logginButton']")
	private WebElement btnLogin;
	
	public void validatePage() {
		assertEquals("Check window page title", Runner.driver.get().getTitle(), "Pages / Login - Test Automation");
	}
	
}
