package com.fortmin.proshopping.gae;

import java.io.IOException;
import java.util.List;

import android.os.AsyncTask;
import android.util.Log;

import com.fortmin.proshopping.CloudEndpointUtils;
import com.fortmin.proshopping.shopping.Shopping;
import com.fortmin.proshopping.shopping.Shopping.GetPaqueteRf;
import com.fortmin.proshopping.shopping.Shopping.GetProductosPaquete;
import com.fortmin.proshopping.shopping.Shopping.Insertcomercio;
import com.fortmin.proshopping.shopping.Shopping.LoginUsuario;
import com.fortmin.proshopping.shopping.Shopping.LogoffUsuario;
import com.fortmin.proshopping.shopping.Shopping.RegistroUsuario;
import com.fortmin.proshopping.shopping.model.Mensaje;
import com.fortmin.proshopping.shopping.model.Paquete;
import com.fortmin.proshopping.shopping.model.Producto;
import com.fortmin.proshopping.shopping.model.ProductoCollection;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson.JacksonFactory;

public class ShoppingNube extends AsyncTask<Object, Void, Object> {

	public static String OPE_GET_PAQUETE_RF = "GetPaqueteRf";
	public static String OPE_GET_PRODUCTOS_PAQUETE = "GetProductosPaquete";
	public static String OPE_INSERT_COMERCIO = "InsertComercio";
	public static String OPE_REGISTRO_USUARIO = "RegistroUsuario";
	public static String OPE_LOGIN_USUARIO = "LoginUsuario";
	public static String OPE_LOGOFF_USUARIO = "LogoffUsuario";

	private String TAG = "ProShopping";
	private String operacion; // Señala el nombre de la operacion a ejecutar

	public ShoppingNube(String operacion) {
		this.operacion = operacion;
	}

	@Override
	protected Object doInBackground(Object... params) {
		Shopping.Builder endpointBuilder = new Shopping.Builder(
				AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
				new HttpRequestInitializer() {
					public void initialize(HttpRequest httpRequest) {
					}
				});
		endpointBuilder.setApplicationName("fortminproshop"); // Nombre de la
																// aplicacion
																// GAE
		Shopping endpoint = CloudEndpointUtils.updateBuilder(endpointBuilder)
				.build();
		try {
			if (operacion.equals(OPE_GET_PAQUETE_RF)) {
				String idElementoRF = (String) params[0];
				Log.i(this.TAG, "ShoppingNube->" + OPE_GET_PAQUETE_RF + "->"
						+ idElementoRF);
				GetPaqueteRf execgae = endpoint.getPaqueteRf(idElementoRF);
				Paquete paquete = execgae.execute();
				Log.i(TAG,
						"ShoppingNube->Obtuve el paquete : "
								+ paquete.getNombre());
				return paquete;
			}
			if (operacion.equals(OPE_GET_PRODUCTOS_PAQUETE)) {
				String nomPaquete = (String) params[0];
				Log.i(this.TAG, "ShoppingNube->" + OPE_GET_PRODUCTOS_PAQUETE
						+ "->" + nomPaquete);
				GetProductosPaquete execgae = endpoint
						.getProductosPaquete(nomPaquete);
				ProductoCollection resp = execgae.execute();
				List<Producto> productos = resp.getItems();
				Log.i(TAG, "ShoppingNube->Obtuve cantidad de productos : "
						+ productos.size());
				return productos;
			}
			if (operacion.equals(OPE_REGISTRO_USUARIO)) {
				String nomUsuario = (String) params[0];
				String email = (String) params[1];
				String nombre = (String) params[2];
				Log.i(this.TAG, "ShoppingNube->" + OPE_REGISTRO_USUARIO + "->"
						+ nomUsuario + "::" + email + "::" + nombre);
				RegistroUsuario execgae = endpoint.registroUsuario(email,nombre,nomUsuario);
				Mensaje resp = execgae.execute();
				Log.i(TAG,
						"ShoppingNube->" + resp.getOperacion() + "->"
								+ resp.getMensaje());
				return resp;
			}
			if (operacion.equals(OPE_LOGIN_USUARIO)) {
				String nomUsuario = (String) params[0];
				String clave = (String) params[1];
				Log.i(this.TAG, "ShoppingNube->" + OPE_LOGIN_USUARIO + "->"
						+ nomUsuario);
				LoginUsuario execgae = endpoint.loginUsuario(nomUsuario, clave);
				Mensaje resp = execgae.execute();
				Log.i(TAG,
						"ShoppingNube->" + resp.getOperacion() + "->"
								+ resp.getMensaje());
				return resp;
			}
			if (operacion.equals(OPE_LOGOFF_USUARIO)) {
				String nomUsuario = (String) params[0];
				String clave = (String) params[1];
				Log.i(this.TAG, "ShoppingNube->" + OPE_LOGOFF_USUARIO + "->"
						+ nomUsuario);
				LogoffUsuario execgae = endpoint.logoffUsuario(nomUsuario,
						clave);
				Mensaje resp = execgae.execute();
				Log.i(TAG,
						"ShoppingNube->" + resp.getOperacion() + "->"
								+ resp.getMensaje());
				return resp;
			}
			if (operacion.equals(OPE_INSERT_COMERCIO)) {
				String nomComercio = (String) params[0];
				String nomUbicacion = (String) params[1];
				Log.i(this.TAG, "ShoppingNube->" + OPE_INSERT_COMERCIO + "->"
						+ nomComercio + "->" + nomUbicacion);
				Insertcomercio execgae = endpoint.insertcomercio(nomComercio,
						nomUbicacion);
				execgae.execute();
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * @SuppressWarnings("unchecked") protected void onPostExecute(Object
	 * result) { if (result != null) { if (operacion.equals(OPE_GET_PAQUETE_RF))
	 * { paquete = (Paquete) result; } else if
	 * (operacion.equals(OPE_GET_PRODUCTOS_PAQUETE)) { productos =
	 * (LinkedList<Producto>) result; } } }
	 */

}
