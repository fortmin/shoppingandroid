package com.fortmin.proshopping;

public class Usuario {
  private String nombre;
  private static Usuario instancia;
  private Usuario(){
	  
  }
public static Usuario getInstance(){
	if(instancia==null)
		instancia= new Usuario();
	return instancia;
	
}
public String getNombre() {
	return nombre;
}

public void setNombre(String nombre) {
	this.nombre = nombre;
}
  
}
