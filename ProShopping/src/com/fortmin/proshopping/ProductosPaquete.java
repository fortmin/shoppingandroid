package com.fortmin.proshopping;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fortmin.proshopping.gae.Nube;
import com.fortmin.proshopping.gae.ShoppingNube;
import com.fortmin.proshopping.logica.shopping.model.Paquete;
import com.fortmin.proshopping.logica.shopping.model.Producto;

public class ProductosPaquete extends Activity {
	private Paquete paquete = null;
	private ArrayList<String> datos = new ArrayList<String>();
	private ArrayList<Producto> productos;
	private ListView lstOpciones;
	private ShoppingNube comNube;
	private Iterator<Producto> iprods;
	private TextView detalle_producto;
	private ProgressDialog PD = null;

	String nombreNFC;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.PD = ProgressDialog.show(this, "Procesando",
				"Espere unos segundos...", true, false);
		setContentView(R.layout.activity_paquete);
		lstOpciones = (ListView) findViewById(R.id.mainListView);
		detalle_producto = (TextView) findViewById(R.id.datosProducto);

		Bundle bundle = getIntent().getExtras();
		nombreNFC = bundle.getString("nombreNFC");
		Nube comNube = new Nube(ShoppingNube.OPE_GET_PAQUETE_RF);
		Paquete paquete = (Paquete) comNube.ejecutarGetPaqueteRf(nombreNFC);
		// Se comienza la nueva Thread que descargará los datos necesarios

		// Si pude obtener el paquete procedo a pedir la lista de productos
		if (paquete != null) {
			detalle_producto.setText("El paquete tiene "
					+ paquete.getCantProductos() + " " + "Productos" + "\n"
					+ "Precio=" + paquete.getPrecio() + "\n" + "puntos="
					+ paquete.getPuntos());

			// Toast.makeText(getApplicationContext(), "Obteniendo productos",
			// Toast.LENGTH_LONG).show();
			comNube = new Nube(ShoppingNube.OPE_GET_PRODUCTOS_PAQUETE);

			productos = (ArrayList<Producto>) comNube.ejecutarGetProductosPaquete(paquete.getNombre());
			listarNombresProductos();// paso los nombres de productos a
										// cargar en el listview a un
										// arreglo de string
		}
		lstOpciones.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String nombre_producto = parent.getItemAtPosition(position)
						.toString();
				String nombre_comercio = darComercio(nombre_producto);
				String precio = darPrecio(nombre_producto);
				detalle_producto.setText("Nombre del Comercio:"
						+ nombre_comercio + "\n" + "Precio=" + precio);

			}

		});

	}

	private class DownloadTask extends AsyncTask<Object, Object, Object> {
		@Override
		protected void onPreExecute() {

			super.onPreExecute();
			PD = new ProgressDialog(ProductosPaquete.this);
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

		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);

			PD.dismiss();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.paquete, menu);
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

	@Override
	public void onResume() {
		super.onResume();

	}

	public void listarNombresProductos() {
		// pasa todos os productos del paquete a un arreglo de string

		if (productos != null) {
			iprods = productos.iterator();
			while (iprods.hasNext()) {
				String nombre = iprods.next().getProducto().intern();
				datos.add(nombre);
				Log.e("dato", nombre);
				// iprods.next();
			}
			// datos.add("productos");
			ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
					R.layout.simplerow, datos);
			lstOpciones.setAdapter(adaptador);
			PD.dismiss();
		}
	}

	public String darComercio(String nombre_producto) {
		String nombre_comercio = null;
		;
		if (productos != null) {
			iprods = productos.iterator();
			boolean encontre = false;
			while (iprods.hasNext() && !encontre) {
				Producto p = iprods.next();
				String nombre = p.getProducto().intern();
				if (nombre_producto.equalsIgnoreCase(nombre)) {
					nombre_comercio = p.getComercio().intern();
					encontre = true;
				}

			}

		}
		return nombre_comercio;

	}

	public String darPrecio(String nombre_producto) {
		String precio = null;
		;
		if (productos != null) {
			iprods = productos.iterator();
			boolean encontre = false;
			while (iprods.hasNext() && !encontre) {
				Producto p = iprods.next();
				String nombre = p.getProducto().intern();
				if (nombre_producto.equalsIgnoreCase(nombre)) {
					precio = String.valueOf(p.getPrecio());
					encontre = true;
				}

			}

		}
		return precio;

	}

}
