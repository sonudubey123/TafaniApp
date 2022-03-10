package com.sunmi.printerhelper.nextgen_sharique.retroft;


import com.sunmi.printerhelper.nextgen_sharique.retroft.api_request.ApiRequestLogin;
import com.sunmi.printerhelper.nextgen_sharique.retroft.api_response.ApiResponseLogin;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIInterface {





    @POST("glorest/api/v1/agentinfo")
    Call<ApiResponseLogin> callLoginApi(@Body ApiRequestLogin apiRequest);



}