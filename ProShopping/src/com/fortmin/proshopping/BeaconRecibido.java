package com.fortmin.proshopping;

import com.fortmin.proshopapi.ble.Ibeacon;

public class BeaconRecibido {
private Ibeacon beacon;
private static BeaconRecibido instancia;
private BeaconRecibido(){
	  
}
public static BeaconRecibido getInstance(){
		if(instancia==null)
			instancia= new BeaconRecibido();
		return instancia;
		
	}
public Ibeacon getBeacon() {
	return beacon;
}
public void setBeacon(Ibeacon beacon) {
	this.beacon = beacon;
}
public String darNombreBeacon(){
	return beacon.getValor_nombre();
}

}
