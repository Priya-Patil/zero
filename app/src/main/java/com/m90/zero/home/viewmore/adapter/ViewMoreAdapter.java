package com.m90.zero.home.viewmore.adapter;

import android.app.Activity;
import android.graphics.Paint;
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
import com.m90.zero.home.HomeActivity;
import com.m90.zero.home.model.ProductDetailsResponse;
import com.m90.zero.productdetail.ProductDetailsActivity;
import com.m90.zero.retrofit.RetrofitClientInstance;
import com.m90.zero.utils.Utilities;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViewMoreAdapter extends RecyclerView.Adapter<ViewMoreAdapter.MyViewHolder> {

    private static final String TAG = "ViewMoreAdapter";

    private Activity mContext;
    ArrayList<ProductDetailsResponse> list;
    private  ItemClickListener itemClickListener;

    public ViewMoreAdapter(Activity context, ArrayList<ProductDetailsResponse> list) {
        this.list = list;
        mContext = context;
    }

    public ViewMoreAdapter(Activity context, ArrayList<ProductDetailsResponse> list, ItemClickListener itemClickListener){
        mContext = context;
        this.list = list;
        this.itemClickListener=itemClickListener;
    }

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    @Override

    public  MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_for_viewmore, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        //  prefManager=new PrefManager(mContext);
        return new  MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, final int i) {
        final ProductDetailsResponse item = list.get(i);

        Log.e("viewMoreAdapter", list.toString());
        viewHolder.tv_title.setText(item.vp_short.get(0).productData.product_name);
        viewHolder.tv_desc.setText(item.vp_short.get(0).productData.short_details);

        try {
            //viewHolder.tv_rate.setText(String.format("%.2f",item.productPricing.unit_price));
            viewHolder.tv_dis_amount.setVisibility(View.GONE);

            if (item.productPricing!=null) {
                if (item.productPricing.unit_price.equals(item.productPricing.discount_price)) {
                    viewHolder.tv_dis_amount.setVisibility(View.GONE);
                    viewHolder.tv_amount.setText(HomeActivity.currency + item.productPricing.unit_price);
                } else {
                    viewHolder.tv_amount.setText(HomeActivity.currency + String.valueOf(String.format("%.2f",Double.parseDouble(item.productPricing.unit_price))));
                    viewHolder.tv_dis_amount.setVisibility(View.VISIBLE);
                    viewHolder.tv_dis_amount.setText( HomeActivity.currency + String.valueOf(String.format("%.2f", Double.parseDouble(item.productPricing.discount_price))));
                    viewHolder.tv_amount.setPaintFlags(viewHolder.tv_amount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }
            }
        }catch (Exception e)
        {
            Log.e(TAG, "onBindViewHolder: "+e.getMessage());
        }

        String phrase = item.productImages.get(0).image_path;
        String[] dateSplit = phrase.split(";");
        for (String singleImg : dateSplit) {
            if (phrase!=null)
            Picasso.with(mContext).load(RetrofitClientInstance.BASE_URL_IMG +singleImg).into(viewHolder.img);
            //Picasso.with(mContext).load("http://api.eurekatalents.in/"+singleImg).into(viewHolder.img);
            Log.e(TAG, "onBindViewHolder: "+singleImg);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle =  new Bundle();
                bundle.putString("product_id", String.valueOf(item.vendor_id.get(0).id));
                bundle.putString("product_name",item.vp_short.get(0).productData.product_name);
                Utilities.launchActivity(mContext, ProductDetailsActivity.class,true,bundle);
            }
        });
       // viewHolder.img.setBackground(mContext.getResources().getDrawable(item.getImage_drawable()));
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView iv_cart;
        RelativeLayout rl_delete;
        int count = 0;
        TextView tv_title, tv_desc,tv_CatName,tv_rate,tv_amount,tv_dis_amount;
        ImageView img;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_desc = itemView.findViewById(R.id.tv_desc);
            tv_rate = itemView.findViewById(R.id.tv_rate);
            tv_amount = itemView.findViewById(R.id.tv_amount);
            tv_CatName = itemView.findViewById(R.id.tv_CatName);
            tv_dis_amount = itemView.findViewById(R.id.tv_dis_amount);
            img = itemView.findViewById(R.id.img);
            //remoteviews.setInt(R.id.tv_dis_amount, "setPaintFlags", Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

            itemView.setOnClickListener(this); // bind the listener
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null) itemClickListener.onClick(view, getAdapterPosition());
        }
    }

    //region Search Filter (setFilter Code)
    public void setFilter(ArrayList<ProductDetailsResponse> newList) {
        list = new ArrayList<>();
        list.addAll(newList);
        notifyDataSetChanged();
    }
}

