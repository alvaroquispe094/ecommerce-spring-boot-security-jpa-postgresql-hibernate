package com.groupal.ecommerce.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@SequenceGenerator(name = "compraComercioFacturacionIdSeq", sequenceName = "compra_comercio_facturacion_id_seq", allocationSize=1)
@Table(name="compra_comercio_facturacion")
public class CompraComercioFacturacion {
	
   
	@Id  
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "compraComercioFacturacionIdSeq")
	private Integer id;
	 @ManyToOne (cascade = CascadeType.ALL)
	@JoinColumn( name="compra_comercio_id")
    private CompraComercio compraComercio;
	private String nombre;
    private String apellido;
    private String username;
    private String direccion;
    private String pais;
	private String ciudad;
    @Column(name = "codigo_postal")
    private String codigoPostal;
    private String mail;
    private String telefono;
    @ManyToOne (cascade = CascadeType.ALL)
	@JoinColumn( name="forma_pago_id")
    private FormaPago formaPago;
    @ManyToOne (cascade = CascadeType.ALL)
   	@JoinColumn( name="forma_envio_id")
    private FormaEnvio formaEnvio;
	private Boolean activo;
    
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
	
    public String getNombre() {
            return nombre;
    }

    public void setNombre(String nombre) {
            this.nombre = nombre;
    }

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	 public String getPais() {
			return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public FormaPago getFormaPago() {
		return formaPago;
	}
	public void setFormaPago(FormaPago formaPago) {
		this.formaPago = formaPago;
	}
	public FormaEnvio getFormaEnvio() {
		return formaEnvio;
	}
	public void setFormaEnvio(FormaEnvio formaEnvio) {
		this.formaEnvio = formaEnvio;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public Boolean getActivo() {
		return activo;
	}
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
    
}