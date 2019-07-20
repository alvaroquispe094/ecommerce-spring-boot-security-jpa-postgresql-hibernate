package com.groupal.ecommerce.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.groupal.ecommerce.model.Destacado;
import com.groupal.ecommerce.service.DestacadoService;



@Controller
public class IndexController {
	
	@Autowired
	private DestacadoService destacadoService;
	
	@RequestMapping(value="/")
	 protected ModelAndView administrarProductos(Principal principal) { 	
		 
		 Map<String,Object> modelo = new HashMap<String, Object>();
		 
//		 BooleanExpression consulta = QDestacado.destacado.id.ne(0);
		 
		
//		 Sort sort = new Sort(Sort.Direction.ASC, "nombre");
//		 Pageable pageRequest = new PageRequest(0, 10, sort);
//		 Pageable page = new Pageable(10,"id");
		 
		 Iterable<Destacado> destacados = destacadoService.listAllDestacados();
		 modelo.put("destacados", destacados);
	    
		 return new ModelAndView("index","modelo",modelo);
	 }
	
	@RequestMapping(value="login")
    public String login(){
        return "login";
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
