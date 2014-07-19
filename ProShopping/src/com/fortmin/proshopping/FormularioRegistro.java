package com.fortmin.proshopping;

import java.util.concurrent.ExecutionException;

import com.fortmin.proshopping.gae.ShoppingNube;
import com.fortmin.proshopping.shopping.model.Mensaje;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FormularioRegistro extends Activity {
    private EditText nombre;
    private EditText e_mail;
    private EditText usuario;
    private TextView titulo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_formulario_registro);
		titulo=(TextView) findViewById(R.id.tituloFormulario);
        nombre=(EditText) findViewById(R.id.registronombre);
        e_mail=(EditText) findViewById(R.id.registroemail);
        usuario=(EditText) findViewById(R.id.registrousuario);
        Button registro=(Button)findViewById(R.id.btnRegistro);
        Typeface fuente=Typeface.createFromAsset(getAssets(),"daniela.ttf");
        Typeface fuente1=Typeface.createFromAsset(getAssets(),"LokiCola.ttf");
        titulo.setTypeface(fuente1);
        titulo.setTextColor(Color.WHITE);
        titulo.setTextSize((float) 60.0);
        usuario.setTypeface(fuente);
        e_mail.setTypeface(fuente);
        nombre.setTypeface(fuente);
        registro.setBackgroundResource(R.drawable.degradado);
        registro.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				ShoppingNube comNube = new ShoppingNube(ShoppingNube.OPE_REGISTRO_USUARIO);
				
					Mensaje resp = null;
					try {
						resp = (Mensaje) comNube.execute(usuario.getText().toString(),e_mail.getText().toString(),nombre.getText().toString()).get();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (resp!=null){
					 String mensaje=resp.getMensaje();	// Respuesta puede ser OK o USUARIO_INEXISTENTE o CLAVE_INCORRECTA
					 if (mensaje.equals("OK")){
						mostrarMensaje("Registrado correctamente");
						verOpciones();
					 }
					 else if(mensaje.contains("USUARIO_EXISTENTE"))
						mostrarMensaje("Usuario Ya registrado");
					 
					}
				
				
				
				}
			
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inicio, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
   public void mostrarMensaje(String mensaje){
	   Toast.makeText(getApplicationContext(), mensaje,Toast.LENGTH_LONG).show();
   }
	
   public void verOpciones(){
	   Intent opciones = new Intent(this, MenuCliente.class);
		startActivity(opciones);
   }
}
