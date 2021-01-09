package com.m90.zero.home.viewmore.api;


import com.m90.zero.home.allcategory.model.CategoryAllResponse;
import com.m90.zero.home.allcategory.model.ProductSubCategoryResponse;
import com.m90.zero.home.viewmore.model.ViewMoreResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ViewMoreApi {


    @GET()
    Call<ViewMoreResponse> getViewMore (
            @Url String url
    );


}
