package com.groupal.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.groupal.ecommerce.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto,Integer>,QueryDslPredicateExecutor<Producto>{
	
}