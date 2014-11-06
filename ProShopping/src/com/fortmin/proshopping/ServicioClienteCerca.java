package com.fortmin.proshopping;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.fortmin.proshopping.gae.Nube;
import com.fortmin.proshopping.gae.ShoppingNube;
import com.fortmin.proshopping.logica.shopping.model.Mensaje;

// Servicio responsable de la interacción con el Ibeacon, toda la logica se simplifico al unico dispostivo
public class ServicioClienteCerca extends Service {

	private Timer mTimer;
	private int contador;
	private Usuario user;

	@Override
	public void onCreate() {
		contador = 0;
		this.mTimer = new Timer();
		this.mTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				contador += 1;
				user = Usuario.getInstance();
				Nube comNube = new Nube(ShoppingNube.OPE_GET_CLIENTE_CERCA);
				Mensaje resp = comNube.getClienteCerca("BEACON001", "BEACON");
				if (resp.getMensaje().contains("OK")) {
					user.setNombre(resp.getTexto());
					Intent tablet = new Intent(Intent.ACTION_CALL);
					tablet.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					tablet.setClass(getApplicationContext(), Tablet.class);
					contador = 0;
					startActivity(tablet);
				}
				if (contador == 2) {
					contador = 0;
					Intent tablet = new Intent(Intent.ACTION_CALL);
					tablet.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					user.setNombre(null);
					user.setNumero_imagen(user.getNumero_imagen() + 1);
					tablet.setClass(getApplicationContext(), Tablet.class);
					startActivity(tablet);
				}

			}

			// No hay else, ni bola porque no hay ningun BLE
		}, 0, 1000 * 3);
	}

	@Override
	public IBinder onBind(Intent intent) {

		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public void onDestroy() {

		super.onDestroy();
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		return Service.START_STICKY;
	}

}
