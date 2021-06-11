package com.gurusankar149.bitbank10th;

public class RankHolderModel {
    String doc_id,cat_id,test_id,username;
    int marks;

    public RankHolderModel() {
    }

    public RankHolderModel(String doc_id, String cat_id, String test_id, String username, int marks) {
        this.doc_id = doc_id;
        this.cat_id = cat_id;
        this.test_id = test_id;
        this.username = username;
        this.marks = marks;
    }

    public String getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(String doc_id) {
        this.doc_id = doc_id;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getTest_id() {
        return test_id;
    }

    public void setTest_id(String test_id) {
        this.test_id = test_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }
}
