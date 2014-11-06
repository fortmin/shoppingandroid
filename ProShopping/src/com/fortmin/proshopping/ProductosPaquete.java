package com.fortmin.proshopping;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.fortmin.proshopping.logica.shopping.model.PaqueteVO;
import com.fortmin.proshopping.logica.shopping.model.ProductoVO;
import com.fortmin.proshopping.persistencia.DatosLocales;

/* clase para mostrar los productos del paquete, con la opcion de pasar al carrito de compras
 * 
 */
public class ProductosPaquete extends Activity {
	private ArrayList<String> datos = new ArrayList<String>();
	private List<ProductoVO> productos;
	private ListView lstOpciones;
	private Iterator<ProductoVO> iprods;
	private ImageView imagen;
	private TextView detalle_producto;
	private TagRecibido tag_recibido;
	private DatosLocales paquete_productos;
	private String nombrePaquete;
	private PaqueteVO paquete_prod = null;
	private static float tam_letra = 35;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		paquete_productos = DatosLocales.getInstance();
		tag_recibido = TagRecibido.getInstance();
		setContentView(R.layout.activity_paquete);
		imagen = (ImageView) findViewById(R.id.imagenProducto);
		lstOpciones = (ListView) findViewById(R.id.mainListView);
		detalle_producto = (TextView) findViewById(R.id.datosProducto);
		Bundle bundle = getIntent().getExtras();
		nombrePaquete = bundle.getString("nombrePaquete");
		paquete_prod = paquete_productos.obtenerPaquete(this, nombrePaquete);
		Log.e("ProductoPaquete", nombrePaquete);
		if (nombrePaquete.contains("Klip")) {
			imagen.setImageResource(R.drawable.mouse);
		} else if (nombrePaquete.contains("Promo")) {
			imagen.setImageResource(R.drawable.img_tvled32);

		}
		// Si pude obtener el paquete procedo a pedir la lista de productos
		if (paquete_prod != null) {
			descripcion(paquete_prod.getPuntos());
			productos = paquete_prod.getProductos();
			listarNombresProductos();
			/*
			 * paso los nombres de productos acargar en el listview a un arreglo
			 * de string
			 */

			lstOpciones.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					view.setSelected(true);
					if (view.isEnabled()) {
						view.setEnabled(false);
					} else {
						view.setEnabled(true);

					}
					String nombre_producto = parent.getItemAtPosition(position)
							.toString();
					/*
					 * las imagenes mostradas son locales, dado que faltan temas
					 * por resolver con respecto a la persistencia de imagenes
					 * en la nube
					 */
					String nombre_comercio = darComercio(nombre_producto);
					String precio = darPrecio(nombre_producto);
					detalle_producto.setText("Comercio: " + nombre_comercio
							+ "\n" + "Precio: " + "$ " + precio);
					if (nombre_producto.contains("Led"))
						imagen.setImageResource(R.drawable.img_tvled32);
					else if (nombre_producto.contains("Bebe"))
						imagen.setImageResource(R.drawable.img_buzobebe);
					else if (nombre_producto.contains("audifonos"))
						imagen.setImageResource(R.drawable.img_auriculares);
					else if (nombre_producto.contains("Klip")) {
						imagen.setImageResource(R.drawable.mouse);
					}

				}

			});

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		CrearMenu(menu);
		return true;
	}

	@SuppressLint({ "NewApi", "InlinedApi" })
	private void CrearMenu(Menu menu) {

		MenuItem canasto = menu.add(0, 0, 0, "Agregar al Canasto");
		{
			canasto.setIcon(R.drawable.canasta);
			canasto.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return MenuSelecciona(item);
	}

	private boolean MenuSelecciona(MenuItem item) {

		boolean opcion_elegida = false;
		if (item.getItemId() == 0) {
			Nube agregarCarrito = new Nube(
					ShoppingNube.OPE_AGREGAR_ITEM_CARRITO);
			Usuario user = com.fortmin.proshopping.Usuario.getInstance();
			agregarCarrito.agregarItemCarrito(nombrePaquete, user.getNombre());
			mostrarDialogo("Paquete agregado al carrito", "Compras");
			opcion_elegida = true;
			this.finish();
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

	public void descripcion(int puntos) {
		tag_recibido.setAtendido(true);
		detalle_producto.setTextSize(tam_letra);
		if (puntos == 0) {
			detalle_producto.setText("Precio: " + paquete_prod.getPrecio()
					+ " $");
		} else {
			detalle_producto.setText("Precio: " + paquete_prod.getPrecio()
					+ " $" + "\n" + "Puntos : " + puntos);
		}
	}

	public void mostrarDialogo(String mensaje, String title) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(mensaje)
				.setTitle(title)
				.setCancelable(false)
				.setNeutralButton("Aceptar",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}

}
