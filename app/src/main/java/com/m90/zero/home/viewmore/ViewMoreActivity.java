package com.m90.zero.home.viewmore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.m90.zero.R;
import com.m90.zero.databinding.ActivityViewMoreBinding;
import com.m90.zero.home.adapter.ProductGroupsAdapter;
import com.m90.zero.home.allcategory.model.CategoryAllDetailsResponse;
import com.m90.zero.home.api.ProductGroupsApi;
import com.m90.zero.home.model.ProductDetailsResponse;
import com.m90.zero.home.model.ProductGroupsDetailsResponse;
import com.m90.zero.home.model.ProductGroupsResponse;
import com.m90.zero.home.viewmore.adapter.ViewMoreAdapter;
import com.m90.zero.home.viewmore.api.ViewMoreApi;
import com.m90.zero.home.viewmore.model.ViewMoreDetailResponce;
import com.m90.zero.home.viewmore.model.ViewMoreResponse;
import com.m90.zero.retrofit.RetrofitClientInstance;
import com.m90.zero.utils.Utilities;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewMoreActivity extends AppCompatActivity implements View.OnClickListener {

    public static String TAG = "ViewMoreActivity";

    ActivityViewMoreBinding  binding;
    Activity activity;
    ArrayList<ProductDetailsResponse> viewMoreDetailResponce;
        int productGroupId;

    ProductGroupsDetailsResponse viewMoreResponse;
    ViewMoreAdapter viewMoreAdapter;
    ProgressDialog progressDialog;
    int vendorid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_view_more);
        activity = ViewMoreActivity.this;
        progressDialog = new ProgressDialog(activity);

        viewMoreResponse =  getIntent().getParcelableExtra("ProductGroupsDetailsResponse");
        vendorid=getIntent().getIntExtra("vendorid",0);
        Log.e( "onCreate: ", String.valueOf(vendorid));

        if (viewMoreResponse!=null) {
            productGroupId = vendorid;
            Log.e(TAG, "onCreate: " + productGroupId +" "+viewMoreResponse.title);
            binding.tvTitle.setText(viewMoreResponse.title);
            getProductGroups(productGroupId);
        }else
        {
            Toast.makeText(activity,"Data Not Found",Toast.LENGTH_SHORT).show();
        }
        binding.btnOpenDrawer.setOnClickListener(this);
    }


    void getProductGroups(int productGroupId) {

        if (Utilities.isNetworkAvailable(activity)){
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ViewMoreApi viewMoreApi  = RetrofitClientInstance.getRetrofitInstanceServer().
                create(ViewMoreApi.class);

        viewMoreApi.getViewMore(RetrofitClientInstance.BASE_URL+"products/group/all/"+productGroupId).
                enqueue(new Callback<ViewMoreResponse>() {

                    @Override
                    public void onResponse(Call<ViewMoreResponse> call, Response<ViewMoreResponse> response) {


                        ViewMoreResponse productGroupsResponse = response.body();

                       // Log.e(TAG, "onResponse: "+productGroupsResponse.toString() );

                        if (productGroupsResponse !=null) {
                            if (productGroupsResponse.status == 200) {
                                Log.e("onResponse: ", productGroupsResponse.toString());
                                setProductGroups(productGroupsResponse.data.get(0).vendor);
                                //  Toast.makeText(activity, "" + aboutResponce.message, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                            } else {
                                Toast.makeText(activity, "" + productGroupsResponse.message, Toast.LENGTH_SHORT).show();
                                Log.e("aboutResponce: ", " " + productGroupsResponse.message);
                                progressDialog.dismiss();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<ViewMoreResponse> call, Throwable t) {
                        progressDialog.dismiss();
                        //Utilities.setError(layout1,layout2,txt_error,getResources().getString(R.string.something_went_wrong));
                        Log.d("errorchk", t.getMessage());

                    }
                });
        } else {
            Toast.makeText(activity, getResources().getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
        }
    }

    void setProductGroups(ArrayList<ProductDetailsResponse> productGroups)
    {
        Log.e("TAG", "setProductGroups: " );
        viewMoreDetailResponce = productGroups;

        //LinearLayoutManager glm = new LinearLayoutManager(getApplicationContext());
        binding.recyclerViewCourses.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false));

        binding.recyclerViewCourses.setItemAnimator(new DefaultItemAnimator());
        //mRecyclerViewCourses.setHasFixedSize(true);

        viewMoreAdapter = new ViewMoreAdapter(this, viewMoreDetailResponce,
                new ViewMoreAdapter.ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {


                    }
                });
        binding.recyclerViewCourses.setAdapter(viewMoreAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_openDrawer:
                    onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}