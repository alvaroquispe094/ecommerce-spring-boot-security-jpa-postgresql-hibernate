package com.groupal.ecommerce.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.google.gson.JsonObject;
import com.groupal.ecommerce.model.Estado;
import com.groupal.ecommerce.service.CompraComercioService;
import com.mercadopago.MercadoPago;
import com.mercadopago.core.MPApiResponse;


@Controller
public class MercadoPagoController {
	
	@Autowired
	CompraComercioService compraComercioService;
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@RequestMapping(value="/notifications", method=RequestMethod.POST)
	protected @ResponseBody String notificacionMercadoPago(@RequestParam String topic, @RequestParam String id){
		System.out.println("topic:sdgsdgsdgsdg");	
		System.out.println("id: sdgsdgsdg2134235");

		// SI EL CLIENTE TIENE CONFIGURADO MercadoPago
//		Parametrizador parametrizador = parametrizadorManager.get(Parametrizador.SHOPPING_CART_PARAMS);
//		if ((parametrizador.getMlClientId() != null && !parametrizador.getMlClientId().equals(""))
//				&& (parametrizador.getMlClientIdSecret() != null && !parametrizador.getMlClientIdSecret().equals(""))) {
		
//		String clientId = "4000364412100819";
//		String clientSecret = "79MxJUEkefNUUqP8rUuxtXKKsLAExtZG";
//		MP mp = new MP (clientId, clientSecret);
//		MercadoPago.SDK mp ;
//		SDK.setClientId(CustomMercadoPago.clientId);
		
//		MercadoPago.SDK.setClientId(CustomMercadoPago.clientId);
//		MercadoPago.SDK.setClientSecret(CustomMercadoPago.clientId);
//		MercadoPago.SDK.setAccessToken(CustomMercadoPago.accessToken);
		
			
		
		if (topic.equalsIgnoreCase("payment")) {
	        try {
//			JSONObject payment = new JSONObject();
	        
			MPApiResponse paymentInfo = MercadoPago.SDK.Get("https://api.mercadopago.com/v1/payments/"+id+"?access_token=TEST-6784463567058779-070621-2b5f4ec5299a6a63084bc6b615d9572b-450014717");
			JsonObject paymentInfoJson = paymentInfo.getJsonElementResponse().getAsJsonObject();
			Integer idCompraComercio = Integer.parseInt(paymentInfoJson.get("external_reference").toString());
			
			compraComercioService.cambiarEstadoVenta(idCompraComercio, Estado.PAGADO);
			
	      } catch (Exception e) {
	    	  e.printStackTrace();
	      }

		}
		return id;
	}
        
	
	@RequestMapping(value="/pagoConfirmado", method=RequestMethod.GET)
	protected ModelAndView compraConfirmada(@RequestParam(value="id") Integer idCompraComercio, @RequestParam(required=false) String Answer, Principal principal ) {
	
		
		compraComercioService.cambiarEstadoVenta(idCompraComercio, Estado.PAGADO);
		System.out.println("entro al pagoConfirmado");
		return new ModelAndView(new RedirectView("/cliente/compras?message=El pago mediante Mercado Pago ha sido confirmado."));
	}
	
	@RequestMapping(value = "/compraPendientePago", method = RequestMethod.GET)
	protected ModelAndView compraPendientePagoMP(@RequestParam Integer id, Principal principal,
			HttpServletRequest request) {
	
//		compraComercioServiceManager.confirmarCompraFormaPago(idEmbajadorSolicitudMatricula, FormaPago.MERCADO_PAGO);

		return new ModelAndView(new RedirectView("/cliente/compras?message=El pago mediante Mercado Pago esta pendiente."));
	}

}
