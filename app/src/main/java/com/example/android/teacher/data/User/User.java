package com.example.android.teacher.data.User;

import static com.example.android.teacher.R.id.username;

/**
 * Created by shkurtagashi on 17.01.17.
 */

public class User {

    public String _android_id;
    public String _username;
    public String _gender;
    public String _age;
    public String _faculty;
    public String _programme;
    public String _courses;
    public String _status;
    public String _teachingExperience;
    public String _agreement;

    public User(){}

    public User(String android_id, String username, String gender, String age, String faculty, String programme, String course, String status, String teachingExperience, String agreement){
        setAndroidId(android_id);
        setUsername(username);
        setGender(gender);
        setAge(age);
        setFaculty(faculty);
        setProgramme(programme);
        setCourse(course);
        setStatus(status);
        setTeachingExperience(teachingExperience);
        setAgreement(agreement);
    }



    public User(String username, String gender, String age, String faculty, String programme, String course, String status, String teachingExperience, String agreement){
        setUsername(username);
        setGender(gender);
        setAge(age);
        setFaculty(faculty);
        setProgramme(programme);
        setCourse(course);
        setStatus(status);
        setTeachingExperience(teachingExperience);
        setAgreement(agreement);
    }


    public void setAndroidId(String id) {
        this._android_id = id;
    }

    public String getAndroidId(){
        return this._android_id;
    }

    public void setGender(String gender) {
        this._gender = gender;
    }

    public String getGender(){
        return this._gender;
    }

    public void setAge(String age) {
        this._age = age;
    }

    public String getAge(){
        return this._age;
    }

    public void setFaculty(String faculty) {
        this._faculty = faculty;
    }

    public String getFaculty(){
        return this._faculty;
    }

    public void setProgramme(String programme) {
        this._programme = programme;
    }

    public String getProgramme(){
        return this._programme;
    }

    public void setCourse(String course) {
        this._courses = course;
    }

    public String getCourse(){
        return this._courses;
    }

    public void setStatus(String status) {
        this._status = status;
    }

    public String getStatus() {
        return this._status;
    }

    public void setTeachingExperience(String teachingExperience) {
        this._teachingExperience = teachingExperience;
    }

    public String getTeachingExperience(){
        return this._teachingExperience;
    }

    public void setAgreement(String agreement){
        this._agreement = agreement;
    }

    public String getAgreement(){
        return this._agreement;
    }

    public void setUsername(String username) {
        this._username = username;
    }

    public String getUsername(){
        return this._username;
    }
}
