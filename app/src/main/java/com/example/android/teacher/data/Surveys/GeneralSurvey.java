package com.example.android.teacher.data.Surveys;

/**
 * Created by shkurtagashi on 26.01.17.
 */

public class GeneralSurvey {

    public int _id;
    public String _android_id;
    public Double _timestamp;
    public String _question1;
    public String _question2;
    public String _question3;
    public String _question4;
    public String _question5;
    public String _question6;
    public String _question7;
    public String _question8;
    public String _question9;
    public String _question10;
    public String _question11;
    public String _question12;
    public String _question13;
    public String _question14;
    public String _question15;
    public String _question16;
    public String _question17;

    public GeneralSurvey(){}

    public GeneralSurvey(String androidID, Double timestamp, String q1, String q2, String q3, String q4, String q5, String q6, String q7, String q8, String q9, String q10, String q11, String q12, String q13, String q14, String q15, String q16, String q17){
        setAndroidID(androidID);
        setTimestamp(timestamp);
        setQuestion1(q1);
        setQuestion2(q2);
        setQuestion3(q3);
        setQuestion4(q4);
        setQuestion5(q5);
        setQuestion6(q6);
        setQuestion7(q7);
        setQuestion8(q8);
        setQuestion9(q9);
        setQuestion10(q10);
        setQuestion11(q11);
        setQuestion12(q12);
        setQuestion13(q13);
        setQuestion14(q14);
        setQuestion15(q15);
        setQuestion16(q16);
        setQuestion17(q17);


    }


    public void setAndroidID(String androidID) {
        this._android_id = androidID;
    }

    public void setTimestamp(Double timestamp) {
        this._timestamp = timestamp;
    }

    public void setQuestion1(String question) {
        this._question1 = question;
    }

    public void setQuestion2(String question) {
        this._question2 = question;
    }

    public void setQuestion3(String question) {
        this._question3 = question;
    }

    public void setQuestion4(String question) {
        this._question4 = question;
    }

    public void setQuestion5(String question) {
        this._question5 = question;
    }

    public void setQuestion6(String question) { this._question6 = question; }

    public void setQuestion7(String question) {
        this._question7 = question;
    }

    public void setQuestion8(String question) {
        this._question8 = question;
    }

    public void setQuestion9(String question) {
        this._question9 = question;
    }

    public void setQuestion10(String question) {
        this._question10 = question;
    }

    public void setQuestion11(String question) {
        this._question11 = question;
    }

    public void setQuestion12(String question) {
        this._question12 = question;
    }

    public void setQuestion13(String question) {
        this._question13 = question;
    }

    public void setQuestion14(String question) {
        this._question14 = question;
    }

    public void setQuestion15(String question) {
        this._question15 = question;
    }

    public void setQuestion16(String question) {
        this._question16 = question;
    }

    public void setQuestion17(String question) {
        this._question17 = question;
    }



}
