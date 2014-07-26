package com.fortmin.proshopping.persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DatosLocales {

	private SQLiteDatabase db;

	public void crearBase(Context context, String nombre) {
		CrearBase base = new CrearBase(context, nombre, null, 1);
		db = base.getWritableDatabase();
	}
	
	public void elementoRfDetectado() {
		// LO BUSCA
		// SI NO EXISTE LO INSERTA
		// SI EXISTE LE HACE UPDATE
	}
	
	public void obtenerPaquetes() {
		// OBTIENE UNA LISTA DE TODOS LOS PAQUETES EN LA BASE JUNTO CON SUS DATOS
	}
	
	
}
