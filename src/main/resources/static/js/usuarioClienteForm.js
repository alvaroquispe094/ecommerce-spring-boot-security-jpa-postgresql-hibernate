$(document).ready(function() {
   
	$("#usuarioClienteForm").validate();
	
	$("#fecha").datepicker({
		format: 'dd/mm/yyyy',
		autoclose: true,
		language: 'es'
		
		});
	
//	$("#usuarioClienterForm").validate({
//		  rules: {
//			    username: {
//			      required: true,
////			      username:true,
//			      remote: {
//			        url: "/exist",
//			        type: "post",
//			        data: {
//				          username: function() { 
//				              return $("#username").val();
//				          },
//				          id: function() { 
//				              return $("#id").val();
//				          },
//			          }
//					
//			        }
//			      }
//			    },
//		messages: {
//			username: {
//				required: "This field is required",
//	            remote: "El usuario ya existe!!"
//	        }
//	    }	  
//	});
	
});