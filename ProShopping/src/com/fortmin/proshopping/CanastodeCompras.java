package com.fortmin.proshopping;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

public class CanastodeCompras extends Activity {
     private ListView lstOpciones;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_canastode_compras);
		ImageButton btn_anular=(ImageButton) findViewById(R.id.imagenAnular);
		lstOpciones = (ListView) findViewById(R.id.listaComprados);
		listarNombresProductos();
		btn_anular.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				borrarLista();
			}

		
		});
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.canastode_compras, menu);
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
	public void listarNombresProductos() {
		// pasa todos os productos del paquete a un arreglo de string

		    CanastaCompras miscompras=CanastaCompras.getInstance();
			Iterator<String> icompras = miscompras.getPaquetes_comprados().iterator();
			ArrayList<String> datos = new ArrayList<String>();
			
			while (icompras.hasNext()) {
				String nombre = icompras.next();
				datos.add(nombre);
			}
			// datos.add("productos");
			ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
					R.layout.simplerow, datos);
			lstOpciones.setAdapter(adaptador);
			// PD.dismiss();
		}
	public void borrarLista(){
	    CanastaCompras miscompras=CanastaCompras.getInstance();
	    miscompras.anularCanasta();
		ArrayList<String> datos = new ArrayList<String>();
		ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,R.layout.simplerow, datos);
		lstOpciones.setAdapter(adaptador);
	}
	}




