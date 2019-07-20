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
import com.groupal.ecommerce.model.CompraComercioProducto;
import com.groupal.ecommerce.model.Producto;
import com.groupal.ecommerce.model.QCompraComercioProducto;
import com.groupal.ecommerce.service.CompraComercioProductoService;
import com.groupal.ecommerce.service.CompraComercioService;
import com.groupal.ecommerce.service.ProductoService;
import com.querydsl.core.types.dsl.BooleanExpression;

@Controller
public class CompraComercioProductoController {
	
	 @Autowired
	 private CompraComercioProductoService compraComercioProductoService;
	 
	 @Autowired
	 private CompraComercioService compraComercioService;
	 
	 @Autowired
	 private ProductoService productoService;
	
	@RequestMapping(value="/administrativo/compraComercio/productos", method=RequestMethod.GET)
	 protected ModelAndView administrarCompraComercioProductos(Principal principal, @RequestParam( value = "id") Integer idCompraComercio, 
			 @PageableDefault(size = 30, sort="producto.nombre") Pageable pageable,
			 @RequestParam (defaultValue="",required=false) String nombre) { 	
		 
		 Map<String,Object> modelo = new HashMap<String, Object>();
		 
		 BooleanExpression consulta = QCompraComercioProducto.compraComercioProducto.id.ne(0);
		 consulta = consulta.and(QCompraComercioProducto.compraComercioProducto.compraComercio.id.eq(idCompraComercio));
		 
		 if(nombre != null && !nombre.equals("")) {
			 consulta = consulta.and(QCompraComercioProducto.compraComercioProducto.producto.nombre.like("%" + nombre + "%"));
		 }	

//		 Sort sort = new Sort(Sort.Direction.ASC, "nombre");
//		 Pageable pageRequest = new PageRequest(0, 10, sort);
//		 Pageable page = new Pageable(10,"id");
		 
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
	 
	 @RequestMapping(value="/administrativo/compraComercio/producto/nuevo", method=RequestMethod.GET)
		public ModelAndView populateNuevaCompraComercioProducto(@RequestParam Integer idCompraComercio) {
		 Map<String, Object> modelo = new HashMap<String, Object>();
		 modelo.put("compraComercioProducto", new CompraComercioProducto());
		 modelo.put("producto", new Producto());
		 
		 modelo.put("compraComercio", compraComercioService.getCompraComercioById(idCompraComercio));
		 Sort sort = new Sort(Sort.Direction.ASC, "nombre");
		 modelo.put("productos", productoService.listAllProductos(sort));	
		 		 
		 return new ModelAndView("compraComercioProductoForm","modelo",modelo);
		}
	 
	 @RequestMapping(value="/administrativo/compraComercio/producto/editar", method=RequestMethod.GET)
		public ModelAndView editarCompraComercioProducto(@RequestParam (value="id") Integer idCompraComercioProductoId, Principal principal) {
		 	Map<String, Object> modelo = new HashMap<String, Object>();


			CompraComercioProducto compraComercioProducto = compraComercioProductoService.getCompraComercioProductoById(idCompraComercioProductoId);
			
			//compraComercio Seleccionada
			modelo.put("producto", productoService.getProductoById(compraComercioProducto.getCompraComercio().getId()));
			
			
			modelo.put("compraComercioProducto", compraComercioProducto);
			modelo.put("compraComercio", compraComercioService.getCompraComercioById(compraComercioProducto.getCompraComercio().getId()));
			modelo.put("productos", productoService.listAllProductos());

			return new ModelAndView("compraComercioProductoForm","modelo",modelo);
		}
	 
	 
	 @RequestMapping(value = "/administrativo/compraComercio/producto/guardar", method = RequestMethod.POST)
	 protected ModelAndView guardarCompraComercioProducto(@RequestParam(required=false) Integer id, @RequestParam Integer idCompraComercio ,
			 									  @RequestParam(value="producto") Integer idProducto, @RequestParam Integer cantidad,
			 									  @RequestParam Double total, @RequestParam(required=false, defaultValue="true") Boolean activo, Principal principal){

		 compraComercioProductoService.saveCompraComercioProducto(id, idCompraComercio, idProducto, cantidad, total, activo);
		
		 
		 return new ModelAndView(new RedirectView("/administrativo/compraComercio/productos?id="+idCompraComercio+ "&message=El producto ha sido guardado"));
   }
	 
	
	 @RequestMapping(value="/administrativo/compraComercio/producto/activar", method=RequestMethod.GET)
		public ModelAndView activarCompraComercioProducto(@RequestParam (value="id") Integer idCompraComercioProducto, Principal principal) {
			compraComercioProductoService.cambiarEstadoCompraComercioProducto(idCompraComercioProducto, true);
			CompraComercioProducto compraComercioProducto = compraComercioProductoService.getCompraComercioProductoById(idCompraComercioProducto);
			CompraComercio compraComercio = compraComercioService.getCompraComercioById(compraComercioProducto.getCompraComercio().getId());
			return new ModelAndView(new RedirectView("/administrativo/compraComercio/productos?id="+compraComercio.getId()+ "&message=La compraComercioProducto ha sido activada"));
		}
	    
	    @RequestMapping(value="/administrativo/compraComercio/producto/desactivar", method=RequestMethod.GET)
		public ModelAndView desactivarCompraComercioProducto(@RequestParam (value="id") Integer idCompraComercioProducto, Principal principal) {
			compraComercioProductoService.cambiarEstadoCompraComercioProducto(idCompraComercioProducto, false);
			CompraComercioProducto compraComercioProducto = compraComercioProductoService.getCompraComercioProductoById(idCompraComercioProducto);
			CompraComercio compraComercio = compraComercioService.getCompraComercioById(compraComercioProducto.getCompraComercio().getId());
			return new ModelAndView(new RedirectView("/administrativo/compraComercio/productos?id="+compraComercio.getId()+ "&message=La compraComercioProducto ha sido desactivada"));
		} 
	

}
