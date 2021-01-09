package com.m90.zero.home.allcategory.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.m90.zero.R;
import com.m90.zero.home.allcategory.ProductSubCategoryActivity;
import com.m90.zero.home.allcategory.ShopNowActivity;
import com.m90.zero.home.allcategory.api.AllCategoryApi;
import com.m90.zero.home.allcategory.model.ProductSubCategoryDetailsResponse;
import com.m90.zero.home.allcategory.model.ProductSubCategoryResponse;
import com.m90.zero.home.brands.adapter.BrandsListAdapter;
import com.m90.zero.home.brands.model.BrandsDetailListResponce;
import com.m90.zero.home.brands.model.BrandsListResponse;
import com.m90.zero.retrofit.RetrofitClientInstance;
import com.m90.zero.utils.Utilities;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductSubCategoryAdapter extends RecyclerView.Adapter<ProductSubCategoryAdapter.MyViewHolder> {

    private static final String TAG = "AllCategoryAdapter";

    private Activity mContext;
    ArrayList<ProductSubCategoryDetailsResponse> list;
    private  ItemClickListener itemClickListener;
    ProgressDialog progressDialog;
    ProductSubCategoryAdapter productSubCategoryAdapter;

    public ProductSubCategoryAdapter(Activity context, ArrayList<ProductSubCategoryDetailsResponse> list) {
        this.list = list;
        mContext = context;
    }

    public ProductSubCategoryAdapter(Activity context, ArrayList<ProductSubCategoryDetailsResponse> list, ItemClickListener itemClickListener){
        mContext = context;
        this.list = list;
        this.itemClickListener=itemClickListener;

       /* for (int i = 0; i < 1; i++)
        {
            Log.e(TAG, "ProductSubCategoryAdapter: "+list.get(0).name);
        }*/
    }

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    @Override

    public  MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_allcategory_grid, null);

        progressDialog = new ProgressDialog(mContext);
        //  prefManager=new PrefManager(mContext);
        return new  MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, final int i) {
        final ProductSubCategoryDetailsResponse item = list.get(i);

        Log.e("vlitCae", list.toString());
        viewHolder.tv_title.setText(item.name);
        viewHolder.iv_explore.setVisibility(View.VISIBLE);

        viewHolder.iv_explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPopup(view,item);
            }
        });
        //viewHolder.img.setBackground(mContext.getResources().getDrawable(item.getImage_drawable()));
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView tv, tv_title;
        ImageView img,iv_explore;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            iv_explore = itemView.findViewById(R.id.iv_explore);

            itemView.setOnClickListener(this); // bind the listener
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null) itemClickListener.onClick(view, getAdapterPosition());
        }
    }

    //region Search Filter (setFilter Code)
    public void setFilter(ArrayList<ProductSubCategoryDetailsResponse> newList) {
        list = new ArrayList<>();
        list.addAll(newList);
        notifyDataSetChanged();
    }


    private void openPopup(View view, ProductSubCategoryDetailsResponse item1) {
        //Creating the instance of PopupMenu
        PopupMenu popup = new PopupMenu(mContext,view);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.poupup_menu, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                int id = item.getItemId();
                switch (id)
                {
                    case R.id.explore:
                        Log.e(TAG," idssss "+item1.id);
                        ProductSubCategoryActivity.binding.tvHierarchy.setText(item1.name);
                        getProductSubCategory(Integer.parseInt(item1.id));
                        return true;
                    case R.id.shopnow:
                        Bundle bundle =  new Bundle();
                        bundle.putSerializable("ProductSubCategoryDetailsResponse",item1);
                        Utilities.launchActivity(mContext, ShopNowActivity.class,false,bundle);
                        return true;
                    default:
                        //Toast.makeText(mContext,"default",Toast.LENGTH_LONG).show();
                        return true;

                }
                // return true;
            }
        });

        popup.show();//showing popup menu

    }

    void getProductSubCategory(int user_id) {

        AllCategoryApi allCategoryApi = RetrofitClientInstance.getRetrofitInstanceServer().
                create(AllCategoryApi.class);

        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        allCategoryApi.getProductSubCategory("http://api.eurekatalents.in/api/category/parent/"+user_id).
                enqueue(new Callback<ProductSubCategoryResponse>() {
                    @Override
                    public void onResponse(Call<ProductSubCategoryResponse> call, Response<ProductSubCategoryResponse> response) {
                        ProductSubCategoryResponse productSubCategoryResponse = response.body();
                        list = (productSubCategoryResponse).data;

                        Log.e("TAG", "onResponse: "+productSubCategoryResponse.toString());

                        if (productSubCategoryResponse.status == 200) {
                            Toast.makeText(mContext, "" + productSubCategoryResponse.message, Toast.LENGTH_SHORT).show();
                            attachResponse(productSubCategoryResponse.data);


                        } else {
                            Toast.makeText(mContext, "" + productSubCategoryResponse.message, Toast.LENGTH_SHORT).show();
                            Log.e("onResponse: ", " " + productSubCategoryResponse.message);
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<ProductSubCategoryResponse> call, Throwable t) {

                        progressDialog.dismiss();
                        //Utilities.setError(layout1,layout2,txt_error,getResources().getString(R.string.something_went_wrong));
                        Log.e("errorchk", t.getMessage());

                    }
                });

    }

    private void attachResponse(ArrayList<ProductSubCategoryDetailsResponse> data) {

        ProductSubCategoryActivity.binding.rvCategories.setLayoutManager(new GridLayoutManager(mContext,2));
        // mRecyclerViewCourses.setLayoutManager(glm);
        ProductSubCategoryActivity.binding.rvCategories.setItemAnimator(new DefaultItemAnimator());
        ProductSubCategoryActivity.binding.rvCategories.setHasFixedSize(true);

        productSubCategoryAdapter = new ProductSubCategoryAdapter(mContext, data,
                new ProductSubCategoryAdapter.ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {

                        //Toast.makeText(activity,""+position,Toast.LENGTH_SHORT).show();

                        Log.e(TAG," idssss "+data.get(position).id);
                        ProductSubCategoryActivity.binding.tvHierarchy.setText(data.get(position).name);
                        getProductSubCategory(Integer.parseInt(data.get(position).id));
                    }
                });

        ProductSubCategoryActivity.binding.rvCategories.setAdapter(productSubCategoryAdapter);
        productSubCategoryAdapter.notifyDataSetChanged();

    }



}

