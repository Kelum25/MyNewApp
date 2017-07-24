package com.sdgp.kelum23.myserveyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class OperationActivity extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth firebaseAuth;
    private Button buttonSignout,buttonAddDetail,buttonViewDetail,buttonDeleteDetail,buttonUpdateDetail,buttonBack;
    private TextView textViewCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation);
        firebaseAuth=FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this,SignInActivity.class));
        }

        FirebaseUser user=firebaseAuth.getCurrentUser();
        textViewCurrentUser=(TextView)findViewById(R.id.textViewSigninUser);
        textViewCurrentUser.setText("Welcome to"+user.getEmail());
        buttonSignout=(Button)findViewById(R.id.buttonSignout);
        buttonAddDetail=(Button)findViewById(R.id.buttonAddDetail);
        buttonDeleteDetail=(Button)findViewById(R.id.buttonDeleteDetail);
        buttonViewDetail=(Button)findViewById(R.id.buttonViewDetail);
        buttonUpdateDetail=(Button)findViewById(R.id.buttonUpdateDetail);
        buttonBack=(Button)findViewById(R.id.buttonGo);

        buttonSignout.setOnClickListener(this);
        buttonAddDetail.setOnClickListener(this);
        buttonDeleteDetail.setOnClickListener(this);
        buttonViewDetail.setOnClickListener(this);
        buttonUpdateDetail.setOnClickListener(this);
        buttonBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view==buttonSignout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this,SignUpActivity.class));
        }
        if (view==buttonBack){
            finish();
            startActivity(new Intent(this,ProfileActivity.class));

        }

        if (view==buttonAddDetail){
            finish();
            startActivity(new Intent(this,AddActivity.class));

        }

        if (view==buttonViewDetail){
            finish();
            startActivity(new Intent(this,ViewActivity.class));

        }

        if (view==buttonUpdateDetail){
            finish();
            startActivity(new Intent(this,UpdateActivity.class));

        }
        if (view==buttonDeleteDetail){
            finish();
            startActivity(new Intent(this,DeleteActivity.class));

        }





    }
}
