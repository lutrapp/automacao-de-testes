package org.curso.automacao.testes.sistemas.actions;

import org.curso.automacao.testes.BaseSteps;
import org.curso.automacao.testes.sistemas.bdd.Runner;

public class CommonActions {
	
	public static void gotoApplication() throws Exception {
		Runner.driver.get().get(BaseSteps.getUrl("webapp.url"));
		Runner.driver.get().manage().window().maximize();
	}

}
