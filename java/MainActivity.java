package com.example.dhruvpatel.login;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class MainActivity extends AppCompatActivity {

    public EditText name;
    public EditText password;
    public TextView info;
    String gName,gGivenname,gFamilyname,gEmail,gid;
    public Uri gphoto;
    public Button btn1, btn2, btn3,btn4;
    private String username;
    public FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    public String admin_email,admin_pass;
    StorageReference storageReference_google;
    DatabaseReference databaseReference_google;

    StorageTask storageTask;

    GoogleSignInClient mGoogleSignInClient;
    private String TAG="MainActivity";
    private int RC_SIGN_IN=1;

    Button signInButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText) findViewById(R.id.main_etuname);
        password = (EditText) findViewById(R.id.main_etpass);
        info = (TextView) findViewById(R.id.info);
        btn1 = (Button) findViewById(R.id.main_btn_login);
        btn2 = (Button) findViewById(R.id.main_btn_signup);
        btn3 = (Button) findViewById(R.id.main_btn_forgotpassword);
        btn4= (Button) findViewById(R.id.main_btn_admin);

        signInButton = (Button) findViewById(R.id.mygoogle);

        firebaseAuth = FirebaseAuth.getInstance();
        storageReference_google=FirebaseStorage.getInstance().getReference("Profile Image Of Google");
        databaseReference_google=FirebaseDatabase.getInstance().getReference("Register With Google");
        progressDialog = new ProgressDialog(MainActivity.this);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {
            finish();
            startActivity(new Intent(MainActivity.this, Homepage.class));
        }

        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Drawable_left=0;
                final int Drawable_top=1;
                final int Drawable_right=2;
                final int Drawable_bottom=3;

                //if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    if(motionEvent.getRawX() >= (password.getRight()-password.getCompoundDrawables()[Drawable_right].getBounds().width())){
                       //for right drawable image click....
                        switch ( motionEvent.getAction() ) {
                            case MotionEvent.ACTION_DOWN://button pressed then down occur and on release up occur
                                //Toast.makeText(MainActivity.this, "Down", Toast.LENGTH_SHORT).show();
                                password.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.resize_unlock,0);
                                password.setInputType(InputType.TYPE_CLASS_TEXT );
                                break;
                            //case MotionEvent.ACTION_UP:
                              //  Toast.makeText(MainActivity.this, "up", Toast.LENGTH_SHORT).show();
                                //password.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.resize_unlock,0);
                               // password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD /*|InputType.TYPE_CLASS_TEXT*/);
                               // break;

                        }
                        return  true;
                    }
                //}
                return false;
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (new ConnectionCall(MainActivity.this).isConnectingToInternet()){

                   AdminVarification();

                }
                else new ConnectionCall(MainActivity.this).connectiondetect();

            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (new ConnectionCall(MainActivity.this).isConnectingToInternet()){

                    validate(name.getText().toString(), password.getText().toString());
                    //new InsertData().execute();
                }
                else new ConnectionCall(MainActivity.this).connectiondetect();



                //validate(name.getText().toString(), password.getText().toString());
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, signup.class);
                startActivity(intent);

            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent b = new Intent(MainActivity.this, ForgotActivity.class);
                startActivity(b);

            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(MainActivity.this,gso);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

    }

    /*private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }*/


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            startActivity(new Intent(MainActivity.this,Homepage.class));
                            finish();
                            GoogleProfileInfo();
                            Toast.makeText(MainActivity.this, "Successfully logged in "+gName, Toast.LENGTH_SHORT).show();

                            //UploadGooglePic();
                            updateUI(user);
                        } else {

                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithCredential:failure", task.getException());
                           // Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });


    }

    public void GoogleProfileInfo(){
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null){
             gName = account.getDisplayName(); //Full NAme
             gGivenname = account.getGivenName();  //First NAme
             gFamilyname = account.getFamilyName();  //Sir Name
             gEmail = account.getEmail();    //Email
             gid = account.getId();
             gphoto = account.getPhotoUrl();



        }else Toast.makeText(this, "First Sign IN", Toast.LENGTH_SHORT).show();
    }

    public void UploadGooglePic(){

        if(gphoto!=null) {

            //Toast.makeText(this, ""+gphoto, Toast.LENGTH_SHORT).show();

            final StorageReference refff = storageReference_google.child(System.currentTimeMillis() + ".jpg");
             refff.putFile(gphoto)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Toast.makeText(MainActivity.this, "Upload Gpic", Toast.LENGTH_SHORT).show();

                            RegisterWithGoogle registerWithGoogle = new RegisterWithGoogle(gName, gEmail, gid, taskSnapshot.getDownloadUrl().toString());
                            String uploadid = databaseReference_google.push().getKey();
                            databaseReference_google.child(uploadid).setValue(registerWithGoogle);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        else Toast.makeText(this, "Image Null", Toast.LENGTH_SHORT).show();

    }
    private String getFileExtension(Uri uri){
        ContentResolver cr=getContentResolver();
        MimeTypeMap mime= MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }


    private void updateUI(FirebaseUser user) {

          user = FirebaseAuth.getInstance().getCurrentUser();
         //GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());

         if (user != null) {

             Intent google=new Intent(MainActivity.this,Homepage.class);
             startActivity(google);
            // Name, email address, and profile photo Url
            //String name = user.getDisplayName();
            //String email = user.getEmail();
           // Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            //boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.

        }

    }

    private void AdminVarification(){

         admin_email=name.getText().toString();
         admin_pass= password.getText().toString();

        if(admin_email.equals("dhruvpatel1560@gmail.com") && admin_pass.equals("aA@123")) {

            Intent email=new Intent(MainActivity.this,OtpActivity.class);
            Bundle b_email = new Bundle();

            b_email.putString("AdminEmail",admin_email);

            email.putExtras(b_email);
            startActivity(email);

        }else {

            if ((!validateEmail()) || (!validatePassword())) {
                Toast.makeText(MainActivity.this, "Enter Details First", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "This Email not for Admin", Toast.LENGTH_SHORT).show();

            }
        }

    }

    private void validate(final String username, final String password) {

        //progressDialog.setMessage("Loading");
       // progressDialog.show();


        if((!validateEmail()) || (!validatePassword())  ){

            Toast.makeText(this, "Enter Correct detail First", Toast.LENGTH_SHORT).show();
        }
        else {

            progressDialog.setMessage("Loading");
            progressDialog.show();

            //vehiclerentalservices2019@gmail.com
            firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful() ) {


                        progressDialog.dismiss();
                        checkEmailVerification();

                        // Toast.makeText(MainActivity.this, "Login success", Toast.LENGTH_SHORT).show();
                        // Intent i = new Intent(MainActivity.this,ThirdActivity.class);
                        //startActivity(i);

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Email And Password Incorrect ", Toast.LENGTH_SHORT).show();

                    }
                }

            });

        }
    }

    private void checkEmailVerification() {

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();

        if (emailflag) {
            Intent i = new Intent(MainActivity.this, Homepage.class);
            startActivity(i);

        } else {

            Toast.makeText(this, "Verify your email first", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }

    private boolean validatePassword() {

        String pass = password.getText().toString().trim();

        if (pass.isEmpty()) {
            password.setError("Field required");
            return false;
        } else {
            return true;
        }


    }
    private boolean validateEmail() {

        String email = name.getText().toString().trim();

        if (email.isEmpty()) {
            name.setError("Field required");
            return false;
        } else {
            return true;
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}