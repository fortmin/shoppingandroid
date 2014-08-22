package com.fortmin.proshopping;

public class TipoNFC {
	String tipo;
	private static TipoNFC instancia;

	private TipoNFC() {

	}

	public static TipoNFC getInstance() {
		if (instancia == null)
			instancia = new TipoNFC();
		return instancia;

	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
