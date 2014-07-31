package com.fortmin.proshopping.gae;

import java.io.IOException;

import android.os.AsyncTask;
import android.util.Log;

import com.fortmin.proshopping.CloudEndpointUtils;
import com.fortmin.proshopping.logica.seguridad.Seguridad;
import com.fortmin.proshopping.logica.seguridad.Seguridad.LoginUsuario;
import com.fortmin.proshopping.logica.seguridad.Seguridad.LogoffUsuario;
import com.fortmin.proshopping.logica.seguridad.Seguridad.RegistroUsuario;
import com.fortmin.proshopping.logica.seguridad.model.Mensaje;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson.JacksonFactory;

public class SeguridadNube extends AsyncTask<Object, Void, Object> {
	
	public static String OPE_REGISTRO_USUARIO = "RegistroUsuario";
	public static String OPE_LOGIN_USUARIO = "LoginUsuario";
	public static String OPE_LOGOFF_USUARIO = "LogoffUsuario";

	private String TAG = "ProShopping";
	private String operacion; // Señala el nombre de la operacion a ejecutar

	public SeguridadNube(String operacion) {
		this.operacion = operacion;
	}

	@Override
	protected Object doInBackground(Object... params) {
		Seguridad.Builder endpointBuilder = new Seguridad.Builder(
				AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
				new HttpRequestInitializer() {
					public void initialize(HttpRequest httpRequest) {
					}
				});
		endpointBuilder.setApplicationName("fortminproshop"); // Nombre de la
																// aplicacion
																// GAE
		Seguridad endpoint = CloudEndpointUtils.updateBuilder(endpointBuilder)
				.build();
		try {
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
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
