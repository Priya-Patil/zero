package com.m90.zero.tac;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.m90.zero.R;
import com.m90.zero.about.api.AboutApi;
import com.m90.zero.about.model.AboutResponce;
import com.m90.zero.databinding.ActivityAboutBinding;
import com.m90.zero.retrofit.RetrofitClientInstance;
import com.m90.zero.tac.api.TACApi;
import com.m90.zero.utils.Utilities;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TACActivity extends AppCompatActivity implements View.OnClickListener {

    ProgressDialog progressDialog;
    Activity activity;
    ActivityAboutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_about);

        activity= TACActivity.this;
        progressDialog=new ProgressDialog(activity);
        binding.imgBack.setOnClickListener(this);
        getTAC();
    }

    void getTAC() {

        if (Utilities.isNetworkAvailable(activity)){
            TACApi tacApi  = RetrofitClientInstance.getRetrofitInstanceServer().
                    create(TACApi.class);

            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            // pbLoading.setProgressStyle(R.id.abbreviationsBar);
            progressDialog.show();
            tacApi.getTAC().
                enqueue(new Callback<AboutResponce>() {

                    @Override
                    public void onResponse(Call<AboutResponce> call, Response<AboutResponce> response) {

                        AboutResponce aboutResponce = response.body();

                        if (aboutResponce.status == 200) {
                          //  Toast.makeText(activity, "" + aboutResponce.message, Toast.LENGTH_SHORT).show();
                           // Log.e("aboutResponce: ", " " + aboutResponce.message);
                            binding.webview.loadData( aboutResponce.data.description, "text/html", null);


                        } else {
                            Toast.makeText(activity, "" + aboutResponce.message, Toast.LENGTH_SHORT).show();
                            Log.e("aboutResponce: ", " " + aboutResponce.message);

                        }


                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<AboutResponce> call, Throwable t) {

                        progressDialog.dismiss();
                        //Utilities.setError(layout1,layout2,txt_error,getResources().getString(R.string.something_went_wrong));
                        Log.d("errorchk", t.getMessage());

                    }
                });

        } else {
            Toast.makeText(activity, getResources().getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            default:
                break;

            case R.id.img_back:
                onBackPressed();
                break;

        }
        }
}