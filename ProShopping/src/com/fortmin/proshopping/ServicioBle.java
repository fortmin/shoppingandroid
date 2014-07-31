package com.fortmin.proshopping;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.fortmin.proshopapi.ble.Ibeacon;

public class ServicioBle extends Service {
	private BeaconRecibido beacon;
	Ibeacon ble;
	private boolean notificacion = false;
	private NotifyManager notify;
	private String uuid;
	private tagRecibido tag_recibido;
	private boolean beacondescubierto = false;
	private boolean notificar = false;
	private Timer mTimer;

	@Override
	public void onCreate() {
		tag_recibido = tagRecibido.getInstance();
		beacon = BeaconRecibido.getInstance();
		this.mTimer = new Timer();
		this.mTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				ble = beacon.getBeacon();
				if (ble != null) {
					uuid = ble.getProximityUuid();
					if (uuid.contains("3705") && !beacondescubierto) {
						tag_recibido.setNombre("BEACON001");
						Log.e("notificacion", "beacon001");
						tag_recibido.setTipo("BEACON");
						tag_recibido.setRssi(ble.darValorRssi());
						tag_recibido.setAtendido(false);
						beacondescubierto = true;

					}
				}
				if (!notificar && beacondescubierto) {
					notify = new NotifyManager();
					notify.playNotification(getApplicationContext(),
							LecturaNfc.class, ble.getProximityUuid(),
							"Ver Paquete", R.drawable.ic_launcher);
					notificar = true;
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
