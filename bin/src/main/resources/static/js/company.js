$( document ).ready(function() {
	
	alert("kkkkkkkkkkkkkkkk")
	
	// SUBMIT FORM
    $("#customerForm").submit(function(event) {
		// Prevent the form from submitting via the browser.
		event.preventDefault();
		login();
	});
    
    
    function login(){
    	// PREPARE FORM DATA
    	var formData = {
    		email : $("#email").val(),
    		password :  $("#password").val()
    	}
    	
    	// DO POST
    	$.ajax({
			type : "POST",
			contentType : "application/json",
			url : window.location + "user/login",
			data : JSON.stringify(formData),
			dataType : 'json',
			success : function(result) {
				if(result.message == "Success") {
					window.localStorage.setItem('userToken', result.data.userToken);
					getHome(result.data.userToken);
					
				}else{
					$("#postResultDiv").html("Error");
				}
				console.log(result);
			},
			error : function(e) {
				alert("Error!")
				console.log("ERROR: ", e);
			}
		});
    	
    	// Reset FormData after Posting
    	resetData();

    }
    
    function getHome(userToken) {
    	var token = {userToken : userToken}
    	
    	$.ajax({
			type : "GET",
			url : window.location + "home",
			headers : {"X-Requested-Token": userToken},
			success : function(result) {
				if(result.message == "Success") {
				}else{
					$("html").html(result);
				}
				console.log(result);
			},
			error : function(e) {
				alert("Error!")
				console.log("ERROR: ", e);
			}
		});
    }
    
    
    function resetData(){
    	$("#email").val("");
    	$("#password").val("");
    }
});

