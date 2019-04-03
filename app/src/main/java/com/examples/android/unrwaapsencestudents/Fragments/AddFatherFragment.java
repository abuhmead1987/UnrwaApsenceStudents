package com.examples.android.unrwaapsencestudents.Fragments;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.examples.android.unrwaapsencestudents.Models.Father;
import com.examples.android.unrwaapsencestudents.Models.FatherFirebaseHelper;
import com.examples.android.unrwaapsencestudents.R;
import com.examples.android.unrwaapsencestudents.Utils.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddFatherFragment extends DialogFragment implements View.OnClickListener {
    EditText edt_fatherName, edt_fatherEmail, edt_fatherID, edt_familyID, edt_mobile;
    private String familyID;
    private Father updateFather;

    public AddFatherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_father_dialog, container, false);
        getDialog().setTitle("إضافة/تعديل بيانات ولي أمر");
        ((Button) rootView.findViewById(R.id.btn_save)).setOnClickListener(this);
        ((Button) rootView.findViewById(R.id.btn_cancel)).setOnClickListener(this);
        setCancelable(false);

        edt_fatherName = rootView.findViewById(R.id.edt_fatherName);
        edt_fatherName.setEnabled(true);
        edt_fatherEmail = rootView.findViewById(R.id.edt_fatherEmail);
        edt_fatherEmail.setEnabled(true);
        edt_fatherID = rootView.findViewById(R.id.edt_fatherID);
        edt_fatherID.setEnabled(true);
        edt_familyID = rootView.findViewById(R.id.edt_familyID);
        edt_familyID.setEnabled(true);
        edt_mobile = rootView.findViewById(R.id.edt_mobile);
        edt_mobile.setEnabled(true);

        ((ImageView)rootView.findViewById(R.id.img_remove)).setVisibility(View.GONE);
        ((ImageView)rootView.findViewById(R.id.img_edit)).setVisibility(View.GONE);

        if (getArguments() != null) {
            updateFather = (Father) getArguments().getSerializable(Constants.FATHER_INFO);
            edt_fatherName.setText(updateFather.getName());
            edt_fatherID.setText(updateFather.getID());
            edt_fatherEmail.setText(updateFather.getEmail());
            edt_familyID.setText(updateFather.getFamilyID());
            edt_mobile.setText(updateFather.getMobileNum());
        }

        return rootView;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                //(String name, String ID, String email, String familyID, String mobileNum)
                FatherFirebaseHelper fatherFirebaseHelper = FatherFirebaseHelper.getInstance(view.getContext());
                if (updateFather == null) {
                    Father father = new Father(edt_fatherName.getText().toString(),
                            edt_fatherID.getText().toString(),
                            edt_fatherEmail.getText().toString(),
                            edt_familyID.getText().toString(),
                            edt_mobile.getText().toString());
                    fatherFirebaseHelper.addFather(father);
                } else {
                    updateFather.setName(edt_fatherName.getText().toString());
                    updateFather.setID(edt_fatherID.getText().toString());
                    updateFather.setEmail(edt_fatherEmail.getText().toString());
                    updateFather.setFamilyID(edt_familyID.getText().toString());
                    updateFather.setMobileNum(edt_mobile.getText().toString());
                    fatherFirebaseHelper.addFather(updateFather);
                    // fatherFirebaseHelper.updateFather(father);
                }
                dismiss();
                break;
            case R.id.btn_cancel:
                dismiss();
                break;
        }
    }
}
