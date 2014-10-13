package com.fortmin.proshopping;

import java.util.ArrayList;
import java.util.Iterator;

import com.fortmin.proshopping.gae.Nube;
import com.fortmin.proshopping.gae.ShoppingNube;
import com.fortmin.proshopping.logica.shopping.model.ComprasVO;

public class ListadoCompras {
	private Usuario user;
	private ArrayList<ComprasVO> compras;
	private static ListadoCompras instancia;
	private boolean tienecompras = false;

	private ListadoCompras() {
		user = Usuario.getInstance();
		Nube miscompras = new Nube(ShoppingNube.OPE_GET_COMPRAS);
		compras = miscompras.getCompras(user.getNombre());
		if (compras.size() != 0)
			compras = miscompras.getCompras(user.getNombre());

		tienecompras = (compras.size() != 0);

	}

	public static ListadoCompras getInstance() {
		if (instancia == null)
			instancia = new ListadoCompras();

		return instancia;

	}

	public ArrayList<ComprasVO> getMisCompras() {
		return compras;
	}

	public datosCompra darDatosCompra(String idcompra) {
		datosCompra dcompra = new datosCompra();
		if (tienecompras) {
			Iterator<ComprasVO> icompras = compras.iterator();
			boolean termine = false;
			while (icompras.hasNext() && !termine) {
				ComprasVO p = icompras.next();
				String nombre = p.getCompra();
				if (nombre.equals(idcompra)) {
					termine = true;
					dcompra.setEntregado(p.getEntregada());
					dcompra.setPrecio(p.getPrecioTotal());
					dcompra.setPuntosgenerados((int) p.getPuntosOtorgados());
					dcompra.setTotalpaquetes((int) p.getCantItems());
				}

			}

		}
		return dcompra;

	}

	public boolean tieneCompras() {
		// true si realizo compras
		return tienecompras;
	}

	public void cargarCompras() {
		Nube miscompras = new Nube(ShoppingNube.OPE_GET_COMPRAS);
		compras = miscompras.getCompras(user.getNombre());
		tienecompras = (compras.size() != 0);

	}

	public class datosCompra {
		private boolean entregado;
		private int totalpaquetes, puntosgenerados;
		private float precio;

		public boolean isEntregado() {
			return entregado;
		}

		public void setEntregado(boolean entregado) {
			this.entregado = entregado;
		}

		public int getTotalpaquetes() {
			return totalpaquetes;
		}

		public void setTotalpaquetes(int totalpaquetes) {
			this.totalpaquetes = totalpaquetes;
		}

		public int getPuntosgenerados() {
			return puntosgenerados;
		}

		public void setPuntosgenerados(int puntosgenerados) {
			this.puntosgenerados = puntosgenerados;
		}

		public float getPrecio() {
			return precio;
		}

		public void setPrecio(float precio) {
			this.precio = precio;
		}

		public datosCompra() {
			super();
			// TODO Auto-generated constructor stub
		}

	}
}
