package com.fortmin.proshopping;

public class TagRecibido {
	private String nombre = "";
	private String nombre_paquete = "";

	private boolean atendido = true;
	private String tipo = "";
	private int rssi = 0;
	private boolean persitido = false;
	private static TagRecibido instancia;

	public static TagRecibido getInstance() {
		if (instancia == null)
			instancia = new TagRecibido();
		return instancia;

	}

	private TagRecibido() {

	}

	public boolean isPersitido() {
		return persitido;
	}

	public void setPersitido(boolean persitido) {
		this.persitido = persitido;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre_tag) {
		this.nombre = nombre_tag;
	}

	public boolean getAtendido() {
		return atendido;
	}

	public void setAtendido(boolean notificacion) {
		this.atendido = notificacion;
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

	public String getNombre_paquete() {
		return nombre_paquete;
	}

	public void setNombre_paquete(String nombre_paquete) {
		this.nombre_paquete = nombre_paquete;
	}

}
