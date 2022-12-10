package com.maizhiyu.yzt.his;

import com.maizhiyu.yzt.bean.aro.TreatmentRo;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface HisApi {

    @POST("doctor/treatment/insertTreatment")
    public Call<Object> insertTreatment(@Body RequestBody  treatmentRo);

    @GET("doctor/treatment/cancelTreatmentById")
    public Call<Object> cancelTreatmentById(@Query("treatmentId") Integer treatmentId);
}
