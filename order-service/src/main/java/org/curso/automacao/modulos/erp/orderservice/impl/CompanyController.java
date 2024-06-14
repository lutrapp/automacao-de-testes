package org.curso.automacao.modulos.erp.orderservice.impl;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/v1/companies")
public class CompanyController {

	@GetMapping("/all")
	public final ResponseEntity<List<String>> findAll() {

		return new ResponseEntity<List<String>>(getCompanies().stream().sorted().collect(Collectors.toList()),
				HttpStatus.OK);
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
