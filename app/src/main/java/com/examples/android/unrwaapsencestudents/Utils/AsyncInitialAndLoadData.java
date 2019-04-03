package com.examples.android.unrwaapsencestudents.Utils;

import android.os.AsyncTask;
import android.view.View;

import com.google.firebase.database.DatabaseReference;

public class AsyncInitialAndLoadData extends AsyncTask<DatabaseReference,Integer, Void> implements View.OnClickListener{
    @Override
    protected Void doInBackground(DatabaseReference... databaseReferences) {
        return null;
    }

    @Override
    public void onClick(View view) {

    }
}
