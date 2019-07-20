$(document).ready(function() {
   
	$("#usuarioClienteForm").validate();
	
	$("#fecha").datepicker({
		format: 'dd/mm/yyyy',
		autoclose: true,
		language: 'es'
		
		});
	
});