package com.m90.zero.home.api;


import com.m90.zero.home.model.BrandResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BrandsApi {


    //http://api.eurekatalents.in/api/category/nav
    @GET("brands")
    Call<BrandResponse> getBrands (
        );
}
