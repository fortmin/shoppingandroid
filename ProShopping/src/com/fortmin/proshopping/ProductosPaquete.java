package com.fortmin.proshopping;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
import com.fortmin.proshopping.logica.shopping.model.PaqueteVO;
import com.fortmin.proshopping.logica.shopping.model.Producto;
import com.fortmin.proshopping.logica.shopping.model.ProductoVO;
import com.fortmin.proshopping.persistencia.DatosLocales;

public class ProductosPaquete extends Activity {
	private Paquete paquete = null;
	private ArrayList<String> datos = new ArrayList<String>();
	private List<ProductoVO> productos;
	private ListView lstOpciones;
	private ShoppingNube comNube;
	private Iterator<ProductoVO> iprods;
	private TextView detalle_producto;
	private ProgressDialog PD = null;
	private String nombrePaquete;
    private DatosLocales paquete_productos;
    PaqueteVO paquete_prod=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		paquete_productos=DatosLocales.getInstance();
		
		/*this.PD = ProgressDialog.show(this, "Procesando",
				"Espere unos segundos...", true, false);*/
		setContentView(R.layout.activity_paquete);
		lstOpciones = (ListView) findViewById(R.id.mainListView);
		detalle_producto = (TextView) findViewById(R.id.datosProducto);

		Bundle bundle = getIntent().getExtras();
		
		nombrePaquete = bundle.getString("nombrePaquete");
		Log.e("ProductoPaquete",nombrePaquete);
	    paquete_prod=paquete_productos.obtenerPaquetes(this, nombrePaquete);
		
	//	Nube comNube = new Nube(ShoppingNube.OPE_GET_PAQUETE_RF);
	//	Paquete paquete = (Paquete) comNube.ejecutarGetPaqueteRf(nombrePaquete);
		// Se comienza la nueva Thread que descargará los datos necesarios

		// Si pude obtener el paquete procedo a pedir la lista de productos
	  if (paquete_prod != null) {
			detalle_producto.setText("El paquete tiene "
					+ paquete_prod.getCantProductos() + " " + "Productos" + "\n"
					+ "Precio=" +  paquete_prod.getPrecio()+ "\n" + "puntos="
					+  paquete_prod.getPuntos());
            
			// Toast.makeText(getApplicationContext(), "Obteniendo productos",
			// Toast.LENGTH_LONG).show();
			//comNube = new Nube(ShoppingNube.OPE_GET_PRODUCTOS_PAQUETE);
            productos= paquete_prod.getProductos();
	
			listarNombresProductos();// paso los nombres de productos a
										// cargar en el listview a un
										// arreglo de string
	
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

	class DownloadTask extends AsyncTask<Object, Object, Object> {
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
				ProductoVO p=new ProductoVO();
				p=iprods.next();
				String nombre = p.getNombre();
				datos.add(nombre);
				Log.e("dato", nombre);
				// iprods.next();
			}
			 //datos.add("productos");
			ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
					R.layout.simplerow, datos);
			lstOpciones.setAdapter(adaptador);
			//PD.dismiss();
		}
	}

	public String darComercio(String nombre_producto) {
		String nombre_comercio = null;
		
		if (productos != null) {
			iprods = productos.iterator();
			boolean encontre = false;
			while (iprods.hasNext() && !encontre) {
				ProductoVO p=iprods.next();
				String nombre = p.getNombre();
				if (nombre_producto.equalsIgnoreCase(nombre)) {
					nombre_comercio = p.getComercio();
					encontre = true;
				}

			}

		}
		return nombre_comercio;

	}

	public String darPrecio(String nombre_producto) {
		String precio = null;
		
		if (productos != null) {
			iprods = productos.iterator();
			boolean encontre = false;
			while (iprods.hasNext() && !encontre) {
				ProductoVO p=iprods.next();
				String nombre = p.getNombre();
				if (nombre_producto.equalsIgnoreCase(nombre)) {
					precio = String.valueOf(p.getPrecio());
					encontre = true;
				}

			}

		}
		return precio;

	}

}
