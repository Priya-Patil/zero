package com.m90.zero.home.brands;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.m90.zero.R;
import com.m90.zero.databinding.ActivityViewMoreBinding;
import com.m90.zero.home.adapter.BrandsAdapter;
import com.m90.zero.home.brands.adapter.BrandsListAdapter;
import com.m90.zero.home.brands.api.BrandsApi;
import com.m90.zero.home.brands.model.BrandsDetailListResponce;
import com.m90.zero.home.brands.model.BrandsListResponse;
import com.m90.zero.home.model.BrandsDetailsResponse;
import com.m90.zero.home.model.ProductGroupsDetailsResponse;
import com.m90.zero.retrofit.RetrofitClientInstance;
import com.m90.zero.utils.Utilities;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrandsActivity extends AppCompatActivity implements View.OnClickListener {

    public static String TAG = BrandsActivity.class.getSimpleName();

    ActivityViewMoreBinding  binding;
    Activity activity;
    ArrayList<BrandsDetailListResponce> brandsDetailResponces;
        int brandsId;

    BrandsDetailsResponse brandsDetailsResponse;
    BrandsListAdapter brandsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_view_more);
        activity = BrandsActivity.this;


        brandsId = getIntent().getIntExtra("brandsId",0);

          //2;
        Log.e(TAG, "onCreate: "+brandsId);
        getBrands(brandsId);

        binding.btnOpenDrawer.setOnClickListener(this);

    }


    void getBrands(int brandsId) {

        if (Utilities.isNetworkAvailable(activity)){
        BrandsApi brandsApi  = RetrofitClientInstance.getRetrofitInstanceServer().
                create(BrandsApi.class);

        brandsApi.getBrands(RetrofitClientInstance.BASE_URL+"products/brand/"+brandsId).
                enqueue(new Callback<BrandsListResponse>() {

                    @Override
                    public void onResponse(Call<BrandsListResponse> call, Response<BrandsListResponse> response) {

                        BrandsListResponse brandsResponse = response.body();
                        if (brandsResponse !=null) {
                            if (brandsResponse.status == 200) {
                                Log.e("onResponse: ", brandsResponse.toString());
                                setBrands(brandsResponse.data);
                                //  Toast.makeText(activity, "" + aboutResponce.message, Toast.LENGTH_SHORT).show();


                            } else {
                                Toast.makeText(activity, "" + brandsResponse.message, Toast.LENGTH_SHORT).show();
                                Log.e("aboutResponce: ", " " + brandsResponse.message);

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BrandsListResponse> call, Throwable t) {

                        //Utilities.setError(layout1,layout2,txt_error,getResources().getString(R.string.something_went_wrong));
                        Log.d("errorchk", t.getMessage());

                    }
                });

        } else {
            Toast.makeText(activity, getResources().getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
        }
    }

    void setBrands(ArrayList<BrandsDetailListResponce> productGroups)
    {

        brandsDetailResponces = productGroups;

        //LinearLayoutManager glm = new LinearLayoutManager(getApplicationContext());
        binding.recyclerViewCourses.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false));

        binding.recyclerViewCourses.setItemAnimator(new DefaultItemAnimator());
        //mRecyclerViewCourses.setHasFixedSize(true);

        brandsAdapter = new BrandsListAdapter(this, brandsDetailResponces,
                new BrandsListAdapter.ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {


                    }
                });
        binding.recyclerViewCourses.setAdapter(brandsAdapter);
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