package com.fortmin.proshopping.gae;

import java.io.IOException;
import java.util.List;

import android.os.AsyncTask;
import android.util.Log;

import com.fortmin.proshopping.CloudEndpointUtils;
import com.fortmin.proshopping.logica.shopping.Shopping;
import com.fortmin.proshopping.logica.shopping.Shopping.ActualizarPosicion;
import com.fortmin.proshopping.logica.shopping.Shopping.AgregarItemCarrito;
import com.fortmin.proshopping.logica.shopping.Shopping.AgregarPuntos;
import com.fortmin.proshopping.logica.shopping.Shopping.CheckoutCarrito;
import com.fortmin.proshopping.logica.shopping.Shopping.EgresoEstacionamiento;
import com.fortmin.proshopping.logica.shopping.Shopping.EliminarItemCarrito;
import com.fortmin.proshopping.logica.shopping.Shopping.GetCalibradoBeacon;
import com.fortmin.proshopping.logica.shopping.Shopping.GetCarritoCompleto;
import com.fortmin.proshopping.logica.shopping.Shopping.GetCompras;
import com.fortmin.proshopping.logica.shopping.Shopping.GetImagen;
import com.fortmin.proshopping.logica.shopping.Shopping.GetPaqueteCompleto;
import com.fortmin.proshopping.logica.shopping.Shopping.GetPaqueteRf;
import com.fortmin.proshopping.logica.shopping.Shopping.GetProductoCompleto;
import com.fortmin.proshopping.logica.shopping.Shopping.GetProductosPaquete;
import com.fortmin.proshopping.logica.shopping.Shopping.GetPuntajeCliente;
import com.fortmin.proshopping.logica.shopping.Shopping.IngresoEstacionamiento;
import com.fortmin.proshopping.logica.shopping.Shopping.QuitarPuntos;
import com.fortmin.proshopping.logica.shopping.model.CarritoVO;
import com.fortmin.proshopping.logica.shopping.model.ComprasVO;
import com.fortmin.proshopping.logica.shopping.model.ComprasVOCollection;
import com.fortmin.proshopping.logica.shopping.model.Imagen;
import com.fortmin.proshopping.logica.shopping.model.Mensaje;
import com.fortmin.proshopping.logica.shopping.model.Paquete;
import com.fortmin.proshopping.logica.shopping.model.PaqueteVO;
import com.fortmin.proshopping.logica.shopping.model.Producto;
import com.fortmin.proshopping.logica.shopping.model.ProductoCollection;
import com.fortmin.proshopping.logica.shopping.model.ProductoExtVO;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson.JacksonFactory;

public class ShoppingNube extends AsyncTask<Object, Void, Object> {

	public static String OPE_GET_PAQUETE_RF = "GetPaqueteRf";
	public static String OPE_GET_PRODUCTOS_PAQUETE = "GetProductosPaquete";
	public static String OPE_GET_PRODUCTO_COMPLETO = "GetProductoCompleto";
	public static String OPE_GET_PAQUETE_COMPLETO = "GetPaqueteCompleto";
	public static String OPE_INGRESO_ESTACIONAMIENTO = "IngresoEstacionamiento";
	public static String OPE_EGRESO_ESTACIONAMIENTO = "EgresoEstacionamiento";
	public static String OPE_GET_CALIBRADO_BEACON = "GetCalibradoBeacon";
	public static String OPE_GET_IMAGEN = "GetImagen";
	public static String OPE_GET_PUNTAJE_CLIENTE = "GetPuntajeCliente";
	public static String OPE_ACTUALIZAR_POSICION = "ActualizarPosicion";
	public static String OPE_AGREGAR_ITEM_CARRITO = "AgregarItemCarrito";
	public static String OPE_ELIMINAR_ITEM_CARRITO = "EliminarItemCarrito";
	public static String OPE_GET_CARRITO_COMPLETO = "GetCarritoCompleto";
	public static String OPE_CHECKOUT_CARRITO = "CheckoutCarrito";
	public static String OPE_GET_COMPRAS = "GetCompras";
	public static String OPE_AGREGAR_PUNTOS = "AgregarPuntos";
	public static String OPE_QUITAR_PUNTOS = "QuitarPuntos";

	private String TAG = "ProShopping";
	private String operacion; // Señala el nombre de la operacion a ejecutar

	public ShoppingNube(String operacion) {
		this.operacion = operacion;
	}

	@Override
	protected Object doInBackground(Object... params) {
		Shopping.Builder endpointBuilder = new Shopping.Builder(
				AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
				new HttpRequestInitializer() {
					public void initialize(HttpRequest httpRequest) {
					}
				});
		endpointBuilder.setApplicationName("fortminproshop"); // Nombre de la
																// aplicacion
																// GAE
		Shopping endpoint = CloudEndpointUtils.updateBuilder(endpointBuilder)
				.build();
		try {
			if (operacion.equals(OPE_GET_PAQUETE_RF)) {
				String idElementoRF = (String) params[0];
				Log.i(this.TAG, "ShoppingNube->" + OPE_GET_PAQUETE_RF + "->"
						+ idElementoRF);
				GetPaqueteRf execgae = endpoint.getPaqueteRf(idElementoRF);
				Paquete paquete = execgae.execute();
				Log.i(TAG,
						"ShoppingNube->Obtuve el paquete : "
								+ paquete.getNombre());
				return paquete;
			}
			if (operacion.equals(OPE_GET_PAQUETE_COMPLETO)) {
				String idElementoRF = (String) params[0];
				Log.i(this.TAG, "ShoppingNube->" + OPE_GET_PAQUETE_COMPLETO
						+ "->" + idElementoRF);
				GetPaqueteCompleto execgae = endpoint
						.getPaqueteCompleto(idElementoRF);
				PaqueteVO paquete = execgae.execute();
				Log.i(TAG,
						"ShoppingNube->Obtuve el paquete : "
								+ paquete.getNombre());
				return paquete;
			}
			if (operacion.equals(OPE_GET_PRODUCTOS_PAQUETE)) {
				String nomPaquete = (String) params[0];
				Log.i(this.TAG, "ShoppingNube->" + OPE_GET_PRODUCTOS_PAQUETE
						+ "->" + nomPaquete);
				GetProductosPaquete execgae = endpoint
						.getProductosPaquete(nomPaquete);
				ProductoCollection resp = execgae.execute();
				List<Producto> productos = resp.getItems();
				Log.i(TAG, "ShoppingNube->Obtuve cantidad de productos : "
						+ productos.size());
				return productos;
			}
			if (operacion.equals(OPE_GET_PRODUCTO_COMPLETO)) {
				String comercio = (String) params[0];
				String codprod = (String) params[1];
				Log.i(this.TAG, "ShoppingNube->" + OPE_GET_PRODUCTO_COMPLETO
						+ "->" + comercio + "::" + codprod);
				GetProductoCompleto execgae = endpoint.getProductoCompleto(
						comercio, codprod);
				ProductoExtVO producto = execgae.execute();
				Log.i(TAG,
						"ShoppingNube->Obtuve el producto : "
								+ producto.getComercio() + "::"
								+ producto.getCodigo());
				return producto;
			}
			if (operacion.equals(OPE_GET_CALIBRADO_BEACON)) {
				String elementoRf = (String) params[0];
				Log.i(this.TAG, "ShoppingNube->" + OPE_GET_CALIBRADO_BEACON
						+ "->" + elementoRf);
				GetCalibradoBeacon execgae = endpoint
						.getCalibradoBeacon(elementoRf);
				Mensaje resp = execgae.execute();
				Log.i(TAG,
						"ShoppingNube->" + resp.getOperacion() + "->"
								+ resp.getMensaje());
				return resp;
			}
			if (operacion.equals(OPE_INGRESO_ESTACIONAMIENTO)) {
				String elementoRf = (String) params[0];
				String usuario = (String) params[1];
				Log.i(this.TAG, "ShoppingNube->" + OPE_INGRESO_ESTACIONAMIENTO
						+ "->" + elementoRf + "::" + usuario);
				IngresoEstacionamiento execgae = endpoint
						.ingresoEstacionamiento(elementoRf, usuario);
				Mensaje resp = execgae.execute();
				Log.i(TAG,
						"ShoppingNube->" + resp.getOperacion() + "->"
								+ resp.getMensaje());
				return resp;
			}
			if (operacion.equals(OPE_EGRESO_ESTACIONAMIENTO)) {
				String elementoRf = (String) params[0];
				String usuario = (String) params[1];
				Log.i(this.TAG, "ShoppingNube->" + OPE_EGRESO_ESTACIONAMIENTO
						+ "->" + elementoRf + "::" + usuario);
				EgresoEstacionamiento execgae = endpoint.egresoEstacionamiento(
						elementoRf, usuario);
				Mensaje resp = execgae.execute();
				Log.i(TAG,
						"ShoppingNube->" + resp.getOperacion() + "->"
								+ resp.getMensaje());
				return resp;
			}
			if (operacion.equals(OPE_GET_IMAGEN)) {
				String comercio = (String) params[0];
				String producto = (String) params[1];
				Log.i(this.TAG, "ShoppingNube->" + OPE_GET_IMAGEN + "->"
						+ comercio + "::" + producto);
				GetImagen execgae = endpoint.getImagen(comercio, producto);
				Imagen imagen = execgae.execute();
				Log.i(TAG,
						"ShoppingNube->imagen recibida de size "
								+ imagen.size());
				return imagen;
			}
			if (operacion.equals(OPE_GET_PUNTAJE_CLIENTE)) {
				String usuario = (String) params[0];
				Log.i(this.TAG, "ShoppingNube->" + OPE_GET_PUNTAJE_CLIENTE
						+ "->" + usuario);
				GetPuntajeCliente execgae = endpoint.getPuntajeCliente(usuario);
				Mensaje resp = execgae.execute();
				Log.i(TAG,
						"ShoppingNube->" + resp.getOperacion() + "->"
								+ resp.getValor());
				return resp;
			}
			if (operacion.equals(OPE_ACTUALIZAR_POSICION)) {
				String usuario = (String) params[0];
				String elementoRf = (String) params[1];
				String tipo = (String) params[2];
				Log.i(this.TAG, "ShoppingNube->" + OPE_ACTUALIZAR_POSICION
						+ "->" + usuario + "::" + elementoRf + "::" + tipo);
				ActualizarPosicion execgae = endpoint.actualizarPosicion(
						usuario, elementoRf, tipo);
				Mensaje resp = execgae.execute();
				Log.i(TAG,
						"ShoppingNube->" + resp.getOperacion() + "->"
								+ resp.getMensaje());
				return resp;
			}
			if (operacion.equals(OPE_AGREGAR_ITEM_CARRITO)) {
				String usuario = (String) params[0];
				String nomPaquete = (String) params[1];
				Log.i(this.TAG, "ShoppingNube->" + OPE_AGREGAR_ITEM_CARRITO
						+ "->" + usuario + "::" + nomPaquete);
				AgregarItemCarrito execgae = endpoint.agregarItemCarrito(
						usuario, nomPaquete);
				Mensaje resp = execgae.execute();
				Log.i(TAG,
						"ShoppingNube->" + resp.getOperacion() + "->"
								+ resp.getMensaje());
				return resp;
			}
			if (operacion.equals(OPE_ELIMINAR_ITEM_CARRITO)) {
				String usuario = (String) params[0];
				String nomPaquete = (String) params[1];
				Log.i(this.TAG, "ShoppingNube->" + OPE_ELIMINAR_ITEM_CARRITO
						+ "->" + usuario + "::" + nomPaquete);
				EliminarItemCarrito execgae = endpoint.eliminarItemCarrito(
						usuario, nomPaquete);
				Mensaje resp = execgae.execute();
				Log.i(TAG,
						"ShoppingNube->" + resp.getOperacion() + "->"
								+ resp.getMensaje());
				return resp;
			}
			if (operacion.equals(OPE_GET_CARRITO_COMPLETO)) {
				String usuario = (String) params[0];
				Log.i(this.TAG, "ShoppingNube->" + OPE_GET_CARRITO_COMPLETO
						+ "->" + usuario);
				GetCarritoCompleto execgae = endpoint
						.getCarritoCompleto(usuario);
				CarritoVO resp = execgae.execute();
				if (resp != null) {
					Log.i(TAG, "ShoppingNube->" + resp.getCliente() + "->"
							+ "Items=" + resp.getCantItems());
				} else
					Log.i(TAG, "ShoppingNube->NULL");
				return resp;
			}
			if (operacion.equals(OPE_CHECKOUT_CARRITO)) {
				String usuario = (String) params[0];
				Log.i(this.TAG, "ShoppingNube->" + OPE_CHECKOUT_CARRITO + "->"
						+ usuario);
				CheckoutCarrito execgae = endpoint.checkoutCarrito(usuario);
				Mensaje resp = execgae.execute();
				Log.i(TAG,
						"ShoppingNube->" + resp.getOperacion() + "->"
								+ resp.getMensaje());
				return resp;
			}
			if (operacion.equals(OPE_GET_COMPRAS)) {
				String usuario = (String) params[0];
				Log.i(this.TAG, "ShoppingNube->" + OPE_GET_COMPRAS + "->"
						+ usuario);
				GetCompras execgae = endpoint.getCompras(usuario);
				ComprasVOCollection resp = execgae.execute();
				List<ComprasVO> compras = resp.getItems();
				if (compras != null)
					Log.i(TAG,
							"ShoppingNube->Cantidad de compras->"
									+ compras.size());
				else
					Log.i(TAG,
							"ShoppingNube->Cantidad de compras->Devolvio null");
				return compras;
			}
			if (operacion.equals(OPE_AGREGAR_PUNTOS)) {
				String usuario = (String) params[0];
				int puntos = (Integer) params[1];
				Log.i(this.TAG, "ShoppingNube->" + OPE_AGREGAR_PUNTOS + "->"
						+ usuario + "->" + puntos);
				AgregarPuntos execgae = endpoint.agregarPuntos(puntos, usuario);
				Mensaje resp = execgae.execute();
				Log.i(TAG,
						"ShoppingNube->" + resp.getOperacion() + "->"
								+ resp.getMensaje());
				return resp;
			}
			if (operacion.equals(OPE_QUITAR_PUNTOS)) {
				String usuario = (String) params[0];
				int puntos = (Integer) params[1];
				Log.i(this.TAG, "ShoppingNube->" + OPE_QUITAR_PUNTOS + "->"
						+ usuario + "->" + puntos);
				QuitarPuntos execgae = endpoint.quitarPuntos(puntos, usuario);
				Mensaje resp = execgae.execute();
				Log.i(TAG,
						"ShoppingNube->" + resp.getOperacion() + "->"
								+ resp.getMensaje());
				return resp;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
