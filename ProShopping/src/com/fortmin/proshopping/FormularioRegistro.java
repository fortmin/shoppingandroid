package com.fortmin.proshopping;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
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

public class FormularioRegistro extends Activity {
	private EditText nombre;
	private EditText e_mail;
	private EditText user;
	private Usuario user_guardado;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_formulario_registro);
		nombre = (EditText) findViewById(R.id.registronombre);
		e_mail = (EditText) findViewById(R.id.registroemail);
		user = (EditText) findViewById(R.id.registrousuario);
		ImageButton registro = (ImageButton) findViewById(R.id.btnRegistro);
		Typeface fuente = Typeface.createFromAsset(getAssets(),
				"Roboto-Regular.ttf");
		user.setTypeface(fuente);
		e_mail.setTypeface(fuente);
		nombre.setTypeface(fuente);

		registro.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Nube comNube = new Nube(SeguridadNube.OPE_REGISTRO_USUARIO);
				Mensaje resp = comNube.ejecutarRegistro(user.getText()
						.toString(), e_mail.getText().toString(), nombre
						.getText().toString());
				if (resp != null) {
					String mensaje = resp.getMensaje(); // Respuesta puede ser
														// OK o
														// USUARIO_INEXISTENTE o
														// CLAVE_INCORRECTA
					if (mensaje.equals("OK")) {
						SharedPreferences prefs = getSharedPreferences(
								"configuracion", Context.MODE_PRIVATE);
						SharedPreferences.Editor editor = prefs.edit();
						editor.putString("Usuario", user.getText().toString());
						editor.commit();
						user_guardado = Usuario.getInstance();
						user_guardado.setNombre(user.getText().toString());
						mostrarMensaje("Registrado correctamente");
						verOpciones();
					} else if (mensaje.contains("USUARIO_EXISTENTE"))
						mostrarMensaje("Usuario Ya registrado");

				}

			}

		});

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
}
