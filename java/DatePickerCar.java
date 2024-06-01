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

public class DatePickerCar extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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
        setContentView(R.layout.activity_date_picker_car);
        autoCompleteTextView=(AutoCompleteTextView)findViewById(R.id.date_location_car);
        spinner=findViewById(R.id.date_spinner_car);
        btn=findViewById(R.id.date_book_car);


        //For calender start------------------

        ll_date = findViewById(R.id.pickup_date_car);
        ll2_date = findViewById(R.id.dropoff_date_car);
        ll_time = findViewById(R.id.pickup_time_car);
        ll2_time = findViewById(R.id.dropoff_time_car);

        ll_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                Calendar calendar = Calendar.getInstance();

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int date = calendar.get(Calendar.DAY_OF_MONTH);


                datePickerDialog_pickup = new DatePickerDialog(DatePickerCar.this, new DatePickerDialog.OnDateSetListener() {
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


                datePickerDialog_dropoff = new DatePickerDialog(DatePickerCar.this, new DatePickerDialog.OnDateSetListener() {
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

                timePickerDialog_pickup = new TimePickerDialog(DatePickerCar.this, new TimePickerDialog.OnTimeSetListener() {
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

                timePickerDialog_dropoff = new TimePickerDialog(DatePickerCar.this, new TimePickerDialog.OnTimeSetListener() {
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

                if(validateDatePicker()){

                    Intent data = new Intent(DatePickerCar.this,DatabaseDateTimeCar.class);
                    Bundle b = new Bundle();

                    b.putString("type",spinner_value);
                    b.putString("location",location_value);
                    b.putString("pickup-date",a1);
                    b.putString("dropoff-date",a2);
                    b.putString("pickup-time",a3);
                    b.putString("dropoff-time",a4);

                    data.putExtras(b);
                    startActivity(data);

                    Toast.makeText(DatePickerCar.this, "Confirm your Detail", Toast.LENGTH_SHORT).show();


                }
                else {
                    Toast.makeText(DatePickerCar.this, "Select all Detail Correctly", Toast.LENGTH_SHORT).show();
                }
            }
        });


        locationAdapter = new ArrayAdapter(DatePickerCar.this,R.layout.support_simple_spinner_dropdown_item,location);
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

        ArrayAdapter spinnerAdapter = new ArrayAdapter(DatePickerCar.this,R.layout.support_simple_spinner_dropdown_item,spinnerlist);
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
