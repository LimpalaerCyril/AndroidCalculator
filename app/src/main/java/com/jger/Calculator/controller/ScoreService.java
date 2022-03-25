package com.jger.Calculator.controller;

import com.jger.Calculator.model.Score;

public class ScoreService {
    private ScoreDao scoreDao;

    public ScoreService(ScoreDao calculDao) {
        this.scoreDao = calculDao;
    }

    public Long getComputeCount(){
        return scoreDao.count();
    }

    public void storeInDB(Score score){
        scoreDao.create(score);
    }

    public int getMax(){
        return scoreDao.max(ScoreDao.cleScore);
    }
}
