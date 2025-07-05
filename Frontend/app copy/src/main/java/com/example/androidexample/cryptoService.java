package com.example.androidexample; // Adjust package name if necessary

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface cryptoService {
    @GET("cryptoPrices") // Endpoint to get Bitcoin price
    Call<List<getCryptoPrice>> getCryptos();

    @GET("cryptoPrices/{name}")
    Call<getCryptoPrice> getCryptoByName(@Path("name") String name);
}
