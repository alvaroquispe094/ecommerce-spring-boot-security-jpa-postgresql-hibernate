package com.groupal.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.groupal.ecommerce.model.Estado;
import com.groupal.ecommerce.repository.EstadoRepository;
import com.querydsl.core.types.dsl.BooleanExpression;

@Service
public class EstadoService {
	
	@Autowired
    private EstadoRepository estadoRepository;
	
	public Iterable<Estado> listAllEstados() {
        return estadoRepository.findAll();
    }

	public Iterable<Estado> listAllEstados(Sort sort) {
        return estadoRepository.findAll(sort);
    }
	
	public Page<Estado> listAllEstadosPagination(BooleanExpression predicate, Pageable pageable) {
        return estadoRepository.findAll(predicate, pageable);
    }
	
    public Estado getEstadoById(Integer id) {
        return estadoRepository.findOne(id);
    }
    
    public Estado getEstadoById(BooleanExpression predicate) {
        return estadoRepository.findOne(predicate);
    }
    

   
    public Estado saveEstado(Integer id, String nombre, Boolean activo) {
    	
    	Estado estado = null;
		if (id!=null){
			estado = estadoRepository.getOne(id);
		}else{
			estado = new Estado();
		}

		estado.setNombre(nombre);
		
		estadoRepository.save(estado);

		return estado;
    }

  
    public void deleteEstado(Integer id) {
    	estadoRepository.delete(id);
    }
	
	
}
