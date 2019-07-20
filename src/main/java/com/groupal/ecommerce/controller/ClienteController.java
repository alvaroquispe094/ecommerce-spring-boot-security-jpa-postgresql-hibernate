package com.groupal.ecommerce.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.groupal.ecommerce.model.CompraComercio;
import com.groupal.ecommerce.model.CompraComercioProducto;
import com.groupal.ecommerce.model.Estado;
import com.groupal.ecommerce.model.QCompraComercio;
import com.groupal.ecommerce.model.QCompraComercioProducto;
import com.groupal.ecommerce.model.QEstado;
import com.groupal.ecommerce.model.Rol;
import com.groupal.ecommerce.model.UsuarioCliente;
import com.groupal.ecommerce.service.CompraComercioProductoService;
import com.groupal.ecommerce.service.CompraComercioService;
import com.groupal.ecommerce.service.EstadoService;
import com.groupal.ecommerce.service.UsuarioClienteService;
import com.querydsl.core.types.dsl.BooleanExpression;


@Controller
public class ClienteController {
		
	@Autowired
	UsuarioClienteService usuarioClienteService;
	
	@Autowired
	EstadoService estadoService;

	@Autowired
	CompraComercioService compraComercioService;
	
	@Autowired
	CompraComercioProductoService compraComercioProductoService;
	
	@RequestMapping(value="/cliente/compras", method=RequestMethod.GET)
	 protected ModelAndView administrarProductos(Principal principal, @PageableDefault(size = 30, sort="fecha", direction = Direction.DESC) Pageable pageable,
			 @RequestParam (defaultValue="", required=false) String nombre, @RequestParam (defaultValue="",value="estado", required=false) Integer idEstado) { 	
		 
		 Map<String,Object> modelo = new HashMap<String, Object>();
		
		 //obtengo el usuario logueado
		 UsuarioCliente usuarioCliente = usuarioClienteService.obtenerUsuarioCliente(principal);
		 modelo.put("usuarioCliente", usuarioCliente);
		
		 BooleanExpression consulta = QCompraComercio.compraComercio.id.ne(0);
		 
		 if(idEstado != null) {
			 consulta = consulta.and(QEstado.estado.id.eq(idEstado));
		 }
		 
		 Page<CompraComercio> page = compraComercioService.listAllCompraComerciosPagination(consulta, pageable);
		 modelo.put("compraComercios", page);
		 
		 Iterable<Estado> estados = estadoService.listAllEstados();
		 modelo.put("estados", estados);
		 
		 modelo.put("nombre", nombre);
		 modelo.put("estado", idEstado);
		 
		 //DATOS PAGINACION
		 modelo.put("page", page);
		 modelo.put("url", "/cliente/compras");
		 modelo.put("cantidad", page.getTotalElements());
		 modelo.put("query", "&estado="+(idEstado != null ? idEstado: ""));
		 return new ModelAndView("misCompras","modelo",modelo);
	 }
	
	@RequestMapping(value="/cliente/editar", method=RequestMethod.GET)
 	public ModelAndView editarUsuarioCliente(@RequestParam (value="id") Integer idUsuarioCliente, Principal principal) {
	 	Map<String, Object> modelo = new HashMap<String, Object>();

	 	UsuarioCliente usuarioCliente = usuarioClienteService.getUsuarioClienteById(idUsuarioCliente);
	 	modelo.put("usuarioCliente", usuarioCliente);

	 	return new ModelAndView("usuarioClienteForm","modelo",modelo);
	}
	
	@RequestMapping(value="/cliente/profile", method=RequestMethod.GET)
 	public ModelAndView eclienteProfile(Principal principal) {
	 	Map<String, Object> modelo = new HashMap<String, Object>();

	 	UsuarioCliente usuarioCliente = usuarioClienteService.obtenerUsuarioCliente(principal);
	 	modelo.put("usuarioCliente", usuarioCliente);

	 	return new ModelAndView("profile","modelo",modelo);
	}
	
	@RequestMapping(value="/registrar", method=RequestMethod.GET)
	 public ModelAndView populateNuevoUsuarioCliente() {
		 Map<String, Object> modelo = new HashMap<String, Object>();
	
		 modelo.put("usuarioCliente", new UsuarioCliente());
		 		 
		 return new ModelAndView("usuarioClienteForm","modelo",modelo);
		}
	 
	
	@RequestMapping(value = "/cliente/guardar", method = RequestMethod.POST)
	protected ModelAndView guardarUsuarioCliente(@RequestParam Integer id,@RequestParam String nombre, 
				@RequestParam String apellido, @RequestParam String username, 
				@RequestParam String password,@RequestParam Integer documento, @RequestParam String telefono,
				@RequestParam String direccion, @RequestParam String fecha, @RequestParam (required=false) String email,
				@RequestParam(required=false, defaultValue="true") Boolean activo, Principal principal){
		 
		usuarioClienteService.saveUsuarioCliente(id, nombre, apellido, username, password, documento, telefono, direccion, fecha, email, Rol.ROL_CLIENTE, activo);
		System.out.println("save usuario");
	
		return new ModelAndView(new RedirectView("/cliente/profile?message=Los datos han sido guardados"));
   }
	
//	@RequestMapping(value = "/cliente/compra/confirmar", method = RequestMethod.POST)
//	protected ModelAndView guardarCompraConfirmacion(@RequestParam Integer id,@RequestParam String nombre, 
//				@RequestParam String apellido, @RequestParam String username, 
//				@RequestParam String password,@RequestParam Integer documento, @RequestParam String telefono,
//				@RequestParam String direccion, @RequestParam String fecha, @RequestParam (required=false) String email,
//				@RequestParam(required=false, defaultValue="true") Boolean activo, Principal principal){
//		 
//		usuarioClienteService.saveUsuarioCliente(id, nombre, apellido, username, password, documento, telefono, direccion, fecha, email, Rol.ROL_CLIENTE, activo);
//		System.out.println("save usuario");
//	
//		return new ModelAndView(new RedirectView("/cliente/profile?message=Los datos han sido guardados"));
//   }
	
	@RequestMapping(value="/cliente/compra/productos", method=RequestMethod.GET)
	 protected ModelAndView administrarCompraComercioProductos(Principal principal, @RequestParam( value = "id") Integer idCompraComercio, 
			 @PageableDefault(size = 30, sort="producto.nombre") Pageable pageable,
			 @RequestParam (defaultValue="",required=false) String nombre) { 	
		 
		 Map<String,Object> modelo = new HashMap<String, Object>();
		 
		 BooleanExpression consulta = QCompraComercioProducto.compraComercioProducto.id.ne(0);
		 consulta = consulta.and(QCompraComercioProducto.compraComercioProducto.compraComercio.id.eq(idCompraComercio));
		 
		 if(nombre != null && !nombre.equals("")) {
			 consulta = consulta.and(QCompraComercioProducto.compraComercioProducto.producto.nombre.like("%" + nombre + "%"));
		 }	


		 Page<CompraComercioProducto> page = compraComercioProductoService.listAllCompraComercioProductosPagination(consulta, pageable);
		 
	      modelo.put("compraComercioProductos", page);
	      modelo.put("compraComercio", compraComercioService.getCompraComercioById(idCompraComercio));
	      modelo.put("nombre", nombre);	

	      //DATOS PAGINACION
		  modelo.put("page", page);
		  modelo.put("url", "/administrativo/compraComercio/productos");
		  modelo.put("cantidad", page.getTotalElements());
		  modelo.put("query", "&nombre="+nombre);
		  return new ModelAndView("abmCompraComercioProducto","modelo",modelo);
	 }
	
	
}
