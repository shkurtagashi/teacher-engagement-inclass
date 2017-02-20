package com.example.android.teacher.data.EmpaticaE4;

import static android.R.attr.name;
import static android.R.attr.value;

/**
 * Created by shkurtagashi on 17.01.17.
 */

public class EmpaticaE4 {
    String _name;
    String _apiKey;

    public EmpaticaE4(){

    }


    public EmpaticaE4(String name, String apiKey){
        setName(name);
        setApiKey(apiKey);
    }


    public void setName(String name){
        this._name = name;
    }

    public String getName(){
        return this._name;
    }

    public void setApiKey(String apiKey){
        this._apiKey = apiKey;
    }

    public String getApiKey(){
        return this._apiKey;
    }

}
