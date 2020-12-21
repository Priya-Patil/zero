package com.m90.zero;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.m90.zero.databinding.ActivityMainBinding;
import com.m90.zero.login.api.LoginApi;
import com.m90.zero.login.model.LoginResponce;
import com.m90.zero.reg.RegActivity;
import com.m90.zero.retrofit.RetrofitClientInstance;
import com.m90.zero.utils.Utilities;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.m90.zero.utils.Utilities.isValidPhoneNumber;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityMainBinding binding;
    ProgressDialog progressDialog;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activity = MainActivity.this;
        progressDialog = new ProgressDialog(activity);

        binding.txtSend.setOnClickListener(this);
        binding.btnReg.setOnClickListener(this);
    }

    void checkLogin(String mobile, String active) {

        LoginApi loginApi = RetrofitClientInstance.getRetrofitInstanceServer().
                create(LoginApi.class);

        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        // pbLoading.setProgressStyle(R.id.abbreviationsBar);
        progressDialog.show();
        loginApi.checkLogin(mobile, active).
                enqueue(new Callback<LoginResponce>() {

                    @Override
                    public void onResponse(Call<LoginResponce> call, Response<LoginResponce> response) {

                        LoginResponce loginResponce = response.body();

                        if (loginResponce.status == 200) {
                            Toast.makeText(activity, "" + loginResponce.message, Toast.LENGTH_SHORT).show();
                            Log.e("onResponse: ", " " + loginResponce.message);
                        } else {
                            Toast.makeText(activity, "" + loginResponce.message, Toast.LENGTH_SHORT).show();
                            Log.e("onResponse: ", " " + loginResponce.message);

                        }


                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<LoginResponce> call, Throwable t) {

                        progressDialog.dismiss();
                        //Utilities.setError(layout1,layout2,txt_error,getResources().getString(R.string.something_went_wrong));
                        Log.d("errorchk", t.getMessage());

                    }
                });


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            default:
                break;

            case R.id.txt_send:
                if(binding.edtMobileno.getText().toString().equals("")|| binding.etPass.getText().toString().equals(""))
                {
                    Toast.makeText(activity, "Enter all details", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (isValidPhoneNumber(binding.edtMobileno.getText().toString()))
                    {
                        checkLogin(binding.edtMobileno.getText().toString(), binding.etPass.getText().toString());
                    }
                    else {
                        Toast.makeText(activity, "Entre proper mobile number", Toast.LENGTH_SHORT).show();
                    }

                }

                break;


            case R.id.btn_reg:
                Utilities.launchActivity(activity, RegActivity.class,true);

                break;
        }
    }



}