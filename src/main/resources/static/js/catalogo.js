
var selected = false
$(document).ready(function(){
	
	
	$('#categoria').click(function() {
		
		if (selected == false){
			$('#categoria option:selected').attr("selected","selected");
			selected = true;
		}else{
			$("#categoria option:selected").removeAttr("selected")
			selected = false;
		}
	});
	
	$('#colores').click(function() {
		
		if (selected == false){
			$('#colores option:selected').attr("selected","selected");
			selected = true;
		}else{
			$("#colores option:selected").removeAttr("selected")
			selected = false;
		}
	});
	
	
	$("#reset").on("click", function () {
	    $('select').prop('selectedIndex',0);
	});
	
	
	
	$("select").on("change", function(){
		$("#materia option").remove();
		var idCategoria = $("#categoria option:selected").val();
		var idColor = $("#colores option:selected").val();
		var min =  $("#min").val();
		var max =  $("#max").val();
		$.ajax({
			type: "POST",
			url: 'guardarAltaInicioCarrera.htm',
			contentType: "application/x-www-form-urlencoded;charset=ISO-8859-1",
			data: 
				{	
					categoria:idCategoria,
					colores:idColor,
					minimo:min,
					maximo:max,
					

				},
			dataType: "json",
			success: function(data){
				console.log(data);
				var selector = '.alert-success';
				if (data.status == 'error')
					selector = '.alert-danger';

				
				try{
					window.location.href="administrarAltaInicioCarreras.htm?mensaje=La carrera iniciada a sido dada de alta";
				}catch(e){}	
				
			}
		});
			
	});
		
		$("#botonNuevoInicioCarrera").unbind('click');
		$("#botonNuevoInicioCarrera").bind('click', function(){
			console.log("entro en el bind");
			if ($("#usuarioEmbajadorAltaCarreraForm").valid()){
//				$('#nuevoTurno').modal('hide');
				console.log("asdasdasdas");
				console.log("id: "+$("#id").val());
				console.log("usuario: "+$("#id").val());
				console.log("escuela: " +$("#escuela").val());
				console.log("carrera: ",$("#carrera").val());
				console.log("inicio: "+$("#fechaInicio").val());
				console.log("fin: "+$("#fechaFin").val());
				console.log("myarray: "+_alumnosSeleccionados);
				
				$.ajax({
					type: "POST",
					url: 'guardarAltaInicioCarrera.htm',
					contentType: "application/x-www-form-urlencoded;charset=ISO-8859-1",
					data: 
						{	
							id:$("#id").val(),
							idUsuario:idUsuario,
							escuela:$("#escuela").val(),
							idCarrera:$("#carrera").val(),
							fechaInicio:$("#fechaInicio").val(),
							fechaFin:$("#fechaFin").val(),
							myArray: _alumnosSeleccionados,
							total: total

						},
					dataType: "json",
					success: function(data){
						console.log(data);
						var selector = '.alert-success';
						if (data.status == 'error')
							selector = '.alert-danger';

						
						try{
	    					window.location.href="administrarAltaInicioCarreras.htm?mensaje=La carrera iniciada a sido dada de alta";
						}catch(e){}	
						
					}
				});
			} 
		});

		
	});

