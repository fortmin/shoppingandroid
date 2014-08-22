package com.fortmin.proshopping;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.fortmin.proshopapi.ble.Ibeacon;
import com.fortmin.proshopping.gae.Nube;
import com.fortmin.proshopping.gae.ShoppingNube;
import com.fortmin.proshopping.logica.shopping.model.Paquete;
import com.fortmin.proshopping.persistencia.BDElementoRf;
import com.fortmin.proshopping.persistencia.DatosLocales;

public class ServicioBle extends Service {
	private BeaconRecibido beacon;
	Ibeacon ble;
	private boolean notificacion = false;
	private NotifyManager notify;
	private String uuid;
	private int rssi = 0;
	private TagRecibido tag_recibido;
	private boolean beacondescubierto = false;
	private boolean notificar = false;
	private Timer mTimer;
	private DatosLocales bd;

	@Override
	public void onCreate() {
		tag_recibido = TagRecibido.getInstance();
		beacon = BeaconRecibido.getInstance();
		bd = DatosLocales.getInstance();
		this.mTimer = new Timer();
		this.mTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {

				ble = beacon.getBeacon();
				if (ble != null) {
					uuid = ble.getProximityUuid();
					if (beacon.getDispositivoencendido()) {
						if (uuid.contains("3705")
								&& !beacon.getEstadoNotificacion()
								&& !beacondescubierto && ble.clienteCerca()) {
							beacondescubierto = true;
							tag_recibido.setNombre("BEACON001");
							Log.e("notificacion", "beacon001");
							tag_recibido.setTipo("BEACON");
							tag_recibido.setRssi(ble.darValorRssi());
							tag_recibido.setAtendido(false);

						}
						if (beacon.getEstadoNotificacion()) {
							Nube comNube = new Nube(
									ShoppingNube.OPE_GET_PAQUETE_RF);
							Paquete paquete = (Paquete) comNube
									.ejecutarGetPaqueteRf(tag_recibido
											.getNombre());
							if (paquete != null) {
								String nombre_paquete = paquete.getNombre();
								BDElementoRf tag = new BDElementoRf(
										tag_recibido.getNombre(), tag_recibido
												.getTipo(), tag_recibido
												.getRssi(), nombre_paquete);
								bd.encontreElementoRf(getBaseContext(), tag);
							}
						}
						if (!beacon.getEstadoNotificacion()
								&& beacondescubierto) {
							notify = new NotifyManager();
							notify.playNotification(getApplicationContext(),
									LecturaNfc.class, ble.getProximityUuid(),
									"Ver Paquete", R.drawable.ic_launcher);
							beacon.setEstadoNotificacion(true);
						}
					}

				}
			}
		}, 0, 1000 * 60);
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

	}

	public int onStartCommand(Intent intent, int flags, int startId) {

		// notificacion=true;

		return Service.START_STICKY;

	}

}
