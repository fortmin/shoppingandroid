package com.fortmin.proshopping;

import com.fortmin.proshopping.gae.Nube;
import com.fortmin.proshopping.gae.ShoppingNube;
import com.fortmin.proshopping.logica.shopping.model.Mensaje;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PruebaFer extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inicio);
        ImageButton usuarioR=(ImageButton)findViewById(R.id.btnusuarioR);
        ImageButton usuarioNR=(ImageButton)findViewById(R.id.btnusuarioNR);
              
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

	public void logearse() {
		Nube nube = new Nube(ShoppingNube.OPE_INGRESO_ESTACIONAMIENTO);
		Mensaje resp = nube.ejecutarIngresoEstacionamiento("NFCPARKITALIA01", "jafortti");
		if (resp != null) {
			String mensaje = resp.getMensaje(); 
			if (mensaje.equals("OK")) 
				Toast.makeText(this, "Acceso Permitido", Toast.LENGTH_LONG).show();;
		}
	}
	
	public void registrarse(){
		Nube nube = new Nube(ShoppingNube.OPE_EGRESO_ESTACIONAMIENTO);
		Mensaje resp = nube.ejecutarEgresoEstacionamiento("NFCPARKITALIA01", "jafortti");
		if (resp != null) {
			String mensaje = resp.getMensaje(); 
			if (mensaje.equals("OK"))
				Toast.makeText(this, "Salida Permitida", Toast.LENGTH_LONG).show();;
		}
	}
	

}
