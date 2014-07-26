package com.fortmin.proshopping;

public class IbeaconRecibidos {
	 String UUID;
	 
	 private static IbeaconRecibidos instancia;
	 private IbeaconRecibidos(){
		 
	 }
	 public static IbeaconRecibidos getInstance(){
			if(instancia==null)
				instancia= new IbeaconRecibidos();
			return instancia;
			
		}
    
	public String getTipo() {
		return UUID;
	}
	
	
}

