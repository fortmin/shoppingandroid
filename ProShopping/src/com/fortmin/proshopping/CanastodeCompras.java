package com.fortmin.proshopping;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fortmin.proshopping.gae.Nube;
import com.fortmin.proshopping.gae.ShoppingNube;
import com.fortmin.proshopping.logica.shopping.model.CarritoVO;
import com.fortmin.proshopping.logica.shopping.model.PaqueteVO;

/* Clase responsable de mostrar todas las compras realizadas, las mismas pueden tener el estado de
 * entregada o pendiente, se muestra la fechahora y nombre de usuario
 */
public class CanastodeCompras extends Activity {
	private ListView lstOpciones;
	private String nombre_paquete;
	private Usuario user = Usuario.getInstance();
	private ImageView btn_checkout;
	private TextView precio_puntos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_canastode_compras);
		precio_puntos = (TextView) findViewById(R.id.precioPuntos);
		lstOpciones = (ListView) findViewById(R.id.listaComprados);
		btn_checkout = (ImageView) findViewById(R.id.btnRecibo);
		listarNombresProductos();
		nombre_paquete = "VACIO";
		lstOpciones
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent,
							final View view, int position, long id) {
						nombre_paquete = (String) parent
								.getItemAtPosition(position);
						view.setSelected(true);
						if (view.isEnabled()) {

							view.setEnabled(false);
						} else {
							view.setEnabled(true);

						}

					}

				});
		btn_checkout.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {

				startAnimation(btn_checkout);
				checkOutCarrito();
				volver();
			}
		});

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

		MenuItem canasto = menu.add(0, 0, 0, "Eliminar Paquete de mi Canasto");
		{
			canasto.setIcon(R.drawable.papelera);
			canasto.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return MenuSelecciona(item);
	}

	private boolean MenuSelecciona(MenuItem item) {

		if (!nombre_paquete.equals("VACIO")) {
			if (item.getItemId() == 0) {
				Nube borrarPaquete = new Nube(
						ShoppingNube.OPE_ELIMINAR_ITEM_CARRITO);

				borrarPaquete.eliminarItemCarrito(nombre_paquete,
						user.getNombre());
				CanastaCompras miscompras = CanastaCompras.getInstance();
				miscompras.anularCanasta();
				actualizarCarro();
				listarNombresProductos();
				mostrarMensaje("Paquete Eliminado");

			}
		} else
			mostrarMensaje("debe seleccionar el paquete que quiere eliminar del carrito");
		return true;
	}

	// El cotenido del paquete se pasa a un arreglo de string
	public void listarNombresProductos() {

		CanastaCompras miscompras = CanastaCompras.getInstance();
		precio_puntos.setText("Precio: " + " $" + miscompras.getPrecio() + "\n"
				+ "Puntos: " + miscompras.getPuntos());
		Iterator<PaqueteVO> icompras = miscompras.getPaquetes_comprados()
				.iterator();
		ArrayList<String> datos = new ArrayList<String>();

		while (icompras.hasNext()) {
			String nombre = icompras.next().getNombre();
			datos.add(nombre);
		}
		ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
				R.layout.simplerow, datos);
		lstOpciones.setAdapter(adaptador);

	}

	public void mostrarMensaje(String mensaje) {
		Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG)
				.show();
	}

	public void actualizarCarro() {
		Nube carrito = new Nube(ShoppingNube.OPE_GET_CARRITO_COMPLETO);
		Usuario user = com.fortmin.proshopping.Usuario.getInstance();
		CarritoVO micarrito = carrito.getCarritoCompleto(user.getNombre());
		if (micarrito.getCantItems() == 0) {
			Intent menuprincipal = new Intent(this, LecturaRF.class);
			startActivity(menuprincipal);
			this.finish();
		} else {
			CanastaCompras canasta = CanastaCompras.getInstance();
			canasta.anularCanasta();
			canasta.setPrecio(micarrito.getPrecioCarrito());
			canasta.setPuntos(Integer.valueOf(micarrito.getPuntosCarrito()));
			List<PaqueteVO> paquetes = micarrito.getPaquetes();
			Iterator<PaqueteVO> ipaquetes = paquetes.iterator();
			while (ipaquetes.hasNext()) {
				PaqueteVO paqueteVO = ipaquetes.next();
				canasta.agregarPaqueteCarrito(paqueteVO);
			}
		}
	}

	public void checkOutCarrito() {
		Nube hacer_checkout = new Nube(ShoppingNube.OPE_CHECKOUT_CARRITO);
		hacer_checkout.checkoutCarrito(user.getNombre());
	}

	public void volver() {
		Intent lecturanfc = new Intent(this, LecturaRF.class);
		startActivity(lecturanfc);
		this.finish();
	}

	// para animar el imageButton
	public void startAnimation(ImageView ivDH) {

		Animation rotateAnim = new RotateAnimation(0, 360);
		rotateAnim.setDuration(5000);
		rotateAnim.setRepeatCount(1);
		rotateAnim.setInterpolator(new AccelerateInterpolator());
		rotateAnim.setRepeatMode(Animation.REVERSE);
		ivDH.startAnimation(rotateAnim);
	}

}
