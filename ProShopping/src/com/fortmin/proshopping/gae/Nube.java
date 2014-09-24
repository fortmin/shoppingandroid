package com.fortmin.proshopping.gae;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.util.Log;

import com.fortmin.proshopping.logica.shopping.model.CarritoVO;
import com.fortmin.proshopping.logica.shopping.model.ComprasVO;
import com.fortmin.proshopping.logica.shopping.model.EstacionamientoVO;
import com.fortmin.proshopping.logica.shopping.model.Paquete;
import com.fortmin.proshopping.logica.shopping.model.PaqueteVO;
import com.fortmin.proshopping.logica.shopping.model.Producto;
import com.fortmin.proshopping.logica.shopping.model.ProductoExtVO;

public class Nube {

    private String TAG = "ProShopping";
    private String operacion; // Señala el nombre de la operacion a ejecutar

    public Nube(String operacion) {
	this.setOperacion(operacion);
    }

    public com.fortmin.proshopping.logica.seguridad.model.Mensaje ejecutarLogin(
	    String clave, String usuario) {
	com.fortmin.proshopping.logica.seguridad.model.Mensaje resp = null;
	SeguridadNube comNube = new SeguridadNube(
		SeguridadNube.OPE_LOGIN_USUARIO);
	try {
	    resp = (com.fortmin.proshopping.logica.seguridad.model.Mensaje) comNube
		    .execute(clave, usuario).get();
	} catch (InterruptedException e) {
	    Log.e(TAG, e.toString());
	} catch (ExecutionException e) {
	    Log.e(TAG, e.toString());
	}

	return resp;
    }

    public com.fortmin.proshopping.logica.seguridad.model.Mensaje ejecutarLogoff(
	    String usuario) {
	com.fortmin.proshopping.logica.seguridad.model.Mensaje resp = null;
	SeguridadNube comNube = new SeguridadNube(
		SeguridadNube.OPE_LOGOFF_USUARIO);
	try {
	    resp = (com.fortmin.proshopping.logica.seguridad.model.Mensaje) comNube
		    .execute(usuario).get();
	} catch (InterruptedException e) {
	    Log.e(TAG, e.toString());
	} catch (ExecutionException e) {
	    Log.e(TAG, e.toString());
	}
	return resp;
    }

    public com.fortmin.proshopping.logica.seguridad.model.Mensaje ejecutarRegistro(
	    String usuario, String email, String nombre) {
	com.fortmin.proshopping.logica.seguridad.model.Mensaje resp = null;
	SeguridadNube comNube = new SeguridadNube(
		SeguridadNube.OPE_REGISTRO_USUARIO);
	try {
	    resp = (com.fortmin.proshopping.logica.seguridad.model.Mensaje) comNube
		    .execute(usuario, email, nombre).get();
	} catch (InterruptedException e) {
	    Log.e(TAG, e.toString());
	} catch (ExecutionException e) {
	    Log.e(TAG, e.toString());
	}
	return resp;
    }

    public Paquete ejecutarGetPaqueteRf(String elemRf) {
	Paquete resp = null;
	ShoppingNube comNube = new ShoppingNube(ShoppingNube.OPE_GET_PAQUETE_RF);
	try {
	    resp = (Paquete) comNube.execute(elemRf).get();
	} catch (InterruptedException e) {
	    Log.e(TAG, e.toString());
	} catch (ExecutionException e) {
	    Log.e(TAG, e.toString());
	}
	return resp;
    }

    public ProductoExtVO ejecutarGetProductoCompleto(String comercio,
	    String codprod) {
	ProductoExtVO resp = null;
	ShoppingNube comNube = new ShoppingNube(
		ShoppingNube.OPE_GET_PRODUCTO_COMPLETO);
	try {
	    resp = (ProductoExtVO) comNube.execute(comercio, codprod).get();
	} catch (InterruptedException e) {
	    Log.e(TAG, e.toString());
	} catch (ExecutionException e) {
	    Log.e(TAG, e.toString());
	}
	return resp;
    }

    public PaqueteVO ejecutarGetPaqueteCompleto(String elemRf) {
	PaqueteVO resp = null;
	ShoppingNube comNube = new ShoppingNube(
		ShoppingNube.OPE_GET_PAQUETE_COMPLETO);
	try {
	    resp = (PaqueteVO) comNube.execute(elemRf).get();
	} catch (InterruptedException e) {
	    Log.e(TAG, e.toString());
	} catch (ExecutionException e) {
	    Log.e(TAG, e.toString());
	}
	return resp;
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Producto> ejecutarGetProductosPaquete(String paquete) {
	ArrayList<Producto> resp = null;
	ShoppingNube comNube = new ShoppingNube(
		ShoppingNube.OPE_GET_PRODUCTOS_PAQUETE);
	try {
	    resp = (ArrayList<Producto>) comNube.execute(paquete).get();
	} catch (InterruptedException e) {
	    Log.e(TAG, e.toString());
	} catch (ExecutionException e) {
	    Log.e(TAG, e.toString());
	}
	return resp;
    }

    public com.fortmin.proshopping.logica.shopping.model.Mensaje ejecutarIngresoEstacionamiento(
	    String elemRf, String usuario) {
	com.fortmin.proshopping.logica.shopping.model.Mensaje resp = null;
	ShoppingNube comNube = new ShoppingNube(
		ShoppingNube.OPE_INGRESO_ESTACIONAMIENTO);
	try {
	    resp = (com.fortmin.proshopping.logica.shopping.model.Mensaje) comNube
		    .execute(elemRf, usuario).get();
	} catch (InterruptedException e) {
	    Log.e(TAG, e.toString());
	} catch (ExecutionException e) {
	    Log.e(TAG, e.toString());
	}
	return resp;
    }

    public com.fortmin.proshopping.logica.shopping.model.Mensaje ejecutarEgresoEstacionamiento(
	    String elemRf, String usuario) {
	com.fortmin.proshopping.logica.shopping.model.Mensaje resp = null;
	ShoppingNube comNube = new ShoppingNube(
		ShoppingNube.OPE_EGRESO_ESTACIONAMIENTO);
	try {
	    resp = (com.fortmin.proshopping.logica.shopping.model.Mensaje) comNube
		    .execute(elemRf, usuario).get();
	} catch (InterruptedException e) {
	    Log.e(TAG, e.toString());
	} catch (ExecutionException e) {
	    Log.e(TAG, e.toString());
	}
	return resp;
    }

    public com.fortmin.proshopping.logica.shopping.model.Mensaje ejecutarGetCalibradoBeacon(
	    String elemRf) {
	com.fortmin.proshopping.logica.shopping.model.Mensaje resp = null;
	ShoppingNube comNube = new ShoppingNube(
		ShoppingNube.OPE_GET_CALIBRADO_BEACON);
	try {
	    resp = (com.fortmin.proshopping.logica.shopping.model.Mensaje) comNube
		    .execute(elemRf).get();
	} catch (InterruptedException e) {
	    Log.e(TAG, e.toString());
	} catch (ExecutionException e) {
	    Log.e(TAG, e.toString());
	}
	return resp;
    }

    /*
     * Obtiene el puntaje del cliente y lo devuelve en Mensaje
     */
    public com.fortmin.proshopping.logica.shopping.model.Mensaje ejecutarGetPuntajeCliente(
	    String usuario) {
	com.fortmin.proshopping.logica.shopping.model.Mensaje resp = null;
	ShoppingNube comNube = new ShoppingNube(
		ShoppingNube.OPE_GET_PUNTAJE_CLIENTE);
	try {
	    resp = (com.fortmin.proshopping.logica.shopping.model.Mensaje) comNube
		    .execute(usuario).get();
	} catch (InterruptedException e) {
	    Log.e(TAG, e.toString());
	} catch (ExecutionException e) {
	    Log.e(TAG, e.toString());
	}
	return resp;
    }

    /*
     * Actualiza la posicion del usuario en referencia al elemento "elemRf" del
     * tipo detectado (NFCTAG o BEACON) Devuelve: OK - Si se pudo actualizar la
     * posicion USUARIO_INEXISTENTE - Si el usuario no existe como cliente
     * SIN_UBICACION_DEFINIDA - Si el elemento no tienen una ubicacion definida
     */
    public com.fortmin.proshopping.logica.shopping.model.Mensaje actualizarPosicion(
	    String usuario, String elemRf, String tipo) {
	com.fortmin.proshopping.logica.shopping.model.Mensaje resp = null;
	ShoppingNube comNube = new ShoppingNube(
		ShoppingNube.OPE_ACTUALIZAR_POSICION);
	try {
	    resp = (com.fortmin.proshopping.logica.shopping.model.Mensaje) comNube
		    .execute(usuario, elemRf, tipo).get();
	} catch (InterruptedException e) {
	    Log.e(TAG, e.toString());
	} catch (ExecutionException e) {
	    Log.e(TAG, e.toString());
	}
	return resp;
    }

    /*
     * Agrega el paquete de nombre "nomPaquete" al carrito del cliente "usuario"
     * Devuelve: OK - Si el paquete fue agregado con exito PAQUETE_NO_AGREGADO -
     * Si no se pudo agregar el paquete (ej: ya estaba) USUARIO_INEXISTENTE - Si
     * el usuario no existe como cliente PAQUETE_INEXISTENTE - Si no existe un
     * paquete con ese nombre
     */
    public com.fortmin.proshopping.logica.shopping.model.Mensaje agregarItemCarrito(
	    String usuario, String nomPaquete) {
	com.fortmin.proshopping.logica.shopping.model.Mensaje resp = null;
	ShoppingNube comNube = new ShoppingNube(
		ShoppingNube.OPE_AGREGAR_ITEM_CARRITO);
	try {
	    resp = (com.fortmin.proshopping.logica.shopping.model.Mensaje) comNube
		    .execute(usuario, nomPaquete).get();
	} catch (InterruptedException e) {
	    Log.e(TAG, e.toString());
	} catch (ExecutionException e) {
	    Log.e(TAG, e.toString());
	}
	return resp;
    }

    /*
     * Elimina el paquete de nombre "nomPaquete" del carrito del cliente
     * "usuario" Devuelve: OK - Si el paquete fue eliminado con exito
     * PAQUETE_NO_ELIMINADO - Si no se pudo eliminar el paquete (ej: no estaba)
     * USUARIO_INEXISTENTE - Si el usuario no existe como cliente
     */
    public com.fortmin.proshopping.logica.shopping.model.Mensaje eliminarItemCarrito(
	    String usuario, String nomPaquete) {
	com.fortmin.proshopping.logica.shopping.model.Mensaje resp = null;
	ShoppingNube comNube = new ShoppingNube(
		ShoppingNube.OPE_ELIMINAR_ITEM_CARRITO);
	try {
	    resp = (com.fortmin.proshopping.logica.shopping.model.Mensaje) comNube
		    .execute(usuario, nomPaquete).get();
	} catch (InterruptedException e) {
	    Log.e(TAG, e.toString());
	} catch (ExecutionException e) {
	    Log.e(TAG, e.toString());
	}
	return resp;
    }

    /*
     * Devuelve el carrito completo con todos sus paquetes Incluye puntaje
     * acumulado, precio acumulado y la cantidad de Items Si no encuentra el
     * usuario o en algun otro caso devuelve Null
     */
    public CarritoVO getCarritoCompleto(String usuario) {
	CarritoVO resp = null;
	ShoppingNube comNube = new ShoppingNube(
		ShoppingNube.OPE_GET_CARRITO_COMPLETO);
	try {
	    resp = (CarritoVO) comNube.execute(usuario).get();
	} catch (InterruptedException e) {
	    Log.e(TAG, e.toString());
	} catch (ExecutionException e) {
	    Log.e(TAG, e.toString());
	}
	return resp;
    }

    /*
     * Realiza el checkout del carrito de compras del cliente "usuario"
     * Devuelve: OK - Si se completo con exito el ckeckout CARRITO_VACIO - Si el
     * carrito no tiene ninguna compra USUARIO_INEXISTENTE - Si el usuario no
     * existe como cliente
     */
    public com.fortmin.proshopping.logica.shopping.model.Mensaje checkoutCarrito(
	    String usuario) {
	com.fortmin.proshopping.logica.shopping.model.Mensaje resp = null;
	ShoppingNube comNube = new ShoppingNube(
		ShoppingNube.OPE_CHECKOUT_CARRITO);
	try {
	    resp = (com.fortmin.proshopping.logica.shopping.model.Mensaje) comNube
		    .execute(usuario).get();
	} catch (InterruptedException e) {
	    Log.e(TAG, e.toString());
	} catch (ExecutionException e) {
	    Log.e(TAG, e.toString());
	}
	return resp;
    }

    @SuppressWarnings("unchecked")
    public ArrayList<ComprasVO> getCompras(String usuario) {
	ArrayList<ComprasVO> resp = null;
	ShoppingNube comNube = new ShoppingNube(ShoppingNube.OPE_GET_COMPRAS);
	try {
	    resp = (ArrayList<ComprasVO>) comNube.execute(usuario).get();
	} catch (InterruptedException e) {
	    Log.e(TAG, e.toString());
	} catch (ExecutionException e) {
	    Log.e(TAG, e.toString());
	}
	return resp;
    }

    /*
     * Agrega punto al usuario producto de una transferencia de otro usuario
     * Devuelve: OK - Si se completo con exito el agregado de puntos
     * USUARIO_INEXISTENTE - Si el usuario no existe
     */
    public com.fortmin.proshopping.logica.shopping.model.Mensaje agregarPuntos(
	    String usuario, int puntos) {
	com.fortmin.proshopping.logica.shopping.model.Mensaje resp = null;
	ShoppingNube comNube = new ShoppingNube(ShoppingNube.OPE_AGREGAR_PUNTOS);
	try {
	    resp = (com.fortmin.proshopping.logica.shopping.model.Mensaje) comNube
		    .execute(usuario, puntos).get();
	} catch (InterruptedException e) {
	    Log.e(TAG, e.toString());
	} catch (ExecutionException e) {
	    Log.e(TAG, e.toString());
	}
	return resp;
    }

    /*
     * Agrega punto al usuario producto de una transferencia de otro usuario
     * Devuelve: OK - Si se completo con exito el agregado de puntos
     * USUARIO_INEXISTENTE - Si el usuario no existe SALDO_INSUFICIENTE - Si el
     * saldo de puntos es insuficiente
     */
    public com.fortmin.proshopping.logica.shopping.model.Mensaje quitarPuntos(
	    String usuario, int puntos) {
	com.fortmin.proshopping.logica.shopping.model.Mensaje resp = null;
	ShoppingNube comNube = new ShoppingNube(ShoppingNube.OPE_QUITAR_PUNTOS);
	try {
	    resp = (com.fortmin.proshopping.logica.shopping.model.Mensaje) comNube
		    .execute(usuario, puntos).get();
	} catch (InterruptedException e) {
	    Log.e(TAG, e.toString());
	} catch (ExecutionException e) {
	    Log.e(TAG, e.toString());
	}
	return resp;
    }

    /*
     * Devuelve el tiempo de estacionamiento del cliente basado en el usuario y
     * la hora de entrada que tiene registrada
     */
    public EstacionamientoVO getTiempoEstacionamiento(String usuario) {
	EstacionamientoVO resp = null;
	ShoppingNube comNube = new ShoppingNube(
		ShoppingNube.OPE_GET_TIEMPO_ESTACIONAMIENTO);
	try {
	    resp = (EstacionamientoVO) comNube.execute(usuario).get();
	} catch (InterruptedException e) {
	    Log.e(TAG, e.toString());
	} catch (ExecutionException e) {
	    Log.e(TAG, e.toString());
	}
	return resp;
    }

    /*
     * Obtiene el estado de visibilidad del cliente Devuelve: VISIBLE - Si el
     * cliente esta configurado como visible INVISIBLE - Si el cliente no esta
     * configurado como visible USUARIO_INEXISTENTE - Si el usuario no existe
     */
    public com.fortmin.proshopping.logica.shopping.model.Mensaje getVisibilidadCliente(
	    String usuario) {
	com.fortmin.proshopping.logica.shopping.model.Mensaje resp = null;
	ShoppingNube comNube = new ShoppingNube(
		ShoppingNube.OPE_GET_VISIBILIDAD_CLIENTE);
	try {
	    resp = (com.fortmin.proshopping.logica.shopping.model.Mensaje) comNube
		    .execute(usuario).get();
	} catch (InterruptedException e) {
	    Log.e(TAG, e.toString());
	} catch (ExecutionException e) {
	    Log.e(TAG, e.toString());
	}
	return resp;
    }

    /*
     * Establece el estado de visibilidad para el cliente. Se le pasa como
     * parametro el usuario y True/False para el estado de visibilidad
     */
    public com.fortmin.proshopping.logica.shopping.model.Mensaje establecerVisibilidad(
	    String usuario, boolean estado) {
	com.fortmin.proshopping.logica.shopping.model.Mensaje resp = null;
	ShoppingNube comNube = new ShoppingNube(
		ShoppingNube.OPE_ESTABLECER_VISIBILIDAD);
	String visible;
	if (estado)
	    visible = ShoppingNube.CLIENTE_VISIBLE;
	else
	    visible = ShoppingNube.CLIENTE_INVISIBLE;
	try {
	    resp = (com.fortmin.proshopping.logica.shopping.model.Mensaje) comNube
		    .execute(usuario, visible).get();
	} catch (InterruptedException e) {
	    Log.e(TAG, e.toString());
	} catch (ExecutionException e) {
	    Log.e(TAG, e.toString());
	}
	return resp;
    }

    /*
     * Obtiene el cliente que esta cerca del elementoRf y resetea posteriormente
     * el usuario asociado.
     */
    public com.fortmin.proshopping.logica.shopping.model.Mensaje getClienteCerca(
	    String elemRf, String tipo) {
	com.fortmin.proshopping.logica.shopping.model.Mensaje resp = null;
	ShoppingNube comNube = new ShoppingNube(
		ShoppingNube.OPE_GET_CLIENTE_CERCA);
	try {
	    resp = (com.fortmin.proshopping.logica.shopping.model.Mensaje) comNube
		    .execute(elemRf, tipo).get();
	} catch (InterruptedException e) {
	    Log.e(TAG, e.toString());
	} catch (ExecutionException e) {
	    Log.e(TAG, e.toString());
	}
	return resp;
    }

    /*
     * Establece el cliente que esta cerca del elementoRf
     */
    public com.fortmin.proshopping.logica.shopping.model.Mensaje establecerClienteCerca(
	    String elemRf, String tipo, String usuario) {
	com.fortmin.proshopping.logica.shopping.model.Mensaje resp = null;
	ShoppingNube comNube = new ShoppingNube(
		ShoppingNube.OPE_ESTABLECER_CLIENTE_CERCA);
	try {
	    resp = (com.fortmin.proshopping.logica.shopping.model.Mensaje) comNube
		    .execute(elemRf, tipo, usuario).get();
	} catch (InterruptedException e) {
	    Log.e(TAG, e.toString());
	} catch (ExecutionException e) {
	    Log.e(TAG, e.toString());
	}
	return resp;
    }

    public String getOperacion() {
	return operacion;
    }

    public void setOperacion(String operacion) {
	this.operacion = operacion;
    }

}
