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

public class StudentFirebaseHelper {
    private static StudentFirebaseHelper thisFirebaseHelper;
    private FirebaseDatabase database;
    private DatabaseReference childRef = null;
    private StudentFirebaseHelper(Context context){
        FirebaseApp.initializeApp(context);
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        database = FirebaseDatabase.getInstance();
        childRef = database.getReference("Students");
        // childRef.keepSynced(true);
    }
    public static StudentFirebaseHelper getInstance(Context context) {
        if(thisFirebaseHelper==null)
            thisFirebaseHelper=new StudentFirebaseHelper(context);
        return thisFirebaseHelper;
    }
    public void addStudent(Student student){
        childRef.child(student.getuNumber()).setValue(student);
    }
    public void removeChild(String familyID){
        childRef.child(familyID).removeValue();
    }
    public Task<Student> getStudentInfo(String studentID) {
        final TaskCompletionSource<Student> tcs = new TaskCompletionSource<>();
        childRef.child(studentID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Student student = dataSnapshot.getValue(Student.class);
                        tcs.setResult(student);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        tcs.setException(new IOException(TAG, databaseError.toException()));
                    }
                });
        return tcs.getTask();
    }
}