package com.m90.zero.home.allcategory.api;


import com.m90.zero.home.allcategory.model.CategoryAllResponse;
import com.m90.zero.home.allcategory.model.ProductSubCategoryResponse;
import com.m90.zero.home.brands.model.BrandsDetailListResponce;
import com.m90.zero.home.brands.model.BrandsListResponse;
import com.m90.zero.home.model.CategoryResponse;
import com.m90.zero.profile.model.WalletResponse;

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
        Call<BrandsListResponse> getShowNow (
                @Url String url
        );


}
