package com.example.dhruvpatel.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class signup extends AppCompatActivity {

    private static final Pattern pass_pattern= Pattern.compile("^"+
            "(?=.*[0-9])"+
             "(?=.*[a-z])"+
              "(?=.*[A-Z])"+
              "(?=.*[$#%&*@])"+
               "(?=\\S+$)"+
                ".{6,}"+
                  "$");

    public EditText signfname,signpass,signemail,signmobno,signlname;

    public Button btn3;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myref;

    private String TAG;
    String fname,lname,email,pass, mobno,address;
    AutoCompleteTextView signaddress;
    String [] location = {"Shahibaug","Bapunagar","Visat","Paldi","Krishnagar","CTM","RTO","Akhbarnagar","Pragatinagar","Shastrinagar","Naranpura","Gota","Gandhinagar","Galaxy","Navrangpura","Naroda","Nikol","Civil"};
    ArrayAdapter locationAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        myref = firebaseDatabase.getReference("Register");


        //signname = (EditText)findViewById(R.id.signup_etuname);
        signfname=(EditText)findViewById(R.id.signup_etfname);
        signlname=(EditText)findViewById(R.id.signup_etlname);
        signpass=(EditText)findViewById(R.id.signup_etpass);
        signemail=(EditText)findViewById(R.id.signup_etemail);
        signmobno=(EditText)findViewById(R.id.signup_etmobno);
        signaddress = (AutoCompleteTextView)findViewById(R.id.signup_etaddress);

        btn3=(Button)findViewById(R.id.signup_btn_confirm);

        locationAdapter = new ArrayAdapter(signup.this,android.R.layout.select_dialog_item,location);

        signaddress.setThreshold(2);
        signaddress.setAdapter(locationAdapter);

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               if( validate()){

                   String user_email=signemail.getText().toString();
                   String user_pass= signpass.getText().toString();

                   firebaseAuth.createUserWithEmailAndPassword(user_email,user_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if(task.isSuccessful()){

                               //sendUserdata();

                               //Toast.makeText(signup.this, "Verification mail send to your mail", Toast.LENGTH_SHORT).show();
                               //firebaseAuth.signOut();
                               //finish();
                               //startActivity(new Intent(signup.this,MainActivity.class));

                               sendEmailVerification();

                              // Log.d(TAG,"data addedd");
                               //Toast.makeText(signup.this,"data added to server",Toast.LENGTH_SHORT).show();

                           }
                           else{
                               Log.w(TAG,"data error",task.getException());
                               Toast.makeText(signup.this,"Email Already Registered",Toast.LENGTH_SHORT).show();
                           }

                       }
                   });
               }
            }
        });
    }




    private boolean validate(){

        if((!validateFname()) || (!validateAddress()) ||(!validateLname()) || (!validateEmail())   ||  (!validatePassword()) || (!validateMobno()) ){

            Toast.makeText(this, "Enter correct detail", Toast.LENGTH_SHORT).show();
            return  false;
        }
        else{

            Toast.makeText(this, "Registration success", Toast.LENGTH_SHORT).show();
            Intent e = new Intent(signup.this,MainActivity.class);
            startActivity(e);
            return  true;

        }

    }

    private boolean validatePassword(){

         pass = signpass.getText().toString().trim();

        if(pass.isEmpty()){
            signpass.setError("Field required");
            return false;
        }
        else if (!pass_pattern.matcher(pass).matches()){

            signpass.setError("password too weak");
            return false;

        }
        else{
            return true;
        }
    }
    private Boolean validateAddress(){
        address = signaddress.getText().toString().trim();
        if(address.isEmpty()){
            signaddress.setError("Field Required");
            return  false;
        }
        else return true;

    }

    private boolean validateEmail(){

         email = signemail.getText().toString().trim();

        if(email.isEmpty()){
            signemail.setError("Field required");
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            signemail.setError("Enter valid email");
            return false;

        }
        else{
            return true;
        }
    }

    private boolean validateFname(){

         fname = signfname.getText().toString();

        if(fname.isEmpty()){
            signfname.setError("Field required");
            return false;
        }
        else if (fname.length()>9){

            signfname.setError("Max 8 Char");
            return false;

        }
        else{

            return true;
        }
    }
    private boolean validateLname(){

        lname = signlname.getText().toString();

        if(lname.isEmpty()){
            signlname.setError("Field required");
            return false;
        }
        else if (lname.length()>9){

            signlname.setError("Max 8 Char");
            return false;

        }
        else{

            return true;
        }
    }

    private boolean validateMobno(){

        mobno = signmobno.getText().toString().trim();

        if(mobno.isEmpty()){
            signmobno.setError("Field required");
            return false;
        }
        else if (mobno.length()>10 || mobno.length()<10){

            signmobno.setError("Enter 10 Digit Number");
            return false;

        }
        else{

            return true;
        }
    }


    private void sendEmailVerification(){

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser != null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){

                        sendUserdata();

                        Toast.makeText(signup.this, "Verification mail send to your mail", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(signup.this,MainActivity.class));

                    }else{

                        Toast.makeText(signup.this, "Email Doesnt send", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

     private void sendUserdata(){
        //FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        //DatabaseReference myref= firebaseDatabase.getReference(firebaseAuth.getUid());
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userID = user.getUid();

        UserProfile userProfile = new UserProfile(fname,lname,email,pass,mobno,address);
        myref.child(userID).setValue(userProfile);



     }

}
