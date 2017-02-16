package com.example.android.teacher.data.EmpaticaE4;

import static android.R.attr.name;
import static android.R.attr.value;

/**
 * Created by shkurtagashi on 17.01.17.
 */

public class EmpaticaE4 {

    String _username;
    String _name;
    String _apiKey;

    public EmpaticaE4(){

    }


    public EmpaticaE4(String username, String name, String apiKey){
        setUsername(username);
        setName(name);
        setApiKey(apiKey);
    }

    public void setUsername(String username){
        this._username = username;
    }

    public String getUsername(){
        return this._username;
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
