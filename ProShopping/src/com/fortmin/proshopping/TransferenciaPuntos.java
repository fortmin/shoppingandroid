package com.fortmin.proshopping;

import java.nio.charset.Charset;
import java.util.Locale;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcAdapter.OnNdefPushCompleteCallback;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fortmin.proshopping.gae.Nube;
import com.fortmin.proshopping.gae.ShoppingNube;
import com.fortmin.proshopping.logica.shopping.model.Mensaje;

public class TransferenciaPuntos extends Activity implements
		CreateNdefMessageCallback, OnNdefPushCompleteCallback {
	private EditText tranfpuntos;
	private NfcAdapter mNfcAdapter;
	private TextView puntos;
	private String payload = "";
	private double mis_puntos;
	private Usuario user = Usuario.getInstance();
	private PendingIntent pendingIntent;
	private static final int MESSAGE_SENT = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.transferencia_puntos);
		puntos = (TextView) findViewById(R.id.mispuntos);
		tranfpuntos = (EditText) findViewById(R.id.transfpuntos);
		ImageButton pasarpuntos = (ImageButton) findViewById(R.id.btnpasarPtos);
		Nube nube = new Nube(ShoppingNube.OPE_GET_PUNTAJE_CLIENTE);
		Mensaje resp = nube.ejecutarGetPuntajeCliente(user.getNombre());
		puntos.setTextColor(Color.YELLOW);
		puntos.setTextSize(18);
		mis_puntos = resp.getValor();
		puntos.setText("Usted disponde de " + mis_puntos + " puntos");
		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		if (mNfcAdapter == null) {
			finish();
			return;
		}
		mNfcAdapter.setNdefPushMessageCallback(this, this);
		// Register callback to listen for message-sent success
		mNfcAdapter.setOnNdefPushCompleteCallback(this, this);
		pasarpuntos.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if (tranfpuntos.getText().toString().equals(""))
					mostrarMensaje("Si desea enviar puntos debe escribir la cantidad a transferir");
				else {
					Nube nube = new Nube(ShoppingNube.OPE_QUITAR_PUNTOS);
					Mensaje resp = nube.quitarPuntos(user.getNombre(),
							Integer.parseInt(tranfpuntos.getText().toString()));
					if (resp.getMensaje().equals("OK")) {
						mostrarMensaje("Acerque sus dispositivo al dispositivo a tranferir");
						nube = new Nube(ShoppingNube.OPE_GET_PUNTAJE_CLIENTE);
						resp = nube.ejecutarGetPuntajeCliente(user.getNombre());
						puntos.setText("Aun disponde de "
								+ resp.getValor().toString());
					} else {

						mostrarMensaje("No disponde de esos Puntos");

					}

				}

			}
		});

	}

	@Override
	public void onResume() {
		super.onResume();
		System.out.println("ZZ onResume");
		// Check to see that the Activity started due to an Android Beam
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
			processIntent(getIntent());
		}

		if (pendingIntent == null) {
			pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
					getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		}
		NfcAdapter.getDefaultAdapter(this).enableForegroundDispatch(this,
				pendingIntent, null, null);
	}

	@Override
	public void onNewIntent(Intent intent) {
		setIntent(intent);
	}

	void processIntent(Intent intent) {
		System.out.println("ZZ processIntent");
		Parcelable[] rawMsgs = intent
				.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
		// only one message sent during the beam
		NdefMessage msg = (NdefMessage) rawMsgs[0];
		// record 1 is the AAR, if present
		Nube nube = new Nube(ShoppingNube.OPE_AGREGAR_PUNTOS);
		payload = new String(msg.getRecords()[0].getPayload());
		Mensaje resp = nube.agregarPuntos(user.getNombre(),
				Integer.parseInt(payload));
		puntos.setText("Usted ha recibido " + payload);
		if (resp.getMensaje().equals("OK")) {
			mostrarMensaje("Se ha acreditado " + payload + "puntos");
			nube = new Nube(ShoppingNube.OPE_GET_PUNTAJE_CLIENTE);
			resp = nube.ejecutarGetPuntajeCliente(user.getNombre());
			puntos.setText("Ahora usted disponde de "
					+ resp.getValor().toString());
		}

	}

	public NdefMessage create_RTD_TEXT_NdefMessage(String inputText) {
		Locale locale = new Locale("en", "US");
		byte[] langBytes = locale.getLanguage().getBytes(
				Charset.forName("US-ASCII"));
		boolean encodeInUtf8 = false;
		Charset utfEncoding = encodeInUtf8 ? Charset.forName("UTF-8") : Charset
				.forName("UTF-16");
		int utfBit = encodeInUtf8 ? 0 : (1 << 7);
		byte status = (byte) (utfBit + langBytes.length);
		byte[] textBytes = inputText.getBytes(utfEncoding);
		byte[] data = new byte[1 + langBytes.length + textBytes.length];
		data[0] = (byte) status;
		System.arraycopy(langBytes, 0, data, 1, langBytes.length);
		System.arraycopy(textBytes, 0, data, 1 + langBytes.length,
				textBytes.length);
		NdefRecord textRecord = new NdefRecord(NdefRecord.TNF_WELL_KNOWN,
				NdefRecord.RTD_TEXT, new byte[0], data);
		NdefMessage message = new NdefMessage(new NdefRecord[] { textRecord });
		return message;
	}

	public NdefMessage[] getNdefMessages(Intent intent) {
		NdefMessage[] msgs = null;
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
			Parcelable[] rawMsgs = intent
					.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
			if (rawMsgs != null) {
				msgs = new NdefMessage[rawMsgs.length];
				for (int i = 0; i < rawMsgs.length; i++) {
					msgs[i] = (NdefMessage) rawMsgs[i];
				}
			} else {
				byte[] empty = new byte[] {};
				NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN,
						empty, empty, empty);
				NdefMessage msg = new NdefMessage(new NdefRecord[] { record });
				msgs = new NdefMessage[] { msg };
			}
		} else {
			Log.d("Peer to Peer 2", "Unknown intent.");
			finish();
		}
		return msgs;
	}

	public void mostrarMensaje(String mensaje) {
		Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG)
				.show();
	}

	@Override
	public void onNdefPushComplete(NfcEvent event) {
		// TODO Auto-generated method stub
		mHandler.obtainMessage(MESSAGE_SENT).sendToTarget();
	}

	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_SENT:
				mostrarMensaje("Puntos Enviados");
				break;
			}
		}
	};

	@Override
	public NdefMessage createNdefMessage(NfcEvent event) {
		String text = (tranfpuntos.getText().toString());
		NdefMessage msg = new NdefMessage(
				new NdefRecord[] {
						createMimeRecord("application/com.fortmin.proshopping",
								text.getBytes())
						/**
						 * The Android Application Record (AAR) is commented
						 * out. When a device receives a push with an AAR in it,
						 * the application specified in the AAR is guaranteed to
						 * run. The AAR overrides the tag dispatch system. You
						 * can add it back in to guarantee that this activity
						 * starts when receiving a beamed message. For now, this
						 * code uses the tag dispatch system.
						 */
						,
						NdefRecord
								.createApplicationRecord("com.fortmin.proshopping") });
		return msg;
	}

	public NdefRecord createMimeRecord(String mimeType, byte[] payload) {
		byte[] mimeBytes = mimeType.getBytes(Charset.forName("US-ASCII"));
		NdefRecord mimeRecord = new NdefRecord(NdefRecord.TNF_MIME_MEDIA,
				mimeBytes, new byte[0], payload);
		return mimeRecord;
	}

	public NdefRecord serialize(String string) {
		System.out.println("ZZ serialize");
		final byte[] language = Locale.ENGLISH.getLanguage().getBytes(
				Charset.forName("US-ASCII"));
		final byte[] text = string.getBytes(Charset.forName("UTF-8"));

		final int languageSize = language.length;
		final int textLength = text.length;

		final byte[] payload = new byte[1 + languageSize + textLength];

		payload[0] = (byte) (languageSize & 0x1F);

		System.arraycopy(language, 0, payload, 1, languageSize);
		System.arraycopy(text, 0, payload, 1 + languageSize, textLength);

		return new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT,
				new byte[0], payload);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	public void activarBeam() {
		mNfcAdapter.setNdefPushMessageCallback(this, this);
		// Register callback to listen for message-sent success
		mNfcAdapter.setOnNdefPushCompleteCallback(this, this);
	}

}
