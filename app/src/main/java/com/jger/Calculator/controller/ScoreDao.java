package com.jger.Calculator.controller;

import android.content.ContentValues;
import android.database.Cursor;

import com.jger.Calculator.model.Score;

public class ScoreDao extends BaseDao<Score> {
    static String cleScore = "score";
    public ScoreDao(DataBaseHelper helper) {
        super(helper);
    }

    @Override
    protected String getTableName() {
        return "scores";
    }

    @Override
    protected void putValues(ContentValues values, Score entity) {
        values.put(cleScore,entity.getScore());
    }

    @Override
    protected Score getEntity(Cursor cursor) {
        Integer indexScore = cursor.getColumnIndex(cleScore);
        Score score = new Score();
        score.setScore(cursor.getInt(indexScore));
        cursor.close();
        return score;
    }


}
