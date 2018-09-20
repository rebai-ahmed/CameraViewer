package com.rebaiahmed.application;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Rebai Ahmed on 19/09/2018.
 */

public class DbManager {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myDb = database.getReference("cameras");

    public List<CameraInfos> getCameras() {
        final List<CameraInfos> cameras = new ArrayList<>();
        final ValueEventListener cameraValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> cameraSnapshot = dataSnapshot.getChildren();
                for (DataSnapshot camera : cameraSnapshot) {
                    CameraInfos cameraInfos = camera.getValue(CameraInfos.class);
                    System.out.println(cameraInfos);
                    cameras.add(cameraInfos);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
            }
        };
        myDb.addValueEventListener(cameraValueEventListener);

        return cameras;
    }

    public void insertCamera(String nameCamera, String ipCamera, String port, String password) {

        String id = myDb.push().getKey();
        CameraInfos cameraInfos = new CameraInfos(nameCamera, ipCamera, port, password, id);
        myDb.child(id).setValue(cameraInfos).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("Insertion", "Done");
            }
        });
    }
    public void updateCamera(String id, String nameCamera, String ipCamera, String port, String password) {
//        CameraInfos cameraInfos = new CameraInfos(nameCamera, ipCamera, port, password);
        HashMap cameraInfos = new HashMap();
        cameraInfos.put("name", nameCamera);
        cameraInfos.put("port", port);
        cameraInfos.put("ip", ipCamera);
        cameraInfos.put("password", password);

        myDb.child(id).updateChildren(cameraInfos).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                Log.d("Update", "Complete");
            }
        });
    }

    public void deleteCamera(String id) {
        myDb.child(id).removeValue();
    }
}
