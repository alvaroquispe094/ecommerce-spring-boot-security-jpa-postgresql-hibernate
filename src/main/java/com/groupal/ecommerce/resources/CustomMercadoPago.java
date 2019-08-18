package com.groupal.ecommerce.resources;

import com.mercadopago.MercadoPago;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.Address;
import com.mercadopago.resources.datastructures.preference.BackUrls;
import com.mercadopago.resources.datastructures.preference.Identification;
import com.mercadopago.resources.datastructures.preference.Item;
import com.mercadopago.resources.datastructures.preference.Payer;
import com.mercadopago.resources.datastructures.preference.Phone;

public class CustomMercadoPago {
	
	public static String clientSecret = "trDUoRzk8HAHhAyTgZs0bEOlGkhxD3LS";
	public static String clientId = "8490206967742231";
	//public static String appId = "TEST-c5e4ea3e-916f-46a2-a710-1b1fb3256060";
//	public static String accessToken = "TEST-6784463567058779-070621-2b5f4ec5299a6a63084bc6b615d9572b-450014717";
	public static String accessToken = "TEST-8490206967742231-081821-9af6f0572cc77ba9ce8d2fa60961348d-462649532";
	public static String userToken = null;
//	public static String appId = null;
    
    public CustomMercadoPago() throws MPException {
    	


		MercadoPago.SDK.setClientSecret(System.getenv(clientSecret));
        MercadoPago.SDK.setClientId(System.getenv(clientId));
        MercadoPago.SDK.setAccessToken(accessToken);
        
//        MercadoPago.SDK.setAppId("TEST-c5e4ea3e-916f-46a2-a710-1b1fb3256060");
    }
    
    
    public Preference createPreference() {
    	return new Preference();
    	
    }
    
    public Item createItem(Integer id, String title, Integer cantidad, String moneda, float precio) {
    	Item item = new Item();
    	item.setId(id.toString())
        .setTitle(title)
        .setQuantity(cantidad)
        .setCurrencyId(moneda)
        .setUnitPrice(precio);
        
        return item;
    	
    }
    
    public Payer createPayer(String nombre, String apellido, String email, String fecha, String telefono, Integer documento,String direccion,String codigoPostal) {
    	
    	Payer payer = new Payer();
        payer.setName(nombre)
             .setSurname(apellido)
             .setEmail(email)
             .setDateCreated(fecha)
             .setPhone(new Phone()
                .setNumber(telefono))
             .setIdentification(new Identification()
                .setNumber(documento.toString()))
             .setAddress(new Address()
                .setStreetName(direccion)
                
                .setZipCode(codigoPostal));
    	
    	return payer;
    	
    }
    
    public BackUrls createBackUrls(String success, String pending, String failures) {

    	BackUrls backUrls = new BackUrls(success, pending, failures);
    	
    	return backUrls;
    	
    }
    
   
}
