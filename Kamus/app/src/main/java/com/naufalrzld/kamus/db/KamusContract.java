package com.naufalrzld.kamus.db;

import android.provider.BaseColumns;

public class KamusContract {
    public static String TABLE_EN_ID = "table_en_id";
    public static String TABLE_ID_EN = "table_id_en";

    static final class KamusEnIdColumns implements BaseColumns {
        static String WORD = "word";
        static String MEANING = "meaning";
    }
}
