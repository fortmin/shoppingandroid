package com.fortmin.proshopping;

public class tipoNFC {
 String tipo;
 private static tipoNFC instancia;
 private tipoNFC(){
	  
 }
 public static tipoNFC getInstance(){
		if(instancia==null)
			instancia= new tipoNFC();
		return instancia;
		
	}

public String getTipo() {
	return tipo;
}

public void setTipo(String tipo) {
	this.tipo = tipo;
}
}
