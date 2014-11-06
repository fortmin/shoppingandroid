package com.fortmin.proshopping;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fortmin.proshopping.logica.shopping.model.PaqueteVO;

/* Clase Singleton con el contenido de las compras realizadas
 * cuya finalidad es tener una persistencia local
 */
public class CanastaCompras {
	private static CanastaCompras instancia;
	private List<PaqueteVO> paquetes_comprados;
	private float precio;
	private int puntos;

	public static CanastaCompras getInstance() {
		if (instancia == null)
			instancia = new CanastaCompras();
		return instancia;

	}

	private CanastaCompras() {
		paquetes_comprados = new ArrayList<PaqueteVO>();
	}

	public boolean hayPaquetesComprados() {
		return !paquetes_comprados.isEmpty();
	}

	public List<PaqueteVO> getPaquetes_comprados() {
		return paquetes_comprados;
	}

	public void agregarPaqueteCarrito(PaqueteVO nombre_paquete) {
		paquetes_comprados.add(nombre_paquete);
	}

	public void anularCanasta() {
		paquetes_comprados.clear();
	}

	public void eliminarPaquete(String nomPaq) {
		Iterator<PaqueteVO> iterator = paquetes_comprados.iterator();
		boolean termine = false;
		while (!termine) {
			if (iterator.hasNext()) {
				PaqueteVO paquete = iterator.next();
				if (paquete.equals(nomPaq)) {
					precio = precio - paquete.getPrecio();
					puntos = puntos - paquete.getPuntos();
					iterator.remove();
					termine = true;
				}
			} else
				termine = true;
		}
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

}
