package com.soft1841.oop.Op;

public class Member {
    //会员名字   密码    卡号   积分
    private String name;
    private String password;
    private int careId;
    private double  integral;
    public Member(){

    }
    public Member(String name, String password, int careId, double integral) {
        this.name = name;
        this.password = password;
        this.careId = careId;
        this.integral = integral;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getCareId() {
        return careId;
    }
    public void setCareId(int careId) {
        this.careId = careId;
    }
    public double getIntegral() {
        return integral;
    }
    public void setIntegral(double integral) {
        this.integral = integral;
    }


}
