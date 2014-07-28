package com.fortmin.proshopping.persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Base extends SQLiteOpenHelper {
	
	public static String TABLE_ELEMENTOSRF = "ElementosRF";
	public static String TABLE_PAQUETES = "Paquetes"; 
	public static String TABLE_PRODUCTOS = "Productos"; 
	
	public static String ELEMENTORF = "elementoRf";
	public static String TIPO = "tipo";
	public static String RSSI = "rssi";
	public static String PAQUETE = "paquete";
	public static String CANTPROD = "cantprod";
	public static String PUNTOS = "puntos";
	public static String PRECIO = "precio";
	public static String COMERCIO = "comercio";
	public static String PRODUCTO = "producto";
	
	// Sentencia SQL para crear la tabla de Usuarios
	String sqlCreateElemRf = "CREATE TABLE " + TABLE_ELEMENTOSRF + " (" +
			ELEMENTORF + " TEXT PRIMARY KEY," +
			TIPO + " TEXT," +							// El tipo es BEACON o TAG	
			RSSI + " INTEGER," +
			PAQUETE + " TEXT)";
	
	String sqlCreatePaquetes = "CREATE TABLE " + TABLE_PAQUETES +" (" +
			PAQUETE + " TEXT PRIMARY KEY," +
			CANTPROD + " INTEGER," +
			PUNTOS + " INTEGER," +
			PRECIO + " REAL)";
	
	String sqlCreateProductos = "CREATE TABLE " +TABLE_PRODUCTOS+" (" +
			PAQUETE + " TEXT," +
			COMERCIO + " TEXT," +
			PRODUCTO + " TEXT," +
			PRECIO + " REAL," +
			"PRIMARY KEY (paquete, comercio, producto))";
	
	public Base(Context contexto, String nombre,
			CursorFactory factory, int version) {
		super(contexto, nombre, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// Se ejecuta la sentencia SQL de creación de la tabla
		Log.e("Base Datos","BD creada");
		db.execSQL(sqlCreateElemRf);
		db.execSQL(sqlCreatePaquetes);
		db.execSQL(sqlCreateProductos);
		}

	@Override
	public void onUpgrade(SQLiteDatabase db, int versionAnterior,
			int versionNueva) {
		// Se elimina la versión anterior de la tabla
		db.execSQL("DROP TABLE IF EXISTS ElementosRF");
		db.execSQL("DROP TABLE IF EXISTS Paquetes");
		db.execSQL("DROP TABLE IF EXISTS Productos");
		// Se crea la nueva versión de la tabla
		db.execSQL(sqlCreateElemRf);
		db.execSQL(sqlCreatePaquetes);
		db.execSQL(sqlCreateProductos);
	}
}