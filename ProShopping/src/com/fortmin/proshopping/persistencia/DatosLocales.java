package com.fortmin.proshopping.persistencia;

import java.util.Iterator;
import java.util.LinkedList;

import com.fortmin.proshopping.gae.Nube;
import com.fortmin.proshopping.gae.ShoppingNube;
import com.fortmin.proshopping.logica.shopping.model.PaqueteVO;
import com.fortmin.proshopping.logica.shopping.model.ProductoVO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatosLocales {

	private static String DBPROSHOP = "DbProshop";
	private Base base;
	private SQLiteDatabase db;

	public SQLiteDatabase obtenerBaseEscritura(Context contexto) {
		base = new Base(contexto, DBPROSHOP, null, 1);
		return base.getWritableDatabase();
	}
	
	public SQLiteDatabase obtenerBaseLectura(Context contexto) {
		base = new Base(contexto, DBPROSHOP, null, 1);
		return base.getReadableDatabase();
	}

	/*
	 * Funcion para obtener todos los datos del paquete incluyendo sus productos
	 * Se usa para desplegarle al cliente el paquete y sus productos
	 */
	public PaqueteVO obtenerPaquetes(Context contexto, String nomPaquete) {
		PaqueteVO paquete = null;
		db = this.obtenerBaseEscritura(contexto);
		paquete = leerPaquete(nomPaquete);
		db.close();
		return paquete;
	}
	
	/* 
	 * Funcion para agregar un nuevo paquete y sus productos en base
	 * a un elementoRF localizado siempre y cuando el elemento no existiera
	 * Si el elemento ya existe y es un Beacon solo se actualiza el RSSI
	 * Si el elemento ya existe y es un Tag no se hace nada
	 */
	public String encontreElementoRf(Context contexto, BDElementoRf bdElemRf) {
		String resp = null;
		db = this.obtenerBaseEscritura(contexto);		
		if (hasElementoRf(bdElemRf.getElementoRf())) {
			if (bdElemRf.getTipo().equals("BEACON")) {
				int result = updateElementoRf(bdElemRf);
				if (result == 1)
					resp = "UPDATE_OK";
				else 
					resp = "UPDATE_FALLIDO";
			}
			else 
				resp = "TIPO_NFC";
		}
		else {
			long result = insertElementoRf(bdElemRf);
			if (result != -1) {
				Nube nube = new Nube(ShoppingNube.OPE_GET_PAQUETE_COMPLETO);
				PaqueteVO paquete = nube.ejecutarGetPaqueteCompleto(bdElemRf.getElementoRf());
				if (paquete != null) {
					result = insertPaquete(paquete);
					if (result != -1)
						resp = "INSERT_OK";
					else
						resp = "INSERT_PAQUETE_FALLIDO";
				}
			}
			else
				resp = "INSERT_ELEMRF_FALLIDO";
		}
		db.close();
		return resp;
	}
	
	public PaqueteVO leerPaquete(String nomPaquete) {
		PaqueteVO paquete = null;
		Cursor c = db.query(Base.TABLE_PAQUETES, new String[] {Base.PAQUETE, Base.CANTPROD, Base.PUNTOS, Base.PRECIO}, Base.PAQUETE+" =?", new String[] {nomPaquete}, null, null, null, null);
		paquete = new PaqueteVO();
		paquete.setNombre(c.getString(0));
		paquete.setCantProductos(c.getInt(1));
		paquete.setPuntos(c.getInt(2));
		paquete.setPrecio(c.getFloat(3));
		paquete.setProductos(leerProductos(nomPaquete));
		return paquete;
	}
	
	public LinkedList<ProductoVO> leerProductos(String nomPaquete) {
		LinkedList<ProductoVO> prods = new LinkedList<ProductoVO>();
		Cursor c = db.query(Base.TABLE_PRODUCTOS, new String[] {Base.COMERCIO, Base.PRODUCTO, Base.PRECIO}, Base.PAQUETE+" =?", new String[] {nomPaquete}, null, null, null, null);
		if (c.moveToFirst()) {
			boolean termine = false;
			ProductoVO producto = new ProductoVO();
			while (!termine) {
				producto.setComercio(c.getString(0));
				producto.setNombre(c.getString(1));
				producto.setPrecio(c.getFloat(2));
				prods.add(producto);
				termine = c.moveToNext();
			}
		}
		return prods;
	}
	
	public boolean hasElementoRf(String elemRf) {
		Cursor c = db.query(Base.TABLE_ELEMENTOSRF, new String[] {Base.ELEMENTORF}, Base.ELEMENTORF+" =?", new String[] {elemRf}, null, null, null, null);
		return (c.getCount() == 1);
	}
	
	public long insertElementoRf(BDElementoRf bdElemRf) {
		ContentValues values = new ContentValues();
		values.put(Base.ELEMENTORF, bdElemRf.getElementoRf());
		values.put(Base.TIPO, bdElemRf.getTipo());
		values.put(Base.RSSI, bdElemRf.getRssi());
		values.put(Base.PAQUETE, bdElemRf.getPaquete());
		long result = db.insert(Base.TABLE_ELEMENTOSRF, null, values);
		return result;
	}
	
	public int updateElementoRf(BDElementoRf bdElemRf) {
		ContentValues values = new ContentValues();
		values.put(Base.RSSI, bdElemRf.getRssi());
		int result = db.update(Base.ELEMENTORF, values, Base.ELEMENTORF+" =?", new String[] {bdElemRf.getElementoRf()});
		return result;
	}

	public long insertPaquete(PaqueteVO paquete) {
		ContentValues valuespaq = new ContentValues();
		valuespaq.put(Base.PAQUETE, paquete.getNombre());
		valuespaq.put(Base.CANTPROD, paquete.getCantProductos());
		valuespaq.put(Base.PUNTOS, paquete.getPuntos());
		valuespaq.put(Base.PRECIO, paquete.getPrecio());
		long result = db.insert(Base.TABLE_PAQUETES, null, valuespaq);
		if (result != -1) {
			ContentValues valuesprod = new ContentValues();		
			ProductoVO prod = null;
			Iterator<ProductoVO> iprods = paquete.getProductos().iterator();
			while (iprods.hasNext()) {
				prod = iprods.next();
				valuesprod.put(Base.PAQUETE, paquete.getNombre());
				valuesprod.put(Base.PRODUCTO, prod.getNombre());
				valuesprod.put(Base.COMERCIO, prod.getComercio());
				valuesprod.put(Base.PRECIO, prod.getPrecio());
				db.insert(Base.TABLE_PRODUCTOS, null, valuesprod);
			}
		}
		return result;
	}

}
