package com.example.dhruvpatel.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Random;

public class OtpActivity extends AppCompatActivity {

    Button otpbtn, verifybtn;
    LinearLayout otplayout, verifylayout, submitlayout;
    EditText email, inputotp;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String semail, sRendom, spass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        getSupportActionBar().setTitle("Forgot Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        otpbtn = (Button) findViewById(R.id.otp_btn_getotp);
        verifybtn = (Button) findViewById(R.id.otp_btn_verify);

        email = (EditText) findViewById(R.id.otp_email_et);
        inputotp = (EditText) findViewById(R.id.otp_verifyotp_et);

        otplayout = (LinearLayout) findViewById(R.id.otpLayout);
        verifylayout = (LinearLayout) findViewById(R.id.verifyLayout);

        Bundle b_email = getIntent().getExtras();

        email.setText(b_email.getString("AdminEmail"));


        final Random random = new Random();
        otpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                semail = email.getText().toString();
                if (semail.matches(emailPattern)) {
                } else {
                    Toast.makeText(OtpActivity.this, "In Valid Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (email.getText().toString().equalsIgnoreCase("")) {
                    email.setError("Email Id Required");
                    return;
                } else {
                    sRendom = String.valueOf(random.nextInt(1000000) + 1);
                    new OTP(OtpActivity.this, semail, Constant.OTPSUBJECT, "Hi,\t" + semail + "\n\n" + Constant.OTPMessage + sRendom).execute();
                    otplayout.setVisibility(View.GONE);
                    verifylayout.setVisibility(View.VISIBLE);
                }
            }

        });

        verifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputotp.getText().toString().equals(sRendom)) {
                    Toast.makeText(OtpActivity.this, "Welcome to Admin Access", Toast.LENGTH_SHORT).show();
                    //otplayout.setVisibility(View.GONE);
                    //verifylayout.setVisibility(View.GONE);
                    startActivity(new Intent(OtpActivity.this,AdminPage.class));
                } else {
                    Toast.makeText(OtpActivity.this, "Enter Correct Otp", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            startActivity(new Intent(OtpActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
        return super.onOptionsItemSelected(item);
    }
}