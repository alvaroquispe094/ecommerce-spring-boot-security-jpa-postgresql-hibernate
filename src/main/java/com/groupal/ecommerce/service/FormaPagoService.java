package com.groupal.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.groupal.ecommerce.model.FormaPago;
import com.groupal.ecommerce.repository.FormaPagoRepository;
import com.querydsl.core.types.dsl.BooleanExpression;

@Service
public class FormaPagoService {
	
	@Autowired
    private FormaPagoRepository formaPagoRepository;
	
	public Iterable<FormaPago> listAllFormaPagos() {
        return formaPagoRepository.findAll();
    }

	public Iterable<FormaPago> listAllFormaPagos(Sort sort) {
        return formaPagoRepository.findAll(sort);
    }
	
	public Page<FormaPago> listAllFormaPagosPagination(BooleanExpression predicate, Pageable pageable) {
        return formaPagoRepository.findAll(predicate, pageable);
    }
	
    public FormaPago getFormaPagoById(Integer id) {
        return formaPagoRepository.findOne(id);
    }
    
    public FormaPago getFormaPagoById(BooleanExpression predicate) {
        return formaPagoRepository.findOne(predicate);
    }
    

   
    public FormaPago saveFormaPago(Integer id, String nombre, Boolean activo) {
    	
    	FormaPago formaPago = null;
		if (id!=null){
			formaPago = formaPagoRepository.getOne(id);
		}else{
			formaPago = new FormaPago();
		}

		formaPago.setNombre(nombre);
		
		formaPagoRepository.save(formaPago);

		return formaPago;
    }

  
    public void deleteFormaPago(Integer id) {
    	formaPagoRepository.delete(id);
    }
	
	
}
