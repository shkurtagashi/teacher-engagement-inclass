package com.example.android.teacher.data.Sensors;

/**
 * Created by shkurtagashi on 17.01.17.
 */

public class TemperatureSensor {

    int _id;
    float _value;
    double _timestamp;

    public TemperatureSensor(){

    }

//    public TemperatureSensor(int id, float value, double time) {
//        setID(id);
//        setValue(value);
//        setTimestamp(time);
//    }

    public TemperatureSensor(float value, double time){
        setValue(value);
        setTimestamp(time);
    }

    public void setID(int id){
        this._id = id;
    }

    public int getID(){
        return this._id;
    }

    public void setValue(float value){
        this._value = value;
    }

    public float getValue(){
        return this._value;
    }

    public void setTimestamp(double timestamp){
        this._timestamp = timestamp;
    }

    public double getTimestamp(){
        return this._timestamp;
    }

}
