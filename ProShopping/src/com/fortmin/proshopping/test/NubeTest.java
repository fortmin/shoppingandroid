package com.fortmin.proshopping.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fortmin.proshopping.gae.Nube;
import com.fortmin.proshopping.gae.ShoppingNube;
import com.fortmin.proshopping.logica.shopping.model.Mensaje;

public class NubeTest {

	/*
	 * Se ejecuta una unica vez por clase y antes de que se construya el objeto
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// Nube nube = new Nube(SeguridadNube.OPE_LOGIN_USUARIO);
		// nube.ejecutarLogin("mundial", "test");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		// Nube nube = new Nube(SeguridadNube.OPE_LOGOFF_USUARIO);
		// nube.ejecutarLogin("mundial", "test");
	}

	/*
	 * Se ejecuta antes de cada test
	 */
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testEjecutarGetPaqueteRf() {
		fail("Not yet implemented");
	}

	@Test
	public void testEjecutarGetProductoCompleto() {
		fail("Not yet implemented");
	}

	@Test
	public void testEjecutarGetPaqueteCompleto() {
		fail("Not yet implemented");
	}

	@Test
	public void testEjecutarGetProductosPaquete() {
		fail("Not yet implemented");
	}

	@Test
	public void testEjecutarIngresoEstacionamiento() {
		fail("Not yet implemented");
	}

	@Test
	public void testEjecutarEgresoEstacionamiento() {
		fail("Not yet implemented");
	}

	@Test
	public void testEjecutarGetCalibradoBeacon() {
		fail("Not yet implemented");
	}

	@Test
	public void testEjecutarGetPuntajeCliente() {
		fail("Not yet implemented");
	}

	@Test
	public void testActualizarPosicion() {
		fail("Not yet implemented");
	}

	@Test
	public void testAgregarItemCarrito() {
		fail("Not yet implemented");
	}

	@Test
	public void testEliminarItemCarrito() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCarritoCompleto() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCompras() {
		fail("Not yet implemented");
	}

	@Test
	public void testAgregarPuntos() {
		fail("Not yet implemented");
	}

	@Test
	public void testQuitarPuntos() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTiempoEstacionamiento() {
		fail("Not yet implemented");
	}

	/*
	 * Testea el funcionamiento del servicio EstablecerVisibilidad y del
	 * servicio GetVisibilidadCliente
	 */
	@Test
	public void testVisibilidadCliente() {
		Nube nube = new Nube(ShoppingNube.OPE_ESTABLECER_VISIBILIDAD);
		nube.establecerVisibilidad("test", true);
		nube.setOperacion(ShoppingNube.OPE_GET_VISIBILIDAD_CLIENTE);
		Mensaje mens = nube.getVisibilidadCliente("test");
		assertNotNull("getVisibilidadCliente no devolvio mensaje", mens);
		if (mens != null) {
			assertEquals("Estado de visibilidad = VISIBLE", "VISIBLE",
					mens.getMensaje());
		}
		nube.setOperacion(ShoppingNube.OPE_ESTABLECER_VISIBILIDAD);
		nube.establecerVisibilidad("test", false);
		nube.setOperacion(ShoppingNube.OPE_GET_VISIBILIDAD_CLIENTE);
		mens = nube.getVisibilidadCliente("test");
		assertNotNull("getVisibilidadCliente no devolvio mensaje", mens);
		if (mens != null) {
			assertEquals("Estado de visibilidad = INVISIBLE", "INVISIBLE",
					mens.getMensaje());
		}
	}

}
