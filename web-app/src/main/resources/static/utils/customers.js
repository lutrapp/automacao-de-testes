// ------------------------------- Get Customers ------------------------------- //
$(document).ready(function() {

	$("#button-forms-customer-create").click(function(event) {
		event.preventDefault();
		location.href = "/customers-edit";
	});
	
	loadCustomers();
		
});

// ------------------------------- Load Customers ------------------------------- //
function loadCustomers(){
	
	var table = $('#table-customers').DataTable({
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
		url: customer_service_url + "/all",
		type: 'get',
		headers: { 'Authorization': sessionStorage.getItem("token") },
		dataType: 'json',
		crossDomain: true
	});

	request.done(function(customers) {
		// Request success
		$("#alert-success").show()
		$("#alert-error").hide()
		
		for(var i=0; i < customers.length; i++){
		
			table.row.add([
				customers[i].id,
				customers[i].name,
				getCurrencyFormatted(customers[i].salary),
				'<button onclick="javascript:editCustomer(' + customers[i].id + ')" type="button" class="btn btn-secondary" id="button-forms-customer-update"><div class="icon"><i class="bx bxs-edit-alt"></i></div></button>',			
				'<button onclick="javascript:deleteCustomer(' + customers[i].id + ',\'' + customers[i].name + '\');" type="button" class="btn btn-secondary" id="button-customer-delete"><div class="icon"><i class="bx bxs-eraser"></i></div></button>',				
				""
			]);
		}
		
		table.draw();
		
		console.log(customers);

	});

	request.fail(function(data) {
		// Request failed
		$("#alert-success").hide()
		$("#alert-error").show()
			
		console.log(data);
	});
}

// ------------------------------- Edit Customer ------------------------------- //
function editCustomer(customerId){
	
	if(customerId != null){
		sessionStorage.setItem("CustomerId", customerId);
		window.location.href="/customers-edit";
	}
	
}

// ------------------------------- Delete Customer ------------------------------- //
function deleteCustomer(customerId, customerName){
	if (confirm("Are you to drop the customer with {Id:" + customerId + ", Name:" + customerName +"}?")){

		var request = $.ajax({
			url: customer_service_url + "/delete-by/id/" + customerId,
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
