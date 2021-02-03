package com.poly.GuitarAssistant.Instrument;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.GuitarAssistant.R;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.CustomViewHolder> {

    private ArrayList<PersonalData> mList = null;
    private Activity context = null;
    public Context acontext;

    public UserAdapter(Activity context, ArrayList<PersonalData> list) {
        this.context = context;
        this.mList = list;
        acontext = context;
    }
    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView file_path;
        protected TextView name;
        protected TextView company;
        protected TextView price;
        protected TextView store;
        protected TextView price2;
        protected TextView store2;
        protected TextView price3;
        protected TextView store3;
        protected TextView price4;
        protected TextView store4;
        protected LinearLayout Linear_link;
        protected LinearLayout Linear_link2;
        protected LinearLayout Linear_link3;
        protected LinearLayout Linear_link4;

        public CustomViewHolder(View view) {
            super(view);
            /*
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("태그", "onClick: "+getAdapterPosition());
                    String s1 = store.getText().toString()+price.getText().toString(),
                            s2 = store2.getText().toString()+price2.getText().toString(),
                            s3 = store3.getText().toString()+price3.getText().toString(),
                            s4 = store4.getText().toString()+price4.getText().toString();
                    coustemDialog cd = new coustemDialog(acontext,name.getText().toString(),s1,s2,s3,s4,getAdapterPosition(),acontext);
                    cd.show();

                }
            });

             */
            this.file_path = (ImageView) view.findViewById(R.id.imageView_list_imgurl);
            this.name = (TextView) view.findViewById(R.id.textView_list_name);
            this.company = (TextView) view.findViewById(R.id.textView_list_company);
            this.price = (TextView) view.findViewById(R.id.textView_list_price);
            this.store = (TextView) view.findViewById(R.id.textView_list_store);
            this.price2 = (TextView) view.findViewById(R.id.textView_list_price2);
            this.store2 = (TextView) view.findViewById(R.id.textView_list_store2);
            this.price3 = (TextView) view.findViewById(R.id.textView_list_price3);
            this.store3 = (TextView) view.findViewById(R.id.textView_list_store3);
            this.price4 = (TextView) view.findViewById(R.id.textView_list_price4);
            this.store4 = (TextView) view.findViewById(R.id.textView_list_store4);
            Linear_link = (LinearLayout)view.findViewById(R.id.Linear_link);
            Linear_link2 = (LinearLayout)view.findViewById(R.id.Linear_link2);
            Linear_link3 = (LinearLayout)view.findViewById(R.id.Linear_link3);
            Linear_link4 = (LinearLayout)view.findViewById(R.id.Linear_link4);
            Linear_link.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    if(mList.get(getAdapterPosition()).getMember_link() != "") {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mList.get(getAdapterPosition()).getMember_link()));
                        context.startActivity(intent);
                    }
                }
            });
            Linear_link2.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    if(mList.get(getAdapterPosition()).getMember_link2() != "") {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mList.get(getAdapterPosition()).getMember_link2()));
                        context.startActivity(intent);
                    }
                }
            });
            Linear_link3.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    if(mList.get(getAdapterPosition()).getMember_link3() != "") {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mList.get(getAdapterPosition()).getMember_link3()));
                        context.startActivity(intent);
                    }
                }
            });
            Linear_link4.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    if(mList.get(getAdapterPosition()).getMember_link4() != "") {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mList.get(getAdapterPosition()).getMember_link4()));
                        context.startActivity(intent);
                    }
                }
            });

        }
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

        viewholder.name.setText(mList.get(position).getMember_name());
        viewholder.company.setText(mList.get(position).getMember_company());
        viewholder.price.setText(mList.get(position).getMember_price());
        viewholder.store.setText(mList.get(position).getMember_store());
        viewholder.price2.setText(mList.get(position).getMember_price2());
        viewholder.store2.setText(mList.get(position).getMember_store2());
        viewholder.price3.setText(mList.get(position).getMember_price3());
        viewholder.store3.setText(mList.get(position).getMember_store3());
        viewholder.price4.setText(mList.get(position).getMember_price4());
        viewholder.store4.setText(mList.get(position).getMember_store4());

        GlideApp.with(viewholder.itemView).load(mList.get(position).getMember_file_path())
                .override(150,150).into(viewholder.file_path);
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}