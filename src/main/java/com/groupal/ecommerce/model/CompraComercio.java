package com.groupal.ecommerce.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@SequenceGenerator(name = "compraComercioIdSeq", sequenceName = "compra_comercio_id_seq", allocationSize=1)
@Table(name="compra_comercio")
public class CompraComercio {
	
	@Id  
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "compraComercioIdSeq")
	private Integer id;
	@ManyToOne (cascade = CascadeType.ALL)
	@JoinColumn( name="usuario_cliente_id")
    private UsuarioCliente usuarioCliente;
	@ManyToOne (cascade = CascadeType.ALL)
	@JoinColumn( name="estado_id")
    private Estado estado;
	private Date fecha;
	private Double total;
	private Boolean activo;
	
 
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public UsuarioCliente getUsuarioCliente() {
		return usuarioCliente;
	}
	public void setUsuarioCliente(UsuarioCliente usuarioCliente) {
		this.usuarioCliente = usuarioCliente;
	}
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Boolean getActivo() {
		return activo;
	}
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
}
