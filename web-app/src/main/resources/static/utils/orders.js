// ------------------------------- Get Customers ------------------------------- //
$(document).ready(function() {

	$("#button-forms-customer-create").click(function(event) {
		event.preventDefault();
		location.href = "/orders-edit";
	});
	
	loadOrders();
		
});

// ------------------------------- Load Customers ------------------------------- //
function loadOrders(){
	
	var table = $('#table-orders').DataTable({
		destroy: true,
		paging: false,
		searching: true,
		bInfo: true,
		sorting: true,
		dom: 'Bfrtip',
		columns: [
			{width: "10%"}, {width: "auto"}, {width:"20%"}, {width: "10%", className: "text-right"}, {width: "5%"}, {width: "5%"}],
		buttons: [
              	'copyHtml5',
            	'excelHtml5',
            	'csvHtml5',
            	'pdfHtml5']
	});
	
	table.clear();
	
	var request = $.ajax({
		url: order_service_url + "/all",
		type: 'get',
		headers: { 'Authorization': sessionStorage.getItem("token") },
		dataType: 'json',
		crossDomain: true
	});

	request.done(function(orders) {
		// Request success
		$("#alert-success").show()
		$("#alert-error").hide()
		
		for(var i=0; i < orders.length; i++){

			table.row.add([
				orders[i].id,
				orders[i].customerName,
				orders[i].date,
				getCurrencyFormatted(orders[i].total),
				'<button onclick="javascript:editOrder(' + orders[i].id + ')" type="button" class="btn btn-secondary" id="button-forms-order-update"><div class="icon"><i class="bx bxs-edit-alt"></i></div></button>',			
				'<button onclick="javascript:deleteOrder(' + orders[i].id + ',\'' + orders[i].customerName + '\');" type="button" class="btn btn-secondary" id="button-order-delete"><div class="icon"><i class="bx bxs-eraser"></i></div></button>',				
				""
			]);
		}
		
		table.draw();
		
		console.log(orders);

	});

	request.fail(function(data) {
		// Request failed
		$("#alert-success").hide()
		$("#alert-error").show()
			
		console.log(data);
	});
}

// ------------------------------- Edit Customer ------------------------------- //
function editOrder(orderId){
	
	if(orderId != null){
		sessionStorage.setItem("OrderId", orderId);
		window.location.href="/orders-edit";
	}
	
}

// ------------------------------- Delete Customer ------------------------------- //
function deleteOrder(orderId, customerName){	
	if (confirm("Are you to drop the order with {Id:" + orderId + ", Customer:" + customerName +"}?")){

		var request = $.ajax({
			url: order_service_url + "/delete-by/id/" + orderId,
			type: 'delete',
			headers: { 'Authorization': sessionStorage.getItem("token") },
			dataType: 'json',
			crossDomain: true
		});
		
		request.done(function(orders) {
			// Request success
			$("#alert-error-deleting").hide();
			$("#alert-success-deleting").show().delay(300);

			loadOrders();

		});

		request.fail(function(data) {
			// Request failed
			$("#alert-error-deleting").show()
			$("#alert-success-deleting").hide()
				
			console.log(data);
		});

	}
}
