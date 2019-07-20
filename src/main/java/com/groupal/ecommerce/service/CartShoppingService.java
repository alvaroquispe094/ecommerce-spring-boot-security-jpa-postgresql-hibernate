package com.groupal.ecommerce.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.groupal.ecommerce.model.Producto;
import com.groupal.ecommerce.repository.CategoriaRepository;
import com.groupal.ecommerce.repository.ProductoRepository;
import com.groupal.ecommerce.resources.Utils;
import com.querydsl.core.types.dsl.BooleanExpression;

@Service
public class CartShoppingService {
	
	@Autowired
    private ProductoRepository productoRepository;
	@Autowired
    private CategoriaRepository categoriaRepository;
	
	
	public Iterable<Producto> listAllProductos() {
        return productoRepository.findAll();
    }
	
	public Iterable<Producto> listAllProductos(Sort sort) {
        return productoRepository.findAll(sort);
    }
	
	public Page<Producto> listAllProductosPagination(BooleanExpression predicate, Pageable pageable) {
        return productoRepository.findAll(predicate, pageable);
    }
  
    public Producto getProductoById(Integer id) {
        return productoRepository.findOne(id);
    }

   
    public Producto saveProducto(Integer id, String nombre,String descripcion,Integer stock,Double precio,String imagen,Integer idCategoria, Boolean activo) {
    	
    	Producto producto = null;
		if (id!=null){
			producto = productoRepository.findOne(id);
		}else{
			producto = new Producto();
		}

		producto.setNombre(nombre);
		producto.setDescripcion(descripcion);
		producto.setStock(stock);
		producto.setPrecio(precio);
		producto.setImagen(imagen);
		producto.setCategoria(categoriaRepository.findOne(idCategoria));
		producto.setActivo(activo);producto.setDescripcion(descripcion);
		
		productoRepository.save(producto);

		return producto;
    }
    
    public Producto searchProductInCart(int idProducto, HttpServletRequest request) {
        Producto p = null;
//        List<Producto> myCart = Utils.getCartInSession(request);
        for (Producto producto : Utils.myProducts) {
            if (producto.getId() == idProducto) {
                p = producto;
                break;
            }
        }
        return p;
    }

    public double getTotalCartShopping(HttpServletRequest request) {
//    	List<Producto> myCart = Utils.getCartInSession(request);
    	 double total = 0 ;
         for(Producto producto : Utils.myProducts){
         	total += producto.getPrecio() * producto.getCantidad();
         }	
         
         return total;
    }
  
    public void deleteProducto(Integer id) {
    	productoRepository.delete(id);
    }


	public void cambiarEstadoProducto(Integer idCarrera, boolean estado) {
		Producto producto = productoRepository.findOne(idCarrera);
		producto.setActivo(estado);
		
		productoRepository.save(producto);
	}
    
    
    
}
