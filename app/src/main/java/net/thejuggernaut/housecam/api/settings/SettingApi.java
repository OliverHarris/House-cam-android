package net.thejuggernaut.housecam.api.settings;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface SettingApi {



    @GET("config")
    Call<Setting> getSetting();

    @POST("config")
    Call<Void> updateSetting(@Body Setting setting);

}
