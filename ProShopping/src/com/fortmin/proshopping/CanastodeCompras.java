package com.fortmin.proshopping;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfDocument.Page;
import android.graphics.pdf.PdfDocument.PageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintAttributes.Margins;
import android.print.PrintAttributes.Resolution;
import android.print.pdf.PrintedPdfDocument;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

public class CanastodeCompras extends Activity implements Runnable {
	private ListView lstOpciones;
	private String nombre_paquete;
	private Usuario user = Usuario.getInstance();
	private int posicion;
	private ImageView btnRecibo;
	private TextView precio_puntos;
	private ArrayList<String> colores_Lista;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_canastode_compras);
		precio_puntos = (TextView) findViewById(R.id.precioPuntos);
		lstOpciones = (ListView) findViewById(R.id.listaComprados);
		btnRecibo = (ImageView) findViewById(R.id.btnRecibo);
		listarNombresProductos();
		lstOpciones
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent,
							final View view, int position, long id) {
						nombre_paquete = (String) parent
								.getItemAtPosition(position);
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
						posicion = position;

					}

				});
		btnRecibo.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {

				makeAndSharePDF(view);
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
		// TODO Auto-generated method stub
		MenuItem canasto = menu.add(0, 0, 0, "Eliminar Paquete de mi Canasto");
		{
			canasto.setIcon(R.drawable.papelera);
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
		if (item.getItemId() == 0) {
			Nube borrarPaquete = new Nube(
					ShoppingNube.OPE_ELIMINAR_ITEM_CARRITO);
			Usuario user = Usuario.getInstance();
			borrarPaquete.eliminarItemCarrito(nombre_paquete, user.getNombre());
			CanastaCompras miscompras = CanastaCompras.getInstance();
			miscompras.anularCanasta();

			// precio_puntos.setText("El precio y los puntos actualizan en la próxima entrada");
			actualizarCarro();
			listarNombresProductos();
			mostrarMensaje("Paquete Eliminado");

		}
		return true;
	}

	public void listarNombresProductos() {
		// pasa todos os productos del paquete a un arreglo de string

		CanastaCompras miscompras = CanastaCompras.getInstance();
		precio_puntos.setText(miscompras.getPrecio() + " $" + "\n"
				+ miscompras.getPuntos() + " " + "Puntos generados");
		Iterator<PaqueteVO> icompras = miscompras.getPaquetes_comprados()
				.iterator();
		ArrayList<String> datos = new ArrayList<String>();

		while (icompras.hasNext()) {
			String nombre = icompras.next().getNombre();
			datos.add(nombre);
		}
		// datos.add("productos");
		ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
				R.layout.simplerow, datos);
		lstOpciones.setAdapter(adaptador);
		// PD.dismiss();
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
			Intent menuprincipal = new Intent(this, LecturaNfc.class);
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

	public void makeAndSharePDF(View buttonSource) {
		new Thread(this).start();

	}

	// TODO Auto-generated method stub
	@Override
	public void run() {

		// TODO Auto-generated method stub
		// Create a shiny new (but blank) PDF document in memory
		// We want it to optionally be printable, so add PrintAttributes
		// and use a PrintedPdfDocument. Simpler: new PdfDocument().

		PrintAttributes printAttrs = new PrintAttributes.Builder()
				.setColorMode(PrintAttributes.COLOR_MODE_COLOR)
				.setMediaSize(PrintAttributes.MediaSize.NA_LETTER)
				.setResolution(
						new Resolution("zooey", PRINT_SERVICE, 980, 1280))
				.setMinMargins(Margins.NO_MARGINS).build();
		PdfDocument document = new PrintedPdfDocument(this, printAttrs);
		// crate a page description
		PageInfo pageInfo = new PageInfo.Builder(1000, 2000, 1).create();

		// create a new page from the PageInfo
		Page page = document.startPage(pageInfo);

		// repaint the user's text into the page
		View content = findViewById(R.id.mirecibo);

		content.draw(page.getCanvas());

		// do final processing of the page
		document.finishPage(page);

		// Here you could add more pages in a longer doc app, but you'd have
		// to handle page-breaking yourself in e.g., write your own word
		// processor...

		// Now write the PDF document to a file; it actually needs to be a file
		// since the Share mechanism can't accept a byte[]. though it can
		// accept a String/CharSequence. Meh.
		try {
			File pdfDirPath = new File(getFilesDir(), "pdfs");

			pdfDirPath.mkdirs();
			File file = new File(pdfDirPath, "ticket.pdf");
			Uri contentUri = FileProvider.getUriForFile(this,
					"com.fortmin.proshopping", file);
			Log.e("PDF", "contentUri");
			FileOutputStream os = new FileOutputStream(file);
			document.writeTo(os);
			document.close();
			os.close();

			shareDocument(contentUri);
		} catch (IOException e) {
			throw new RuntimeException("Error generating file", e);
		}

	}

	private void shareDocument(Uri uri) {
		// TODO Auto-generated method stub
		Intent mShareIntent = new Intent();
		mShareIntent.setAction(Intent.ACTION_SEND);
		mShareIntent.setType("application/pdf");
		// Assuming it may go via eMail:
		mShareIntent.putExtra(Intent.EXTRA_SUBJECT, "Enviado de Proshooping");

		// Attach the PDf as a Uri, since Android can't take it as bytes yet.
		mShareIntent.putExtra(Intent.EXTRA_STREAM, uri);
		startActivity(mShareIntent);
		checkOutCarrito();// hago el chekout
		return;
	}

	public void checkOutCarrito() {
		Nube hacer_checkout = new Nube(ShoppingNube.OPE_CHECKOUT_CARRITO);
		hacer_checkout.checkoutCarrito(user.getNombre());
	}

	public void volver() {
		Intent lecturanfc = new Intent(this, LecturaNfc.class);
		startActivity(lecturanfc);
		this.finish();
	}
}
