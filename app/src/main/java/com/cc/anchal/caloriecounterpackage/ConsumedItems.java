package com.cc.anchal.caloriecounterpackage;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ConsumedItems extends AppCompatActivity {

    LinearLayout LItems,LQuan;
    TextView sel_date;
    Button sel_date_button;
    DatePickerDialog.OnDateSetListener date;
    Calendar dateSelected = Calendar.getInstance();
    Calendar myCalendar = dateSelected;
    HelperClass dbHelper;
    SQLiteDatabase database;
    public ConsumedItems()
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
        setContentView(R.layout.activity_consumed_items);

        LItems=(LinearLayout)findViewById(R.id.linearLayoutItems);
        LQuan=(LinearLayout)findViewById(R.id.linearLayoutQuan);
        TextView[] itemTextViews=new TextView[0];
        TextView[] quanTextViews=new TextView[0];

        Bundle extras = getIntent().getExtras();
        String curr_date = extras.getString("key");
        sel_date = (TextView) findViewById(R.id.sel_date);
        sel_date.setText(curr_date);

        int count,l=0;
        open();
        Cursor cursor = database.rawQuery(("SELECT * FROM User_Consumption WHERE date=?"), new String[]{curr_date});
        if (cursor.moveToFirst()) {
            count=cursor.getCount();
            itemTextViews=new TextView[count];
            quanTextViews=new TextView[count];
            for(int i=0;i<count;i++)
            {
                TextView rowTextView=new TextView(this);
                rowTextView.setTextSize(18);
                rowTextView.setTextColor(Color.parseColor("#008000"));
                rowTextView.setText(cursor.getString(1));
                LItems.addView(rowTextView);
                itemTextViews[i]=rowTextView;
                rowTextView=new TextView(this);
                rowTextView.setTextSize(18);
                rowTextView.setTextColor(Color.parseColor("#006400"));
                rowTextView.setText(cursor.getString(2));
                LQuan.addView(rowTextView);
                quanTextViews[i]=rowTextView;
                cursor.moveToNext();
            }

        } else {
            if(itemTextViews!=null)
                l=itemTextViews.length;
            if(l>0){
                for(int i=0;i<l;i++)
                {
                    itemTextViews[i].setText("");
                    quanTextViews[i].setText("");
                }
            }else {
                Toast.makeText(ConsumedItems.this, "Data not found", Toast.LENGTH_SHORT).show();
            }
        }
        close();

        sel_date_button = (Button) findViewById(R.id.sel_date_button);
        sel_date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ConsumedItems.this, date, myCalendar
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

                open();
                Cursor cursor = database.rawQuery(("SELECT * FROM User_Consumption WHERE date=?"), new String[]{sel_date.getText().toString()});
                if (cursor.moveToFirst()) {
                    while (!cursor.isLast()) {
                        cursor.moveToNext();
                    }
                } else {
                    Toast.makeText(ConsumedItems.this, "Data not found", Toast.LENGTH_SHORT).show();
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
