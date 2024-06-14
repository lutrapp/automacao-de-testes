// ------------------------------- Open Customer Edition ------------------------------- //
function loadProfile(){

	sessionStorage.setItem("UserId", sessionStorage.getItem("user.id"));
	window.location.href="users-edit"	
}