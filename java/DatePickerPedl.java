package com.example.dhruvpatel.login;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class DatePickerPedl extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String [] location={"Shahibaug","Bapunagar","Visat","Paldi","Krishnagar","CTM","RTO","Akhbarnagar","Pragatinagar","Shastrinagar","Naranpura","Gota","Gandhinagar","Galaxy","Navrangpura","Naroda","Nikol","Civil","SG highway"};
    AutoCompleteTextView autoCompleteTextView;
    Spinner spinner;
    public String a1,a2,a3,a4,spinner_value,location_value;



    Button btn;
    boolean pick_date=false,pick_time=false,drop_time=false,drop_date=false,location_boolean=false;
    ArrayAdapter locationAdapter;
    LinearLayout ll_date,ll2_date,ll_time,ll2_time;

    DatePickerDialog datePickerDialog_pickup,datePickerDialog_dropoff;
    TimePickerDialog timePickerDialog_pickup,timePickerDialog_dropoff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker_pedl);
        autoCompleteTextView=(AutoCompleteTextView)findViewById(R.id.date_location_pedl);
        spinner=findViewById(R.id.date_spinner_pedl);
        btn=findViewById(R.id.date_book_pedl);


        //For calender start------------------

        ll_date = findViewById(R.id.pickup_date_pedl);
        ll2_date = findViewById(R.id.dropoff_date_pedl);
        ll_time = findViewById(R.id.pickup_time_pedl);
        ll2_time = findViewById(R.id.dropoff_time_pedl);

        ll_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                Calendar calendar = Calendar.getInstance();

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int date = calendar.get(Calendar.DAY_OF_MONTH);


                datePickerDialog_pickup = new DatePickerDialog(DatePickerPedl.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker datePicker, int myear, int mmonth, int mday) {

                        a1= (mday+"-"+(mmonth+1)+"-"+myear);

                    }
                },date,month,year);

                datePickerDialog_pickup.show();
                pick_date=true;
            }
        });

        ll2_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v2) {
                Calendar calendar = Calendar.getInstance();

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int date = calendar.get(Calendar.DAY_OF_MONTH);


                datePickerDialog_dropoff = new DatePickerDialog(DatePickerPedl.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker datePicker, int myear, int mmonth, int mday) {

                        a2=(mday+"-"+(mmonth+1)+"-"+myear);

                    }
                },date,month,year);

                datePickerDialog_dropoff.show();
                drop_date=true;
            }
        });

        ll_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v3) {
                Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR);
                int minute = c.get(Calendar.MINUTE);

                timePickerDialog_pickup = new TimePickerDialog(DatePickerPedl.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int mhour, int mminute) {

                        if(mhour>=0 && mhour <12){
                            a3=(mhour+":"+mminute+" AM");
                        }
                        else if(mhour==12){
                            a3=(mhour+":"+mminute+" PM");
                        }
                        else{
                            mhour = (mhour-12);
                            a3=(mhour+":"+mminute+" PM");
                        }


                    }
                },minute,hour, false);

                timePickerDialog_pickup.show();
                pick_time=true;

            }
        });

        ll2_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v4) {
                Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR);
                int minute = c.get(Calendar.MINUTE);

                timePickerDialog_dropoff = new TimePickerDialog(DatePickerPedl.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int mhour, int mminute) {

                        if(mhour>=0 && mhour <12){
                            a4=(mhour+":"+mminute+" AM");
                        }
                        else if(mhour==12){
                            a4=(mhour+":"+mminute+" PM");
                        }
                        else{
                            mhour = (mhour-12);
                            a4=(mhour+":"+mminute+" PM");
                        }

                    }
                },minute,hour, false);

                timePickerDialog_dropoff.show();
                drop_time=true;

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validateDatePicker()) {
                    Intent data = new Intent(DatePickerPedl.this, DatabaseDateTimePedl.class);
                    Bundle pedl = new Bundle();

                    pedl.putString("type", spinner_value);
                    pedl.putString("location", location_value);
                    pedl.putString("pickup-date", a1);
                    pedl.putString("dropoff-date", a2);
                    pedl.putString("pickup-time", a3);
                    pedl.putString("dropoff-time", a4);

                    data.putExtras(pedl);
                    startActivity(data);

                    Toast.makeText(DatePickerPedl.this, "Confirm Your Booking", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(DatePickerPedl.this, "Select All Detail Correctly", Toast.LENGTH_SHORT).show();
            }
        });


        locationAdapter = new ArrayAdapter(DatePickerPedl.this,R.layout.support_simple_spinner_dropdown_item,location);
        autoCompleteTextView.setThreshold(2);
        autoCompleteTextView.setAdapter(locationAdapter);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                location_value=adapterView.getItemAtPosition(i).toString();
                location_boolean=true;
            }
        });


        ArrayList<String> spinnerlist=new ArrayList<>();
        spinnerlist.add("Self-service");
        spinnerlist.add("Rental-service");

        ArrayAdapter spinnerAdapter = new ArrayAdapter(DatePickerPedl.this,R.layout.support_simple_spinner_dropdown_item,spinnerlist);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);


    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        //String getvalue = String.valueOf(spinner.getSelectedItem());

        spinner_value = adapterView.getItemAtPosition(i).toString();



    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private boolean validateDatePicker(){
        if(!pick_time || !pick_date || !drop_time || !drop_date || !location_boolean){
            return false;
        }
        else return true;

    }
}
