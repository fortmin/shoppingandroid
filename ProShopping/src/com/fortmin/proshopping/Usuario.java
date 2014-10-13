package com.fortmin.proshopping;

public class Usuario {
	private String nombre;
	private int numero_imagen = 0;
	private static Usuario instancia;

	private Usuario() {

	}

	public static Usuario getInstance() {
		if (instancia == null)
			instancia = new Usuario();
		return instancia;

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getNumero_imagen() {
		return numero_imagen;
	}

	public void setNumero_imagen(int numero_imagen) {

		this.numero_imagen = numero_imagen;
	}

}
