package com.fortmin.proshopping;

import java.util.ArrayList;
import java.util.Iterator;

import com.fortmin.proshopping.logica.shopping.model.PaqueteVO;
import com.fortmin.proshopping.logica.shopping.model.ProductoVO;
import com.fortmin.proshopping.persistencia.DatosLocales;
import com.fortmin.proshopping.persistencia.PaquetesVO;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.os.Build;

public class MostrarPaquetes extends Activity {
        private DatosLocales datos=DatosLocales.getInstance();
        private  PaquetesVO paquetes;
        private ListView lstOpciones;
        private ArrayList<String> lista_paquetes = new ArrayList<String>();
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mostrar_paquetes);
        paquetes=datos.obtenerPaquetes(this);
        lstOpciones = (ListView) findViewById(R.id.listaPaquetes);
    	listarNombresPaquetes();
    	lstOpciones.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String nombre_paquete = parent.getItemAtPosition(position).toString();
			    verPaquete(nombre_paquete);

			}

		});
	}

	private void listarNombresPaquetes() {
			// TODO Auto-generated method stub
		
		Iterator<PaqueteVO> ipaquetes = paquetes.getPaquetes().iterator();
		while (ipaquetes.hasNext()) {
			PaqueteVO p = new PaqueteVO();
			p = ipaquetes.next();
			String nombre = p.getNombre();
			lista_paquetes.add(nombre);
		
		}
		ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
				R.layout.simplerow, lista_paquetes);
		lstOpciones.setAdapter(adaptador);
		}

	public void verPaquete(String nombrepaquete) {
		// llamo a mostrarPaquete

		Intent paquete = new Intent(this, ProductosPaquete.class);
		paquete.putExtra("nombrePaquete", nombrepaquete);
		startActivity(paquete);

	}

	
}
