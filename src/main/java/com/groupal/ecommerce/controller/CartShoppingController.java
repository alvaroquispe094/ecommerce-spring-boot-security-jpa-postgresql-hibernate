package com.groupal.ecommerce.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.groupal.ecommerce.model.CompraComercio;
import com.groupal.ecommerce.model.CompraComercioFacturacion;
import com.groupal.ecommerce.model.CompraComercioProducto;
import com.groupal.ecommerce.model.Estado;
import com.groupal.ecommerce.model.Producto;
import com.groupal.ecommerce.model.QCompraComercioFacturacion;
import com.groupal.ecommerce.model.QCompraComercioProducto;
import com.groupal.ecommerce.model.UsuarioCliente;
import com.groupal.ecommerce.resources.CustomMercadoPago;
import com.groupal.ecommerce.resources.FechaHelper;
import com.groupal.ecommerce.resources.Utils;
import com.groupal.ecommerce.service.CartShoppingService;
import com.groupal.ecommerce.service.CompraComercioFacturacionService;
import com.groupal.ecommerce.service.CompraComercioProductoService;
import com.groupal.ecommerce.service.CompraComercioService;
import com.groupal.ecommerce.service.FormaEnvioService;
import com.groupal.ecommerce.service.FormaPagoService;
import com.groupal.ecommerce.service.ProductoService;
import com.groupal.ecommerce.service.UsuarioClienteService;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.BackUrls;
import com.mercadopago.resources.datastructures.preference.Item;
import com.mercadopago.resources.datastructures.preference.Payer;
import com.querydsl.core.types.dsl.BooleanExpression;

@Controller
public class CartShoppingController {
	
	@Autowired
	ProductoService productoService;

	@Autowired
	CartShoppingService cartShoppingService;  
	
	@Autowired
	CompraComercioService compraComercioService;  
	
	@Autowired
	CompraComercioFacturacionService compraComercioFacturacionService;  
	
	@Autowired
	CompraComercioProductoService compraComercioProductoService;  
	
	@Autowired
	FormaPagoService formaPagoService;
	
	@Autowired
	FormaEnvioService formaEnvioService;
	
	@Autowired
	UsuarioClienteService usuarioClienteService;
	
	@RequestMapping(value="/cart", method=RequestMethod.GET)
	 protected ModelAndView administrarCart(HttpServletRequest request, Principal principal) { 	
		 
		 Map<String,Object> modelo = new HashMap<String, Object>();
		 
		//obtengo los items del carrito
//	    List<Producto> myProducts = Utils.getCartInSession(request);
	    
	    modelo.put("cantidad",Utils.myProducts.size());
		modelo.put("productos", Utils.myProducts);
		
		//calculo el total del carrito
        double total = cartShoppingService.getTotalCartShopping(request);
		modelo.put("total", total);
		return new ModelAndView("cart","modelo",modelo);
	 }
	
	@RequestMapping(value="/cart/add", method=RequestMethod.GET)
	public ModelAndView addCart(@RequestParam (value="id") Integer idProducto, HttpServletRequest request, Principal principal) {
		Map<String, Object> modelo = new HashMap<String, Object>();

		//obtengo los items del carrito
//    	List<Producto> myProducts = Utils.getCartInSession(request);
		
        //busco si el item ya esta en el carrito
        Producto item = cartShoppingService.searchProductInCart(idProducto, request);
        if(item != null){
        	
        	int index = Utils.myProducts.indexOf(item);
        	Utils.myProducts.get(index).setCantidad(Utils.myProducts.get(index).getCantidad() + 1);
        	
        	
        }else {
            Producto producto = productoService.getProductoById(idProducto);
            producto.setCantidad(1);
            Utils.myProducts.add(producto);
        }
        //seteo en session el carrito
//        Utils.setCartInSession(request, Utils.myProducts);
        
        //calculo el total del carrito
        double total = cartShoppingService.getTotalCartShopping(request);
        
        modelo.put("total", total);
        modelo.put("productos", Utils.myProducts);
		
        modelo.put("cantidad",Utils.myProducts.size());
        return new ModelAndView(new RedirectView("/cart"));
	}
	
	@RequestMapping(value="/cart/remove", method=RequestMethod.GET)
	public ModelAndView removeCart(@RequestParam (value="id") Integer idProducto, HttpServletRequest request, Principal principal) {
		Map<String, Object> modelo = new HashMap<String, Object>();

		//obtengo los items del carrito
//    	List<Producto> myProducts = Utils.getCartInSession(request);
		
        //busco el item en el carrito
        Producto item = cartShoppingService.searchProductInCart(idProducto, request);
        if(item != null){
        	
        	 if(item.getCantidad() <= 1){
             	int index = Utils.myProducts.indexOf(item);
             	Utils.myProducts.remove(index);
             }else {
        	
            	 int index = Utils.myProducts.indexOf(item);
            	 Utils.myProducts.get(index).setCantidad(Utils.myProducts.get(index).getCantidad() - 1);
             }
        	
        }
        //seteo en session el carrito
//        Utils.setCartInSession(request, Utils.myProducts);
        
        //calculo el total del carrito
        double total = cartShoppingService.getTotalCartShopping(request);
        
        modelo.put("total", total);
        modelo.put("productos", Utils.myProducts);
		
        modelo.put("cantidad", Utils.myProducts.size());
        return new ModelAndView(new RedirectView("/cart"));
	}
	
	
	@RequestMapping(value="/cart/delete", method=RequestMethod.GET)
	public ModelAndView removeItem(@RequestParam (value="id") Integer idProducto, HttpServletRequest request, Principal principal) {

        
		//obtengo los items del carrito
//    	List<Producto> myProducts = Utils.getCartInSession(request);
    	
    	//busco el item en el carrito
        Producto item = cartShoppingService.searchProductInCart(idProducto, request);
        
        if(item != null){
        	int index = Utils.myProducts.indexOf(item);
        	Utils.myProducts.remove(index);
        }
        
        //seteo en session el carrito
//        Utils.setCartInSession(request, Utils.myProducts);
        return new ModelAndView(new RedirectView("/cart"));
	}
       
	@RequestMapping(value="/details", method=RequestMethod.GET)
	public ModelAndView details(@RequestParam (value="id") Integer idProducto, HttpServletRequest request, Principal principal) {
		
		Map<String, Object> modelo = new HashMap<String, Object>();
	
		Producto producto = productoService.getProductoById(idProducto);
		
		modelo.put("producto", producto);
		return new ModelAndView("details","modelo",modelo);
	}	
	
	
	@RequestMapping(value="/cart/clearCart", method=RequestMethod.GET)
	public ModelAndView cartClear(HttpServletRequest request, Principal principal) {
	
		Utils.myProducts.clear();
		return new ModelAndView(new RedirectView("/cart"));
	}	
	
	@RequestMapping(value="/cart/checkout/guardar", method=RequestMethod.GET)
	public ModelAndView cartCheckoutSave(HttpServletRequest request, @RequestParam(required=false, defaultValue="true") Boolean activo, Principal principal) { 	
		 
		 Map<String,Object> modelo = new HashMap<String, Object>();
		 
		 //obtengo al usuario logeado
		 UsuarioCliente usuarioCliente = usuarioClienteService.obtenerUsuarioCliente(principal);
		 
		 //fecha
		 Date date = new Date();
		 String fecha = FechaHelper.convertirFechaAString(date);
		 
		//obtengo los items del carrito
//	    List<Producto> myProducts = Utils.getCartInSession(request);
	    
	    modelo.put("cantidad",Utils.myProducts.size());
		modelo.put("productos", Utils.myProducts);
		
		//calculo el total del carrito
		double total = cartShoppingService.getTotalCartShopping(request);
		modelo.put("total", total);
		
		//guardo la compraComercio
		CompraComercio compraComercio = compraComercioService.saveCompraComercio(null, usuarioCliente.getId(), Estado.INGRESADO, fecha, total, activo);
		
		
		//guardo los producto de la compra
		for (Producto producto: Utils.myProducts) {
			Double subtotal = producto.getPrecio()*producto.getCantidad();
			compraComercioProductoService.saveCompraComercioProducto(null, compraComercio.getId(), producto.getId(), producto.getCantidad(), subtotal, activo);
			
		}
		
		//elimino los producto de la session
//		Utils.removeCartSession(request);
//		Utils.myProducts.clear();
		return new ModelAndView(new RedirectView("/cart/checkout?id="+compraComercio.getId()+"&message=La compra fue ingresada"));
	 }
	
	@RequestMapping(value="/cart/checkout", method=RequestMethod.GET)
	public ModelAndView cartCheckout(HttpServletRequest request, Principal principal, @RequestParam( value = "id") Integer idCompraComercio) { 	
		 
		Map<String,Object> modelo = new HashMap<String, Object>();
		
		BooleanExpression consulta = QCompraComercioProducto.compraComercioProducto.id.ne(0);
		consulta = consulta.and(QCompraComercioProducto.compraComercioProducto.compraComercio.id.eq(idCompraComercio));
		 
		//Obtengo los productos de la compra ingresada
		Iterable<CompraComercioProducto> compraComercioProductos = compraComercioProductoService.listAllCompraComercioProductosPagination(consulta);
		
		//calculo el total del carrito
		double total = compraComercioService.getCompraComercioById(idCompraComercio).getTotal();
		
		modelo.put("formaPagos", formaPagoService.listAllFormaPagos());
		modelo.put("formaEnvios", formaEnvioService.listAllFormaEnvios());
		modelo.put("compraComercioProductos", compraComercioProductos);
		modelo.put("compraComercio", compraComercioService.getCompraComercioById(idCompraComercio));
		modelo.put("total", total);
		return new ModelAndView("checkout","modelo",modelo);
	 }
	
	@RequestMapping(value="/cart/checkout/confirmacion", method=RequestMethod.GET)
	public ModelAndView cartCheckoutConfirmacion(HttpServletRequest request, @RequestParam( value = "id") Integer idCompraComercio, 
								@RequestParam String nombre, @RequestParam String apellido, @RequestParam String username, 
								@RequestParam String pais,@RequestParam String ciudad, @RequestParam String telefono,
								@RequestParam String codigoPostal, @RequestParam( value = "formaPago") Integer idFormaPago, 
								@RequestParam( value = "formaEnvio") Integer idFormaEnvio,
								@RequestParam String direccion, @RequestParam String mail,
			@RequestParam(required=false, defaultValue="true") Boolean activo, Principal principal) { 	
		 
		
		 
		 //obtengo al usuario logeado
		 UsuarioCliente usuarioCliente = usuarioClienteService.obtenerUsuarioCliente(principal);
		 
		
		 compraComercioFacturacionService.saveCompraComercioFacturacion(null, idCompraComercio, nombre, apellido, username, mail, direccion, telefono, pais, ciudad, codigoPostal, idFormaPago, idFormaEnvio, activo);
		
		 CompraComercio compraComercio = compraComercioService.getCompraComercioById(idCompraComercio);
		
		//cambio estado de compraComercio 
		compraComercioService.saveCompraComercio(idCompraComercio, usuarioCliente.getId(), Estado.CONFIRMADO, FechaHelper.convertirFechaAString(compraComercio.getFecha()), compraComercio.getTotal(), activo);
		
		return new ModelAndView(new RedirectView("/cart/payment?id="+idCompraComercio+"&message=La compra fue confirmada"));
	 }
	
	@RequestMapping(value="/cart/payment", method=RequestMethod.GET)
	public ModelAndView cartPayment(HttpServletRequest request, Principal principal, @RequestParam( value = "id") Integer idCompraComercio) { 	
		
		
		//realizar mi logica de pantalla de payment
		
		Map<String,Object> modelo = new HashMap<String, Object>();
		
		//Obtengo los productos de la compra ingresada
		BooleanExpression consulta1 = QCompraComercioProducto.compraComercioProducto.id.ne(0);
		consulta1 = consulta1.and(QCompraComercioProducto.compraComercioProducto.compraComercio.id.eq(idCompraComercio));
		Iterable<CompraComercioProducto> compraComercioProductos = compraComercioProductoService.listAllCompraComercioProductosPagination(consulta1);
		
		//obtengo la compra
		CompraComercio compraComercio = compraComercioService.getCompraComercioById(idCompraComercio);
		
		//Obtengo la facturacion
		BooleanExpression consulta2 = QCompraComercioFacturacion.compraComercioFacturacion.id.ne(0);
		consulta2= consulta2.and(QCompraComercioFacturacion.compraComercioFacturacion.compraComercio.id.eq(idCompraComercio));
		CompraComercioFacturacion compraComercioFacturacion = compraComercioFacturacionService.getCompraComercioFacturacionByConsulta(consulta2);
		
		
		//calculo el total del carrito
		double total = compraComercio.getTotal();

		modelo.put("compraComercioProductos", compraComercioProductos);
		modelo.put("compraComercioFacturacion", compraComercioFacturacion);
		modelo.put("compraComercio", compraComercio);
		modelo.put("total", total);
		return new ModelAndView("payment","modelo",modelo);
	 }
	
	@RequestMapping(value="/cart/payment/mercadoPago", method=RequestMethod.GET)
	protected @ResponseBody String cartPaymenteMercadoPago(HttpServletRequest request, Principal principal, @RequestParam( value = "id") Integer idCompraComercio) throws MPException { 	
		CustomMercadoPago customMercadoPago = new CustomMercadoPago();
		
		//obtengo la compra
		CompraComercio compraComercio = compraComercioService.getCompraComercioById(idCompraComercio);
		
		//Obtengo los productos de la compra
		BooleanExpression consulta = QCompraComercioProducto.compraComercioProducto.id.ne(0);
		consulta = consulta.and(QCompraComercioProducto.compraComercioProducto.compraComercio.id.eq(idCompraComercio));		 
		Iterable<CompraComercioProducto> compraComercioProductos = compraComercioProductoService.listAllCompraComercioProductosPagination(consulta);
		
		//Obtengo la facturacion
		BooleanExpression consulta2 = QCompraComercioFacturacion.compraComercioFacturacion.id.ne(0);
		consulta2= consulta2.and(QCompraComercioFacturacion.compraComercioFacturacion.compraComercio.id.eq(idCompraComercio));
		CompraComercioFacturacion compraComercioFacturacion = compraComercioFacturacionService.getCompraComercioFacturacionByConsulta(consulta2);
		
        // Create a preference object
        Preference preference = customMercadoPago.createPreference();
        
        // Create an item object
        for(CompraComercioProducto compraComercioProducto: compraComercioProductos) {
	        Item item = customMercadoPago.createItem(compraComercioProducto.getId(), compraComercioProducto.getProducto().getNombre(), compraComercioProducto.getCantidad(), "ARS", compraComercioProducto.getTotal().floatValue());
	        preference.appendItem(item);
        }
        
        //Create an Payer object
        Date fecha = new Date();
        Payer payer = customMercadoPago.createPayer(compraComercioFacturacion.getNombre(), compraComercioFacturacion.getApellido(), 
        			compraComercioFacturacion.getMail(), FechaHelper.convertirFechaAString(fecha), compraComercioFacturacion.getTelefono(), 
        			compraComercio.getUsuarioCliente().getDocumento(), compraComercioFacturacion.getDireccion(), compraComercioFacturacion.getCodigoPostal());
        preference.setPayer(payer);
        
        preference.setExternalReference(idCompraComercio.toString()); //id compracomercio para que se vea en mercado pago
       
        
        
        BackUrls backUrls = customMercadoPago.createBackUrls("https://ecommerce-aplicacion.herokuapp.com/pagoConfirmado?id="+idCompraComercio+"" , 
        													 "https://ecommerce-aplicacion.herokuapp.com/compraPendientePago?id="+idCompraComercio+"" , 
        													 "https://ecommerce-aplicacion.herokuapp.com/?mesagge=Su pago no pudo ser procesado");
        		
       

        preference.setBackUrls(backUrls);
//        preference.setNotificationUrl("http://localhost:8080/notificacionMercadoPago?id="+idCompraComercio+"");
        preference.setNotificationUrl("https://ecommerce-aplication.herokuapp.com/notifications");
        // Save and posting preference
        
        preference.save();
		
        String checkoutURL = preference.getSandboxInitPoint();
        
//        String  url= "/"+checkoutURL+"";
				
		return checkoutURL;

	 }
	
	@RequestMapping(value = "/cart/isLogin", method = RequestMethod.GET)
	protected @ResponseBody String isCartLogged(HttpServletResponse response, Principal principal) throws IOException {
		

		if(principal != null) {
			return "{\"logged\":\"true\",\"mensaje\":\"Debe loguarse en el sistema\"}";
		}
		
		return "{\"logged\":\"false\",\"mensaje\":\"Debe loguarse en el sistema\"}";

	 }
	
	@RequestMapping(value="/cart/items/count", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getCartItemCount(HttpServletRequest request, Model model){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", Utils.myProducts.size());
		return map;
	}
	
}
