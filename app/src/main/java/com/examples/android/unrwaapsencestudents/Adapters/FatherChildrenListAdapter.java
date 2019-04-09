package com.examples.android.unrwaapsencestudents.Adapters;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.examples.android.unrwaapsencestudents.Fragments.AddFatherFragment;
import com.examples.android.unrwaapsencestudents.Fragments.AddStudentFragment;
import com.examples.android.unrwaapsencestudents.Models.Father;
import com.examples.android.unrwaapsencestudents.Models.FatherFirebaseHelper;
import com.examples.android.unrwaapsencestudents.Models.Student;
import com.examples.android.unrwaapsencestudents.Models.StudentFirebaseHelper;
import com.examples.android.unrwaapsencestudents.R;
import com.examples.android.unrwaapsencestudents.Utils.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.Serializable;
import java.util.LinkedList;

public class FatherChildrenListAdapter extends RecyclerView.Adapter<FatherChildrenListAdapter.ItemViewHolder> {
    final LinkedList<Student> studentsList;
    final String familyID;
    final FragmentManager fragmentManager;
    private int currentItemPos=0;
public interface StudentInfoInterface extends Serializable {
    void onSave(Student updated);
}
    StudentInfoInterface studentInfoInterface=new StudentInfoInterface(){

        @Override
        public void onSave(Student updated) {
            if(updated!=null) {
                studentsList.set(currentItemPos, updated);
                notifyDataSetChanged();
            }
        }
    };
    public FatherChildrenListAdapter(LinkedList<Student> studentsList, String familyID, FragmentManager fragmentManager) {
        this.studentsList = studentsList;
        this.familyID = familyID;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new FatherChildrenListAdapter.ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.childs_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int i) {
        try {
            Student  student= studentsList.get(i);
            holder.tv_Name.setText(student.getName());
            holder.tv_Number.setText(student.getuNumber());
            holder.tv_Gender.setText(student.getGender());
            holder.tv_Grade.setText(student.getGrade());
            holder.tv_Name.setText(student.getName());
            Glide.with(holder.img_Photo.getContext()).load(Uri.parse(student.getImagePath())).into(holder.img_Photo);
        }catch (Exception e){
            Log.i("FatherChildrenAdapter","onBindViewHolder "+e.getMessage());}
    }

    @Override
    public int getItemCount() {
        return studentsList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_Name,tv_Number, tv_Gender, tv_Grade;
        ImageView img_Photo;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_Name=itemView.findViewById(R.id.tv_stdName);
            tv_Number=itemView.findViewById(R.id.tv_stdNumber);
            tv_Gender=itemView.findViewById(R.id.tv_stdGender);
            tv_Grade=itemView.findViewById(R.id.tv_stdGrade);
            img_Photo=itemView.findViewById(R.id.img_stdImage);
            ((ImageView)itemView.findViewById(R.id.img_remove)).setOnClickListener(this);
            ((ImageView)itemView.findViewById(R.id.img_edit)).setOnClickListener(this);
            ((ImageView)itemView.findViewById(R.id.img_addChildren)).setVisibility(View.GONE);
        }

        @Override
        public void onClick(View view) {
            Student temp=studentsList.get(getAdapterPosition());
            final Context context=view.getContext();
            switch (view.getId()){
                case R.id.img_remove:
                    FatherFirebaseHelper.getInstance(context).removeChild(temp.getuNumber(),familyID);
                    StudentFirebaseHelper.getInstance(context).removeChild(temp.getuNumber());
                    break;
                case R.id.img_edit:
                    currentItemPos=getAdapterPosition();
                    Task<Student> studentTask= StudentFirebaseHelper.getInstance(context).getStudentInfo(temp.getuNumber());
                    studentTask.addOnSuccessListener(new OnSuccessListener<Student>() {
                        @Override
                        public void onSuccess(Student student) {
                            AddStudentFragment dialogFragment = new AddStudentFragment();
                            Bundle b=  new Bundle();
                            b.putSerializable(Constants.STUDENT_INFO, student);
                            b.putString(Constants.FAMILY_ID, familyID);
                            b.putSerializable("stdInterface",studentInfoInterface);
                            dialogFragment.setArguments(b);
                            dialogFragment.show(fragmentManager.beginTransaction(), "");
                        }
                    });
                    studentTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context,"Edit called for "+e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                     break;
            }

        }
    }
}
