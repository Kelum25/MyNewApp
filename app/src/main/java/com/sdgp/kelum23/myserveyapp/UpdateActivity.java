package com.sdgp.kelum23.myserveyapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UpdateActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private Button buttonSignout,buttonBack;
    private TextView textViewCurrentUser;

    DatabaseReference questionOneDbRef;
    ListView listViewQsnOne;
    List<QuestionOne> questionOneList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        questionOneDbRef= FirebaseDatabase.getInstance().getReference("QsnOneDetails");
        firebaseAuth=FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this,SignInActivity.class));
        }

        FirebaseUser user=firebaseAuth.getCurrentUser();
        textViewCurrentUser=(TextView)findViewById(R.id.textViewSigninUser);
        textViewCurrentUser.setText("Welcome to"+user.getEmail());
        buttonSignout=(Button)findViewById(R.id.buttonSignout);
        buttonBack=(Button)findViewById(R.id.buttonBackUpdte);

        listViewQsnOne=(ListView)findViewById(R.id.listViewDetailsList);
        questionOneList=new ArrayList<>();

        buttonSignout.setOnClickListener(this);
        buttonBack.setOnClickListener(this);


        listViewQsnOne.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                QuestionOne questionOne=questionOneList.get(i);
                showUpdateDialog(questionOne.getId(),questionOne.getInterviewerName());

            }
        });
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
                QuestionOneList oneList=new QuestionOneList(UpdateActivity.this,questionOneList);
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

    private void showUpdateDialog(final String id, String name){
        AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(this);
        LayoutInflater inflater=getLayoutInflater();
        final View dialogView=inflater.inflate(R.layout.update_dialog,null);
        dialogBuilder.setView(dialogView);
        final EditText editTextName=(EditText)dialogView.findViewById(R.id.editTextNewInterviewerName);
        final EditText editTextDate=(EditText)dialogView.findViewById(R.id.editTextNewDate);
        final EditText editTextAddress=(EditText)dialogView.findViewById(R.id.editTextNewHomeAddress);
        final Spinner spinnerDistrict=(Spinner)dialogView.findViewById(R.id.spinnerNewDistrict);
        final EditText editTextDsd=(EditText)dialogView.findViewById(R.id.editTextNewDSD);
        final EditText editTextGnd=(EditText)dialogView.findViewById(R.id.editTextNewGND);
        final EditText editTextMobile=(EditText)dialogView.findViewById(R.id.editTextNewMobileNo);
        final Button buttonUpdate=(Button)dialogView.findViewById(R.id.buttonUpdate);

        dialogBuilder.setTitle("Updating Details"+name);

        final AlertDialog alertDialog=dialogBuilder.create();
        alertDialog.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=editTextName.getText().toString().trim();
                String date=editTextDate.getText().toString().trim();
                String address=editTextAddress.getText().toString().trim();
                String district=spinnerDistrict.getSelectedItem().toString().trim();
                String dsd=editTextDsd.getText().toString().trim();
                String gnd=editTextGnd.getText().toString().trim();
                String mobile=editTextMobile.getText().toString().trim();

                if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(date) && !TextUtils.isEmpty(address) && !TextUtils.isEmpty(district) && !TextUtils.isEmpty(dsd) && !TextUtils.isEmpty(gnd) && !TextUtils.isEmpty(mobile)){
                    editDetails(id,name,date,address,district,dsd,gnd,mobile);
                    alertDialog.dismiss();
                }

            }
        });


    }

    private boolean editDetails(String id, String name,String date,String address, String district, String dsd, String gnd, String mobile){

        questionOneDbRef= FirebaseDatabase.getInstance().getReference("QsnOneDetails").child(id);
        QuestionOne questionOne=new QuestionOne(id,name,date,address,district,dsd,gnd,mobile);
        questionOneDbRef.setValue(questionOne);

        Toast.makeText(getApplicationContext(), "Question One details Updated", Toast.LENGTH_LONG).show();

        return true;


    }


}
