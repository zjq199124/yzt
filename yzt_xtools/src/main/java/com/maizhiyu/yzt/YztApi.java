package com.maizhiyu.yzt;


import com.maizhiyu.yzt.result.Result;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.*;


public interface YztApi {

    /**
     * 经络检查文件上传
     *
     * @param file
     * @return
     */
    @Multipart
    @POST("jinluo/receiveFile")
    Call<Result> upload(@Part MultipartBody.Part file, @Query("patientPhone") String patientPhone);


}