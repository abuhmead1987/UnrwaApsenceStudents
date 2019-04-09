package com.examples.android.unrwaapsencestudents;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.examples.android.unrwaapsencestudents.Adapters.FathersListAdapter;
import com.examples.android.unrwaapsencestudents.Fragments.AddFatherFragment;
import com.examples.android.unrwaapsencestudents.Models.Father;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class FathersActivity extends AppCompatActivity {
    private FathersListAdapter fathersListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fathers);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(FathersActivity.this,AddStudentsActivity.class));
                AddFatherFragment dialogFragment = new AddFatherFragment();
                dialogFragment.show(getSupportFragmentManager().beginTransaction(), "fff");
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //endregion
        FirebaseApp.initializeApp(this);
        setupFathersRecyclerView();
    }

    private void setupFathersRecyclerView() {
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Fathers");
        FirebaseRecyclerOptions<Father> options =
                new FirebaseRecyclerOptions.Builder<Father>()
                        .setQuery(query, Father.class)
                        .build();
        fathersListAdapter=new FathersListAdapter(options, this, getSupportFragmentManager());
        RecyclerView recyclerView= (RecyclerView)findViewById(R.id.rv_fathersList);
        recyclerView.setAdapter(fathersListAdapter);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        fathersListAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        fathersListAdapter.stopListening();
    }
}
