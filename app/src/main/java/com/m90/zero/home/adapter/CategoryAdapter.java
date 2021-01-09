package com.m90.zero.home.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.m90.zero.R;
import com.m90.zero.home.allcategory.ProductSubCategoryActivity;
import com.m90.zero.home.model.CategoryDetailsResponse;
import com.m90.zero.home.model.CategoryDetailsResponse;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    private static final String TAG = "CategoryAdapter";

    private Activity mContext;
    ArrayList<CategoryDetailsResponse> list;
    private  ItemClickListener itemClickListener;

    public CategoryAdapter(Activity context, ArrayList<CategoryDetailsResponse> list) {
        this.list = list;
        mContext = context;
    }

    public CategoryAdapter(Activity context, ArrayList<CategoryDetailsResponse> list, ItemClickListener itemClickListener){
        mContext = context;
        this.list = list;
        this.itemClickListener=itemClickListener;
    }

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    @Override

    public  MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, null);

        //  prefManager=new PrefManager(mContext);
        return new  MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, final int i) {
        final CategoryDetailsResponse item = list.get(i);

        Log.e("vlistqq", list.toString());
        viewHolder.tv_category.setText(item.name);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ProductSubCategoryActivity.type = 1;
                Intent send = new Intent(mContext, ProductSubCategoryActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("CategoryDetailsResponse",item);
                send.putExtras(b);
                mContext.startActivity(send);
            }
        });
        //viewHolder.img.setBackground(mContext.getResources().getDrawable(item.getImage_drawable()));
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView tv, tv_category;
        ImageView img;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_category = itemView.findViewById(R.id.tv_category);

            itemView.setOnClickListener(this); // bind the listener
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null) itemClickListener.onClick(view, getAdapterPosition());
        }
    }

    //region Search Filter (setFilter Code)
    public void setFilter(ArrayList<CategoryDetailsResponse> newList) {
        list = new ArrayList<>();
        list.addAll(newList);
        notifyDataSetChanged();
    }
}

