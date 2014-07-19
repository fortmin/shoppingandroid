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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {
    private EditText usuario;
    private EditText pass;
    private TextView titulo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		titulo=(TextView) findViewById(R.id.tituloLogin);
		usuario=(EditText) findViewById(R.id.usuario);
        pass=(EditText) findViewById(R.id.pass);
        Button login=(Button)findViewById(R.id.btnLogeo);
        Typeface fuente=Typeface.createFromAsset(getAssets(),"daniela.ttf");
        Typeface fuente1=Typeface.createFromAsset(getAssets(),"LokiCola.ttf");
        titulo.setTypeface(fuente1);
        titulo.setTextColor(Color.WHITE);
        titulo.setTextSize((float) 50.0);
        usuario.setTypeface(fuente);
        pass.setTypeface(fuente);  
        login.setBackgroundResource(R.drawable.degradado);
        login.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				ShoppingNube comNube = new ShoppingNube(ShoppingNube.OPE_LOGIN_USUARIO);
				
					Mensaje resp = null;
					try {
						resp = (Mensaje) comNube.execute( pass.getText().toString(),usuario.getText().toString()).get();
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
						mostrarMensaje("Bienvenido");
						verOpciones();
					 }
					 else if(mensaje.contains("USUARIO_INEXISTENTE"))
						mostrarMensaje("Usuario inexistente");
					 else if (mensaje.contains("CLAVE_INCORRECTA"))
						mostrarMensaje("error en password ");
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
