package com.gurusankar149.bitbank10th;

public class Home_model {
    private  String docID,name;
    private int numberoftests;

    public Home_model() {
    }

    public Home_model(String docID, String name, int numberoftests) {
        this.docID = docID;
        this.name = name;
        this.numberoftests = numberoftests;
    }

    public String getDocID() {
        return docID;
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberoftests() {
        return numberoftests;
    }

    public void setNumberoftests(int numberoftests) {
        this.numberoftests = numberoftests;
    }
}
