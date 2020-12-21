package com.m90.zero.reg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.easebuzz.payment.kit.PWECouponsActivity;
import com.google.gson.Gson;
import com.m90.zero.MainActivity;
import com.m90.zero.R;
import com.m90.zero.databinding.ActivityRegBinding;
import com.m90.zero.reg.api.RegApi;
import com.m90.zero.reg.model.CodeResponce;
import com.m90.zero.reg.model.RegResponce;
import com.m90.zero.retrofit.RetrofitClientInstance;
import com.m90.zero.utils.Utilities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import datamodels.PWEStaticDataModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.m90.zero.utils.Utilities.isValidPhoneNumber;

public class RegActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityRegBinding binding;
    ProgressDialog progressDialog;
    Activity activity;


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
    int customers_unique_id = 1;
    String payment_mode = "test";
    //String payment_mode="production";
    StringBuilder sb;
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

        customer_firstName="priya";
        customer_email_id="priyapalaskar545@gmail.com";
        customer_phone="9172887936";
        customers_unique_id= 1;
        TrxnId();
        binding.chType.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(binding.chType.isChecked()==true)
                {
                    binding.etCode.setText("");
                    binding.btnCode.setVisibility(View.GONE);
                }
                else if(binding.chType.isChecked()==false)
                {
                    binding.btnCode.setVisibility(View.VISIBLE);

                }
            }
        });

   /*     addUser("ppp","jeijoijf","8669176540",
                "priyapalaskar545@gmail.com","123456","123456",
                "ZEEERO379988");
*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;

            case R.id.txt_send:


                break;
            case R.id.btn_reg:

                merchant_payment_amount=1000.0;
                String hash = merchant_key + "|" + merchant_trxnId + "|" + merchant_payment_amount + "|" +
                        merchant_productInfo + "|"+ customer_firstName + "|" + customer_email_id + "|"
                        + merchant_udf1 + "|" + merchant_udf2 + "|" + merchant_udf3 + "|" + merchant_udf4 + "|"
                        + merchant_udf5 + "||||||" + salt + "|"+ merchant_key;
                Log.e( "hashchk: ",hash );
                hashgeneration(hash);
                Log.e( "onClick: ",merchant_trxnId+"  " + merchant_payment_amount+" "+merchant_productInfo+" "+customer_firstName+""+customer_email_id);

                payment(merchant_trxnId, merchant_payment_amount, merchant_productInfo,
                        customer_firstName,customer_email_id, customer_phone,
                        merchant_key, merchant_udf1, merchant_udf2,
                        merchant_udf3, merchant_udf4, merchant_udf5, customer_address1, customer_address2,
                        customer_city, customer_state,
                        customer_country, customer_zipcode, sb, customers_unique_id, payment_mode);

              /*  if(binding.etFirstname.getText().toString().equals("")|| binding.etLastname.getText().toString().equals("")
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

                }*/
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
                        if (regResponce.status == 201)
                        {
                           Toast.makeText(activity, "" + regResponce.message, Toast.LENGTH_SHORT).show();
                           Log.e("onResponse: ", " " + regResponce.message);

                        }
                        else {
                            Toast.makeText(activity, "" + regResponce.message.toString(), Toast.LENGTH_SHORT).show();
                            Log.e("onResponse: ", " " + new Gson().toJson(regResponce.message));
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

    void payment(String merchant_trxnId, Double merchant_payment_amount, String merchant_productInfo,
                 String customer_firstName,
                 String customer_email_id, String customer_phone, String merchant_key, String merchant_udf1,
                 String merchant_udf2, String merchant_udf3, String merchant_udf4, String merchant_udf5,
                 String customer_address1, String customer_address2, String customer_city,
                 String customer_state, String customer_country, String customer_zipcode, StringBuilder sb,
                 int customers_unique_id, String payment_mode) {
        Intent intentProceed = new Intent(RegActivity.this, PWECouponsActivity.class);
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
                     //   addFees(prefManager.getUSER_ID(), String.valueOf(merchant_payment_amount), merchant_trxnId);
//PWEStaticDataModel. TXN_SUCCESS_CODE is a string constant and its value is “ payment_successfull ”
//Code here will execute if the payment transaction completed successfully.
// here merchant can show the payment success message.
                    } else if (result.contains(PWEStaticDataModel.TXN_TIMEOUT_CODE)) {
//PWEStaticDataModel. TXN_TIMEOUT_CODE is a string constant and its value is “ txn_session_timeout ”
//Code here will execute if the payment transaction failed because of the transaction time out.
// here merchant can show the payment failed message.
                    } else if (result.contains(PWEStaticDataModel.TXN_BACKPRESSED_CODE)) {
//PWEStaticDataModel. TXN_BACKPRESSED_CODE is a string constant and its value is “ back_pressed ”
//Code here will execute if the user pressed the back button on coupons Activity.
// here merchant can show the payment failed message.
                    } else if (result.contains(PWEStaticDataModel.TXN_USERCANCELLED_CODE)) {
//PWEStaticDataModel. TXN_USERCANCELLED_CODE is a string constant and its value is “ user_cancelled ”
//Code here will execute if the the user pressed the cancel button during the payment process.
// here merchant can show the payment failed message.
                    } else if (result.contains(PWEStaticDataModel.TXN_ERROR_SERVER_ERROR_CODE)) {
//PWEStaticDataModel. TXN_ERROR_SERVER_ERROR_CODE is a string constant and its value  is “error_server_error ”
//Code here will execute if the server side error occured during payment process.
// here merchant can show the payment failed message.
                    } else if (result.contains(PWEStaticDataModel.TXN_ERROR_TXN_NOT_ALLOWED_CODE)) {
//PWEStaticDataModel. TXN_ERROR_TXN_NOT_ALLOWED_CODE is a string constant and its value is trxn_not_allowed ”
//Code here will execute if the the transaction is not allowed.
// here merchant can show the payment failed message.
                    } else if (result.contains(PWEStaticDataModel.TXN_BANK_BACK_PRESSED_CODE)) {
//PWEStaticDataModel. TXN_BANK_BACK_PRESSED_CODE is a string constant and its value is “bank_back_pressed”
//Code here will execute if the the customer press the back button on bank screen.
// here merchant can show the payment failed message.
                    } else {
// Here the value of result is “ payment_failed ” or “ error_noretry ” or “ retry_fail_error ”
//Code here will execute if payment is failed some other reasons.
// here merchant can show the payment failed message.
                    }
                } catch (Exception e) {
//Handle exceptions here
                }
            }
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