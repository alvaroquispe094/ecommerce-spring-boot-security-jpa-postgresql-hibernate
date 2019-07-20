package com.groupal.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.groupal.ecommerce.model.FormaEnvio;
import com.groupal.ecommerce.repository.FormaEnvioRepository;
import com.querydsl.core.types.dsl.BooleanExpression;

@Service
public class FormaEnvioService {
	
	@Autowired
    private FormaEnvioRepository formaEnvioRepository;
	
	public Iterable<FormaEnvio> listAllFormaEnvios() {
        return formaEnvioRepository.findAll();
    }

	public Iterable<FormaEnvio> listAllFormaEnvios(Sort sort) {
        return formaEnvioRepository.findAll(sort);
    }
	
	public Page<FormaEnvio> listAllFormaEnviosPagination(BooleanExpression predicate, Pageable pageable) {
        return formaEnvioRepository.findAll(predicate, pageable);
    }
	
    public FormaEnvio getFormaEnvioById(Integer id) {
        return formaEnvioRepository.findOne(id);
    }
    
    public FormaEnvio getFormaEnvioById(BooleanExpression predicate) {
        return formaEnvioRepository.findOne(predicate);
    }
    

   
    public FormaEnvio saveFormaEnvio(Integer id, String nombre, Boolean activo) {
    	
    	FormaEnvio formaEnvio = null;
		if (id!=null){
			formaEnvio = formaEnvioRepository.getOne(id);
		}else{
			formaEnvio = new FormaEnvio();
		}

		formaEnvio.setNombre(nombre);
		
		formaEnvioRepository.save(formaEnvio);

		return formaEnvio;
    }

  
    public void deleteFormaEnvio(Integer id) {
    	formaEnvioRepository.delete(id);
    }
	
	
}
