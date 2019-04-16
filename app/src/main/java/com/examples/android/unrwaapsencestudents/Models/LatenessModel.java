package com.examples.android.unrwaapsencestudents.Models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class LatenessModel {

    private String studentUNumber;
    private long latenessDateLong;
    private String latenessDateTime;
    private String latenessReason;
    private String insertedBy;
    private String editedBy="-1";
    private String deletedBy="-1";
    private boolean isDeleted=false;
    private boolean isEdited=false;

    public void getDateFromString(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//       Date date = new Date();
//       String strDate = dateFormat.format(date).toString();
//       myRef.child("datetime").setValue(strDate);
    }
}
