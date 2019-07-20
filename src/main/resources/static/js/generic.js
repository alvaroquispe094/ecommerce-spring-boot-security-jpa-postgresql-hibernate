$(document).ready(function(){
	updateCartItemCount()
	
	
    setTimeout(function() {
        $("#mensajes").fadeOut(1500);
    },3000);
	
	
	
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