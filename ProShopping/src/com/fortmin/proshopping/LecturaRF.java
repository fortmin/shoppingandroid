package com.fortmin.proshopping;

import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
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
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
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
	private DatosLocales datos;
	private com.fortmin.proshopapi.ble.EscucharIbeacons escuchar_ibeacons;
	private BeaconRecibido beacon_recibido;
	private Timer mTimer;
	private boolean scanning = false;
	private String nombre_paquete;
	private ListadoCompras miscompras;
	private ProShopMgr apiNfc;
	private IngresoAmigo amigo;
	private ImageButton btn_puntos;
	private ImageButton btn_paquetes;
	private ImageButton btn_micarrito;
	private ImageButton btn_miscompras;
	private ImageButton btn_pasarpuntos;
	private ImageButton btn_parking;
	private SharedPreferences prefs;
	private String nombre;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		datos = DatosLocales.getInstance();
		new TipoTag();
		super.onCreate(savedInstanceState);
		if (!verificaConexion(this)) {
			mostrarMensaje("Para Usar la APP necesita estar conectado a internet");
			this.finish();
		}

		apiNfc = new ProShopMgr(getApplicationContext());
		tag_recibido = TagRecibido.getInstance();
		setContentView(R.layout.activity_lectura_rf);
		btn_puntos = (ImageButton) findViewById(R.id.btnPuntos);
		btn_paquetes = (ImageButton) findViewById(R.id.btnPaquetes);
		btn_micarrito = (ImageButton) findViewById(R.id.btnMiCarrito);
		btn_miscompras = (ImageButton) findViewById(R.id.btnMisCompras);
		btn_pasarpuntos = (ImageButton) findViewById(R.id.btnPasarPuntos);
		btn_parking = (ImageButton) findViewById(R.id.btnParking);
		// me fijo si el usuario ya se habia logoneado en la app
		prefs = getSharedPreferences("configuracion", MODE_PRIVATE);

		nombre = prefs.getString("Usuario", "no existe");
		if (!nombre.equals("no existe")) {
			nombre_usuario = Usuario.getInstance();
			nombre_usuario.setNombre(nombre);
		} else {
			// mostrarDialogo("Debe estar logeado", "Sistema");
			Intent inicio = new Intent(this, Inicio.class);
			startActivity(inicio);

		}

		beacon_recibido = BeaconRecibido.getInstance();// clase que me da el
														// valor real del beacon
		btn_puntos.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				/*
				 * boton que me dice de cuantos puntos acumulados dispongo
				 */
				startAnimation(btn_puntos);
				Nube nube = new Nube(ShoppingNube.OPE_GET_PUNTAJE_CLIENTE);
				Mensaje resp = nube.ejecutarGetPuntajeCliente(nombre_usuario
						.getNombre());
				mostrarDialogo("Dispone de  " + resp.getValor().toString()
						+ " puntos", "Puntos");

			}
		});
		btn_paquetes.setOnClickListener(new View.OnClickListener() {
			/*
			 * me muestra los paquetes que fueron leidos meduiante tags NFC o
			 * mediante las notificaciones del Ibeacon
			 */
			public void onClick(View view) {
				startAnimation(btn_paquetes);
				SharedPreferences pref = getSharedPreferences("bd",
						MODE_PRIVATE);
				String base_datos = pref.getString("haydatos", "no");
				boolean hay_datos = base_datos.equals("no");
				if (!hay_datos) {
					datos.setHaydatos(true);
				} else
					datos.setHaydatos(false);
				if (datos.getHaydatos()) {
					/*
					 * cuando los usiarios matan la aplicacion, la app pierde la
					 * clase singleton que permite recuperar la base en solo
					 * lectura
					 */
					datos.obtenerBaseLectura(getBaseContext());
					verPaquetes();
					datos.cerrarBase();
				} else {
					mostrarDialogo("No ha visto paquetes aun", "Paquetes");
				}

			}
		});
		btn_micarrito.setOnClickListener(new View.OnClickListener() {
			/*
			 * desde aqui se ven los paquetes que se han pasado al carrito puede
			 * efectuarse la compra al hacer el checkout o eliminarse el paquete
			 * del carrito
			 */
			public void onClick(View view) {
				startAnimation(btn_micarrito);
				hacerCompra();
			}
		});

		btn_miscompras.setOnClickListener(new View.OnClickListener() {
			/*
			 * muestra las comprar realizadas y su estado que puede ser
			 * entregado o pendiente de entrega
			 */
			public void onClick(View view) {
				startAnimation(btn_miscompras);
				verMisCompras();
			}
		});
		btn_pasarpuntos.setOnClickListener(new View.OnClickListener() {
			/*
			 * desde esta nueva actividad puedo intercambiar puntos con otro
			 * cliente
			 */
			public void onClick(View view) {
				startAnimation(btn_pasarpuntos);
				pasarPuntos();
			}
		});
		btn_parking.setOnClickListener(new View.OnClickListener() {
			/*
			 * voy contra la nube y veo el tiempo que me queda disponible en el
			 * parking
			 */
			public void onClick(View view) {
				startAnimation(btn_parking);
				verTiempoEstacionamiento(nombre_usuario.getNombre());

			}

		});
		amigo = IngresoAmigo.getInstance();
		if (amigo.getIngreso()) {
			mostrarAmigo();
		}
		// escucho tag propietario es decir el que tiene grabado
		// "com.fortmin.proshopping" ademas de la info
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
			prefs = getSharedPreferences("configuracion", MODE_PRIVATE);

			nombre = prefs.getString("Usuario", "no existe");
			if (!nombre.equals("no existe")) {
				nombre_usuario = Usuario.getInstance();
				nombre_usuario.setNombre(nombre);
				AnalizarId(apiNfc.getNFC().nombreTagRecibido(getIntent()));
			} else {
				Intent inicio = new Intent(this, Inicio.class);
				startActivity(inicio);
				this.finish();

			}

		}

		escuchar_ibeacons = new com.fortmin.proshopapi.ble.EscucharIbeacons(
				this);

		/*
		 * hace un loop cada 5 segundos, crado para evitar disparar el servicio
		 * antes de que el movil este preparado y scaneando los beacon
		 */
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
							.getEsta_recibiendo()); // beacons.setEsta_recibiendo(false);
					beacon_recibido.fijarDistanca();

				}

			}
		}, 0, 1000 * 5);
		/*
		 * solo se muestra el beacon una vez, para evitar at izar al cliente
		 */
		if (!tag_recibido.fueMostrado()) {
			tag_recibido.setMostrado(true);
			persistirBeacon();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.lectura_nfc, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		/*
		 * controlador del menu con sus diferentes opciones, esta vinculado a
		 * menu.xml
		 */
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
		case R.id.ribeacon:
			encenderServicio();
			return true;
		case R.id.nribeacon:
			stopServicio();
			return true;
		case R.id.borrardatos:
			if (datos.getHaydatos()) {
				datos.limpiarBase();
				SharedPreferences prefs = getSharedPreferences("bd",
						Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = prefs.edit();
				editor.putString("haydatos", "no");
				editor.commit();
				datos.setHaydatos(false);
			} else
				mostrarDialogo("No hay Paquetes", "Sistema");
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void verPaquete(String nombrepaquete) {
		// se muestra el paquete
		this.setVisible(false);
		if (datos.getHaydatos()) {
			SharedPreferences prefs = getSharedPreferences("bd",
					Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = prefs.edit();
			editor.putString("haydatos", "TRUE");
			editor.commit();
		}
		paquete = new Intent(this, ProductosPaquete.class);
		paquete.putExtra("nombrePaquete", nombrepaquete);
		startActivity(paquete);
		this.finish();

	}

	public void entradaEstacionamiento(String acceso, String nom_usuario,
			String id) {

		if (acceso.equals("entrada")) {

			Nube nube = new Nube(ShoppingNube.OPE_INGRESO_ESTACIONAMIENTO);
			Mensaje resp = nube.ejecutarIngresoEstacionamiento(id, nom_usuario);
			if (resp != null) {
				String mensaje = resp.getMensaje();
				if (mensaje.equals("OK"))
					mostrarDialogo("Bienvenido " + nombre_usuario.getNombre(),
							"Parking");

			}

		} else if (acceso.equals("salida")) {
			Nube nube = new Nube(ShoppingNube.OPE_INGRESO_ESTACIONAMIENTO);
			Mensaje resp = nube.ejecutarEgresoEstacionamiento(id, nom_usuario);
			if (resp != null) {
				String mensaje = resp.getMensaje();
				if (!mensaje.equals("PLAZO_VENCIDO") || mensaje.equals("OK")) {
					mostrarDialogo(
							"Gracias por su visita "
									+ nombre_usuario.getNombre(), "Parking");
					this.finish();

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
		/*
		 * cuando se graba el tag debe estar precedido por ENT en caso de ser
		 * entrada al estacionamiento con SAL si es salida del estacionamiento,
		 * y con PRU si es una tag de prueba, en este unico caso se muestra un
		 * dialogo y es solo a los efectos de mostrar que el tag es propietario
		 */
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
		} else if (id.contains("PRU")) {
			mostrarDialogo("Tag contiene " + id.substring(3), "tag de prueba");
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
			// se guardo el tag en la base de datos
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
		/*
		 * cuando la app es pausada debemos saber si hay datos en la base porque
		 * las clases singleton pueden ser borradas de memoria
		 */

		if (datos.getHaydatos()) {
			SharedPreferences prefs = getSharedPreferences("bd",
					Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = prefs.edit();
			editor.putString("haydatos", "TRUE");
			editor.commit();
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		boolean inicializar = true;
		// Se chequea si el dispositivo es compatible con ble
		ProShopMgr mgr = new ProShopMgr(getApplicationContext());
		if (mgr.getBLE(this).bleSoportado(this) == false) {
			mostrarDialogo("Su dispositivo no admite lectura de ibeacon",
					"Beacon");
			inicializar = false;

		} else if (mgr.bluetoothHabilitado(this) == false) {
			// enciendo automaticamente el bluetooth
			BluetoothAdapter mBluetoothAdapter = BluetoothAdapter
					.getDefaultAdapter();
			mBluetoothAdapter.enable();
		}

		// inicializacion del ble y comienzo del scanning
		if (inicializar) {
			escuchar_ibeacons.initialize();
			escuchar_ibeacons.startScanning();
			scanning = true;

		}

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
			Log.e("voy a ver el paquete", nombre_paquete);
			verPaquete(nombre_paquete);

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

	public void stopServicio() {

		stopService(new Intent(LecturaRF.this, ServicioBle.class));

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
			mostrarDialogo("No tiene paquetes en su canasto", "Carrito");
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
		if (!miscompras.tieneCompras())
			miscompras.cargarCompras();
		if (miscompras.tieneCompras()) {
			Intent vercompras = new Intent(this, ListadoMisCompras.class);
			startActivity(vercompras);
		} else {
			mostrarDialogo("no tiene compras registradas", "Compras");
		}

	}

	public void pasarPuntos() {
		Intent pasarpuntos = new Intent(this, TransferenciaPuntos.class);
		startActivity(pasarpuntos);
	}

	private void verTiempoEstacionamiento(String nombre) {

		Nube parking = new Nube(ShoppingNube.OPE_GET_TIEMPO_ESTACIONAMIENTO);
		EstacionamientoVO tiempo_parking = parking
				.getTiempoEstacionamiento(nombre);
		if (!tiempo_parking.getPresente())
			mostrarDialogo("No ingreso al parking", "Parking");
		else {
			/*
			 * hay que convertir la hora por el formatoq ue maneja google
			 */
			DateTime hora1 = tiempo_parking.getTopSalidaGratis();
			String hora = hora1.toString();
			String hora2 = hora.substring(11, 16);
			mostrarDialogo("Hora de salida sin cargo " + convertirHora(hora2),
					"Parking");
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

	public void mostrarDialogo(String mensaje, String title) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(mensaje)
				.setTitle(title)
				.setCancelable(false)
				.setNeutralButton("Aceptar",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

								dialog.cancel();

							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}

	public void startAnimation(ImageView ivDH) {

		Animation rotateAnim = new RotateAnimation(0, 360);
		rotateAnim.setDuration(5000);
		rotateAnim.setRepeatCount(1);
		rotateAnim.setInterpolator(new AccelerateInterpolator());
		rotateAnim.setRepeatMode(Animation.REVERSE);

		ivDH.startAnimation(rotateAnim);
	}

}
