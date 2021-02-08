package com.m90.zero.home.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.m90.zero.R;
import com.m90.zero.home.model.ProductDetailsResponse;
import com.m90.zero.home.model.ProductGroupsDetailsResponse;
import com.m90.zero.home.viewmore.ViewMoreActivity;
import com.m90.zero.utils.Utilities;

import java.util.ArrayList;

public class ProductGroupsAdapter extends RecyclerView.Adapter<ProductGroupsAdapter.MyViewHolder> {

    private static final String TAG = "ProductGroupsAdapter";

    private Activity mContext;
    ArrayList<ProductGroupsDetailsResponse> list;
    private  ItemClickListener itemClickListener;
    ArrayList<ProductDetailsResponse> productGroupsResponses;
    ProductDetailssAdapter topRatedProductsAdapter;

    public ProductGroupsAdapter(Activity context, ArrayList<ProductGroupsDetailsResponse> list) {
        this.list = list;
        mContext = context;
    }

    public ProductGroupsAdapter(Activity context, ArrayList<ProductGroupsDetailsResponse> list, ItemClickListener itemClickListener){
        mContext = context;
        this.list = list;
        this.itemClickListener=itemClickListener;
    }

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    @Override

    public  MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producttitle, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        //  prefManager=new PrefManager(mContext);
        return new  MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, final int i) {


        final ProductGroupsDetailsResponse item = list.get(i);

        Log.e("vlistqq", list.toString());
        viewHolder.tv_category.setText(item.title);

        setProductGroups(item.vendor,viewHolder.recyclerViewCourses);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "onClick: "+ item.vendor.get(0).vendor_id.get(0).id);
                Bundle bundle = new Bundle();
                bundle.putParcelable("ProductGroupsDetailsResponse",item);
                bundle.putInt("vendorid",item.group_id);
                Utilities.launchActivity(mContext,ViewMoreActivity.class,false,bundle);
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
        RecyclerView recyclerViewCourses;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_category = itemView.findViewById(R.id.tv_category);
            recyclerViewCourses = itemView.findViewById(R.id.recyclerViewCourses);

            itemView.setOnClickListener(this); // bind the listener
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null) itemClickListener.onClick(view, getAdapterPosition());
        }
    }

    //region Search Filter (setFilter Code)
    public void setFilter(ArrayList<ProductGroupsDetailsResponse> newList) {
        list = new ArrayList<>();
        list.addAll(newList);
        notifyDataSetChanged();
    }


    void setProductGroups(ArrayList<ProductDetailsResponse> productGroups, RecyclerView mRecyclerViewCourses)
    {
        Log.e("TAG", "setProductGroups: " );
        productGroupsResponses = productGroups;

        //LinearLayoutManager glm = new LinearLayoutManager(getApplicationContext());
        mRecyclerViewCourses.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL,
                false));
        //binding.recyclerViewCourses.setLayoutManager(glm);
        mRecyclerViewCourses.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewCourses.setHasFixedSize(true);

        topRatedProductsAdapter = new ProductDetailssAdapter(mContext, productGroupsResponses,
                new ProductDetailssAdapter.ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {


                    }
                });
        mRecyclerViewCourses.setAdapter(topRatedProductsAdapter);
    }
}

