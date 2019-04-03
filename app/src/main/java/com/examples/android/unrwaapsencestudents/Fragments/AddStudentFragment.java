package com.examples.android.unrwaapsencestudents.Fragments;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.examples.android.unrwaapsencestudents.Models.Student;
import com.examples.android.unrwaapsencestudents.Models.StudentFirebaseHelper;
import com.examples.android.unrwaapsencestudents.R;
import com.examples.android.unrwaapsencestudents.Utils.Constants;

import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddStudentFragment extends DialogFragment implements View.OnClickListener {
    EditText edt_stdName, edt_stdNumber;
    Spinner spinner_grade;
    RadioGroup rg_gender;
    ImageView img_stdImage;
    private String familyID;
    private Student studentInfo;
    public AddStudentFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_student_fragment, container, false);
        getDialog().setTitle("إضافة/تعديل بيانات");
        getDialog().getWindow().requestFeature(Window.FEATURE_CUSTOM_TITLE);
        ((Button) rootView.findViewById(R.id.btn_save)).setOnClickListener(this);
        ((Button) rootView.findViewById(R.id.btn_cancel)).setOnClickListener(this);
        setCancelable(false);

        edt_stdName = rootView.findViewById(R.id.edt_stdName);
        edt_stdNumber = rootView.findViewById(R.id.edt_stdNumber);
        spinner_grade = rootView.findViewById(R.id.spinner_grade);
        rg_gender = rootView.findViewById(R.id.rg_gender);
        img_stdImage = rootView.findViewById(R.id.img_stdImage);

        if (getArguments() != null) {
            familyID=getArguments().getString(Constants.FAMILY_ID);
            studentInfo = (Student) getArguments().getSerializable(Constants.STUDENT_INFO);
            edt_stdName.setText(studentInfo.getName());
            edt_stdNumber.setText(studentInfo.getuNumber());
            spinner_grade.setSelection(Arrays.asList((getResources().getStringArray(R.array.Grades))).indexOf(studentInfo.getGrade()));
            rg_gender.getChildAt(Arrays.asList((getResources().getStringArray(R.array.gender))).indexOf(studentInfo.getGender())).setSelected(true);
           }
        return rootView;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                //(String name, String ID, String email, String familyID, String mobileNum)
                StudentFirebaseHelper fatherFirebaseHelper = StudentFirebaseHelper.getInstance(view.getContext());
                if (studentInfo == null) {
                    String gender;
                    int checkedRadioButtonId = rg_gender.getCheckedRadioButtonId();
                    if(checkedRadioButtonId==R.id.rb_female){
                        gender=(getResources().getStringArray(R.array.gender))[0];
                    }else{
                        gender=(getResources().getStringArray(R.array.gender))[1];;
                    }
                    Student student = new Student(edt_stdName.getText().toString(),
                            edt_stdNumber.getText().toString(),
                            "https://images.idgesg.net/images/article/2017/08/android_robot_logo_by_ornecolorada_cc0_via_pixabay1904852_wide-100732483-large.jpg",
                            spinner_grade.getSelectedItem().toString(),
                            gender);
                    fatherFirebaseHelper.addStudent(student);
                } else {
                    studentInfo.setName(edt_stdName.getText().toString());
                    studentInfo.setuNumber(edt_stdNumber.getText().toString());
                    studentInfo.setGrade((String) spinner_grade.getSelectedItem());
                   // studentInfo.setGender(rg_gender.se);
                    int checkedRadioButtonId = rg_gender.getCheckedRadioButtonId();
                    if(checkedRadioButtonId==R.id.rb_female){
                        rg_gender.getChildAt(0).setSelected(true);
                    }else{
                        rg_gender.getChildAt(1).setSelected(true);
                    }
                }
                dismiss();
                break;
            case R.id.btn_cancel:
                dismiss();
                break;
        }
    }

}
