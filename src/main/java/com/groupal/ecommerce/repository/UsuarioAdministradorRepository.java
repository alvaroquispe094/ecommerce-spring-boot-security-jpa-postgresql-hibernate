package com.groupal.ecommerce.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.groupal.ecommerce.model.UsuarioAdministrador;

@Transactional
@Repository
public interface UsuarioAdministradorRepository extends JpaRepository<UsuarioAdministrador,Integer>, QueryDslPredicateExecutor<UsuarioAdministrador>{
	
	public UsuarioAdministrador findByUsername(String username);
	

    
    
}

