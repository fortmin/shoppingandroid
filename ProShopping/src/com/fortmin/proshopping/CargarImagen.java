package com.fortmin.proshopping;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.fortmin.proshopping.gae.GestionNube;
import com.fortmin.proshopping.gae.Nube;
import com.fortmin.proshopping.logica.gestion.model.Imagen;


public class CargarImagen {

	public static void main(String[] args) throws IOException,
			FileNotFoundException {

		byte readBuf[] = new byte[512 * 1024];
		FileInputStream fin = null;
		fin = new FileInputStream("");
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		int readCnt = 0;
		readCnt = fin.read(readBuf);
		while (0 < readCnt) {
			bout.write(readBuf, 0, readCnt);
			readCnt = fin.read(readBuf);
		}
		fin.close();
		
		byte[] img = bout.toByteArray();
		Imagen imagen = new Imagen();
		imagen.set("img/png", img);
		
		Nube nube = new Nube(GestionNube.OPE_CARGAR_IMAGEN);
		nube.ejecutarCargarImagen("TodoMusica", "tvled32", imagen);


	}

}
