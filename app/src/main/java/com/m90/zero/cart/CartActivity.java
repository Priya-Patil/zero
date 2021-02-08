package com.m90.zero.cart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ScrollView;
import android.widget.Toast;

import com.easebuzz.payment.kit.PWECouponsActivity;
import com.m90.zero.R;
import com.m90.zero.cart.adapter.CartAdapter;
import com.m90.zero.cart.adapter.WishlistAdapter;
import com.m90.zero.cart.api.CartApi;
import com.m90.zero.cart.model.CartResponse;
import com.m90.zero.cart.model.ConfrimOrderResponse;
import com.m90.zero.cart.model.ContentResponse;
import com.m90.zero.cart.model.EmptyCartResponse;
import com.m90.zero.cart.model.CustomItemForPlaceOrder;
import com.m90.zero.cart.model.PlaceOrderResponse;
import com.m90.zero.databinding.ActivityCartBinding;
import com.m90.zero.home.HomeActivity;
import com.m90.zero.login.LoginActivity;
import com.m90.zero.productdetail.model.CartItem;
import com.m90.zero.profile.api.CityApi;
import com.m90.zero.profile.api.ProfileApi;
import com.m90.zero.profile.api.StateApi;
import com.m90.zero.profile.model.ProfileResponse;
import com.m90.zero.profile.model.StateDetailsResponse;
import com.m90.zero.profile.model.StateResponse;
import com.m90.zero.retrofit.RetrofitClientInstance;
import com.m90.zero.utils.PrefManager;
import com.m90.zero.utils.Utilities;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import datamodels.PWEStaticDataModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity implements View.OnClickListener {

    public static String TAG = CartActivity.class.getSimpleName();
    public static  ActivityCartBinding binding;
    ProgressDialog progressDialog;
    Activity activity;
    CartAdapter cartAdapter;
    PrefManager prefManager;
    WishlistAdapter wishlistAdapter;

    String userId;

    ArrayList<CartItem> cartItemArrayList;
    ArrayList<CustomItemForPlaceOrder> stringToarrayForPlace =  new ArrayList<>();
    private int[] myImageList = new int[]{R.drawable.headpgns, R.drawable.headpgns, R.drawable.headpgns, R.drawable.headpgns, R.drawable.headpgns, R.drawable.headpgns};

    private String[] myImageNameList = new String[]{"Add User", "User History", "Event Message", "Event History", "Payment History", "Contact"};
    private String[] myCategory = new String[]{"User", "Home", "Message", "History", "History", "Contact"};
    private int[] rate = new int[]{4,3,2,11,6,5};
    private int[] amount = new int[]{4,3,2,11,6,3};
    ArrayList<ContentResponse> imageModelYouTubeArrayList;
    String wishlistORcart;
    String onlineORcod;
    String stringToarray;

    ScrollView scrollView;

    int count=0 , stateIdFetch = 0;
    int cityIdP = 0, cityIdFetch = 0;

    String accessToken ;

    String merchant_trxnId;
    Double merchant_payment_amount;
    String merchant_productInfo="Eureka" ;
    String customer_firstName ="" ;
    String customer_email_id = "";
    String customer_phone = "" ;
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
    int order_id;
    String payment_mode = "test";

    StringBuilder sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_cart);
        activity = CartActivity.this;
        progressDialog =  new ProgressDialog(activity);
        prefManager =  new PrefManager(activity);
        binding.btnBack.setOnClickListener(this);

        TrxnId();

        bindViewForWishlist();

       //accessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiOTZkOTg0NDkzMjdmMzg5YTQ2NmQ5NTdjYWRlNzQxMjE3YTIyZmM3YzE4NThjY2RhMjRjNDg5NTU3MmZkMWFlNzBhOGVhOTc2NDI0MGU5ZmEiLCJpYXQiOiIxNjEyNjA1MDM2Ljc4MDgwNSIsIm5iZiI6IjE2MTI2MDUwMzYuNzgwODExIiwiZXhwIjoiMTY0NDE0MTAzNi43NzcyODAiLCJzdWIiOiIxMCIsInNjb3BlcyI6W119.3-K5Lt55fB7LA1G8dNc9URd7fArgHSSzgOQRoYMBfhBPr5nm17zJNWW33ZQqfFZWAk0Fv96AzMRQDMuq1eBM7MzVokvrhCw0l5Pt0Yo28_AJPW_HiF5FIpYTmapg0FFCqG7ud8qJP2zmG_dtG_ilqiZUCMju3h5WYWqgPkyDITVtba9_eQPbfz4M-2pGSqgImellaPVDZUCu89slmwiRFgR_rchPsWAQ8-t6hCn15k_9Yf3eO2nkXsm9gHdtAB9YsJVCFKWJ39xfZ93RwH3gC_irdcPccPf9vuK6xcJmnwvM6fCEL9Wl8zm4CsPWyiMhbKzxkQ4N86tdP3pRxSel7IFfTQLn-Ys1iFel-UacUVI2BFNEe308QMJURUmO9qNEkcYik8QSb65RIcOz6D0-oCLJMIrtefUQ5lKDf6Ipi9VTlOG8QUCuqto-H2kBsUtIPSLSc8Af06DPmMxZW2e6gPKL9c1CccS4u_HPQfSSbBMOCkC51WARM6nr_P9O1sgwcY-ZqONUYq8vhi4yOYeDrey0nWx0OCjWCYcYWo0KD5sduDd_MGE6uVuIgExogAGnGR0uAv1A3VSJQ-rp9D7s1BbfoW8K5xwCMO0L9drCUy93IczoAIZIWSJW0Eh9aK1pjZyDtAnZArPdGD3m4s5jg_89-weYAY2dRHZF9Lk8eco";
        //getcart("10","cart",accessToken);

        try {
            accessToken = Utilities.getSavedUserData(activity,"accesstoken");
            wishlistORcart = getIntent().getStringExtra("wishlistORcart");
            userId = Utilities.getSavedUserData(activity,"userid");
            Log.e(TAG, "onCreateAc: "+userId +" "+wishlistORcart +" "+accessToken );

            if (wishlistORcart.equals("wishlist")){
                binding.txt.setText("Wishlist");

                binding.llTotamt.setVisibility(View.GONE);
                binding.llForcart.setVisibility(View.GONE);
                binding.llPlace.setVisibility(View.GONE);
                binding.tvEmptyWishlist.setVisibility(View.VISIBLE);
                binding.tvEmptyCart.setVisibility(View.GONE);
                binding.scrollView.setVisibility(View.GONE);
                getWishlist(userId,wishlistORcart,accessToken);
            }
            else if(wishlistORcart.equals("cart")){

                binding.txt.setText("Cart");
                Log.e(TAG, "onCreate: "+userId +""+wishlistORcart );


                binding.llTotamt.setVisibility(View.GONE);
                binding.llForcart.setVisibility(View.GONE);
                binding.llPlace.setVisibility(View.GONE);
                binding.tvEmptyWishlist.setVisibility(View.GONE);
                binding.tvEmptyCart.setVisibility(View.GONE);
                binding.scrollView.setVisibility(View.GONE);

                getProfile(Integer.parseInt(userId),accessToken);
                getcart(userId,wishlistORcart,accessToken);
                getState();
                getStateShip();
            }

        }catch (Exception e){
            Log.e(TAG, "onCreate: "+e.getMessage());
        }

    }

    private void bindViewForWishlist() {


        binding.tvEmptyWishlist.setOnClickListener(this);
        binding.tvNavHomeWish.setOnClickListener(this);


        binding.tvEmptyCart.setOnClickListener(this);
        binding.tvPlaceOrder.setOnClickListener(this);
        binding.tvBillingEdit.setOnClickListener(this);
        binding.tvShippingEdit.setOnClickListener(this);
        binding.tvShippingAddress.setOnClickListener(this);
        binding.tvShippingName.setOnClickListener(this);
        binding.tvBillingAddress.setOnClickListener(this);
        binding.tvBillingName.setOnClickListener(this);
        binding.cbDetails.setOnClickListener(this);
        binding.rbCod.setOnClickListener(this);
        binding.rbOnline.setOnClickListener(this);
        binding.tvNavHomeCart.setOnClickListener(this);
        binding.tvCheckout.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.tv_emptyCart:

                emptyDialog(v,wishlistORcart,accessToken);
                //addCartnew("12","455");
                break;


            case R.id.tv_emptyWishlist:
                emptyDialog(v, wishlistORcart,accessToken);
                //addCartnew("12","455");
                break;


            case R.id.tv_billingEdit:
                binding.tvBillingAddress.setEnabled(true);
                binding.tvBillingName.setEnabled(true);
                binding.tvBillingPhone.setEnabled(true);
                binding.tvBillingPincode.setEnabled(true);
                break;

            case R.id.tv_shippingEdit:
                binding.tvShippingAddress.setEnabled(true);
                binding.tvShippingName.setEnabled(true);
                binding.tvShippingPhone.setEnabled(true);
                binding.tvShippingPincode.setEnabled(true);
                break;

            case R.id.tv_shippingAddress:
                binding.tvShippingAddress.setEnabled(true);
                break;

            case R.id.tv_shippingName:
                binding.tvShippingName.setEnabled(true);
                break;

            case R.id.tv_billingAddress:
                binding.tvBillingAddress.setEnabled(true);
                break;

            case R.id.tv_billingName:
                binding.tvShippingName.setEnabled(true);
                break;

            case R.id.rb_cod:
                onlineORcod = "cod";
                break;

            case R.id.rb_online:
                onlineORcod = "online";
                break;

            case R.id.btn_back:
                onBackPressed();
                break;

              case R.id.tv_NavHomeCart:

                Utilities.launchActivity(activity,HomeActivity.class,true);
                break;

              case R.id.tv_NavHomeWish:

                    Utilities.launchActivity(activity,HomeActivity.class,true);
                  break;

            case R.id.cb_details:
                if(binding.cbDetails.isChecked())
                {
                    binding.tvBillingName.setText(binding.tvShippingName.getText().toString());
                    binding.tvBillingPhone.setText(binding.tvShippingPhone.getText().toString());
                    binding.tvBillingAddress.setText(binding.tvShippingAddress.getText().toString());
                    binding.tvBillingPincode.setText(binding.tvShippingPincode.getText().toString());

                }else
                {
                    binding.tvBillingName.setText("");
                    binding.tvBillingPhone.setText("");
                    binding.tvBillingAddress.setText("");
                    binding.tvBillingPincode.setText("");
                }
            break;


            case R.id.tv_placeOrder:

                binding.rvCart.setVisibility(View.VISIBLE);
                binding.llForcart.setVisibility(View.VISIBLE);
                binding.tvPlaceOrder.setVisibility(View.GONE);
                binding.tvCheckout.setVisibility(View.VISIBLE);

           break;


           case R.id.tv_checkout:

                if (binding.tvBillingName.getText().toString().equals("")||
                        binding.tvBillingPhone.getText().toString().equals("")||
                        binding.tvBillingAddress.getText().toString().equals("")||
                        binding.tvBillingPincode.getText().toString().equals(""))
                {
                    Toast.makeText(activity,"Please Enter Billing Details",Toast.LENGTH_SHORT).show();
                }
                else if (binding.tvShippingName.getText().toString().equals("")||
                        binding.tvShippingPhone.getText().toString().equals("")||
                        binding.tvShippingAddress.getText().toString().equals("")||
                        binding.tvShippingPincode.getText().toString().equals("") )
                {
                    Toast.makeText(activity,"Please Enter Shipping Details",Toast.LENGTH_SHORT).show();
                }
                else if (onlineORcod == null){
                    Toast.makeText(activity,"Please choose Payment Type",Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.e(TAG, "onClick: "+accessToken +" "+userId+" "+binding.tvTotFinalAmt.getText().toString()+" "+ String.valueOf(count)+" "+
                            "pending"+" "+onlineORcod+" "+binding.tvShippingName.getText().toString()+" "+ "20"+" "+"30"+" "+
                            binding.tvShippingAddress.getText().toString()+" "+binding.tvShippingPincode.getText().toString()+" "+
                            binding.tvShippingPhone.getText().toString()+" "+binding.tvBillingName.getText().toString()+" "+
                            "20"+" "+"30"+" "+binding.tvBillingAddress.getText().toString()+" "+binding.tvBillingPincode.getText().toString()+" "+
                            binding.tvBillingPhone.getText().toString()+" "+stringToarray );

                    placeOrder(accessToken,userId,binding.tvTotAmt.getText().toString(), String.valueOf(count),
                            "pending",onlineORcod,binding.tvShippingName.getText().toString(), "20","30",
                            binding.tvShippingAddress.getText().toString(),binding.tvShippingPincode.getText().toString(),
                            binding.tvShippingPhone.getText().toString(),binding.tvBillingName.getText().toString(),
                            "20","30",binding.tvBillingAddress.getText().toString(),binding.tvBillingPincode.getText().toString(),
                            binding.tvBillingPhone.getText().toString(),stringToarray);

                  /*  placeOrder(accessToken,"10","23000", "2",
                            "pending","cod","raj Mohan Patil", "20","30",
                            "Meghmalhar complex, N-3, Aurangangabad","431010",
                            "9823017721","raj Mohan Patil",
                            "20","30","Meghmalhar complex, N-3, Aurangangabad","431010",
                            "9823017721","[{\"id\":5,\"price\":868,\"quantity\":3,\"item_image\":\"/storage/products/25.jpg\"},{\"id\":2,\"price\":711,\"quantity\":2,\"item_image\":\"images/1.jpg\"},{\"id\":4,\"price\":257,\"quantity\":1,\"item_image\":\"images/1.jpg\"}]");
*/
                }

                break;
        }
    }

    private void emptyDialog(View v, String wishlistORcart,String accessToken) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity);

        if (wishlistORcart.equals("wishlist")){
            mBuilder.setTitle("Are you sure you want to empty your Wishlist?");
        }
        else if(wishlistORcart.equals("cart")){
            mBuilder.setTitle("Are you sure you want to empty your cart?");
        }

        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

                dialog.dismiss();
                emptyCart(userId,wishlistORcart,accessToken);

            }
        });

        mBuilder.setNegativeButton("Cancel", null);
        AlertDialog mDialog = mBuilder.create();
        mDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_rounded_background);
        mDialog.show();
    }

    void emptyCart(String userId, String wishlistORcart, String accesstoken) {

        CartApi regApi = RetrofitClientInstance.getRetrofitInstanceServer().
                create(CartApi.class);

        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        // pbLoading.setProgressStyle(R.id.abbreviationsBar);
        progressDialog.show();
        regApi.cartEmpty("Bearer "+accesstoken,RetrofitClientInstance.BASE_URL+wishlistORcart+"/empty?user_id="+userId).
                enqueue(new Callback<EmptyCartResponse>() {

                    @Override
                    public void onResponse(Call<EmptyCartResponse> call, Response<EmptyCartResponse> response) {

                        EmptyCartResponse cartResponse = response.body();
                        if (cartResponse.status == 200)
                        {
                            //Toast.makeText(activity, "" + cartResponse.message , Toast.LENGTH_SHORT).show();

                            binding.llTotamt.setVisibility(View.GONE);
                            binding.llForcart.setVisibility(View.GONE);
                            binding.llPlace.setVisibility(View.GONE);
                            binding.tvEmptyWishlist.setVisibility(View.VISIBLE);
                            binding.tvEmptyCart.setVisibility(View.GONE);

                            binding.llForcart.setVisibility(View.VISIBLE);
                            binding.tvEmptyWishlist.setVisibility(View.GONE);
                            binding.tvEmptyCart.setVisibility(View.VISIBLE);

                            finish();
                            startActivity(getIntent());
                        }

                        if (cartResponse.status == 401)
                        {
                            Log.e(TAG, "onResponse: "+cartResponse.message );
                            authenticationDialog(cartResponse.message);
                        }

                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<EmptyCartResponse> call, Throwable t) {

                        progressDialog.dismiss();
                        //Utilities.setError(layout1,layout2,txt_error,getResources().getString(R.string.something_went_wrong));
                        Log.e("errorchkemptyCart", t.getMessage());

                    }
                });

    }

    void emptyCartforPlaceOrder(String userId, String wishlistORcart, String accesstoken) {

        CartApi regApi = RetrofitClientInstance.getRetrofitInstanceServer().
                create(CartApi.class);

        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        // pbLoading.setProgressStyle(R.id.abbreviationsBar);
        progressDialog.show();
        regApi.cartEmpty("Bearer "+accesstoken,RetrofitClientInstance.BASE_URL+wishlistORcart+"/empty?user_id="+userId).
                enqueue(new Callback<EmptyCartResponse>() {

                    @Override
                    public void onResponse(Call<EmptyCartResponse> call, Response<EmptyCartResponse> response) {

                        EmptyCartResponse cartResponse = response.body();
                        if (cartResponse.status == 200)
                        {
                            //Toast.makeText(activity, "" + cartResponse.message , Toast.LENGTH_SHORT).show();

                            // merchant_payment_amount= regResponce.data.amount;
                            merchant_payment_amount= Double.valueOf(binding.tvTotFinalAmt.getText().toString());


                            String hash = merchant_key + "|" + merchant_trxnId + "|" + merchant_payment_amount + "|" +
                                    merchant_productInfo + "|"+ customer_firstName + "|" + customer_email_id + "|"
                                    + merchant_udf1 + "|" + merchant_udf2 + "|" + merchant_udf3 + "|" + merchant_udf4 + "|"
                                    + merchant_udf5 + "||||||" + salt + "|"+ merchant_key;
                            Log.e( "hashchk: ",hash );
                            hashgeneration(hash);
                            Log.e( "onClickMerchant: ",merchant_trxnId+"  " + merchant_payment_amount+" "+merchant_productInfo+" "+customer_firstName+""+customer_email_id
                                    +" "+customer_phone);

                            payment(merchant_trxnId, merchant_payment_amount, merchant_productInfo,
                                    customer_firstName,customer_email_id, customer_phone,
                                    merchant_key, merchant_udf1, merchant_udf2,
                                    merchant_udf3, merchant_udf4, merchant_udf5, customer_address1, customer_address2,
                                    customer_city, customer_state,
                                    customer_country, customer_zipcode, sb, customers_unique_id, payment_mode);

                        }


                        if (cartResponse.status == 401)
                        {
                            Log.e(TAG, "onResponse: "+cartResponse.message );
                            authenticationDialog(cartResponse.message);
                        }

                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<EmptyCartResponse> call, Throwable t) {

                        progressDialog.dismiss();
                        //Utilities.setError(layout1,layout2,txt_error,getResources().getString(R.string.something_went_wrong));
                        Log.e("errorchkemptyCart", t.getMessage());

                    }
                });

    }

    void getcart(String customerId, String wishlistORcart,String code) {

        cartItemArrayList=new ArrayList<CartItem>();

        if (Utilities.isNetworkAvailable(activity)){
            CartApi cartApi = RetrofitClientInstance.getRetrofitInstanceServer().
                    create(CartApi.class);

            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            cartApi.getCart("Bearer "+code,RetrofitClientInstance.BASE_URL+wishlistORcart+"?user_id="+customerId).
                    enqueue(new Callback<CartResponse>() {

                        @Override
                        public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {

                            CartResponse cartResponse = response.body();
                            Log.e("getcart: ", " " + cartResponse.toString());
                            if (cartResponse.status == 200) {

                                if (cartResponse.data.count == 0) {
                                    binding.llCartNoItem.setVisibility(View.VISIBLE);
                                    binding.rvCart.setVisibility(View.GONE);
                                    binding.llTotamt.setVisibility(View.GONE);
                                    binding.llPlace.setVisibility(View.GONE);
                                    binding.tvEmptyCart.setVisibility(View.GONE);

                                }else{
                                    binding.llCartNoItem.setVisibility(View.GONE);
                                    binding.rvCart.setVisibility(View.VISIBLE);
                                    binding.llForcart.setVisibility(View.GONE);
                                    binding.llTotamt.setVisibility(View.VISIBLE);
                                    binding.llPlace.setVisibility(View.VISIBLE);
                                    binding.tvEmptyCart.setVisibility(View.VISIBLE);
                                    binding.scrollView.setVisibility(View.VISIBLE);

                                    binding.tvTotFinalAmt.setText(" "+cartResponse.data.total);
                                    count = cartResponse.data.count;
                                    prefManager.setCartCount(cartResponse.data.count);
                                    String image = "";

                                    Iterator myVeryOwnIterator = cartResponse.data.items.keySet().iterator();
                                    Iterator myVeryIterator = cartResponse.data.items.keySet().iterator();
                                    while(myVeryOwnIterator.hasNext()) {
                                        String key = (String) myVeryOwnIterator.next();
                                        int value = Integer.parseInt(cartResponse.data.items.get(key).id);
                                        int strId = Integer.parseInt(cartResponse.data.items.get(key).id);
                                        int price = cartResponse.data.items.get(key).price;
                                        int quanity = cartResponse.data.items.get(key).quantity;
                                        image = cartResponse.data.items.get(key).image;
                                        cartItemArrayList.add(cartResponse.data.items.get(key));

                                       // image = "\"quantity\"";
                                        Log.e(TAG, "onResponse: " + key + " Values " + value + " " + price + " " + quanity+" "+image);

                                        CustomItemForPlaceOrder customItemForPlaceOrder = new CustomItemForPlaceOrder(value, price, quanity, image);
                                        stringToarrayForPlace.add(customItemForPlaceOrder);
                                    }

                                    setCartData(cartItemArrayList,code);

                                    String s1 = TextUtils.join(",", stringToarrayForPlace);
                                    String arryToString= "["+s1+"]";
                                    String rmvstringItemToPlaceCustom = arryToString.replace("CustomItemForPlaceOrder", "");
                                    String rmvSpace = rmvstringItemToPlaceCustom.replace(", ", ",");
                                    String replaceEqualeto = rmvSpace.replace("=", ":");
                                    String strId = replaceEqualeto.replace("id","\"id\"");
                                    String strPrice = strId.replace("price","\"price\"");
                                    String strQuantity = strPrice.replace("quantity","\"quantity\"");
                                    String strItemImg = strQuantity.replace("item_image","\"item_image\"");
                                    stringToarray = strItemImg.replace("\'" ,"\"");
                                    //stringToarray = strId;

                                    Log.e(TAG, "stringToarray:sxaaa\n "+stringToarray);
                                 }
                            }

                            if (cartResponse.status == 401)
                            {
                                Log.e(TAG, "onResponse: "+cartResponse.message );
                                authenticationDialog(cartResponse.message);
                            }

                            progressDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<CartResponse> call, Throwable t) {

                            progressDialog.dismiss();
                            //Utilities.setError(layout1,layout2,txt_error,getResources().getString(R.string.something_went_wrong));
                            Log.e("errorgetcart", t.getMessage());

                        }
                    });
        } else {
            Toast.makeText(activity, getResources().getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
        }
    }

    void orderConfirm(String accessToken,int order_id, String txnid,String easepayid,String status,String error) {

        cartItemArrayList=new ArrayList<CartItem>();

        if (Utilities.isNetworkAvailable(activity)){
            CartApi orderApi = RetrofitClientInstance.getRetrofitInstanceServer().
                    create(CartApi.class);

            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            orderApi.orderConfirm("Bearer "+accessToken, order_id,txnid,easepayid,status,error).
                    enqueue(new Callback<ConfrimOrderResponse>() {

                        @Override
                        public void onResponse(Call<ConfrimOrderResponse> call, Response<ConfrimOrderResponse> response) {

                            ConfrimOrderResponse cartResponse = response.body();
                            Log.e(TAG,"ConfrimOrderResponse: "+ cartResponse.toString());

                            if (cartResponse.status == 200) {
                                Toast.makeText(activity, "" + cartResponse.message, Toast.LENGTH_SHORT).show();
                            }

                            if (cartResponse.status == 401)
                            {
                                Log.e(TAG, "onResponse: "+cartResponse.message );
                                authenticationDialog(String.valueOf(cartResponse.message));
                            }

                            /*if (cartResponse.status == 206)
                            {

                                Toast.makeText(activity, "" + cartResponse.message, Toast.LENGTH_SHORT).show();
                                Log.e(TAG, "onResponse: "+cartResponse.toString() );

                            }*/

                            progressDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<ConfrimOrderResponse> call, Throwable t) {

                            progressDialog.dismiss();
                            //Utilities.setError(layout1,layout2,txt_error,getResources().getString(R.string.something_went_wrong));
                            Log.e("errorgetcart", t.getMessage());

                        }
                    });
        } else {
            Toast.makeText(activity, getResources().getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
        }
    }

    void getWishlist(String customerId, String wishlistORcart,String code) {
        cartItemArrayList=new ArrayList<CartItem>();

        if(Utilities.isNetworkAvailable(activity)){
            CartApi cartApi = RetrofitClientInstance.getRetrofitInstanceServer().
                    create(CartApi.class);

            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            // pbLoading.setProgressStyle(R.id.abbreviationsBar);
            progressDialog.show();

            //http://z.eurekatalents.in/api/cart?user_id=12
            cartApi.getCart("Bearer "+code,RetrofitClientInstance.BASE_URL+wishlistORcart+"?user_id="+customerId).
                    enqueue(new Callback<CartResponse>() {

                        @Override
                        public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {

                            CartResponse cartResponse = response.body();
                            Log.e("onResponse: ", " " + cartResponse.toString());

                            if (cartResponse.status == 200) {

                                binding.llWishlistNoItem.setVisibility(View.GONE);
                                binding.rvCart.setVisibility(View.VISIBLE);

                                if (cartResponse.data.count == 0) {
                                    binding.llWishlistNoItem.setVisibility(View.VISIBLE);
                                    binding.rvCart.setVisibility(View.GONE);
                                    binding.tvEmptyWishlist.setVisibility(View.GONE);
                                }else{
                                    binding.llWishlistNoItem.setVisibility(View.GONE);
                                    binding.rvCart.setVisibility(View.VISIBLE);
                                    binding.tvEmptyWishlist.setVisibility(View.VISIBLE);
                                    binding.scrollView.setVisibility(View.VISIBLE);

                                    Iterator myVeryOwnIterator = cartResponse.data.items.keySet().iterator();
                                    while (myVeryOwnIterator.hasNext()) {
                                        String key = (String) myVeryOwnIterator.next();
                                        String value = cartResponse.data.items.get(key).name;
                                        cartItemArrayList.add(cartResponse.data.items.get(key));
                                        //  Toast.makeText( "Key: "+key+" Value: "+value, Toast.LENGTH_LONG).show();
                                    }
                                    Log.e("chkonResponse: ", cartItemArrayList.toString());
                                    setWishlistData(cartItemArrayList, code);
                                }

                            }



                            if (cartResponse.status == 401)
                            {
                                Log.e(TAG, "onResponse: "+cartResponse.message );
                                authenticationDialog(cartResponse.message);
                            }

                            progressDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<CartResponse> call, Throwable t) {

                            progressDialog.dismiss();
                            //Utilities.setError(layout1,layout2,txt_error,getResources().getString(R.string.something_went_wrong));
                            Log.e("errorgetWishlist", t.getMessage());

                        }
                    });
        } else {
            Toast.makeText(activity, getResources().getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
        }
    }

    private void setCartData(ArrayList<CartItem> productDescribeAddResons,String code) {
        binding.rvCart.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        // mRecyclerViewCourses.setLayoutManager(glm);
        binding.rvCart.setItemAnimator(new DefaultItemAnimator());
        binding.rvCart.setHasFixedSize(true);

        cartAdapter = new CartAdapter(this, productDescribeAddResons,code,
                new CartAdapter.ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {

                    }
                });
        binding.rvCart.setAdapter(cartAdapter);

    }

    private void setWishlistData(ArrayList<CartItem> productDescribeAddResons, String code) {
        binding.rvCart.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        // mRecyclerViewCourses.setLayoutManager(glm);
        binding.rvCart.setItemAnimator(new DefaultItemAnimator());
        binding.rvCart.setHasFixedSize(true);

        wishlistAdapter = new WishlistAdapter(this, productDescribeAddResons,code,
                new WishlistAdapter.ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {

                    }
                });
        binding.rvCart.setAdapter(wishlistAdapter);

    }

    void placeOrder(String accessToken,String userid, String totAmount, String totQuantity,String is_paid,String payMethod,
                    String shipFullName,String shipState,String shipCity,String shipAddr,String shipPincode,
                    String shipPhone,String billFullName,String billState,String billCity,String billAddr,String billPincode,
                    String billPhone,String stringToarray) {

        CartApi cartApi = RetrofitClientInstance.getRetrofitInstanceServer().
                create(CartApi.class);

        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Log.e(TAG, "placeOrder: "+stringToarray );
        cartApi.placeOrder("Bearer "+accessToken,userid,totAmount, totQuantity,is_paid,payMethod,
                shipFullName,shipState,shipCity,shipAddr,shipPincode,
                shipPhone,billFullName, billState, billCity, billAddr, billPincode,
                billPhone,stringToarray).
                enqueue(new Callback<PlaceOrderResponse>() {

                    @Override
                    public void onResponse(Call<PlaceOrderResponse> call, Response<PlaceOrderResponse> response) {

                        PlaceOrderResponse cartResponse = response.body();
                        Log.e(TAG, "placeOrder: "+cartResponse.toString());

                        if (cartResponse.status == 200)
                        {
                            Toast.makeText(activity,""+cartResponse.message,Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "PlaceOrderResponse: "+cartResponse.toString());
                            order_id = cartResponse.data.items.get(0).order_id;

                            emptyCartforPlaceOrder(userId,wishlistORcart,accessToken);
                        }

                        if (cartResponse.status == 401)
                        {
                            Log.e(TAG, "onResponse: "+cartResponse.message );
                            authenticationDialog(String.valueOf(cartResponse.message));
                        }

                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<PlaceOrderResponse> call, Throwable t) {

                        progressDialog.dismiss();
                        //Utilities.setError(layout1,layout2,txt_error,getResources().getString(R.string.something_went_wrong));
                        Log.e("ChkPlaceOrderResponse", t.getMessage());

                    }
                });

    }

    void getProfile(int user_id,String accessToken) {

        if(Utilities.isNetworkAvailable(activity)){
            ProfileApi profileApi = RetrofitClientInstance.getRetrofitInstanceServer().
                    create(ProfileApi.class);

            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            profileApi.getProfile("Bearer "+accessToken,user_id).

                    enqueue(new Callback<ProfileResponse>() {

                        @Override
                        public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {

                            ProfileResponse profileResponse = response.body();

                            Log.e(TAG,"onResponsezz: " + profileResponse.toString());
                            if (profileResponse.status== 200) {

                                customer_firstName=profileResponse.data.profile.get(0).first_name+" "+
                                        profileResponse.data.profile.get(0).middle_name +" "+
                                        profileResponse.data.profile.get(0).last_name;

                                customer_email_id=profileResponse.data.profile.get(0).user.email;
                                customer_phone= profileResponse.data.profile.get(0).user.mobile_number;

                                binding.tvShippingName.setText(profileResponse.data.profile.get(0).first_name+" "+
                                        profileResponse.data.profile.get(0).middle_name +" "+
                                        profileResponse.data.profile.get(0).last_name);

                                binding.tvShippingAddress.setText(profileResponse.data.profile.get(0).address);
                                binding.tvShippingPincode.setText(profileResponse.data.profile.get(0).pincode);
                                binding.tvShippingPhone.setText(profileResponse.data.profile.get(0).user.mobile_number);

                                Toast.makeText(activity, "" + profileResponse.message, Toast.LENGTH_SHORT).show();
                                Log.e(TAG,"onResponseaa: " + profileResponse.message);
                            }

                            if (profileResponse.status == 401)
                            {
                                Log.e(TAG,"onResponsezz: " + profileResponse.message);
                                authenticationDialog(profileResponse.message);
                                //Toast.makeText(activity, "" + profileResponse.message , Toast.LENGTH_SHORT).show();
                            }

                            progressDialog.dismiss();

                        }

                        @Override
                        public void onFailure(Call<ProfileResponse> call, Throwable t) {

                            progressDialog.dismiss();
                            Log.e(TAG,"errorchk getProf "+ t.getMessage());

                        }
                    });

        } else {
            Toast.makeText(activity, getResources().getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
        }
    }

    void getState() {

        if (Utilities.isNetworkAvailable(activity)){
            StateApi stateApi = RetrofitClientInstance.getRetrofitInstanceServer().
                    create(StateApi.class);

            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            stateApi.getState().enqueue(new Callback<StateResponse>() {
                @Override
                public void onResponse(Call<StateResponse> call, Response<StateResponse> response) {
                    StateResponse stateResponse = response.body();
                    ArrayList<StateDetailsResponse> stateDetailsResponses = stateResponse.data;

                    // Log.e(TAG, "onResponse: "+stateResponse.toString());

                    if (stateResponse.status == 200) {
                        Toast.makeText(activity, "" + stateResponse.message, Toast.LENGTH_SHORT).show();
                        //Log.e(TAG, "onResponse: "+stateResponse.toString()+" "+stateDetailsResponses.toString() );

                        String[] spinnerArray = new String[stateResponse.data.size()];
                        HashMap<Integer,String> spinnerMap = new HashMap<Integer, String>();
                        for (int i = 0; i < stateResponse.data.size(); i++)
                        {
                            spinnerMap.put(i, String.valueOf(stateResponse.data.get(i).id));
                            spinnerArray[i] = stateResponse.data.get(i).name;
                        }

                        ArrayAdapter<String> adapter =new ArrayAdapter<String>(activity,
                                android.R.layout.simple_spinner_item, spinnerArray);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.tvBillingstate.setAdapter(adapter);

                        binding.tvBillingstate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {

                                String name = binding.tvBillingstate.getSelectedItem().toString();
                                String id1 = spinnerMap.get(binding.tvBillingstate.getSelectedItemPosition());

                                if(stateIdFetch!=0)
                                {
                                    int selectionPosition= adapter.getPosition("id");
                                    binding.tvBillingstate.setSelection(stateIdFetch);
                                    getCityFromState(String.valueOf(stateIdFetch));
                                }
                                else {
                                    int selectionPosition= adapter.getPosition("id");
                                    binding.tvBillingstate.setSelection(selectionPosition);
                                    getCityFromState(id1);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });

                    } else {
                        Toast.makeText(activity, "" + stateResponse.message, Toast.LENGTH_SHORT).show();
                        Log.e("onResponse: ", " " + stateResponse.message);
                    }
                    progressDialog.dismiss();

                }

                @Override
                public void onFailure(Call<StateResponse> call, Throwable t) {

                    progressDialog.dismiss();
                    //Utilities.setError(layout1,layout2,txt_error,getResources().getString(R.string.something_went_wrong));
                    Log.e("errorchkupProf", t.getMessage());

                }
            });
        } else {
            Toast.makeText(activity, getResources().getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
        }
    }

    void getCityFromState(String stateid) {

        Log.e("onResponse:ssss ", "called");

        if (Utilities.isNetworkAvailable(activity)){
            CityApi cityApi = RetrofitClientInstance.getRetrofitInstanceServer().
                    create(CityApi.class);

            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            cityApi.getCity(RetrofitClientInstance.BASE_URL+"city/"+stateid).
                    //cityApi.getCity("http://api.eurekatalents.in/api/city/"+stateid).
                            enqueue(new Callback<StateResponse>() {

                        @Override
                        public void onResponse(Call<StateResponse> call, Response<StateResponse> response) {

                            StateResponse stateResponse = response.body();
                            // ArrayList<StateDetailsResponse> stateDetailsResponses = stateResponse.data;

                            Log.e( "toString: ",stateResponse.toString() );
                            String[] spinnerArray = new String[stateResponse.data.size()];
                            HashMap<Integer,String> spinnerMap = new HashMap<Integer, String>();
                            for (int i = 0; i < stateResponse.data.size(); i++)
                            {
                                spinnerMap.put(i, String.valueOf(stateResponse.data.get(i).id));
                                spinnerArray[i] = stateResponse.data.get(i).name;
                            }

                            ArrayAdapter<String> adapter =new ArrayAdapter<String>(activity,android.R.layout.simple_spinner_item, spinnerArray);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            binding.tvBillingcity.setAdapter(adapter);
                            binding.tvBillingcity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {

                                    String name = binding.tvBillingcity.getSelectedItem().toString();

                                    if(stateIdFetch!=0)
                                    {
                                        int selectionPosition= adapter.getPosition("id");
                                        binding.tvBillingcity.setSelection(selectionPosition);
                                        stateIdFetch=0;
                                    }
                                    else {
                                        binding.tvBillingcity.setSelection(0);
                                    }
                                    Toast.makeText(activity, "mmmm "+name+"    ", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });

                            progressDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<StateResponse> call, Throwable t) {

                            progressDialog.dismiss();
                            //Utilities.setError(layout1,layout2,txt_error,getResources().getString(R.string.something_went_wrong));
                            Log.e("errorchk", t.getMessage());

                        }
                    });
        } else {
            Toast.makeText(activity, getResources().getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
        }
    }

    void getStateShip() {

        if (Utilities.isNetworkAvailable(activity)){
            StateApi stateApi = RetrofitClientInstance.getRetrofitInstanceServer().
                    create(StateApi.class);

            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            stateApi.getState().enqueue(new Callback<StateResponse>() {
                @Override
                public void onResponse(Call<StateResponse> call, Response<StateResponse> response) {
                    StateResponse stateResponse = response.body();
                    ArrayList<StateDetailsResponse> stateDetailsResponses = stateResponse.data;

                    // Log.e(TAG, "onResponse: "+stateResponse.toString());

                    if (stateResponse.status == 200) {
                        Toast.makeText(activity, "" + stateResponse.message, Toast.LENGTH_SHORT).show();
                        //Log.e(TAG, "onResponse: "+stateResponse.toString()+" "+stateDetailsResponses.toString() );

                        String[] spinnerArray = new String[stateResponse.data.size()];
                        HashMap<Integer,String> spinnerMap = new HashMap<Integer, String>();
                        for (int i = 0; i < stateResponse.data.size(); i++)
                        {
                            spinnerMap.put(i, String.valueOf(stateResponse.data.get(i).id));
                            spinnerArray[i] = stateResponse.data.get(i).name;
                        }

                        ArrayAdapter<String> adapter =new ArrayAdapter<String>(activity,
                                android.R.layout.simple_spinner_item, spinnerArray);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.tvShippingstate.setAdapter(adapter);

                        binding.tvShippingstate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {

                                String name = binding.tvShippingstate.getSelectedItem().toString();
                                String id1 = spinnerMap.get(binding.tvShippingstate.getSelectedItemPosition());

                                if(stateIdFetch!=0)
                                {
                                    int selectionPosition= adapter.getPosition("id");
                                    binding.tvShippingstate.setSelection(stateIdFetch);
                                    getCityFromStateShip(String.valueOf(stateIdFetch));
                                }
                                else {
                                    int selectionPosition= adapter.getPosition("id");
                                    binding.tvShippingstate.setSelection(selectionPosition);
                                    getCityFromStateShip(id1);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });

                    } else {
                        Toast.makeText(activity, "" + stateResponse.message, Toast.LENGTH_SHORT).show();
                        Log.e("onResponse: ", " " + stateResponse.message);
                    }
                    progressDialog.dismiss();

                }

                @Override
                public void onFailure(Call<StateResponse> call, Throwable t) {

                    progressDialog.dismiss();
                    //Utilities.setError(layout1,layout2,txt_error,getResources().getString(R.string.something_went_wrong));
                    Log.e("errorchkupProf", t.getMessage());

                }
            });
        } else {
            Toast.makeText(activity, getResources().getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
        }
    }

    void getCityFromStateShip(String stateid) {
        Log.e("onResponse:ssss ", "called");

        if (Utilities.isNetworkAvailable(activity)){
            CityApi cityApi = RetrofitClientInstance.getRetrofitInstanceServer().
                    create(CityApi.class);

            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            cityApi.getCity(RetrofitClientInstance.BASE_URL+"city/"+stateid).
                    //cityApi.getCity("http://api.eurekatalents.in/api/city/"+stateid).
                            enqueue(new Callback<StateResponse>() {

                        @Override
                        public void onResponse(Call<StateResponse> call, Response<StateResponse> response) {

                            StateResponse stateResponse = response.body();
                            // ArrayList<StateDetailsResponse> stateDetailsResponses = stateResponse.data;

                            progressDialog.dismiss();
                            Log.e( "toString: ",stateResponse.toString() );
                            String[] spinnerArray = new String[stateResponse.data.size()];
                            HashMap<Integer,String> spinnerMap = new HashMap<Integer, String>();
                            for (int i = 0; i < stateResponse.data.size(); i++)
                            {
                                spinnerMap.put(i, String.valueOf(stateResponse.data.get(i).id));
                                spinnerArray[i] = stateResponse.data.get(i).name;
                            }

                            ArrayAdapter<String> adapter =new ArrayAdapter<String>(activity,android.R.layout.simple_spinner_item, spinnerArray);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            binding.tvShippingcity.setAdapter(adapter);
                            binding.tvShippingcity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {

                                    String name = binding.tvShippingcity.getSelectedItem().toString();

                                    if(stateIdFetch!=0)
                                    {
                                        int selectionPosition= adapter.getPosition("id");
                                        binding.tvShippingcity.setSelection(selectionPosition);
                                        stateIdFetch=0;
                                    }
                                    else {
                                        binding.tvShippingcity.setSelection(0);
                                    }
                                    //Toast.makeText(activity, "mmmm "+name+"    ", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });


                        }

                        @Override
                        public void onFailure(Call<StateResponse> call, Throwable t) {

                            progressDialog.dismiss();
                            //Utilities.setError(layout1,layout2,txt_error,getResources().getString(R.string.something_went_wrong));
                            Log.e("errorchk", t.getMessage());

                        }
                    });
        } else {
            Toast.makeText(activity, getResources().getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
        }
    }

    void payment(String merchant_trxnId, Double merchant_payment_amount, String merchant_productInfo,
                 String customer_firstName,
                 String customer_email_id, String customer_phone, String merchant_key, String merchant_udf1,
                 String merchant_udf2, String merchant_udf3, String merchant_udf4, String merchant_udf5,
                 String customer_address1, String customer_address2, String customer_city,
                 String customer_state, String customer_country, String customer_zipcode, StringBuilder sb,
                 int customers_unique_id, String payment_mode) {
        Intent intentProceed = new Intent(activity, PWECouponsActivity.class);
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
                        Log.e(TAG, accessToken+" "+order_id+" "+obj.getString("txnid")+" "+obj.getString("easepayid")+" "+
                                obj.getString("status")+" "+obj.getString("error"));

                        orderConfirm(accessToken,order_id,obj.getString("txnid"),obj.getString("easepayid"),
                                obj.getString("status"),obj.getString("error"));


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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}