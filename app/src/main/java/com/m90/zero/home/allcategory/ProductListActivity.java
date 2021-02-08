package com.m90.zero.home.allcategory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
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
import com.m90.zero.home.allcategory.adapter.ProductListOfSubcategoryAdapter;
import com.m90.zero.home.allcategory.api.AllCategoryApi;
import com.m90.zero.home.allcategory.model.ProductListDetailsResponse;
import com.m90.zero.home.allcategory.model.ProductListResponse;
import com.m90.zero.home.allcategory.model.ProductSubCategoryDetailsResponse;
import com.m90.zero.home.search.api.SearchApi;
import com.m90.zero.retrofit.RetrofitClientInstance;
import com.m90.zero.home.search.model.SearchCategoryMainResponse;
import com.m90.zero.utils.Utilities;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListActivity extends AppCompatActivity implements View.OnClickListener {

    public static String TAG = ProductListActivity.class.getSimpleName();
    ActivityShopNowBinding binding;
    ProductListOfSubcategoryAdapter productListOfSubcategoryAdapter;
    ProgressDialog progressDialog;
    Activity activity;

    ProductSubCategoryDetailsResponse detailsResponse;
    String forCategoryOrSearch,characters,categoryOrBrand;
    int searchid;
    int productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_shop_now);
        activity = ProductListActivity.this;
        progressDialog = new ProgressDialog(activity);

        binding.btnBack.setOnClickListener(this);
        forCategoryOrSearch = getIntent().getStringExtra("ForCategoryOrSearch");
        characters = getIntent().getStringExtra("characters");
        categoryOrBrand = getIntent().getStringExtra("categoryOrBrand");
        searchid = getIntent().getIntExtra("searchid",0);

        Log.e( "onCreate: ",  characters+categoryOrBrand);


         //getSearchCategoryNew( characters, searchid, categoryOrBrand) ;



        try {
            forCategoryOrSearch = getIntent().getStringExtra("ForCategoryOrSearch");

            Log.e(TAG, "forCategoryOrSearch: " +forCategoryOrSearch);

            if (forCategoryOrSearch.equals("ForCategory"))
            {
                detailsResponse = getIntent().getParcelableExtra("ProductSubCategoryDetailsResponse");
                if (detailsResponse != null)
                productId = detailsResponse.id;
                Log.e(TAG, "onCreate: " + detailsResponse.id);

            }else if (forCategoryOrSearch.equals("ForSearch")) {
                searchid = getIntent().getIntExtra("searchid",0);
                productId = searchid;
                Log.e(TAG, "onCreateaa: " + productId);
            }

            getShopNow(productId);
        }catch (Exception e)
        {
            Log.e(TAG, "onCreate: " + e.getMessage());
        }
    }


    void getShopNow(int user_id) {

        if (Utilities.isNetworkAvailable(activity)){
        AllCategoryApi allCategoryApi = RetrofitClientInstance.getRetrofitInstanceServer().
                create(AllCategoryApi.class);

        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        allCategoryApi.getShopNow(RetrofitClientInstance.BASE_URL+"products/category/"+user_id).
                enqueue(new Callback<ProductListResponse>() {
                    @Override
                    public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {
                        ProductListResponse productListResponse = response.body();
                        //brandsDetailListResponces = productSubCategoryResponse.discount;

                        Log.e("TAG", "onResponse11: "+productListResponse.toString());

                        if (productListResponse.status == 200) {
                            Toast.makeText(activity, "" + productListResponse.message, Toast.LENGTH_SHORT).show();
                            attachShopNowResponse(productListResponse.data);


                        } else {
                            Toast.makeText(activity, "" + productListResponse.message, Toast.LENGTH_SHORT).show();
                            Log.e("onResponse: ", " " + productListResponse.message);
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<ProductListResponse> call, Throwable t) {

                        progressDialog.dismiss();
                        //Utilities.setError(layout1,layout2,txt_error,getResources().getString(R.string.something_went_wrong));
                        Log.e("errorchk", t.getMessage());

                    }
                });
        } else {
            Toast.makeText(activity, getResources().getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
        }
    }

    private void attachShopNowResponse(ArrayList<ProductListDetailsResponse> data) {

        binding.rvCategories.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.VERTICAL,false));
        // mRecyclerViewCourses.setLayoutManager(glm);
        binding.rvCategories.setItemAnimator(new DefaultItemAnimator());
        binding.rvCategories.setHasFixedSize(true);

        productListOfSubcategoryAdapter = new ProductListOfSubcategoryAdapter(activity, data,
                new ProductListOfSubcategoryAdapter.ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {

                        //Toast.makeText(activity,""+position,Toast.LENGTH_SHORT).show();

                    }
                });

        binding.rvCategories.setAdapter(productListOfSubcategoryAdapter);
        productListOfSubcategoryAdapter.notifyDataSetChanged();

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



    private void attachShopNowResponseforSearch(ArrayList<ProductListDetailsResponse> data) {

        binding.rvCategories.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.VERTICAL,false));
        // mRecyclerViewCourses.setLayoutManager(glm);
        binding.rvCategories.setItemAnimator(new DefaultItemAnimator());
        binding.rvCategories.setHasFixedSize(true);

        productListOfSubcategoryAdapter = new ProductListOfSubcategoryAdapter(activity, data,
                new ProductListOfSubcategoryAdapter.ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {

                        //Toast.makeText(activity,""+position,Toast.LENGTH_SHORT).show();

                    }
                });

        binding.rvCategories.setAdapter(productListOfSubcategoryAdapter);
        productListOfSubcategoryAdapter.notifyDataSetChanged();

    }

}