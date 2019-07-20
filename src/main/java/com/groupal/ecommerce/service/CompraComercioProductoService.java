package com.groupal.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.groupal.ecommerce.model.CompraComercioProducto;
import com.groupal.ecommerce.repository.CompraComercioProductoRepository;
import com.querydsl.core.types.dsl.BooleanExpression;

@Service
public class CompraComercioProductoService {
	
	@Autowired
    private CompraComercioProductoRepository compraComercioProductoRepository;
	
	@Autowired
    private CompraComercioService compraComercioService;
	
	@Autowired
    private ProductoService productoService;
	
	
	public Iterable<CompraComercioProducto> listAllCompraComercioProductos() {
        return compraComercioProductoRepository.findAll();
    }
	
	public Iterable<CompraComercioProducto> listAllCompraComercioProductos(Sort sort) {
        return compraComercioProductoRepository.findAll(sort);
    }
	
	public Page<CompraComercioProducto> listAllCompraComercioProductosPagination(BooleanExpression predicate, Pageable pageable) {
        return compraComercioProductoRepository.findAll(predicate, pageable);
    }
	
	public Iterable<CompraComercioProducto> listAllCompraComercioProductosPagination(BooleanExpression predicate) {
        return compraComercioProductoRepository.findAll(predicate);
    }
  
    public CompraComercioProducto getCompraComercioProductoById(Integer id) {
        return compraComercioProductoRepository.findOne(id);
    }

   
    public CompraComercioProducto saveCompraComercioProducto(Integer id, Integer compraComercioId, Integer producto_id, Integer cantidad, Double total, Boolean activo) {
    	
    	CompraComercioProducto compraComercioProducto = null;
		if (id!=null){
			compraComercioProducto = compraComercioProductoRepository.findOne(id);
		}else{
			compraComercioProducto = new CompraComercioProducto();
		}

		compraComercioProducto.setCompraComercio(compraComercioService.getCompraComercioById(compraComercioId));
		compraComercioProducto.setProducto(productoService.getProductoById(producto_id));
		compraComercioProducto.setCantidad(cantidad);
		compraComercioProducto.setTotal(total);
		compraComercioProducto.setActivo(activo);
		
		compraComercioProductoRepository.save(compraComercioProducto);

		return compraComercioProducto;
    }

  
    public void deleteCompraComercioProducto(Integer id) {
    	compraComercioProductoRepository.delete(id);
    }


	public void cambiarEstadoCompraComercioProducto(Integer idCarrera, boolean estado) {
		CompraComercioProducto compraComercioProducto = compraComercioProductoRepository.findOne(idCarrera);
		compraComercioProducto.setActivo(estado);
		
		compraComercioProductoRepository.save(compraComercioProducto);
	}

}
