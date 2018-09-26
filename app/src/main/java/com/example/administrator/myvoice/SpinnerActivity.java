package com.example.administrator.myvoice;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class SpinnerActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    Button btn1;
    Spinner spinner01, spinner02;

    String a1 = "";
    String a2 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spinner);

        btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(this);

        final String[] items = {"1형", "2형", "3형", "4형", "5형"};

        final String[] items1 ={"1-1형", "1-2형", "1-3형", "1-4형"};
        final String[] items2 ={"2-1형", "2-2형", "2-3형", "2-4형"};
        final String[] items3 ={"3-1형", "3-2형", "3-3형", "3-4형"};
        final String[] items4 ={"4-1형", "4-2형", "4-3형", "4-4형"};
        final String[] items5 ={"5-1형", "5-2형", "5-3형", "5-4형"};

        final Spinner spinner01 = (Spinner) findViewById(R.id.spinner01);
        spinner01.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        final Spinner spinner02 = (Spinner) findViewById(R.id.spinner02);
        spinner02.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner01.setAdapter(adapter);
        spinner01.setSelection(0);
        a1 = (String) spinner01.getSelectedItem();
        spinner01.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinner01.getItemAtPosition(position) == "1형") {
                    // 스피너 칸, 폰트, 크기 바꾸자!
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(SpinnerActivity.this, android.R.layout.simple_spinner_item, items1);
                    adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                    spinner02.setAdapter(adapter2);
                    spinner02.setSelection(0);
                } else if (spinner01.getItemAtPosition(position) == "2형") {
                    ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(SpinnerActivity.this, android.R.layout.simple_spinner_item, items2);
                    adapter3.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                    spinner02.setAdapter(adapter3);
                    spinner02.setSelection(0);
                } else if (spinner01.getItemAtPosition(position) == "3형") {
                    ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(SpinnerActivity.this, android.R.layout.simple_spinner_item, items3);
                    adapter4.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                    spinner02.setAdapter(adapter4);
                    spinner02.setSelection(0);
                } else if (spinner01.getItemAtPosition(position) == "4형") {
                    ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(SpinnerActivity.this, android.R.layout.simple_spinner_item, items4);
                    adapter5.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                    spinner02.setAdapter(adapter5);
                    spinner02.setSelection(0);
                } else if (spinner01.getItemAtPosition(position) == "5형") {
                    ArrayAdapter<String> adapter6 = new ArrayAdapter<String>(SpinnerActivity.this, android.R.layout.simple_spinner_item, items5);
                    adapter6.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                    spinner02.setAdapter(adapter6);
                    spinner02.setSelection(0);
                }
                a1 = (String) spinner01.getItemAtPosition(position);
                a2 = (String) spinner02.getSelectedItem();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1:
                String AA = a1 + ", " + a2 ;
                Intent returnIntent = new Intent();
                returnIntent.putExtra("AA",AA);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
