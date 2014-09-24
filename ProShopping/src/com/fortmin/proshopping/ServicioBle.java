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

    private BeaconRecibido beacon_recibido;
    Ibeacon ble;
    private NotifyManager notify;
    private String uuid;
    private TagRecibido tag_recibido;
    private Timer mTimer;
    private DatosLocales bd;
    private Usuario usuario;

    @Override
    public void onCreate() {
	tag_recibido = TagRecibido.getInstance();
	beacon_recibido = BeaconRecibido.getInstance();
	bd = DatosLocales.getInstance();
	usuario = Usuario.getInstance();

	this.mTimer = new Timer();
	this.mTimer.scheduleAtFixedRate(new TimerTask() {
	    @Override
	    public void run() {
		ble = beacon_recibido.getBeacon(); // Exito, obtuve un beacon
						   // para jugar

		uuid = ble.getProximityUuid();
		if (ble.clienteCerca()) { // Si no esta cerca ni me molesto
		    if (!beacon_recibido.getEstadoNotificacion()) { // No lo
			// notifique
			// nunca
			if (uuid.contains("3705")) { // Esto solo va por que
						     // tenemos 1 beacon
						     // nada mas
			    Log.e("notificacion", "beacon001");
			    tag_recibido.setNombre("BEACON001");
			    tag_recibido.setTipo("BEACON");
			    tag_recibido.setRssi(ble.darValorRssi());
			    tag_recibido.setAtendido(false);
			    beacon_recibido.setEstadoNotificacion(true);
			    tag_recibido.setMostrado(false);

			    Nube comNube = new Nube(
				    ShoppingNube.OPE_ESTABLECER_CLIENTE_CERCA);
			    comNube.establecerClienteCerca(
				    tag_recibido.getNombre(),
				    tag_recibido.getTipo(), usuario.getNombre());

			    comNube.setOperacion(ShoppingNube.OPE_GET_PAQUETE_RF);
			    Paquete paquete = (Paquete) comNube
				    .ejecutarGetPaqueteRf(tag_recibido
					    .getNombre());
			    if (paquete != null) { // Baje la informacion,
						   // puedo notificar
				String nombre_paquete = paquete.getNombre();
				BDElementoRf tag = new BDElementoRf(
					tag_recibido.getNombre(), tag_recibido
						.getTipo(), tag_recibido
						.getRssi(), nombre_paquete);
				bd.encontreElementoRf(getBaseContext(), tag);
				notify = new NotifyManager();
				notify.playNotification(
					getApplicationContext(),
					LecturaRF.class,
					ble.getProximityUuid(), "Ver Paquete",
					R.drawable.ic_launcher);

			    } else {
				// TODO, Evaluar si le pasamos un mensaje de
				// que no tiene Internet o nada
				// Observar que solo notifique en el if si
				// logre bajar la informacion del paquete
				// Sino, es mejor que no se entere y que no
				// le quede inconsistente
			    }
			}
			// No hay else, ni bola si no es el 3705
		    } else { // Solo actualizo el RSSI y solo si es el 3705
			if (uuid.contains("3705")) {
			    tag_recibido.setRssi(ble.darValorRssi());
			}
			// No hay else, ni bola si no es el 3705
		    }

		    // No hay else, ni bola si no esta cerca
		}
	    }

	    // No hay else, ni bola porque no hay ningun BLE
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
	return Service.START_STICKY;
    }

}
