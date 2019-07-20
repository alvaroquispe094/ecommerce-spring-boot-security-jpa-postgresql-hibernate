$(document).ready(function() {
   
	$("#compraComercioForm").validate();
	
	$("#fecha").datepicker({
		format: 'dd/mm/yyyy',
		autoclose: true,
		language: 'es'
		
		});
	
});