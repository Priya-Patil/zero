package com.m90.zero.home.api;


import com.m90.zero.home.model.BrandResponse;
import com.m90.zero.home.model.ProductGroupsResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductGroupsApi {


    @GET("products/group/")
    Call<ProductGroupsResponse> getproductGroups (
        );
}
