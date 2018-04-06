package com.niks.pdf.network;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;


public interface ApiInterface {
    @GET("default/sample.pdf")
    Call<ResponseBody> getPdf();
}