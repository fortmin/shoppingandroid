package com.fortmin.proshopping;

public class usuario {
  private String nombre;
  private static usuario instancia;
  private usuario(){
	  
  }
public static usuario getInstance(){
	if(instancia==null)
		instancia= new usuario();
	return instancia;
	
}
public String getNombre() {
	return nombre;
}

public void setNombre(String nombre) {
	this.nombre = nombre;
}
  
}
