package com.groupal.ecommerce.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.groupal.ecommerce.model.UsuarioCliente;

@Transactional
@Repository
public interface UsuarioClienteRepository extends JpaRepository<UsuarioCliente,Integer>, QueryDslPredicateExecutor<UsuarioCliente>{
	
	public UsuarioCliente findByUsername(String username);
	

    
    
}

