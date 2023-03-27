package ec.edu.tecazuay.edu.apiboli;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ec.edu.tecazuay.edu.apiboli.modelo.Publicacion;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {





  ArrayList<String> datos = new ArrayList<>();
  ArrayAdapter arrayAdapter;
  ListView listView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);



    Button btnllamar = (Button) findViewById(R.id.btnLlamarRegistro);
    btnllamar.setOnClickListener(this);



    listView = findViewById(R.id.listViewNoticias);
    arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, datos);

    listView.setAdapter(arrayAdapter);
    obtenerDatos();
  }

  private void obtenerDatos() {
    String url = "https://jsonplaceholder.typicode.com/albums";
    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
      @Override
      public void onResponse(JSONArray response) {
        manejarJson(response);
      }
    }, new Response.ErrorListener() {

      @Override
      public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
      }
    }
    );
    //REALIZAR LA PETICION DE TIPO GET AL WS
    Volley.newRequestQueue(this).add(jsonArrayRequest);//
  }

  private void manejarJson(JSONArray jsonArray) {
    for (int i = 0; i < jsonArray.length(); i++) {
      JSONObject jsonObject = null;
      try {
        jsonObject = jsonArray.getJSONObject(i);
        Publicacion publicacion = new Publicacion();
        publicacion.setUserId(jsonObject.getInt("userId"));
        publicacion.setId(jsonObject.getInt("id"));
        publicacion.setTitulo(jsonObject.getString("title"));

        //ALMACENAR EN ARRAYLIST<PUBLICACION>TODA LA INFORMACION
        datos.add(String.valueOf(publicacion.getUserId()));
        datos.add(String.valueOf(publicacion.getId()));
        datos.add(publicacion.getTitulo());

      } catch (JSONException e) {

        e.printStackTrace();
      }
    }
    arrayAdapter.notifyDataSetChanged();
  }

  @Override
  public void onClick(View view) {
    Intent intent= new Intent(getApplication(), InsertarAlbum.class);
    startActivity(intent);

  }
}
