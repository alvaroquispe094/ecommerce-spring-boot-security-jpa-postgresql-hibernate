package com.groupal.ecommerce.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "destacadoIdSeq", sequenceName = "destacado_id_seq", allocationSize=1)
public class Destacado {
	
	@Id  
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "destacadoIdSeq")
	private Integer id;

	@ManyToOne (fetch = FetchType.LAZY, optional = false)
	@JoinColumn( name="producto_id")
    private Producto producto;
	private Boolean activo;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
    
	}
	
	public Boolean getActivo() {
		return activo;
	}
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	

}
