package com.fortmin.proshopping.gae;

import java.io.IOException;

import android.os.AsyncTask;
import android.util.Log;

import com.fortmin.proshopping.CloudEndpointUtils;
import com.fortmin.proshopping.logica.gestion.Gestion;
import com.fortmin.proshopping.logica.gestion.Gestion.Cargarimagen;
import com.fortmin.proshopping.logica.gestion.model.Imagen;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson.JacksonFactory;

public class GestionNube extends AsyncTask<Object, Void, Object> {

	public static String OPE_CARGAR_IMAGEN = "CargarImagen";

	private String TAG = "ProShopping";
	private String operacion; // Señala el nombre de la operacion a ejecutar

	public GestionNube(String operacion) {
		this.operacion = operacion;
	}

	@Override
	protected Object doInBackground(Object... params) {
		Gestion.Builder endpointBuilder = new Gestion.Builder(
				AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
				new HttpRequestInitializer() {
					public void initialize(HttpRequest httpRequest) {
					}
				});
		endpointBuilder.setApplicationName("fortminproshop"); // Nombre de la
																// aplicacion
																// GAE
		Gestion endpoint = CloudEndpointUtils.updateBuilder(endpointBuilder)
				.build();
		try {
			if (operacion.equals(OPE_CARGAR_IMAGEN)) {
				String comercio = (String) params[0];
				String producto = (String) params[1];
				Imagen imagen = (Imagen) params[2];
				Log.i(this.TAG,
						"ShoppingNube->" + OPE_CARGAR_IMAGEN + "->" + comercio
								+ "::" + producto + "->" + imagen.getImagen());
				Cargarimagen execgae = endpoint.cargarimagen(comercio,
						producto, imagen);
				execgae.execute();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
