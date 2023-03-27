package ec.edu.tecazuay.edu.apiboli;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class InsertarAlbum extends AppCompatActivity implements View.OnClickListener {
  private Button registrar;
  private Button llamarRegistro;
  private EditText userId;
  private EditText id;
  private EditText titulo;

  private RequestQueue queue;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_insertar_album);


    id = findViewById(R.id.txtId);
    userId = findViewById(R.id.txtUserId);
    id = findViewById(R.id.txtId);


    queue = Volley.newRequestQueue(this);

  }

  private void ingresarlosAlbums() {
    String url = "https://jsonplaceholder.typicode.com/albums";
    JSONObject jsonObject = new JSONObject();
    try {
      jsonObject.put("id", id.getText().toString());
      jsonObject.put("userid", userId.getText().toString());
      jsonObject.put("title", titulo.getText().toString());

    } catch (JSONException e) {
      e.printStackTrace();
    }
    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
      url, jsonObject, new Response.Listener<JSONObject>() {
      @Override
      public void onResponse(JSONObject response) {
        Toast.makeText(InsertarAlbum.this, "ALBUM CARGADO DE MANERA EFICIENTE", Toast.LENGTH_SHORT).show();
        id.setText("");
        userId.setText("");
        titulo.setText("");
        Intent intent = new Intent(getApplication(), MainActivity.class);
        startActivity(intent);
      }

    }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        Toast.makeText(InsertarAlbum.this, "NO SE PUDO CARGAR EL ALBUM", Toast.LENGTH_SHORT).show();

      }
    });
    queue.add(jsonObjectRequest);
  }


  @Override
  public void onClick(View view) {
    ingresarlosAlbums();
  }
}
