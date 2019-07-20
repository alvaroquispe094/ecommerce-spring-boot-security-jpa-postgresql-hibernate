package com.groupal.ecommerce.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.groupal.ecommerce.model.Rol;

@Transactional
@Repository
public interface RolRepository extends JpaRepository<Rol,Integer>{
		
}