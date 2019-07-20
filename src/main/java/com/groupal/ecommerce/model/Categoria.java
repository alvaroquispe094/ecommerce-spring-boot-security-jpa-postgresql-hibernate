package com.groupal.ecommerce.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "categoriaIdSeq", sequenceName = "categoria_id_seq", allocationSize=1)
public class Categoria {
	
   
	@Id  
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "categoriaIdSeq")
	private Integer id;
	private String nombre;
    private Boolean activo;
    
  
    public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Boolean getActivo() {
		return activo;
	}
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

    
    
    
}