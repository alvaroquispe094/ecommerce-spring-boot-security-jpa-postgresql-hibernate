package com.groupal.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.groupal.ecommerce.model.Destacado;

@Repository
public interface DestacadoRepository extends JpaRepository<Destacado,Integer>,QueryDslPredicateExecutor<Destacado>{
	
}