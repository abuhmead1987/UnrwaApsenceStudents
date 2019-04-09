package com.examples.android.unrwaapsencestudents.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.examples.android.unrwaapsencestudents.AddStudentsActivity;
import com.examples.android.unrwaapsencestudents.Fragments.AddFatherFragment;
import com.examples.android.unrwaapsencestudents.Models.Father;
import com.examples.android.unrwaapsencestudents.Models.FatherFirebaseHelper;
import com.examples.android.unrwaapsencestudents.R;
import com.examples.android.unrwaapsencestudents.Utils.Constants;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class FathersListAdapter extends FirebaseRecyclerAdapter<Father,FathersListAdapter.ItemViewHolder> {
    private Context context;
    private FragmentManager fragmentManager;
    public FathersListAdapter(@NonNull FirebaseRecyclerOptions<Father> options, Context context, FragmentManager fragmentManager) {
        super(options);
        this.context=context;
        this.fragmentManager=fragmentManager;
    }
    @Override
    protected void onBindViewHolder(@NonNull ItemViewHolder holder, int position, @NonNull Father father) {
        holder.edt_fatherName.setText(father.getName());
        holder.edt_fatherEmail.setText(father.getEmail());
        holder.edt_fatherID.setText(father.getID());
        holder.edt_familyID.setText(father.getFamilyID());
        holder.edt_mobile.setText(father.getMobileNum());
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int index) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.father_list_item,parent,false));
    }
    public class ItemViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

        EditText edt_fatherName,edt_fatherEmail, edt_fatherID, edt_familyID,edt_mobile;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            edt_fatherName=itemView.findViewById(R.id.edt_fatherName);
            edt_fatherEmail=itemView.findViewById(R.id.edt_fatherEmail);
            edt_fatherID=itemView.findViewById(R.id.edt_fatherID);
            edt_familyID=itemView.findViewById(R.id.edt_familyID);
            edt_mobile=itemView.findViewById(R.id.edt_mobile);
            ((ImageView)itemView.findViewById(R.id.img_remove)).setOnClickListener(this);
            ((ImageView)itemView.findViewById(R.id.img_edit)).setOnClickListener(this);
            ((ImageView)itemView.findViewById(R.id.img_addChildren)).setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.img_remove:
                    FatherFirebaseHelper.getInstance(view.getContext()).removeFather(getRef(getAdapterPosition()).getKey());
                    Toast.makeText(view.getContext(),"Remove called for "+getRef(getAdapterPosition()).getKey(),Toast.LENGTH_LONG).show();
                    break;
                case R.id.img_edit:
                    Task<Father> ff= FatherFirebaseHelper.getInstance(view.getContext()).getFatherInfo(getRef(getAdapterPosition()).getKey());
                    ff.addOnSuccessListener(new OnSuccessListener<Father>() {
                        @Override
                        public void onSuccess(Father father) {
                            Toast.makeText(context,"Edit called for "+getRef(getAdapterPosition()).getKey(),Toast.LENGTH_LONG).show();
                            AddFatherFragment dialogFragment = new AddFatherFragment();
                            Bundle b=  new Bundle();
                            b.putSerializable(Constants.FATHER_INFO, father);
                            dialogFragment.setArguments(b);
                            dialogFragment.show(fragmentManager.beginTransaction(), "");
                        }
                    });
                    ff.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context,"Edit called for "+e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                    break;
                    case R.id.img_addChildren:
                        Task<Father> fatherGet= FatherFirebaseHelper.getInstance(view.getContext()).getFatherInfo(getRef(getAdapterPosition()).getKey());
                        fatherGet.addOnSuccessListener(new OnSuccessListener<Father>() {
                            @Override
                            public void onSuccess(Father father) {
                                Intent i=new Intent(context, AddStudentsActivity.class);
                                i.putExtra(Constants.FATHER_INFO, father );
                               context.startActivity(i);
                            }
                        });
                        fatherGet.addOnFailureListener(new OnFailureListener() {
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

