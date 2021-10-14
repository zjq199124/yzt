package com.maizhiyu.yzt;


import com.maizhiyu.yzt.entity.BuCheck;
import com.maizhiyu.yzt.result.Result;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface YztApi {

    @Multipart
    @POST("file/upload")
    Call<Result> upload(@Part MultipartBody.Part file);

    @POST("check/addCheck")
    Call<Result> addCheck(@Body BuCheck check);

}