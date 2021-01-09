package com.m90.zero.productdetail;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.m90.zero.R;
import com.m90.zero.home.HomeActivity;
import com.m90.zero.home.api.SliderApi;
import com.m90.zero.home.brands.api.BrandsApi;
import com.m90.zero.home.brands.model.BrandsListResponse;
import com.m90.zero.home.model.SliderDetails;
import com.m90.zero.home.model.SliderResponse;
import com.m90.zero.productdetail.api.ProductDescribeApi;
import com.m90.zero.productdetail.model.ProductDescribeResponse;
import com.m90.zero.retrofit.RetrofitClientInstance;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener, View.OnClickListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_product_details);
        activity = ProductDetailsActivity.this;
        progressDialog =  new ProgressDialog(activity);

        initView();
        getProductDescribe(100);
        btnOpenDrawer.setOnClickListener(this);
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
                        .image("http://api.eurekatalents.in/"+name)
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

        Log.e("TAG", "getProductDescribe: " );

        ProductDescribeApi productDescribeApi  = RetrofitClientInstance.getRetrofitInstanceServer().
                create(ProductDescribeApi.class);

        productDescribeApi.getproductDescribe("http://api.eurekatalents.in/api/products/item/"+productId).
                enqueue(new Callback<ProductDescribeResponse>() {

                    @Override
                    public void onResponse(Call<ProductDescribeResponse> call, Response<ProductDescribeResponse> response) {
                        Log.e("TAG", "getProductDescribe: ssssssssssssss" );

                        ProductDescribeResponse brandsResponse = response.body();
                        Log.e("onResoProductDescribe: ", brandsResponse.toString());
                        if (brandsResponse !=null) {
                            if (brandsResponse.status == 200) {
                                Log.e("onResoProductDescribe: ", brandsResponse.toString());

                                tvName.setText(brandsResponse.data.product_name);
                                tvShoartDesc.setText("\t"+brandsResponse.data.short_details);
                                tvDesc.setText("\t"+brandsResponse.data.description);
                                tvQty.setText(brandsResponse.data.vendor_details.get(0).quantity_per_unit);
                                tvUnitType.setText("/"+brandsResponse.data.vendor_details.get(0).unit_type);
                                tvRating.setText(brandsResponse.data.vendor_details.get(0).rating);
                                tvNote.setText(brandsResponse.data.vendor_details.get(0).note);
                                slider(brandsResponse.data.vendor_details.get(0).pictures);

                                //  Toast.makeText(activity, "" + aboutResponce.message, Toast.LENGTH_SHORT).show();


                            } else {
                                Toast.makeText(activity, "" + brandsResponse.message, Toast.LENGTH_SHORT).show();
                                Log.e("onResoProductDescribe: ", " " + brandsResponse.message);

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ProductDescribeResponse> call, Throwable t) {

                        //Utilities.setError(layout1,layout2,txt_error,getResources().getString(R.string.something_went_wrong));
                        Log.d("errorchk", t.getMessage());

                    }
                });
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
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}