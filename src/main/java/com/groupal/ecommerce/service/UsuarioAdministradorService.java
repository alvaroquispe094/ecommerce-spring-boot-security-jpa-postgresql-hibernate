package com.groupal.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.groupal.ecommerce.model.UsuarioAdministrador;
import com.groupal.ecommerce.repository.RolRepository;
import com.groupal.ecommerce.repository.UsuarioAdministradorRepository;
import com.querydsl.core.types.dsl.BooleanExpression;

@Service
public class UsuarioAdministradorService {
	
	@Autowired
    private UsuarioAdministradorRepository usuarioAdministradorRepository;
	
	@Autowired
    private RolRepository rolRepository;
	
	
	public Iterable<UsuarioAdministrador> listAllUsuarioAdministradores() {
        return usuarioAdministradorRepository.findAll();
    }

	public Page<UsuarioAdministrador> listAllUsuarioAdministradoresPagination(BooleanExpression predicate, Pageable pageable) {
        return usuarioAdministradorRepository.findAll(predicate, pageable);
    }
	
  
    public UsuarioAdministrador getUsuarioAdministradorById(Integer id) {
        return usuarioAdministradorRepository.getOne(id);
    }
    
    public UsuarioAdministrador getUsuarioAdministradorByUsername(String username) {
        return usuarioAdministradorRepository.findByUsername(username);
    }
    
    public UsuarioAdministrador getUsuarioAdministradorById(BooleanExpression predicate) {
        return usuarioAdministradorRepository.findOne(predicate);
    }
    

   
    public UsuarioAdministrador saveUsuarioAdministrador(Integer id, String username, String clave, String mail, Integer rol_id, Boolean activo) {
    	
    	UsuarioAdministrador usuarioAdministrador = null;
		if (id!=null){
			usuarioAdministrador = usuarioAdministradorRepository.getOne(id);
		}else{
			usuarioAdministrador = new UsuarioAdministrador();
		}

		
		usuarioAdministrador.setUsername(username);
		usuarioAdministrador.setPassword(clave);
		
		usuarioAdministrador.setMail(mail);
		usuarioAdministrador.setRol(rolRepository.findOne(rol_id));
		usuarioAdministrador.setActivo(activo);
		
		usuarioAdministradorRepository.save(usuarioAdministrador);

		return usuarioAdministrador;
    }

  
    public void deleteUsuario(Integer id) {
    	usuarioAdministradorRepository.delete(id);
    }

	public void cambiarEstadoUsuario(Integer idUsuario, Boolean estado ) {
		UsuarioAdministrador usuario = usuarioAdministradorRepository.findOne(idUsuario);
		usuario.setActivo(estado);
		usuarioAdministradorRepository.save(usuario);		
	}
	
	
}
