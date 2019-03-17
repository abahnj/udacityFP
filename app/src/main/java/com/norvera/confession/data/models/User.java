package com.norvera.confession.data.models;

import androidx.sqlite.db.SimpleSQLiteQuery;

public class User {

    public User( int vocation, int age, int gender) {

        switch (vocation){
            case 0: {
                this.vocation = " SINGLE = 1";
                break;
            }
            case 1: {
                this.vocation = " MARRIED = 1";
                break;
            }
            case 2:{
                this.vocation = " PRIEST = 1";
                break;
            }
            case 3:
                this.vocation = " RELIGIOUS = 1";
                break;
            default:
        }
        switch (age) {
            case 0: {
                this.age = " ADULT = 1";
                break;
            }
            case 1: {
                this.age = " TEEN = 1";
               break;
            }
            case 2: {
                this.age = " CHILD = 1";
                break;
            }
        }
        switch (gender) {
            case 0: {
                this.gender = " MALE = 1";
                break;
            }
            case 1:{
                this.gender = " FEMALE = 1";
                break;
            }
        }
    }
    private String vocation;
    private String gender;
    private String age;

    public SimpleSQLiteQuery getUserSelectionForCommandment(long commandment) {
        String and = " AND";
        String selection  = "SELECT * FROM SIN WHERE COMMANDMENT_ID = " + commandment +  and + vocation + and + age + and + gender;
        return new SimpleSQLiteQuery(selection);
    }

}
