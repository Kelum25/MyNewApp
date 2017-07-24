package com.sdgp.kelum23.myserveyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth firebaseAuth;
    private Button buttonBack,buttonAddDetail;
    private TextView textViewCurrentUser;
    private EditText editTextInterviewerName,editTextDate,editTextHomeAddress,editTextDSD,editTextGND,editTextMobileNo;
    private Spinner spinnerDistrict;

    DatabaseReference questionOneDbRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        questionOneDbRef= FirebaseDatabase.getInstance().getReference("QsnOneDetails");


        firebaseAuth=FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this,SignInActivity.class));
        }

        FirebaseUser user=firebaseAuth.getCurrentUser();
        textViewCurrentUser=(TextView)findViewById(R.id.textViewSigninUser);
        textViewCurrentUser.setText("Welcome to"+user.getEmail());
        buttonBack=(Button)findViewById(R.id.buttonSignout);
        buttonAddDetail=(Button)findViewById(R.id.buttonAdd);

        editTextInterviewerName=(EditText)findViewById(R.id.editTextNewInterviewerName);
        editTextDate=(EditText)findViewById(R.id.editTextNewDate);
        editTextHomeAddress=(EditText)findViewById(R.id.editTextNewHomeAddress);
        editTextDSD=(EditText)findViewById(R.id.editTextNewDSD);
        editTextGND=(EditText)findViewById(R.id.editTextNewGND);
        editTextMobileNo=(EditText)findViewById(R.id.editTextNewMobileNo);
        spinnerDistrict=(Spinner)findViewById(R.id.spinnerNewDistrict);


        buttonBack.setOnClickListener(this);
        buttonAddDetail.setOnClickListener(this);




    }

    @Override
    public void onClick(View view) {
        if (view==buttonBack){

            finish();
            startActivity(new Intent(this,OperationActivity.class));
        }

        if (view==buttonAddDetail){
            addQ1Details();
        }

    }

    private void addQ1Details() {
        String name=editTextInterviewerName.getText().toString().trim();
        String date=editTextDate.getText().toString().trim();
        String address=editTextHomeAddress.getText().toString().trim();
        String district=spinnerDistrict.getSelectedItem().toString();
        String dsd=editTextDSD.getText().toString().trim();
        String gnd=editTextGND.getText().toString().trim();
        String mobileNo=editTextMobileNo.getText().toString().trim();

        if (!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(date)&&!TextUtils.isEmpty(address)&&!TextUtils.isEmpty(dsd)&&!TextUtils.isEmpty(gnd)&&!TextUtils.isEmpty(mobileNo)){
            String id=questionOneDbRef.push().getKey();
            QuestionOne details=new QuestionOne(id,name,date,address,district,dsd,gnd,mobileNo);
            questionOneDbRef.child(id).setValue(details);

            editTextInterviewerName.setText("");
            editTextDate.setText("");
            editTextHomeAddress.setText("");
            editTextDSD.setText("");
            editTextGND.setText("");
            editTextMobileNo.setText("");
            Toast.makeText(this,"Question One details added successfully!",Toast.LENGTH_LONG).show();

        }else {
            Toast.makeText(this,"Please entered all fields data",Toast.LENGTH_LONG).show();
        }

    }



}
