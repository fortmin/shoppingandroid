package com.fortmin.proshopping;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.fortmin.proshopping.gae.ShoppingNube;
import com.fortmin.proshopping.shopping.model.Mensaje;
import com.fortmin.proshopping.shopping.model.Paquete;
import com.fortmin.proshopping.shopping.model.Producto;

/**
 * The Main Activity.
 * 
 * This activity starts up the RegisterActivity immediately, which communicates
 * with your App Engine backend using Cloud Endpoints. It also receives push
 * notifications from backend via Google Cloud Messaging (GCM).
 * 
 * Check out RegisterActivity.java for more details.
 */
public class MainActivity extends Activity {

	private String TAG = "ProShopping";

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Start up RegisterActivity right away
		// Intent intent = new Intent(this, RegisterActivity.class);
		// startActivity(intent);

		Toast.makeText(getApplicationContext(), "Obteniendo paquete",
				Toast.LENGTH_SHORT).show();
		ShoppingNube comNube = new ShoppingNube(ShoppingNube.OPE_GET_PAQUETE_RF);
		Paquete paquete = null;
		try {
			paquete = (Paquete) comNube.execute("BEACON001").get();
		} catch (InterruptedException e1) {
			Log.e(TAG, ShoppingNube.OPE_GET_PAQUETE_RF + "InterruptedException");
		} catch (ExecutionException e1) {
			Log.e(TAG, ShoppingNube.OPE_GET_PRODUCTOS_PAQUETE
					+ "ExecutionException");
		}

		// Codigo para mostrar datos del paquete en el log
		if (paquete != null) {
			Log.i(TAG, "Nombre del paquete    : " + paquete.getNombre());
			Log.i(TAG, "Elemento RF asociado  : " + paquete.getElementoRF());
			Log.i(TAG, "Puntos del paquete    : " + paquete.getPuntos());
			Log.i(TAG, "Precio del paquete    : " + paquete.getPrecio());
			Log.i(TAG, "Cantidad de productos : " + paquete.getCantProductos());
			Log.i(TAG, "\n");
		}

		// Si pude obtener el paquete procedo a pedir la lista de productos
		if (paquete != null) {
			Toast.makeText(getApplicationContext(), "Obteniendo productos",
					Toast.LENGTH_SHORT).show();
			comNube = new ShoppingNube(ShoppingNube.OPE_GET_PRODUCTOS_PAQUETE);
			ArrayList<Producto> productos = null;
			try {
				productos = (ArrayList<Producto>) comNube.execute(
						paquete.getNombre()).get();
			} catch (InterruptedException e) {
				Log.e(TAG, ShoppingNube.OPE_GET_PRODUCTOS_PAQUETE
						+ "InterruptedException");
			} catch (ExecutionException e) {
				Log.e(TAG, ShoppingNube.OPE_GET_PRODUCTOS_PAQUETE
						+ "ExecutionException");
			}

			// Si pude obtener la lista de productos la muestro
			// Codigo para mostrar la lista de productos en el log
			if (productos != null) {
				Producto proxprod = null;
				Iterator<Producto> iprods = productos.iterator();
				while (iprods.hasNext()) {
					proxprod = iprods.next();
					Log.i(TAG, "----------------------------------------------");
					Log.i(TAG,
							"Codigo del producto   : " + proxprod.getCodigo());
					Log.i(TAG,
							"Comercio del producto : " + proxprod.getComercio());
					Log.i(TAG,
							"Nombre del producto   : " + proxprod.getNombre());
					Log.i(TAG,
							"Precio del producto   : " + proxprod.getPrecio());
				}
			}
		}

		// -----------------------------------------------------------------------
		// -----------------------------------------------------------------------
		// -----------------------------------------------------------------------
		// DE ACA PARA ABAJO ES TODO LO NUEVO...
		// -----------------------------------------------------------------------
		// -----------------------------------------------------------------------
		// -----------------------------------------------------------------------
		
		String nomUsuario = "jafortti";
		String email = "jafortti@gmail.com";
		String nombre = "Julio Fortti";
		String clave = "mundial";

		comNube = new ShoppingNube(ShoppingNube.OPE_REGISTRO_USUARIO);
		try {
			Mensaje resp = (Mensaje) comNube.execute(nomUsuario, email, nombre)
					.get();
			Log.i(TAG, "Respuesta = " + resp.getMensaje());
			// Respuesta puede ser OK o USUARIO_EXISTENTE (el usuario tiene que
			// elegir otro nombre de usuario)
		} catch (InterruptedException e) {
			Log.e(TAG, ShoppingNube.OPE_REGISTRO_USUARIO
					+ "InterruptedException");
		} catch (ExecutionException e) {
			Log.e(TAG, ShoppingNube.OPE_REGISTRO_USUARIO + "ExecutionException");
		}

		comNube = new ShoppingNube(ShoppingNube.OPE_LOGIN_USUARIO);
		try {
			Mensaje resp = (Mensaje) comNube.execute(nomUsuario, clave).get();
			Log.i(TAG, "Respuesta = " + resp.getMensaje());
			// Respuesta puede ser OK o USUARIO_INEXISTENTE o CLAVE_INCORRECTA
		} catch (InterruptedException e) {
			Log.e(TAG, ShoppingNube.OPE_LOGIN_USUARIO + "InterruptedException");
		} catch (ExecutionException e) {
			Log.e(TAG, ShoppingNube.OPE_LOGIN_USUARIO + "ExecutionException");
		}

		comNube = new ShoppingNube(ShoppingNube.OPE_LOGOFF_USUARIO);
		try {
			Mensaje resp = (Mensaje) comNube.execute(nomUsuario, clave).get();
			Log.i(TAG, "Respuesta = " + resp.getMensaje());
			// Respuesta puede ser OK o USUARIO_INEXISTENTE o CLAVE_INCORRECTA o
			// SIN_LOGIN_PREVIO
		} catch (InterruptedException e) {
			Log.e(TAG, ShoppingNube.OPE_LOGOFF_USUARIO + "InterruptedException");
		} catch (ExecutionException e) {
			Log.e(TAG, ShoppingNube.OPE_LOGOFF_USUARIO + "ExecutionException");
		}

	}

	/*
	 * public void onResume() {
	 * 
	 * 
	 * }
	 */

}
