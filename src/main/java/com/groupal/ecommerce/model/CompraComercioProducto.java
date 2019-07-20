package com.groupal.ecommerce.model;

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
@SequenceGenerator(name = "compraComercioProductoIdSeq", sequenceName = "compra_comercio_producto_id_seq", allocationSize=1)
@Table(name="compra_comercio_producto")
public class CompraComercioProducto {
	
	@Id  
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "compraComercioProductoIdSeq")
	private Integer id;
	@ManyToOne (cascade = CascadeType.ALL)
	@JoinColumn( name="compra_comercio_id")
    private CompraComercio compraComercio;
	@ManyToOne (cascade = CascadeType.ALL)
	@JoinColumn( name="producto_id")
    private Producto producto;
	private Integer cantidad;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public CompraComercio getCompraComercio() {
		return compraComercio;
	}
	public void setCompraComercio(CompraComercio compraComercio) {
		this.compraComercio = compraComercio;
	}
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
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
	private Double total;
	private Boolean activo;
	
 
	
}
