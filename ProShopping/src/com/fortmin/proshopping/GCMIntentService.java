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

/**
 * This class is started up as a service of the Android application. It listens
 * for Google Cloud Messaging (GCM) messages directed to this device.
 * 
 * When the device is successfully registered for GCM, a message is sent to the
 * App Engine backend via Cloud Endpoints, indicating that it wants to receive
 * broadcast messages from the it.
 * 
 * Before registering for GCM, you have to create a project in Google's Cloud
 * Console (https://code.google.com/apis/console). In this project, you'll have
 * to enable the "Google Cloud Messaging for Android" Service.
 * 
 * Once you have set up a project and enabled GCM, you'll have to set the
 * PROJECT_NUMBER field to the project number mentioned in the "Overview" page.
 * 
 * See the documentation at
 * http://developers.google.com/eclipse/docs/cloud_endpoints for more
 * information.
 */
public class GCMIntentService extends GCMBaseIntentService {
	private final Deviceinfoendpoint endpoint;

	/*
	 * TODO: Set this to a valid project number. See
	 * http://developers.google.com/eclipse/docs/cloud_endpoints for more
	 * information.
	 */
	protected static final String PROJECT_NUMBER = "368922725826";

	/**
	 * Register the device for GCM.
	 * 
	 * @param mContext
	 *            the activity's context.
	 */
	public static void register(Context mContext) {

		GCMRegistrar.checkDevice(mContext);
		GCMRegistrar.checkManifest(mContext);
		GCMRegistrar.register(mContext, PROJECT_NUMBER);
	}

	/**
	 * Unregister the device from the GCM service.
	 * 
	 * @param mContext
	 *            the activity's context.
	 */
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

	/**
	 * Called on registration error. This is called in the context of a Service
	 * - no dialog or UI.
	 * 
	 * @param context
	 *            the Context
	 * @param errorId
	 *            an error message
	 */
	@Override
	public void onError(Context context, String errorId) {

		sendNotificationIntent(
				context,
				"Ha habido un error al registrarse, no podra recibir notificaciones de presencia de amigo",
				true, true);
	}

	/**
	 * Called when a cloud message has been received.
	 */
	@Override
	public void onMessage(Context context, Intent intent) {
		sendNotificationIntent(context,
				"Ingreso de amigo " + intent.getStringExtra("message"), true,
				false);
	}

	/**
	 * Called back when a registration token has been received from the Google
	 * Cloud Messaging service.
	 * 
	 * @param context
	 *            the Context
	 */
	@Override
	public void onRegistered(Context context, String registration) {

		boolean alreadyRegisteredWithEndpointServer = false;
		Usuario user = Usuario.getInstance();// singleton de proshooping
		String nom_user = user.getNombre();// agrego nombre usario
		try {

			/*
			 * Using cloud endpoints, see if the device has already been
			 * registered with the backend
			 */
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
				/*
				 * We are not registered as yet. Send an endpoint message
				 * containing the GCM registration id and some of the device's
				 * product information over to the backend. Then, we'll be
				 * registered.
				 */
				DeviceInfo deviceInfo = new DeviceInfo();
				endpoint.insertDeviceInfo(
						deviceInfo
								.setDeviceRegistrationID(registration)
								.setTimestamp(System.currentTimeMillis())
								.setDeviceInformation(
										URLEncoder.encode(nom_user // android.os.Build.MANUFACTURER
												// iba " "
												/*
												 * + android.os.Build.PRODUCT
												 */, "UTF-8"))).execute();
			}
		} catch (IOException e) {
			Log.e(GCMIntentService.class.getName(),
					"Exception received when attempting to register with server at "
							+ endpoint.getRootUrl(), e);

			sendNotificationIntent(
					context,
					"Ha habido un error, y no podra ser notificado si entra un amigo",
					true, true);
			return;
		}

		sendNotificationIntent(context, "Notificaremos cuando ingrese amigo",
				false, true);
	}

	/**
	 * Called back when the Google Cloud Messaging service has unregistered the
	 * device.
	 * 
	 * @param context
	 *            the Context
	 */
	@Override
	protected void onUnregistered(Context context, String registrationId) {

		if (registrationId != null && registrationId.length() > 0) {

			try {
				endpoint.removeDeviceInfo(registrationId).execute();
			} catch (IOException e) {
				Log.e(GCMIntentService.class.getName(),
						"Exception received when attempting to unregister with server at "
								+ endpoint.getRootUrl(), e);
				sendNotificationIntent(
						context,
						"1) De-registration with Google Cloud Messaging....SUCCEEDED!\n\n"
								+ "2) De-registration with Endpoints Server...FAILED!\n\n"
								+ "We were unable to de-register your device from your Cloud "
								+ "Endpoints server running at "
								+ endpoint.getRootUrl() + "."
								+ "See your Android log for more information.",
						true, true);
				return;
			}
		}

		sendNotificationIntent(context, "Invisible para Amigos", false, true);
	}

	/**
	 * Generate a notification intent and dispatch it to the RegisterActivity.
	 * This is how we get information from this service (non-UI) back to the
	 * activity.
	 * 
	 * For this to work, the 'android:launchMode="singleTop"' attribute needs to
	 * be set for the RegisterActivity in AndroidManifest.xml.
	 * 
	 * @param context
	 *            the application context
	 * @param message
	 *            the message to send
	 * @param isError
	 *            true if the message is an error-related message; false
	 *            otherwise
	 * @param isRegistrationMessage
	 *            true if this message is related to registration/unregistration
	 */
	private void sendNotificationIntent(Context context, String message,
			boolean isError, boolean isRegistrationMessage) {
		IngresoAmigo amigo = IngresoAmigo.getInstance();
		if (message.contains("Ingreso de amigo ")) {
			amigo.setIngreso(true);
		}
		NotifyManager notify = new NotifyManager();
		notify.playNotification(getApplicationContext(), LecturaRF.class,
				message, "Ingresó amigo", R.drawable.ic_launcher);

		/*
		 * String Amigo = "Amigo Presente"; Intent notificationIntent = new
		 * Intent(context, LecturaRF.class);
		 * notificationIntent.putExtra("gcmIntentServiceMessage", true);
		 * notificationIntent.putExtra("registrationMessage",
		 * isRegistrationMessage); notificationIntent.putExtra("error",
		 * isError); notificationIntent.putExtra("Amigo", Amigo);
		 * notificationIntent.putExtra("message", message);
		 * notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// //
		 * FLAG_ACTIVITY_NEW_TASK startActivity(notificationIntent);
		 */

		/*
		 * try { JSONObject json = new JSONObject(notificationIntent.getExtras()
		 * .getString("com.parse.Data")); NotificationCompat.Builder mBuilder =
		 * new NotificationCompat.Builder(
		 * context).setSmallIcon(R.drawable.ic_launcher)
		 * .setContentTitle(json.getString("Amigo"))
		 * .setContentText(json.getString("message")); NotificationManager
		 * mNotificationManager = (NotificationManager) context
		 * .getSystemService(Context.NOTIFICATION_SERVICE);
		 * mNotificationManager.notify(1, mBuilder.build());
		 * 
		 * } catch (JSONException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

	}

}
