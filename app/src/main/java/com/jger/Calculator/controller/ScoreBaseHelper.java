package com.jger.Calculator.controller;

import android.content.Context;

public class ScoreBaseHelper extends DataBaseHelper {

    public ScoreBaseHelper(Context context) {
        super(context, "Calcul", 1);
    }

    @Override
    protected String getCreationSql() {
        return "CREATE TABLE IF NOT EXISTS scores  (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                ScoreDao.cleScore + " INTEGER NOT NULL" +
                ")";
    }

    @Override
    protected String getDeleteSql() {
        return null;
    }
}
