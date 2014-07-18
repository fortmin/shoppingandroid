package com.fortmin.proshopping;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Inicio extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inicio);
        TextView titulo=(TextView) findViewById(R.id.bienvenida);
        TextView titulo2=(TextView) findViewById(R.id.bienvenida2);
        Button usuarioR=(Button)findViewById(R.id.btnusuarioR);
        Button usuarioNR=(Button)findViewById(R.id.btnusuarioNR);
        Typeface fuente = Typeface.createFromAsset(getAssets(),	"Fuente1.ttf");
        Typeface fuente2=Typeface.createFromAsset(getAssets(),	"daniela.ttf");
        usuarioR.setTypeface(fuente2);
        usuarioNR.setTypeface(fuente2);
        usuarioR.setBackgroundResource(R.drawable.degradado);
        usuarioNR.setBackgroundResource(R.drawable.degradado);
		titulo.setTypeface(fuente);
		titulo2.setTypeface(fuente);
		usuarioR.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				logearse();
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

	public void logearse(){
		Intent logeo = new Intent(this, Login.class);
		startActivity(logeo);
	}
	public void registrarse(){
		Intent registro = new Intent(this, FormularioRegistro.class);
		startActivity(registro);
	}
}
