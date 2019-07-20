package com.groupal.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.groupal.ecommerce.model.CompraComercio;
import com.groupal.ecommerce.repository.CompraComercioRepository;
import com.groupal.ecommerce.resources.FechaHelper;
import com.querydsl.core.types.dsl.BooleanExpression;

@Service
public class CompraComercioService {
	
	@Autowired
    private CompraComercioRepository compraComercioRepository;
	
	@Autowired
    private UsuarioClienteService usuarioClienteService;
	
	@Autowired
    private EstadoService estadoService;
	
	public Iterable<CompraComercio> listAllCompraComercios() {
        return compraComercioRepository.findAll();
    }
	
	public Iterable<CompraComercio> listAllCompraComercios(Sort sort) {
        return compraComercioRepository.findAll(sort);
    }
	
	public Page<CompraComercio> listAllCompraComerciosPagination(BooleanExpression predicate, Pageable pageable) {
        return compraComercioRepository.findAll(predicate, pageable);
    }
  
    public CompraComercio getCompraComercioById(Integer id) {
        return compraComercioRepository.findOne(id);
    }

   
    public CompraComercio saveCompraComercio(Integer id, Integer usuarioClienteId,Integer estado_id, String fecha,Double total, Boolean activo) {
    	
    	CompraComercio compraComercio = null;
		if (id!=null){
			compraComercio = compraComercioRepository.findOne(id);
		}else{
			compraComercio = new CompraComercio();
		}

		compraComercio.setUsuarioCliente(usuarioClienteService.getUsuarioClienteById(usuarioClienteId));
		compraComercio.setEstado(estadoService.getEstadoById(estado_id));
		compraComercio.setFecha(FechaHelper.convertirFechaADate(fecha, "dd/MM/yyyy"));
		compraComercio.setTotal(total);
		compraComercio.setActivo(activo);
		
		compraComercioRepository.save(compraComercio);

		return compraComercio;
    }

  
    public void deleteCompraComercio(Integer id) {
    	compraComercioRepository.delete(id);
    }


	public void cambiarEstadoCompraComercio(Integer idCarrera, boolean estado) {
		CompraComercio compraComercio = compraComercioRepository.findOne(idCarrera);
		compraComercio.setActivo(estado);
		
		compraComercioRepository.save(compraComercio);
	}
	
	
	public void cambiarEstadoVenta(Integer idCompraComercio, Integer idEstado) {
		CompraComercio compraComercio = compraComercioRepository.findOne(idCompraComercio);
		compraComercio.setEstado(estadoService.getEstadoById(idEstado));
		compraComercioRepository.save(compraComercio);
	}
    
    
}
