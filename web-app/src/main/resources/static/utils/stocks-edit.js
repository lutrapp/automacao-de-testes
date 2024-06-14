// ------------------------------- Default ------------------------------- //
$(document).ready(function() {
	
	$("#productQuantity").inputmask(
			'decimal', {
            'alias': 'numeric',
            'groupSeparator': ',',
            'autoGroup': true,
            'digits': 0,
            'radixPoint': ".",
            'digitsOptional': false,
            'allowMinus': false,
            'prefix': 'R$ ',
            'placeholder': ''});
			
	$("#form-product").submit(function(event){
		event.preventDefault();
		
		$("#alert-success-loading").hide()
		$("#alert-error-loading").hide()
	
		productId = $("#productId").val()
		productName = $("#productName").val()
		productQuantity = $("#productQuantity").val()
				
		createOrUpdateProduct(event, productId, 
									 productName,
									 productQuantity);							
	});

	// If has customer value change form to edit mode	
	productId = sessionStorage.getItem("ProductId");

	if(productId != null){
		loadCustomer(productId);
		sessionStorage.removeItem("ProductId");
	} else {
		$("#submit").prop("disabled", "true")
	}

});

// ------------------------------- Check form mode (Update / Insert) ------------------------------- // 
function checkMode(){
	if ($("#productId").val() != ''){
		$("#submit").html("Update Stock")
		$("#submit").removeProp("disabled")
	}
	else
		$("#submit").prop("disabled", "true")
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
		$("#productQuantity").val(product.stock == null? 0 : product.stock.quantity)		
			
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
 
// ------------------------------- Create Customer ------------------------------- // 
function createOrUpdateProduct(event, productId, 
									  productName,
									  productQuantity){

	body = {
		"id": productId == '' ? 0 : productId,
		//"name": productName,
		"quantity" : productQuantity
	};
	
	console.log(body);
	console.log(JSON.stringify(body))
	
	request = $.ajax({
		url: product_service_url + "/update-stock",
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

 