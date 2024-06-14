// ------------------------------- Default ------------------------------- //
$(document).ready(function() {
	
	$("#productPrice").inputmask(
			'decimal', {
            'alias': 'numeric',
            'groupSeparator': ',',
            'autoGroup': true,
            'digits': 2,
            'radixPoint': ".",
            'digitsOptional': false,
            'allowMinus': false,
            'prefix': 'R$ ',
            'placeholder': ''});

	$("#productManufacturer").empty();
	$("#productSupplier").empty();
		
	// Load all suppliers and manufacturers
	loadCompanies();
		
	$("#form-product").submit(function(event){
		event.preventDefault();
		
		$("#alert-success-loading").hide()
		$("#alert-error-loading").hide()
	
		productId = $("#productId").val()
		productName = $("#productName").val()
		productPrice = $("#productPrice").val()
		productQuantity = $("#productQuantity").val()
		productManufacturer = $("#productManufacturer option:selected").text()
		productSupplier = $("#productSupplier option:selected").text()
		productStatus = $("#productStatus").checked ? true : false
				
		createOrUpdateProduct(event, productId, 
									 productName,
									 productPrice,
									 productManufacturer,
									 productSupplier,
									 productStatus,
									 productQuantity); 
							
	});

	// If has customer value change form to edit mode	
	productId = sessionStorage.getItem("ProductId");

	if(productId != null){
		loadCustomer(productId);
		sessionStorage.removeItem("ProductId");
	}

});

// ------------------------------- Load companies ------------------------------- // 

function loadCompanies(){
	
	request = $.ajax({
		url: company_service_url + "/all",
		type: 'GET',
		dataType: 'json',
		data: "",
		headers: {'Authorization': sessionStorage.getItem("token")},					 
		crossDomain: true
	});
	
	request.done(function(companies){
		
		//console.log(companies)
				
		for(var i=0; i < companies.length; i++){
			
			$("#productManufacturer").append('<option value="'+i+'">'+companies[i]+'</option>')
			$("#productSupplier").append('<option value="'+i+'">'+companies[i]+'</option>')
		}

	});
	
	request.fail(function(data){
		console.log(data);
	});
	
}


// ------------------------------- Check form mode (Update / Insert) ------------------------------- // 

function checkMode(){
	if ($("#productId").val() != '')
		$("#submit").html("Update Product")
	else
		$("#submit").html("Create Product")
}

// ------------------------------- Load Customer ------------------------------- //
function loadCustomer(productId){
	
	request = $.ajax({
		url: product_service_url + "/find-by/id/" + productId,
		type: 'GET',
		dataType: 'json',
		data: "",
		headers: {'Authorization': sessionStorage.getItem("token")},					 
		crossDomain: true
	});
	
	request.done(function(product){
		
		console.log(product)
		
		$("#productId").val(product.id)
		$("#productName").val(product.name)
		$("#productPrice").val(product.price)	
		$("#productStock").val(product.stock.quantity)	
		
		//console.log(getOptionIndex(product.manufacturer))
		
		$("#productManufacturer").val(getOptionIndex(product.manufacturer)).change()
		$("#productSupplier").val(getOptionIndex(product.supplier)).change()
		
		if(product.status === true)
		 	$("#productStatus").attr("checked", "checked")
		else
			$("#productStatus").removeAttr("checked")

		checkMode();
		
		$("#alert-success-loading").show()
		$("#alert-error-loading").hide()	
				 
		console.log(product);
	});
	
	request.fail(function(data){
		$("#alert-success-loading").hide()
		$("#alert-error-loading").show()
				 
		
		console.log(data);
	});
	
}

// ------------------------------- Get Company Index in List ------------------------------- //

function getOptionIndex(optionText){
	var options = [];
	
	$('#productManufacturer option').each(function(){
		options.push($(this).text())
	});
	
	for(var i=0; i < options.length; i++){		
		if (optionText === options[i])
			return i;
	}
	
	return 0;
}
 
// ------------------------------- Create Customer ------------------------------- // 
function createOrUpdateProduct(event, productId, 
									  productName,
									  productPrice,
									  productManufacturer,
									  productSupplier,
									  productStatus,
									  productQuantity){

	body = {
		"id": productId == '' ? 0 : productId,
		"name": productName,
		"price": productPrice,
		"manufacturer": productManufacturer,
		"supplier": productSupplier,
		"status": productStatus,
		"stock" : {
			"quantity": productQuantity
		}
	};
	
	console.log(body);
	console.log(JSON.stringify(body))
	
	request = $.ajax({
		url: product_service_url + "/save",
		type: 'PUT',
		dataType: 'json',
		contentType:"application/json; charset=utf-8",
		data: JSON.stringify(body),
		headers: {'Authorization': sessionStorage.getItem("token")},					 
		crossDomain: true
	});
	
	request.done(function(product){
		$("#productId").val(product.id)
		$("#alert-success").show()
		$("#alert-error").hide()
		
		checkMode();	
				 
		console.log(product);
	});
	
	request.fail(function(data){
		$("#alert-success").hide()
		$("#alert-error").show()
		
		console.log(data);
	});
	
	return true; 
}

 