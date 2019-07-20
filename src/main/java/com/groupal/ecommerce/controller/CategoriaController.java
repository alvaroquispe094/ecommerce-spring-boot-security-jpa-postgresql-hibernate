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

import com.groupal.ecommerce.model.Categoria;
import com.groupal.ecommerce.model.QCategoria;
import com.groupal.ecommerce.service.CategoriaService;
import com.querydsl.core.types.dsl.BooleanExpression;

@Controller
public class CategoriaController {
		
	 @Autowired
	 private CategoriaService categoriaService;

	 @RequestMapping(value="/administrativo/categorias", method=RequestMethod.GET)
	    protected ModelAndView administrarCategorias(Principal principal, @PageableDefault(size = 30, sort="nombre") Pageable pageable,
				 @RequestParam (defaultValue="", required=false) String nombre, @RequestParam (defaultValue="",required=false) String mail) {
			 	
			 
			 Map<String,Object> modelo = new HashMap<String, Object>();

			 BooleanExpression consulta = QCategoria.categoria.id.ne(0);
			 
			 if(nombre != null && !nombre.equals("")) {
				consulta = consulta.and(QCategoria.categoria.nombre.like("%" + nombre + "%"));
			 }	
			 
			 
			 Page<Categoria> page = categoriaService.listAllCategoriasPagination(consulta, pageable);
			 
			
		     modelo.put("categorias", page);
		     
		     modelo.put("nombre", nombre);

		     //DATOS PAGINACION
			 modelo.put("page", page);
			 modelo.put("url", "administrarCategorias");
			 modelo.put("cantidad", page.getTotalElements());
			 modelo.put("query", "&nombre="+nombre+"&mail="+mail);	
			 return new ModelAndView("abmCategoria","modelo",modelo);	    
	 }
	 
	 @RequestMapping(value="/administrativo/categoria/nuevo", method=RequestMethod.GET)
	 public ModelAndView populateNuevaCategoria() {
		 Map<String, Object> modelo = new HashMap<String, Object>();
	
		 modelo.put("categoria", new Categoria());
		 		 
		 return new ModelAndView("categoriaForm","modelo",modelo);
		}
	 
	 @RequestMapping(value="/administrativo/categoria/editar", method=RequestMethod.GET)
		public ModelAndView editarCategoria(@RequestParam (value="id") Integer idCategoria, Principal principal) {
		 	Map<String, Object> modelo = new HashMap<String, Object>();

			Categoria categoria = categoriaService.getCategoriaById(idCategoria);
			modelo.put("categoria", categoria);

			return new ModelAndView("categoriaForm","modelo",modelo);
		}
	 
	 
	 @RequestMapping(value = "/administrativo/categoria/guardar", method = RequestMethod.POST)
	 protected ModelAndView guardarCategoria(@RequestParam Integer id, @RequestParam String nombre,
    		@RequestParam(required=false, defaultValue="true") Boolean activo, Principal principal){

		 categoriaService.saveCategoria(id, nombre, activo);
		 

		 return new ModelAndView(new RedirectView("/administrativo/categorias?message=La categoria ha sido guadada"));
    }
	 
	 @RequestMapping(value="/administrativo/categoria/activar", method=RequestMethod.GET)
		public ModelAndView activarCategoria(@RequestParam (value="id") Integer idCategoria, Principal principal) {
			categoriaService.cambiarEstadoCategoria(idCategoria, true);
			return new ModelAndView(new RedirectView("/administrativo/categorias?message=La categoria ha sido activada"));
		}
	    
    @RequestMapping(value="/administrativo/categoria/desactivar", method=RequestMethod.GET)
	public ModelAndView desactivarCategoria(@RequestParam (value="id") Integer idCategoria, Principal principal) {
		categoriaService.cambiarEstadoCategoria(idCategoria, false);
		return new ModelAndView(new RedirectView("/administrativo/categorias?message=La categoria ha sido desactivada"));
	}  
	
}