package com.m90.zero.order.adapter;

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
import com.m90.zero.order.OrderResponse;

import java.util.ArrayList;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.MyViewHolder> {

    private static final String TAG = "CartAdapter";

    private Activity mContext;
    ArrayList<OrderResponse> list;
    private  ItemClickListener itemClickListener;

    public OrdersAdapter(Activity context, ArrayList<OrderResponse> list) {
        this.list = list;
        mContext = context;
    }

    public OrdersAdapter(Activity context, ArrayList<OrderResponse> list, ItemClickListener itemClickListener){
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

        //  prefManager=new PrefManager(mContext);
        return new  MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, final int i) {
        final OrderResponse item = list.get(i);

        Log.e("vlist", list.toString());
        viewHolder.tv_title.setText(item.getTitle());
        viewHolder.tv_desc.setText(item.getStatus());
        viewHolder.tv_rate.setText(item.getAmount());
        viewHolder.tv_amount.setText(HomeActivity.currency+item.getAmount());
        viewHolder.img.setBackground(mContext.getResources().getDrawable(item.getImg()));
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView iv_cart;
        RelativeLayout rl_delete;
        int count = 0;
        TextView tv_title, tv_desc,tv_rate,tv_amount;
        ImageView img;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_desc = itemView.findViewById(R.id.tv_desc);
            tv_rate = itemView.findViewById(R.id.tv_rate);
            tv_amount = itemView.findViewById(R.id.tv_amount);
            img = itemView.findViewById(R.id.img);

            itemView.setOnClickListener(this); // bind the listener
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null) itemClickListener.onClick(view, getAdapterPosition());
        }
    }

    //region Search Filter (setFilter Code)
    public void setFilter(ArrayList<OrderResponse> newList) {
        list = new ArrayList<>();
        list.addAll(newList);
        notifyDataSetChanged();
    }
}

