package com.fortmin.proshopping;

import java.nio.charset.Charset;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.fortmin.proshopping.gae.Nube;
import com.fortmin.proshopping.gae.ShoppingNube;
import com.fortmin.proshopping.logica.shopping.model.Mensaje;
import com.fortmin.proshopping.logica.shopping.model.Paquete;
import com.fortmin.proshopping.logica.shopping.model.Producto;
import com.fortmin.proshopping.persistencia.BDElementoRf;
import com.fortmin.proshopping.persistencia.DatosLocales;

public class LecturaNfc extends Activity {

	private Intent paquete;
	private usuario nombre_usuario;
    private tipoNFC tipo;
    private String nombre_paquete;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lectura_nfc);
		ImageButton btn_nfc = (ImageButton) findViewById(R.id.btnNFC);
		nombre_usuario = usuario.getInstance();
		 tipo= tipoNFC.getInstance();
		// Log.e("usuario",nombre_usuario.getNombre());
		// escucho el tag nfc para obtener el id
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
			Log.e("tag", "leyendo tag nfc");
			NdefMessage[] messages = getNdefMessages(getIntent());
			for (int i = 0; i < messages.length; i++) {
				for (int j = 0; j < messages[0].getRecords().length; j++) {
					NdefRecord record = messages[i].getRecords()[j];
					String payload = new String(record.getPayload(), 0,
							record.getPayload().length,
							Charset.forName("UTF-8"));
					String delimiter = ":";
					String[] temp = payload.split(delimiter);
					String Id = temp[0];
					// if(tipo.getTipo().equals("tag")){
					// Log.e("opcion","tag");
					AnalizarId(Id);

					// }
					// else
					// entradaEstacionamiento(tipo.getTipo(),nombre_usuario.getNombre(),Id);

				}

			}
		}

	/*	btn_nfc.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				// viene el contenido leido del nfc
				// verPaquete("NFC001");
			}
		});*/
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

	public void verPaquete(String nombrepaquete) {
		// llamo a mostrarPaquete

		paquete = new Intent(this, ProductosPaquete.class);
		paquete.putExtra("nombrePaquete", nombrepaquete);
		startActivity(paquete);

	}

	public NdefMessage[] getNdefMessages(Intent intent) {
		NdefMessage[] message = null;
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
			Parcelable[] rawMessages = intent
					.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
			if (rawMessages != null) {
				message = new NdefMessage[rawMessages.length];
				for (int i = 0; i < rawMessages.length; i++) {
					message[i] = (NdefMessage) rawMessages[i];
				}
			} else {
				byte[] empty = new byte[] {};
				NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN,
						empty, empty, empty);
				NdefMessage msg = new NdefMessage(new NdefRecord[] { record });
				message = new NdefMessage[] { msg };
			}
		} else {

			finish();
		}
		return message;
	}

	public void entradaEstacionamiento(String acceso, String nom_usuario,
			String id) {
		// llamo a mostrarPaquete
		if (acceso.equals("entrada")) {// entrada
			Nube nube = new Nube(ShoppingNube.OPE_INGRESO_ESTACIONAMIENTO);
			Mensaje resp = nube.ejecutarIngresoEstacionamiento(id, nom_usuario);
			if (resp != null) {
				String mensaje = resp.getMensaje(); // Respuesta puede ser
													// OK o
													// USUARIO_INEXISTENTE o
													// CLAVE_INCORRECTA
				if (mensaje.equals("OK"))
					mostrarMensaje("Acceso Permitido");
			}
			// Respuesta puede ser:
			// SIN_ACCESO_RELACIONADO si es un Tag NFC pero no esta
			// relacionado con un Acceso
			// NO_ES_ACCESO_ESTACIONAMIENTO porque es un Acceso pero no para
			// Autos (ej: Peatonal)
			// CLIENTE_INEXISTENTE no se encontro el usuario
			// OK si todo salio bien
		} else if (acceso.equals("salida")) { // salida
			Nube nube = new Nube(ShoppingNube.OPE_INGRESO_ESTACIONAMIENTO);
			Mensaje resp = nube.ejecutarEgresoEstacionamiento(id, nom_usuario);
			if (resp != null) {
				String mensaje = resp.getMensaje(); // Respuesta puede ser
													// OK o
													// USUARIO_INEXISTENTE o
													// CLAVE_INCORRECTA
				if (mensaje.equals("PLAZO_VENCIDO") || mensaje.equals("OK")) {
					mostrarMensaje("Gracias por su visita");
				}
			}
			// Respuesta puede ser:
			// SIN_ACCESO_RELACIONADO si es un Tag NFC pero no esta
			// relacionado con un Acceso
			// NO_ES_ACCESO_ESTACIONAMIENTO porque es un Acceso pero no para
			// Autos (ej: Peatonal)
			// CLIENTE_INEXISTENTE no se encontro el usuario
			// OK si todo salio bien
			// PLAZO_VENCIDO si la fecha hora de salida supera a la fecha
			// hora de entrada
			// en mas del valor del parametro PLAZO_ESTACIONAMIENTO
			// establecido en la tabla Config
		}
		Intent menucliente = new Intent(this, MenuCliente.class);
		startActivity(menucliente);
	}

	public void mostrarMensaje(String mensaje) {
		Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG)
				.show();
	}

	public void AnalizarId(String id) {
	    nombre_paquete = null;
		Log.e("ID", id);
		Log.e("NomUsuario", nombre_usuario.getNombre());
		String nfc = id.substring(3);
		Log.e("ID", nfc);
		if (id.contains("ENT")) {
			Log.e("estacionamiento", nombre_usuario.getNombre());
			entradaEstacionamiento("entrada", nombre_usuario.getNombre(), nfc);

		} else if (id.contains("SAL"))
			entradaEstacionamiento("salida", nombre_usuario.getNombre(), nfc);
		else{
			Nube comNube = new Nube(ShoppingNube.OPE_GET_PAQUETE_RF);
			Paquete paquete = (Paquete) comNube.ejecutarGetPaqueteRf(id);
			// Se comienza la nueva Thread que descargará los datos necesarios

			// Si pude obtener el paquete procedo a pedir la lista de productos
			if (paquete != null) {
				nombre_paquete=paquete.getNombre();
			}
			Log.e("Nombre Paquete", nombre_paquete);
			BDElementoRf tag=new BDElementoRf(id, "NFC", 0, nombre_paquete);
			DatosLocales datos = DatosLocales.getInstance();
			
			datos.encontreElementoRf(this, tag);
			verPaquete(nombre_paquete);
		}
	}
}
