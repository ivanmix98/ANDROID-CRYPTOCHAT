package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnItemSelectedListener {

    private ArrayAdapter<String> tiposAdapter;
    private Spinner spinner;
    private String tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inici);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);


    }

    public void nextPage(View v){
        Intent intent = new Intent(this, ClientXatActivity.class);
        intent.putExtra("tipo",tipo);
        startActivity(intent);
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        tipo = parent.getItemAtPosition(pos).toString();
        System.out.println(tipo);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}

