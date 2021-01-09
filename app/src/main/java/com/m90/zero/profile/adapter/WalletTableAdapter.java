package com.m90.zero.profile.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.m90.zero.R;
import com.m90.zero.home.HomeActivity;

import com.m90.zero.profile.model.WalletDetailsResponse;
import com.m90.zero.profile.model.WalletDetailsResponse;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class WalletTableAdapter extends RecyclerView.Adapter<WalletTableAdapter.MyViewHolder> {

    private static final String TAG = "WalletTableAdapter";

    private Activity mContext;
    ArrayList<WalletDetailsResponse> list;
    private  ItemClickListener itemClickListener;

    //ItemWallettableBinding binding;
    LayoutInflater inflater;

    public WalletTableAdapter(Activity context, ArrayList<WalletDetailsResponse> list) {
        this.list = list;
        mContext = context;
    }

    public WalletTableAdapter(Activity context, ArrayList<WalletDetailsResponse> list, ItemClickListener itemClickListener){
        mContext = context;
        this.list = list;
        this.itemClickListener=itemClickListener;
    }

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    @Override

    public  MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

       // binding = DataBindingUtil.inflate(in);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wallettable, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));

        //  prefManager=new PrefManager(mContext);
        return new  MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, final int i) {
        final WalletDetailsResponse item = list.get(i);

        String c = item.created_at;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");

        Date date= null;
        String dateFormatdd="";
        try {
            date = format.parse(c);
            Log.e(TAG," dt "+date+" kk "+format.format(date));
            dateFormatdd = format.format(date);

        } catch (ParseException e) {
            Log.e(TAG," "+e.getMessage());
        }

        Log.e("vlist", list.toString());
        viewHolder.tv_srno.setText(String.valueOf(item.id));
        viewHolder.tv_date.setText(dateFormatdd);
        viewHolder.tv_from.setText(item.sponsor.sponsor_code);
        viewHolder.tv_credit.setText(item.credit_amount);
        viewHolder.tv_debit.setText(item.debit_amount);
        viewHolder.tv_balance.setText(item.balance_amount);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView iv_cart;
        RelativeLayout rl_delete;
        int count = 0;
        TextView tv_srno, tv_date,tv_from,tv_credit,tv_debit,tv_balance;
        ImageView img;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_srno = itemView.findViewById(R.id.tv_srno);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_from = itemView.findViewById(R.id.tv_from);
            tv_credit = itemView.findViewById(R.id.tv_credit);
            tv_debit = itemView.findViewById(R.id.tv_debit);
            tv_balance = itemView.findViewById(R.id.tv_balance);

            itemView.setOnClickListener(this); // bind the listener
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null) itemClickListener.onClick(view, getAdapterPosition());
        }
    }

    //region Search Filter (setFilter Code)
    public void setFilter(ArrayList<WalletDetailsResponse> newList) {
        list = new ArrayList<>();
        list.addAll(newList);
        notifyDataSetChanged();
    }
}

