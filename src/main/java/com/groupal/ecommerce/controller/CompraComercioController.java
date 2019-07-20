package com.groupal.ecommerce.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.groupal.ecommerce.model.CompraComercio;
import com.groupal.ecommerce.model.Estado;
import com.groupal.ecommerce.model.QCompraComercio;
import com.groupal.ecommerce.model.UsuarioCliente;
import com.groupal.ecommerce.service.CompraComercioService;
import com.groupal.ecommerce.service.EstadoService;
import com.groupal.ecommerce.service.UsuarioClienteService;
import com.querydsl.core.types.dsl.BooleanExpression;

@Controller
public class CompraComercioController {
	
	
	@Autowired
	private CompraComercioService compraComercioService;
	
	@Autowired
	private UsuarioClienteService usuarioClienteService;
	
	@Autowired
	private EstadoService estadoService;
	 
	
	 @RequestMapping(value="/administrativo/compraComercios", method=RequestMethod.GET)
	 protected ModelAndView administrarCompraComercios(Principal principal, @PageableDefault(size = 30, sort="fecha") Pageable pageable,
			 @RequestParam (defaultValue="",required=false) String nombre) { 	
		 
		 Map<String,Object> modelo = new HashMap<String, Object>();
		 
		 BooleanExpression consulta = QCompraComercio.compraComercio.id.ne(0);
		 
		 if(nombre != null && !nombre.equals("")) {
			 consulta = consulta.and(QCompraComercio.compraComercio.usuarioCliente.nombre.like("%" + nombre + "%"));
		 }	
	
		 Page<CompraComercio> page = compraComercioService.listAllCompraComerciosPagination(consulta, pageable);
		 
		 
		
		 modelo.put("compraComercios", page);
	     modelo.put("nombre", nombre);		
	    

	     //DATOS PAGINACION
		 modelo.put("page", page);
		 modelo.put("url", "/administrativo/compraComercios");
		 modelo.put("cantidad", page.getTotalElements());
		 modelo.put("query", "&nombre="+nombre);	
		 return new ModelAndView("abmCompraComercio","modelo",modelo);
	 }
	 
	 @RequestMapping(value="/administrativo/compraComercio/nuevo", method=RequestMethod.GET)
		public ModelAndView populateNuevoCompraComercio() {
		 Map<String, Object> modelo = new HashMap<String, Object>();
		 
		 modelo.put("compraComercio", new CompraComercio());
		 modelo.put("usuarioCliente", new UsuarioCliente());
	
		 //listado categorias ordenado
		 Sort sort = new Sort(Sort.Direction.ASC, "nombre");
		 modelo.put("usuarioClientes", usuarioClienteService.listAllUsuarioClientes(sort));
		 			 
		 
		 return new ModelAndView("compraComercioForm","modelo",modelo);
		}
	 
	 @RequestMapping(value="/administrativo/compraComercio/editar", method=RequestMethod.GET)
		public ModelAndView editarCompraComercio(@RequestParam (value="id") Integer idcompraComercio, Principal principal) {
		 	Map<String, Object> modelo = new HashMap<String, Object>();

		 	CompraComercio compraComercio = compraComercioService.getCompraComercioById(idcompraComercio);
		 
		 	//usuarioCliente Seleccionado
			modelo.put("usuarioCliente", compraComercio.getUsuarioCliente());
		 	
		 	//listado categorias ordenado
		 	Sort sort = new Sort(Sort.Direction.ASC, "nombre");
			 modelo.put("usuarioClientes", usuarioClienteService.listAllUsuarioClientes(sort));
		 	
			modelo.put("compraComercio", compraComercio);
			
			return new ModelAndView("compraComercioForm","modelo",modelo);
		}
	 
	 
	 @RequestMapping(value ="/administrativo/compraComercio/guardar", method = RequestMethod.POST)
	 protected ModelAndView guardarCompraComercio(@RequestParam(required=false) Integer id, @RequestParam(value="usuarioCliente") Integer idUsuarioCliente, 
			 								@RequestParam Integer idEstado,@RequestParam String fecha,@RequestParam Double total, 
			 								@RequestParam(required=false, defaultValue="true") Boolean activo, Principal principal){
			 								
		 
		 compraComercioService.saveCompraComercio(id, idUsuarioCliente, idEstado,fecha,total,activo);
		
		 
		 return new ModelAndView(new RedirectView("/administrativo/compraComercios?message=La compra ha sido guardada"));
    }
	 
	
	 @RequestMapping(value="/administrativo/compraComercio/activar", method=RequestMethod.GET)
		public ModelAndView activarCompraComercio(@RequestParam (value="id") Integer idcompraComercio, Principal principal) {
			compraComercioService.cambiarEstadoCompraComercio(idcompraComercio, true);
			return new ModelAndView(new RedirectView("/administrativo/compraComercios?message=La compra ha sido activada"));
		}
	    
	    @RequestMapping(value="/administrativo/compraComercio/desactivar", method=RequestMethod.GET)
		public ModelAndView desactivarCompraComercio(@RequestParam (value="id") Integer idcompraComercio, Principal principal) {
			compraComercioService.cambiarEstadoCompraComercio(idcompraComercio, false);
			return new ModelAndView(new RedirectView("/administrativo/compraComercios?message=La compra ha sido desactivadoa"));
		} 
	
	
	    @RequestMapping(value="/administrativo/compraComercio/cambiarEstado", method=RequestMethod.GET)
		public ModelAndView cambiarEstadoVenta(@RequestParam (value="id") Integer idCompraComercio, 
												     @RequestParam Integer idEstado, Principal principal) {
//			Usuario usuario = usuarioServiceManager.obtenerUsuario(principal);
//			Turno turno = turnoManager.get(idTurno);
			Estado estado =	estadoService.getEstadoById(idEstado);
	    	compraComercioService.cambiarEstadoVenta(idCompraComercio, idEstado);
			return new ModelAndView(new RedirectView("/administrativo/compraComercios?message=Se ha cambiado el estado de la compra a "+estado.getNombre()));
			
		}
	    
}