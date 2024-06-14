// ------------------------------- Default ------------------------------- //
$(document).ready(function() {

	$("#customerSalary").inputmask(
		'decimal', {
		'alias': 'numeric',
		'groupSeparator': ',',
		'autoGroup': true,
		'digits': 2,
		'radixPoint': ".",
		'digitsOptional': false,
		'allowMinus': false,
		'prefix': 'R$ ',
		'placeholder': ''
	});

	$("#customerCountry").empty();

	loadCountries();

	$("#form-customer").submit(function(event) {
		event.preventDefault();

		$("#alert-success-loading").hide()
		$("#alert-error-loading").hide()

		customerId = $("#customerId").val()
		customerName = $("#customerName").val()
		customerEmail = $("#customerEmail").val()
		customerCompany = $("#customerCompany").val()
		customerSalary = $("#customerSalary").val()
		customerCity = $("#customerCity").val()
		customerState = $("#customerState").val()
		customerAddress = $("#customerAddress").val()
		customerCountry = $("#customerCountry option:selected").text()
		customerZipCode = $("#customerZipCode").val()
		customerPhoneNumber = $("#customerPhoneNumber").val()
		customerStatus = $("#customerStatus").checked ? true : false

		createOrUpdateCustomer(event, customerId,
			customerName,
			customerEmail,
			customerCompany,
			customerSalary,
			customerCity,
			customerState,
			customerAddress,
			customerCountry,
			customerZipCode,
			customerPhoneNumber,
			customerStatus);

	});

	// If has customer value change form to edit mode	
	customerId = sessionStorage.getItem("CustomerId");

	if (customerId != null) {
		loadCustomer(customerId);
		sessionStorage.removeItem("CustomerId");
	}

});


// ------------------------------- Load countries ------------------------------- // 

function loadCountries() {

	request = $.ajax({
		url: customer_service_url + "/countries/all",
		type: 'GET',
		dataType: 'json',
		data: "",
		headers: { 'Authorization': sessionStorage.getItem("token") },
		crossDomain: true
	});

	request.done(function(countries) {

		console.log(countries)

		for (var i = 0; i < countries.length; i++) {
			$("#customerCountry").append('<option value="' + i + '">' + countries[i] + '</option>')
		}

	});

	request.fail(function(data) {
		console.log(data);
	});

}

// ------------------------------- Check form mode (Update / Insert) ------------------------------- // 

function checkMode() {
	if ($("#customerId").val() != '')
		$("#submit").html("Update Customer")
	else
		$("#submit").html("Create Customer")
}

// ------------------------------- Load Customer ------------------------------- //
function loadCustomer(customerId) {

	request = $.ajax({
		url: customer_service_url + "/find-by/id/" + customerId,
		type: 'GET',
		dataType: 'json',
		data: "",
		headers: { 'Authorization': sessionStorage.getItem("token") },
		crossDomain: true
	});

	request.done(function(customer) {
		
		console.log(customer);
		console.log(customer.status)
				
		$("#customerId").val(customer.id)
		$("#customerName").val(customer.name)
		$("#customerEmail").val(customer.email)
		$("#customerCompany").val(customer.company)
		$("#customerSalary").val(customer.salary)
		$("#customerCity").val(customer.city)
		$("#customerState").val(customer.state)

		$("#customerAddress").val(customer.address)
		$("#customerCountry").val(getOptionIndex(customer.country)).change()

		$("#customerZipCode").val(customer.zipcode)
		$("#customerPhoneNumber").val(customer.phoneNumber)

		if (customer.status === true)
			$("#customerStatus").attr("checked", "checked")
		else
			$("#customerStatus").removeAttr("checked")

		checkMode();

		$("#alert-success-loading").show()
		$("#alert-error-loading").hide()
	});

	request.fail(function(data) {
		$("#alert-success-loading").hide()
		$("#alert-error-loading").show()

		console.log(data);
	});

}

// ------------------------------- Get Country Index in List ------------------------------- //

function getOptionIndex(optionText) {
	var options = [];

	$('#customerCountry option').each(function() {
		options.push($(this).text())
	});
	
	for (var i = 0; i < options.length; i++) {
		if (optionText === options[i])
			return i;
	}

	return 0;
}

// ------------------------------- Create Customer ------------------------------- // 
function createOrUpdateCustomer(event, customerId,
	customerName,
	customerEmail,
	customerCompany,
	customerSalary,
	customerCity,
	customerState,
	customerAddress,
	customerCountry,
	customerZipCode,
	customerPhoneNumber,
	customerStatus) {

	body = {
		"id": customerId == '' ? 0 : customerId,
		"name": customerName,
		"email": customerEmail,
		"company": customerCompany,
		"salary": customerSalary,
		"city": customerCity,
		"state": customerState,
		"address": customerAddress,
		"country": customerCountry,
		"zipcode": customerZipCode,
		"phoneNumber": customerPhoneNumber,
		"status": customerStatus
	};

	console.log(body);

	request = $.ajax({
		url: customer_service_url + "/save",
		type: 'PUT',
		dataType: 'json',
		contentType: "application/json; charset=utf-8",
		data: JSON.stringify(body),
		headers: { 'Authorization': sessionStorage.getItem("token") },
		crossDomain: true
	});

	request.done(function(customer) {
		$("#customerId").val(customer.id)
		$("#alert-success").show()
		$("#alert-error").hide()

		checkMode();

		console.log(customer);
	});

	request.fail(function(data) {
		$("#alert-success").hide()
		$("#alert-error").show()

		console.log(data);
	});

	return true;
}

