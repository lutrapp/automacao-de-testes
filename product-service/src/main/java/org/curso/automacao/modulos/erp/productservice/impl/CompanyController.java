package org.curso.automacao.modulos.erp.productservice.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import aj.org.objectweb.asm.TypeReference;

@RestController
@RequestMapping("/api/v1/companies")
public class CompanyController {

	@GetMapping("/all")
	public final ResponseEntity<List<String>> findAll() {
		
		return new ResponseEntity<List<String>>(getCompanies().stream().sorted().toList(), HttpStatus.OK);
		
//		HashSet<String> companies = new HashSet<String>();
//
//		Faker faker = Faker.instance();
//		String company = faker.company().name();
//
//		while (!companies.contains(company)) {
//			companies.add(company);
//			company = faker.company().name();
//		}
//
//		return new ResponseEntity<List<String>>(new ArrayList<String>(companies), HttpStatus.OK);
	}

	private List<String> getCompanies() {

		try (InputStream inputStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("data/companies.json")) {
			ObjectMapper mapper = new ObjectMapper();
			String[] companies = mapper.readValue(inputStream, String[].class);
			return List.of(companies);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
