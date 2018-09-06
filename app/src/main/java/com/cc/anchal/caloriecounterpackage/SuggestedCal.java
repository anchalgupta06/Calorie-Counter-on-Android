package com.cc.anchal.caloriecounterpackage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SuggestedCal extends AppCompatActivity {

    EditText age,weight,height;
    Spinner spinner;
    RadioGroup genderGroup;
    RadioButton genderButton;
    Button submit;
    TextView res,text_gain,text_lose;
    String atv;
    Double result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_cal);
        age=(EditText)findViewById(R.id.age);
        weight=(EditText)findViewById(R.id.weight);
        height=(EditText)findViewById(R.id.height);
        spinner=(Spinner)findViewById(R.id.spinner);
        genderGroup=(RadioGroup)findViewById(R.id.radioGroup);
        submit=(Button)findViewById(R.id.submit);
        res=(TextView)findViewById(R.id.res);
        text_gain=(TextView)findViewById(R.id.text1);
        text_lose=(TextView)findViewById(R.id.text2);
        List<String> spinnerOptions=new ArrayList<>();
        spinnerOptions.add("Low");
        spinnerOptions.add("Moderate");
        spinnerOptions.add("High");
        ArrayAdapter<String> dataAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,spinnerOptions);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                atv=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int selectedID = genderGroup.getCheckedRadioButtonId();
                    genderButton = (RadioButton) findViewById(selectedID);
                    Integer ag = Integer.parseInt(age.getText().toString());
                    Double ht = Double.parseDouble(height.getText().toString());
                    Double wt = Double.parseDouble(weight.getText().toString());
                    Double atv_val;
                    if (atv.equals("Low")) {
                        atv_val = 1.2;
                    } else if (atv.equals("Moderate")) {
                        atv_val = 1.5;
                    } else {
                        atv_val = 1.7;
                    }
                    if (genderButton.getText().toString().equals("Male")) {
                        result = (10 * wt + 6.25 * ht - 5 * ag + 5) * atv_val; //10 x weight (kg) + 6.25 x height (cm) - 5 x age (y) + 5
                    } else {
                        result = (10 * (wt) + 6.25 * (ht) - 5 * ag - 161) * atv_val; //10 x weight (kg) + 6.25 x height (cm) - 5 x age (y) - 161
                    }
                    String ans = String.valueOf(result.intValue());
                    res.setText(ans + " Calories per day");
                    text_gain.setText("add calories to gain weight");
                    text_lose.setText("subtract calories to lose weight");
                }catch (Exception e)
                {
                    Toast.makeText(SuggestedCal.this,"Invalid Input",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
