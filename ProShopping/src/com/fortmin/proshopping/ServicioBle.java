package com.fortmin.proshopping;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;

public class ServicioBle extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub

		/*
		 * if (beacons.isBtEnabled() == false) { // BT not enabled. Request to
		 * turn it on. User needs to restart app once it's turned on. Intent
		 * enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		 * startActivity(enableBtIntent); //finish(); }
		 */

		// inicializacion del ble

		Uri defaultSound = RingtoneManager
				.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		Notification.Builder mBuilder = new Notification.Builder(this)
				.setSmallIcon(android.R.drawable.stat_sys_warning)
				.setLargeIcon(
						(((BitmapDrawable) getResources().getDrawable(
								R.drawable.ic_launcher)).getBitmap()))
				.setContentTitle("Oferta Nueva").setContentText("prueba")
				.setSound(defaultSound).setContentInfo("4")
				.setTicker("Alerta!");
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		mNotificationManager.notify(1, mBuilder.build());
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

	}
}
