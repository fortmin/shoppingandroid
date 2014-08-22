package com.fortmin.proshopping;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.fortmin.proshopping.gae.Nube;
import com.fortmin.proshopping.gae.ShoppingNube;
import com.fortmin.proshopping.logica.shopping.model.ComprasVO;
import com.fortmin.proshopping.persistencia.BDElementoRf;
import com.fortmin.proshopping.persistencia.DatosLocales;

public class PruebasUnitarias extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_pruebas_unitarias);

		String queProbar = "GetCompras";
		// String queProbar = "DatosLocales";

		if (queProbar.equals("GetCompras")) {
			Nube nube = new Nube(ShoppingNube.OPE_GET_COMPRAS);
			ArrayList<ComprasVO> result = nube.getCompras("jafortti");
			if (result != null)
				Log.i("FER", "Me devolvio " + result.size() + " compras");
			else
				Log.i("FER", "GetCompras me devolvio null");
		} else if (queProbar.equals("GetCompras")) {

			DatosLocales dl = DatosLocales.getInstance();
			String resp = null;

			// Borro la base
			dl.obtenerBaseEscritura(getApplicationContext());
			dl.limpiarBase();
			dl.cerrarBase();

			// Me fijo que las tablas esten vacias
			dl.obtenerBaseLectura(getApplicationContext());
			dl.listarTablaElementos();
			dl.listarTablaPaquetes();
			dl.listarTablaProductos();
			dl.cerrarBase();

			// Me creo el BEACON y el NFC a mano
			BDElementoRf bdElemRf1 = new BDElementoRf("BEACON001", "BEACON",
					20, "MundialAbrigado");
			BDElementoRf bdElemRf2 = new BDElementoRf("NFC001", "NFC", 20,
					"MundialRuidoso");

			// Inserto un Beacon y un NFC
			dl.obtenerBaseEscritura(getApplicationContext());
			dl.insertElementoRf(bdElemRf1);
			dl.insertElementoRf(bdElemRf2);
			dl.cerrarBase();

			// Me fijo si dio de alta el Beacon y el NFC
			dl.obtenerBaseLectura(getApplicationContext());
			if (dl.hasElementoRf("BEACON001"))
				Log.i("FER", "Tiene el BEACON001");
			else
				Log.i("FER", "No tiene el BEACON001");
			if (dl.hasElementoRf("NFC001"))
				Log.i("FER", "Tiene el NFC001");
			else
				Log.i("FER", "No tiene el NFC001");
			dl.cerrarBase();

			// Le cambio el RSSI al Beacon para ver si lo cambia en la tabla
			dl.obtenerBaseEscritura(getApplicationContext());
			bdElemRf1.setRssi(100);
			dl.updateElementoRf(bdElemRf1);
			dl.listarTablaElementos();
			dl.cerrarBase();

			// Le digo que encontre un NFC y luego un Beacon con RSSI 40
			resp = dl.encontreElementoRf(getApplicationContext(), bdElemRf2);
			Log.i("FER", "encontreElementoRf con " + bdElemRf2.getElementoRf()
					+ " devolvio " + resp);
			bdElemRf1.setRssi(40);
			resp = dl.encontreElementoRf(getApplicationContext(), bdElemRf1);
			Log.i("FER", "encontreElementoRf con " + bdElemRf1.getElementoRf()
					+ " devolvio " + resp);

			// Veo como quedo la tabla de ElementosRf
			dl.obtenerBaseLectura(getApplicationContext());
			dl.listarTablaElementos();
			dl.cerrarBase();

			// Ahora borro todo porque voy a usar la nube
			dl.obtenerBaseEscritura(getApplicationContext());
			dl.limpiarBase();
			dl.cerrarBase();

			// Ahora le voy a decir que encontre el BEACON001 y el NFC001
			resp = dl.encontreElementoRf(getApplicationContext(), bdElemRf2);
			Log.i("FER", "encontreElementoRf con " + bdElemRf2.getElementoRf()
					+ " devolvio " + resp);
			resp = dl.encontreElementoRf(getApplicationContext(), bdElemRf1);
			Log.i("FER", "encontreElementoRf con " + bdElemRf1.getElementoRf()
					+ " devolvio " + resp);

			// Veo como quedan las tablas de ElementosRf, Paquetes y Productos
			dl.obtenerBaseLectura(getApplicationContext());
			dl.listarTablaElementos();
			dl.listarTablaPaquetes();
			dl.listarTablaProductos();
			dl.cerrarBase();
		}

	}

}
