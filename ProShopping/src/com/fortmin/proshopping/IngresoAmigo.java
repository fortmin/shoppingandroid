package com.fortmin.proshopping;

public class IngresoAmigo {
	private boolean ingreso = false;
	private static IngresoAmigo instancia;

	private IngresoAmigo() {

	}

	public static IngresoAmigo getInstance() {
		if (instancia == null)
			instancia = new IngresoAmigo();
		return instancia;

	}

	public boolean getIngreso() {
		return ingreso;
	}

	public void setIngreso(boolean ing) {
		this.ingreso = ing;
	}

}
