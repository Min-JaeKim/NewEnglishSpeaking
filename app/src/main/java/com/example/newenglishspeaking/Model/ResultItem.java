package com.example.newenglishspeaking.Model;

public class ResultItem {
    private String resultKey;
    private String a1, a2, a3, ma1, ma2, ma3, q1, q2, q3, sc1, sc2, sc3, kq1, kq2, kq3,  ka1, ka2, ka3, testName, userId, date;


    public ResultItem(String a1, String a2, String a3, String ma1, String ma2, String ma3,
                      String q1, String q2, String q3, String sc1, String sc2, String sc3,
                      String kq1, String kq2, String kq3, String ka1, String ka2, String ka3,
                      String testName, String userId, String date){
        this.a1 = a1;
        this.a2 = a2;
        this.a3 = a3;
        this.ma1 = ma1;
        this.ma2 = ma2;
        this.ma3 = ma3;
        this.q1 = q1;
        this.q2 = q2;
        this.q3 = q3;
        this.sc1 = sc1;
        this.sc2 = sc2;
        this.sc3 = sc3;
        this.kq1 = kq1;
        this.kq2 = kq2;
        this.kq3 = kq3;
        this.ka1 = ka1;
        this.ka2 = ka2;
        this.ka3 = ka3;
        this.testName = testName;
        this.userId = userId;
        this.date = date;

    }

    public ResultItem(String resultKey, String a1, String a2, String a3, String ma1, String ma2, String ma3,
                      String q1, String q2, String q3, String sc1, String sc2, String sc3,
                      String kq1, String kq2, String kq3, String ka1, String ka2, String ka3,
                      String testName, String userId, String date){
        this.resultKey = resultKey;
        this.a1 = a1;
        this.a2 = a2;
        this.a3 = a3;
        this.ma1 = ma1;
        this.ma2 = ma2;
        this.ma3 = ma3;
        this.q1 = q1;
        this.q2 = q2;
        this.q3 = q3;
        this.sc1 = sc1;
        this.sc2 = sc2;
        this.sc3 = sc3;
        this.kq1 = kq1;
        this.kq2 = kq2;
        this.kq3 = kq3;
        this.ka1 = ka1;
        this.ka2 = ka2;
        this.ka3 = ka3;
        this.testName = testName;
        this.userId = userId;
        this.date = date;
    }

    public String getResultKey() {
        return resultKey;
    }

    public String getTestName(){
        return testName;
    }

    public String getDate() {
        return date;
    }

    public String getMa1() {
        return ma1;
    }

    public String getMa2() {
        return ma2;
    }

    public String getMa3() {
        return ma3;
    }

    public String getQ1() {
        return q1;
    }

    public String getQ2() {
        return q2;
    }

    public String getQ3() {
        return q3;
    }

    public String getSc1() {
        return sc1;
    }

    public String getSc2() {
        return sc2;
    }

    public String getSc3() {
        return sc3;
    }

    public String getKq1() {
        return kq1;
    }

    public String getKq2() {
        return kq2;
    }

    public String getKq3() {
        return kq3;
    }

    public String getKa1() {
        return ka1;
    }

    public String getKa2() {
        return ka2;
    }

    public String getKa3() {
        return ka3;
    }

    public String getUserId() {
        return userId;
    }

    public String getA1() {
        return a1;
    }

    public String getA2() {
        return a2;
    }
    public String getA3() {
        return a3;
    }

}
