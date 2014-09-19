package com.fortmin.proshopping;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class PresenciaAmigo extends Activity {
	private Usuario user;
	private TextView mensaje;
	private ImageView amigo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_presencia_amigo);
		amigo = (ImageView) findViewById(R.id.imagen_amigo);
		mensaje = (TextView) findViewById(R.id.datos_amigo);
		mensaje.setTextSize(25);
		user = Usuario.getInstance();

		if (user.getNombre().equals("jafortti")) {
			amigo.setImageResource(R.drawable.fernando);
			mensaje.setText("Su amigo Fernando esta en el estacionamiento");
		} else if (user.getNombre().equals("fminos")) {
			amigo.setImageResource(R.drawable.julio);
			mensaje.setText("Su amigo Julio esta en el estacionamiento");
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}