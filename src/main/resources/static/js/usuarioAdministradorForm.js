$(document).ready(function() {
   
	$("#usuarioAdministradorForm").validate({
		  rules: {
			    username: {
			      required: true,
//			      username:true,
			      remote: {
			        url: "/existAdmin",
			        type: "post",
			        data: {
				          username: function() { 
				              return $("#username").val();
				          },
				          id: function() { 
				              return $("#id").val();
				          },
			          }
					
			        }
			      }
			    },
		messages: {
			username: {
				required: "This field is required",
	            remote: "El usuario ya existe!!"
	        }
	    }	  
	});
	
});