package com.example.android.teacher.data.Surveys;

/**
 * Created by shkurtagashi on 29.01.17.
 */

public class PostLectureSurvey {

    public int _id;
    public String _android_id;
    public Double _timestamp;
    public String _question1;
    public String _question2;
    public String _question3;
    public String _question4;
    public String _question5;
    public String _question6;

    public PostLectureSurvey(){}

    public PostLectureSurvey(String androidID, Double timestamp, String q1, String q2, String q3, String q4, String q5, String q6){
        setAndroidID(androidID);
        setTimestamp(timestamp);
        setQuestion1(q1);
        setQuestion2(q2);
        setQuestion3(q3);
        setQuestion4(q4);
        setQuestion5(q5);
        setQuestion6(q6);


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


}
