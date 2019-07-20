package com.groupal.ecommerce.service;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.groupal.ecommerce.model.QUsuarioCliente;
import com.groupal.ecommerce.model.UsuarioCliente;
import com.groupal.ecommerce.repository.RolRepository;
import com.groupal.ecommerce.repository.UsuarioClienteRepository;
import com.groupal.ecommerce.resources.FechaHelper;
import com.querydsl.core.types.dsl.BooleanExpression;

@Service
public class UsuarioClienteService {
	
	@Autowired
    private UsuarioClienteRepository usuarioClienteRepository;
	
	@Autowired
    private RolRepository rolRepository;
	
	
	public Iterable<UsuarioCliente> listAllUsuarioClientes() {
        return usuarioClienteRepository.findAll();
    }

	public Page<UsuarioCliente> listAllUsuarioClientesPagination(BooleanExpression predicate, Pageable pageable) {
        return usuarioClienteRepository.findAll(predicate, pageable);
    }
	
	public Iterable<UsuarioCliente> listAllUsuarioClientes(Sort sort) {
        return usuarioClienteRepository.findAll(sort);
    }
  
    public UsuarioCliente getUsuarioClienteById(Integer id) {
        return usuarioClienteRepository.getOne(id);
    }
    
    public UsuarioCliente getUsuarioClienteByUsername(String username) {
        return usuarioClienteRepository.findByUsername(username);
    }
    
    public UsuarioCliente getUsuarioClienteById(BooleanExpression predicate) {
        return usuarioClienteRepository.findOne(predicate);
    }
    

   
    public UsuarioCliente saveUsuarioCliente(Integer id, String nombre, String apellido, String username, String clave, Integer documento, String telefono, String direccion, String fecha, String mail, Integer rol_id, Boolean activo) {
    	
    	UsuarioCliente usuarioCliente = null;
		if (id!=null){
			usuarioCliente = usuarioClienteRepository.getOne(id);
		}else{
			usuarioCliente = new UsuarioCliente();
		}

		usuarioCliente.setNombre(nombre);
		usuarioCliente.setApellido(apellido);
		usuarioCliente.setUsername(username);
		usuarioCliente.setPassword(clave);
		usuarioCliente.setDocumento(documento);
		usuarioCliente.setDireccion(direccion);
		usuarioCliente.setTelefono(telefono);
		usuarioCliente.setFecha(FechaHelper.convertirFechaADate(fecha, "dd/MM/yyyy"));
		usuarioCliente.setMail(mail);
		usuarioCliente.setRol(rolRepository.findOne(rol_id));
		usuarioCliente.setActivo(activo);
		
		usuarioClienteRepository.save(usuarioCliente);

		return usuarioCliente;
    }

  
    public void deleteUsuarioCliente(Integer id) {
    	usuarioClienteRepository.delete(id);
    }

	public void cambiarEstadoUsuarioCliente(Integer idUsuario, Boolean estado ) {
		UsuarioCliente usuarioCliente = usuarioClienteRepository.findOne(idUsuario);
		usuarioCliente.setActivo(estado);
		usuarioClienteRepository.save(usuarioCliente);		
	}
	
	public UsuarioCliente obtenerUsuarioCliente(Principal principal) {

    	BooleanExpression consulta = QUsuarioCliente.usuarioCliente.id.ne(0);
    	consulta = consulta.and(QUsuarioCliente.usuarioCliente.username.eq(principal.getName()));
    	UsuarioCliente usuarioCliente = usuarioClienteRepository.findOne(consulta);
    	return usuarioCliente;
    }
	
}
