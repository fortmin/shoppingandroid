package com.fortmin.proshopping;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class Inicio extends Activity {
	private String nombre;
	private ProgressDialog pDialog;
	private ImageButton usuarioR;
	private ImageButton usuarioNR;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inicio);
		usuarioR = (ImageButton) findViewById(R.id.btnusuarioR);
		usuarioNR = (ImageButton) findViewById(R.id.btnusuarioNR);

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
			mostrarMensaje("No tiene conecición a internet");
			this.finish();
		}

		usuarioR.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				startAnimation(usuarioR);
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
				startAnimation(usuarioR);
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

		getMenuInflater().inflate(R.menu.inicio, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		/*
		 * controlador del action bar, esta ligado al manifest
		 */

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

	public void mostrarDialogo(String mensaje, String title) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(mensaje)
				.setTitle(title)
				.setCancelable(false)
				.setNeutralButton("Aceptar",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
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

	public void startAnimation(ImageView ivDH) {

		Animation rotateAnim = new RotateAnimation(0, 360);
		rotateAnim.setDuration(5000);
		rotateAnim.setRepeatCount(1);
		rotateAnim.setInterpolator(new AccelerateInterpolator());
		rotateAnim.setRepeatMode(Animation.REVERSE);

		ivDH.startAnimation(rotateAnim);
	}

}
