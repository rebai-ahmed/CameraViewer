package com.rebaiahmed.application;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity implements View.OnClickListener{
    Button getStat;
    TextView nbPerson;
    TextView nbPersonIn;
    TextView nbPersonOut;
    private Context mContext;
    private Button startStreaming;
    private ImageView mImageView;
    private String ip;
    private String port;
    private ImageButton editButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getStat = (Button) findViewById(R.id.getStat);
        nbPerson = (TextView) findViewById(R.id.nbPerson);
        nbPersonIn = (TextView) findViewById(R.id.nbPersonIn);
        nbPersonOut = (TextView) findViewById(R.id.nbPersonOut);
        ip = this.getIntent().getExtras().getString("IP");
        port = this.getIntent().getExtras().getString("Port");
        // Get the application context
        mContext = getApplicationContext();
        startStreaming = (Button) findViewById(R.id.startStreaming);
        mImageView = (ImageView) findViewById(R.id.iv);
        editButton = (ImageButton) findViewById(R.id.editButton);
        editButton.setOnClickListener(this);
        final Thread firstThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        volleyJsonObjectRequest("http://" + ip + ":" + port + "/getstat.php");
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        final Thread secondThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (!Thread.currentThread().isInterrupted()) {
                        try {
                            // Initialize a new ImageRequest
                            ImageRequest imageRequest = new ImageRequest(
                                    "http://" + ip + ":" + port + "/test.jpg", // Image URL
                                    new Response.Listener<Bitmap>() { // Bitmap listener
                                        @Override
                                        public void onResponse(Bitmap response) {
                                            // Do something with response
                                            mImageView.setImageBitmap(response);
                                        }
                                    },
                                    0, // Image width
                                    0, // Image height
                                    null, // Image scale type
                                    null, //Image decode configuration
                                    new Response.ErrorListener() { // Error listener
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            // Do something with error response
                                            error.printStackTrace();
                                            Log.d("ff", "errorrrrr");
                                        }
                                    }
                            );
                            // Add ImageRequest to the RequestQueue
                            AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(imageRequest, "");
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.d("interupted", "stoped");
                        break;
                    }
                }
            }
        });
        getStat.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           if (!firstThread.isAlive()) {
                                               volleyJsonObjectRequest("http://" + ip + ":" + port + "/getstat.php");
                                               Log.d("hh", "lo");
                                               firstThread.start();
                                           }
                                       }
                                   }
        );
        // Set a click listener for button widget
        startStreaming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!secondThread.isAlive())
                    secondThread.start();
            }
        });
    }

    public void volleyJsonObjectRequest(String url) {
        String REQUEST_TAG = "";
        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            nbPerson.setText("nbrPers: " + response.getString("nbr"));
                            nbPersonIn.setText("in: " + response.getString("inn"));
                            nbPersonOut.setText("out: " + response.getString("outt"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("lol1", "Error: " + error.getMessage());
            }
        });
        // Adding JsonObject request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectReq, REQUEST_TAG);
    }

    @Override
    public void onClick(View view) {
        if (view == editButton){
            String id = this.getIntent().getExtras().getString("Id");;
            String name = this.getIntent().getExtras().getString("Name");
            String password = this.getIntent().getExtras().getString("Password");

            Intent modifyCamera = new Intent(getApplicationContext(), NewCameraActivity.class);
            modifyCamera.putExtra("Activity", "Modify");
            modifyCamera.putExtra("Id", id);
            modifyCamera.putExtra("Name", name);
            modifyCamera.putExtra("IP", ip);
            modifyCamera.putExtra("Port", port);
            modifyCamera.putExtra("Password", password);

            startActivity(modifyCamera);
        }
    }
}
