package com.groupal.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.groupal.ecommerce.model.Categoria;
import com.groupal.ecommerce.repository.CategoriaRepository;
import com.querydsl.core.types.dsl.BooleanExpression;

@Service
public class CategoriaService {
	
	@Autowired
    private CategoriaRepository categoriaRepository;
	
	public Iterable<Categoria> listAllCategorias() {
        return categoriaRepository.findAll();
    }

	public Iterable<Categoria> listAllCategorias(Sort sort) {
        return categoriaRepository.findAll(sort);
    }
	
	public Page<Categoria> listAllCategoriasPagination(BooleanExpression predicate, Pageable pageable) {
        return categoriaRepository.findAll(predicate, pageable);
    }
	
  
    public Categoria getCategoriaById(Integer id) {
        return categoriaRepository.getOne(id);
    }
    
    public Categoria getCategoriaById(BooleanExpression predicate) {
        return categoriaRepository.findOne(predicate);
    }
    

   
    public Categoria saveCategoria(Integer id, String nombre, Boolean activo) {
    	
    	Categoria categoria = null;
		if (id!=null){
			categoria = categoriaRepository.getOne(id);
		}else{
			categoria = new Categoria();
		}

		categoria.setNombre(nombre);
		categoria.setActivo(activo);
		
		categoriaRepository.save(categoria);

		return categoria;
    }

  
    public void deleteCategoria(Integer id) {
    	categoriaRepository.delete(id);
    }

	public void cambiarEstadoCategoria(Integer idCategoria, Boolean estado ) {
		Categoria categoria = categoriaRepository.findOne(idCategoria);
		categoria.setActivo(estado);
		categoriaRepository.save(categoria);		
	}
	
	
}
