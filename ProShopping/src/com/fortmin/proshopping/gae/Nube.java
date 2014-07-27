package com.fortmin.proshopping.gae;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.fortmin.proshopping.logica.shopping.model.Paquete;
import com.fortmin.proshopping.logica.shopping.model.PaqueteVO;
import com.fortmin.proshopping.logica.shopping.model.Producto;

import android.util.Log;

public class Nube {

	private String TAG = "ProShopping";
	private String operacion; // Señala el nombre de la operacion a ejecutar
	
	public Nube(String operacion) {
		this.setOperacion(operacion);
	}
	
	public com.fortmin.proshopping.logica.seguridad.model.Mensaje ejecutarLogin(String clave, String usuario) {
		com.fortmin.proshopping.logica.seguridad.model.Mensaje resp = null;
		SeguridadNube comNube = new SeguridadNube(SeguridadNube.OPE_LOGIN_USUARIO);
		try {
			resp = (com.fortmin.proshopping.logica.seguridad.model.Mensaje) comNube.execute(clave,usuario).get();
		} catch (InterruptedException e) {
			Log.e(TAG,e.toString());
		} catch (ExecutionException e) {
			Log.e(TAG,e.toString());
		}

		return resp;
	}

	public com.fortmin.proshopping.logica.seguridad.model.Mensaje ejecutarLogoff(String usuario) {
		com.fortmin.proshopping.logica.seguridad.model.Mensaje resp = null;
		SeguridadNube comNube = new SeguridadNube(SeguridadNube.OPE_LOGOFF_USUARIO);
		try {
			resp = (com.fortmin.proshopping.logica.seguridad.model.Mensaje) comNube.execute(usuario).get();
		} catch (InterruptedException e) {
			Log.e(TAG,e.toString());
		} catch (ExecutionException e) {
			Log.e(TAG,e.toString());
		}
		return resp;
	}
	
	public com.fortmin.proshopping.logica.seguridad.model.Mensaje ejecutarRegistro(String usuario, String email, String nombre) {
		com.fortmin.proshopping.logica.seguridad.model.Mensaje resp = null;
		SeguridadNube comNube = new SeguridadNube(SeguridadNube.OPE_REGISTRO_USUARIO);
		try {
			resp = (com.fortmin.proshopping.logica.seguridad.model.Mensaje) comNube.execute(usuario,email,nombre).get();
		} catch (InterruptedException e) {
			Log.e(TAG,e.toString());
		} catch (ExecutionException e) {
			Log.e(TAG,e.toString());
		}
		return resp;
	}
	
	public Paquete ejecutarGetPaqueteRf(String elemRf) {
		Paquete resp = null;
		ShoppingNube comNube = new ShoppingNube(ShoppingNube.OPE_GET_PAQUETE_RF);
		try {
			resp = (Paquete) comNube.execute(elemRf).get();
		} catch (InterruptedException e) {
			Log.e(TAG,e.toString());
		} catch (ExecutionException e) {
			Log.e(TAG,e.toString());
		}
		return resp;
	}

	public PaqueteVO ejecutarGetPaqueteCompleto(String elemRf) {
		PaqueteVO resp = null;
		ShoppingNube comNube = new ShoppingNube(ShoppingNube.OPE_GET_PAQUETE_COMPLETO);
		try {
			resp = (PaqueteVO) comNube.execute(elemRf).get();
		} catch (InterruptedException e) {
			Log.e(TAG,e.toString());
		} catch (ExecutionException e) {
			Log.e(TAG,e.toString());
		}
		return resp;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Producto> ejecutarGetProductosPaquete(String paquete) {
		ArrayList<Producto> resp = null;
		ShoppingNube comNube = new ShoppingNube(ShoppingNube.OPE_GET_PRODUCTOS_PAQUETE);
		try {
			resp = (ArrayList<Producto>) comNube.execute(paquete).get();
		} catch (InterruptedException e) {
			Log.e(TAG,e.toString());
		} catch (ExecutionException e) {
			Log.e(TAG,e.toString());
		}
		return resp;
	}

	public com.fortmin.proshopping.logica.shopping.model.Mensaje ejecutarIngresoEstacionamiento(String elemRf, String usuario) {
		com.fortmin.proshopping.logica.shopping.model.Mensaje resp = null;
		ShoppingNube comNube = new ShoppingNube(ShoppingNube.OPE_INGRESO_ESTACIONAMIENTO);
		try {
			resp = (com.fortmin.proshopping.logica.shopping.model.Mensaje) comNube.execute(elemRf,usuario).get();
		} catch (InterruptedException e) {
			Log.e(TAG,e.toString());
		} catch (ExecutionException e) {
			Log.e(TAG,e.toString());
		}
		return resp;
	}

	public com.fortmin.proshopping.logica.shopping.model.Mensaje ejecutarEgresoEstacionamiento(String elemRf, String usuario) {
		com.fortmin.proshopping.logica.shopping.model.Mensaje resp = null;
		ShoppingNube comNube = new ShoppingNube(ShoppingNube.OPE_EGRESO_ESTACIONAMIENTO);
		try {
			resp = (com.fortmin.proshopping.logica.shopping.model.Mensaje) comNube.execute(elemRf,usuario).get();
		} catch (InterruptedException e) {
			Log.e(TAG,e.toString());
		} catch (ExecutionException e) {
			Log.e(TAG,e.toString());
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
