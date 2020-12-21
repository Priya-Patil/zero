package com.m90.zero.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.m90.zero.MainActivity;
import com.m90.zero.R;
import com.m90.zero.databinding.ActivityHomeBinding;
import com.m90.zero.home.adapter.CategoryAdapter;
import com.m90.zero.home.adapter.HomeLikeAdapter;
import com.m90.zero.home.adapter.TopRatedProductsAdapter;
import com.m90.zero.home.model.CategoryResponse;
import com.m90.zero.home.model.HomeModel;
import com.m90.zero.reg.RegActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity  implements View.OnClickListener,
        BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener {

    public  static  String currency ="₹";
    TopRatedProductsAdapter dailyQuizAdapter;
    ActivityHomeBinding binding;
    private int[] myImageList = new int[]{R.drawable.headpgns, R.drawable.headpgns,R.drawable.headpgns,R.drawable.headpgns,R.drawable.headpgns, R.drawable.headpgns};

    private String[] myImageNameList = new String[]{"Add User","User History", "Event Message","Event History","Payment History", "Contact" };
    private String[] myCategory = new String[]{"User","Home", "Message","History","History", "Contact" };
    private String[] rate = new String[]{"4.9","4.9", "4.9","4.9","4.9", "4.9" };
    private String[] amount = new String[]{"288","288", "288","288","288", "288" };

    ArrayList<HomeModel> imageModelYouTubeArrayList ;
    CategoryAdapter categoryAdapter;
    HomeLikeAdapter homeLikeAdapter;
    ArrayList<CategoryResponse> categoryResponses;
    RelativeLayout rl_login,rl_reg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        /// mDemoSlider = (SliderLayout) findViewById(R.id.slider);

        //binding.slider = (SliderLayout) findViewById(R.id.slider);

        rl_reg=findViewById(R.id.rl_reg);
        rl_login=findViewById(R.id.rl_login);
        rl_reg.setOnClickListener(this);
        rl_login.setOnClickListener(this);
        setCategory();
        setCity();
        setLikes();
        slider();
    }

    void setCity()
    {
        imageModelYouTubeArrayList = arrayDailyQuiz();

        binding.recyclerViewCourses.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        // binding.recyclerViewCourses.setLayoutManager(glm);
        binding.recyclerViewCourses.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerViewCourses.setHasFixedSize(true);

        dailyQuizAdapter = new TopRatedProductsAdapter(this, imageModelYouTubeArrayList,
                new TopRatedProductsAdapter.ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {


                    }
                });
        binding.recyclerViewCourses.setAdapter(dailyQuizAdapter);
    }

    void setLikes()
    {
        imageModelYouTubeArrayList = arrayDailyQuiz();

        //binding.rvLike.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
        //      true));
        GridLayoutManager re = new GridLayoutManager(this,2);
        binding.rvLike.setLayoutManager(re);
        // binding.recyclerViewCourses.setLayoutManager(glm);
        binding.rvLike.setItemAnimator(new DefaultItemAnimator());
        binding.rvLike.setHasFixedSize(true);

        homeLikeAdapter = new HomeLikeAdapter(this, imageModelYouTubeArrayList,
                new HomeLikeAdapter.ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {


                    }
                });
        binding.rvLike.setAdapter(homeLikeAdapter);
    }

    void setCategory()
    {
        Log.e("TAG", "setCategory: " );
        categoryResponses = arrayCategory();

        //LinearLayoutManager glm = new LinearLayoutManager(getApplicationContext());
        binding.rvCategories.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        //binding.recyclerViewCourses.setLayoutManager(glm);
        binding.rvCategories.setItemAnimator(new DefaultItemAnimator());
        binding.rvCategories.setHasFixedSize(true);

        categoryAdapter = new CategoryAdapter(this, categoryResponses,
                new CategoryAdapter.ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {


                    }
                });
        binding.rvCategories.setAdapter(categoryAdapter);
    }

    public void slider() {
        HashMap<String, String> url_maps = new HashMap<String, String>();
        url_maps.put("Hurryup", "http://pmbjk.in/1.jpg");
        url_maps.put("Limited Time Offer", "http://pmbjk.in/2.jpg");
        url_maps.put("Most Selling", "http://pmbjk.in/3.jpg");

        for (String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            binding.slider.addSlider(textSliderView);
        }
        binding.slider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        //  binding.slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        binding.slider.setCustomAnimation(new DescriptionAnimation());
        binding.slider.setDuration(4000);
        binding.slider.addOnPageChangeListener(this);


    }


    private ArrayList<HomeModel> arrayDailyQuiz(){
        ArrayList<HomeModel> list = new ArrayList<>();
        for(int i = 0; i < 6; i++){
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

    private ArrayList<CategoryResponse> arrayCategory(){
        ArrayList<CategoryResponse> list = new ArrayList<>();
        for(int i = 0; i < 6; i++){
            CategoryResponse homeModel = new CategoryResponse();
            homeModel.setCategory(myCategory[i]);
            list.add(homeModel);
        }
        return list;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.rl_cart:
                binding.drlOpener.openDrawer(Gravity.LEFT); //Edit Gravity.START need API 14
                break;

            case R.id.rl_category:
                binding.drlOpener.openDrawer(Gravity.LEFT); //Edit Gravity.START need API 14
                break;

            case R.id.rl_home:
                binding.drlOpener.openDrawer(Gravity.LEFT); //Edit Gravity.START need API 14
                break;

            case R.id.rl_Orders:
                binding.drlOpener.openDrawer(Gravity.LEFT); //Edit Gravity.START need API 14
                break;

            case R.id.rl_profile:
                binding.drlOpener.openDrawer(Gravity.LEFT); //Edit Gravity.START need API 14
                break;

            case R.id.rl_wishlist:
                binding.drlOpener.openDrawer(Gravity.LEFT); //Edit Gravity.START need API 14
                break;

            case R.id.rl_login:
                binding.drlOpener.openDrawer(Gravity.LEFT); //Edit Gravity.START need API 14
                Intent i = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(i);
                break;

            case R.id.rl_reg:
                binding.drlOpener.openDrawer(Gravity.LEFT); //Edit Gravity.START need API 14
                Intent ii = new Intent(HomeActivity.this, RegActivity.class);
                startActivity(ii);
                break;
/*
              case R.id.rl_profile:
                binding.drlOpener.openDrawer(Gravity.LEFT); //Edit Gravity.START need API 14
            break;*/

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
}


  /*  @Override
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

    }*/
//}