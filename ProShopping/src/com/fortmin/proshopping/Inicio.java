package com.fortmin.proshopping;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class Inicio extends Activity {
  private String nombre;
  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inicio);
		ImageButton usuarioR = (ImageButton) findViewById(R.id.btnusuarioR);
		ImageButton usuarioNR = (ImageButton) findViewById(R.id.btnusuarioNR);
		SharedPreferences prefs = getSharedPreferences("configuracion",MODE_PRIVATE);
	    nombre = prefs.getString("Usuario","no existe");
		if  (!nombre.equals("no existe")){
		  mostrarMensaje("Bienvenido "+nombre);	
		  verOpciones();
		}

		usuarioR.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if  (!nombre.equals("no existe")){
					  mostrarMensaje("Bienvenido "+nombre);	
					  verOpciones();
					}
				else
				{
			    		
				 logearse();
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
		Intent opciones = new Intent(this, LecturaNfc.class);
		startActivity(opciones);
	}
	public void mostrarMensaje(String mensaje) {
		Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG)
				.show();
	}
}
