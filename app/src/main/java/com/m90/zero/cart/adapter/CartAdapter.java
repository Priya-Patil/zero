package com.m90.zero.cart.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.m90.zero.R;
import com.m90.zero.home.allcategory.ProductSubCategoryActivity;
import com.m90.zero.home.allcategory.model.CategoryAllDetailsResponse;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    private static final String TAG = "CartAdapter";

    private Activity mContext;
    ArrayList<CategoryAllDetailsResponse> list;
    private  ItemClickListener itemClickListener;

    public CartAdapter(Activity context, ArrayList<CategoryAllDetailsResponse> list) {
        this.list = list;
        mContext = context;
    }

    public CartAdapter(Activity context, ArrayList<CategoryAllDetailsResponse> list, ItemClickListener itemClickListener){
        mContext = context;
        this.list = list;
        this.itemClickListener=itemClickListener;
    }

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    @Override

    public  MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_allcategory_grid, null);

        //  prefManager=new PrefManager(mContext);
        return new  MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, final int i) {
        final CategoryAllDetailsResponse item = list.get(i);

        Log.e("vlitCae", list.toString());
        viewHolder.tv_title.setText(item.name);
        viewHolder.iv_explore.setVisibility(View.GONE);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductSubCategoryActivity.type = 2;

                Intent send = new Intent(mContext,ProductSubCategoryActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("serialzable",item);
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


        TextView tv, tv_title;
        ImageView img,iv_explore;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            iv_explore = itemView.findViewById(R.id.iv_explore);

            itemView.setOnClickListener(this); // bind the listener
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null) itemClickListener.onClick(view, getAdapterPosition());
        }
    }

    //region Search Filter (setFilter Code)
    public void setFilter(ArrayList<CategoryAllDetailsResponse> newList) {
        list = new ArrayList<>();
        list.addAll(newList);
        notifyDataSetChanged();
    }
}

