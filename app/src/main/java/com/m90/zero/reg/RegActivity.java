package com.m90.zero.reg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.m90.zero.MainActivity;
import com.m90.zero.R;
import com.m90.zero.databinding.ActivityMainBinding;
import com.m90.zero.databinding.ActivityRegBinding;
import com.m90.zero.loging.api.LoginApi;
import com.m90.zero.loging.model.LoginResponce;
import com.m90.zero.reg.api.RegApi;
import com.m90.zero.reg.model.CodeResponce;
import com.m90.zero.reg.model.RegResponce;
import com.m90.zero.retrofit.RetrofitClientInstance;
import com.m90.zero.utils.Utilities;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.m90.zero.utils.Utilities.isValidPhoneNumber;

public class RegActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityRegBinding binding;
    ProgressDialog progressDialog;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_reg);
        activity = RegActivity.this;
        progressDialog = new ProgressDialog(activity);
        binding.btnReg.setOnClickListener(this);
        binding.btnCode.setOnClickListener(this);
        binding.btnLogin.setOnClickListener(this);

        binding.chType.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(binding.chType.isChecked()==true)
                {
                    binding.etCode.setText("");
                }
                else if(binding.chType.isChecked()==false)
                {

                }
            }
        });

        addUser("ppp","jeijoijf","8669176540",
                "priyapalaskar545@gmail.com","123456","123456",
                "ZEEERO379988");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;

            case R.id.txt_send:


                break;
            case R.id.btn_reg:
                if(binding.etFirstname.getText().toString().equals("")|| binding.etLastname.getText().toString().equals("")
                || binding.etMobile.getText().toString().equals("")|| binding.etEmail.getText().toString().equals("")||
                        binding.etPass.getText().toString().equals("")|| binding.etConpass.getText().toString().equals("")
                ||binding.etCode.getText().toString().equals(""))
                {
                    Toast.makeText(activity, "Enter all details", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (isValidPhoneNumber(binding.etMobile.getText().toString()))
                    {
                            addUser(binding.etFirstname.getText().toString(), binding.etLastname.getText().toString(),
                                    binding.etMobile.getText().toString(), binding.etEmail.getText().toString(),
                                    binding.etPass.getText().toString(), binding.etConpass.getText().toString(),
                                    binding.etCode.getText().toString());
                    }
                    else {
                        Toast.makeText(activity, "Entre proper mobile number", Toast.LENGTH_SHORT).show();
                    }

                }
                break;

                case R.id.btn_code:
                Toast.makeText(activity, "nnn", Toast.LENGTH_SHORT).show();
                getcode();
                break;

                case R.id.btn_login:
                    Utilities.launchActivity(activity,MainActivity.class,true);
                    
                break;
        }
    }

    void getcode() {

        RegApi regApi = RetrofitClientInstance.getRetrofitInstanceServer().
                create(RegApi.class);

        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        // pbLoading.setProgressStyle(R.id.abbreviationsBar);
        progressDialog.show();
        regApi.getCode().
                enqueue(new Callback<CodeResponce>() {

                    @Override
                    public void onResponse(Call<CodeResponce> call, Response<CodeResponce> response) {

                        CodeResponce codeResponce = response.body();

                        binding.etCode.setText(codeResponce.code);
                        binding.chType.setChecked(false);

                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<CodeResponce> call, Throwable t) {

                        progressDialog.dismiss();
                        //Utilities.setError(layout1,layout2,txt_error,getResources().getString(R.string.something_went_wrong));
                        Log.d("errorchk", t.getMessage());

                    }
                });


    }

    //http://api.eurekatalents.in/api/customer/register?first_name=Manish&last_name=Patil
    // &mobile_number=9823017728&email=kalpana.kambl4@gmail.com&password=123456&password_confirmation=123456&
    // sponsor_by=ZEEERO379988

    void addUser(String first_name, String last_name, String mobile_number,String email,
                 String password, String password_confirmation, String sponsor_by) {

        RegApi regApi = RetrofitClientInstance.getRetrofitInstanceServer().
                create(RegApi.class);

        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        // pbLoading.setProgressStyle(R.id.abbreviationsBar);
        progressDialog.show();
        regApi.addUser(first_name,last_name,mobile_number,email,password,password_confirmation, sponsor_by).
                enqueue(new Callback<RegResponce>() {

                    @Override
                    public void onResponse(Call<RegResponce> call, Response<RegResponce> response) {

                        RegResponce regResponce = response.body();

                        if (response.body() != null)
                        {
                            Log.e("onResponse: ", " " + regResponce.message);

                            if (regResponce.status == 201) {
                                Toast.makeText(activity, "" + regResponce.message, Toast.LENGTH_SHORT).show();
                                Log.e("onResponse: ", " " + regResponce.message);
                            } else {
                                Toast.makeText(activity, "" + regResponce.message, Toast.LENGTH_SHORT).show();
                                Log.e("onResponse: ", " " + regResponce.message);

                            }
                            Log.e( "onResponse: ", regResponce.toString() );

                        }


                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<RegResponce> call, Throwable t) {

                        progressDialog.dismiss();
                        //Utilities.setError(layout1,layout2,txt_error,getResources().getString(R.string.something_went_wrong));
                        Log.e("errorchk", t.getMessage());

                    }
                });


    }


}