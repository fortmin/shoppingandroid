package com.fortmin.proshopping;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.fortmin.proshopping.gae.Nube;
import com.fortmin.proshopping.gae.SeguridadNube;
import com.fortmin.proshopping.logica.seguridad.model.Mensaje;

public class Login extends Activity {
	private EditText nom_user;
	private EditText pass;
	private String nom_usuario;
	private Usuario user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!verificaConexion(this)) {
			mostrarMensaje("Para Usar la APP necesita estar conectado a internet");
			this.finish();
		}
		setContentView(R.layout.activity_login);
		nom_user = (EditText) findViewById(R.id.usuario);
		pass = (EditText) findViewById(R.id.pass);
		ImageButton login = (ImageButton) findViewById(R.id.btnLogeo);
		int blanco = R.color.white;
		nom_user.setTextColor(blanco);
		pass.setTextColor(blanco);
		login.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {

				Nube comNube = new Nube(SeguridadNube.OPE_LOGIN_USUARIO);
				nom_usuario = nom_user.getText().toString();
				Mensaje resp = comNube.ejecutarLogin(pass.getText().toString(),
						nom_usuario);
				if (resp != null) {
					String mensaje = resp.getMensaje(); // Respuesta puede ser
														// OK o
														// USUARIO_INEXISTENTE o
														// CLAVE_INCORRECTA
					if (mensaje.equals("OK")) {

						SharedPreferences prefs = getSharedPreferences(
								"configuracion", Context.MODE_PRIVATE);
						SharedPreferences.Editor editor = prefs.edit();
						editor.putString("Usuario", nom_usuario = nom_user
								.getText().toString());
						editor.commit();

						user = Usuario.getInstance();
						user.setNombre(nom_usuario);
						mostrarMensaje("Bienvenido " + nom_usuario);
						verOpciones();
					} else if (mensaje.contains("USUARIO_INEXISTENTE"))
						mostrarMensaje("USUARIO NO REGISTRADO");
					else if (mensaje.contains("CLAVE_INCORRECTA"))
						mostrarMensaje("CLAVE INCORRECTA ");
				}

			}

		});

	}

	@Override
	protected void onPause() {
		super.onPause();
		this.finish();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inicio, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void mostrarMensaje(String mensaje) {
		Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG)
				.show();
	}

	public void verOpciones() {
		Intent opciones = new Intent(this, LecturaRF.class);
		startActivity(opciones);

	}

	// para chequear si tiene conexion a internet
	public static boolean verificaConexion(Context ctx) {
		boolean bConectado = false;
		ConnectivityManager connec = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		// No sólo wifi, también GPRS
		NetworkInfo[] redes = connec.getAllNetworkInfo();
		for (int i = 0; i < 2; i++) {
			// true si hay conexión
			if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
				bConectado = true;
			}
		}
		return bConectado;
	}

}
