package com.examples.android.unrwaapsencestudents.Models;

import java.io.Serializable;
import java.util.HashMap;

public class Father implements Serializable {

    private String Name, ID,email, familyID, mobileNum;

    /**
     * Students IDs (Unrwa ID)
     */
    private HashMap<String,Boolean> children =new HashMap<>();

    public Father(){}//for firebase
    public Father(String name, String ID, String email, String familyID, String mobileNum) {
        Name = name;
        this.ID = ID;
        this.email = email;
        this.familyID = familyID;
        this.mobileNum = mobileNum;
    }
    public Father(String name, String ID, String email, String familyID, String mobileNum, HashMap<String,Boolean> children) {
        Name = name;
        this.ID = ID;
        this.email = email;
        this.familyID = familyID;
        this.mobileNum = mobileNum;
        this.children = children;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFamilyID() {
        return familyID;
    }

    public void setFamilyID(String familyID) {
        this.familyID = familyID;
    }

    public HashMap<String,Boolean> getChildren() {
        return children;
    }

    public void setChildren(HashMap<String,Boolean> children) {
        this.children = children;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }
}
