package com.fortmin.proshopping;

import java.io.IOException;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.googleapis.services.AbstractGoogleClient;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

public class CloudEndpointUtils {

	protected static final boolean LOCAL_ANDROID_RUN = false;

	// si se corre el backend local para prueba
	protected static final String LOCAL_APP_ENGINE_SERVER_URL = "http://localhost:8888/";

	// si vamos contra la nube
	protected static final String LOCAL_APP_ENGINE_SERVER_URL_FOR_ANDROID = "http://10.0.2.2:8888";

	public static <B extends AbstractGoogleClient.Builder> B updateBuilder(
			B builder) {
		if (LOCAL_ANDROID_RUN) {
			builder.setRootUrl(LOCAL_APP_ENGINE_SERVER_URL_FOR_ANDROID
					+ "/_ah/api/");
		}

		final boolean enableGZip = builder.getRootUrl().startsWith("https:");
		builder.setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
			public void initialize(AbstractGoogleClientRequest<?> request)
					throws IOException {
				if (!enableGZip) {
					request.setDisableGZipContent(true);
				}
			}
		});

		return builder;
	}

	public static void logAndShow(Activity activity, String tag, String message) {
		Log.e(tag, message);
		showError(activity, message);
	}

	public static void logAndShow(Activity activity, String tag, Throwable t) {
		Log.e(tag, "Error", t);
		String message = t.getMessage();
		if (t instanceof GoogleJsonResponseException) {
			GoogleJsonError details = ((GoogleJsonResponseException) t)
					.getDetails();
			if (details != null) {
				message = details.getMessage();
			}
		}
		showError(activity, message);
	}

	public static void showError(final Activity activity, String message) {
		final String errorMessage = message == null ? "Error" : "[Error ] "
				+ message;
		activity.runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG)
						.show();
			}
		});
	}
}
