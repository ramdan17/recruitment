package com.example.recruitmenttest.activity.util.api;

public class UtilsApi {

    // ini adalah localhost.
    public static final String BASE_URL_API = "http://18.141.178.15:8080/v2/api-docs";

    // Mendeklarasikan Interface BaseApiService
    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
