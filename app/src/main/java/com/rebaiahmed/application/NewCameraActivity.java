package com.rebaiahmed.application;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class NewCameraActivity extends AppCompatActivity implements View.OnClickListener {

    private Button validate;
    private EditText cameraName;
    private EditText ip;
    private EditText port;
    private EditText password;
    private Boolean modification = false;
    private String passwordStr = "";
    private String id = "";
    private DbManager dbManager;
    private ImageButton deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_camera);
        validate = (Button) findViewById(R.id.validate);
        validate.setOnClickListener(this);
        cameraName = (EditText) findViewById(R.id.cameraName);
        ip = (EditText) findViewById(R.id.ipAddress);
        port = (EditText) findViewById(R.id.port);
        password = (EditText) findViewById(R.id.password);
        deleteButton = (ImageButton) findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(this);
        dbManager = new DbManager();
        Intent modify = this.getIntent();
        if (modify.getExtras().getString("Activity").equals("Modify")) {
            deleteButton.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Modify Activity", Toast.LENGTH_SHORT).show();
            cameraName.setText(modify.getExtras().getString("Name"));
            id = modify.getExtras().getString("Id");
            Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
            ip.setText(modify.getExtras().getString("IP"));
            port.setText(modify.getExtras().getString("Port"));
            passwordStr = modify.getExtras().getString("Password");
            modification = true;
        }
    }

    public void insertCamera() {
        String cameraNameStr = cameraName.getText().toString();
        Toast.makeText(this, "Insertion : " + cameraNameStr, Toast.LENGTH_SHORT).show();
        String ipStr = ip.getText().toString();
        String portStr = port.getText().toString();
        String passwordStr = password.getText().toString();
        dbManager.insertCamera(portStr, passwordStr, cameraNameStr, ipStr);
    }

    public void updateCamera(String id) {
        String cameraNameStr = cameraName.getText().toString();
        Toast.makeText(this, "Update : " + cameraNameStr, Toast.LENGTH_SHORT).show();
        String ipStr = ip.getText().toString();
        String portStr = port.getText().toString();
        if (!password.getText().toString().isEmpty()) {
            passwordStr = password.getText().toString();
        }
        dbManager.updateCamera(id, cameraNameStr, ipStr, portStr, passwordStr);
    }

    @Override
    public void onClick(View view) {
        if (view == validate) {
            if (modification) {
                updateCamera(id);
            } else {
                insertCamera();
                Toast.makeText(this, "Camera inserted", Toast.LENGTH_SHORT).show();
            }
        }
        if (view == deleteButton){
            deleteCamera(id);
        }
    }

    private void deleteCamera(String id) {
        dbManager.deleteCamera(id);
        finish();
        Toast.makeText(this, "Delete successful", Toast.LENGTH_SHORT).show();
    }
}
