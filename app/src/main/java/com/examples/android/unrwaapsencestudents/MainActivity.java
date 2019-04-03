package com.examples.android.unrwaapsencestudents;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startActivity(View view) {
        switch (view.getId()){
            case R.id.btn_addFamily:
                startActivity(new Intent(this,FathersActivity.class));
                break;
            case R.id.btn_addStudent:
                break;
        }
    }
}
