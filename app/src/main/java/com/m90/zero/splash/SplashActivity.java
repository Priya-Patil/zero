package com.m90.zero.splash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.m90.zero.R;
import com.m90.zero.cart.api.CartApi;
import com.m90.zero.cart.model.CartResponse;
import com.m90.zero.cart.model.CustomItemForPlaceOrder;
import com.m90.zero.home.HomeActivity;
import com.m90.zero.productdetail.model.CartItem;
import com.m90.zero.retrofit.RetrofitClientInstance;
import com.m90.zero.utils.PrefManager;
import com.m90.zero.utils.SessionHelper;
import com.m90.zero.utils.Utilities;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    Activity activity ;
    Handler handler;
    ProgressDialog progressDialog;
    PrefManager prefManager;
    SessionHelper sessionHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        activity = SplashActivity.this;
        progressDialog =   new ProgressDialog(activity);
        prefManager = new PrefManager(activity);
        sessionHelper =  new SessionHelper(activity);

                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run() {

                        if(sessionHelper.isLoggedIn())
                        {
                            getcart(Utilities.getSavedUserData(activity,"userid"),"cart",
                                    Utilities.getSavedUserData(activity,"accesstoken"));
                        }

                        else {
                            Intent mainIntent = new Intent(SplashActivity.this, HomeActivity.class);
                            activity.startActivity(mainIntent);
                            activity.finish();
                        }


                    }
                }, 3000);
    }

    void getcart(String customerId, String wishlistORcart,String code) {

        if (Utilities.isNetworkAvailable(activity)){
            CartApi cartApi = RetrofitClientInstance.getRetrofitInstanceServer().
                    create(CartApi.class);

            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            cartApi.getCart("Bearer "+code,RetrofitClientInstance.BASE_URL+wishlistORcart+"?user_id="+customerId).
                    enqueue(new Callback<CartResponse>() {

                        @Override
                        public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {

                            CartResponse cartResponse = response.body();

                            if (cartResponse.status == 200) {
                                Log.e("onResponse1111: ", " " + cartResponse.toString());
                                prefManager.setCartCount(cartResponse.data.count);
                                Intent mainIntent = new Intent(SplashActivity.this, HomeActivity.class);
                                activity.startActivity(mainIntent);
                                activity.finish();
                            }

                            if (cartResponse.status == 401)
                            {
                                Log.e("TAG", "onResponse: "+cartResponse.message );
                            }

                            progressDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<CartResponse> call, Throwable t) {

                            progressDialog.dismiss();
                            //Utilities.setError(layout1,layout2,txt_error,getResources().getString(R.string.something_went_wrong));
                            Log.e("errorgetcart", t.getMessage());

                        }
                    });
        } else {
            Toast.makeText(activity, getResources().getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
        }
    }
}