package com.groupal.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@SequenceGenerator(name = "formaEnvioIdSeq", sequenceName = "forma_envio_id_seq", allocationSize=1)
@Table(name="forma_envio")
public class FormaEnvio {

	public static final Integer LO_VOY_A_RETIRAR = new Integer(1);
	public static final Integer ENVIO_A_DOMICILIO = new Integer(2);
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "formaEnvioIdSeq")
	private Integer id;
	private String nombre;
	private Boolean activo;
	
	@Column(name = "id")
	public Integer getId() {
		return id;
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
