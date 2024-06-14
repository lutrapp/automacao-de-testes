/**
 * 
 */
$(document).ready(function() {

	if (isAuthenticated() && window.location.href.includes("login")) {
		window.location.href = "/"
	}

	$("#logginButton").click(function(event) {
		event.preventDefault();

		if (isValid($("#form-login"))) {
			$("#form-detail").removeClass("was-validated")
			authenticateUser(event, $("#yourUsername").val(), $("#yourPassword").val())
		}
	});
});

function isValid(formToValidate) {
	//formToValidate.each(function() {
	var results = $(".invalid-feedback:visible")
	return results.length == 0
	//});
}

function isAuthenticated() {
	if (sessionStorage.getItem("token") != null
		&& sessionStorage.getItem("token").trim() != "") {
		return true;
	}

	return false;
}

function userInfo(event, username) {

	request = $.ajax({
		async: false,
		url: user_service_url + "/find-by/user-name/",
		headers: { 'Authorization': sessionStorage.getItem("token") },
		type: 'GET',
		dataType: 'json',
		data: { "username": username }
	});

	request.done(function(user) {
		sessionStorage.setItem("user.id", user.id);
		$.cookie('user.id', user.id)
		console.log(user.id);
	});

	request.fail(function(data) {
		console.log(data);
		$("#alert-error").show()
	});
}

function authenticateUser(event, username, userpass) {

	event.preventDefault();

	body = {
		"username": username,
		"password": userpass
	};

	request = $.ajax({
		async: false,
		url: user_service_auth_url + "/auth",
		type: 'POST',
		dataType: 'json',
		contentType: "application/json; charset=utf-8",
		data: JSON.stringify(body),
	});

	request.done(function(auth) {

		sessionStorage.setItem("token", auth.token);
		$.cookie('token', auth.token);

		console.log(auth.token);

		userInfo(event, username);

		window.location.href = "/"

	});

	request.fail(function(data) {
		console.log(data);
		$("#alert-error").show()

		return false;
	});
}

function logout(event) {
	if (event != null) event.preventDefault();

	sessionStorage.setItem("token", "");
	sessionStorage.setItem("user.id", "");
	$.cookie('token', "");
	$.cookie('user.id', "");
	location.href = "/logout";
}