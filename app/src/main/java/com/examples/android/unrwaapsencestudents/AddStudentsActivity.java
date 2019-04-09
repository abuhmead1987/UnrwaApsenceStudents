package com.examples.android.unrwaapsencestudents;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.examples.android.unrwaapsencestudents.Adapters.FatherChildrenListAdapter;
import com.examples.android.unrwaapsencestudents.Adapters.StudentsListAdapter;
import com.examples.android.unrwaapsencestudents.Fragments.AddStudentFragment;
import com.examples.android.unrwaapsencestudents.Models.Father;
import com.examples.android.unrwaapsencestudents.Models.Student;
import com.examples.android.unrwaapsencestudents.Utils.Constants;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;


public class AddStudentsActivity extends AppCompatActivity {
    Father father;
    LinkedList<Student> studentsList = new LinkedList<>();
    FatherChildrenListAdapter fatherChildrenListAdapter;
    private StudentsListAdapter studentsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_students);
        father = (Father) getIntent().getSerializableExtra(Constants.FATHER_INFO);
        if (father != null) {
            ((TextView) findViewById(R.id.tv_fatherName)).setText(father.getName());
            ((TextView) findViewById(R.id.tv_FamilyID)).setText(father.getFamilyID());
        }
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddStudentFragment dialogFragment = new AddStudentFragment();
                Bundle b = new Bundle();
                b.putString(Constants.FAMILY_ID, father.getFamilyID());
                dialogFragment.setArguments(b);
                dialogFragment.show(getSupportFragmentManager().beginTransaction(), "fff");
            }
        });
        FirebaseApp.initializeApp(this);
        //  setupFathersRecyclerView();
        getStudentsForFather(father.getFamilyID());
    }

    private void setupFathersRecyclerView() {
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Students");
        FirebaseRecyclerOptions<Student> options =
                new FirebaseRecyclerOptions.Builder<Student>()
                        .setQuery(query, Student.class)
                        .build();
        studentsListAdapter = new StudentsListAdapter(options, this, getSupportFragmentManager());
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.studentsList);
        recyclerView.setAdapter(studentsListAdapter);
        recyclerView.setHasFixedSize(true);
    }


    public void getStudentsForFather(String fatherID) {
        DatabaseReference childrenRef = FirebaseDatabase.getInstance().getReference("Fathers")
                .child(fatherID).child("Children");

        childrenRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DatabaseReference studentsRef = FirebaseDatabase.getInstance()
                        .getReference()
                        .child("Students");
                studentsList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    studentsRef.child(snapshot.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            studentsList.add(dataSnapshot.getValue(Student.class));
                            fatherChildrenListAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        fatherChildrenListAdapter = new FatherChildrenListAdapter(studentsList, father.getFamilyID(), getSupportFragmentManager());
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.studentsList);
        recyclerView.setAdapter(fatherChildrenListAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
