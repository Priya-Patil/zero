package com.m90.zero.home.api;


import com.m90.zero.home.allcategory.model.CategoryAllResponse;
import com.m90.zero.home.model.CategoryResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryApi {


    //http://api.eurekatalents.in/api/category/nav
    @GET("category/nav")
    Call<CategoryResponse> getCategory (
        );

     @GET("category/base")
    Call<CategoryAllResponse> getAllCategory (
        );


}
