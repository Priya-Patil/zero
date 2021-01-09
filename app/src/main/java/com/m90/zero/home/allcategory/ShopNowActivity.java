package com.m90.zero.home.allcategory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.m90.zero.R;
import com.m90.zero.databinding.ActivityShopNowBinding;
import com.m90.zero.home.allcategory.api.AllCategoryApi;
import com.m90.zero.home.allcategory.model.ProductSubCategoryDetailsResponse;
import com.m90.zero.home.brands.adapter.BrandsListAdapter;
import com.m90.zero.home.brands.model.BrandsDetailListResponce;
import com.m90.zero.home.brands.model.BrandsListResponse;
import com.m90.zero.retrofit.RetrofitClientInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopNowActivity extends AppCompatActivity implements View.OnClickListener {

    public static String TAG = ShopNowActivity.class.getSimpleName();
    ActivityShopNowBinding binding;
    BrandsListAdapter brandsListAdapter;
    ArrayList<BrandsDetailListResponce> brandsDetailListResponces;
    ProgressDialog progressDialog;
    Activity activity;

    ProductSubCategoryDetailsResponse detailsResponse;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_shop_now);
        activity = ShopNowActivity.this;
        progressDialog = new ProgressDialog(activity);

        binding.btnBack.setOnClickListener(this);

        detailsResponse = (ProductSubCategoryDetailsResponse) getIntent().getSerializableExtra("ProductSubCategoryDetailsResponse");
        if (detailsResponse!=null)
        Log.e(TAG, "onCreate: "+detailsResponse.id);

        getShopNow(4);
    }


    void getShopNow(int user_id) {

        AllCategoryApi allCategoryApi = RetrofitClientInstance.getRetrofitInstanceServer().
                create(AllCategoryApi.class);

        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        allCategoryApi.getShowNow(RetrofitClientInstance.BASE_URL+"products/category/"+user_id).
                enqueue(new Callback<BrandsListResponse>() {
                    @Override
                    public void onResponse(Call<BrandsListResponse> call, Response<BrandsListResponse> response) {
                        BrandsListResponse productSubCategoryResponse = response.body();
                        //brandsDetailListResponces = productSubCategoryResponse.discount;

                        Log.e("TAG", "onResponse: "+productSubCategoryResponse.toString());

                        if (productSubCategoryResponse.status == 200) {
                            Toast.makeText(activity, "" + productSubCategoryResponse.message, Toast.LENGTH_SHORT).show();
                            attachShopNowResponse(productSubCategoryResponse.data);


                        } else {
                            Toast.makeText(activity, "" + productSubCategoryResponse.message, Toast.LENGTH_SHORT).show();
                            Log.e("onResponse: ", " " + productSubCategoryResponse.message);
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<BrandsListResponse> call, Throwable t) {

                        progressDialog.dismiss();
                        //Utilities.setError(layout1,layout2,txt_error,getResources().getString(R.string.something_went_wrong));
                        Log.e("errorchk", t.getMessage());

                    }
                });

    }

    private void attachShopNowResponse(ArrayList<BrandsDetailListResponce> data) {

        binding.rvCategories.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.VERTICAL,true));
        // mRecyclerViewCourses.setLayoutManager(glm);
        binding.rvCategories.setItemAnimator(new DefaultItemAnimator());
        binding.rvCategories.setHasFixedSize(true);

        brandsListAdapter = new BrandsListAdapter(activity, data,
                new BrandsListAdapter.ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {

                        //Toast.makeText(activity,""+position,Toast.LENGTH_SHORT).show();

                    }
                });

        binding.rvCategories.setAdapter(brandsListAdapter);
        brandsListAdapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_back:
                onBackPressed();
                break;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}