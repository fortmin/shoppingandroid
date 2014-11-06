package com.fortmin.proshopping;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

/* clase que solo sirve para la tableta, a forma en que fue manejado es que cuando se va a instalar en tableta se cambia en el manifest la
 * activity principal, esta activity si se pausa es levantada desde el servicio, poolea constantemente para saber el usuario que esta cerca
 * para mostrar un mensaje personalizado, como la tableta tiene salida hdmi, la idea es mostrarlo en monitor grande
 */
public class Tablet extends Activity {
	private Intent servicio;
	private TextView mensaje;
	private boolean servicioiniciado = false;
	private Usuario user;
	private static float tam_letra = 35;
	private ImageView imagen;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		user = Usuario.getInstance();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tablet);
		mensaje = (TextView) findViewById(R.id.texto);
		imagen = (ImageView) findViewById(R.id.oferta);
		mensaje.setTextSize(tam_letra);

		if (!servicioiniciado) {
			encenderServicio();
		}

		if (user.getNombre() != null) {
			if (user.getNombre().contains("jafortti")) {
				imagen.setImageResource(R.drawable.imagen_julio);
			} else if (user.getNombre().contains("fminos")) {
				imagen.setImageResource(R.drawable.imagen_fernando);

			}
		} else {
			switch (user.getNumero_imagen()) {
			case 0:
				imagen.setImageResource(R.drawable.imagen1);
				break;
			case 1:
				imagen.setImageResource(R.drawable.imagen2);
				break;
			case 2:
				imagen.setImageResource(R.drawable.imagen3);
				break;
			case 3:
				imagen.setImageResource(R.drawable.imagen4);
				break;
			case 4:
				imagen.setImageResource(R.drawable.imagen5);
				break;
			case 5:
				imagen.setImageResource(R.drawable.imagen1);
				user.setNumero_imagen(0);
				break;
			}

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.tablet, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPause() {
		super.onPause();
		this.finish();

	}

	public void encenderServicio() {
		// se guarda el estado del servicio en una preferencia para cuando se
		// vuelva a correr la app

		servicio = new Intent(this, ServicioClienteCerca.class);
		startService(servicio);

	}

}
