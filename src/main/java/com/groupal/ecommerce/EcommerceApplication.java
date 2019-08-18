package com.groupal.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mercadopago.exceptions.MPException;


@SpringBootApplication
public class EcommerceApplication {

	public static void main(String[] args) throws MPException {
		SpringApplication.run(EcommerceApplication.class, args);
	}

}
