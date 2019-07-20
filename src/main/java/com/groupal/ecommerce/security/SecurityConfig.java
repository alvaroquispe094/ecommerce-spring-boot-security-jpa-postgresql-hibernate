package com.groupal.ecommerce.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableAutoConfiguration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
   
	@Autowired
    PasswordEncoder passwordEncoder; 

    @Autowired
    DataSource dataSource;
    
    @Autowired
    CustomAuthenticationSuccessHandler customSuccessHandler;
    
    @Autowired
    CustomUserDetailsService customUserDetailsService;
   
    @Override
	protected void configure(HttpSecurity http) throws Exception {
    	 http.csrf().disable();      
		http.authorizeRequests()
			.antMatchers("/","/index","/css/**","/js/**","/img/**","/register","/login","/contacto","/cart/**","/catalogo","/details","/registrar,/notificacionMercadoPago").permitAll()
			.antMatchers("/administrativo/**").access("hasAuthority('ROL_ADMINISTRADOR')") 
			.antMatchers("/vendedor/**").access("hasAuthority('ROL_VENDEDOR')")
			.antMatchers("/cliente/**").access("hasAuthority('ROL_CLIENTE')")
            .anyRequest().authenticated()
            .and()
            .formLogin().loginPage("/login").successHandler(customSuccessHandler)
            .and()
            .logout()
            .permitAll();
            http.exceptionHandling().accessDeniedPage("/403");
	}

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
    	
    	 auth.inMemoryAuthentication().withUser("administrador").password("12345").roles("ROL_ADMINISTRADOR");
    	auth.userDetailsService(customUserDetailsService);
    	
       

    }

}

