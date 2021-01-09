package com.m90.zero.productdetail.api;


import com.m90.zero.home.brands.model.BrandsListResponse;
import com.m90.zero.productdetail.model.ProductDescribeResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ProductDescribeApi {


    @GET()
    Call<ProductDescribeResponse> getproductDescribe (
            @Url String url
    );


}
