package com.groupal.ecommerce.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.groupal.ecommerce.model.QUsuarioCliente;
import com.groupal.ecommerce.model.Rol;
import com.groupal.ecommerce.model.UsuarioCliente;
import com.groupal.ecommerce.service.UsuarioClienteService;
import com.querydsl.core.types.dsl.BooleanExpression;

@Controller
public class UsuarioClienteController {
	
	
	
	 @Autowired
	 private UsuarioClienteService usuarioClienteService;
	 
	 @RequestMapping(value="/administrativo/clientes", method=RequestMethod.GET)
	    protected ModelAndView administrarUsuarioClientes(Principal principal, @PageableDefault(size = 30) Pageable pageable,
				 @RequestParam (defaultValue="",required=false) String nombre, @RequestParam (defaultValue="",required=false) String mail) {
			 	
			 
			 Map<String,Object> modelo = new HashMap<String, Object>();

			 BooleanExpression consulta = QUsuarioCliente.usuarioCliente.id.ne(0);
			 
			 if(nombre != null && !nombre.equals("")) {
				consulta = consulta.and(QUsuarioCliente.usuarioCliente.username.like("%" + nombre + "%"));
			 }	
			 
			 if(mail != null && !mail.equals("")) {
					consulta = consulta.and(QUsuarioCliente.usuarioCliente.mail.like("%" + mail + "%"));
				 }	
			 
			 Page<UsuarioCliente> page = usuarioClienteService.listAllUsuarioClientesPagination(consulta, pageable);

		     modelo.put("usuarioClientes", page);
		     modelo.put("nombre", nombre);
		     modelo.put("mail", mail);

		     //DATOS PAGINACION
			 modelo.put("page", page);
			 modelo.put("url", "/administrativo/clientes");
			 modelo.put("cantidad", page.getTotalElements());
			 modelo.put("query", "&nombre="+nombre+"&mail="+mail);	
			 return new ModelAndView("abmUsuarioCliente","modelo",modelo);	    
	 }
	 
	 @RequestMapping(value="/administrativo/cliente/nuevo", method=RequestMethod.GET)
	 public ModelAndView populateNuevoUsuarioCliente() {
		 Map<String, Object> modelo = new HashMap<String, Object>();
	
		 modelo.put("usuarioCliente", new UsuarioCliente());
		 		 
		 return new ModelAndView("usuarioClienteForm","modelo",modelo);
		}
	 
	 @RequestMapping(value="/administrativo/cliente/editar", method=RequestMethod.GET)
		public ModelAndView editarUsuarioCliente(@RequestParam (value="id") Integer idUsuarioCliente, Principal principal) {
		 	Map<String, Object> modelo = new HashMap<String, Object>();

			UsuarioCliente usuarioCliente = usuarioClienteService.getUsuarioClienteById(idUsuarioCliente);
			modelo.put("usuarioCliente", usuarioCliente);

			return new ModelAndView("usuarioClienteForm","modelo",modelo);
		}
	 
	 
	 @RequestMapping(value = "/administrativo/cliente/guardar", method = RequestMethod.POST)
	 protected ModelAndView guardarUsuarioCliente(@RequestParam Integer id,@RequestParam String nombre, 
				@RequestParam String apellido, @RequestParam String username, 
			@RequestParam String password,@RequestParam Integer documento, @RequestParam String telefono,
			@RequestParam String direccion, @RequestParam String fecha, @RequestParam (required=false) String email,
    		@RequestParam(required=false, defaultValue="true") Boolean activo, Principal principal){
		 
		 usuarioClienteService.saveUsuarioCliente(id, nombre, apellido, username, password, documento, telefono, direccion, fecha, email, Rol.ROL_CLIENTE, activo);
		 System.out.println("save usuario");
	
		 return new ModelAndView(new RedirectView("/administrativo/clientes?message=El usuario cliente ha sido dado de alta"));
    }
	 
	 @RequestMapping(value="/administrativo/cliente/activar", method=RequestMethod.GET)
		public ModelAndView activarUsuarioCliente(@RequestParam (value="id") Integer idUsuarioCliente, Principal principal) {
			usuarioClienteService.cambiarEstadoUsuarioCliente(idUsuarioCliente, true);
			return new ModelAndView(new RedirectView("/administrativo/clientes?message=El usuario cliente ha sido activado"));
		}
	    
    @RequestMapping(value="/administrativo/cliente/desactivar", method=RequestMethod.GET)
	public ModelAndView desactivarUsuarioCliente(@RequestParam (value="id") Integer idUsuarioCliente, Principal principal) {
		usuarioClienteService.cambiarEstadoUsuarioCliente(idUsuarioCliente, false);
		return new ModelAndView(new RedirectView("/administrativo/clientes?message=El usuario cliente ha sido desactivado"));
	}  
	
//    @RequestMapping(value = "existAdmin", method = RequestMethod.POST)
//	 @ResponseBody
//	 public boolean isBrandNameExists(HttpServletResponse response,
//	         @RequestParam String username, @RequestParam(required=false) Integer id) throws IOException {
//		 	
//		 
//		 BooleanExpression consulta3 = QUsuarioCliente.usuarioCliente.username.eq(username);		 
//		 UsuarioCliente usuario = usuarioClienteService.getUsuarioClienteById(consulta3);
//		 
//		 Boolean validar;
//		 
//		 if(usuario == null) {
//			 validar =true;
//		 }else {
//			 if(id==null) {
//				 validar=false;
//			 }else {
//				 validar=true;
//			 }
//		 }
//		 
//	     return validar;
//	 }
	
}