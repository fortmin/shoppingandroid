package com.fortmin.proshopping.gae;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.util.Log;

import com.fortmin.proshopping.logica.gestion.model.Imagen;
import com.fortmin.proshopping.logica.shopping.model.Paquete;
import com.fortmin.proshopping.logica.shopping.model.PaqueteVO;
import com.fortmin.proshopping.logica.shopping.model.Producto;
import com.fortmin.proshopping.logica.shopping.model.ProductoExtVO;

public class Nube {

	private String TAG = "ProShopping";
	private String operacion; // Señala el nombre de la operacion a ejecutar

	public Nube(String operacion) {
		this.setOperacion(operacion);
	}

	public com.fortmin.proshopping.logica.seguridad.model.Mensaje ejecutarLogin(
			String clave, String usuario) {
		com.fortmin.proshopping.logica.seguridad.model.Mensaje resp = null;
		SeguridadNube comNube = new SeguridadNube(
				SeguridadNube.OPE_LOGIN_USUARIO);
		try {
			resp = (com.fortmin.proshopping.logica.seguridad.model.Mensaje) comNube
					.execute(clave, usuario).get();
		} catch (InterruptedException e) {
			Log.e(TAG, e.toString());
		} catch (ExecutionException e) {
			Log.e(TAG, e.toString());
		}

		return resp;
	}

	public com.fortmin.proshopping.logica.seguridad.model.Mensaje ejecutarLogoff(
			String usuario) {
		com.fortmin.proshopping.logica.seguridad.model.Mensaje resp = null;
		SeguridadNube comNube = new SeguridadNube(
				SeguridadNube.OPE_LOGOFF_USUARIO);
		try {
			resp = (com.fortmin.proshopping.logica.seguridad.model.Mensaje) comNube
					.execute(usuario).get();
		} catch (InterruptedException e) {
			Log.e(TAG, e.toString());
		} catch (ExecutionException e) {
			Log.e(TAG, e.toString());
		}
		return resp;
	}

	public com.fortmin.proshopping.logica.seguridad.model.Mensaje ejecutarRegistro(
			String usuario, String email, String nombre) {
		com.fortmin.proshopping.logica.seguridad.model.Mensaje resp = null;
		SeguridadNube comNube = new SeguridadNube(
				SeguridadNube.OPE_REGISTRO_USUARIO);
		try {
			resp = (com.fortmin.proshopping.logica.seguridad.model.Mensaje) comNube
					.execute(usuario, email, nombre).get();
		} catch (InterruptedException e) {
			Log.e(TAG, e.toString());
		} catch (ExecutionException e) {
			Log.e(TAG, e.toString());
		}
		return resp;
	}

	public Paquete ejecutarGetPaqueteRf(String elemRf) {
		Paquete resp = null;
		ShoppingNube comNube = new ShoppingNube(ShoppingNube.OPE_GET_PAQUETE_RF);
		try {
			resp = (Paquete) comNube.execute(elemRf).get();
		} catch (InterruptedException e) {
			Log.e(TAG, e.toString());
		} catch (ExecutionException e) {
			Log.e(TAG, e.toString());
		}
		return resp;
	}

	public com.fortmin.proshopping.logica.shopping.model.Imagen ejecutarGetImagen(
			String comercio, String producto) {
		com.fortmin.proshopping.logica.shopping.model.Imagen resp = null;
		ShoppingNube comNube = new ShoppingNube(ShoppingNube.OPE_GET_IMAGEN);
		try {
			resp = (com.fortmin.proshopping.logica.shopping.model.Imagen) comNube
					.execute(comercio, producto).get();
		} catch (InterruptedException e) {
			Log.e(TAG, e.toString());
		} catch (ExecutionException e) {
			Log.e(TAG, e.toString());
		}
		return resp;
	}

	public ProductoExtVO ejecutarGetProductoCompleto(String comercio,
			String codprod) {
		ProductoExtVO resp = null;
		ShoppingNube comNube = new ShoppingNube(
				ShoppingNube.OPE_GET_PRODUCTO_COMPLETO);
		try {
			resp = (ProductoExtVO) comNube.execute(comercio, codprod).get();
		} catch (InterruptedException e) {
			Log.e(TAG, e.toString());
		} catch (ExecutionException e) {
			Log.e(TAG, e.toString());
		}
		return resp;
	}

	public PaqueteVO ejecutarGetPaqueteCompleto(String elemRf) {
		PaqueteVO resp = null;
		ShoppingNube comNube = new ShoppingNube(
				ShoppingNube.OPE_GET_PAQUETE_COMPLETO);
		try {
			resp = (PaqueteVO) comNube.execute(elemRf).get();
		} catch (InterruptedException e) {
			Log.e(TAG, e.toString());
		} catch (ExecutionException e) {
			Log.e(TAG, e.toString());
		}
		return resp;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Producto> ejecutarGetProductosPaquete(String paquete) {
		ArrayList<Producto> resp = null;
		ShoppingNube comNube = new ShoppingNube(
				ShoppingNube.OPE_GET_PRODUCTOS_PAQUETE);
		try {
			resp = (ArrayList<Producto>) comNube.execute(paquete).get();
		} catch (InterruptedException e) {
			Log.e(TAG, e.toString());
		} catch (ExecutionException e) {
			Log.e(TAG, e.toString());
		}
		return resp;
	}

	public com.fortmin.proshopping.logica.shopping.model.Mensaje ejecutarIngresoEstacionamiento(
			String elemRf, String usuario) {
		com.fortmin.proshopping.logica.shopping.model.Mensaje resp = null;
		ShoppingNube comNube = new ShoppingNube(
				ShoppingNube.OPE_INGRESO_ESTACIONAMIENTO);
		try {
			resp = (com.fortmin.proshopping.logica.shopping.model.Mensaje) comNube
					.execute(elemRf, usuario).get();
		} catch (InterruptedException e) {
			Log.e(TAG, e.toString());
		} catch (ExecutionException e) {
			Log.e(TAG, e.toString());
		}
		return resp;
	}

	public com.fortmin.proshopping.logica.shopping.model.Mensaje ejecutarEgresoEstacionamiento(
			String elemRf, String usuario) {
		com.fortmin.proshopping.logica.shopping.model.Mensaje resp = null;
		ShoppingNube comNube = new ShoppingNube(
				ShoppingNube.OPE_EGRESO_ESTACIONAMIENTO);
		try {
			resp = (com.fortmin.proshopping.logica.shopping.model.Mensaje) comNube
					.execute(elemRf, usuario).get();
		} catch (InterruptedException e) {
			Log.e(TAG, e.toString());
		} catch (ExecutionException e) {
			Log.e(TAG, e.toString());
		}
		return resp;
	}

	public com.fortmin.proshopping.logica.shopping.model.Mensaje ejecutarGetCalibradoBeacon(
			String elemRf) {
		com.fortmin.proshopping.logica.shopping.model.Mensaje resp = null;
		ShoppingNube comNube = new ShoppingNube(
				ShoppingNube.OPE_GET_CALIBRADO_BEACON);
		try {
			resp = (com.fortmin.proshopping.logica.shopping.model.Mensaje) comNube
					.execute(elemRf).get();
		} catch (InterruptedException e) {
			Log.e(TAG, e.toString());
		} catch (ExecutionException e) {
			Log.e(TAG, e.toString());
		}
		return resp;
	}

	public void ejecutarCargarImagen(String comercio, String producto,
			Imagen imagen) {
		GestionNube comNube = new GestionNube(GestionNube.OPE_CARGAR_IMAGEN);
		try {
			comNube.execute(comercio, producto, imagen).get();
		} catch (InterruptedException e) {
			Log.e(TAG, e.toString());
		} catch (ExecutionException e) {
			Log.e(TAG, e.toString());
		}
	}
	
	/*
	 * Obtiene el puntaje del cliente y lo devuelve en Mensaje
	 */
	public com.fortmin.proshopping.logica.shopping.model.Mensaje ejecutarGetPuntajeCliente(
			String usuario) {
		com.fortmin.proshopping.logica.shopping.model.Mensaje resp = null;
		ShoppingNube comNube = new ShoppingNube(
				ShoppingNube.OPE_GET_PUNTAJE_CLIENTE);
		try {
			resp = (com.fortmin.proshopping.logica.shopping.model.Mensaje) comNube
					.execute(usuario).get();
		} catch (InterruptedException e) {
			Log.e(TAG, e.toString());
		} catch (ExecutionException e) {
			Log.e(TAG, e.toString());
		}
		return resp;
	}


	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

}
