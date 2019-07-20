package com.groupal.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "rolIdSeq", sequenceName = "rol_id_seq", allocationSize=1)
public class Rol {

	public static final Integer ROL_ADMINISTRADOR = new Integer(1);
	public static final Integer ROL_CLIENTE = new Integer(2);
	public static final Integer ROL_VENDEDOR = new Integer(3);
	
	
	@Id  
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "rolIdSeq")
	private Integer id;
	private String nombre;
	private Boolean activo;
	
	public Integer getId() {
        return this.id;
    }
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "nombre")
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
