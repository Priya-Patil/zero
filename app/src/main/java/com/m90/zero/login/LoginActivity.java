package com.m90.zero.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.easebuzz.payment.kit.PWECouponsActivity;
import com.google.gson.Gson;
import com.m90.zero.R;
import com.m90.zero.databinding.ActivityMainBinding;
import com.m90.zero.forgetpassword.ForgetPasswordActivity;
import com.m90.zero.home.HomeActivity;
import com.m90.zero.login.api.LoginApi;
import com.m90.zero.login.model.LoginResponce;

import com.m90.zero.reg.RegActivity;
import com.m90.zero.reg.api.RegApi;
import com.m90.zero.reg.model.AfterPaymentRegResponce;
import com.m90.zero.retrofit.RetrofitClientInstance;
import com.m90.zero.splash.SplashActivity;
import com.m90.zero.utils.PrefManager;
import com.m90.zero.utils.SessionHelper;
import com.m90.zero.utils.Utilities;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import datamodels.PWEStaticDataModel;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.m90.zero.utils.Utilities.isValidPhoneNumber;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityMainBinding binding;
    ProgressDialog progressDialog;
    Activity activity;
    SessionHelper sessionHelper;


    String merchant_trxnId;
    Double merchant_payment_amount;
    String merchant_productInfo="Eureka" ;
    String customer_firstName ;
    String customer_email_id ;
    String customer_phone ;
    // String merchant_key="03M4QISVM0";  //production
    String merchant_key = "2PBP7IABZ2";
    String merchant_udf1 = "";
    String merchant_udf2 = "";
    String merchant_udf3 = "";
    String merchant_udf4 = "";
    String merchant_udf5 = "";
    String customer_address1 = "";
    String customer_address2 = "";
    String customer_city = "";
    String customer_state = "";
    String customer_country = "";
    String customer_zipcode = "";
    //String salt="5NVGFAOE8Z"; //production
    String salt = "DAH88E3UWQ";
    //int customers_unique_id = 1;
    int customers_unique_id;
    String payment_mode = "test";
    //String payment_mode="production";
    StringBuilder sb;
    int userid;
    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activity = LoginActivity.this;
        progressDialog = new ProgressDialog(activity);
        sessionHelper = new SessionHelper(activity);
        prefManager = new PrefManager(activity);

        binding.txtSend.setOnClickListener(this);
        binding.btnReg.setOnClickListener(this);
        binding.btnForgotpass.setOnClickListener(this);
        binding.btnHome.setOnClickListener(this);
        TrxnId();

    }

    void checkLogin(String mobile, String active) {

        if (Utilities.isNetworkAvailable(activity)){
            LoginApi loginApi = RetrofitClientInstance.getRetrofitInstanceServer().
                    create(LoginApi.class);

            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            loginApi.checkLogin(mobile, active).
                enqueue(new Callback<LoginResponce>() {

                    @Override
                    public void onResponse(Call<LoginResponce> call, Response<LoginResponce> response) {

                        LoginResponce loginResponce = response.body();
                        progressDialog.dismiss();

                        if (loginResponce!=null) {

                            if (loginResponce.status == 200) {
                                Log.e("onResponse: ", " " + loginResponce.toString() + "" + loginResponce.data.id);

                                sessionHelper.setLogin(true);
                                Utilities.launchActivity(activity, SplashActivity.class, true);
                                Utilities.saveUserData(activity, "userid", String.valueOf(loginResponce.data.id));
                                Utilities.saveUserData(activity, "profilePic", loginResponce.data.avatar);
                                Utilities.saveUserData(activity, "name", loginResponce.data.name);
                                Utilities.saveUserData(activity, "mobileNumber", loginResponce.data.mobile_number);
                                Utilities.saveUserData(activity, "accesstoken", loginResponce.access_token);
                                Utilities.saveUserData(activity, "sponsorcode", loginResponce.data.sponsor_code);
                                Toast.makeText(activity, "" + loginResponce.message, Toast.LENGTH_SHORT).show();
                                Log.e("onResponse: ", " " + loginResponce.message + "" + loginResponce.data.id);
                            }

                            //if (loginResponce.status == 403)
                            if (loginResponce.status == 401) {

                                Toast.makeText(activity, "chklogin" + loginResponce.toString(), Toast.LENGTH_SHORT).show();
                                customers_unique_id = loginResponce.data.id;
                                userid = loginResponce.data.id;
                                merchant_payment_amount = Double.valueOf(loginResponce.data.amount);
                                customer_firstName = loginResponce.data.name;
                                customer_email_id = loginResponce.data.email;
                                customer_phone = loginResponce.data.mobile_number;

                                String hash = merchant_key + "|" + merchant_trxnId + "|" + merchant_payment_amount + "|" +
                                        merchant_productInfo + "|" + customer_firstName + "|" + customer_email_id + "|"
                                        + merchant_udf1 + "|" + merchant_udf2 + "|" + merchant_udf3 + "|" + merchant_udf4 + "|"
                                        + merchant_udf5 + "||||||" + salt + "|" + merchant_key;
                                Log.e("hashchk: ", hash);
                                hashgeneration(hash);
                                Log.e("onClick: ", merchant_trxnId + "  " + merchant_payment_amount + " " + merchant_productInfo + " " + customer_firstName + "" + customer_email_id);

                                payment(merchant_trxnId, merchant_payment_amount, merchant_productInfo,
                                        customer_firstName, customer_email_id, customer_phone,
                                        merchant_key, merchant_udf1, merchant_udf2,
                                        merchant_udf3, merchant_udf4, merchant_udf5, customer_address1, customer_address2,
                                        customer_city, customer_state,
                                        customer_country, customer_zipcode, sb, customers_unique_id, payment_mode);

                                //Toast.makeText(activity, "" + loginResponce.message, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(activity, "" + loginResponce.message, Toast.LENGTH_SHORT).show();
                                Log.e("onResponse: ", " " + loginResponce.message);
                            }
                        }else
                        {
                            Toast.makeText(activity, "Wrong Credential!!", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<LoginResponce> call, Throwable t) {

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
                        Toast.makeText(activity, "Mobile number must be 10 digits", Toast.LENGTH_SHORT).show();
                    }

                }

                break;


            case R.id.btn_reg:
                Utilities.launchActivity(activity, RegActivity.class,true);

                break;
            case R.id.btn_forgotpass:
                Utilities.launchActivity(activity, ForgetPasswordActivity.class,false);
                break;

             case R.id.btn_home:
                Utilities.launchActivity(activity, HomeActivity.class,false);
                break;



        }
    }

    void payment(String merchant_trxnId, Double merchant_payment_amount, String merchant_productInfo,
                 String customer_firstName,
                 String customer_email_id, String customer_phone, String merchant_key, String merchant_udf1,
                 String merchant_udf2, String merchant_udf3, String merchant_udf4, String merchant_udf5,
                 String customer_address1, String customer_address2, String customer_city,
                 String customer_state, String customer_country, String customer_zipcode, StringBuilder sb,
                 int customers_unique_id, String payment_mode) {
        Intent intentProceed = new Intent(LoginActivity.this, PWECouponsActivity.class);
        intentProceed.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); // This is mandatory flag
        intentProceed.putExtra("txnid", merchant_trxnId);
        intentProceed.putExtra("amount", merchant_payment_amount);
        intentProceed.putExtra("productinfo", merchant_productInfo);
        intentProceed.putExtra("firstname", customer_firstName);
        intentProceed.putExtra("email", customer_email_id);
        intentProceed.putExtra("phone", customer_phone);
        intentProceed.putExtra("key", merchant_key);
        intentProceed.putExtra("udf1", merchant_udf1);
        intentProceed.putExtra("udf2", merchant_udf2);
        intentProceed.putExtra("udf3", merchant_udf3);
        intentProceed.putExtra("udf4", merchant_udf4);
        intentProceed.putExtra("udf5", merchant_udf5);
        intentProceed.putExtra("address1", customer_address1);
        intentProceed.putExtra("address2", customer_address2);
        intentProceed.putExtra("city", customer_city);
        intentProceed.putExtra("state", customer_state);
        intentProceed.putExtra("country", customer_country);
        intentProceed.putExtra("zipcode", customer_zipcode);
        intentProceed.putExtra("hash", sb.toString());
        intentProceed.putExtra("unique_id", customers_unique_id);
        intentProceed.putExtra("pay_mode", payment_mode);
        startActivityForResult(intentProceed, PWEStaticDataModel.PWE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == PWEStaticDataModel.PWE_REQUEST_CODE) {
                try {
                    String result = data.getStringExtra("result");
                    String payment_response = data.getStringExtra("payment_response");
                    if (result.contains(PWEStaticDataModel.TXN_SUCCESS_CODE)) {

                        Log.e( "onActivityResult: ", payment_response);
                        JSONObject obj = new JSONObject(payment_response);
                        Log.e("MyApp", obj.getString("net_amount_debit"));
                        addUserAfterPayment(String.valueOf(userid),obj.getString("txnid"),obj.getString("status"));
                        //   addFees(prefManager.getUSER_ID(), String.valueOf(merchant_payment_amount), merchant_trxnId);
//PWEStaticDataModel. TXN_SUCCESS_CODE is a string constant and its value is “ payment_successfull ”
//Code here will execute if the payment transaction completed successfully.
// here merchant can show the payment success message.
                    } else if (result.contains(PWEStaticDataModel.TXN_TIMEOUT_CODE)) {

                        Log.e( "onActivityResult: ", "TXN_TIMEOUT_CODE");

//PWEStaticDataModel. TXN_TIMEOUT_CODE is a string constant and its value is “ txn_session_timeout ”
//Code here will execute if the payment transaction failed because of the transaction time out.
// here merchant can show the payment failed message.
                    } else if (result.contains(PWEStaticDataModel.TXN_BACKPRESSED_CODE)) {
                        Log.e( "onActivityResult: ", "TXN_BACKPRESSED_CODE");

//PWEStaticDataModel. TXN_BACKPRESSED_CODE is a string constant and its value is “ back_pressed ”
//Code here will execute if the user pressed the back button on coupons Activity.
// here merchant can show the payment failed message.
                    } else if (result.contains(PWEStaticDataModel.TXN_USERCANCELLED_CODE)) {

                        Log.e( "onActivityResult: ", "TXN_USERCANCELLED_CODE");

//PWEStaticDataModel. TXN_USERCANCELLED_CODE is a string constant and its value is “ user_cancelled ”
//Code here will execute if the the user pressed the cancel button during the payment process.
// here merchant can show the payment failed message.
                    } else if (result.contains(PWEStaticDataModel.TXN_ERROR_SERVER_ERROR_CODE)) {
                        Log.e( "onActivityResult: ", "TXN_ERROR_SERVER_ERROR_CODE");

//PWEStaticDataModel. TXN_ERROR_SERVER_ERROR_CODE is a string constant and its value  is “error_server_error ”
//Code here will execute if the server side error occured during payment process.
// here merchant can show the payment failed message.
                    } else if (result.contains(PWEStaticDataModel.TXN_ERROR_TXN_NOT_ALLOWED_CODE)) {
                        Log.e( "onActivityResult: ", "TXN_ERROR_TXN_NOT_ALLOWED_CODE");

//PWEStaticDataModel. TXN_ERROR_TXN_NOT_ALLOWED_CODE is a string constant and its value is trxn_not_allowed ”
//Code here will execute if the the transaction is not allowed.
// here merchant can show the payment failed message.
                    } else if (result.contains(PWEStaticDataModel.TXN_BANK_BACK_PRESSED_CODE)) {
                        Log.e( "onActivityResult: ", "TXN_BANK_BACK_PRESSED_CODE");

//PWEStaticDataModel. TXN_BANK_BACK_PRESSED_CODE is a string constant and its value is “bank_back_pressed”
//Code here will execute if the the customer press the back button on bank screen.
// here merchant can show the payment failed message.
                    } else {

                        Log.e( "onActivityResult: ", "else");

// Here the value of result is “ payment_failed ” or “ error_noretry ” or “ retry_fail_error ”
//Code here will execute if payment is failed some other reasons.
// here merchant can show the payment failed message.
                    }
                } catch (Exception e) {

                    Log.e( "onActivityResult: ", e.toString());

//Handle exceptions here
                }
            }
        }
    }

    void addUserAfterPayment(String userid, String transid, String status) {

        if (Utilities.isNetworkAvailable(activity)){
            RegApi regApi = RetrofitClientInstance.getRetrofitInstanceServer().
                    create(RegApi.class);

            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            // pbLoading.setProgressStyle(R.id.abbreviationsBar);
            progressDialog.show();
            regApi.addUserAfterPayment(RetrofitClientInstance.BASE_URL+"customer/activation/"+userid+"/"+transid+"/"+status).
                enqueue(new Callback<AfterPaymentRegResponce>() {

                    @Override
                    public void onResponse(Call<AfterPaymentRegResponce> call, Response<AfterPaymentRegResponce> response) {

                        AfterPaymentRegResponce regResponce = response.body();
                        if (regResponce.status == 201)
                        {
                            // success
                            sessionHelper.setLogin(true);
                            Utilities.launchActivity(LoginActivity.this, HomeActivity.class,true);

                        }
                        else {
                            Toast.makeText(activity, "" + regResponce.message.toString(), Toast.LENGTH_SHORT).show();
                            Log.e("onResponse: ", " " + new Gson().toJson(regResponce.message));
                        }

                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<AfterPaymentRegResponce> call, Throwable t) {

                        progressDialog.dismiss();
                        //Utilities.setError(layout1,layout2,txt_error,getResources().getString(R.string.something_went_wrong));
                        Log.e("errorchk", t.getMessage());

                    }
                });
        } else {
            Toast.makeText(activity, getResources().getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
        }
    }


    void hashgeneration(String hash1) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] digest = md.digest(hash1.getBytes());
        sb = new StringBuilder();
        for (int i = 0; i < digest.length; i++) {
            sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
        }
        Log.e("hashgeneration: ", sb.toString());
    }

    void TrxnId() {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmssMs");
        merchant_trxnId = ft.format(dNow);
        Log.e("TrxnId: ", merchant_trxnId);
    }

}