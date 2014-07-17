package com.fortmin.proshopping;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class LecturaNfc extends Activity {

	private Intent paquete;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lectura_nfc);
		ImageButton btn_nfc = (ImageButton) findViewById(R.id.btnNFC);
		btn_nfc.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				// biene el contenido leido del nfc
				verPaquete("BEACON001");
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lectura_nfc, menu);
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

	public void verPaquete(String nfc) {
		// llamo a mostrarPaquete

		paquete = new Intent(this, ProductosPaquete.class);
		paquete.putExtra("nombreNFC", nfc);
		startActivity(paquete);

	}

}
