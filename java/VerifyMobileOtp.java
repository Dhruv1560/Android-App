package com.example.dhruvpatel.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyMobileOtp extends AppCompatActivity {

    Button verify_btn,continue_btn;
    EditText otp_et,mobile_no;
    ProgressDialog pd;
    Spinner spinner;
    ArrayAdapter arrayAdapter;
    String mverificationId;
    FirebaseAuth firebaseAuth;
    String phonenumber,verification_code,Auto_code;
    ProgressBar progressBar;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_mobile_otp);
        spinner=findViewById(R.id.verifymobileotp_spinner);
        pd = new ProgressDialog(VerifyMobileOtp.this);


        arrayAdapter=new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,CountryData.countrynames);
        spinner.setAdapter(arrayAdapter);

        otp_et=findViewById(R.id.verifymobileotp_otp);
        mobile_no=findViewById(R.id.verifymobileotp_mobileno);

        verify_btn=findViewById(R.id.verifymobileotp_btn_submit);
        continue_btn=findViewById(R.id.verifymobileotp_btn_continue);

        progressBar = findViewById(R.id.verifymobileotp_progressbar);
        firebaseAuth = FirebaseAuth.getInstance();


        mcallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                Auto_code= phoneAuthCredential.getSmsCode();


                otp_et.setText(Auto_code);

                //verifyPhonenNumberAuto(code);

                //startActivity(new Intent(VerifyMobileOtp.this,AdminPage.class));

            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s,forceResendingToken);
                verification_code=s;

                //progressBar.setVisibility(View.VISIBLE);
                if(verification_code == null){
                    Toast.makeText(VerifyMobileOtp.this, "Code not send", Toast.LENGTH_SHORT).show();

                }

                else

                Toast.makeText(VerifyMobileOtp.this, "OTP send to your number", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(String s) {
                super.onCodeAutoRetrievalTimeOut(s);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(VerifyMobileOtp.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        };


    }

    public boolean validateMobileNo(){

        String code= CountryData.countrycodes[spinner.getSelectedItemPosition()];
        String mobilenumber=mobile_no.getText().toString().trim();

        if(mobilenumber.isEmpty() || mobilenumber.length()<10 || mobilenumber.length()>10 ){
            mobile_no.setError("Enter valid mobile No");
            mobile_no.requestFocus();
            return false;
        }
        else {
            phonenumber = code + mobilenumber;
            return true;
        }

    }
    public void send_sms(View v){

        if(validateMobileNo()) {
            Toast.makeText(this, "" + phonenumber, Toast.LENGTH_SHORT).show();

            PhoneAuthProvider.getInstance().verifyPhoneNumber(phonenumber,
                    60,
                    TimeUnit.SECONDS,
                    this, mcallback);
        }
        else Toast.makeText(this, "Enter Correct Mobile No", Toast.LENGTH_SHORT).show();
    }

    public void verify(View v){

        String input_code=otp_et.getText().toString();
        if(verification_code == null){
            Toast.makeText(this, "OTP Not Sent Please Wait!!", Toast.LENGTH_SHORT).show();
        }
        else if(otp_et == null){
            Toast.makeText(this, "Enter OTP", Toast.LENGTH_SHORT).show();
        }
        else verifyPhonenNumber(verification_code,input_code);
    }

    private void verifyPhonenNumber(String verifycode,String inputcode){

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verifycode,inputcode);
        SignInWithPhone(credential);
    }


    public  void SignInWithPhone(PhoneAuthCredential credential){

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(VerifyMobileOtp.this,AdminPage.class));
                            Toast.makeText(VerifyMobileOtp.this, "Success", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


}
