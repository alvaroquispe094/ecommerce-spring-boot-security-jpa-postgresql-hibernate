var logged=true;
$(document).ready(function(){
	$("#continue").on("click", function () {
		$("#loading").fadeIn();
		$("#loading").fadeOut(5000);
	});
	
	
	$("#btnIngresado").on('click', function(){
		console.log("entro en el bind");
//		console.log("usuario1212: "+usuario);
		
		$.ajax({
			type: "GET",
			url: 'cart/isLogin',
			async: false,
			contentType: "application/x-www-form-urlencoded;charset=ISO-8859-1",
			dataType: "json",
			success: function(data){
				
				console.log("mensaje: "+data.logged);
//				alert("asfasdgadg");
				if(data.logged =="false"){
					$("#login").modal('show');
					logged = false;
				}
			
				
			},
			 error: function(XMLHttpRequest, textStatus, errorThrown) { 
                 alert("Status: " + textStatus); alert("Error: " + errorThrown); 
             } 
		});
		if(logged == false){
			return false;
		}else{
			return true;
		}

	});
})

