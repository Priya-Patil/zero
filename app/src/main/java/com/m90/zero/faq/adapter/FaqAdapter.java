package com.m90.zero.faq.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.m90.zero.R;
import com.m90.zero.faq.model.FaqDetailsResponce;
import com.m90.zero.utils.PrefManager;

import java.util.ArrayList;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.MyViewHolder> {

    private static final String TAG = "FaqAdapter";

    private Activity mContext;
    ArrayList<FaqDetailsResponce> list;
    private  ItemClickListener itemClickListener;
    int faqOnOff = 0;
    PrefManager prefManager;

    public FaqAdapter(Activity context, ArrayList<FaqDetailsResponce> list) {
        this.list = list;
        mContext = context;
    }

    public FaqAdapter(Activity context, ArrayList<FaqDetailsResponce> list, ItemClickListener itemClickListener){
        mContext = context;
        this.list = list;
        this.itemClickListener=itemClickListener;
    }

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    @Override

    public  MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_faq, null);
        prefManager = new PrefManager(mContext);
        prefManager.setFaqOnOff("1");
        //  prefManager=new PrefManager(mContext);
        return new  MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, final int i) {
        final FaqDetailsResponce item = list.get(i);

        Log.e("vlistqq", list.toString());
        viewHolder.tv_que.setText(item.getQuestion());
        viewHolder.tv_ans.setVisibility(View.GONE);

        viewHolder.tv_que.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (prefManager.getFaqOnOff().equals("1"))
                {
                    viewHolder.tv_ans.setVisibility(View.VISIBLE);
                    viewHolder.view.setVisibility(View.VISIBLE);
                    viewHolder.tv_ans.setText(item.getAnswer());

                    prefManager.setFaqOnOff("0");

                }else
                {
                    viewHolder.tv_ans.setVisibility(View.GONE);
                    viewHolder.view.setVisibility(View.GONE);
                    viewHolder.tv_ans.setText(item.getAnswer());
                    prefManager.setFaqOnOff("1");


                }
            }
        });

        //viewHolder.img.setBackground(mContext.getResources().getDrawable(item.getImage_drawable()));
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView tv_ans, tv_que;
        ImageView img;
        View view;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_que = itemView.findViewById(R.id.tv_que);
            tv_ans = itemView.findViewById(R.id.tv_ans);
            view = itemView.findViewById(R.id.view);

            itemView.setOnClickListener(this); // bind the listener
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null) itemClickListener.onClick(view, getAdapterPosition());
        }
    }

    //region Search Filter (setFilter Code)
    public void setFilter(ArrayList<FaqDetailsResponce> newList) {
        list = new ArrayList<>();
        list.addAll(newList);
        notifyDataSetChanged();
    }
}

