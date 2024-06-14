package org.curso.automacao.testes.integrados.actions;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.curso.automacao.testes.BaseSteps;
import org.curso.automacao.testes.helpers.entities.Customer;
import org.curso.automacao.testes.integrados.bdd.Runner;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import com.github.javafaker.Faker;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomerActions {
	
	public static final String CUSTOMER_SAVE_ENDPOINT = "/api/v1/customers/save";
	
	public static Customer getNewCustomer() {
		
		return Customer.builder()
					.address(Faker.instance().address().fullAddress())
					.city(Faker.instance().address().cityName())
					.company(Faker.instance().company().name())
					.country(Faker.instance().country().name())
					.email(Faker.instance().internet().emailAddress())
					.name(Faker.instance().name().fullName())
					.phoneNumber(Faker.instance().phoneNumber().phoneNumber())									
					.salary(Faker.instance().number().numberBetween(5000, 20000))
					.state(Faker.instance().address().state())
					.zipcode(Faker.instance().address().zipCode())
					.build();

			
	}
	
	public static RequestSpecification getNewCustomerRequestSpecification(Customer customer) throws Exception {
		return  given()
				.baseUri(BaseSteps.getUrl("services.customers.url"))
				.header("Authorization", Runner.token.get())
				.body(customer)
				.contentType(ContentType.JSON)
				.log().all(true);
	}
	
	public static Response requestNewCustomer(RequestSpecification requestSpecification) {
		return requestSpecification
				.when()
					.put(CUSTOMER_SAVE_ENDPOINT);
	}
	
	public static Customer validateNewCustomerResponse(Customer customerCreated,  Response response) {
		
		Customer customer = response.then()
									.log()
									.all(true)
									.assertThat()
									.statusCode(201).extract().as(Customer.class);

		assertNotNull("Validate if customer response is not null.", customer);
		assertNotEquals("Validate if customer id is greater than zero.", customer.getId(), 0);
		
		assertTrue("Validate if all fiels of customer are equals (except the id).", new ReflectionEquals(customerCreated, "id").matches(customer));
		
		BaseSteps.logInfo("Customer created id [" + customer.getId() + "]");
		
		return customer;
		
	}
	
	public static Customer createNewCustomer() throws Exception {

		BaseSteps.logInfo("Prepare data for new customer.");
		Customer customer = CustomerActions.getNewCustomer();

		BaseSteps.logInfo("Preparing the request specification.");
		RequestSpecification requestSpecification = CustomerActions.getNewCustomerRequestSpecification(customer);

		BaseSteps.logInfo("Executing the request to authentication api [" + BaseSteps.getUrl("services.customer.url")
				+ CustomerActions.CUSTOMER_SAVE_ENDPOINT + "]");
		Response response = CustomerActions.requestNewCustomer(requestSpecification);

		BaseSteps.logInfo("Validate if customer was succesfully created.");
		return CustomerActions.validateNewCustomerResponse(customer, response);
	}

}
