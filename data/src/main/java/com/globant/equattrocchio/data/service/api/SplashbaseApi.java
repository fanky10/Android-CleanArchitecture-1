package com.globant.equattrocchio.data.service.api;

import com.globant.equattrocchio.data.response.Result;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SplashbaseApi {

    // avoid video preview images
    @GET("api/v1/images/latest?images_only=true")
    Call<Result> getImages();
}
