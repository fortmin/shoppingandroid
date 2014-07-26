package com.fortmin.proshopping.persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class CrearBase extends SQLiteOpenHelper {

	// Sentencia SQL para crear la tabla de Usuarios
	String sqlCreateElemRf = "CREATE TABLE ElementosRF (" +
			"elementoRf TEXT PRIMARY KEY," +
			"paquete TEXT," +
			"cantprod INTEGER," +
			"puntos INTEGER," +
			"precio REAL)";
	
	String sqlCreateProductos = "CREATE TABLE Productos (" +
			"paquete TEXT," +
			"comercio TEXT," +
			"producto TEXT," +
			"precio REAL," +
			"PRIMARY KEY (paquete, comercio, producto))";
	
	public CrearBase(Context contexto, String nombre,
			CursorFactory factory, int version) {
		super(contexto, nombre, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// Se ejecuta la sentencia SQL de creación de la tabla
		db.execSQL(sqlCreateElemRf);
		db.execSQL(sqlCreateProductos);
		}

	@Override
	public void onUpgrade(SQLiteDatabase db, int versionAnterior,
			int versionNueva) {
		// Se elimina la versión anterior de la tabla
		db.execSQL("DROP TABLE IF EXISTS ElementosRF");
		db.execSQL("DROP TABLE IF EXISTS Productos");
		// Se crea la nueva versión de la tabla
		db.execSQL(sqlCreateElemRf);
		db.execSQL(sqlCreateProductos);
	}
}