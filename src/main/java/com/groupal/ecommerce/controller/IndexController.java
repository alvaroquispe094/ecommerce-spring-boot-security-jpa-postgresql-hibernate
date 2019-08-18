package com.groupal.ecommerce.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.groupal.ecommerce.model.Destacado;
import com.groupal.ecommerce.model.QUsuarioAdministrador;
import com.groupal.ecommerce.model.QUsuarioCliente;
import com.groupal.ecommerce.model.Rol;
import com.groupal.ecommerce.model.UsuarioAdministrador;
import com.groupal.ecommerce.model.UsuarioCliente;
import com.groupal.ecommerce.service.DestacadoService;
import com.groupal.ecommerce.service.UsuarioAdministradorService;
import com.groupal.ecommerce.service.UsuarioClienteService;
import com.querydsl.core.types.dsl.BooleanExpression;



@Controller
public class IndexController {
	
	@Autowired
	private DestacadoService destacadoService;
	
	@Autowired
	private UsuarioClienteService usuarioClienteService;
	
	@Autowired
	 private UsuarioAdministradorService usuarioAdministradorService;
	
	@RequestMapping(value="/")
	 protected ModelAndView administrarProductos(Principal principal) { 	
		 
		 Map<String,Object> modelo = new HashMap<String, Object>();
		
		 Iterable<Destacado> destacados = destacadoService.listAllDestacados();
		 modelo.put("destacados", destacados);
	    
		 return new ModelAndView("index","modelo",modelo);
	 }
	
	@RequestMapping(value="login")
    public String login(){
        return "login";
    }
	
	@RequestMapping(value="/registrar")
	 public ModelAndView registrar() {
		 Map<String, Object> modelo = new HashMap<String, Object>();
	
		 modelo.put("usuarioCliente", new UsuarioCliente());
		 		 
		 return new ModelAndView("register","modelo",modelo);
	}
	
	@RequestMapping(value = "/guardar", method = RequestMethod.POST)
	protected ModelAndView guardarUsuarioCliente(@RequestParam Integer id,@RequestParam String nombre, 
				@RequestParam String apellido, @RequestParam String username, 
				@RequestParam String password,@RequestParam Integer documento, @RequestParam String telefono,
				@RequestParam String direccion, @RequestParam String fecha, @RequestParam (required=false) String email,
				@RequestParam(required=false, defaultValue="true") Boolean activo, Principal principal){
		 
		usuarioClienteService.saveUsuarioCliente(id, nombre, apellido, username, password, documento, telefono, direccion, fecha, email, Rol.ROL_CLIENTE, activo);
		System.out.println("save usuario");
	
		return new ModelAndView(new RedirectView("/registrar?message=Felicitaciones, se ha registrado con exito!!!"));
   }
	
	@RequestMapping(value = "/exist", method = RequestMethod.POST)
	 @ResponseBody
	 public boolean isBrandNameExists(HttpServletResponse response,
	         @RequestParam String username, @RequestParam(required=false) Integer id) throws IOException {
		 	
		 
		 BooleanExpression consulta = QUsuarioAdministrador.usuarioAdministrador.username.eq(username);		 
		 UsuarioAdministrador usuarioAdminnistrador = usuarioAdministradorService.getUsuarioAdministradorById(consulta);
		 
		 BooleanExpression consulta2 = QUsuarioCliente.usuarioCliente.username.eq(username);	
		 UsuarioCliente usuarioCliente = usuarioClienteService.getUsuarioClienteById(consulta2);
		 
		 Boolean validar;
		 
		 if(usuarioAdminnistrador == null && usuarioCliente == null) {
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

	@RequestMapping(value="logout")
    public String logout(){
        return "login";
    }
	
	@RequestMapping("contacto")
    public String contacto() {
        return "contacto";
    }
	
	@RequestMapping("catalogo")
    public String catalogo() {
        return "catalogo";
    }
	
	@RequestMapping("/403")
    public String deniedPage() {
        return "403";
    }
	
	
 
}
