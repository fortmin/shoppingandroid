package com.fortmin.proshopping.gae;

import java.io.IOException;
import java.util.List;

import android.os.AsyncTask;
import android.util.Log;

import com.fortmin.proshopping.CloudEndpointUtils;
import com.fortmin.proshopping.logica.shopping.Shopping;
import com.fortmin.proshopping.logica.shopping.Shopping.EgresoEstacionamiento;
import com.fortmin.proshopping.logica.shopping.Shopping.GetPaqueteCompleto;
import com.fortmin.proshopping.logica.shopping.Shopping.GetPaqueteRf;
import com.fortmin.proshopping.logica.shopping.Shopping.GetProductosPaquete;
import com.fortmin.proshopping.logica.shopping.Shopping.IngresoEstacionamiento;
import com.fortmin.proshopping.logica.shopping.model.Mensaje;
import com.fortmin.proshopping.logica.shopping.model.Paquete;
import com.fortmin.proshopping.logica.shopping.model.PaqueteVO;
import com.fortmin.proshopping.logica.shopping.model.Producto;
import com.fortmin.proshopping.logica.shopping.model.ProductoCollection;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson.JacksonFactory;

public class ShoppingNube extends AsyncTask<Object, Void, Object> {

	public static String OPE_GET_PAQUETE_RF = "GetPaqueteRf";
	public static String OPE_GET_PRODUCTOS_PAQUETE = "GetProductosPaquete";
	public static String OPE_GET_PAQUETE_COMPLETO = "GetPaqueteCompleto";
	public static String OPE_INGRESO_ESTACIONAMIENTO = "IngresoEstacionamiento";
	public static String OPE_EGRESO_ESTACIONAMIENTO = "EgresoEstacionamiento";

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
			if (operacion.equals(OPE_GET_PAQUETE_COMPLETO)) {
				String idElementoRF = (String) params[0];
				Log.i(this.TAG, "ShoppingNube->" + OPE_GET_PAQUETE_COMPLETO + "->"
						+ idElementoRF);
				GetPaqueteCompleto execgae = endpoint.getPaqueteCompleto(idElementoRF);
				PaqueteVO paquete = execgae.execute();
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
			if (operacion.equals(OPE_INGRESO_ESTACIONAMIENTO)) {
				String elementoRf = (String) params[0];
				String usuario = (String) params[1];
				Log.i(this.TAG, "ShoppingNube->" + OPE_INGRESO_ESTACIONAMIENTO + "->"
						+ elementoRf + "::" + usuario);
				IngresoEstacionamiento execgae = endpoint.ingresoEstacionamiento(elementoRf,
						usuario);
				Mensaje resp = execgae.execute();
				Log.i(TAG,"ShoppingNube->" + resp.getOperacion() + "->" + resp.getMensaje());
				return resp;
			}
			if (operacion.equals(OPE_EGRESO_ESTACIONAMIENTO)) {
				String elementoRf = (String) params[0];
				String usuario = (String) params[1];
				Log.i(this.TAG, "ShoppingNube->" + OPE_EGRESO_ESTACIONAMIENTO + "->"
						+ elementoRf + "::" + usuario);
				EgresoEstacionamiento execgae = endpoint.egresoEstacionamiento(elementoRf,
						usuario);
				Mensaje resp = execgae.execute();
				Log.i(TAG,"ShoppingNube->" + resp.getOperacion() + "->" + resp.getMensaje());
				return resp;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
