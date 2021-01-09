package com.m90.zero.home.adapter;

import android.app.Activity;
import android.graphics.Paint;
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
import com.m90.zero.utils.Utilities;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductDetailssAdapter extends RecyclerView.Adapter<ProductDetailssAdapter.MyViewHolder> {

    private static final String TAG = "TopRatedProductsAdapter";

    private Activity mContext;
    ArrayList<ProductDetailsResponse> list;
    private  ItemClickListener itemClickListener;

    public ProductDetailssAdapter(Activity context, ArrayList<ProductDetailsResponse> list) {
        this.list = list;
        mContext = context;
    }

    public ProductDetailssAdapter(Activity context, ArrayList<ProductDetailsResponse> list, ItemClickListener itemClickListener){
        mContext = context;
        this.list = list;
        this.itemClickListener=itemClickListener;
    }

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    @Override

    public  MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_for_category_grid, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        //  prefManager=new PrefManager(mContext);
        return new  MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, final int i) {
        final ProductDetailsResponse item = list.get(i);

        Log.e("vlist", list.toString());
        viewHolder.tv_title.setText(item.name);
        viewHolder.tv_desc.setText(item.short_details);
        viewHolder.tv_rate.setText(String.valueOf(item.rating));
        viewHolder.tv_dis_amount.setVisibility(View.GONE);

        if (item.unit_price.equals(item.discount_price)) {
            viewHolder.tv_dis_amount.setVisibility(View.GONE);
            viewHolder.tv_amount.setText(HomeActivity.currency + String.valueOf(item.unit_price));
        }else {
            viewHolder.tv_amount.setText(HomeActivity.currency + String.valueOf(item.unit_price));
            viewHolder.tv_dis_amount.setVisibility(View.VISIBLE);
            viewHolder.tv_dis_amount.setText("( "+HomeActivity.currency + String.valueOf(item.discount_price)+" )");
            viewHolder.tv_amount.setPaintFlags(viewHolder.tv_amount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        String phrase = item.pictures;
        String[] dateSplit = phrase.split(";");
        for (String singleImg : dateSplit) {
            Picasso.with(mContext).load("http://api.eurekatalents.in/"+singleImg).into(viewHolder.img);
            Log.e(TAG, "onBindViewHolder: "+singleImg);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 /* ProductSubCategoryActivity.type = 1;
                Intent send = new Intent(mContext, ProductSubCategoryActivity.class);
                Bundle b = new Bundle();
                //b.putSerializable("ProductGroupsDetailsResponse",item);
                //send.putExtras(b);
                mContext.startActivity(send);*/
                Utilities.launchActivity(mContext, ProductDetailsActivity.class,false);
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
        TextView tv_title, tv_desc,tv_rate,tv_amount,tv_dis_amount;
        ImageView img;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_desc = itemView.findViewById(R.id.tv_desc);
            tv_rate = itemView.findViewById(R.id.tv_rate);
            tv_amount = itemView.findViewById(R.id.tv_amount);
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

