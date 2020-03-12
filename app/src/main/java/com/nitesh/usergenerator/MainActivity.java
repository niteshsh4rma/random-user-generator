package com.nitesh.usergenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageView pic;
    TextView name;
    TextView gender;
    TextView email;
    TextView phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void generate(View view){

        pic = findViewById(R.id.pic);
        name = findViewById(R.id.name);
        gender = findViewById(R.id.gender);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);

        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://randomuser.me/api/", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null){
                    Toast.makeText(MainActivity.this, "Data Fetched", Toast.LENGTH_SHORT).show();
                    try {
                        JSONArray results = response.getJSONArray("results");
                        JSONObject resultobj = results.getJSONObject(0);
                        JSONObject Name = resultobj.getJSONObject("name");
                        JSONObject Pic = resultobj.getJSONObject("picture");
                        Picasso.get().load(Pic.getString("large")).into(pic);
                        String naame = Name.getString("first") + " " + Name.getString("last");
                        name.setText(naame);
                        gender.setText(resultobj.getString("gender"));
                        email.setText(resultobj.getString("email"));
                        phone.setText(resultobj.getString("phone"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else{
                    Toast.makeText(MainActivity.this, "Null Data", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }
    }
