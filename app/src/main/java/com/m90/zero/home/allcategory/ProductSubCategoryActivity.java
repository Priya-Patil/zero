package com.m90.zero.home.allcategory;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

import com.m90.zero.R;
import com.m90.zero.databinding.ActivityAllCategoryBinding;
import com.m90.zero.home.allcategory.adapter.AllCategoryAdapter;
import com.m90.zero.home.allcategory.adapter.ProductSubCategoryAdapter;
import com.m90.zero.home.allcategory.api.AllCategoryApi;
import com.m90.zero.home.allcategory.model.CategoryAllDetailsResponse;
import com.m90.zero.home.allcategory.model.ProductSubCategoryDetailsResponse;
import com.m90.zero.home.allcategory.model.ProductSubCategoryResponse;
import com.m90.zero.home.model.CategoryDetailsResponse;
import com.m90.zero.profile.WalletBalanceTableActivity;
import com.m90.zero.profile.model.WalletDetailsResponse;
import com.m90.zero.retrofit.RetrofitClientInstance;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductSubCategoryActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = "ProductSubCategoryActivity" ;
    Activity activity;
    AllCategoryAdapter categoryAdapter;
    ArrayList<ProductSubCategoryDetailsResponse> categoryResponses;
    public  static ActivityAllCategoryBinding binding;
    ProgressDialog progressDialog;
    Bundle bundle;

    CategoryAllDetailsResponse cad;
    CategoryDetailsResponse categoryDetailsResponse;
    Serializable item;
    int name = 0;
    public static int type = 0;
    ProductSubCategoryAdapter productSubCategoryAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_all_category);
        activity = ProductSubCategoryActivity.this;
        progressDialog = new ProgressDialog(this);

        binding.llNameHierarchy.setVisibility(View.VISIBLE);
        binding.txt.setText("Product Sub Category");
        binding.btnBack.setOnClickListener(this);


        if (type == 2) {
            cad = (CategoryAllDetailsResponse) getIntent().getSerializableExtra("serialzable");
            name = Integer.parseInt(cad.id);
            Log.e(TAG, "onCreate: " + name+" "+cad.name);
            binding.tvHierarchy.setText(cad.name);


        }else if (type == 1){
            categoryDetailsResponse = (CategoryDetailsResponse) getIntent().getSerializableExtra("CategoryDetailsResponse");
            name = Integer.parseInt(categoryDetailsResponse.id);
            Log.e(TAG, "onCreate: "+ name+" "+categoryDetailsResponse.name);
            binding.tvHierarchy.setText(categoryDetailsResponse.name);

        }
        getProductSubCategory(name);


        //}

    }

    void getProductSubCategory(int user_id) {

        AllCategoryApi allCategoryApi = RetrofitClientInstance.getRetrofitInstanceServer().
                create(AllCategoryApi.class);

        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        allCategoryApi.getProductSubCategory("http://api.eurekatalents.in/api/category/parent/"+user_id).
                enqueue(new Callback<ProductSubCategoryResponse>() {
            @Override
            public void onResponse(Call<ProductSubCategoryResponse> call, Response<ProductSubCategoryResponse> response) {
                ProductSubCategoryResponse productSubCategoryResponse = response.body();
                categoryResponses = (productSubCategoryResponse).data;

                Log.e("TAG", "onResponse: "+productSubCategoryResponse.toString());

                if (productSubCategoryResponse.status == 200) {
                    Toast.makeText(activity, "" + productSubCategoryResponse.message, Toast.LENGTH_SHORT).show();
                        attachResponse(productSubCategoryResponse.data);


                } else {
                    Toast.makeText(activity, "" + productSubCategoryResponse.message, Toast.LENGTH_SHORT).show();
                    Log.e("onResponse: ", " " + productSubCategoryResponse.message);
                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<ProductSubCategoryResponse> call, Throwable t) {

                progressDialog.dismiss();
                //Utilities.setError(layout1,layout2,txt_error,getResources().getString(R.string.something_went_wrong));
                Log.e("errorchk", t.getMessage());

            }
        });

    }

    private void attachResponse(ArrayList<ProductSubCategoryDetailsResponse> data) {

        binding.rvCategories.setLayoutManager(new GridLayoutManager(this,2));
        // mRecyclerViewCourses.setLayoutManager(glm);
        binding.rvCategories.setItemAnimator(new DefaultItemAnimator());
        binding.rvCategories.setHasFixedSize(true);

        productSubCategoryAdapter = new ProductSubCategoryAdapter(activity, data,
                new ProductSubCategoryAdapter.ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {

                        //Toast.makeText(activity,""+position,Toast.LENGTH_SHORT).show();
                        /*Intent send = new Intent(activity, WalletBalanceTableActivity.class);
                        Bundle b = new Bundle();
                        b.putSerializable("ProductSubCategoryDetailsData",data);
                        send.putExtras(b);
                        activity.startActivity(send);
*/

                        Log.e(TAG," idssss "+data.get(position).id);
                        binding.tvHierarchy.setText(data.get(position).name);
                        getProductSubCategory(Integer.parseInt(data.get(position).id));
                    }
                });

        binding.rvCategories.setAdapter(productSubCategoryAdapter);
        productSubCategoryAdapter.notifyDataSetChanged();

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



}