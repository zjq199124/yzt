package com.maizhiyu.yzt.his;

import com.maizhiyu.yzt.bean.aro.TreatmentRo;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface HisApi {

    @POST("doctor/treatment/insertTreatment")
    public Call<Object> insertTreatment(@Body RequestBody  treatmentRo);
}
