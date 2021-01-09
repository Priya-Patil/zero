package com.m90.zero.order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.m90.zero.R;
import com.m90.zero.databinding.ActivityOrderBinding;
import com.m90.zero.order.adapter.OrdersAdapter;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    ArrayList<OrderResponse> imageModelYouTubeArrayList;
    OrdersAdapter ordersAdapter;
    ActivityOrderBinding binding;

    private int[] myImageList = new int[]{R.drawable.headpgns, R.drawable.headpgns, R.drawable.headpgns, R.drawable.headpgns, R.drawable.headpgns, R.drawable.headpgns};

    private String[] myImageNameList = new String[]{"Add User", "User History", "Event Message", "Event History", "Payment History", "Contact"};
    private String[] myCategory = new String[]{"User", "Home", "Message", "History", "History", "Contact"};
    private String[] rate = new String[]{"4.9", "4.9", "4.9", "4.9", "4.9", "4.9"};
    private String[] amount = new String[]{"288", "288", "288", "288", "288", "288"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order);
    }

    void setCity() {
        imageModelYouTubeArrayList = arrayDailyQuiz();

        binding.rvOrder.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        // mRecyclerViewCourses.setLayoutManager(glm);
        binding.rvOrder.setItemAnimator(new DefaultItemAnimator());
        binding.rvOrder.setHasFixedSize(true);

        ordersAdapter = new OrdersAdapter(this, imageModelYouTubeArrayList,
                new OrdersAdapter.ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {


                    }
                });
        binding.rvOrder.setAdapter(ordersAdapter);
    }

    private ArrayList<OrderResponse> arrayDailyQuiz() {
        ArrayList<OrderResponse> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            OrderResponse homeModel = new OrderResponse();
            homeModel.setTitle(myImageNameList[i]);
            homeModel.setImg(myImageList[i]);
            homeModel.setStatus(myCategory[i]);
            homeModel.setDeliverytime(rate[i]);
            homeModel.setAmount(amount[i]);
            list.add(homeModel);
        }
        return list;
    }


}