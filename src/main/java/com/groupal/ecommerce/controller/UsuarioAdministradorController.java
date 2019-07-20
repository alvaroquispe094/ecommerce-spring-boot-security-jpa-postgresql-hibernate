package com.groupal.ecommerce.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.groupal.ecommerce.model.QUsuarioAdministrador;
import com.groupal.ecommerce.model.Rol;
import com.groupal.ecommerce.model.UsuarioAdministrador;
import com.groupal.ecommerce.service.UsuarioAdministradorService;
import com.querydsl.core.types.dsl.BooleanExpression;

@Controller
public class UsuarioAdministradorController {

	 @Autowired
	 private UsuarioAdministradorService usuarioAdministradorService;
	 

	 @RequestMapping(value="/administrativo/administradores", method=RequestMethod.GET)
	    protected ModelAndView administrarUsuarioAdministradores(Principal principal, @PageableDefault(size = 30) Pageable pageable,
				 @RequestParam (defaultValue="",required=false) String nombre, @RequestParam (defaultValue="",required=false) String mail) {
			 	
			 
			 Map<String,Object> modelo = new HashMap<String, Object>();

			 BooleanExpression consulta = QUsuarioAdministrador.usuarioAdministrador.id.ne(0);
			 
			 if(nombre != null && !nombre.equals("")) {
				consulta = consulta.and(QUsuarioAdministrador.usuarioAdministrador.username.like("%" + nombre + "%"));
			 }	
			 
			 if(mail != null && !mail.equals("")) {
					consulta = consulta.and(QUsuarioAdministrador.usuarioAdministrador.mail.like("%" + mail + "%"));
				 }	
			 
			 Page<UsuarioAdministrador> page = usuarioAdministradorService.listAllUsuarioAdministradoresPagination(consulta, pageable);

		     modelo.put("usuarioAdministradores", page);
		     modelo.put("nombre", nombre);
		     modelo.put("mail", mail);

		     //DATOS PAGINACION
			 modelo.put("page", page);
			 modelo.put("url", "administrarUsuarioAdministradores");
			 modelo.put("cantidad", page.getTotalElements());
			 modelo.put("query", "&nombre="+nombre+"&mail="+mail);	
			 return new ModelAndView("abmUsuarioAdministrador","modelo",modelo);	    
	 }
	 
	 @RequestMapping(value="/administrativo/administrador/nuevo", method=RequestMethod.GET)
	 public ModelAndView populateNuevoUsuarioAdministrador() {
		 Map<String, Object> modelo = new HashMap<String, Object>();
	
		 modelo.put("usuarioAdministrador", new UsuarioAdministrador());
		 		 
		 return new ModelAndView("usuarioAdministradorForm","modelo",modelo);
		}
	 
	 @RequestMapping(value="/administrativo/administrador/editar", method=RequestMethod.GET)
		public ModelAndView editarUsuarioAdministrador(@RequestParam (value="id") Integer idUsuario, Principal principal) {
		 	Map<String, Object> modelo = new HashMap<String, Object>();

			UsuarioAdministrador usuario = usuarioAdministradorService.getUsuarioAdministradorById(idUsuario);
			modelo.put("usuario", usuario);

			return new ModelAndView("usuarioAdministradorForm","modelo",modelo);
		}
	 
	 
	 @RequestMapping(value = "/administrativo/administrador/guardar", method = RequestMethod.POST)
	 protected ModelAndView guardarUsuarioAdministrador(@RequestParam Integer id, @RequestParam String username, 
			@RequestParam String password, @RequestParam (required=false) String mail,
    		@RequestParam(required=false, defaultValue="true") Boolean activo, Principal principal){
		 
		 usuarioAdministradorService.saveUsuarioAdministrador(id, username, password, mail, Rol.ROL_ADMINISTRADOR, activo);
		 System.out.println("save usuario Administrador");
	
		 return new ModelAndView(new RedirectView("/administrativo/administradores?message=El usuario administrador ha sido dado de alta"));
    }
	 
	 @RequestMapping(value="/administrativo/administrador/activar", method=RequestMethod.GET)
		public ModelAndView activarUsuario(@RequestParam (value="id") Integer idUsuario, Principal principal) {
			usuarioAdministradorService.cambiarEstadoUsuario(idUsuario, true);
			return new ModelAndView(new RedirectView("/administrativo/administradores?message=El usuario ha sido activado"));
		}
	    
    @RequestMapping(value="/administrativo/administrador/desactivar", method=RequestMethod.GET)
	public ModelAndView desactivarUsuario(@RequestParam (value="id") Integer idUsuario, Principal principal) {
		usuarioAdministradorService.cambiarEstadoUsuario(idUsuario, false);
		return new ModelAndView(new RedirectView("/administrativo/administradores?message=El usuario ha sido desactivado"));
	}  
	
    @RequestMapping(value = "/existAdmin", method = RequestMethod.POST)
	 @ResponseBody
	 public boolean isBrandNameExists(HttpServletResponse response,
	         @RequestParam String username, @RequestParam(required=false) Integer id) throws IOException {
		 	
		 
		 BooleanExpression consulta3 = QUsuarioAdministrador.usuarioAdministrador.username.eq(username);		 
		 UsuarioAdministrador usuario = usuarioAdministradorService.getUsuarioAdministradorById(consulta3);
		 
		 Boolean validar;
		 
		 if(usuario == null) {
			 validar =true;
		 }else {
			 if(id==null) {
				 validar=false;
			 }else {
				 validar=true;
			 }
		 }
		 
	     return validar;
	 }
	
}