package com.fortmin.proshopping;

import java.util.ArrayList;
import java.util.List;

public class CanastaCompras {
   private static CanastaCompras instancia;	
   private List<String> paquetes_comprados;
   private float precio=0;
  

  public static CanastaCompras getInstance(){
		if(instancia==null)
			instancia= new CanastaCompras();
		return instancia;
		
	}
   
   private CanastaCompras(){
		  paquetes_comprados= new ArrayList<String>();
   }
   
   public boolean hayPaquetesComprados(){
	   return !paquetes_comprados.isEmpty();
   }
   
   public List<String> getPaquetes_comprados() {
	return paquetes_comprados;
   }
  
  public void agregarPaqueteCarrito(String nombre_paquete){
	  paquetes_comprados.add(nombre_paquete);
  }
  public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio+this.precio;
	}
	public void anularCanasta(){
		paquetes_comprados.clear();
	}
   
}
