package com.m90.zero.faq;

import android.app.Activity;
import android.app.ProgressDialog;
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
import com.m90.zero.databinding.ActivityFaqBinding;
import com.m90.zero.faq.adapter.FaqAdapter;
import com.m90.zero.faq.api.FaqApi;
import com.m90.zero.faq.model.FaqDetailsResponce;
import com.m90.zero.faq.model.FaqResponse;
import com.m90.zero.home.HomeActivity;
import com.m90.zero.retrofit.RetrofitClientInstance;
import com.m90.zero.utils.Utilities;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FaqActivity extends AppCompatActivity implements View.OnClickListener {

    public static String TAG ="FaqActivity";

    //RecyclerView binding.rvFaq;

    ActivityFaqBinding binding;
    Activity activity;
    ProgressDialog progressDialog ;

    ArrayList<FaqDetailsResponce> faqDetailsResponces;
    FaqAdapter faqAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_faq);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_faq);
        activity =  FaqActivity.this;
        initView();
        getFaq();
    }

    private void initView() {
        progressDialog = new ProgressDialog(FaqActivity.this);
        binding.btnBack.setOnClickListener(this);
        //binding.rvFaq = findViewById(R.id.binding.rvFaq);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_back:
                Utilities.launchActivity(activity, HomeActivity.class,true);
                break;
        }
    }

    void getFaq() {

        FaqApi faqApi = RetrofitClientInstance.getRetrofitInstanceServer().
                create(FaqApi.class);

        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        faqApi.getFaq().enqueue(new Callback<FaqResponse>() {

                    @Override
                    public void onResponse(Call<FaqResponse> call, Response<FaqResponse> response) {

                        FaqResponse faqModel = response.body();
                        faqDetailsResponces = (faqModel).data;
                        Log.e(TAG, "onResponse: "+faqModel.toString()+" "+faqDetailsResponces.toString() );

                        if (faqModel.status == 200) {
                            Toast.makeText(activity, "" + faqModel.message, Toast.LENGTH_SHORT).show();

                            binding.rvFaq.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, true));
                            // mRecyclerViewCourses.setLayoutManager(glm);
                            binding.rvFaq.setItemAnimator(new DefaultItemAnimator());
                            binding.rvFaq.setHasFixedSize(true);

                            faqAdapter = new FaqAdapter(activity, faqDetailsResponces,
                                    new FaqAdapter.ItemClickListener() {
                                        @Override
                                        public void onClick(View view, int position) {
                                        }
                                    });
                            binding.rvFaq.setAdapter(faqAdapter);

                            Log.e("onResponse: ", " " + faqModel.message);
                        } else {
                            Toast.makeText(activity, "" + faqModel.message, Toast.LENGTH_SHORT).show();
                            Log.e("onResponse: ", " " + faqModel.message);
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<FaqResponse> call, Throwable t) {
                        progressDialog.dismiss();
                        //Utilities.setError(layout1,layout2,txt_error,getResources().getString(R.string.something_went_wrong));
                        Log.e("errorchk", t.getMessage());
                    }
                });


    }

}