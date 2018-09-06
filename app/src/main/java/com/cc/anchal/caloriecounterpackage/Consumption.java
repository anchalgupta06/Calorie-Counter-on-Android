package com.cc.anchal.caloriecounterpackage;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Consumption extends AppCompatActivity {
    Button sel_date_button,add,undo,macro_button,consumed_items;
    EditText sugg_cal;
    EditText add_cal;
    Spinner spinner;
    PieChart pieChart;
    TextView sel_date;
    Calendar dateSelected = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date;
    SimpleDateFormat dateFormatter;
    Calendar myCalendar = dateSelected;

    double sg_cal,food_cal=0,pro=0,carb=0,fat=0,cons_cal,recent_add=0,addcal=0,prot=0,carbs=0,fats=0;
    String dt,item;

    HelperClass dbHelper;
    SQLiteDatabase database;
    public Consumption()
    {
        dbHelper=new HelperClass(this);
    }

    public void open() throws SQLException
    {
        database= dbHelper.getWritableDatabase();
    }

    public void close()
    {
        dbHelper.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumption);

        Calendar c=Calendar.getInstance();
        String myFormat = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String curr_date=sdf.format(c.getTime());
        sel_date=(TextView) findViewById(R.id.sel_date);
        sel_date.setText(curr_date);

        add_cal=(EditText)findViewById(R.id.Addcal);
        add_cal.setHint("Additional Calories(if any)");
        sugg_cal=(EditText)findViewById(R.id.sugg_cal);

        pieChart=(PieChart)findViewById(R.id.piechart);
        pieChart.setDescription(null);
        pieChart.setHoleRadius(40);
        pieChart.setCenterText("Consumption");
        pieChart.setTransparentCircleAlpha(0);

        open();
        Cursor cursor=database.rawQuery(("SELECT * FROM User_Data WHERE date=?"),new String[]{curr_date});
        if ( cursor.moveToFirst() ) {
            sugg_cal.setText(cursor.getString(1));
            sg_cal=new Double(cursor.getString(1));
            cons_cal=new Double(cursor.getString(2));
            addDataSet(sg_cal,cons_cal);
        } else {

            if (pieChart.getData()!=null)
            {
                pieChart.clearValues();
                pieChart.clear();
            }
            Toast.makeText(Consumption.this, "Data not found", Toast.LENGTH_SHORT).show();
        }
        close();

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();

                //accessing databse
                open();
                Cursor cursor=database.rawQuery(("SELECT * FROM User_Data WHERE date=?"),new String[]{sel_date.getText().toString()});
                if ( cursor.moveToFirst() ) {
                    sugg_cal.setText(cursor.getString(1));
                    sg_cal=new Double(cursor.getString(1));
                    cons_cal=new Double(cursor.getString(2));
                    addDataSet(sg_cal,cons_cal);
                } else {
                    Log.d("DB", String.valueOf(cursor.getCount()));
                    sugg_cal.setText("");
                    if (pieChart.getData()!=null)
                    {
                        pieChart.clearValues();
                        pieChart.clear();
                    }
                    Toast.makeText(Consumption.this, "Data not found", Toast.LENGTH_SHORT).show();
                }
                close();
            }
        };
        sel_date_button=(Button)findViewById(R.id.sel_date_button);
        sel_date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Consumption.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        List<String> spinnerOptions=new ArrayList<>();
        spinnerOptions.add("Select Food Item");
        spinnerOptions.add("Apple (1 medium size)");
        spinnerOptions.add("Almonds (1 oz)");
        spinnerOptions.add("Banana (1 medium size)");
        spinnerOptions.add("Boiled Egg (1 large)");
        spinnerOptions.add("Cashew (1 oz)");
        spinnerOptions.add("Chapati (7\" diameter)");
        spinnerOptions.add("Chicken - boneless(100g)");
        spinnerOptions.add("Chole - Chickpeas (100g)");
        spinnerOptions.add("Curd (100g)");
        spinnerOptions.add("Dal (1 cup)");
        spinnerOptions.add("Fish (100g)");
        spinnerOptions.add("Lamb (100g)");
        spinnerOptions.add("Milk (250ml)");
        spinnerOptions.add("Oats (40g)");
        spinnerOptions.add("Omelette (1 egg)");
        spinnerOptions.add("Orange (1 medium size)");
        spinnerOptions.add("Paneer (100g)");
        spinnerOptions.add("Paratha (1 medium size)");
        spinnerOptions.add("Peanuts (1 oz)");
        spinnerOptions.add("Pizza (1 medium size)");
        spinnerOptions.add("Pasta (100g)");
        spinnerOptions.add("Pork (100g)");
        spinnerOptions.add("Rajma (100g)");
        spinnerOptions.add("Rice (1 cup)");
        spinnerOptions.add("Upma (1 cup)");
        spinnerOptions.add("Veggies cooked (1 cup)");
        spinnerOptions.add("Yogurt (100g)");

        ArrayAdapter<String> dataAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,spinnerOptions);
        spinner=(Spinner)findViewById(R.id.spinner);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                        item=null;
                        food_cal=0;
                        pro=0;
                        carb=0;
                        fat=0;
                        break;
                    case 1:
                        item="Apple";
                        food_cal=95;
                        pro=0.8;
                        carb=25;
                        fat=0.3;
                        break;
                    case 2:
                        item="Almonds";
                        food_cal=163;
                        pro=6;
                        carb=6;
                        fat=14;
                    case 3:
                        item="Banana";
                        food_cal=105;
                        pro=1.3;
                        carb=27;
                        fat=0.4;
                        break;
                    case 4:
                        item="Boiled egg";
                        food_cal=78;
                        pro=6;
                        carb=0.6;
                        fat=5;
                        break;
                    case 5:
                        item="Cashew";
                        food_cal=157;
                        pro=5;
                        carb=9;
                        fat=12;
                        break;
                    case 6:
                        item="Chapati";
                        food_cal=106;
                        pro=4;
                        carb=22;
                        fat=1;
                        break;
                    case 7:
                        item="Chicken";
                        food_cal=165;
                        pro=30;
                        carb=0;
                        fat=4;
                        break;
                    case 8:
                        item="Chole";
                        food_cal=364;
                        pro=19;
                        carb=61;
                        fat=6;
                        break;
                    case 9:
                        item="Curd";
                        food_cal=98;
                        pro=11;
                        carb=3.4;
                        fat=4.3;
                        break;
                    case 10:
                        item="Dal";
                        food_cal=200;
                        pro=11;
                        carb=28;
                        fat=4;
                        break;
                    case 11:
                        item="Fish";
                        food_cal=109;
                        pro=22.6;
                        carb=0.28;
                        fat=1.18;
                        break;
                    case 12:
                        item="Lamb";
                        food_cal=240;
                        pro=26;
                        carb=0;
                        fat=14;
                        break;
                    case 13:
                        item="Milk";
                        food_cal=148;
                        pro=8;
                        carb=12;
                        fat=8;
                        break;
                    case 14:
                        item="Oats";
                        food_cal=151;
                        pro=7;
                        carb=27;
                        fat=2.6;
                        break;
                    case 15:
                        item="Omelette";
                        food_cal=94;
                        pro=6;
                        carb=0.4;
                        fat=7;
                        break;
                    case 16:
                        item="Orange";
                        food_cal=47;
                        pro=0.9;
                        carb=11.8;
                        fat=0.1;
                        break;
                    case 17:
                        item="Paneer";
                        food_cal=265;
                        pro=18.3;
                        carb=1.2;
                        fat=20.8;
                        break;
                    case 18:
                        item="Paratha";
                        food_cal=349;
                        pro=6;
                        carb=44;
                        fat=17;
                        break;
                    case 19:
                        item="Peanuts";
                        food_cal=161;
                        pro=7;
                        carb=4.6;
                        fat=14;
                        break;
                    case 20:
                        item="Pizza";
                        food_cal=1680;
                        pro=64;
                        carb=200;
                        fat=68;
                        break;
                    case 21:
                        item="Pasta";
                        food_cal=131;
                        pro=5;
                        carb=25;
                        fat=1.1;
                        break;
                    case 22:
                        item="Pork";
                        food_cal=297;
                        pro=25.7;
                        carb=0;
                        fat=20.8;
                        break;
                    case 23:
                        item="Rajma";
                        food_cal=127;
                        pro=9;
                        carb=23;
                        fat=0.5;
                        break;
                    case 24:
                        item="Rice";
                        food_cal=206;
                        pro=4.3;
                        carb=45;
                        fat=0.4;
                        break;
                    case 25:
                        item="Upma";
                        food_cal=322;
                        pro=11;
                        carb=59;
                        fat=6;
                        break;
                    case 26:
                        item="Veggies cooked";
                        food_cal=147;
                        pro=5;
                        carb=23;
                        fat=4;
                        break;
                    case 27:
                        item="Yogurt";
                        food_cal=59;
                        pro=10;
                        carb=3.6;
                        fat=0.4;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //add button
        add=(Button)findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (add_cal.getText().toString() == null || add_cal.getText().toString() == "")
                        addcal = 0;
                    else addcal = Double.parseDouble(add_cal.getText().toString());
                }catch (Exception e)
                {
                    addcal=0;
                }

                sugg_cal=(EditText)findViewById(R.id.sugg_cal);
                sel_date=(TextView)findViewById(R.id.sel_date);
                if(sel_date.getText().toString()==null)
                {
                    Toast.makeText(Consumption.this, "Select date", Toast.LENGTH_SHORT).show();
                }else{
                    if(sugg_cal.getText().toString().matches(""))
                    {
                        Toast.makeText(Consumption.this, "Enter suggested calories", Toast.LENGTH_SHORT).show();
                    }else {
                        //inserting data
                        try {
                            sg_cal = new Double(sugg_cal.getText().toString());
                            dt = new String(sel_date.getText().toString());
                            if (dt.equals("")) {
                                Toast.makeText(Consumption.this, "Select date", Toast.LENGTH_SHORT).show();
                            } else {
                                open();
                                Cursor cursor = database.rawQuery(("SELECT * FROM User_Data WHERE date=?"), new String[]{dt});
                                if (cursor.moveToFirst()) {
                                    if (item == null) {
                                        cons_cal = new Double(cursor.getString(2));
                                        cons_cal = cons_cal + addcal;
                                        recent_add = addcal;
                                        pro=0;carb=0;fat=0;
                                        ContentValues values = new ContentValues();
                                        values.put("consumed_calories", cons_cal);
                                        values.put("protein", prot);
                                        values.put("carbs", carbs);
                                        values.put("fat", fats);
                                        int x = database.update("User_Data", values, "date=?", new String[]{dt});
                                        if (x != 0) {
                                            Toast.makeText(Consumption.this, "Data updated", Toast.LENGTH_SHORT).show();
                                            addDataSet(sg_cal, cons_cal);
                                            add_cal.setText("");
                                        } else {
                                            Toast.makeText(Consumption.this, "Data not found", Toast.LENGTH_SHORT).show();
                                        }


                                    } else {
                                    cons_cal = new Double(cursor.getString(2));
                                    cons_cal = cons_cal + food_cal + addcal;
                                    prot = new Double(cursor.getString(3));
                                    carbs = new Double(cursor.getString(4));
                                    fats = new Double(cursor.getString(5));
                                    prot = prot + pro;
                                    carbs = carbs + carb;
                                    fats = fats + fat;
                                    recent_add = food_cal + addcal;
                                    //update database
                                    ContentValues values = new ContentValues();
                                    values.put("consumed_calories", cons_cal);
                                    values.put("protein", prot);
                                    values.put("carbs", carbs);
                                    values.put("fat", fats);
                                    int x = database.update("User_Data", values, "date=?", new String[]{dt});
                                    if (x != 0) {
                                        Toast.makeText(Consumption.this, "Data updated", Toast.LENGTH_SHORT).show();
                                        addDataSet(sg_cal, cons_cal);
                                        add_cal.setText("");
                                    } else {
                                        Toast.makeText(Consumption.this, "Data not found", Toast.LENGTH_SHORT).show();
                                    }

                                    Cursor cursor2 = database.rawQuery(("SELECT * FROM User_Consumption WHERE date=? AND item=?"), new String[]{dt, item});
                                    if (cursor2.moveToFirst()) {
                                        double q = new Double(cursor2.getString(2));
                                        q = q + 1;
                                        ContentValues values2 = new ContentValues();
                                        values2.put("quantity", q);
                                        database.update("User_Consumption", values2, "date=? and item=?", new String[]{dt, item});
                                    } else {
                                        if (item != null) {
                                            ContentValues values2 = new ContentValues();
                                            values2.put("date", dt);
                                            values2.put("item", item);
                                            values2.put("quantity", 1);
                                            database.insert("User_Consumption", null, values2);
                                        }
                                    }
                                }

                                } else {
                                    //adding new data
                                    ContentValues values = new ContentValues();
                                    values.put("date", dt);
                                    values.put("suggested_calories", sg_cal);
                                    values.put("consumed_calories", food_cal+addcal);
                                    values.put("protein",pro);
                                    values.put("carbs",carb);
                                    values.put("fat",fat);
                                    long x=database.insert("User_Data", null, values);
                                    if(x!=0) Toast.makeText(Consumption.this, "Data Added", Toast.LENGTH_SHORT).show();
                                    else Toast.makeText(Consumption.this, "Error", Toast.LENGTH_SHORT).show();
                                    addDataSet(sg_cal, food_cal+addcal);
                                    recent_add=food_cal+addcal;
                                    add_cal.setText("");

                                    if(item!=null) {
                                        ContentValues values2 = new ContentValues();
                                        values2.put("date", dt);
                                        values2.put("item", item);
                                        values2.put("quantity", 1);
                                        database.insert("User_Consumption", null, values2);
                                    }
                                }
                                close();
                            }
                        }
                        catch(NumberFormatException e)
                            {
                                e.printStackTrace();
                                Toast.makeText(Consumption.this, "Invalid entry", Toast.LENGTH_SHORT).show();
                            }
                    }
                }
            }
        });

        //recent_add=food_cal+addcal;
        undo=(Button)findViewById(R.id.undo);
        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(recent_add>0)
                    {
                        open();
                        Cursor cursor = database.rawQuery(("SELECT * FROM User_Data WHERE date=?"), new String[]{dt});
                        if (cursor.moveToFirst()) {
                            cons_cal = new Double(cursor.getString(2));
                            cons_cal = cons_cal - recent_add;
                            prot = new Double(cursor.getString(3))-pro;
                            carbs = new Double(cursor.getString(4))-carb;
                            fats = new Double(cursor.getString(5))-fat;
                        //update database
                            ContentValues values = new ContentValues();
                            values.put("consumed_calories", cons_cal);
                            values.put("protein",prot);
                            values.put("carbs",carbs);
                            values.put("fat",fats);
                            int x = database.update("User_Data", values, "date=?", new String[]{dt});

                            Cursor cursor2 = database.rawQuery(("SELECT * FROM User_Consumption WHERE date=? AND item=?"), new String[]{dt,item});
                            if(cursor2.moveToFirst()) {
                                double q = new Double(cursor2.getString(2));
                                q = q - 1;
                                if (q > 0) {
                                    ContentValues values2 = new ContentValues();
                                    values2.put("quantity", q);
                                    database.update("User_Consumption", values2, "date=? and item=?", new String[]{dt, item});
                                } else {
                                    database.delete("User_Consumption", "date=? and item=?", new String[]{dt, item});
                                }
                            }

                            if (x != 0) {
                                Toast.makeText(Consumption.this, "Data updated", Toast.LENGTH_SHORT).show();
                                addDataSet(sg_cal, cons_cal);
                                close();
                                recent_add=0;
                            }
                        }
                    }else Toast.makeText(Consumption.this,"Nothing to undo",Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(Consumption.this,"Error",Toast.LENGTH_SHORT).show();
                }
            }
        });

        macro_button=(Button)findViewById(R.id.macro_button);
        macro_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Consumption.this,MacroDetails.class);
                i.putExtra("key", sel_date.getText().toString());
                startActivity(i);
            }
        });

        consumed_items=(Button)findViewById(R.id.consumed_items);
        consumed_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Consumption.this,ConsumedItems.class);
                i.putExtra("key", sel_date.getText().toString());
                startActivity(i);
            }
        });

    }

    private void addDataSet(double sg_cal,double cons_cal) {
        Float s=new Float(sg_cal);
        Float c=new Float(cons_cal);
        ArrayList<PieEntry> info=new ArrayList<>();
        info.add(new PieEntry(c,"Consumed"));
        info.add(new PieEntry(s-c,"Needed"));
        PieDataSet pieDataSet=new PieDataSet(info,"");
        pieDataSet.setSliceSpace(0);
        pieDataSet.setValueTextSize(12);
        ArrayList<Integer> color=new ArrayList<>();
        color.add(Color.GREEN);
        color.add(Color.CYAN);
        pieDataSet.setColors(color);
        PieData pieData=new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        sel_date=(TextView) findViewById(R.id.sel_date);
        sel_date.setText(sdf.format(myCalendar.getTime()));
    }
}
