package com.gurusankar149.bitbank10th;

public class QModel {
    String Question,OptionA,OptionB,OptionC,OptionD;
    int ans,seleteAns,status;


    public QModel() {
    }

    public QModel(String question, String optionA, String optionB, String optionC, String optionD, int ans,int seleteAns,int status) {
        Question = question;
        OptionA = optionA;
        OptionB = optionB;
        OptionC = optionC;
        OptionD = optionD;
        this.ans = ans;
        this.seleteAns=seleteAns;
        this.status=status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getOptionA() {
        return OptionA;
    }

    public void setOptionA(String optionA) {
        OptionA = optionA;
    }

    public String getOptionB() {
        return OptionB;
    }

    public void setOptionB(String optionB) {
        OptionB = optionB;
    }

    public String getOptionC() {
        return OptionC;
    }

    public void setOptionC(String optionC) {
        OptionC = optionC;
    }

    public String getOptionD() {
        return OptionD;
    }

    public void setOptionD(String optionD) {
        OptionD = optionD;
    }

    public int getAns() {
        return ans;
    }

    public void setAns(int ans) {
        this.ans = ans;
    }

    public int getSeleteAns() {
        return seleteAns;
    }

    public void setSeleteAns(int seleteAns) {
        this.seleteAns = seleteAns;
    }
}
