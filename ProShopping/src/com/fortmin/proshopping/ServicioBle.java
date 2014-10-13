package com.fortmin.proshopping;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import com.fortmin.proshopapi.ble.Ibeacon;
import com.fortmin.proshopping.gae.Nube;
import com.fortmin.proshopping.gae.ShoppingNube;
import com.fortmin.proshopping.logica.shopping.model.Paquete;
import com.fortmin.proshopping.persistencia.BDElementoRf;
import com.fortmin.proshopping.persistencia.DatosLocales;

public class ServicioBle extends Service {

	private BeaconRecibido beacon_recibido;
	private Ibeacon ble;
	private NotifyManager notify;
	private String uuid;
	private TagRecibido tag_recibido;
	private Timer mTimer;
	private DatosLocales bd;
	private Usuario usuario;

	@Override
	public void onCreate() {
		beacon_recibido = BeaconRecibido.getInstance();
		if (beacon_recibido != null) {
			bd = DatosLocales.getInstance();
			usuario = Usuario.getInstance();
			tag_recibido = TagRecibido.getInstance();
		}

		this.mTimer = new Timer();
		this.mTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if (!isForeground("com.fortmin.proshopping")) {
					Log.e("Service", "no in foregroind");
					Intent dialogIntent = new Intent(getBaseContext(),
							LecturaRF.class);
					dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					getApplication().startActivity(dialogIntent);

				}
				beacon_recibido = BeaconRecibido.getInstance();
				if (beacon_recibido != null) {

					ble = beacon_recibido.getBeacon(); // Exito, obtuve un
														// beacon
					// para jugar
					if (ble != null) {
						uuid = ble.getProximityUuid();
						if (ble.clienteCerca() && uuid != null) { // Si no esta
																	// cerca ni
																	// me
																	// molesto
							if (!beacon_recibido.getEstadoNotificacion()) { // No
																			// lo
								// notifique
								// nunca
								if (uuid.contains("3705")) { // Esto solo va por
																// que
									// tenemos 1 beacon
									// nada mas
									Log.e("notificacion", "beacon001");
									tag_recibido.setNombre("BEACON001");
									tag_recibido.setTipo("BEACON");
									tag_recibido.setRssi(ble.darValorRssi());
									tag_recibido.setAtendido(false);
									beacon_recibido.setEstadoNotificacion(true);
									tag_recibido.setMostrado(false);
									SharedPreferences prefs = getSharedPreferences(
											"configuracion", MODE_PRIVATE);
									String nombre = prefs.getString("Usuario",
											"no existe");
									if (!nombre.equals("no existe")) {
										usuario.setNombre(nombre);

									}

									Nube comNube = new Nube(
											ShoppingNube.OPE_ESTABLECER_CLIENTE_CERCA);
									comNube.establecerClienteCerca(
											tag_recibido.getNombre(),
											tag_recibido.getTipo(),
											usuario.getNombre());

									comNube.setOperacion(ShoppingNube.OPE_GET_PAQUETE_RF);
									Paquete paquete = (Paquete) comNube
											.ejecutarGetPaqueteRf(tag_recibido
													.getNombre());
									if (paquete != null) { // Baje la
															// informacion,
										// puedo notificar
										String nombre_paquete = paquete
												.getNombre();
										BDElementoRf tag = new BDElementoRf(
												tag_recibido.getNombre(),
												tag_recibido.getTipo(),
												tag_recibido.getRssi(),
												nombre_paquete);
										bd.encontreElementoRf(getBaseContext(),
												tag);
										notify = new NotifyManager();
										notify.playNotification(
												getApplicationContext(),
												LecturaRF.class,
												ble.getProximityUuid(),
												"Ver Paquete",
												R.drawable.ic_launcher);

									} else {
										// TODO, Evaluar si le pasamos un
										// mensaje de
										// que no tiene Internet o nada
										// Observar que solo notifique en el if
										// si
										// logre bajar la informacion del
										// paquete
										// Sino, es mejor que no se entere y que
										// no
										// le quede inconsistente
									}
								}
								// No hay else, ni bola si no es el 3705
							} else { // Solo actualizo el RSSI y solo si es el
										// 3705
								if (uuid.contains("3705")) {
									tag_recibido.setRssi(ble.darValorRssi());
								}
								// No hay else, ni bola si no es el 3705
							}

							// No hay else, ni bola si no esta cerca
						}
					}
				}
			}

			// No hay else, ni bola porque no hay ningun BLE
		}, 0, 1000 * 10);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		stopForeground(true);
		this.mTimer.cancel();
		this.mTimer.purge();
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		return Service.START_STICKY;
	}

	public boolean isForeground(String myPackage) {
		ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> runningTaskInfo = manager
				.getRunningTasks(1);

		ComponentName componentInfo = runningTaskInfo.get(0).topActivity;
		if (componentInfo.getPackageName().equals(myPackage))
			return true;
		return false;
	}

}
