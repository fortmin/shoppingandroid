package com.fortmin.proshopping.persistencia;

import java.util.Iterator;
import java.util.LinkedList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.fortmin.proshopping.gae.Nube;
import com.fortmin.proshopping.gae.ShoppingNube;
import com.fortmin.proshopping.logica.shopping.model.Imagen;
import com.fortmin.proshopping.logica.shopping.model.PaqueteVO;
import com.fortmin.proshopping.logica.shopping.model.ProductoVO;

public class DatosLocales {

	private static String DBPROSHOP = "DbProshop";
	private Base base;
	private SQLiteDatabase db;
	private Context cont;
	private static DatosLocales instancia;

	private DatosLocales() {
	}

	public static DatosLocales getInstance() {
		if (instancia == null)
			instancia = new DatosLocales();
		return instancia;

	}

	/*
	 * Abre la base en modalidad escritura
	 */
	public void obtenerBaseEscritura(Context contexto) {
		cont = contexto;
		base = new Base(cont, DBPROSHOP, null, 1);
		db = base.getWritableDatabase();
	}

	/*
	 * Abre la base en modalidad lectura
	 */
	public void obtenerBaseLectura(Context contexto) {
		cont = contexto;
		base = new Base(cont, DBPROSHOP, null, 1);
		db = base.getReadableDatabase();
	}

	/*
	 * Funcion para obtener todos los paquetes almacenados en la base SQLite
	 * Si no encuentra ningun paquete devuelve null
	 */
	public PaquetesVO obtenerPaquetes(Context contexto) {
		PaquetesVO paquetes = null;
		obtenerBaseLectura(cont);
		Cursor c = db.query(Base.TABLE_PAQUETES, new String[] { Base.PAQUETE },
				null, null, null, null, null, null);
		if (c.getCount() > 0) {
			paquetes = new PaquetesVO();
			c.moveToFirst();
			boolean termine = true;
			PaqueteVO paqvo;
			while (termine) {
				paqvo = leerPaquete(c.getString(0));
				paquetes.agregarPaquete(paqvo);
				termine = c.moveToNext();
			}
			Log.i("obtenerPaquetes", "retorno la lista de paquetes");
		}
		else 
			Log.i("obtenerPaquetes", "no encontro ningun paquete");			
		db.close();
		return paquetes;
	}

	/*
	 * Funcion para obtener todos los paquetes almacenados en la base SQLite
	 */
	public PaqueteVO obtenerPaquete(Context contexto, String nompaq) {
		obtenerBaseLectura(cont);
		PaqueteVO paquete = leerPaquete(nompaq);
		db.close();
		Log.i("PaqueteVO", "retorno el paquete");
		return paquete;
	}

	/*
	 * Funcion para agregar un nuevo paquete y sus productos en base a un
	 * elementoRF localizado siempre y cuando el elemento no existiera Si el
	 * elemento ya existe y es un Beacon solo se actualiza el RSSI Si el
	 * elemento ya existe y es un Tag no se hace nada
	 */
	public String encontreElementoRf(Context contexto, BDElementoRf bdElemRf) {
		String resp = null;
		obtenerBaseEscritura(contexto);
		if (hasElementoRf(bdElemRf.getElementoRf())) {
			if (bdElemRf.getTipo().equals("BEACON")) {
				int result = updateElementoRf(bdElemRf);
				if (result == 1)
					resp = "UPDATE_OK";
				else
					resp = "UPDATE_FALLIDO";
			} else
				resp = "TIPO_NFC";
		} else {
			Nube nube = new Nube(ShoppingNube.OPE_GET_PAQUETE_COMPLETO);
			PaqueteVO paquete = nube.ejecutarGetPaqueteCompleto(bdElemRf
					.getElementoRf());
			if (paquete != null) {
				bdElemRf.setPaquete(paquete.getNombre());
				long result = insertElementoRf(bdElemRf);
				if (result != -1) {
					result = insertPaquete(paquete);
					if (result != -1)
						resp = "INSERT_OK";
					else
						resp = "INSERT_PAQUETE_FALLIDO";
				} else
					resp = "INSERT_ELEMRF_FALLIDO";
			} else
				resp = "GET_PAQUETE_COMPLETO_FALLIDO";
		}
		db.close();
		return resp;
	}

	/*
	 * Devuelve un paquete completo con todos sus productos
	 */
	public PaqueteVO leerPaquete(String nomPaquete) {
		PaqueteVO paquete = new PaqueteVO();
		Cursor c = db.query(Base.TABLE_PAQUETES, new String[] { Base.PAQUETE,
				Base.CANTPROD, Base.PUNTOS, Base.PRECIO },
				Base.PAQUETE + " =?", new String[] { nomPaquete }, null, null,
				null, null);
		c.moveToFirst();
		Log.e("leerPaquete", Integer.toString(c.getCount()));
		paquete.setNombre(c.getString(c.getColumnIndex(Base.PAQUETE)));
		paquete.setCantProductos(c.getInt(c.getColumnIndex(Base.CANTPROD)));
		paquete.setPuntos(c.getInt(c.getColumnIndex(Base.PUNTOS)));
		paquete.setPrecio(c.getFloat(c.getColumnIndex(Base.PRECIO)));
		Log.e("leerPaquete", "campos seteados");
		paquete.setProductos(leerProductos(nomPaquete));
		db.close();
		return paquete;
	}

	/*
	 * Devuelve una lista con todos los productos del paquete o null
	 */
	public LinkedList<ProductoVO> leerProductos(String nomPaquete) {
		LinkedList<ProductoVO> prods = null;
		Cursor c = db.query(Base.TABLE_PRODUCTOS, new String[] { Base.COMERCIO,
				Base.PRODUCTO, Base.PRECIO }, Base.PAQUETE + " =?",
				new String[] { nomPaquete }, null, null, null, null);
		if (c.getCount() > 0) {
			prods = new LinkedList<ProductoVO>();
			c.moveToFirst();
			boolean termine = true;
			while (termine) {
				ProductoVO producto = new ProductoVO();
				producto.setComercio(c.getString(0));
				producto.setNombre(c.getString(1));
				producto.setPrecio(c.getFloat(2));
				prods.add(producto);
				termine = c.moveToNext();
			}
		}
		return prods;
	}

	/*
	 * Devuelve true si la tabla ElementosRf ya tiene un elemento con esa Id
	 */
	public boolean hasElementoRf(String elemRf) {
		Cursor c = db.query(Base.TABLE_ELEMENTOSRF,
				new String[] { Base.ELEMENTORF }, Base.ELEMENTORF + " =?",
				new String[] { elemRf }, null, null, null, null);
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
		int result = db.update(Base.TABLE_ELEMENTOSRF, values,
				Base.ELEMENTORF + " =?",
				new String[] { bdElemRf.getElementoRf() });
		return result;
	}

	/*
	 * Inserta un paquete en la tabla de paquetes y luego
	 * todos sus productos en la tabla de productos
	 */
	public long insertPaquete(PaqueteVO paquete) {
		obtenerBaseEscritura(cont);
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
				Log.e("Inserto Paquete", "inserte el paquete");
				valuesprod.put(Base.PAQUETE, paquete.getNombre());
				valuesprod.put(Base.PRODUCTO, prod.getNombre());
				valuesprod.put(Base.COMERCIO, prod.getComercio());
				valuesprod.put(Base.PRECIO, prod.getPrecio());
				db.insert(Base.TABLE_PRODUCTOS, null, valuesprod);
			}
			db.close();
		}
		return result;
	}
	
	/*
	 * Obtener una imagen identificada por comercio y producto
	 */
	public Imagen obtenerImagenProducto(String comercio, String producto) {
		if (!this.hasImagen(comercio, producto)) {
			Nube nube = new Nube(ShoppingNube.OPE_GET_IMAGEN);
			Imagen newimg = nube.ejecutarGetImagen(comercio, producto);
			if (newimg != null)
				actualizarImagenProducto(comercio,producto,newimg);
		}
		Imagen imagen = new Imagen();
		obtenerBaseLectura(cont);
		Cursor c = db.query(Base.TABLE_IMAGENES, new String[] { Base.TIPOIMAGEN,
				Base.IMAGEN }, Base.COMERCIO + " =?" + " AND "
						+ Base.PRODUCTO + "=?",
				new String[] { comercio, producto }, null, null, null, null);
		c.moveToFirst();
		byte[] img = c.getBlob(1);
		imagen.set(c.getString(0), img);
		db.close();
		return imagen;
	}

	/*
	 * Actualizar una imagen en la tabla de imagenes, si ya existe
	 * la borra e inserta la nueva imagen.
	 */
	public long actualizarImagenProducto(String comercio, String producto,
			Imagen imagen) {
		obtenerBaseEscritura(cont);
		if (hasImagen(comercio, producto))
			borrarImagen(comercio, producto);
		ContentValues valuesimg = new ContentValues();
		valuesimg.put(Base.COMERCIO, comercio);
		valuesimg.put(Base.PRODUCTO, producto);
		valuesimg.put(Base.TIPOIMAGEN, imagen.getTipoImg());
		valuesimg.put(Base.IMAGEN, imagen.getImagen());
		long result = db.insert(Base.TABLE_IMAGENES, null, valuesimg);
		db.close();
		return result;
	}

	/* 
	 * Borra la imagen identificado por comercio y producto
	 */
	public void borrarImagen(String comercio, String producto) {
		db.delete(Base.TABLE_IMAGENES, Base.COMERCIO + " =?" + " AND "
				+ Base.PRODUCTO + "=?", new String[] { comercio, producto });
	}

	/*
	 * Busca en la tabla de imagenes a ver si existe una imagen para ese
	 * comercio y producto devolviendo true en caso afirmativo y false en caso
	 * contrario
	 */
	public boolean hasImagen(String comercio, String producto) {
		Cursor c = db.query(Base.TABLE_IMAGENES, new String[] { Base.COMERCIO,
				Base.PRODUCTO }, Base.COMERCIO + " =?" + " AND "
				+ Base.PRODUCTO + "=?", new String[] { comercio, producto },
				null, null, null, null);
		return (c.getCount() == 1);
	}

	/*
	 * 
	 */

}
