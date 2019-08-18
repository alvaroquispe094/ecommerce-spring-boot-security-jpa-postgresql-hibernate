$(document).ready(function(){
	updateCartItemCount()
	
	
    setTimeout(function() {
        $("#mensajes").fadeOut(1500);
    },3000);
	
	$("#usuarioAdministradorForm").validate({
		  rules: {
			    username: {
			      required: true,
//			      username:true,
			      remote: {
			        url: "/exist",
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
	
	$("#usuarioClienteForm").validate({
		  rules: {
			    username: {
			      required: true,
//			      username:true,
			      remote: {
			        url: "/exist",
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
	
	$("#registerForm").validate({
		  rules: {
			    username: {
			      required: true,
//			      username:true,
			      remote: {
			        url: "/exist",
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


function updateCartItemCount()
{
	$.ajax ({ 
		url: '/cart/items/count', 
		type: "GET", 
		dataType: "json",
		contentType: "application/json",
		complete: function(responseData, status, xhttp){ 
			$('#cart-item-count').text('('+responseData.responseJSON.count+')');
		}
	});
}