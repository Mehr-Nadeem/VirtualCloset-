package com.example.mehr.virtualcloset;



import com.example.mehr.virtualcloset.model.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface APIInterface {

    @GET("forecast")
    Call<WeatherResponse> getWeatherInfoCity(@Query("q") String cityNameAndCountryCode,
                                             @Query("units") String units,
                                             @Query("appid")String APPID);

    @GET("forecast")
    Call<WeatherResponse> getWeatherInfoCoords(@Query("lat") double latitude,
                                               @Query("lon") double longitude,
                                               @Query("units") String units,
                                               @Query("appid") String APPID);

}
