package com.sdgp.kelum23.myserveyapp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Kelum23 on 21/07/2017.
 */

public class QuestionOneList extends ArrayAdapter<QuestionOne> {

    private Activity contextQuestionOne;
    List<QuestionOne>questionOnes;

    public QuestionOneList(Activity contextQuestionOne, List<QuestionOne> questionOnes) {
        super(contextQuestionOne, R.layout.layout_question_one_list, questionOnes);
        this.contextQuestionOne = contextQuestionOne;
        this.questionOnes = questionOnes;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater1=contextQuestionOne.getLayoutInflater();
        View listViewQuestionOne = inflater1.inflate(R.layout.layout_question_one_list,null,true);

        TextView textViewName= (TextView)listViewQuestionOne.findViewById(R.id.textViewName);
        TextView textViewDate= (TextView)listViewQuestionOne.findViewById(R.id.textViewDate);
        TextView textViewAddress= (TextView)listViewQuestionOne.findViewById(R.id.textViewAddress);
        TextView textViewDistrict= (TextView)listViewQuestionOne.findViewById(R.id.textViewDistrict);
        TextView textViewDsd= (TextView)listViewQuestionOne.findViewById(R.id.textViewDsd);
        TextView textViewGnd= (TextView)listViewQuestionOne.findViewById(R.id.textViewGnd);
        TextView textViewMobileNo= (TextView)listViewQuestionOne.findViewById(R.id.textViewMobileNo);

        QuestionOne questionOne=questionOnes.get(position);
        textViewName.setText("Interviewer:"+questionOne.getInterviewerName());
        textViewDate.setText("Date       :"+questionOne.getDate());
        textViewAddress.setText("Address :"+questionOne.getAddress());
        textViewDistrict.setText("District  :"+questionOne.getDistrict());
        textViewDsd.setText("DSD          :"+questionOne.getDsd());
        textViewGnd.setText("GND         :"+questionOne.getGnd());
        textViewMobileNo.setText("Mobile No:"+questionOne.getMobileNo());


        return listViewQuestionOne;

    }
}
