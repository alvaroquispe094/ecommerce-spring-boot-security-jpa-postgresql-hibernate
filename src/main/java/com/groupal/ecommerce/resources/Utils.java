package com.groupal.ecommerce.resources;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.groupal.ecommerce.model.Producto;

public class Utils {
	
	public static List<Producto> myProducts = new ArrayList<Producto>();
	
	
	public static List<Producto> getCartInSession(HttpServletRequest request){

        //mengambil session name "myCart"
        @SuppressWarnings("unchecked")
        List<Producto> myCart = (List<Producto>) request.getSession().getAttribute("myCart");


        if(myCart == null){
        	myCart = new ArrayList<Producto>();

            request.getSession().setAttribute("myCart", myCart);
        }
        return myCart;
    }
	
	public static void setCartInSession(HttpServletRequest request, List<Producto> myCart){
		request.getSession().removeAttribute("myCart");
		request.getSession().setAttribute("myCart", myCart);
       
    }

    //menghapus cart session "myCart"
    public static void removeCartSession(HttpServletRequest request){
        request.getSession().removeAttribute("myCart");
    }

}
