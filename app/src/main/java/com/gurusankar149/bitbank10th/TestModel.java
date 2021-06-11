package com.gurusankar149.bitbank10th;

public class TestModel {
    private String TestID;
    private int topscore,time;

    public TestModel() {
    }

    public TestModel(String testID, int topscore, int time) {
        TestID = testID;
        this.topscore = topscore;
        this.time = time;
    }

    public String getTestID() {
        return TestID;
    }

    public void setTestID(String testID) {
        TestID = testID;
    }

    public int getTopscore() {
        return topscore;
    }

    public void setTopscore(int topscore) {
        this.topscore = topscore;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
