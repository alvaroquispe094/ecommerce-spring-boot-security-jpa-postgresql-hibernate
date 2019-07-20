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

import com.groupal.ecommerce.model.Categoria;
import com.groupal.ecommerce.model.Producto;
import com.groupal.ecommerce.model.QProducto;
import com.groupal.ecommerce.service.CategoriaService;
import com.groupal.ecommerce.service.ProductoService;
import com.querydsl.core.types.dsl.BooleanExpression;

@Controller
public class ProductoController {
	
	
	@Autowired
	private ProductoService productoService;
	@Autowired
	private CategoriaService categoriaService;
	 
	
	 @RequestMapping(value="/administrativo/productos", method=RequestMethod.GET)
	 protected ModelAndView administrarProductos(Principal principal, @PageableDefault(size = 30, sort="nombre") Pageable pageable,
			 @RequestParam (defaultValue="",required=false) String nombre, @RequestParam (value="categoria",required=false) Integer idCategoria) { 	
		 
		 Map<String,Object> modelo = new HashMap<String, Object>();
		 
		 BooleanExpression consulta = QProducto.producto.id.ne(0);
		 
		 if(nombre != null && !nombre.equals("")) {
			 consulta = consulta.and(QProducto.producto.nombre.like("%" + nombre + "%"));
		 }	
		 if(idCategoria != null) {
				consulta = consulta.and(QProducto.producto.categoria.id.eq(idCategoria));
			 }

		 Page<Producto> page = productoService.listAllProductosPagination(consulta, pageable);
		 
		 
		 modelo.put("categorias", categoriaService.listAllCategorias());
		 modelo.put("productos", page);
	     modelo.put("nombre", nombre);		
	     
	     modelo.put("idCategoria",idCategoria);

	     //DATOS PAGINACION
		 modelo.put("page", page);
		 modelo.put("url", "administrarProductos");
		 modelo.put("cantidad", page.getTotalElements());
		 modelo.put("query", "&nombre="+nombre);	
		 return new ModelAndView("abmProducto","modelo",modelo);
	 }
	 
	 @RequestMapping(value="/administrativo/producto/nuevo", method=RequestMethod.GET)
		public ModelAndView populateNuevoProducto() {
		 Map<String, Object> modelo = new HashMap<String, Object>();
	
		 modelo.put("producto", new Producto());
		 modelo.put("categoria", new Categoria());
		 
		 Sort sort = new Sort(Sort.Direction.ASC, "nombre");
		 modelo.put("categorias", categoriaService.listAllCategorias(sort));
		 
		 return new ModelAndView("productoForm","modelo",modelo);
		}
	 
	 @RequestMapping(value="/administrativo/producto/editar", method=RequestMethod.GET)
		public ModelAndView editarProducto(@RequestParam (value="id") Integer idproducto, Principal principal) {
		 	Map<String, Object> modelo = new HashMap<String, Object>();

		 	Producto producto = productoService.getProductoById(idproducto);
		 	
		 	//categoria Seleccionada
		 	modelo.put("categoria", categoriaService.getCategoriaById(producto.getCategoria().getId()));
		 	
		 	//listado categorias ordenado
		 	Sort sort = new Sort(Sort.Direction.ASC, "nombre");
			 modelo.put("categorias", categoriaService.listAllCategorias(sort));
		 	
			
			modelo.put("producto", producto);
			
			return new ModelAndView("productoForm","modelo",modelo);
		}
	 
	 
	 @RequestMapping(value ="/administrativo/producto/guardar", method = RequestMethod.POST)
	 protected ModelAndView guardarProducto(@RequestParam(required=false) Integer id, @RequestParam String nombre, 
			 								@RequestParam String descripcion,@RequestParam Integer stock,
			 								@RequestParam Double precio, @RequestParam String imagen,  @RequestParam(value="categoria") Integer idCategoria,
			 					    		@RequestParam(required=false, defaultValue="true") Boolean activo, Principal principal){
			 								
		 
		 productoService.saveProducto(id,nombre,descripcion,stock,precio,imagen, idCategoria,activo);
		
		 
		 return new ModelAndView(new RedirectView("/administrativo/productos?message=El producto ha sido guardado"));
    }
	 
	
	 @RequestMapping(value="/administrativo/producto/activar", method=RequestMethod.GET)
		public ModelAndView activarProducto(@RequestParam (value="id") Integer idproducto, Principal principal) {
			productoService.cambiarEstadoProducto(idproducto, true);
			return new ModelAndView(new RedirectView("/administrativo/productos?message=El producto ha sido activado"));
		}
	    
	    @RequestMapping(value="/administrativo/producto/desactivar", method=RequestMethod.GET)
		public ModelAndView desactivarProducto(@RequestParam (value="id") Integer idproducto, Principal principal) {
			productoService.cambiarEstadoProducto(idproducto, false);
			return new ModelAndView(new RedirectView("/administrativo/productos?message=El producto ha sido desactivado"));
		} 
	
	
}