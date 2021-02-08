package com.m90.zero.productdetail;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.m90.zero.R;
import com.m90.zero.cart.CartActivity;
import com.m90.zero.cart.api.CartApi;
import com.m90.zero.cart.model.AddCartResponse;
import com.m90.zero.home.HomeActivity;
import com.m90.zero.home.model.SliderDetails;
import com.m90.zero.login.LoginActivity;
import com.m90.zero.productdetail.api.ProductDescribeApi;
import com.m90.zero.productdetail.model.ProductDescribeResponse;
import com.m90.zero.retrofit.RetrofitClientInstance;
import com.m90.zero.utils.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener, View.OnClickListener {

    public static String TAG = ProductDetailsActivity.class.getSimpleName();
    ProgressDialog progressDialog;
    Activity activity;
    private SliderLayout mSlider;
    List<SliderDetails> sliderDetails;

    ProductDescribeResponse productDescribeResponse;

    private ImageView btnOpenDrawer;
    private TextView txt;
    private ImageView ivCart;
    private SliderLayout slider;
    private TextView tvName;
    private TextView tvShoartDesc;
    private TextView tvDesc;
    private TextView tvQty;
    private TextView tvUnitType;
    private TextView tvRating;
    private TextView tvNote;
    private TextView tv_addToCart;
    private TextView tv_qtyInc;
    private TextView txt_title;
    private ImageView iv_add;
    private ImageView iv_sub;
    private ImageView iv_wishlist;

    String product_id;
    String user_id;
    String product_name;

    String wishlistORCart;
    String accesstoken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_product_details);
        activity = ProductDetailsActivity.this;
        progressDialog =  new ProgressDialog(activity);

        initView();
        try {
            product_id = getIntent().getStringExtra("product_id");
            product_name = getIntent().getStringExtra("product_name");
            Log.e(TAG, "onCreate: "+product_id);
            user_id = Utilities.getSavedUserData(activity,"userid");
            accesstoken = Utilities.getSavedUserData(activity,"accesstoken");
            txt_title.setText("Product Details");
            getProductDescribe(Integer.parseInt(product_id));

        }catch (Exception e){
            Log.e(TAG, "onCreate: "+e.getMessage() );
        }
        btnOpenDrawer.setOnClickListener(this);
        iv_add.setOnClickListener(this);
        iv_sub.setOnClickListener(this);
        tv_addToCart.setOnClickListener(this);
        iv_wishlist.setOnClickListener(this);
    }

    private void initView() {
        mSlider = findViewById(R.id.slider);
        btnOpenDrawer = (ImageView) findViewById(R.id.btn_openDrawer);
        txt = (TextView) findViewById(R.id.txt);
        ivCart = (ImageView) findViewById(R.id.iv_cart);
        slider = (SliderLayout) findViewById(R.id.slider);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvShoartDesc = (TextView) findViewById(R.id.tv_shoartDesc);
        tvDesc = (TextView) findViewById(R.id.tv_Desc);
        tvQty = (TextView) findViewById(R.id.tv_qty);
        tvUnitType = (TextView) findViewById(R.id.tv_unitType);
        tvRating = (TextView) findViewById(R.id.tv_rating);
        tvNote = (TextView) findViewById(R.id.tv_note);
        tv_qtyInc = (TextView) findViewById(R.id.tv_qtyInc);
        iv_add =  findViewById(R.id.iv_add);
        iv_sub =  findViewById(R.id.iv_sub);
        tv_addToCart =  findViewById(R.id.tv_addToCart);
        iv_wishlist =  findViewById(R.id.iv_wishlist);
        txt_title =  findViewById(R.id.txt_title);
    }

    public void slider(String pictures) {

           String phrase = pictures;
            String[] dateSplit = phrase.split(";");

        for (String name : dateSplit) {
            //if (phrase!=null)
            Log.e("TAG", "onBindViewHolder: "+name);
                TextSliderView textSliderView = new TextSliderView(activity);
                // initialize a SliderLayout
                textSliderView
                        //.description(name)
                        .image(RetrofitClientInstance.BASE_URL_IMG+name)
                        //.image("http://api.eurekatalents.in/"+name)
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(ProductDetailsActivity.this);

                //add your extra information
                textSliderView.bundle(new Bundle());
                textSliderView.getBundle()
                        .putString("extra", name);

                mSlider.addSlider(textSliderView);
            }
            mSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
            //  mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            mSlider.setCustomAnimation(new DescriptionAnimation());
            mSlider.setDuration(4000);
            mSlider.addOnPageChangeListener(ProductDetailsActivity.this);
        }

    void getProductDescribe(int productId) {

        progressDialog.setMessage("Loading..");
        progressDialog.show();
      if (Utilities.isNetworkAvailable(activity))
      {
          ProductDescribeApi productDescribeApi  = RetrofitClientInstance.getRetrofitInstanceServer().
                  create(ProductDescribeApi.class);

        productDescribeApi.getproductDescribe(RetrofitClientInstance.BASE_URL+"products/item/"+productId).
                enqueue(new Callback<ProductDescribeResponse>() {

                    @Override
                    public void onResponse(Call<ProductDescribeResponse> call, Response<ProductDescribeResponse> response) {

                        ProductDescribeResponse brandsResponse = response.body();

                        progressDialog.dismiss();
                       // if (brandsResponse !=null) {
                            Log.e("onResoProductDescribe: ", brandsResponse.toString());
                            if (brandsResponse.status == 200)
                            {
                                tvName.setText(brandsResponse.data.productData.product_name);
                                tvShoartDesc.setText("\t"+brandsResponse.data.productData.short_details);
                                //tvDesc.setText("\t"+brandsResponse.data.description);
                                //tvQty.setText(brandsResponse.data.productPricing.unit_price);
                                tvQty.setText(String.format("%.2f",Double.parseDouble(brandsResponse.data.productPricing.unit_price)));
                                //tvUnitType.setText("/"+brandsResponse.data.vendor_details.get(0).unit_type);
                                //tvRating.setText(brandsResponse.data.vendor_details.get(0).rating);
                               // tvNote.setText(brandsResponse.data.vendor_details.get(0).note);
                                slider(brandsResponse.data.productImages.image_path);
                            }

                            if (brandsResponse.status == 204) {
                                //Toast.makeText(activity, "" + brandsResponse.message, Toast.LENGTH_SHORT).show();
                                emptyDialog();
                            }

                       // }
                    }

                    @Override
                    public void onFailure(Call<ProductDescribeResponse> call, Throwable t) {
                        progressDialog.dismiss();

                        //Utilities.setError(layout1,layout2,txt_error,getResources().getString(R.string.something_went_wrong));
                        Log.d("errorchk", t.getMessage());

                    }
                });
      } else {
          progressDialog.dismiss();
          Toast.makeText(activity, getResources().getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
      }
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_openDrawer:
                    onBackPressed();
                break;

             case R.id.tv_addToCart:
                 wishlistORCart = "cart";
                 Log.e(TAG, "onClick:ss "+user_id+ " "+product_id);
                 addCartnew(user_id,product_id,wishlistORCart,accesstoken);
                    //addCart(user_id,product_id);
                break;

              case R.id.iv_wishlist:
                  wishlistORCart = "wishlist";
                 Log.e(TAG, "onClick:ss "+user_id+ " "+product_id);
                 addCartnew(user_id,product_id,wishlistORCart,accesstoken);
                    //addCart(user_id,product_id);
                break;


        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    //http://z.eurekatalents.in/api/cart/add?user_id=12&product_id=2
    void addCartnew(String user_id, String product_id, String wishlistORCart, String accesstoken) {

        if (Utilities.isNetworkAvailable(activity)){
        CartApi cartApi  = RetrofitClientInstance.getRetrofitInstanceServer().
                create(CartApi.class);
                cartApi.addCart("Bearer "+accesstoken,RetrofitClientInstance.BASE_URL+wishlistORCart+"/add?user_id="+user_id+"&product_id="+product_id).
                enqueue(new Callback<AddCartResponse>() {
                    @Override
                    public void onResponse(Call<AddCartResponse> call, Response<AddCartResponse> response) {
                        Log.e( "chkonResponse: ", response.body().toString());
                        AddCartResponse cartResponse = response.body();

                        if (cartResponse.status ==200)
                        {
                            Toast.makeText(activity,cartResponse.message,Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "onResponse:new "+cartResponse.message );

                            if (wishlistORCart.equals("wishlist")){
                                Toast.makeText(activity,cartResponse.message,Toast.LENGTH_SHORT).show();
                            }
                            else if(wishlistORCart.equals("cart")){
                                Bundle bundle = new Bundle();
                                bundle.putString("wishlistORcart","cart");
                                Utilities.launchActivity(activity, CartActivity.class,false,bundle);
                            }

                        }

                        if (cartResponse.status == 401)
                        {
                            Log.e(TAG, "onResponse: "+cartResponse.message );
                            authenticationDialog(cartResponse.message);
                        }
                    }

                    @Override
                    public void onFailure(Call<AddCartResponse> call, Throwable t) {
                        Log.e("errorchk", t.getMessage());

                    }
                });
        } else {
            Toast.makeText(activity, getResources().getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
        }
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


    private void emptyDialog() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity);
        mBuilder.setCancelable(false);
        mBuilder.setTitle("Oops! Record Not Found");

        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

                dialog.dismiss();
                Utilities.launchActivity(activity, HomeActivity.class,true);

            }
        });

        mBuilder.setNegativeButton("Cancel", null);
        AlertDialog mDialog = mBuilder.create();
        mDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_rounded_background);
        mDialog.show();
    }



}