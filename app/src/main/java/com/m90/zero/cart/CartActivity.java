package com.m90.zero.cart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.m90.zero.R;
import com.m90.zero.cart.api.CartApi;
import com.m90.zero.cart.model.AddCartResponse;
import com.m90.zero.cart.model.CartResponse;
import com.m90.zero.databinding.ActivityCartBinding;
import com.m90.zero.retrofit.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    public static String TAG = CartActivity.class.getSimpleName();
    ActivityCartBinding binding;
    ProgressDialog progressDialog;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_cart);
        activity = CartActivity.this;
        progressDialog =  new ProgressDialog(activity);

        getcart(String.valueOf(10));

    }


    void getcart(String customerId) {

        CartApi cartApi = RetrofitClientInstance.getRetrofitInstanceServer().
                create(CartApi.class);

        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        // pbLoading.setProgressStyle(R.id.abbreviationsBar);
        progressDialog.show();
        cartApi.getCart(customerId).
                enqueue(new Callback<CartResponse>() {

                    @Override
                    public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {

                        CartResponse cartResponse = response.body();
                        Toast.makeText(activity, "" + cartResponse.toString(), Toast.LENGTH_SHORT).show();
                        Log.e("onResponse: ", " " + cartResponse.toString());
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<CartResponse> call, Throwable t) {

                        progressDialog.dismiss();
                        //Utilities.setError(layout1,layout2,txt_error,getResources().getString(R.string.something_went_wrong));
                        Log.e("errorchk", t.getMessage());

                    }
                });

    }

    void addcart(String first_name, String last_name, String mobile_number,String email,
                 String password, String password_confirmation, String sponsor_by) {


        CartApi regApi = RetrofitClientInstance.getRetrofitInstanceServer().
                create(CartApi.class);

        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        // pbLoading.setProgressStyle(R.id.abbreviationsBar);
        progressDialog.show();
        regApi.addCart(mobile_number,email,password,password_confirmation, sponsor_by).
                enqueue(new Callback<AddCartResponse>() {

                    @Override
                    public void onResponse(Call<AddCartResponse> call, Response<AddCartResponse> response) {

                        AddCartResponse cartResponse = response.body();
                        if (cartResponse.status == 200)
                        {
                        Toast.makeText(activity, "" + cartResponse.message, Toast.LENGTH_SHORT).show();
                            Log.e("onResponse: ", " " + cartResponse.message);
                        }
                        else {
                            Toast.makeText(activity, "" + cartResponse.message.toString(), Toast.LENGTH_SHORT).show();
                            Log.e("onResponse: ", " " + new Gson().toJson(cartResponse.message));
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

}