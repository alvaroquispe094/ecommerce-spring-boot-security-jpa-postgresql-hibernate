package com.groupal.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.groupal.ecommerce.model.CompraComercioFacturacion;


@Repository
public interface CompraComercioFacturacionRepository extends JpaRepository<CompraComercioFacturacion,Integer>,QueryDslPredicateExecutor<CompraComercioFacturacion>{
	
}	