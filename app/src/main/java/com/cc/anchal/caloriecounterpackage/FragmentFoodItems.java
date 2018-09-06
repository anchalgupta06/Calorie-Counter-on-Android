package com.cc.anchal.caloriecounterpackage;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anchal on 30-09-2017.
 */

public class FragmentFoodItems extends Fragment {

    Spinner spinner;
    TextView cal,pro,carb,fat;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_food_items, container, false);

        cal = (TextView) rootView.findViewById(R.id.calorie);
        pro=(TextView)rootView.findViewById(R.id.protein);
        carb=(TextView)rootView.findViewById(R.id.carbs);
        fat=(TextView)rootView.findViewById(R.id.fat);
        List<String> spinnerOptions=new ArrayList<>();
        spinnerOptions.add("Select");
        spinnerOptions.add("Apple (1 medium size)");
        spinnerOptions.add("Almonds (1 oz)");
        spinnerOptions.add("Banana (1 medium size)");
        spinnerOptions.add("Cashew (1 oz)");
        spinnerOptions.add("Chapati (7\" diameter)");
        spinnerOptions.add("Chole - Chickpeas (100g)");
        spinnerOptions.add("Curd (100g)");
        spinnerOptions.add("Dal (1 cup)");
        spinnerOptions.add("Milk (250ml)");
        spinnerOptions.add("Oats (40g)");
        spinnerOptions.add("Orange (1 medium size)");
        spinnerOptions.add("Paneer (100g)");
        spinnerOptions.add("Paratha (1 medium size)");
        spinnerOptions.add("Peanuts (1 oz)");
        spinnerOptions.add("Peanut butter (1 spoon)");
        spinnerOptions.add("Pizza (1 medium size)");
        spinnerOptions.add("Pasta (100g)");
        spinnerOptions.add("Rajma - Kidney beans (100g)");
        spinnerOptions.add("Rice (1 cup)");
        spinnerOptions.add("Upma (1 cup)");
        spinnerOptions.add("Veggies cooked (1 cup)");
        spinnerOptions.add("Yogurt Greek non-fat (100g)");

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
                        cal.setText("95");
                        pro.setText("0.8");
                        carb.setText("25");
                        fat.setText("0.3");
                        break;
                    case 2:
                        cal.setText("163");
                        pro.setText("6");
                        carb.setText("6");
                        fat.setText("14");
                        break;
                    case 3:
                        cal.setText("105");
                        pro.setText("1.3");
                        carb.setText("27");
                        fat.setText("0.4");
                        break;
                    case 4:
                        cal.setText("157");
                        pro.setText("5");
                        carb.setText("9");
                        fat.setText("12");
                        break;
                    case 5:
                        cal.setText("106");
                        pro.setText("4");
                        carb.setText("22");
                        fat.setText("1");
                        break;
                    case 6:
                        cal.setText("364");
                        pro.setText("19");
                        carb.setText("61");
                        fat.setText("6");
                        break;
                    case 7:
                        cal.setText("98");
                        pro.setText("11");
                        carb.setText("3.4");
                        fat.setText("4.3");
                        break;
                    case 8:
                        cal.setText("200");
                        pro.setText("11");
                        carb.setText("28");
                        fat.setText("4");
                        break;
                    case 9:
                        cal.setText("148");
                        pro.setText("8");
                        carb.setText("12");
                        fat.setText("8");
                        break;
                    case 10:
                        cal.setText("151");
                        pro.setText("7");
                        carb.setText("27");
                        fat.setText("2.6");
                        break;
                    case 11:
                        cal.setText("47");
                        pro.setText("0.9");
                        carb.setText("11.8");
                        fat.setText("0.1");
                        break;
                    case 12:
                        cal.setText("265");
                        pro.setText("18.3");
                        carb.setText("1.2");
                        fat.setText("20.8");
                        break;
                    case 13:
                        cal.setText("349");
                        pro.setText("6");
                        carb.setText("44");
                        fat.setText("17");
                        break;
                    case 14:
                        cal.setText("161");
                        pro.setText("7");
                        carb.setText("4.6");
                        fat.setText("14");
                        break;
                    case 15:
                        cal.setText("94");
                        pro.setText("4");
                        carb.setText("3");
                        fat.setText("8");
                        break;
                    case 16:
                        cal.setText("1680");
                        pro.setText("64");
                        carb.setText("200");
                        fat.setText("68");
                        break;
                    case 17:
                        cal.setText("131");
                        pro.setText("5");
                        carb.setText("25");
                        fat.setText("1.1");
                        break;
                    case 18:
                        cal.setText("127");
                        pro.setText("9");
                        carb.setText("23");
                        fat.setText("0.5");
                        break;
                    case 19:
                        cal.setText("206");
                        pro.setText("4.3");
                        carb.setText("45");
                        fat.setText("0.4");
                        break;
                    case 20:
                        cal.setText("322");
                        pro.setText("11");
                        carb.setText("59");
                        fat.setText("6");
                        break;
                    case 21:
                        cal.setText("147");
                        pro.setText("5");
                        carb.setText("23");
                        fat.setText("4");
                        break;
                    case 22:
                        cal.setText("59");
                        pro.setText("10");
                        carb.setText("3.6");
                        fat.setText("0.4");
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
