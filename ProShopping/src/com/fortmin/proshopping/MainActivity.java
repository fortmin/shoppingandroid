package com.fortmin.proshopping;

import java.io.OutputStream;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	private Intent mShareIntent;

	private OutputStream os;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button) findViewById(R.id.ejecutar);
		btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				pasarPuntos();

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void pasarPuntos() {
		mShareIntent = new Intent(this, TransferenciaPuntos.class);
		startActivity(mShareIntent);
	}
}