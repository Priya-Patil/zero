package com.m90.zero.home.allcategory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.m90.zero.R;
import com.m90.zero.databinding.ActivityAllCategoryBinding;
import com.m90.zero.home.HomeActivity;
import com.m90.zero.home.allcategory.adapter.AllCategoryAdapter;
import com.m90.zero.home.allcategory.api.AllCategoryApi;
import com.m90.zero.home.allcategory.model.CategoryAllDetailsResponse;
import com.m90.zero.home.allcategory.model.CategoryAllResponse;
import com.m90.zero.retrofit.RetrofitClientInstance;
import com.m90.zero.utils.Utilities;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllCategoryActivity extends AppCompatActivity implements View.OnClickListener {

    Activity activity;
    AllCategoryAdapter categoryAdapter;
    ArrayList<CategoryAllDetailsResponse> categoryResponses;
    ActivityAllCategoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_all_category);
        activity = AllCategoryActivity.this;
        binding.llNameHierarchy.setVisibility(View.GONE);
        binding.btnBack.setOnClickListener(this);
        getCategory();
    }

    void getCategory() {

        AllCategoryApi categoryApi  = RetrofitClientInstance.getRetrofitInstanceServer().
                create(AllCategoryApi.class);

        categoryApi.getAllCategory().
                enqueue(new Callback<CategoryAllResponse>() {

                    @Override
                    public void onResponse(Call<CategoryAllResponse> call, Response<CategoryAllResponse> response) {

                        CategoryAllResponse categoryResponse = response.body();
                        Log.e("aboutResponce: ", " " +categoryResponse.toString());

                        if (categoryResponse.status == 200) {
                            Log.e( "onResponse: ", categoryResponse.data.toString());
                            setCategory(categoryResponse.data);

                        } else {
                            Toast.makeText(activity, "" + categoryResponse.message, Toast.LENGTH_SHORT).show();
                            Log.e("aboutResponce: ", " " + categoryResponse.message);

                        }


                    }

                    @Override
                    public void onFailure(Call<CategoryAllResponse> call, Throwable t) {

                        //Utilities.setError(layout1,layout2,txt_error,getResources().getString(R.string.something_went_wrong));
                        Log.d("errorchkAllCat", t.getMessage());

                    }
                });


    }

    void setCategory(ArrayList<CategoryAllDetailsResponse> categoryResponsesnew)
    {
        Log.e("TAG", "setCategory: " );
        categoryResponses = categoryResponsesnew;

        //LinearLayoutManager glm = new LinearLayoutManager(getApplicationContext());
        binding.rvCategories.setLayoutManager(new GridLayoutManager(this,2));
        //binding.recyclerViewCourses.setLayoutManager(glm);
        binding.rvCategories.setItemAnimator(new DefaultItemAnimator());
        binding.rvCategories.setHasFixedSize(true);

        categoryAdapter = new AllCategoryAdapter(this, categoryResponses,
                new AllCategoryAdapter.ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {


                    }
                });
        binding.rvCategories.setAdapter(categoryAdapter);
    }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_back:
                    //Utilities.launchActivity(activity, HomeActivity.class,true);

                    onBackPressed();
                    break;

            }
    }


}