package com.fortmin.proshopping;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class MenuCliente extends Activity {
	private List<String> lista;
    private Spinner opciones;
	private Intent lecturanfc;
	private tipoNFC tipo;
	private com.fortmin.proshopapi.ble.EscucharIbeacons beacons;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_cliente);
		 opciones=(Spinner)findViewById(R.id.spinner);
	  	ImageButton ver_oferta=(ImageButton)findViewById(R.id.btnVerOferta);
        ImageButton recibir_oferta=(ImageButton)findViewById(R.id.btnRecibirOfertas);
        beacons = new com.fortmin.proshopapi.ble.EscucharIbeacons(this);
        tipo=tipoNFC.getInstance();
        lista = new ArrayList<String>();
        lista.add("ESTACIONAMIENTO");
        lista.add("ENTRADA ESTACIONAMIENTO");
        lista.add("SALIDA ESTACIONAMIENTO");
		ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,R.layout.spinner_item, lista);
	    adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		opciones.setAdapter(adaptador);
		opciones.setOnItemSelectedListener(new OnItemSelectedListener(){
			 @Override
			   public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			     if (arg2==1){
			    	tipo.setTipo("entrada");
			    	leerNFC();
			     }
			     else if (arg2==2){
			    	 tipo.setTipo("salida");
			    	 leerNFC();
			     }
			   }
			   @Override
			   public void onNothingSelected(AdapterView<?> arg0) {
			   }
		
				
			
			
			});
		
        ver_oferta.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				tipo.setTipo("tag");
				leerNFC();
			}
		});
        recibir_oferta.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				beacons.startScanning();
				hacerInvisible();
				startService(new Intent(getBaseContext(), ServicioBle.class));
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
    
	public void leerNFC(){
		lecturanfc = new Intent(this, LecturaNfc.class);
		startActivity(lecturanfc);
	}
	
	 @Override
	    protected void onResume() {
	        super.onResume();

	        // check for Bluetooth enabled on each resume
	        if (beacons.isBtEnabled() == false)
	        {
	            // BT not enabled. Request to turn it on. User needs to restart app once it's turned on.
	            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	            startActivity(enableBtIntent);
	            //finish();
	        }

	        // inicializacion del ble 
	        beacons.initialize();
	       // beacons.startScanning();
	       //  ListaIbeacon Ibeacons=beacons.getIbeacons();
		 //    ArrayList<Ibeacon> dispositivos=Ibeacons.IbeaconsEncendidos();
		 //    beacon=dispositivos.get(0);
	        
	    }
	public void hacerInvisible(){
		this.setVisible(false);
	}
}
