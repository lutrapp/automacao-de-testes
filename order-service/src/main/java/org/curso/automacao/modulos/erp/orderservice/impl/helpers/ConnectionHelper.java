package org.curso.automacao.modulos.erp.orderservice.impl.helpers;

import java.io.File;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ConnectionHelper {

	@Autowired
	Environment environment;
	
	public String getHost(String service) {

		if (isRunningInsideDocker()) {
			return getActiveProfile() + "-" + service;
		} else {
			return "localhost";
		}

	}

	public String getActiveProfile() {
		if (Arrays.stream(environment.getActiveProfiles()).anyMatch(env -> env.equalsIgnoreCase("test")))
			return "test";
		if (Arrays.stream(environment.getActiveProfiles()).anyMatch(env -> env.equalsIgnoreCase("dev")))
			return "dev";
		if (Arrays.stream(environment.getActiveProfiles()).anyMatch(env -> env.equalsIgnoreCase("prod")))
			return "prod";
		if (Arrays.stream(environment.getActiveProfiles()).anyMatch(env -> env.equalsIgnoreCase("tmp")))
			return "tmp";
		
		return "dev";
	}
	
	public Boolean isRunningInsideDocker() {

		return new File("/.dockerenv").exists();		
    }

	public boolean isTest() {
		for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
			if (element.getClassName().startsWith("org.junit.") ||
				element.getClassName().startsWith("org.testng.")) {
				return true;
			}
		}
		return false;
	}

}
