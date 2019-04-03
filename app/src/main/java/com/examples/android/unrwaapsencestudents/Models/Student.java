package com.examples.android.unrwaapsencestudents.Models;

public class Student {
    private String Name,uNumber, imagePath,Grade,Gender;
    public Student(){}//For firebase
    public Student(String name, String uNumber, String imagePath, String grade, String gender) {
        Name = name;
        this.uNumber = uNumber;
        this.imagePath = imagePath;
        Grade = grade;
        Gender = gender;
    }
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getuNumber() {
        return uNumber;
    }

    public void setuNumber(String uNumber) {
        this.uNumber = uNumber;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getGrade() {
        return Grade;
    }

    public void setGrade(String grade) {
        Grade = grade;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }
}
