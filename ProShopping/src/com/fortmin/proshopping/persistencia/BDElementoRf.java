package com.fortmin.proshopping.persistencia;

public class BDElementoRf {

	private String elementoRf;
	private String tipo;
	private int rssi;
	private String paquete;

	public BDElementoRf(String elementoRf, String tipo, int rssi, String paquete) {
		this.elementoRf = elementoRf;
		this.tipo = tipo;
		this.rssi = rssi;
		this.paquete = paquete;
	}

	public BDElementoRf(String elementoRf, int rssi) {
		this.elementoRf = elementoRf;
		this.tipo = null;
		this.rssi = rssi;
		this.paquete = null;
	}

	public String getElementoRf() {
		return elementoRf;
	}

	public void setElementoRf(String elementoRf) {
		this.elementoRf = elementoRf;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getRssi() {
		return rssi;
	}

	public void setRssi(int rssi) {
		this.rssi = rssi;
	}

	public String getPaquete() {
		return paquete;
	}

	public void setPaquete(String paquete) {
		this.paquete = paquete;
	}

}
