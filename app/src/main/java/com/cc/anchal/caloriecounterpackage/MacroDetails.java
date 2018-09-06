package com.cc.anchal.caloriecounterpackage;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MacroDetails extends AppCompatActivity {

    Button sel_date_button;
    TextView sel_date,protein,carbs,fat;
    Calendar dateSelected = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date;
    Calendar myCalendar = dateSelected;

    HelperClass dbHelper;
    SQLiteDatabase database;
    public MacroDetails()
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
        setContentView(R.layout.activity_macro_details);

        Bundle extras=getIntent().getExtras();
        String curr_date=extras.getString("key");

        protein=(TextView)findViewById(R.id.protein);
        carbs=(TextView)findViewById(R.id.carbs);
        fat=(TextView)findViewById(R.id.fat);

        /*Calendar c=Calendar.getInstance();
        String myFormat = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String curr_date=sdf.format(c.getTime());
        */
        sel_date=(TextView) findViewById(R.id.sel_date);
        sel_date.setText(curr_date);
        open();
        Cursor cursor=database.rawQuery(("SELECT protein,carbs,fat FROM User_Data WHERE date=?"),new String[]{curr_date});
        if ( cursor.moveToFirst() ) {
            protein.setText(cursor.getString(0));
            carbs.setText(cursor.getString(1));
            fat.setText(cursor.getString(2));
        } else {
            Toast.makeText(MacroDetails.this, "Data not found", Toast.LENGTH_SHORT).show();
        }
        close();

        sel_date_button=(Button)findViewById(R.id.sel_date_button);
        sel_date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(MacroDetails.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();

                //accessing databse
                open();
                Cursor cursor=database.rawQuery(("SELECT protein,carbs,fat FROM User_Data WHERE date=?"),new String[]{sel_date.getText().toString()});
                if ( cursor.moveToFirst() ) {
                    protein.setText(cursor.getString(0));
                    carbs.setText(cursor.getString(1));
                    fat.setText(cursor.getString(2));
                } else {
                    protein.setText("");
                    carbs.setText("");
                    fat.setText("");
                    Toast.makeText(MacroDetails.this, "Data not found", Toast.LENGTH_SHORT).show();
                }
                close();
            }
        };

    }

    private void updateLabel() {
        String myFormat = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        sel_date=(TextView) findViewById(R.id.sel_date);
        sel_date.setText(sdf.format(myCalendar.getTime()));
    }
}
