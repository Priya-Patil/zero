package com.m90.zero.faq.api;


import com.m90.zero.faq.model.FaqResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FaqApi {


    @GET("faq")
    Call<FaqResponse> getFaq();

}
