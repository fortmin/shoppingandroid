package com.fortmin.proshopping;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.fortmin.proshopping.gae.Nube;
import com.fortmin.proshopping.gae.SeguridadNube;
import com.fortmin.proshopping.logica.seguridad.model.Mensaje;

// Clase cuya finalidad es ver el formulario de registro de nuevo usuario
public class FormularioRegistro extends Activity {
	private EditText nombre;
	private EditText e_mail;
	private EditText user;
	private Usuario user_guardado;
	private ImageButton registro;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_formulario_registro);
		nombre = (EditText) findViewById(R.id.registronombre);
		e_mail = (EditText) findViewById(R.id.registroemail);
		user = (EditText) findViewById(R.id.registrousuario);
		registro = (ImageButton) findViewById(R.id.btnRegistro);

		registro.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				startAnimation(registro);
				Nube comNube = new Nube(SeguridadNube.OPE_REGISTRO_USUARIO);
				Mensaje resp = comNube.ejecutarRegistro(user.getText()
						.toString(), e_mail.getText().toString(), nombre
						.getText().toString());
				if (resp != null) {
					/*
					 * Respuesta puede ser OK o USUARIO_INEXISTENTE o
					 * CLAVE_INCORRECTA
					 */
					String mensaje = resp.getMensaje();
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

	// Metodo para rotar los botones, para que no quede fijo cuando se lo
	// presiona
	public void startAnimation(ImageView ivDH) {

		Animation rotateAnim = new RotateAnimation(0, 360);
		rotateAnim.setDuration(5000);
		rotateAnim.setRepeatCount(1);
		rotateAnim.setInterpolator(new AccelerateInterpolator());
		rotateAnim.setRepeatMode(Animation.REVERSE);

		ivDH.startAnimation(rotateAnim);
	}
}
