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

import com.groupal.ecommerce.model.Producto;
import com.groupal.ecommerce.model.QProducto;
import com.groupal.ecommerce.service.CategoriaService;
import com.groupal.ecommerce.service.ProductoService;
import com.querydsl.core.types.dsl.BooleanExpression;

@Controller
public class CatalogoController {
	
	@Autowired
	private ProductoService productoService;
	@Autowired
	private CategoriaService categoriaService;
	
	 @RequestMapping(value="/catalogo", method=RequestMethod.GET)
	 protected ModelAndView administrarCatalogo(Principal principal, @PageableDefault(size = 3, sort="nombre") Pageable pageable,
			 @RequestParam (value="categoria",required=false) Integer idCategoria, @RequestParam (value="color",required=false) Integer idColor, 
			 @RequestParam(required=false) Integer max, @RequestParam(required=false) Integer min) { 	
		 
		 Map<String,Object> modelo = new HashMap<String, Object>();
		 
		 BooleanExpression consulta = QProducto.producto.id.ne(0);
		 
		 
		 if(idCategoria != null) {
				consulta = consulta.and(QProducto.producto.categoria.id.eq(idCategoria));
			 }

		 if(min != null ) {
				consulta = consulta.and(QProducto.producto.precio.goe(min));
			 }	
		 
		 if(max != null ) {
				consulta = consulta.and(QProducto.producto.precio.loe(max));
			 }	


		 Page<Producto> page = productoService.listAllProductosPagination(consulta, pageable);
		 
		 
		 modelo.put("categorias", categoriaService.listAllCategorias());
		 modelo.put("productos", page);
	    		
		 
	     
	     modelo.put("idCategoria",idCategoria);
	     modelo.put("minimo",min);
	     modelo.put("maximo",max);

	     //DATOS PAGINACION
		 modelo.put("page", page);
		 modelo.put("url", "/catalogo");
		 modelo.put("cantidad", page.getTotalElements());
		 modelo.put("query", "&categoria="+(idCategoria != null ? idCategoria : "")+"&colores="+(idColor != null ? idColor : "")+"&max="+(max != null ? max : "")+"&min="+(min != null ? min : ""));	
		 return new ModelAndView("catalogo","modelo",modelo);
	 }
}
