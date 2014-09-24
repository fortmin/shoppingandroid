package com.fortmin.proshopping;

import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.fortmin.proshopapi.ProShopMgr;
import com.fortmin.proshopping.gae.Nube;
import com.fortmin.proshopping.gae.ShoppingNube;
import com.fortmin.proshopping.logica.shopping.model.CarritoVO;
import com.fortmin.proshopping.logica.shopping.model.EstacionamientoVO;
import com.fortmin.proshopping.logica.shopping.model.Mensaje;
import com.fortmin.proshopping.logica.shopping.model.Paquete;
import com.fortmin.proshopping.logica.shopping.model.PaqueteVO;
import com.fortmin.proshopping.persistencia.BDElementoRf;
import com.fortmin.proshopping.persistencia.DatosLocales;
import com.google.api.client.util.DateTime;

public class LecturaRF extends Activity {

	private Intent servicio, paquete;
	private Usuario nombre_usuario;
	private TagRecibido tag_recibido;
	private DatosLocales datos = DatosLocales.getInstance();
	private com.fortmin.proshopapi.ble.EscucharIbeacons escuchar_ibeacons;
	private BeaconRecibido beacon_recibido;
	private Timer mTimer;
	private boolean servicioiniciado;
	private boolean scanning = false;
	private String nombre_paquete;
	private ListadoCompras miscompras;
	private ProShopMgr apiNfc;
	private IngresoAmigo amigo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		new TipoTag();
		super.onCreate(savedInstanceState);
		if (!verificaConexion(this)) {
			mostrarMensaje("Para Usar la APP necesita estar conectado a internet");
			this.finish();
		}
		apiNfc = new ProShopMgr(getApplicationContext());
		tag_recibido = TagRecibido.getInstance();
		setContentView(R.layout.activity_lectura_rf);
		ImageButton btn_puntos = (ImageButton) findViewById(R.id.btnPuntos);
		ImageButton btn_paquetes = (ImageButton) findViewById(R.id.btnPaquetes);
		ImageButton btn_micarrito = (ImageButton) findViewById(R.id.btnMiCarrito);
		ImageButton btn_miscompras = (ImageButton) findViewById(R.id.btnMisCompras);
		ImageButton btn_pasarpuntos = (ImageButton) findViewById(R.id.btnPasarPuntos);
		ImageButton btn_parking = (ImageButton) findViewById(R.id.btnParking);

		nombre_usuario = Usuario.getInstance();// para que el nombre de usuario
												// pueda ser utilizado en
												// cualquier activity
		beacon_recibido = BeaconRecibido.getInstance();// esta clase la
														// actualiza
														// ActualizarBeacons,
														// muestra el ibeacons
														// instantaneo
		btn_puntos.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Nube nube = new Nube(ShoppingNube.OPE_GET_PUNTAJE_CLIENTE);
				Mensaje resp = nube.ejecutarGetPuntajeCliente(nombre_usuario
						.getNombre());
				mostrarMensaje("Usted dispone de : "
						+ resp.getValor().toString() + " puntos");

			}
		});
		btn_paquetes.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if (datos.getHaydatos()) // si aun no se ha crado la base de
											// datos
					verPaquetes();
				else
					mostrarMensaje("No ha visto paquetes aun");
			}
		});
		btn_micarrito.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				hacerCompra();
			}
		});

		btn_miscompras.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {

				verMisCompras();
			}
		});
		btn_pasarpuntos.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {

				pasarPuntos();
			}
		});
		btn_parking.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				verTiempoEstacionamiento(nombre_usuario.getNombre());

			}

		});
		amigo = IngresoAmigo.getInstance();
		if (amigo.getIngreso()) {
			mostrarAmigo();
		}
		// escucho tag propietario
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
			AnalizarId(apiNfc.getNFC().nombreTagRecibido(getIntent()));
		}

		escuchar_ibeacons = new com.fortmin.proshopapi.ble.EscucharIbeacons(
				this);

		// hace un loop cada 60 segundos
		this.mTimer = new Timer();
		this.mTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Log.e("run", "seteando beacon");
				// si ya fue inicializado el hardware y ya esta escuchando
				if (scanning && escuchar_ibeacons.getEsta_recibiendo()) {
					beacon_recibido.setBeacon(escuchar_ibeacons.darIbeacon());
					beacon_recibido.setBeacon_leido(true);
					beacon_recibido.setDispositivoencendido(escuchar_ibeacons
							.getEsta_recibiendo());
					// beacons.setEsta_recibiendo(false);
					beacon_recibido.fijarDistanca();

				}
				SharedPreferences prefs = getSharedPreferences(
						"estadoservicio", MODE_PRIVATE);
				String estado_servicio = prefs.getString("conectado", "no");
				servicioiniciado = estado_servicio.equals("ok");
				if (!servicioiniciado && beacon_recibido.getBeacon_leido()) {
					encenderServicio();
				}

			}
		}, 0, 1000 * 60);

		if (!tag_recibido.fueMostrado()) {
			tag_recibido.setMostrado(true);
			persistirBeacon();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.lectura_nfc, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		Nube comNube = new Nube(ShoppingNube.OPE_ESTABLECER_VISIBILIDAD);
		switch (item.getItemId()) {
		case R.id.visible:

			comNube.establecerVisibilidad(nombre_usuario.getNombre(), true);
			GCMIntentService.register(getApplicationContext());
			return true;
		case R.id.invisible:
			comNube.establecerVisibilidad(nombre_usuario.getNombre(), false);
			GCMIntentService.unregister(getApplicationContext());
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void verPaquete(String nombrepaquete) {
		// llamo a mostrarPaquete
		this.setVisible(false);
		paquete = new Intent(this, ProductosPaquete.class);
		paquete.putExtra("nombrePaquete", nombrepaquete);
		startActivity(paquete);
		this.finish();

	}

	public void entradaEstacionamiento(String acceso, String nom_usuario,
			String id) {
		// llamo a mostrarPaquete
		if (acceso.equals("entrada")) {// entrada
			Nube nube = new Nube(ShoppingNube.OPE_INGRESO_ESTACIONAMIENTO);
			Mensaje resp = nube.ejecutarIngresoEstacionamiento(id, nom_usuario);
			if (resp != null) {
				String mensaje = resp.getMensaje();
				if (mensaje.equals("OK"))
					mostrarMensaje("Acceso Permitido");
			}

		} else if (acceso.equals("salida")) { // salida
			Nube nube = new Nube(ShoppingNube.OPE_INGRESO_ESTACIONAMIENTO);
			Mensaje resp = nube.ejecutarEgresoEstacionamiento(id, nom_usuario);
			if (resp != null) {
				String mensaje = resp.getMensaje();
				if (!mensaje.equals("PLAZO_VENCIDO") || mensaje.equals("OK")) {
					mostrarMensaje("Gracias por su visita");
					try {
						this.finalize();
					} catch (Throwable e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}

	}

	public void mostrarMensaje(String mensaje) {
		Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG)
				.show();
	}

	/*
	 * Este metodo esta vinculado a los tag NFC, distingue entre tag de paquetes
	 * y tag de estacionamiento
	 */
	public void AnalizarId(String id) {

		nombre_paquete = null;
		Log.e("ID", id);
		Log.e("NomUsuario", nombre_usuario.getNombre());
		String nfc = id.substring(3);
		Log.e("ID", nfc);
		if (id.contains("ENT")) {
			Log.e("estacionamiento", nombre_usuario.getNombre());
			entradaEstacionamiento("entrada", nombre_usuario.getNombre(), nfc);

		} else if (id.contains("SAL")) {
			entradaEstacionamiento("salida", nombre_usuario.getNombre(), nfc);
		} else {
			// si es nfc de comercio o smartposter

			Nube comNube = new Nube(ShoppingNube.OPE_GET_PAQUETE_RF);
			Paquete paquete = (Paquete) comNube.ejecutarGetPaqueteRf(id);

			// Si pude obtener el paquete obtengo el nombre para guardarlo en
			// la base de datos
			if (paquete != null) {
				nombre_paquete = paquete.getNombre();
			}
			Log.e("Nombre Paquete", nombre_paquete);
			BDElementoRf tag = new BDElementoRf(id, TipoTag.tipoNFC, 0,
					nombre_paquete);
			comNube.actualizarPosicion(nombre_usuario.getNombre(), id,
					TipoTag.tipoNFC);
			tag_recibido.setTipo(TipoTag.tipoNFC);
			DatosLocales datos = DatosLocales.getInstance();
			datos.encontreElementoRf(this, tag);
			verPaquete(nombre_paquete);
		}
	}

	protected void onPause() {
		super.onPause();
		this.mTimer.cancel();
		this.mTimer.purge();

	}

	@Override
	protected void onResume() {
		super.onResume();
		boolean inicializar = true;
		// check for Bluetooth enabled on each resume
		ProShopMgr mgr = new ProShopMgr(getApplicationContext());
		if (mgr.getBLE(this).bleSoportado(this) == false) {
			mostrarMensaje("Su dispositivo no admite lectura de ibeacon");
			inicializar = false;

		} else if (mgr.bluetoothHabilitado(this) == false) {
			// BT not enabled. Request to turn it on. User needs to restart app
			// once it's turned on.
			/*
			 * Intent enableBtIntent = new Intent(
			 * BluetoothAdapter.ACTION_REQUEST_ENABLE);
			 * startActivity(enableBtIntent); finish();
			 */
			// enciendo automaticamente el bluetooth
			BluetoothAdapter mBluetoothAdapter = BluetoothAdapter
					.getDefaultAdapter();
			mBluetoothAdapter.enable();
		}

		// inicializacion del ble
		if (inicializar) {
			escuchar_ibeacons.initialize();
			escuchar_ibeacons.startScanning();
			scanning = true;
		}
		// ListaIbeacon Ibeacons=beacons.getIbeacons();
		// ArrayList<Ibeacon> dispositivos=Ibeacons.IbeaconsEncendidos();
		// beacon=dispositivos.get(0);

	}

	public void persistirBeacon() {
		Nube comNube = new Nube(ShoppingNube.OPE_GET_PAQUETE_RF);
		Paquete paquete = (Paquete) comNube.ejecutarGetPaqueteRf(tag_recibido
				.getNombre());
		if (paquete != null) {
			nombre_paquete = paquete.getNombre();
			BDElementoRf tag = new BDElementoRf(tag_recibido.getNombre(),
					tag_recibido.getTipo(), tag_recibido.getRssi(),
					paquete.getNombre());
			DatosLocales datos = DatosLocales.getInstance();
			comNube.actualizarPosicion(nombre_usuario.getNombre(),
					tag_recibido.getNombre(), TipoTag.tipoBEACON);
			Log.e("grabacion beacon", "antes de ir a la tabla");
			String resultado = datos.encontreElementoRf(this, tag);
			Log.e("grabacion beacon", resultado);
			// beacons.stopScanning();
			Log.e("voy a ver el paquete", nombre_paquete);
			verPaquete(nombre_paquete);
			// beacons.startScanning();

		}

	}

	public void encenderServicio() {
		// se guarda el estado del servicio en una preferencia para cuando se
		// vuelva a correr la app
		SharedPreferences prefs = getSharedPreferences("estadoservicio",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("conectado", "ok");
		editor.commit();
		servicio = new Intent(this, ServicioBle.class);
		startService(servicio);

	}

	public void verPaquetes() {
		Intent mostrar_paquetes = new Intent(this, MostrarPaquetes.class);
		startActivity(mostrar_paquetes);

	}

	public void hacerCompra() {
		Nube carrito = new Nube(ShoppingNube.OPE_GET_CARRITO_COMPLETO);
		Usuario user = com.fortmin.proshopping.Usuario.getInstance();
		CarritoVO micarrito = carrito.getCarritoCompleto(user.getNombre());
		if (micarrito.getCantItems() == 0) {
			mostrarMensaje("No tiene paquetes en su canasto");
		} else {
			CanastaCompras canasta = CanastaCompras.getInstance();
			canasta.anularCanasta();
			canasta.setPrecio(micarrito.getPrecioCarrito());
			canasta.setPuntos(Integer.valueOf(micarrito.getPuntosCarrito()));
			List<PaqueteVO> paquetes = micarrito.getPaquetes();
			Iterator<PaqueteVO> ipaquetes = paquetes.iterator();
			while (ipaquetes.hasNext()) {
				PaqueteVO paqueteVO = ipaquetes.next();
				canasta.agregarPaqueteCarrito(paqueteVO);
			}
			Intent hacer_compra = new Intent(this, CanastodeCompras.class);
			startActivity(hacer_compra);
		}
	}

	public void verMisCompras() {
		miscompras = ListadoCompras.getInstance();
		miscompras.cargarCompras();
		if (miscompras.tieneCompras()) {
			Intent vercompras = new Intent(this, ListadoMisCompras.class);
			startActivity(vercompras);
		} else {
			mostrarMensaje("no tiene compras registradas");
		}

	}

	public void pasarPuntos() {
		Intent pasarpuntos = new Intent(this, TransferenciaPuntos.class);
		startActivity(pasarpuntos);
	}

	private void verTiempoEstacionamiento(String nombre) {
		// TODO Auto-generated method stub
		Nube parking = new Nube(ShoppingNube.OPE_GET_TIEMPO_ESTACIONAMIENTO);
		EstacionamientoVO tiempo_parking = parking
				.getTiempoEstacionamiento(nombre);
		if (!tiempo_parking.getPresente())
			mostrarMensaje("Usted no esta en el estacionamiento");
		else {
			DateTime hora1 = tiempo_parking.getTopSalidaGratis();
			String hora = hora1.toString();
			String hora2 = hora.substring(11, 16);

			// long hora2 = tiempo_parking.getActual().getValue();
			// long tiempo_restante_segundos = (hora2 - hora1) / 1000;
			// long tiempo_restante_minutos = tiempo_restante_segundos / 60;
			mostrarMensaje("Hora de salida sin cargo " + convertirHora(hora2));
		}
	}

	private String convertirHora(String hora_entrada) {
		String hora = hora_entrada.substring(0, 2);
		int hh = Integer.parseInt(hora);
		int hhnueva;
		int resta_nueva = hh - 3;
		switch (resta_nueva) {
		case -1:
			hhnueva = 23;
			break;
		case -2:
			hhnueva = 22;
			break;
		default:
			hhnueva = resta_nueva;
			break;
		}
		hora = String.valueOf(hhnueva);
		hora = hora + hora_entrada.substring(2, 5);
		return hora;
	}

	public void mostrarAmigo() {
		Intent mostrar_amigo = new Intent(this, PresenciaAmigo.class);
		amigo.setIngreso(false);
		startActivity(mostrar_amigo);
	}

	// para chequear si tiene conexion a internet
	public static boolean verificaConexion(Context ctx) {
		boolean bConectado = false;
		ConnectivityManager connec = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		// No sólo wifi, también GPRS
		NetworkInfo[] redes = connec.getAllNetworkInfo();
		for (int i = 0; i < 2; i++) {
			// true si hay conexión
			if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
				bConectado = true;
			}
		}
		return bConectado;
	}

}
