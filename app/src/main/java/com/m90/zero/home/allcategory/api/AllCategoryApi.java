package com.m90.zero.home.allcategory.api;


import com.m90.zero.categorynew.NewCategoryAllResponse;
import com.m90.zero.categorynew.subcategory.NewSubCategoryAllResponse;
import com.m90.zero.home.allcategory.model.CategoryAllResponse;
import com.m90.zero.home.allcategory.model.ProductListResponse;
import com.m90.zero.home.allcategory.model.ProductSubCategoryResponse;
import com.m90.zero.home.brands.model.BrandsListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface AllCategoryApi {


     @GET("category/base")
    Call<CategoryAllResponse> getAllCategory (
        );

    @GET()
    Call<ProductSubCategoryResponse> getProductSubCategory (
            @Url String url
    );

    //BrandsListResponse Reuse here
     @GET()
        Call<ProductListResponse> getShopNow (
                @Url String url
        );



    @GET("category")
    Call<NewCategoryAllResponse> getNewAllCategory (
    );


    @GET()
    Call<NewCategoryAllResponse> getNewAllCategoryForPagination (
            @Url String url
    );

    @GET()
    Call<NewSubCategoryAllResponse> getNewProductSubCategory (
            @Url String url
    );
}
