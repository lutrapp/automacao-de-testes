// ------------------------------- Get Customers ------------------------------- //
$(document).ready(function() {

	$("#button-forms-stocks-create").click(function(event) {
		event.preventDefault();
		location.href = "/stocks-edit";
	});
	
	loadCustomers();
		
});

// ------------------------------- Load Customers ------------------------------- //
function loadCustomers(){
	
	var table = $('#table-products').DataTable({
		destroy: true,
		paging: false,
		searching: true,
		bInfo: true,
		sorting: true,
		dom: 'Bfrtip',
		columns: [
			{width: "10%"}, {width: "auto"}, {width: "20%", className: "text-right"}, {width: "5%"}, {width: "5%"}],
		buttons: [
              	'copyHtml5',
            	'excelHtml5',
            	'csvHtml5',
            	'pdfHtml5']
	});
	
	table.clear();
	
	var request = $.ajax({
		url: product_service_url + "/all",
		type: 'get',
		headers: { 'Authorization': sessionStorage.getItem("token") },
		dataType: 'json',
		crossDomain: true
	});

	request.done(function(products) {
		// Request success
		$("#alert-success").show()
		$("#alert-error").hide()
		
		for(var i=0; i < products.length; i++){
		
			table.row.add([
				products[i].id,
				products[i].name,
				products[i].stock == null? 0: products[i].stock.quantity,
				'<button onclick="javascript:editCustomer(' + products[i].id + ')" type="button" class="btn btn-secondary" id="button-forms-customer-update"><div class="icon"><i class="bx bxs-edit-alt"></i></div></button>',			
				'<button disabled onclick="javascript:deleteCustomer(' + products[i].id + ',\'' + products[i].name + '\');" type="button" class="btn btn-secondary" id="button-customer-delete"><div class="icon"><i class="bx bxs-eraser"></i></div></button>',				
				""
			]);
		}
		
		table.draw();
		
		console.log(products);

	});

	request.fail(function(data) {
		// Request failed
		$("#alert-success").hide()
		$("#alert-error").show()
			
		console.log(data);
	});
}

// ------------------------------- Edit Customer ------------------------------- //
function editCustomer(productId){
	
	if(productId != null){
		sessionStorage.setItem("ProductId", productId);
		window.location.href="/stocks-edit";
	}
	
}

// ------------------------------- Delete Customer ------------------------------- //
function deleteCustomer(productId, productName){
	if (confirm("Are you to drop the product with {Id:" + productId + ", Name:" + productName +"}?")){

		var request = $.ajax({
			url: product_service_url + "/delete-by/id/" + productId,
			type: 'delete',
			headers: { 'Authorization': sessionStorage.getItem("token") },
			dataType: 'json',
			crossDomain: true
		});
		
		request.done(function(customers) {
			// Request success
			$("#alert-error-deleting").hide();
			$("#alert-success-deleting").show().delay(300);

			loadCustomers();

			//var table = $('#table-customers').DataTable();
			//table.ajax.reload();	
		});

		request.fail(function(data) {
			// Request failed
			$("#alert-error-deleting").show()
			$("#alert-success-deleting").hide()
				
			console.log(data);
		});

	}
}
