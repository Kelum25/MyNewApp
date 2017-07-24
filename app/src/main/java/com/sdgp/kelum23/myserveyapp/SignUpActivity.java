package com.sdgp.kelum23.myserveyapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    private Button buttonSignup,buttonSignin;
    private EditText editTextEmail,editTextpassword;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);

        if (firebaseAuth.getCurrentUser()!=null){
            // profile activity here
            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));

        }

        buttonSignin=(Button)findViewById(R.id.buttonSignin);
        buttonSignup=(Button)findViewById(R.id.buttonSignup);
        editTextEmail=(EditText)findViewById(R.id.editTextEmail);
        editTextpassword=(EditText)findViewById(R.id.editTextPassword);

        buttonSignup.setOnClickListener(this);
        buttonSignin.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        if (view==buttonSignup){
            createUserAccount();
        }
        if (view==buttonSignin){
            //will open login activity
            startActivity(new Intent(this,SignInActivity.class));
        }

    }

    private void createUserAccount() {
        String email=editTextEmail.getText().toString().trim();
        String password=editTextpassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this,"Please enter email address",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(this,"Please enter password",Toast.LENGTH_SHORT).show();
            return;

        }

        progressDialog.setMessage("Creating user account....");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            // start profile activity here
                            Toast.makeText(SignUpActivity.this,"Registered Successfully",Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        }else {
                            Toast.makeText(SignUpActivity.this,"Could not register.Please try again..!!!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
