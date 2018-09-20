package com.rebaiahmed.application;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListCamerasActivity extends AppCompatActivity implements View.OnClickListener {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    FloatingActionButton add;
    DatabaseReference myDb = database.getReference("cameras");
    List<CameraInfos> cameras = new ArrayList<>();
    RecyclerView myrv;
    final RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this, cameras);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cameras);
        add = (FloatingActionButton) findViewById(R.id.addButton);
        add.setOnClickListener(this);
        myrv = (RecyclerView) findViewById(R.id.recyclerViewId);
        myrv.setLayoutManager(new GridLayoutManager(this, 2));

    }

    @Override
    public void onClick(View view) {
        if (view == add) {
            Log.d("Position", "add button");
            Intent newCamera = new Intent(getApplicationContext(), NewCameraActivity.class);
            newCamera.putExtra("Activity", "new");
            startActivity(newCamera);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCameras();
    }

    private void getCameras() {
        myDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> cameraSnapshot = dataSnapshot.getChildren();
                cameras.clear();
                for (DataSnapshot camera : cameraSnapshot) {
                    CameraInfos cameraInfos = camera.getValue(CameraInfos.class);
                    cameras.add(cameraInfos);
                }
                myrv.setAdapter(myAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        String[] values = new String[cameras.size()];
        int i = 0;
        // Defined Array values to show in ListView
        for (CameraInfos camera : cameras) {
            values[i] = camera.getName();
            i++;
        }
        myAdapter.notifyDataSetChanged();

    }
}
