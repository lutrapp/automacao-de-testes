var host = ""

user_service_auth_url  	= ""
user_service_url 	 	= ""
customer_service_url  	= ""
product_service_url  	= ""
company_service_url  	= ""
order_service_url 	 	= ""
env						= ""
port 					= String(window.location.port).substring(0, 2)

if(port.startsWith("80")) 		env = "tmp"
else if(port.startsWith("81")) 	env = "dev"
else if(port.startsWith("82")) 	env = "test"
else 							env = "prod"

if(window.location.hostname == "localhost"){
	host = "localhost"
		
	user_service_auth_url 	= "http://" + host + ":" + port + "00"
	user_service_url 		= "http://" + host + ":" + port + "00/api/v1/users";
	customer_service_url 	= "http://" + host + ":" + port + "01/api/v1/customers";
	product_service_url 	= "http://" + host + ":" + port + "02/api/v1/products";
	company_service_url 	= "http://" + host + ":" + port + "02/api/v1/companies";
	order_service_url 		= "http://" + host + ":" + port + "03/api/v1/orders";
	
}else{
		
	user_service_auth_url 	= "http://" + env + "-user-service:" 		+ port + "00"
	user_service_url 		= "http://" + env + "-user-service:" 		+ port + "00/api/v1/users";
	customer_service_url 	= "http://" + env + "-customer-service:" 	+ port + "01/api/v1/customers";
	product_service_url 	= "http://" + env + "-product-service:"  	+ port + "02/api/v1/products";
	company_service_url 	= "http://" + env + "-product-service:" 	+ port + "02/api/v1/companies";
	order_service_url 		= "http://" + env + "-order-service:"		+ port + "03/api/v1/orders";
}

console.log("User service auth url: " + user_service_auth_url)
console.log("User service url: " + user_service_url)
console.log("Customer service url: " + customer_service_url)
console.log("Product service url: " + product_service_url)
console.log("Company service url: " + company_service_url)
console.log("Order service url: " + order_service_url)
