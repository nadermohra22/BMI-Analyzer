package com.example.myapplication.android;

import com.example.myapplication.foodInfo;
import com.example.myapplication.Status;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class user implements Serializable {
    String id;
    String name;
    String username;
    String pass_user;
    List<Status> s;
    String Birth_date;
    int age;
    double bmi;
    List<foodInfo> f;
    String state;

    public user() {
    }


    public user(String id, String name, String username, String pass_user, List<Status> s, String birth_date, int age, double bmi, List<foodInfo> f, String state) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.pass_user = pass_user;
        this.s = s;
        Birth_date = birth_date;
        this.age = age;
        this.bmi = bmi;
        this.f = f;
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass_user() {
        return pass_user;
    }

    public void setPass_user(String pass_user) {
        this.pass_user = pass_user;
    }

    public List<Status> getS() {
        return s;
    }

    public void setS(List<Status> s) {
        this.s = s;
    }

    public String getBirth_date() {
        return Birth_date;
    }

    public void setBirth_date(String birth_date) {
        Birth_date = birth_date;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<foodInfo> getF() {
        return f;
    }

    public void setF(List<foodInfo> f) {
        this.f = f;
    }

   /* public char getState() {
        return state;
    }*/

  /*  public void setState(char state) {
        this.state = state;
    }*/

    public void addStatus(Status s) {
        if (getS() == null)
            setS(new ArrayList<>());
        this.s.add(s);

    }

    public void addFoodInfo(foodInfo info) {
        if (getF() == null)
            setF(new ArrayList<>());
        this.f.add(info);
    }

    public void editFood(int index, foodInfo info) {
        if (getF() == null)
            setF(new ArrayList<>());
        this.f.remove(index);
        this.f.add(index, info);
    }

    public void deleteFood(int index) {
        if (getF() == null)
            setF(new ArrayList<>());
        this.f.remove(index);
    }
}
