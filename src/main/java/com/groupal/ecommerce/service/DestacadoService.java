package com.groupal.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.groupal.ecommerce.model.Destacado;
import com.groupal.ecommerce.repository.DestacadoRepository;
import com.groupal.ecommerce.repository.ProductoRepository;
import com.querydsl.core.types.dsl.BooleanExpression;

@Service
public class DestacadoService {
	
	@Autowired
    private DestacadoRepository destacadoRepository;
	@Autowired
	private ProductoRepository productoRepository;
	
	
	public Iterable<Destacado> listAllDestacados() {
        return destacadoRepository.findAll();
    }
	
	public Iterable<Destacado> listAllDestacados(Sort sort) {
        return destacadoRepository.findAll(sort);
    }
	
	public Page<Destacado> listAllDestacadosPagination(BooleanExpression predicate, Pageable pageable) {
        return destacadoRepository.findAll(predicate, pageable);
    }
  
    public Destacado getDestacadoById(Integer id) {
        return destacadoRepository.findOne(id);
    }

   
    public Destacado saveDestacado(Integer id, Integer idProducto, Boolean activo) {
    	
    	Destacado destacado = null;
		if (id!=null){
			destacado = destacadoRepository.findOne(id);
		}else{
			destacado = new Destacado();
		}
		
		destacado.setProducto(productoRepository.findOne(idProducto));
		destacado.setActivo(activo);
		destacadoRepository.save(destacado);

		return destacado;
    }

  
    public void deleteDestacado(Integer id) {
    	destacadoRepository.delete(id);
    }


	public void cambiarEstadoDestacado(Integer idCarrera, boolean estado) {
		Destacado destacado = destacadoRepository.findOne(idCarrera);
		destacado.setActivo(estado);
		
		destacadoRepository.save(destacado);
	}
    
    
    
}
