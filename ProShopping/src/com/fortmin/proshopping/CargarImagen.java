package com.fortmin.proshopping;

import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.fortmin.proshopping.gae.GestionNube;
import com.fortmin.proshopping.gae.Nube;
import com.fortmin.proshopping.logica.gestion.model.Imagen;

public class CargarImagen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
/*
		Drawable drawable = getResources().getDrawable(R.drawable.img_tvled32);
		Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
		byte[] bitMapData = stream.toByteArray();

		Imagen imagen = new Imagen();
		Imagen imgcodif = imagen.encodeImagen(bitMapData);
		imagen.setImagen(imgcodif.getImagen());
		
		Nube nube = new Nube(GestionNube.OPE_CARGAR_IMAGEN);
		nube.ejecutarCargarImagen("TodoMusica", "tvled32", imagen);
*/
	}

}
