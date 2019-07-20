package com.groupal.ecommerce.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.groupal.ecommerce.model.UsuarioAdministrador;
import com.groupal.ecommerce.model.UsuarioCliente;
import com.groupal.ecommerce.service.UsuarioAdministradorService;
import com.groupal.ecommerce.service.UsuarioClienteService;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	 	@Autowired
	    private UsuarioAdministradorService usuarioAdministradorService;
	 	
	 	@Autowired
	    private UsuarioClienteService usuarioClienteService;
	 	
	    @Override
	    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
	       
	    	//busca al usuario en las tablas usuarioCliente y usuarioAdministrador para ver si esta registrado en el sistema
	    	UsuarioAdministrador usuarioAdministrador = usuarioAdministradorService.getUsuarioAdministradorByUsername(s);
	        UsuarioCliente usuarioCliente = usuarioClienteService.getUsuarioClienteByUsername(s);
	        
	        //check if this user with this username exist, if not, throw an exception
	        // and stop the login process
	        if (usuarioAdministrador == null && usuarioCliente == null) {
	            throw new UsernameNotFoundException("User details not found with this username: " + s);
	        }
	        
	        User user = null;
	        
	        if(usuarioAdministrador != null) {
		        String username = usuarioAdministrador.getUsername();
		        String password = usuarioAdministrador.getPassword();
		        String rol = usuarioAdministrador.getRol().getNombre();
		 
		        List<SimpleGrantedAuthority> authList = getAuthorities(rol);
		        user = new User(username, password, authList);
	        }
	        
	        if(usuarioCliente != null) {
		        String username = usuarioCliente.getUsername();
		        String password = usuarioCliente.getPassword();
		        String rol = usuarioCliente.getRol().getNombre();
		 
		        List<SimpleGrantedAuthority> authList = getAuthorities(rol);
		        user = new User(username, password, authList);
	        }
	        return user;
	    }
	 
	    private List<SimpleGrantedAuthority> getAuthorities(String role) {
	        List<SimpleGrantedAuthority> authList = new ArrayList<>();
	        authList.add(new SimpleGrantedAuthority(role));
	        return authList;
	    }

}