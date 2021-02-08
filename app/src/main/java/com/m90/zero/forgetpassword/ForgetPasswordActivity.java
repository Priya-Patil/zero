package com.m90.zero.forgetpassword;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.androidnetworking.error.ANError;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.m90.zero.R;
import com.m90.zero.databinding.ActivityForgetPasswordBinding;
import com.m90.zero.forgetpassword.api.OTPApi;
import com.m90.zero.forgetpassword.model.OTPResponce;
import com.m90.zero.forgetpassword.model.SetPassResponce;
import com.m90.zero.home.HomeActivity;
import com.m90.zero.login.LoginActivity;
import com.m90.zero.login.api.LoginApi;
import com.m90.zero.login.model.LoginResponce;

import com.m90.zero.reg.api.RegApi;
import com.m90.zero.reg.model.RegResponce;
import com.m90.zero.retrofit.RetrofitClientInstance;
import com.m90.zero.utils.PrefManager;
import com.m90.zero.utils.SessionHelper;
import com.m90.zero.utils.Utilities;

import java.util.List;
import java.util.Locale;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;


public class ForgetPasswordActivity extends AppCompatActivity {

    ActivityForgetPasswordBinding binding;
    private CountDownTimer mCountDownTimer;
    private long mTimeLeftInMillis;

    //String OTP="1234";
    String OTP="";
    Activity activity;

    ProgressDialog progressDialog;
    PrefManager prefManager;
    SessionHelper sessionHelper;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forget_password);

        activity= ForgetPasswordActivity.this;
        progressDialog=new ProgressDialog(ForgetPasswordActivity.this);
        prefManager=new PrefManager(activity);
        sessionHelper=new SessionHelper(activity);

        requestPermission();

        binding.submit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isValidPhoneNumber(binding.etMobile.getText().toString()))
                {
                    //  Utility.launchActivity(ForgetPasswordActivity.this, HomeActivity.class, true);


                    String message = "Thank you for visiting Wheel alignment app! \n Your OTP Is :" + OTP;

                    binding.layout1.setVisibility(View.GONE);
                    binding.layout2.setVisibility(View.VISIBLE);
                    timerForOtp(binding.etMobile.getText().toString(), message);


                }
                else {
                    Toast.makeText(activity, "Enter proper mobile number", Toast.LENGTH_SHORT).show();
                }

            }
        });
        binding.submit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(binding.etOtp.getText().toString().equals(""))
                {
                    Toast.makeText(activity, "Enter otp", Toast.LENGTH_SHORT).show();

                }
                else {

                                 /*    setPassword("9172887936","9564",
                                             "111111", "111111"*/
                    setPassword(binding.etMobile.getText().toString(),binding.etOtp.getText().toString(),
                            binding.etPassword.getText().toString(), binding.etConpass.getText().toString()
                    );

                }
            }

        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.launchActivity(activity, LoginActivity.class,true);
            }
        });

    }


    private void updateCountDownText(long millisUntilFinished) {
        int minutes = (int) (millisUntilFinished / 1000) / 60;
        int seconds = (int) (millisUntilFinished / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%d:%d", minutes, seconds);
        //text_otp_expire.setText(timeLeftFormatted);
        Spannable WordtoSpan = new SpannableString("OTP expire after "+timeLeftFormatted+"  ");
        WordtoSpan.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimaryDark)), 17, 21,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.textOtpExpire.setText(WordtoSpan);

        //text_otp_expire.setText("OTP expire after  "+timeLeftFormatted+"  Seconds");

    }

    private void timerForOtp(String mobileNumber,String message) {
        binding.tvSmsRecv.setVisibility(View.GONE);
        SpannableString span = new SpannableString("Didn't receive SMS ? Resend");
        span.setSpan(new ForegroundColorSpan(Color.GRAY), 21, 27, 0);
        binding.tvSmsRecv.setTextColor(Color.parseColor("#48494b"));

//         mCountDownTimer = new CountDownTimer(120000, 1000) {
        mCountDownTimer = new CountDownTimer(180000, 1000) {

            public void onTick(long millisUntilFinished) {

                mTimeLeftInMillis=millisUntilFinished;
                updateCountDownText(millisUntilFinished);
                //120 sec=120000ms
                // if ((millisUntilFinished / 1000) == 117) {
                if ((millisUntilFinished / 1000) == 178) {

                    sendOTP(mobileNumber);
                }
                else {

                    //  sendCodeButton.setVisibility(View.VISIBLE);
                }

            }

            public void onFinish() {
                //sendOTP(mobileNumber, message);
                //Toast.makeText(AuthenticationActivity.this,"Time Out!! Resend OTP",Toast.LENGTH_LONG).show();
                SpannableString span = new SpannableString("Didn't receive SMS ? Resend");
                span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimaryDark)), 21, 27, 0);
                binding.tvSmsRecv.setText(span, TextView.BufferType.SPANNABLE);
                binding.tvSmsRecv.setVisibility(View.VISIBLE);
                //tv_sms_recv.setTextColor(Color.parseColor("#002266"));
                binding.tvSmsRecv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        OTP = GenerateRandomNumber(6);
                        Log.e("ChkOTP",""+OTP);
                        String message2 = "Thank you for visiting app! \n Your OTP Is :" + OTP;
                        binding.layout1.setVisibility(View.GONE);
                        binding.layout2.setVisibility(View.VISIBLE);
                        timerForOtp(mobileNumber,message2);

                    }
                });

            }

        }.start();
    }


    public static boolean isValidPhoneNumber(String mobile) {
        String regEx = "^[0-9]{10}$";
        return mobile.matches(regEx);
    }

    String GenerateRandomNumber(int charLength) {
        return String.valueOf(charLength < 1 ? 0 : new Random()
                .nextInt((9 * (int) Math.pow(10, charLength - 1)) - 1)
                + (int) Math.pow(10, charLength - 1));
    }

    void sendOTP(String mobile) {

        if (Utilities.isNetworkAvailable(activity)){
        OTPApi otpApi = RetrofitClientInstance.getRetrofitInstanceServer().
                create(OTPApi.class);

        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        // pbLoading.setProgressStyle(R.id.abbreviationsBar);
        progressDialog.show();
        otpApi.getOTP(mobile).
                enqueue(new Callback<OTPResponce>() {

                    @Override
                    public void onResponse(Call<OTPResponce> call, Response<OTPResponce> response) {

                        OTPResponce otpResponce = response.body();
                        Log.e("onResponse: ", " " + otpResponce.toString());

                        if(otpResponce.status==200)
                        {
                            Toast.makeText(activity, "otp is"+otpResponce.data.toString(), Toast.LENGTH_SHORT).show();
                        }
                        else {

                            Toast.makeText(activity, ""+otpResponce.message, Toast.LENGTH_SHORT).show();
                        }
                        /* if (loginResponce.status == 200) {


                         }

                         else {
                             Toast.makeText(activity, "" + loginResponce.message, Toast.LENGTH_SHORT).show();
                             Log.e("onResponse: ", " " + loginResponce.message);

                         }*/
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<OTPResponce> call, Throwable t) {

                        progressDialog.dismiss();
                        //Utilities.setError(layout1,layout2,txt_error,getResources().getString(R.string.something_went_wrong));
                        Log.d("errorchk", t.getMessage());

                    }
                });

        } else {
            Toast.makeText(activity, getResources().getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
        }
    }


    void setPassword(String mobile_number, String otp, String password,String password_confirmation) {

        if (Utilities.isNetworkAvailable(activity)){
        OTPApi otpApi = RetrofitClientInstance.getRetrofitInstanceServer().
                create(OTPApi.class);

        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        // pbLoading.setProgressStyle(R.id.abbreviationsBar);
        progressDialog.show();
        otpApi.setPassword(mobile_number,otp,password,password_confirmation).
                enqueue(new Callback<SetPassResponce>() {

                    @Override
                    public void onResponse(Call<SetPassResponce> call, Response<SetPassResponce> response) {
                        SetPassResponce setPassResponce = response.body();

                        if (setPassResponce.status== 200)
                        {
                            Toast.makeText(activity, "" + setPassResponce.message.toString(), Toast.LENGTH_SHORT).show();
                            binding.layout1.setVisibility(View.GONE);
                            binding.layout2.setVisibility(View.GONE);
                            binding.layout3.setVisibility(View.VISIBLE);

                        }

                        if (setPassResponce.status == 401)
                        {
                            Log.e("TAG","setPassResponce: " + setPassResponce.message);
                            authenticationDialog(String.valueOf(setPassResponce.message));
                            //Toast.makeText(activity, "" + profileResponse.message , Toast.LENGTH_SHORT).show();
                        }

                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<SetPassResponce> call, Throwable t) {

                        progressDialog.dismiss();
                        Log.e("errorchk", t.getMessage());

                    }
                });
        } else {
            Toast.makeText(activity, getResources().getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("otp"));
        super.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("otp")) {
                final String message = intent.getStringExtra("message");

                binding.etOtp.setText(message);
                Log.e( "onReceive: ", message );
                // message is the fetching OTP
            }
        }
    };



    private void requestPermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.SEND_SMS)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }


                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Error occurred! "+error, Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    private void authenticationDialog(String errorMessage) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity);
        mBuilder.setTitle(errorMessage);
        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

                dialog.dismiss();
                Utilities.launchActivity(activity, LoginActivity.class,true);

            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_rounded_background);
        mDialog.show();
    }


}