// ------------------------------- Get Customers ------------------------------- //
$(document).ready(function() {

	$("#button-forms-customer-create").click(function(event) {
		event.preventDefault();
		location.href = "/users-edit";
	});
	
	loadUsers();
		
});

// ------------------------------- Load Customers ------------------------------- //
function loadUsers(){
	
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
              'pdf',
              'excel',
              'print',
              'csv']
	});
	
	table.clear();
	
	var request = $.ajax({
		url: user_service_url + "/all",
		type: 'get',
		headers: { 'Authorization': sessionStorage.getItem("token") },
		dataType: 'json',
		crossDomain: true
	});

	request.done(function(users) {
		// Request success
		$("#alert-success").show()
		$("#alert-error").hide()
		
		for(var i=0; i < users.length; i++){
		
			table.row.add([
				users[i].id,
				users[i].name,
				users[i].username,
				'<button onclick="javascript:editUser(' + users[i].id + ')" type="button" class="btn btn-secondary" id="button-forms-customer-update"><div class="icon"><i class="bx bxs-edit-alt"></i></div></button>',			
				'<button onclick="javascript:deleteUser(' + users[i].id + ',\'' + users[i].name + '\');" type="button" class="btn btn-secondary" id="button-customer-delete"><div class="icon"><i class="bx bxs-eraser"></i></div></button>',				
				""
			]);
		}
		
		table.draw();
		
		console.log(users);

	});

	request.fail(function(data) {
		// Request failed
		$("#alert-success").hide()
		$("#alert-error").show()
			
		console.log(data);
	});
}

// ------------------------------- Edit Customer ------------------------------- //
function editUser(userId){
	
	if(userId != null){
		sessionStorage.setItem("UserId", userId);
		window.location.href="/users-edit";
	}
	
}

// ------------------------------- Delete Customer ------------------------------- //
function deleteUser(userId, userName){
	if (confirm("Are you to drop the user with {Id:" + userId + ", Name:" + userName +"}?")){

		var request = $.ajax({
			url: user_service_url + "/delete-by/id/" + userId,
			type: 'delete',
			headers: { 'Authorization': sessionStorage.getItem("token") },
			dataType: 'json',
			crossDomain: true
		});
		
		request.done(function(users) {
			// Request success
			$("#alert-error-deleting").hide();
			$("#alert-success-deleting").show().delay(300);

			loadUsers();
		});

		request.fail(function(data) {
			// Request failed
			$("#alert-error-deleting").show()
			$("#alert-success-deleting").hide()
				
			console.log(data);
		});

	}
}
