package com.example.android.teacher.data.EmpaticaE4;

import android.provider.BaseColumns;

/**
 * Created by shkurtagashi on 18.12.16.
 */

public final class E4DataContract {

    // Private constructor to prevent someone from accidentally instantiating the contract class,
    private E4DataContract() {}

    /* Inner class that defines the table contents */
    public static final class E4DataEntry{

        /*
        * Empatica E4 - Table and Columns declaration
        */
        public final static String TABLE_NAME_EMPATICA_E4 = "empatica_e4";

        public final static String _ID = "id";
        public final static String COLUMN_E4_NAME = "empatica_name";
        public final static String COLUMN_API_KEY = "api_key";

    }
}
