package com.fortmin.proshopping;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
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

/* clase para mostrar en pantalla el listado de las compras
 * 
 */
public class ListadoMisCompras extends Activity {
	private ArrayList<String> datos = new ArrayList<String>();
	private ListView lstOpciones;
	private Iterator<ComprasVO> icompras;
	private TextView datos_compra;
	private ArrayList<ComprasVO> compras;
	private ListadoCompras mis_compras;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		mis_compras = ListadoCompras.getInstance();
		setContentView(R.layout.activity_listadomiscompras);
		datos_compra = (TextView) findViewById(R.id.datosMisCompras);
		lstOpciones = (ListView) findViewById(R.id.miListaCompras);

		/*
		 * paso los nombres de productos a cargar en el listview a un arreglo de
		 * string
		 */
		cargarCompras();
		lstOpciones.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String nombre_compra = parent.getItemAtPosition(position)
						.toString();
				view.setSelected(true);
				if (view.isEnabled()) {
					view.setEnabled(false);
				} else {
					view.setEnabled(true);

				}

				mostrarDatosCompra(nombre_compra);

			}

		});

	}

	public void mostrarMensaje(String mensaje) {
		Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG)
				.show();
	}

	@Override
	public void onResume() {
		super.onResume();
		if (!mis_compras.tieneCompras())
			mis_compras.cargarCompras();

	}

	public void listarMisCompras() {

		if (compras != null) {
			icompras = compras.iterator();

			while (icompras.hasNext()) {

				ComprasVO p = icompras.next();
				String nombre = p.getCompra();
				datos.add(nombre);
				Log.e("dato", nombre);

			}

			ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
					R.layout.simplerow, datos);
			lstOpciones.setAdapter(adaptador);

		}
	}

	public void cargarCompras() {
		if (mis_compras.tieneCompras())
			compras = mis_compras.getMisCompras();
		else {
			mis_compras.cargarCompras();
			compras = mis_compras.getMisCompras();
		}

		listarMisCompras();
	}

	public void mostrarDatosCompra(String idcompra) {
		datosCompra datos_mi_compra = mis_compras.darDatosCompra(idcompra);
		String estado;
		if (datos_mi_compra.isEntregado()) {
			estado = "Compra pendiente de entrega";
			datos_compra.setText(estado + "\n" + "La compra tenia "
					+ datos_mi_compra.getTotalpaquetes() + " paquetes" + "\n"
					+ "El costo ascendio a $ " + datos_mi_compra.getPrecio()
					+ "\n" + "La compra genero "
					+ datos_mi_compra.getPuntosgenerados() + " puntos" + "\n");

		} else {
			estado = "Compra entregada";
			datos_compra.setText(estado + "\n" + "La compra tenia "
					+ datos_mi_compra.getTotalpaquetes() + " paquetes" + "\n"
					+ "El costo ascendio a $ " + datos_mi_compra.getPrecio()
					+ "\n" + "La compra genero "
					+ datos_mi_compra.getPuntosgenerados() + " puntos" + "\n");
		}

	}

}
