package com.groupal.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mercadopago.MercadoPago;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.Address;
import com.mercadopago.resources.datastructures.preference.BackUrls;
import com.mercadopago.resources.datastructures.preference.Identification;
import com.mercadopago.resources.datastructures.preference.Item;
import com.mercadopago.resources.datastructures.preference.Payer;
import com.mercadopago.resources.datastructures.preference.Phone;


@SpringBootApplication
public class EcommerceApplication {

	public static void main(String[] args) throws MPException {
		SpringApplication.run(EcommerceApplication.class, args);
		
		
		MercadoPago.SDK.setClientSecret(System.getenv("F0m81rp7pJHdUcqtc1mMvEmjNNwirVCC"));
        MercadoPago.SDK.setClientId(System.getenv("8748088937636555"));
        MercadoPago.SDK.setAccessToken("TEST-8748088937636555-062013-fc5dd4aa173193dce1e6fd339f7862cd-295953388");
        MercadoPago.SDK.setAppId("TEST-c5e4ea3e-916f-46a2-a710-1b1fb3256060");
        // Create a preference object
        Preference preference = new Preference();
        // Create an item object
        Item item = new Item();
        item.setId("1234")
            .setTitle("Synergistic Concrete Clock")
            .setQuantity(1)
            .setCurrencyId("ARS")
            .setUnitPrice((float) 72.66);
        
        // Create a payer object
        Payer payer = new Payer();
        payer.setName("Charles")
             .setSurname("Mayer")
             .setEmail("charles@yahoo.com")
             .setDateCreated("2018-06-02T12:58:41.425-04:00")
             .setPhone(new Phone()
                .setNumber("(713) 942-4377 x2770"))
             .setIdentification(new Identification()
                .setType("DNI")
                .setNumber("12345678"))
             .setAddress(new Address()
                .setStreetName("Schiller Row")
                .setStreetNumber(1234)
                .setZipCode("20220"));
        
        // Setting preference properties
        preference.setPayer(payer);
        preference.appendItem(item);
        
        //back url
        BackUrls backUrls = new BackUrls(
                         "https://www.tu-sitio/success",
                         "http://www.tu-sitio/pending",
                         "http://www.tu-sitio/failure");

        preference.setBackUrls(backUrls);
        
        preference.setExternalReference("23"); //id compracomercio para que se vea en mercado pago
        
        // Save and posting preference
        preference.save();

        
	}

}
