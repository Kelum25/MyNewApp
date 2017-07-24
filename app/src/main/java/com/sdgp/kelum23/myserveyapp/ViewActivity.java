package com.sdgp.kelum23.myserveyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewActivity extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth firebaseAuth;
    private Button buttonSignout,buttonAddDetail,buttonBack;
    private TextView textViewCurrentUser;


    DatabaseReference questionOneDbRef;
    ListView listViewQsnOne;
    List<QuestionOne> questionOneList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        questionOneDbRef=FirebaseDatabase.getInstance().getReference("QsnOneDetails");

        firebaseAuth=FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this,SignInActivity.class));
        }

        FirebaseUser user=firebaseAuth.getCurrentUser();
        textViewCurrentUser=(TextView)findViewById(R.id.textViewSigninUser);
        textViewCurrentUser.setText("Welcome to"+user.getEmail());
        buttonSignout=(Button)findViewById(R.id.buttonSignout);
        buttonBack=(Button)findViewById(R.id.buttonGoBack);
        listViewQsnOne=(ListView)findViewById(R.id.listViewQsnOne);
        questionOneList=new ArrayList<>();

        buttonSignout.setOnClickListener(this);
        buttonBack.setOnClickListener(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        questionOneDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                questionOneList.clear();
                for (DataSnapshot qsnOneSnapshot: dataSnapshot.getChildren()){
                    QuestionOne questionOne=qsnOneSnapshot.getValue(QuestionOne.class);
                    questionOneList.add(questionOne);
                }
                QuestionOneList oneList=new QuestionOneList(ViewActivity.this,questionOneList);
                listViewQsnOne.setAdapter(oneList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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
            startActivity(new Intent(this,OperationActivity.class));
        }
    }


}
