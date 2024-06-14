package org.curso.automacao.testes.sistemas.pages;

import org.curso.automacao.testes.sistemas.bdd.Runner;
import org.openqa.selenium.support.PageFactory;

public class MasterPageFactory {
	
	public static <T> T getPage(Class<T> pageClass){
		return PageFactory.initElements(Runner.driver.get(), pageClass);	
	}

}
