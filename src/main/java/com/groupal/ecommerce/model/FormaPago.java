package com.groupal.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@SequenceGenerator(name = "formaPagoIdSeq", sequenceName = "forma_pago_id_seq", allocationSize=1)
@Table(name="forma_pago")
public class FormaPago {

	public static final Integer MERCADO_PAGO = new Integer(1);
	public static final Integer TRANSFERENCIA_BANCARIA = new Integer(2);
	public static final Integer EFECTIVO = new Integer(3);
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "formaPagoIdSeq")
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
