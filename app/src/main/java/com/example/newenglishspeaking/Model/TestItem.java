package com.example.newenglishspeaking.Model;

public class TestItem {
    public String testNumber, testName;
    public String level;
    public String q1, q2, q3, a1, a2, a3, kq1, kq2, kq3, ka1, ka2, ka3;
    public String isTested;

    public TestItem(String testName){
        this.testName = testName;
    }
    public TestItem(String testNumber, String testName, String level, String isTested){
        this.testNumber = testNumber;
        this.testName = testName;
        this.level = level;
        this.isTested = isTested;
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

    public String getA1() {
        return a1;
    }

    public String getA2() {
        return a2;
    }

    public String getA3() {
        return a3;
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

    public String getIsTested() {
        return isTested;
    }

    public TestItem(String testNumber, String testName, String level, String isTested, String q1, String q2, String q3,
                    String a1, String a2, String a3, String kq1, String kq2, String kq3, String ka1, String ka2, String ka3){
        this.testNumber = testNumber;
        this.testName = testName;
        this.level = level;
        this.q1 = q1;
        this.q2 = q2;
        this.q3 = q3;
        this.a1 = a1;
        this.a2 = a2;
        this.a3 = a3;
        this.kq1 = kq1;
        this.kq2 = kq2;
        this.kq3 = kq3;
        this.ka1 = ka1;
        this.ka2 = ka2;
        this.ka3 = ka3;
        this.isTested = isTested;
    }

    public String getTestNumber() {
        return testNumber;
    }

    public String getTestName() {
        return testName;
    }

    public String getLevel() {
        return level;
    }


}
