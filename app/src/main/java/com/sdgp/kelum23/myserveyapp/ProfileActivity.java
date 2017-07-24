package com.sdgp.kelum23.myserveyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private Button buttonSignout,buttonQuestion1;
    private TextView textViewCurrentUser;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth=FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this,SignInActivity.class));
        }

        FirebaseUser user=firebaseAuth.getCurrentUser();
        textViewCurrentUser=(TextView)findViewById(R.id.textViewSigninUser);
        textViewCurrentUser.setText("Welcome to"+user.getEmail());
        buttonSignout=(Button)findViewById(R.id.buttonSignout);
        buttonQuestion1=(Button)findViewById(R.id.buttonGoQuestion);

        buttonSignout.setOnClickListener(this);
        buttonQuestion1.setOnClickListener(this);

        imageView=(ImageView)findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.question);
    }

    @Override
    public void onClick(View view) {

        if (view==buttonSignout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this,SignUpActivity.class));
        }

        if (view==buttonQuestion1){
            finish();
            startActivity(new Intent(this,OperationActivity.class));

        }

    }
}
