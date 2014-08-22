package com.fortmin.proshopping;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fortmin.proshopping.gae.Nube;
import com.fortmin.proshopping.gae.ShoppingNube;
import com.fortmin.proshopping.logica.shopping.model.Paquete;
import com.fortmin.proshopping.logica.shopping.model.PaqueteVO;
import com.fortmin.proshopping.logica.shopping.model.ProductoVO;
import com.fortmin.proshopping.persistencia.DatosLocales;

public class ProductosPaquete extends Activity {
	private Paquete paquete = null;
	private ArrayList<String> datos = new ArrayList<String>();
	private List<ProductoVO> productos;
	private ListView lstOpciones;
	private ShoppingNube comNube;
	private Iterator<ProductoVO> iprods;
	private ImageView imagen;
	private TextView detalle_producto;
	private ProgressDialog PD = null;
	private String nombrepaquete;
	private TagRecibido tag_recibido;
	private DatosLocales paquete_productos;
	private String nombrePaquete;
	PaqueteVO paquete_prod = null;
	private ArrayList<String> colores_Lista;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		paquete_productos = DatosLocales.getInstance();
		tag_recibido = TagRecibido.getInstance();
		/*
		 * this.PD = ProgressDialog.show(this, "Procesando",
		 * "Espere unos segundos...", true, false);
		 */
		setContentView(R.layout.activity_paquete);
		imagen = (ImageView) findViewById(R.id.imagenProducto);

		lstOpciones = (ListView) findViewById(R.id.mainListView);
		detalle_producto = (TextView) findViewById(R.id.datosProducto);
		Bundle bundle = getIntent().getExtras();
		nombrePaquete = bundle.getString("nombrePaquete");
		paquete_prod = paquete_productos.obtenerPaquete(this, nombrePaquete);
		Log.e("ProductoPaquete", nombrePaquete);
		colores_Lista = new ArrayList();
		// Si pude obtener el paquete procedo a pedir la lista de productos
		if (paquete_prod != null) {
			tag_recibido.setAtendido(true);
			detalle_producto.setText("Este paquete tiene "
					+ paquete_prod.getCantProductos() + " " + "Productos"
					+ "\n" + paquete_prod.getPrecio() + " $" + "\n"
					+ paquete_prod.getPuntos() + "puntos acumulados");

			// Toast.makeText(getApplicationContext(), "Obteniendo productos",
			// Toast.LENGTH_LONG).show();
			// comNube = new Nube(ShoppingNube.OPE_GET_PRODUCTOS_PAQUETE);
			productos = paquete_prod.getProductos();

			listarNombresProductos();// paso los nombres de productos a
										// cargar en el listview a un
										// arreglo de string

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
					String nombre_producto = parent.getItemAtPosition(position)
							.toString();
					String nombre_comercio = darComercio(nombre_producto);
					String precio = darPrecio(nombre_producto);
					detalle_producto.setText(nombre_comercio + "\n" + "$ "
							+ precio);
					if (nombre_producto.contains("Led"))
						imagen.setImageResource(R.drawable.img_tvled32);
					else if (nombre_producto.contains("Bebe"))
						imagen.setImageResource(R.drawable.img_buzobebe);
					else if (nombre_producto.contains("audifonos"))
						imagen.setImageResource(R.drawable.img_auriculares);

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
		super.onCreateOptionsMenu(menu);
		CrearMenu(menu);
		return true;
	}

	@SuppressLint({ "NewApi", "InlinedApi" })
	private void CrearMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuItem canasto = menu.add(0, 0, 0, "Agregar al Canasto");
		{
			canasto.setIcon(R.drawable.canasta);
			canasto.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		return MenuSelecciona(item);
	}

	private boolean MenuSelecciona(MenuItem item) {
		// TODO Auto-generated method stub
		boolean opcion_elegida = false;
		if (item.getItemId() == 0) {
			Nube agregarCarrito = new Nube(
					ShoppingNube.OPE_AGREGAR_ITEM_CARRITO);
			Usuario user = com.fortmin.proshopping.Usuario.getInstance();
			agregarCarrito.agregarItemCarrito(nombrePaquete, user.getNombre());
			// miscompras.agregarPaqueteCarrito(nombrePaquete);
			mostrarMensaje("Paquete agregado a su canasto");
			opcion_elegida = true;
		}
		return opcion_elegida;
	}

	public void mostrarMensaje(String mensaje) {
		Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG)
				.show();
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
				ProductoVO p = new ProductoVO();
				p = iprods.next();
				String nombre = p.getNombre();
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

	public String darComercio(String nombre_producto) {
		String nombre_comercio = null;

		if (productos != null) {
			iprods = productos.iterator();
			boolean encontre = false;
			while (iprods.hasNext() && !encontre) {
				ProductoVO p = iprods.next();
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
				ProductoVO p = iprods.next();
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
