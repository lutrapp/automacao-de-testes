// ------------------------------- Default ------------------------------- //
$(document).ready(function() {
	
	// Initialize table items
	$('#table-items').DataTable({ bInfo: false, searching: false, paging: false });
		
	$("#orderDate").val(getToday())
	$("#orderDeliveryDate").val(getToday())
	$("#buttonCloseModal").click(function(){clearModalState()})
	$("#quantity").focusout(function(){calculateTotal()});
	$("#productPrice").focusout(function(){calculateTotal()});
	$("#productName").focusout(function(){calculateTotal()});
	$("#orderTotal").maskMoney({showSymbol:true, symbol:"R$", decimal:",", thousands:"."});
	$("#productName").on('change', function(){searchProductPrice()})
	$("#productPrice").focusin(function(){searchProductPrice()})
	$(document).keyup(function(e) {if (e.key === "Escape") { clearModalState() }})
	
	// Load all customers
	$("#customerName").empty();
	loadCustomers();
	
	// Load all products
	$("#productName").empty();
	loadProducts();

	$("#form-order").submit(function(event) {
		event.preventDefault();

		$("#alert-success-loading").hide()
		$("#alert-error-loading").hide()

		orderId = $("#orderId").val()
		customerName = $("#customerName option:selected").text()
		orderDate = $("#orderDate").val()
		orderDeliveryDate = $("#orderDeliveryDate").val()

		createOrUpdateOrder(event, orderId,
			customerName,
			orderDate,
			orderDeliveryDate,
			orderTotal);

	});
	
	$("#form-detail").submit(function(event) {
		event.preventDefault();
			
		if(isValid($("#form-detail"))){
			
			$("#form-detail").removeClass("was-validated")
						
			addOrUpdateItem($("#orderItemId").val(),
							$("#productName option:selected").text(),
							$("#productPrice").val(),
							$("#quantity").val(),
							$("#totalItem").val())
							
			calculateTotal()
			clearModalState()	
		}
		
	});

	// If has customer value change form to edit mode	
	orderId = sessionStorage.getItem("OrderId");

	if (orderId != null) {
		loadOrder(orderId);
		sessionStorage.removeItem("OrderId");
	}

});

// ------------------------------- Check form mode (Update / Insert) ------------------------------- // 

function checkMode() {
	if ($("#orderId").val() != '')
		$("#submit").html("Update Order")
	else
		$("#submit").html("Create Order")
}

// ------------------------------- Load Customer ------------------------------- //
function loadOrder(orderId) {

	request = $.ajax({
		url: order_service_url + "/find-by/id/" + orderId,
		type: 'GET',
		dataType: 'json',
		data: "",
		headers: { 'Authorization': sessionStorage.getItem("token") },
		crossDomain: true
	});

	request.done(function(order) {

		console.log(order)

		$("#orderId").val(order.id)
		$("#customerName").val(getOptionIndex($("#customerName option"), order.customerName)).change()	
		$("#orderDate").val(order.date)
		$("#orderDeliveryDate").val(order.deliveryDate)
		$("#orderTotal").val(getCurrencyFormatted(parseFloat(order.total).toFixed(2)))

		var table = $('#table-items').DataTable();
		table.clear();

		for (i = 0; i < order.items.length; i++) {

			orderItem = order.items[i]

			table.row.add([
				orderItem.id.id,
				orderItem.productName,
				orderItem.productPrice,
				orderItem.quantity,
				getCurrencyFormatted(parseFloat(orderItem.productPrice).toFixed(2) * parseFloat(orderItem.quantity).toFixed(2)),
				'<button onclick="javascript:editOrderItem(' + orderItem.id.id + ')" type="button" class="btn btn-secondary" id="button-forms-order-update"><div class="icon"><i class="bx bxs-edit-alt"></i></div></button>',
				'<button onclick="javascript:deleteOrderItem(' + orderItem.id.id + ',\'' + orderItem.productName + '\');" type="button" class="btn btn-secondary" id="button-order-delete"><div class="icon"><i class="bx bxs-eraser"></i></div></button>',
			])
		}

		table.draw();
		checkMode();

		$("#alert-success-loading").show()
		$("#alert-error-loading").hide()

		console.log(order);
	});

	request.fail(function(data) {
		$("#alert-success-loading").hide()
		$("#alert-error-loading").show()
		
		console.log(data);
	});

}

function getOptionIndex(selectControl, optionText){
	var options = [];
	
	selectControl.each(function(){
		options.push($(this).text())
	});
	
	console.log(options)
	
	for(var i=0; i < options.length; i++){		
		if (optionText === options[i])
			return i;
	}
	
	return 0;
}
 

// ------------------------------- Create Customer ------------------------------- // 
function createOrUpdateOrder(event, orderId,
	customerName,
	orderDate,
	orderDeliveryDate) {

	body = {
		"id": orderId == '' ? 0 : orderId,
		"name": "",
		"idCustomer": 0,
		"customerName": customerName,
		"date": orderDate,
		"deliveryDate": orderDeliveryDate,
		"items": getItems()
	};
	
	console.log(body);

	request = $.ajax({
		url: order_service_url + "/save",
		type: 'PUT',
		dataType: 'json',
		contentType: "application/json; charset=utf-8",
		data: JSON.stringify(body),
		headers: { 'Authorization': sessionStorage.getItem("token") },
		crossDomain: true
	});

	request.done(function(order) {
		$("#orderId").val(order.id)
		$("#alert-success").show()
		$("#alert-error").hide()

		checkMode();

		console.log(order);
	});

	request.fail(function(data) {
		$("#alert-success").hide()
		$("#alert-error").show()

		console.log(data);
	});

	return true;
}


function loadProducts() {

	request = $.ajax({
		url: product_service_url + "/all",
		type: 'GET',
		dataType: 'json',
		data: "",
		headers: { 'Authorization': sessionStorage.getItem("token") },
		crossDomain: true
	});

	request.done(function(products) {
		for (var i = 0; i < products.length; i++) {
			$("#productName").append('<option value="' + i + '">' + products[i].name + '</option>')
		}
	});

	request.fail(function(data) {
		console.log(data);
	});
		
}


function loadCustomers() {

	request = $.ajax({
		url: customer_service_url + "/all",
		type: 'GET',
		dataType: 'json',
		data: "",
		headers: { 'Authorization': sessionStorage.getItem("token") },
		crossDomain: true
	});

	request.done(function(customers) {
		for (var i = 0; i < customers.length; i++) {
			$("#customerName").append('<option value="' + i + '">' + customers[i].name + '</option>')
		}
	});

	request.fail(function(data) {
		console.log(data);
	});

}

function editOrderItem(idOrderItem){
	
	var table = $('#table-items').DataTable();
	
	table.rows().every(function(){
		var orderItem = this.data();
		
		if(orderItem[0] === idOrderItem){
			
			$("#orderItemId").val(orderItem[0])
			$("#productName").val(getOptionIndex($("#productName option"), orderItem[1])).change()			
			$("#productPrice").val(orderItem[2])
			$("#quantity").val(orderItem[3])
			$("#totalItem").val(getCurrencyFormatted(parseFloat(orderItem[4].replaceAll(".","").replace(",",".")).toFixed(2)))
			$("#submitItem").text("Edit Item")

			$("#createOrUpdateItem").trigger("click");
						
			console.log(orderItem[4].replaceAll(".",""))
			return;
		}
	})
}

function addOrUpdateItem(idOrderItem, productName, productPrice, quantity, total){
	
	var table = $('#table-items').DataTable();
	rowFound = false;
	lastOrderItemId = 0;
	
	if(idOrderItem != null && idOrderItem != ""){	
		table.rows().every(function(){
			var orderItem = this.data();
			
			lastOrderItemId = parseInt(orderItem[0])
			
			if(orderItem[0] === parseInt(idOrderItem)){
				var orderItem = this.data();
				
				orderItem[1] = productName
				orderItem[2] = productPrice
				orderItem[3] = quantity
				orderItem[4] = getCurrencyFormatted(parseFloat(productPrice).toFixed(2) * parseFloat(quantity).toFixed(2)),
				rowFound = true
				
				table.row($(this)).data(orderItem)
				return false
			}	
		});
	} else {
		lastOrderItemId = getLastItemId()
	}
	
	
	if(!rowFound){
		table.row.add([
			lastOrderItemId,
			productName,
			productPrice,
			quantity,
			getCurrencyFormatted(parseFloat(productPrice).toFixed(2) * parseFloat(quantity).toFixed(2)),
			'<button onclick="javascript:editOrderItem(' + lastOrderItemId + ')" type="button" class="btn btn-secondary" id="button-forms-order-update"><div class="icon"><i class="bx bxs-edit-alt"></i></div></button>',
			'<button onclick="javascript:deleteOrderItem(' + lastOrderItemId + ',\'' + productName + '\');" type="button" class="btn btn-secondary" id="button-order-delete"><div class="icon"><i class="bx bxs-eraser"></i></div></button>',
		])
		
		table.draw()
	}
	
	$("#buttonCloseModal").trigger('click')
}

function isValid(formToValidate){
	//formToValidate.each(function(){		
		var results = $(".invalid-feedback:visible")	
		return results.length == 0
	//});	
}

function calculateTotal(){
	//console.log(parseFloat($("#quantity").val()))
	//console.log(parseFloat($("#productPrice").val()))
		console.log(getTotal())
	$("#totalItem").val(getCurrencyFormatted(parseFloat($("#quantity").val()) * parseFloat($("#productPrice").val())))
	$("#orderTotal").val(getCurrencyFormatted(getTotal()))
	

}

function searchProductPrice(){
	
	request = $.ajax({
		url: product_service_url + "/find-by/product-name/" + $("#productName option:selected").text(),
		type: 'GET',
		dataType: 'json',
		data: "",
		headers: { 'Authorization': sessionStorage.getItem("token") },
		crossDomain: true
	});

	request.done(function(product) {		
		$("#productPrice").val(product.price)
	});

	request.fail(function(data) {
		console.log(data);
	});
}

function clearModalState(){
	$("#orderItemId").val("")
	$("#productName").val(0);
	$("#productPrice").val("")
	$("#quantity").val("")
	$("#totalItem").val("")
	$("#submitItem").text("Create Item")
}

function getTotal(){
	items = new Array()
	var table = $('#table-items').DataTable();
	var total = 0
	
	table.rows().every(function(){
		var row = this.data()
		total += parseFloat(row[4].replaceAll(".", ""))
	});
	
	return total
}

function getItems(){
	
	items = new Array()
	var table = $('#table-items').DataTable();
	
	table.rows().every(function(){
		var orderItem = this.data();
		
		console.log($("#orderId").val())
	
		item = {
			"id" : {
				"idOrder": $("#orderId").val(),
				"id": orderItem[0]
			},
			"idProduct" : "",
			"productName" : orderItem[1],
			"productPrice" : orderItem[2],
			"quantity" : orderItem[3],
			
		}
		
		items.push(item)
	});
	
	return items;
}

function getToday(){
	var now = new Date()
	var day = ("0" + now.getDate()).slice(-2);
	var month = ("0" + (now.getMonth() + 1)).slice(-2);
	
	var today = now.getFullYear()+"-"+(month)+"-"+(day) ;

	return today
}

function deleteOrderItem(idOrdemItem, productName){
	if (confirm("Are you to drop the item with {Id:" + idOrdemItem + ", Product Name:" + productName +"}?")){
		var table = $('#table-items').DataTable();
		i = 0;
		table.rows().every(function(){
			row = this.data()
			if(idOrdemItem === parseInt(row[0])){
				table.row(i).remove().draw()
				
				if($("#orderId").val() != ""){
					
					console.log($("#orderId").val())
		
					request = $.ajax({
						url: order_service_url + "/delete-order-item-by/id/" +  $("#orderId").val() + "/" + idOrdemItem,
						type: 'DELETE',
						dataType: 'json',
						data: "",
						headers: { 'Authorization': sessionStorage.getItem("token") },
						crossDomain: true
					});
				
					request.done(function(data) {		
						console.log(data);
					});
				
					request.fail(function(data) {
						console.log(data);
					});	
				}
				
				return
			}
			i++;
		});
	}
}

function getLastItemId(){
	
	lastOrderItemId = 0;
	
	var table = $('#table-items').DataTable();
	
	if (table.rows().count() > 0){
		table.rows().every(function(){
			var orderItem = this.data();
			var orderItemId = parseInt(orderItem[0])
			
			if(orderItemId > lastOrderItemId){
				lastOrderItemId = orderItemId
			}	
		});
	}
	
	return lastOrderItemId + 1
}
