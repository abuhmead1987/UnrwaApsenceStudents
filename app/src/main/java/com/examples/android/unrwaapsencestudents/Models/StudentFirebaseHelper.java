package com.examples.android.unrwaapsencestudents.Models;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
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
    private Context context;

    private StudentFirebaseHelper(Context context) {
        FirebaseApp.initializeApp(context);
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        database = FirebaseDatabase.getInstance();
        childRef = database.getReference("Students");
        this.context = context;
        // childRef.keepSynced(true);
    }

    public static StudentFirebaseHelper getInstance(Context context) {
        if (thisFirebaseHelper == null)
            thisFirebaseHelper = new StudentFirebaseHelper(context);
        return thisFirebaseHelper;
    }

    public void addStudent(final Student student, final String familyID) {
        childRef.child(student.getuNumber()).setValue(student).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                FatherFirebaseHelper.getInstance(context).addChild(student.getuNumber(), familyID);
            }
        });
    }

    public void removeChild(String studentUNumber) {
        childRef.child(studentUNumber).removeValue();
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