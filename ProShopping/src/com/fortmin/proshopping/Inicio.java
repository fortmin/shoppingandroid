package com.fortmin.proshopping;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class Inicio extends Activity {
	private String nombre;
	ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inicio);
		ImageButton usuarioR = (ImageButton) findViewById(R.id.btnusuarioR);
		ImageButton usuarioNR = (ImageButton) findViewById(R.id.btnusuarioNR);

		SharedPreferences prefs = getSharedPreferences("configuracion",
				MODE_PRIVATE);
		nombre = prefs.getString("Usuario", "no existe");
		if (!nombre.equals("no existe")) {
			Usuario user = Usuario.getInstance();
			user.setNombre(nombre);
			mostrarMensaje("Bienvenido " + nombre);
			verOpciones();
		}
		if (!verificaConexion(this)) {
			mostrarMensaje("Para Usar la APP necesita estar conectado a internet");
			this.finish();
		}

		usuarioR.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if (!nombre.equals("no existe")) {
					mostrarMensaje("Bienvenido " + nombre);
					verOpciones();
				} else {
					pDialog = new ProgressDialog(Inicio.this);
					pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
					pDialog.setMessage("Procesando...");

					TareaAsincrona iralanube = new TareaAsincrona();
					Void params = null;
					iralanube.execute(params);
				}
			}

		});
		usuarioNR.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				registrarse();
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

	public void logearse() {
		Intent logeo = new Intent(this, Login.class);
		startActivity(logeo);
	}

	public void registrarse() {
		Intent registro = new Intent(this, FormularioRegistro.class);
		startActivity(registro);
	}

	private void verOpciones() {
		// TODO Auto-generated method stub
		Intent opciones = new Intent(this, LecturaRF.class);
		startActivity(opciones);

		this.finish();
	}

	public void mostrarMensaje(String mensaje) {
		Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG)
				.show();
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

	private class TareaAsincrona extends AsyncTask<Void, Integer, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			logearse();
			return true;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {

		}

		@Override
		protected void onPreExecute() {

		}

		@Override
		protected void onPostExecute(Boolean result) {
			if (result)
				pDialog.dismiss();
		}

	}
}
