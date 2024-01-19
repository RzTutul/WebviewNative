package com.rztechtunes.motherbdrecharge;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Url;

public interface ApiService {
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @GET()
    Call<ExpireResponse> chcekExpired(@Url String url);
}
