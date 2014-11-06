package com.fortmin.proshopping;

import java.io.IOException;
import java.net.URLEncoder;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.fortmin.proshopping.deviceinfoendpoint.Deviceinfoendpoint;
import com.fortmin.proshopping.deviceinfoendpoint.model.DeviceInfo;
import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson.JacksonFactory;

/* Esta clase corre como servicio y esta escuchando Google Clou Messaging enviados al dispositivo
 * Cuando el dispositivo se resgistra correctamente para GCM, se envia un mensaje al backend de app engine via Cloud Endpoint
 * indicando que pueden recibir mensajes brodcast desde el
 * se necesita el numero de proyecto
 */

public class GCMIntentService extends GCMBaseIntentService {
	private final Deviceinfoendpoint endpoint;
	// Numero del proyecto Proshopping
	protected static final String PROJECT_NUMBER = "368922725826";

	public static void register(Context mContext) {

		GCMRegistrar.checkDevice(mContext);
		GCMRegistrar.checkManifest(mContext);
		GCMRegistrar.register(mContext, PROJECT_NUMBER);
	}

	public static void unregister(Context mContext) {
		GCMRegistrar.unregister(mContext);
	}

	public GCMIntentService() {
		super(PROJECT_NUMBER);
		Deviceinfoendpoint.Builder endpointBuilder = new Deviceinfoendpoint.Builder(
				AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
				new HttpRequestInitializer() {
					public void initialize(HttpRequest httpRequest) {
					}
				});
		endpoint = CloudEndpointUtils.updateBuilder(endpointBuilder).build();
	}

	@Override
	public void onError(Context context, String errorId) {

		sendNotificationIntent(
				context,
				"Ha habido un error al registrarse, no podra recibir notificaciones de presencia de amigo",
				true, true);
	}

	/**
	 * llamado cuando se recibe un cloud mensaje.
	 */
	@Override
	public void onMessage(Context context, Intent intent) {
		sendNotificationIntent(context, intent.getStringExtra("message"), true,
				false);
	}

	@Override
	public void onRegistered(Context context, String registration) {
		// Se uso un artilujio para registrar con el nombre de usuario
		boolean alreadyRegisteredWithEndpointServer = false;
		Usuario user = Usuario.getInstance();// singleton de proshooping
		String nom_user = user.getNombre();// agrego nombre usario
		try {

			DeviceInfo existingInfo = endpoint.getDeviceInfo(registration)
					.execute();

			if (existingInfo != null
					&& registration.equals(existingInfo
							.getDeviceRegistrationID())) {
				alreadyRegisteredWithEndpointServer = true;
			}
		} catch (IOException e) {
			// Ignore
		}

		try {
			if (!alreadyRegisteredWithEndpointServer) {
				DeviceInfo deviceInfo = new DeviceInfo();
				endpoint.insertDeviceInfo(
						deviceInfo
								.setDeviceRegistrationID(registration)
								.setTimestamp(System.currentTimeMillis())
								.setDeviceInformation(
										URLEncoder.encode(nom_user

										, "UTF-8"))).execute();
			}
		} catch (IOException e) {
			Log.e(GCMIntentService.class.getName(),
					"Excepción por tiempo agotado  " + endpoint.getRootUrl(), e);

			sendNotificationIntent(
					context,
					"Ha habido un error, y no podra ser notificado si entra un amigo",
					true, true);
			return;
		}

		sendNotificationIntent(context, "alerta amigo encendida", false, true);
	}

	@Override
	protected void onUnregistered(Context context, String registrationId) {

		if (registrationId != null && registrationId.length() > 0) {

			try {
				endpoint.removeDeviceInfo(registrationId).execute();
			} catch (IOException e) {
				Log.e(GCMIntentService.class.getName(),
						"Excepcion por tiempo de espera al querer conectarse al servidor "
								+ endpoint.getRootUrl(), e);
				sendNotificationIntent(
						context,
						"1) No esta registrado!\n\n"
								+ "2) Falla al intentar desregistrar usuario!\n\n"
								+ "No es posible registrarse en la nube"
								+ "Endpoint server conocido como "
								+ endpoint.getRootUrl() + "."
								+ "ver el log de android por mas informacion.",
						true, true);
				return;
			}
		}

		sendNotificationIntent(context, "Invisible para Amigos", false, true);
	}

	// envio de la notificación al movil en la clase LecturaRF
	private void sendNotificationIntent(Context context, String message,
			boolean isError, boolean isRegistrationMessage) {
		IngresoAmigo amigo = IngresoAmigo.getInstance();
		if (message.contains("Ingreso de amigo ")) {
			amigo.setIngreso(true);
		}
		NotifyManager notify = new NotifyManager();
		notify.playNotification(getApplicationContext(), LecturaRF.class,
				message, "Alerta Amigo", R.drawable.ic_launcher);

	}

}
