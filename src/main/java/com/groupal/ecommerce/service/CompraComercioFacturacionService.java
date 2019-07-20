package com.groupal.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.groupal.ecommerce.model.CompraComercioFacturacion;
import com.groupal.ecommerce.repository.CompraComercioFacturacionRepository;
import com.querydsl.core.types.dsl.BooleanExpression;

@Service
public class CompraComercioFacturacionService {
	
	@Autowired
    private CompraComercioFacturacionRepository compraComercioFacturacionRepository;
	
	@Autowired
    private CompraComercioService compraComercioService;

	@Autowired
    private FormaPagoService formaPagoService;
	

	@Autowired
    private FormaEnvioService formaEnvioService;
	
	
	public Iterable<CompraComercioFacturacion> listAllCompraComercioFacturacions() {
        return compraComercioFacturacionRepository.findAll();
    }
	
	public Iterable<CompraComercioFacturacion> listAllCompraComercioFacturacions(Sort sort) {
        return compraComercioFacturacionRepository.findAll(sort);
    }
	
	public Page<CompraComercioFacturacion> listAllCompraComercioFacturacionsPagination(BooleanExpression predicate, Pageable pageable) {
        return compraComercioFacturacionRepository.findAll(predicate, pageable);
    }
	
	public Iterable<CompraComercioFacturacion> listAllCompraComercioFacturacionsPagination(BooleanExpression predicate) {
        return compraComercioFacturacionRepository.findAll(predicate);
    }
  
    public CompraComercioFacturacion getCompraComercioFacturacionById(Integer id) {
        return compraComercioFacturacionRepository.findOne(id);
    }
    
    public CompraComercioFacturacion getCompraComercioFacturacionByConsulta(BooleanExpression predicate) {
        return compraComercioFacturacionRepository.findOne(predicate);
    }

   
    public CompraComercioFacturacion saveCompraComercioFacturacion(Integer id, Integer compraComercioId, String nombre, String apellido, String username, 
    		String mail, String direccion, String telefono, String pais,
    		String ciudad,String codigoPostal, Integer idFormaPago, Integer idFormaEnvio, Boolean activo) {
    	
    	CompraComercioFacturacion compraComercioFacturacion = null;
		if (id!=null){
			compraComercioFacturacion = compraComercioFacturacionRepository.findOne(id);
		}else{
			compraComercioFacturacion = new CompraComercioFacturacion();
		}

		compraComercioFacturacion.setCompraComercio(compraComercioService.getCompraComercioById(compraComercioId));
		compraComercioFacturacion.setNombre(nombre);
		compraComercioFacturacion.setApellido(apellido);
		compraComercioFacturacion.setUsername(username);
		compraComercioFacturacion.setMail(mail);
		compraComercioFacturacion.setDireccion(direccion);
		compraComercioFacturacion.setTelefono(telefono);
		compraComercioFacturacion.setPais(pais);
		compraComercioFacturacion.setCiudad(ciudad);
		compraComercioFacturacion.setCodigoPostal(codigoPostal);
		compraComercioFacturacion.setFormaPago(formaPagoService.getFormaPagoById(idFormaPago));
		compraComercioFacturacion.setFormaEnvio(formaEnvioService.getFormaEnvioById(idFormaEnvio));
		compraComercioFacturacion.setActivo(activo);
		
		compraComercioFacturacionRepository.save(compraComercioFacturacion);

		return compraComercioFacturacion;
    }

  
    public void deleteCompraComercioFacturacion(Integer id) {
    	compraComercioFacturacionRepository.delete(id);
    }


	public void cambiarEstadoCompraComercioFacturacion(Integer idCarrera, boolean estado) {
		CompraComercioFacturacion compraComercioFacturacion = compraComercioFacturacionRepository.findOne(idCarrera);
		compraComercioFacturacion.setActivo(estado);
		
		compraComercioFacturacionRepository.save(compraComercioFacturacion);
	}

}
