package com.fortmin.proshopping;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MenuCliente extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_cliente);
        Button ver_oferta=(Button)findViewById(R.id.btnVerOferta);
        Button recibir_oferta=(Button)findViewById(R.id.btnRecibirOfertas);
        Button estacionamiento=(Button)findViewById(R.id.btnEstacionamiento);
        Typeface fuente2=Typeface.createFromAsset(getAssets(),	"daniela.ttf");
        ver_oferta.setTypeface(fuente2);
        recibir_oferta.setTypeface(fuente2);
        estacionamiento.setTypeface(fuente2);
        ver_oferta.setBackgroundResource(R.drawable.degradado);
        recibir_oferta.setBackgroundResource(R.drawable.degradado);
        estacionamiento.setBackgroundResource(R.drawable.degradado);
		
        ver_oferta.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				obtenerOferta();
			}
		});
        recibir_oferta.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				
			}
		});
        estacionamiento.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				
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
    
	public void obtenerOferta(){
		Intent leerNFC = new Intent(this, LecturaNfc.class);
		startActivity(leerNFC);
	}
	
}
