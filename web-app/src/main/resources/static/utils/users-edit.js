// ------------------------------- Default ------------------------------- //
$(document).ready(function() {
	
	$("#form-customer").submit(function(event){
		event.preventDefault();
		
		$("#alert-success-loading").hide()
		$("#alert-error-loading").hide()
	
		userId = $("#userId").val()
		userName = $("#userName1").val()
		userName2 = $("#userName2").val()
		userPassword = $("#userPassword1").val()
		userRoles = $("#userRoles").val()
		userStatus = $("#userStatus").checked ? true : false
		
		createOrUpdateUser(event, userId, 
								  userName,
								  userName2,
								  userPassword,
								  userRoles,
								  userStatus);
							
	});
	
	// If has customer value change form to edit mode	
	userId = sessionStorage.getItem("UserId");
	
	if(userId != null){
		loadUser(userId);
		sessionStorage.removeItem("UserId");
	} else {
		$("#userName1").val("")
		$("#userPassword1").val("")
	}

});

// ------------------------------- Check form mode (Update / Insert) ------------------------------- // 

function checkMode(){
	if ($("#userId").val() != '')
		$("#submit").html("Update User")
	else
		$("#submit").html("Create User")
}

// ------------------------------- Load Customer ------------------------------- //
function loadUser(userId){
	
	request = $.ajax({
		url: user_service_url + "/find-by/id/" + userId,
		type: 'GET',
		dataType: 'json',
		data: "",
		headers: {'Authorization': sessionStorage.getItem("token")},					 
		crossDomain: true
	});
	
	request.done(function(user){
		
		console.log(user)
		console.log(user.status)
		
		$("#userId").val(user.id)
		$("#userName1").val(user.name)
		$("#userName2").val(user.username)
		$("#userPassword1").val(user.userpass)
		$("#userRoles").val(user.roles)
		$("#userPasswordConfirmation").val(user.userpass)
		
		if(user.status == true)
		 	$("#userStatus").attr("checked", "checked")
		else
			$("#userStatus").removeAttr("checked")

		checkMode();
		
		$("#alert-success-loading").show()
		$("#alert-error-loading").hide()	
				 
		console.log(user);
	});
	
	request.fail(function(data){
		$("#alert-success-loading").hide()
		$("#alert-error-loading").show()
				 
		
		console.log(data);
	});
	
}
 
// ------------------------------- Create Customer ------------------------------- // 
function createOrUpdateUser(event, userId, 
							   userName,
							   userName2,
							   userPassword,
							   userRoles,
							   userStatus){

	body = {
		id: userId == '' ? 0 : userId,
		name: userName,
		username: userName2,
		userpass: userPassword,
		roles: userRoles,
		status: userStatus
	};
	
	console.log(body);
	
	request = $.ajax({
		url: user_service_url + "/save",
		type: 'PUT',
		dataType: 'json',
		data: JSON.stringify(body),
		contentType:"application/json; charset=utf-8",
		headers: {'Authorization': sessionStorage.getItem("token")},					 
		crossDomain: true
	});
	
	request.done(function(user){
		$("#userId").val(user.id)
		$("#alert-success").show()
		$("#alert-error").hide()
		
		checkMode();	
				 
		console.log(user);
	});
	
	request.fail(function(data){
		$("#alert-success").hide()
		$("#alert-error").show()
		
		console.log(data);
	});
	
	return true; 
}

 