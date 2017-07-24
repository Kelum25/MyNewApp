package com.sdgp.kelum23.myserveyapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
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

public class DeleteActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private Button buttonSignout,buttonBack;
    private TextView textViewCurrentUser;

    DatabaseReference questionOneDbRef;
    ListView listViewQsnOne;
    List<QuestionOne> questionOneList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

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
        buttonBack=(Button)findViewById(R.id.buttonBackDel);

        listViewQsnOne=(ListView)findViewById(R.id.listViewDeleteList);
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
                QuestionOneList oneList=new QuestionOneList(DeleteActivity.this,questionOneList);
                listViewQsnOne.setAdapter(oneList);

                listViewQsnOne.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        QuestionOne questionOne=questionOneList.get(i);
                        showDeleteDialog(questionOne.getId(),questionOne.getInterviewerName());

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    private void showDeleteDialog(final String id, String name){

        AlertDialog.Builder dBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dView = inflater.inflate(R.layout.delete_form_dialog, null);
        dBuilder.setView(dView);

        final Button buttonDeld = (Button)dView.findViewById(R.id.buttonDeleteDlt);

        dBuilder.setTitle(name);
        final AlertDialog delDia = dBuilder.create();
        delDia.show();

        buttonDeld.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                deleteArtist(id);
                delDia.dismiss();
            }

        });


    }

    private boolean deleteArtist(String id) {

        questionOneDbRef= FirebaseDatabase.getInstance().getReference("QsnOneDetails").child(id);

        questionOneDbRef.removeValue();

        Toast.makeText(getApplicationContext(), "Question One details Deleted", Toast.LENGTH_LONG).show();

        return true;
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
