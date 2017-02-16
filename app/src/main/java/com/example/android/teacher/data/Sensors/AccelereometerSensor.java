package com.example.android.teacher.data.Sensors;

/**
 * Created by shkurtagashi on 17.01.17.
 */

public class AccelereometerSensor {
    int _id;
    int _Xvalue;
    int _Yvalue;
    int _Zvalue;
    double _timestamp;

    public AccelereometerSensor(){

    }

    public AccelereometerSensor(int Xvalue, int Yvalue, int Zvalue,  double time){
        setXvalue(Xvalue);
        setYvalue(Yvalue);
        setZvalue(Zvalue);
        setTimestamp(time);
    }

    public void setID(int id){
        this._id = id;
    }

    public int getID(){
        return this._id;
    }


    public void setXvalue(int value){
        this._Xvalue = value;
    }

    public int getXvalue(){
        return this._Xvalue;
    }

    public void setYvalue(int value){
        this._Yvalue = value;
    }

    public int getYvalue(){
        return this._Yvalue;
    }


    public void setZvalue(int value){
        this._Zvalue = value;
    }

    public int getZvalue(){
        return this._Zvalue;
    }


    public void setTimestamp(double timestamp){
        this._timestamp = timestamp;
    }

    public double getTimestamp(){
        return this._timestamp;
    }
}
