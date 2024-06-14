package org.curso.automacao.testes.sistemas.pages;

import org.curso.automacao.testes.sistemas.bdd.Runner;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class PageBase {

	
	public void fillText(WebElement element, String value) {
		element.clear();
		element.sendKeys(value);
	}
	
	public void selectItemOnCombo(WebElement element, String value) {
		Select combo = new Select(element);
		combo.selectByVisibleText(value);
	}

	public void click(WebElement element) {
		((JavascriptExecutor) Runner.driver.get()).executeScript("arguments[0].click();", element);
	}

}
