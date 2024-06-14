package org.curso.automacao.testes.integrados.bdd;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = {"src/test/resources/org/curso/automacao/testes/integrados/bdd" }, 
		glue = "org.curso.automacao.testes.integrados.bdd", 
		plugin = {
				"pretty", "html:target/test-output", "json:target/cucumber-report/cucumber.json",
				"io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"})
public class Runner {

	private static Logger logger = LoggerFactory.getLogger(Runner.class);

	public static ThreadLocal<String> environment = new ThreadLocal<>();
	
	public static ThreadLocal<String> token = new ThreadLocal<>();

}
