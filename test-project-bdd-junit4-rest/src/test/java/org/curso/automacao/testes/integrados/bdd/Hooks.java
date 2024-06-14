package org.curso.automacao.testes.integrados.bdd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Hooks.class);
	
	@Before
	public void setUp(Scenario scenario) {

		for (String tag : scenario.getSourceTagNames()) {

			if (tag.startsWith("@env")) {
				if (tag.equalsIgnoreCase("@env.dev") || tag.equalsIgnoreCase("@env.test")
						|| tag.equalsIgnoreCase("@env.prod") || tag.equalsIgnoreCase("@env.tmp")) {

					Runner.environment.set(tag.replace("@", ""));
				} else {
					LOGGER.error("Invalid environment tag [" + tag + "]");
				}
			}
		}
	
	}

}
