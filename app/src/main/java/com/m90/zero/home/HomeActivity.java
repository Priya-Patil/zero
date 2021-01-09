package com.m90.zero.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.m90.zero.home.adapter.BrandsAdapter;
import com.m90.zero.home.adapter.ProductGroupsAdapter;
import com.m90.zero.home.allcategory.AllCategoryActivity;
import com.m90.zero.home.api.BrandsApi;
import com.m90.zero.home.api.ProductGroupsApi;
import com.m90.zero.home.api.SliderApi;
import com.m90.zero.home.model.BrandResponse;
import com.m90.zero.home.model.BrandsDetailsResponse;
import com.m90.zero.home.model.ProductGroupsDetailsResponse;
import com.m90.zero.home.model.ProductGroupsResponse;
import com.m90.zero.home.model.SliderDetails;
import com.m90.zero.home.model.SliderResponse;
import com.m90.zero.login.LoginActivity;
import com.m90.zero.R;
import com.m90.zero.faq.FaqActivity;
import com.m90.zero.home.adapter.CategoryAdapter;
import com.m90.zero.home.adapter.HomeLikeAdapter;
import com.m90.zero.home.adapter.ProductDetailssAdapter;
import com.m90.zero.home.api.CategoryApi;
import com.m90.zero.home.model.CategoryDetailsResponse;
import com.m90.zero.home.model.CategoryResponse;
import com.m90.zero.home.model.HomeModel;
import com.m90.zero.profile.ProfileActivity;
import com.m90.zero.reg.RegActivity;
import com.m90.zero.retrofit.RetrofitClientInstance;
import com.m90.zero.splash.SplashActivity;
import com.m90.zero.tac.TACActivity;
import com.m90.zero.utils.SessionHelper;
import com.m90.zero.utils.Utilities;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener,
        BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener {

    Activity activity;
    public static String currency = "₹";
    ProductDetailssAdapter dailyQuizAdapter;
    //ActivityMainBinding binding;
    private int[] myImageList = new int[]{R.drawable.headpgns, R.drawable.headpgns, R.drawable.headpgns, R.drawable.headpgns, R.drawable.headpgns, R.drawable.headpgns};

    private String[] myImageNameList = new String[]{"Add User", "User History", "Event Message", "Event History", "Payment History", "Contact"};
    private String[] myCategory = new String[]{"User", "Home", "Message", "History", "History", "Contact"};
    private String[] rate = new String[]{"4.9", "4.9", "4.9", "4.9", "4.9", "4.9"};
    private String[] amount = new String[]{"288", "288", "288", "288", "288", "288"};

    ArrayList<HomeModel> imageModelYouTubeArrayList;
    CategoryAdapter categoryAdapter;
    ProductGroupsAdapter productGroupsAdapter;
    BrandsAdapter brandsAdapter;
    HomeLikeAdapter homeLikeAdapter;
    ArrayList<CategoryDetailsResponse> categoryResponses;
    ArrayList<ProductGroupsDetailsResponse> productGroupsResponses;
    ArrayList<BrandResponse> brandResponses;
    ArrayList<BrandsDetailsResponse> brandResponses1;
    private ImageView mIvCart;
    private SliderLayout mSlider;
    private RecyclerView mRvCategories;
    private RecyclerView rv_brands;
    private TextView mTvCategory;
    private TextView mTvMore;
    private RecyclerView mRecyclerViewCourses;
    private TextView mTvCategory2;
    private TextView mTvMore1;
    private RecyclerView mRvLike;
    private CardView mImgProfileMale;
    private TextView mTvProfileName;
    private TextView mTvMobile;
    private RelativeLayout mRlProfile;
    private RelativeLayout mRlHome;
    private RelativeLayout mRlCategory;
    private TextView mTvCart;
    private RelativeLayout mRlCart;
    private RelativeLayout mRlWishlist;
    private RelativeLayout mRlOrders;
    private RelativeLayout mrl_notifications;
    public static RelativeLayout mRlLogin;
    private ImageView mIvReg;
    private TextView mTvReg;
    public static RelativeLayout mRlReg;
    private TextView mTvContactus;
    private TextView mTvTandc;
    private TextView mTvHelpcenter;
    public static TextView mTvExit;
    private TextView mTvAbtUs;
    private ScrollView mScrllDrawer;
    private DrawerLayout mDrlOpener;
    ImageView btn_openDrawer;
    SessionHelper sessionHelper;
    public static RelativeLayout mRlProfileClick;
    ProgressDialog progressDialog;

    CircleImageView civ_profile_nav;
    TextView tv_profile_name_nav;
    TextView tv_mobile_nav;
    TextView all_category;


    List<SliderDetails> sliderDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);

        //binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activity = HomeActivity.this;
        InitView();
        //mRecyclerViewCourses.setNestedScrollingEnabled(false);
        getCategory();
        //setCity();
        getBrands();
        //setLikes();
        slider();
        getProductGroups();


        mRlProfile.setVisibility(View.VISIBLE);

        if(sessionHelper.isLoggedIn())
        {//loginned
            mRlOrders.setVisibility(View.VISIBLE);
            mRlProfileClick.setVisibility(View.VISIBLE);
            mTvExit.setVisibility(View.VISIBLE);
           //http://api.eurekatalents.in/users/default.png
            tv_mobile_nav.setText(Utilities.getSavedUserData(activity,"mobileNumber"));
            tv_profile_name_nav.setText(Utilities.getSavedUserData(activity,"name"));
            //tv_mobile_nav.setText("http://api.eurekatalents.in/"+Utilities.getSavedUserData(activity,"profilePic"));
            Picasso.with(activity).load("http://api.eurekatalents.in/"+
                    Utilities.getSavedUserData(activity,"profilePic")).into(civ_profile_nav);

            Log.e("TAG", "onCreate: "+Utilities.getSavedUserData(activity,"profilePic") );

        }
        else {
            //reg
            HomeActivity.mRlReg.setVisibility(View.VISIBLE);
            HomeActivity.mRlLogin.setVisibility(View.VISIBLE);
            mRlOrders.setVisibility(View.GONE);

        }
    }

    private void InitView() {

        sessionHelper = new SessionHelper(activity);
        progressDialog = new ProgressDialog(activity);
        btn_openDrawer = findViewById(R.id.btn_openDrawer);
        civ_profile_nav = findViewById(R.id.civ_profile_nav);
        all_category = findViewById(R.id.all_category);
        tv_profile_name_nav = findViewById(R.id.tv_profile_name_nav);
        tv_mobile_nav = findViewById(R.id.tv_mobile_nav);
        mrl_notifications = findViewById(R.id.rl_notifications);
        mRlProfileClick = findViewById(R.id.rl_profile_click);
        mIvCart = findViewById(R.id.iv_cart);
        mSlider = findViewById(R.id.slider);
        mRvCategories = findViewById(R.id.rv_categories);
        rv_brands = findViewById(R.id.rv_brands);
        mTvCategory = findViewById(R.id.tv_category);
        mTvMore = findViewById(R.id.tv_more);
        mRecyclerViewCourses = findViewById(R.id.recyclerViewCourses);
        mTvCategory2 = findViewById(R.id.tv_category2);
        mTvMore1 = findViewById(R.id.tv_more1);
        mRvLike = findViewById(R.id.rv_like);

        mTvMobile = findViewById(R.id.tv_mobile);

        mRlHome = findViewById(R.id.rl_home);
        mRlProfile = findViewById(R.id.rl_profile);
        mRlCategory = findViewById(R.id.rl_category);
        mTvCart = findViewById(R.id.tv_cart);
        mRlCart = findViewById(R.id.rl_cart);
        mRlWishlist = findViewById(R.id.rl_wishlist);
        mRlOrders = findViewById(R.id.rl_Orders);
        mRlLogin = findViewById(R.id.rl_login);
        mIvReg = findViewById(R.id.iv_reg);
        mTvReg = findViewById(R.id.tv_reg);
        mRlReg = findViewById(R.id.rl_reg);
        mTvContactus = findViewById(R.id.tv_contactus);
        mTvTandc = findViewById(R.id.tv_tandc);
        mTvHelpcenter = findViewById(R.id.tv_helpcenter);
        mTvExit = findViewById(R.id.tv_exit);
        mTvAbtUs = findViewById(R.id.tv_abtUs);
        mScrllDrawer = findViewById(R.id.Scrll_Drawer);
        mDrlOpener = findViewById(R.id.drl_Opener);

        btn_openDrawer.setOnClickListener(this);
        mRlHome.setOnClickListener(this);
        mTvTandc.setOnClickListener(this);
        mTvExit.setOnClickListener(this);
        mTvHelpcenter.setOnClickListener(this);
        mRlLogin.setOnClickListener(this);
        mRlReg.setOnClickListener(this);
        mRlProfileClick.setOnClickListener(this);
        mRlCategory.setOnClickListener(this);
        mRlCart.setOnClickListener(this);
        mRlWishlist.setOnClickListener(this);
        mRlOrders.setOnClickListener(this);
        mTvAbtUs.setOnClickListener(this);
        all_category.setOnClickListener(this);
    }



    void setLikes() {
        imageModelYouTubeArrayList = arrayDailyQuiz();

        //binding.rvLike.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
        //      true));
        GridLayoutManager re = new GridLayoutManager(this, 2);
        mRvLike.setLayoutManager(re);
        // mRecyclerViewCourses.setLayoutManager(glm);
        mRvLike.setItemAnimator(new DefaultItemAnimator());
        mRvLike.setHasFixedSize(true);

        homeLikeAdapter = new HomeLikeAdapter(this, imageModelYouTubeArrayList,
                new HomeLikeAdapter.ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {


                    }
                });
        mRvLike.setAdapter(homeLikeAdapter);
    }

    public void slider() {

        SliderApi sliderApi = RetrofitClientInstance.getRetrofitInstanceServer().
                create(SliderApi.class);

        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        sliderApi.getSlider().enqueue(new Callback<SliderResponse>() {

            @Override
            public void onResponse(Call<SliderResponse> call, Response<SliderResponse> response) {

                SliderResponse sliderResponse = response.body();
                sliderDetails = (sliderResponse).data;
                Log.e("TAG", "onResponse: "+sliderResponse.toString()+" "+sliderDetails.toString() );

                if (sliderResponse.status == 200) {
                    Toast.makeText(activity, "" + sliderResponse.message, Toast.LENGTH_SHORT).show();

                    for (SliderDetails name : sliderDetails) {
                        TextSliderView textSliderView = new TextSliderView(activity);
                        // initialize a SliderLayout
                        textSliderView
                                .description(name.sectionName)
                                .image("http://api.eurekatalents.in/"+name.sliderImage)
                                .setScaleType(BaseSliderView.ScaleType.Fit)
                                .setOnSliderClickListener(HomeActivity.this::onSliderClick);

                        //add your extra information
                        textSliderView.bundle(new Bundle());
                        textSliderView.getBundle()
                                .putString("extra", name.sliderImage);

                        mSlider.addSlider(textSliderView);
                    }
                    mSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
                    //  mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                    mSlider.setCustomAnimation(new DescriptionAnimation());
                    mSlider.setDuration(4000);
                    mSlider.addOnPageChangeListener(HomeActivity.this);

                    Log.e("onResponse: ", " " + sliderResponse.message);
                } else {
                    Toast.makeText(activity, "" + sliderResponse.message, Toast.LENGTH_SHORT).show();
                    Log.e("onResponse: ", " " + sliderResponse.message);
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<SliderResponse> call, Throwable t) {
                progressDialog.dismiss();
                //Utilities.setError(layout1,layout2,txt_error,getResources().getString(R.string.something_went_wrong));
                Log.e("Slider errorchk", t.getMessage());
            }
        });

    }

    void setCategory( ArrayList<CategoryDetailsResponse> categoryResponsesnew)
    {
        Log.e("TAG", "setCategory: " );
        categoryResponses = categoryResponsesnew;

        //LinearLayoutManager glm = new LinearLayoutManager(getApplicationContext());
        mRvCategories.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false));
        //binding.recyclerViewCourses.setLayoutManager(glm);
        mRvCategories.setItemAnimator(new DefaultItemAnimator());
        mRvCategories.setHasFixedSize(true);

        categoryAdapter = new CategoryAdapter(this, categoryResponses,
                new CategoryAdapter.ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {


                    }
                });
        mRvCategories.setAdapter(categoryAdapter);
    }


    private ArrayList<HomeModel> arrayDailyQuiz() {
        ArrayList<HomeModel> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            HomeModel homeModel = new HomeModel();
            homeModel.setName(myImageNameList[i]);
            homeModel.setImage_drawable(myImageList[i]);
            homeModel.setDesc(myCategory[i]);
            homeModel.setRate(rate[i]);
            homeModel.setAmount(amount[i]);
            list.add(homeModel);
        }
        return list;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.rl_cart:
                mDrlOpener.closeDrawer(Gravity.LEFT); //Edit Gravity.START need API 14

                break;

            case R.id.btn_openDrawer:
                mDrlOpener.openDrawer(Gravity.LEFT); //Edit Gravity.START need API 14
                break;

            case R.id.rl_category:
                mDrlOpener.closeDrawer(Gravity.LEFT); //Edit Gravity.START need API 14
                Utilities.launchActivity(activity, AllCategoryActivity.class,false);
                break;

            case R.id.rl_home:
                mDrlOpener.closeDrawer(Gravity.LEFT); //Edit Gravity.START need API 14
                break;

            case R.id.rl_Orders:
                mDrlOpener.closeDrawer(Gravity.LEFT); //Edit Gravity.START need API 14
                break;

            case R.id.rl_profile_click:
                mDrlOpener.closeDrawer(Gravity.LEFT); //Edit Gravity.START need API 14
                Utilities.launchActivity(activity, ProfileActivity.class,false);
                break;

            case R.id.rl_login:
                mDrlOpener.closeDrawer(Gravity.LEFT); //Edit Gravity.START need API 14
                Intent i = new Intent(activity, LoginActivity.class);
                startActivity(i);
                break;

            case R.id.rl_reg:
                mDrlOpener.closeDrawer(Gravity.LEFT);
                Utilities.launchActivity(activity, RegActivity.class,true);
                break;

             case R.id.all_category:
                mDrlOpener.closeDrawer(Gravity.LEFT);
                Utilities.launchActivity(activity, AllCategoryActivity.class,false);
                break;

            case R.id.rl_wishlist:
                mDrlOpener.closeDrawer(Gravity.LEFT); //Edit Gravity.START need API 14
                break;

            case R.id.rl_notifications:
                mDrlOpener.closeDrawer(Gravity.LEFT); //Edit Gravity.START need API 14
                break;

            case R.id.tv_helpcenter:
                mDrlOpener.closeDrawer(Gravity.LEFT); //Edit Gravity.START need API 14
                Utilities.launchActivity(activity, FaqActivity.class, false);
                break;

            case R.id.tv_tandc:
                mDrlOpener.closeDrawer(Gravity.LEFT); //Edit Gravity.START need API 14
                Utilities.launchActivity(activity, TACActivity.class, false);
                break;

                case R.id.tv_abtUs:
                mDrlOpener.closeDrawer(Gravity.LEFT); //Edit Gravity.START need API 14
                Utilities.launchActivity(activity, TACActivity.class, false);
                break;

            case R.id.tv_exit:
                mDrlOpener.closeDrawer(Gravity.LEFT); //Edit Gravity.START need API 14
                logoutDialog(v);
                break;



/*
              case R.id.rl_profile:
                mDrlOpener.openDrawer(Gravity.LEFT); //Edit Gravity.START need API 14
            break;*/

        }
    }

    private void logoutDialog(View v) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity);
        mBuilder.setTitle("Are you sure you want to exit?");
       /* final View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.logout_dialog, null);
        mBuilder.setView(dialogView);

        Button btn_Cancel = dialogView.findViewById(R.id.btn_Cancel);
        Button btn_ok = dialogView.findViewById(R.id.btn_ok);

        mBuilder.setCancelable(false);

        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //dialogView.dismiss();
            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionHelper.setLogin(false);
            }
        });*/
        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                sessionHelper.setLogin(false);
                             dialog.dismiss();
                             Utilities.launchActivity(activity, SplashActivity.class,true);

            }
        });

        mBuilder.setNegativeButton("Cancel", null);
        AlertDialog mDialog = mBuilder.create();
        mDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_rounded_background);
        mDialog.show();
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

    void getCategory() {

        CategoryApi categoryApi  = RetrofitClientInstance.getRetrofitInstanceServer().
                create(CategoryApi.class);

        categoryApi.getCategory().
                enqueue(new Callback<CategoryResponse>() {

                    @Override
                    public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {

                        CategoryResponse categoryResponse = response.body();

                        if (categoryResponse.status == 200) {
                            Log.e( "onResponse: ", categoryResponse.data.toString());
                            setCategory(categoryResponse.data);
                            //  Toast.makeText(activity, "" + aboutResponce.message, Toast.LENGTH_SHORT).show();
                            // Log.e("aboutResponce: ", " " + aboutResponce.message);


                        } else {
                            Toast.makeText(activity, "" + categoryResponse.message, Toast.LENGTH_SHORT).show();
                            Log.e("aboutResponce: ", " " + categoryResponse.message);

                        }


                    }

                    @Override
                    public void onFailure(Call<CategoryResponse> call, Throwable t) {

                        //Utilities.setError(layout1,layout2,txt_error,getResources().getString(R.string.something_went_wrong));
                        Log.d("errorchk", t.getMessage());

                    }
                });


    }

    void getBrands() {

        BrandsApi brandsApi  = RetrofitClientInstance.getRetrofitInstanceServer().
                create(BrandsApi.class);

        brandsApi.getBrands().
                enqueue(new Callback<BrandResponse>() {

                    @Override
                    public void onResponse(Call<BrandResponse> call, Response<BrandResponse> response) {

                        BrandResponse brandResponse = response.body();
                        if (brandResponse !=null) {
                            if (brandResponse.status == 200) {
                                Log.e("onResponse: ", brandResponse.data.toString());
                                setBrands(brandResponse.data);

                            } else {
                                Toast.makeText(activity, "" + brandResponse.message, Toast.LENGTH_SHORT).show();
                                Log.e("aboutResponce: ", " " + brandResponse.message);

                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<BrandResponse> call, Throwable t) {
                        Log.d("errorchkBr", t.getMessage());
                    }

                });


    }

    private void setBrands(ArrayList<BrandsDetailsResponse> data) {

            Log.e("TAG", "setBrands: " );
            brandResponses1 = data;

            //LinearLayoutManager glm = new LinearLayoutManager(getApplicationContext());
            rv_brands.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL
                    , false));
            //binding.recyclerViewCourses.setLayoutManager(glm);
            rv_brands.setItemAnimator(new DefaultItemAnimator());
            rv_brands.setHasFixedSize(true);

            brandsAdapter = new BrandsAdapter(this, brandResponses1,
                    new BrandsAdapter.ItemClickListener() {
                        @Override
                        public void onClick(View view, int position) {


                        }
                    });
        rv_brands.setAdapter(brandsAdapter);

    }

    void getProductGroups() {

        ProductGroupsApi productGroupsApi  = RetrofitClientInstance.getRetrofitInstanceServer().
                create(ProductGroupsApi.class);

        productGroupsApi.getproductGroups().
                enqueue(new Callback<ProductGroupsResponse>() {

                    @Override
                    public void onResponse(Call<ProductGroupsResponse> call, Response<ProductGroupsResponse> response) {

                        ProductGroupsResponse productGroupsResponse = response.body();
                        if (productGroupsResponse !=null) {
                            if (productGroupsResponse.status == 200) {
                                Log.e("onResponse: ", productGroupsResponse.data.toString());
                                setProductGroups(productGroupsResponse.data);
                                //  Toast.makeText(activity, "" + aboutResponce.message, Toast.LENGTH_SHORT).show();
                                Log.e("aboutResponce: ", " " + productGroupsResponse.toString());


                            } else {
                                Toast.makeText(activity, "" + productGroupsResponse.message, Toast.LENGTH_SHORT).show();
                                Log.e("aboutResponce: ", " " + productGroupsResponse.message);

                            }
                        }


                    }

                    @Override
                    public void onFailure(Call<ProductGroupsResponse> call, Throwable t) {

                        //Utilities.setError(layout1,layout2,txt_error,getResources().getString(R.string.something_went_wrong));
                        Log.d("errorchk", t.getMessage());

                    }
                });
    }

    void setProductGroups(ArrayList<ProductGroupsDetailsResponse> productGroups)
    {
        Log.e("TAG", "setProductGroups: " );
        productGroupsResponses = productGroups;

        //LinearLayoutManager glm = new LinearLayoutManager(getApplicationContext());
        mRecyclerViewCourses.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false));
        ViewGroup.LayoutParams params=mRecyclerViewCourses.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        mRecyclerViewCourses.setLayoutParams(params);

        //binding.recyclerViewCourses.setLayoutManager(glm);
        mRecyclerViewCourses.setItemAnimator(new DefaultItemAnimator());
        //mRecyclerViewCourses.setHasFixedSize(true);

        productGroupsAdapter = new ProductGroupsAdapter(this, productGroupsResponses,
                new ProductGroupsAdapter.ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {


                    }
                });
        mRecyclerViewCourses.setAdapter(productGroupsAdapter);
    }


}
/*list.remove(position);
recycler.removeViewAt(position);
mAdapter.notifyItemRemoved(position);
mAdapter.notifyItemRangeChanged(position, list.size());
*/

