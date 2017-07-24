package com.sdgp.kelum23.myserveyapp;

/**
 * Created by Kelum23 on 21/07/2017.
 */

public class QuestionOne {

    String id;
    String interviewerName;
    String date;
    String address;
    String district;
    String dsd;
    String gnd;
    String mobileNo;

    QuestionOne(){

    }

    public QuestionOne(String id, String interviewerName, String date, String address, String district, String dsd, String gnd, String mobileNo) {
        this.id = id;
        this.interviewerName = interviewerName;
        this.date = date;
        this.address = address;
        this.district = district;
        this.dsd = dsd;
        this.gnd = gnd;
        this.mobileNo = mobileNo;
    }

    public String getId() {
        return id;
    }

    public String getInterviewerName() {
        return interviewerName;
    }

    public String getDate() {
        return date;
    }

    public String getAddress() {
        return address;
    }

    public String getDistrict() {
        return district;
    }

    public String getDsd() {
        return dsd;
    }

    public String getGnd() {
        return gnd;
    }

    public String getMobileNo() {
        return mobileNo;
    }
}
