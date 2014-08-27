package com.fortmin.proshopping;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fortmin.proshopping.ListadoCompras.datosCompra;
import com.fortmin.proshopping.logica.shopping.model.ComprasVO;

public class ListadoMisCompras extends Activity {
	private ArrayList<String> datos = new ArrayList<String>();
	private ListView lstOpciones;
	private Iterator<ComprasVO> icompras;
	private TextView datos_compra;
	private ProgressDialog PD = null;
	private ArrayList<ComprasVO> compras;
	private ListadoCompras mis_compras = ListadoCompras.getInstance();
	private ArrayList<String> colores_Lista;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		/*
		 * this.PD = ProgressDialog.show(this, "Procesando",
		 * "Espere unos segundos...", true, false);
		 */

		setContentView(R.layout.activity_listadomiscompras);
		datos_compra = (TextView) findViewById(R.id.datosMisCompras);
		lstOpciones = (ListView) findViewById(R.id.miListaCompras);
		colores_Lista = new ArrayList();
		// paso los nombres de productos a
		// cargar en el listview a un
		// arreglo de string
		cargarCompras();
		lstOpciones.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (!colores_Lista.isEmpty()) {
					if (colores_Lista.get(position).equals("GREEN")) {
						view.setBackgroundColor(Color.WHITE);
						colores_Lista.add(position, "WHITE");
					} else {
						view.setBackgroundColor(Color.GREEN);
						colores_Lista.add(position, "GREEN");
					}
				} else {
					view.setBackgroundColor(Color.GREEN);
					colores_Lista.add(position, "GREEN");
				}
				String nombre_compra = parent.getItemAtPosition(position)
						.toString();
				mostrarDatosCompra(nombre_compra);

			}

		});

	}

	class DownloadTask extends AsyncTask<Object, Object, Object> {
		@Override
		protected void onPreExecute() {

			super.onPreExecute();
			PD = new ProgressDialog(ListadoMisCompras.this);
			PD.setTitle("Please Wait..");
			PD.setMessage("Loading...");
			PD.setCancelable(false);
			PD.show();

		}

		@Override
		protected Object doInBackground(Object... paq) {
			// TODO Auto-generated method stub

			return paq;

		}
	}

	public void mostrarMensaje(String mensaje) {
		Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG)
				.show();
	}

	@Override
	public void onResume() {
		super.onResume();

	}

	public void listarMisCompras() {

		if (compras != null) {
			icompras = compras.iterator();

			while (icompras.hasNext()) {

				ComprasVO p = icompras.next();
				String nombre = p.getCompra();
				datos.add(nombre);
				Log.e("dato", nombre);
				// iprods.next();
			}
			// datos.add("productos");
			ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
					R.layout.simplerow, datos);
			lstOpciones.setAdapter(adaptador);
			// PD.dismiss();
		}
	}

	public void cargarCompras() {

		compras = mis_compras.getMisCompras();
		listarMisCompras();
	}

	public void mostrarDatosCompra(String idcompra) {
		datosCompra datos_mi_compra = mis_compras.darDatosCompra(idcompra);
		String estado;
		if (datos_mi_compra.isEntregado()) {
			estado = "entregado";
			datos_compra.setText("La compra fue " + estado + "\n"
					+ "La compra tenia " + datos_mi_compra.getTotalpaquetes()
					+ " paquetes" + "\n" + "El costo ascendio a $ "
					+ datos_mi_compra.getPrecio() + "\n" + "La compra genero "
					+ datos_mi_compra.getPuntosgenerados() + " puntos" + "\n");

		} else {
			estado = "pendiente";
			datos_compra.setText("La compra aun esta " + estado + "\n"
					+ "La compra tenia " + datos_mi_compra.getTotalpaquetes()
					+ " paquetes" + "\n" + "El costo ascendio a $ "
					+ datos_mi_compra.getPrecio() + "\n" + "La compra genero "
					+ datos_mi_compra.getPuntosgenerados() + " puntos" + "\n");
		}

	}

}
