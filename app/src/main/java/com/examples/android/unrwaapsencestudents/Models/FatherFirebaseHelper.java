package com.examples.android.unrwaapsencestudents.Models;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

import static android.support.constraint.Constraints.TAG;

public class FatherFirebaseHelper {
    private static FatherFirebaseHelper fatherFirebaseHelper;
    private FirebaseDatabase database;
    private DatabaseReference fatherDBRef = null;
    private FatherFirebaseHelper(Context context){
        FirebaseApp.initializeApp(context);
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        database = FirebaseDatabase.getInstance();
        fatherDBRef = database.getReference("Fathers");

       // fatherDBRef.keepSynced(true);
    }
    public static FatherFirebaseHelper getInstance(Context context) {
        if(fatherFirebaseHelper==null)
            fatherFirebaseHelper=new FatherFirebaseHelper(context);
        return fatherFirebaseHelper;
    }
    public void addFather(Father father){
        fatherDBRef.child(father.getFamilyID()).setValue(father);
    }
    public void removeFather(String familyID){
        fatherDBRef.child(familyID).removeValue();
    }
//    Father father;
//    public Father getFatherInfo(String familyID){
//        father=new Father();
//        fatherDBRef.child(familyID);
//        fatherDBRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot postSnapshot:dataSnapshot.getChildren()) {
//                    father=postSnapshot.getValue(Father.class);
//                }
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//        return father;
//    }
    public Task<Father> getFatherInfo(String username) {
        final TaskCompletionSource<Father> tcs = new TaskCompletionSource<>();
        fatherDBRef.child(username)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Father father = dataSnapshot.getValue(Father.class);
                        tcs.setResult(father);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        tcs.setException(new IOException(TAG, databaseError.toException()));
                    }
                });
        return tcs.getTask();
    }

    public void addChild(String childUNumber, String familyID) {
        fatherDBRef.child(familyID).child("Children").child(childUNumber).setValue(true);
    }
    public void removeChild(String childUNumber, String familyID) {
        fatherDBRef.child(familyID).child("Children").child(childUNumber).removeValue();
    }
}
