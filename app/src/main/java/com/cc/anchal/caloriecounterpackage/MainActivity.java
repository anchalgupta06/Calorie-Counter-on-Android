package com.cc.anchal.caloriecounterpackage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button sgcal=(Button)findViewById(R.id.sgcal);
        Button filist=(Button)findViewById(R.id.filist);
        Button cons=(Button)findViewById(R.id.cons);

        sgcal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,SuggestedCal.class);
                startActivity(i);
            }
        });

        filist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,FoodItems.class);
                startActivity(i);
            }
        });

        cons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,Consumption.class);
                startActivity(i);
            }
        });
    }
}
