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
@SequenceGenerator(name = "usuarioAdministradorIdSeq", sequenceName = "usuario_administrador_id_seq", allocationSize=1)
@Table(name="usuario_administrador")	
public class UsuarioAdministrador {
	
   
	@Id  
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "usuarioAdministradorIdSeq")
	private Integer id;
	private String username;
    private String mail;
    private String password;
    @ManyToOne (cascade = CascadeType.ALL)
	@JoinColumn( name="rol_id")
    private Rol rol;
	private Boolean activo;
    
  
    public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public Rol getRol() {
		return rol;
	}
	public void setRol(Rol rol) {
		this.rol = rol;
	}
	
	public Boolean getActivo() {
		return activo;
	}
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

    
    
    
}