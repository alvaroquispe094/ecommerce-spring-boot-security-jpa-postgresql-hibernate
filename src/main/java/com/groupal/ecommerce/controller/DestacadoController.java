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

import com.groupal.ecommerce.model.Destacado;
import com.groupal.ecommerce.model.Producto;
import com.groupal.ecommerce.model.QDestacado;
import com.groupal.ecommerce.service.DestacadoService;
import com.groupal.ecommerce.service.ProductoService;
import com.querydsl.core.types.dsl.BooleanExpression;

@Controller
public class DestacadoController {
	
	
	@Autowired
	private DestacadoService destacadoService;
	@Autowired
	private ProductoService productoService;
	 
	
	 @RequestMapping(value="/administrativo/destacados", method=RequestMethod.GET)
	 protected ModelAndView administrarDestacados(Principal principal, @PageableDefault(size = 30, sort="producto.nombre") Pageable pageable,
			 @RequestParam (defaultValue="",required=false) String nombre) { 	
		 
		 Map<String,Object> modelo = new HashMap<String, Object>();
		 
		 BooleanExpression consulta = QDestacado.destacado.id.ne(0);
		 
		 if(nombre != null && !nombre.equals("")) {
			 consulta = consulta.and(QDestacado.destacado.producto.nombre.like("%" + nombre + "%"));
		 }	
		
//		 Sort sort = new Sort(Sort.Direction.ASC, "nombre");
//		 Pageable pageRequest = new PageRequest(0, 10, sort);
//		 Pageable page = new Pageable(10,"id");
		 
		 Page<Destacado> page = destacadoService.listAllDestacadosPagination(consulta, pageable);
		 
		 
		 
		 modelo.put("productos", productoService.listAllProductos());
		 modelo.put("destacados", page);
	     modelo.put("nombre", nombre);		
	     
	     

	     //DATOS PAGINACION
		 modelo.put("page", page);
		 modelo.put("url", "administrarDestacados");
		 modelo.put("cantidad", page.getTotalElements());
		 modelo.put("query", "&nombre="+nombre);	
		 return new ModelAndView("abmDestacado","modelo",modelo);
	 }
	 
	 @RequestMapping(value="/administrativo/destacado/nuevo", method=RequestMethod.GET)
		public ModelAndView populateNuevoDestacado() {
		 Map<String, Object> modelo = new HashMap<String, Object>();
	
		 modelo.put("destacado", new Destacado());
		 modelo.put("producto", new Producto());
		 
		 Sort sort = new Sort(Sort.Direction.ASC, "nombre");
		 modelo.put("productos", productoService.listAllProductos(sort));
		 
		 return new ModelAndView("destacadoForm","modelo",modelo);
		}
	 
	 @RequestMapping(value="/administrativo/destacado/editar", method=RequestMethod.GET)
		public ModelAndView editarDestacado(@RequestParam (value="id") Integer idDestacado, Principal principal) {
		 	Map<String, Object> modelo = new HashMap<String, Object>();

		 	Destacado destacado = destacadoService.getDestacadoById(idDestacado);
		 	
		 	//producto Seleccionada
		 	modelo.put("producto", productoService.getProductoById(destacado.getProducto().getId()));
		 	
		 	//listado categorias ordenado
		 	Sort sort = new Sort(Sort.Direction.ASC, "nombre");
			 modelo.put("productos", productoService.listAllProductos(sort));
		 	
			
			modelo.put("destacado", destacado);
			
			return new ModelAndView("destacadoForm","modelo",modelo);
		}
	 
	 
	 @RequestMapping(value ="/administrativo/destacado/guardar", method = RequestMethod.POST)
	 protected ModelAndView guardarDestacado(@RequestParam(required=false) Integer id,
			 								@RequestParam(value="producto") Integer idProducto,
			 					    		@RequestParam(required=false, defaultValue="true") Boolean activo, Principal principal){
			 								
		 
		 destacadoService.saveDestacado(id, idProducto,activo);
		
		 
		 return new ModelAndView(new RedirectView("/administrativo/destacados?message=El destacado ha sido guardado"));
    }
	 
	
	 @RequestMapping(value="/administrativo/destacado/activar", method=RequestMethod.GET)
		public ModelAndView activarDestacado(@RequestParam (value="id") Integer iddestacado, Principal principal) {
			destacadoService.cambiarEstadoDestacado(iddestacado, true);
			return new ModelAndView(new RedirectView("/administrativo/destacados?message=El destacado ha sido activado"));
		}
	    
	    @RequestMapping(value="/administrativo/destacado/desactivar", method=RequestMethod.GET)
		public ModelAndView desactivarDestacado(@RequestParam (value="id") Integer iddestacado, Principal principal) {
			destacadoService.cambiarEstadoDestacado(iddestacado, false);
			return new ModelAndView(new RedirectView("/administrativo/destacados?message=El destacado ha sido desactivado"));
		} 
	
	
}