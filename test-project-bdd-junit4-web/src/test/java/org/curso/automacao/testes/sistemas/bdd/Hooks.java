package org.curso.automacao.testes.sistemas.bdd;

import java.time.Duration;

import org.curso.automacao.testes.helpers.ConnectionHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Hooks {

	private static final Logger LOGGER = LoggerFactory.getLogger(Hooks.class);

	@BeforeAll
	public static void setUp() {
		if (ConnectionHelper.isRunningInsideDocker()) {
			
			//System.setProperty("webdriver.chrome.verboseLogging", "true");
			
			LOGGER.info("Running on container.");
			WebDriverManager.chromedriver()
						.forceDownload()
						.browserInDocker()			
						.setup();
		} else {
			LOGGER.info("Running on physical host.");
			WebDriverManager.chromedriver().setup();
		}
	}
	
	@AfterAll
	public static void tearDown() {		
		
		if(WebDriverManager.chromedriver() != null)
			WebDriverManager.chromedriver().quit();
	}
	
	
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

		initializeDriver();

	}

	private void initializeDriver() {
		try {

			WebDriver chromeDriver;

			if (ConnectionHelper.isRunningInsideDocker()) {
				LOGGER.info("Running script in container");
				chromeDriver = new ChromeDriver(CommomHelper.getChromeOptionsForContainer());
			} else {
				chromeDriver = new ChromeDriver(CommomHelper.getChromeOptions() );
			}
			
			chromeDriver.manage().timeouts()
					.pageLoadTimeout(Duration.ofSeconds(Constants.IMPLICIT_PAGE_LOAD_TIMEOUT_SECS))
					.implicitlyWait(Duration.ofSeconds(Constants.IMPLICIT_WAIT_TIMEOUT_SECS))
					.scriptTimeout(Duration.ofSeconds(Constants.IMPLICIT_SCRIPT_TIMEOUT_SECS));
			
			Runner.driver.set(chromeDriver);

		} catch (Exception e) {
			LOGGER.error("Error when creating driver", e);
			throw e;
		}
	}

	@After
	public void tearDown(Scenario scenario) {
		terminateDriver();
	}

	private void terminateDriver() {
		try {
			WebDriver chromeDriver = Runner.driver.get();

			if (chromeDriver != null) {
				chromeDriver.quit();
			
				Runner.driver.set(null);
			}
		} catch (Exception e) {
			LOGGER.error("Error when quiting driver", e);
			throw e;
		}
	}

}
