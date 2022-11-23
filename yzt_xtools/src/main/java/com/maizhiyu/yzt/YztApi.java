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
    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @Multipart
    @POST("file/uploadCheck")
    Call<Result> upload(@Part MultipartBody.Part file);

    /**
     * 添加患者检查报告
     *
     * @param check
     * @return
     */
    @POST("check/addCheck")
    Call<Result> addCheck(@Body BuCheck check);

}