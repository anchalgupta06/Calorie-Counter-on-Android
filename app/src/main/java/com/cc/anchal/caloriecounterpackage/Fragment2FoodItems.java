package com.cc.anchal.caloriecounterpackage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anchal on 30-09-2017.
 */

public class Fragment2FoodItems extends Fragment {

    Spinner spinner;
    TextView cal,pro,carb,fat;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment2_food_items, container, false);

        cal = (TextView) rootView.findViewById(R.id.calorie);
        pro=(TextView)rootView.findViewById(R.id.protein);
        carb=(TextView)rootView.findViewById(R.id.carbs);
        fat=(TextView)rootView.findViewById(R.id.fat);
        List<String> spinnerOptions=new ArrayList<>();
        spinnerOptions.add("Select");
        spinnerOptions.add("Boiled Egg (1 large)");
        spinnerOptions.add("Chicken - boneless(100g)");
        spinnerOptions.add("Fish (100g)");
        spinnerOptions.add("Lamb (100g)");
        spinnerOptions.add("Omelette (1 egg)");
        spinnerOptions.add("Pork (100g)");
        ArrayAdapter<String> dataAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,spinnerOptions);
        spinner=(Spinner)rootView.findViewById(R.id.spinner);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        cal.setText("");
                        pro.setText("");
                        carb.setText("");
                        fat.setText("");
                        break;
                    case 1:
                        cal.setText("78");
                        pro.setText("6");
                        carb.setText("0.6");
                        fat.setText("5");
                        break;
                    case 2:
                        cal.setText("165");
                        pro.setText("30");
                        carb.setText("0");
                        fat.setText("4");
                        break;
                    case 3:
                        cal.setText("109");
                        pro.setText("22.6");
                        carb.setText("0.28");
                        fat.setText("1.18");
                        break;
                    case 4:
                        cal.setText("240");
                        pro.setText("26");
                        carb.setText("0");
                        fat.setText("14");
                        break;
                    case 5:
                        cal.setText("94");
                        pro.setText("6");
                        carb.setText("0.4");
                        fat.setText("7");
                        break;
                    case 6:
                        cal.setText("297");
                        pro.setText("25.7");
                        carb.setText("0");
                        fat.setText("20.8");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return rootView;
    }

}
