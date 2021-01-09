package com.m90.zero.profile.adapter;

import android.app.Activity;
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

import com.m90.zero.profile.model.DownlineResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DownlineAdapter extends RecyclerView.Adapter<DownlineAdapter.MyViewHolder> {

    private static final String TAG = "DownlineAdapter";

    private Activity mContext;
    ArrayList<DownlineResponse> list;
    private  ItemClickListener itemClickListener;

    public DownlineAdapter(Activity context, ArrayList<DownlineResponse> list) {
        this.list = list;
        mContext = context;
    }

    public DownlineAdapter(Activity context, ArrayList<DownlineResponse> list, ItemClickListener itemClickListener){
        mContext = context;
        this.list = list;
        this.itemClickListener=itemClickListener;
    }

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    @Override

    public  MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_downline, null);

        //  prefManager=new PrefManager(mContext);
        return new  MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, final int i) {
        final DownlineResponse item = list.get(i);

        Log.e("vlist", list.toString());
        viewHolder.tv_name.setText(item.name);
        viewHolder.tv_mobile.setText(item.mobile_number);
        viewHolder.tv_email.setText(item.email);
        //viewHolder.iv_imgWallet.setBackground(mContext.getResources().getDrawable(item.avatar));
        //viewHolder.iv_imgWallet.set(item.avatar);

         Picasso.with(mContext).load("http://api.eurekatalents.in/"+item.avatar).into(viewHolder.iv_imgWallet);

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView iv_cart;
        RelativeLayout rl_delete;
        int count = 0;
        TextView tv_name, tv_mobile,tv_email,tv_amount;
        ImageView iv_imgWallet;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_mobile = itemView.findViewById(R.id.tv_mobile);
            tv_email = itemView.findViewById(R.id.tv_email);
            iv_imgWallet = itemView.findViewById(R.id.iv_imgWallet);

            itemView.setOnClickListener(this); // bind the listener
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null) itemClickListener.onClick(view, getAdapterPosition());
        }
    }

    //region Search Filter (setFilter Code)
    public void setFilter(ArrayList<DownlineResponse> newList) {
        list = new ArrayList<>();
        list.addAll(newList);
        notifyDataSetChanged();
    }
}

