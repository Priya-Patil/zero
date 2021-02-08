package com.m90.zero.cart.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.m90.zero.R;
import com.m90.zero.cart.CartActivity;
import com.m90.zero.cart.api.CartApi;
import com.m90.zero.cart.model.AddCartResponse;
import com.m90.zero.cart.model.CartResponse;
import com.m90.zero.cart.model.ContentResponse;
import com.m90.zero.login.LoginActivity;
import com.m90.zero.productdetail.model.CartItem;
import com.m90.zero.productdetail.model.ProductDescribeAddheadResponse;
import com.m90.zero.retrofit.RetrofitClientInstance;
import com.m90.zero.utils.Utilities;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "CartAdapter";

    private Activity mContext;
    ArrayList<CartItem> list;
    ArrayList<ContentResponse> list1;

    private  ItemClickListener itemClickListener;
    String[] quantity = {"1","2","3","4","5","6","7","8","9","10"};
    ProgressDialog progressDialog;

    ArrayList<CartItem> cartItemArrayList;
    CartAdapter cartAdapter;
    String chk ;
    String accesstoken;
    //String userid = "12";

    public CartAdapter(Activity activity, ArrayList<CartItem> items, String accesstoken, ItemClickListener itemClickListener) {
        mContext = activity;
        this.list = items;
        this.accesstoken = accesstoken;
        this.itemClickListener=itemClickListener;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(mContext,quantity[position] , Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    @Override

    public  MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, null);

        progressDialog = new ProgressDialog(mContext);
        //  prefManager=new PrefManager(mContext);
        return new  MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, final int i) {

        final CartItem item = list.get(i);

        viewHolder.ll_movetocart.setVisibility(View.GONE);
        viewHolder.ll_plusminus.setVisibility(View.VISIBLE);

        String userid =  Utilities.getSavedUserData(mContext,"userid");
        Log.e("cartAsnb", item.toString());
        viewHolder.tv_title.setText(item.name);
        viewHolder.tv_amount.setText(String.valueOf(item.price));
        viewHolder.tv_qty.setText(String.valueOf(item.quantity));
        Picasso.with(mContext).load(RetrofitClientInstance.BASE_URL_IMG +item.image).into(viewHolder.img);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item,quantity);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        viewHolder.spin_qty.setAdapter(dataAdapter);

        viewHolder.spin_qty.setSelected(false); // must
        viewHolder.spin_qty.setSelection(0,true); //must
        viewHolder.spin_qty.setOnItemSelectedListener(this);

        int totalSingle =  (list.get(i).getQuantity())*list.get(i).getPrice();
        Log.e(TAG, "onClickAdd: "+totalSingle );
        viewHolder.tv_amount.setText(String.valueOf(totalSingle));

        viewHolder.spin_qty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //Toast.makeText(mContext,quantity[position],Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onItemSelected: "+ Utilities.getSavedUserData(mContext,"userid")+" "+item.id+" "+quantity[position]);
                updateQuantity(userid,item.id,quantity[position],accesstoken);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        viewHolder.ivSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subSingle(userid,item.id,accesstoken,i,viewHolder.tv_amount);
            }
        });

        viewHolder.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: "+i);

                addSingle(userid,item.id,accesstoken,i,viewHolder.tv_amount);
            }
        });

        viewHolder.iv_rmv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e(TAG, "onClick: "+userid+" "+item.id+" "+accesstoken );
                removeSingleItem(userid,item.id,accesstoken);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
       // return (list == null) ? 0 : list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView tv, tv_title;
        TextView tv_qty,tv_amount;
        Spinner spin_qty;
        private ImageView ivAdd,img;
        private TextView tvQty;
        private ImageView ivSub;
        private ImageView iv_rmv;
        private LinearLayout ll_movetocart;
        LinearLayout ll_plusminus;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_amount = itemView.findViewById(R.id.tv_amount);
            tv_qty = itemView.findViewById(R.id.tv_qty);
            spin_qty = itemView.findViewById(R.id.spin_qty);
            ivAdd = (ImageView) itemView.findViewById(R.id.iv_add);
            tvQty = (TextView) itemView.findViewById(R.id.tv_qty);
            ivSub = (ImageView) itemView.findViewById(R.id.iv_sub);
            iv_rmv = (ImageView) itemView.findViewById(R.id.iv_rmv);
            img = (ImageView) itemView.findViewById(R.id.img);
            ll_movetocart =  itemView.findViewById(R.id.ll_movetocart);
            ll_plusminus =  itemView.findViewById(R.id.ll_plusminus);


            itemView.setOnClickListener(this); // bind the listener
            ivAdd.setOnClickListener(this);
            ivSub.setOnClickListener(this);
            iv_rmv.setOnClickListener(this);
            ll_movetocart.setOnClickListener(this);
            ll_plusminus.setOnClickListener(this);

        }

       /* @Override
        public void onClick(View view) {
            if (itemClickListener != null) itemClickListener.onClick(view, getAdapterPosition());
        }*/

        @Override
        public void onClick(View v) {

            if (v.getId() == ivAdd.getId()){

                View tempview = null;//= null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.DONUT) {
                    tempview = (View) ivAdd.getTag(R.integer.btn_plus_view);
                }
                int number = Integer.parseInt(tvQty.getText().toString()) + 1;
                tvQty.setText(String.valueOf(number));
                list.get(getAdapterPosition()).setQuantity(number);

                int amt = list.get(getAdapterPosition()).getPrice();
                //tv_amount.setText(String.valueOf(amt));

                Log.e(TAG, "onClick:Add  "+number +" "+amt );

                int price = 0;
                for(int position = 0 ;position<list.size()-1;position++){
                    price = amt * number;
                    Log.e(TAG, "QtyAndPriceAdd: "+price +" "+number+" "+amt);

                    tv_amount.setText(String.valueOf(price));
                    //CartActivity.binding.tvTotalCartAmt.setText(HomeActivity.currency+" "+price);
                }

            } else if(v.getId() == ivSub.getId()) {

                View tempview = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.DONUT) {
                    tempview = (View) ivSub.getTag(R.integer.btn_minus_view);
                }
                //TextView tv = (TextView) tempview.findViewById(R.id.tv_qty);
                int number =0;
                if(number!=1) {
                    number = number-1;
                    number = Integer.parseInt(tvQty.getText().toString()) - 1;

                    if (number == -1)
                    {
                        number=0;
                    }
                }
                tvQty.setText(String.valueOf(number));
                list.get(getAdapterPosition()).setQuantity(number);

                int amt = list.get(getAdapterPosition()).getPrice();

                Log.e(TAG, "onClick:Sub  "+number +" "+amt );

                int price = 0;
                for(int position = 0 ;position<list.size()-1;position++){
                    price = amt * number;
                    Log.e(TAG, "QtyAndPriceSub: "+price +" "+number+" "+amt);

                    tv_amount.setText(String.valueOf(price));
                    //CartActivity.binding.tvTotalCartAmt.setText(HomeActivity.currency+" "+price);
                }
            }
        }


    }


    void updateQuantity(String userid, String productId, String quantity, String accesstoken) {

        CartApi cartApi = RetrofitClientInstance.getRetrofitInstanceServer().
                create(CartApi.class);

        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        // pbLoading.setProgressStyle(R.id.abbreviationsBar);
        progressDialog.show();
        cartApi.updateQuantity("Bearer "+accesstoken,userid,productId,quantity).
                enqueue(new Callback<AddCartResponse>() {

                    @Override
                    public void onResponse(Call<AddCartResponse> call, Response<AddCartResponse> response) {

                        AddCartResponse cartResponse = response.body();
                       // Log.e(TAG, "updateQuantity: "+cartResponse.toString());

                        if (cartResponse.status == 200)
                        {
                            Toast.makeText(mContext,cartResponse.message,Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "updateQuantity: "+cartResponse.toString());

                            //getcart(userid);
                            getcart(userid,accesstoken);
                        }

                        if (cartResponse.status == 401)
                        {
                            Log.e(TAG, "onResponse: "+cartResponse.message );
                            authenticationDialog(cartResponse.message);
                        }

                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<AddCartResponse> call, Throwable t) {

                        progressDialog.dismiss();
                        //Utilities.setError(layout1,layout2,txt_error,getResources().getString(R.string.something_went_wrong));
                        Log.e("errorchk", t.getMessage());

                    }
                });

    }

    void getcart(String customerId,String accesstoken) {

        cartItemArrayList=new ArrayList<CartItem>();

        CartApi cartApi = RetrofitClientInstance.getRetrofitInstanceServer().
                create(CartApi.class);

        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        // pbLoading.setProgressStyle(R.id.abbreviationsBar);
        progressDialog.show();
        cartApi.getCart("Bearer "+accesstoken,RetrofitClientInstance.BASE_URL+"cart?user_id="+customerId).
                enqueue(new Callback<CartResponse>() {

                    @Override
                    public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {

                        CartResponse cartResponse = response.body();
                        Log.e("onResponse: ", " " + cartResponse.toString());

                        if (cartResponse.status == 200) {

                            Iterator myVeryOwnIterator = cartResponse.data.items.keySet().iterator();
                            while(myVeryOwnIterator.hasNext()) {
                                String key=(String)myVeryOwnIterator.next();
                                String value=cartResponse.data.items.get(key).name;
                                cartItemArrayList.add(cartResponse.data.items.get(key));
                            }
                            Log.e( "chkonResponse: ", cartItemArrayList.toString());
                            setCartData(cartItemArrayList,accesstoken);
                        }

                        if (cartResponse.status == 401)
                        {
                            Log.e(TAG, "onResponse: "+cartResponse.message );
                            authenticationDialog(cartResponse.message);
                        }

                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<CartResponse> call, Throwable t) {

                        progressDialog.dismiss();
                        //Utilities.setError(layout1,layout2,txt_error,getResources().getString(R.string.something_went_wrong));
                        Log.e("errorchk11", t.getMessage());

                    }
                });
    }

    private void setCartData(ArrayList<CartItem> productDescribeAddResons,String accesstoken) {
        CartActivity.binding.rvCart.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        // mRecyclerViewCourses.setLayoutManager(glm);
        CartActivity.binding.rvCart.setItemAnimator(new DefaultItemAnimator());
        CartActivity.binding.rvCart.setHasFixedSize(true);

        cartAdapter = new CartAdapter(mContext, productDescribeAddResons,
                accesstoken, new CartAdapter.ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {

                    }
                });
        CartActivity.binding.rvCart.setAdapter(cartAdapter);

    }

    void addSingle(String user_id, String product_id, String accesstoken, int pos, TextView tv_amount) {
        CartApi cartApi  = RetrofitClientInstance.getRetrofitInstanceServer().
                create(CartApi.class);
        cartApi.addCart("Bearer "+accesstoken,RetrofitClientInstance.BASE_URL+"cart/add?user_id="+user_id+"&product_id="+product_id).
                enqueue(new Callback<AddCartResponse>() {
                    @Override
                    public void onResponse(Call<AddCartResponse> call, Response<AddCartResponse> response) {
                        Log.e( "chkonResponse: ", response.body().toString());

                        AddCartResponse cartResponse = response.body();

                        if (cartResponse.status == 200) {
                            Toast.makeText(mContext,cartResponse.message,Toast.LENGTH_SHORT).show();


                                getcart(user_id,accesstoken);
                            //}
                        }

                        if (cartResponse.status == 401)
                        {
                            Log.e(TAG, "onResponse: "+cartResponse.message );
                            authenticationDialog(cartResponse.message);
                        }

                    }

                    @Override
                    public void onFailure(Call<AddCartResponse> call, Throwable t) {
                        Log.e("errorchk", t.getMessage());

                    }
                });
    }

    void subSingle(String user_id, String product_id, String accesstoken, int pos, TextView tv_amount) {

        CartApi regApi = RetrofitClientInstance.getRetrofitInstanceServer().
                create(CartApi.class);

        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        regApi.subSingle("Bearer "+accesstoken,user_id,product_id).
                enqueue(new Callback<ProductDescribeAddheadResponse>() {

                    @Override
                    public void onResponse(Call<ProductDescribeAddheadResponse> call, Response<ProductDescribeAddheadResponse> response) {

                        ProductDescribeAddheadResponse cartResponse = response.body();

                        if (cartResponse.status == 200) {
                            Toast.makeText(mContext,cartResponse.message,Toast.LENGTH_SHORT).show();
                                getcart(user_id,accesstoken);


                        }

                        if (cartResponse.status == 401)
                        {
                            Log.e(TAG, "onResponse: "+cartResponse.message );
                            authenticationDialog(cartResponse.message);
                        }

                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<ProductDescribeAddheadResponse> call, Throwable t) {

                        progressDialog.dismiss();
                        //Utilities.setError(layout1,layout2,txt_error,getResources().getString(R.string.something_went_wrong));
                        Log.e("errorchk", t.getMessage());

                    }
                });

    }

    void removeSingleItem(String user_id, String product_id, String accesstoken) {


        CartApi regApi = RetrofitClientInstance.getRetrofitInstanceServer().
                create(CartApi.class);

        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        regApi.removeSingleItem("Bearer "+accesstoken,RetrofitClientInstance.BASE_URL+"cart/remove/all?user_id="+user_id+"&product_id="+product_id).
                enqueue(new Callback<ProductDescribeAddheadResponse>() {

                    @Override
                    public void onResponse(Call<ProductDescribeAddheadResponse> call, Response<ProductDescribeAddheadResponse> response) {

                        ProductDescribeAddheadResponse cartResponse = response.body();

                        if (cartResponse.status == 200) {
                            Toast.makeText(mContext,cartResponse.message,Toast.LENGTH_SHORT).show();
                            getcart(user_id,accesstoken);
                        }

                        if (cartResponse.status == 401)
                        {
                            Log.e(TAG, "onResponse: "+cartResponse.message );
                            authenticationDialog(cartResponse.message);
                        }

                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<ProductDescribeAddheadResponse> call, Throwable t) {

                        progressDialog.dismiss();
                        //Utilities.setError(layout1,layout2,txt_error,getResources().getString(R.string.something_went_wrong));
                        Log.e("errorchk", t.getMessage());

                    }
                });

    }


    private void authenticationDialog(String errorMessage) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext);
        mBuilder.setTitle(errorMessage);
        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

                dialog.dismiss();
                Utilities.launchActivity(mContext, LoginActivity.class,true);

            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_rounded_background);
        mDialog.show();
    }



}

