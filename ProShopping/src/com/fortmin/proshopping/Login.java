package com.fortmin.proshopping;

import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fortmin.proshopping.gae.ShoppingNube;
import com.fortmin.proshopping.logica.shopping.model.Mensaje;

public class Login extends Activity {
    private EditText nom_user;
    private EditText pass;
    private String nom_usuario;
    private usuario user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	    nom_user=(EditText) findViewById(R.id.usuario);
		pass=(EditText) findViewById(R.id.pass);
        ImageButton login=(ImageButton)findViewById(R.id.btnLogeo);
        Typeface fuente=Typeface.createFromAsset(getAssets(),"Roboto-Regular.ttf");
        nom_user.setTypeface(fuente);
        pass.setTypeface(fuente); 
       
        login.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				ShoppingNube comNube = new ShoppingNube(ShoppingNube.OPE_LOGIN_USUARIO);
				    nom_usuario=nom_user.getText().toString();
					Mensaje resp = null;
					try {
						resp = (Mensaje) comNube.execute( pass.getText().toString(),nom_usuario).get();
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
						user=usuario.getInstance();
						user.setNombre(nom_usuario);
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
