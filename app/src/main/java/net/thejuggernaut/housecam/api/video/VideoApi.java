package net.thejuggernaut.housecam.api.video;

import net.thejuggernaut.housecam.api.settings.Setting;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface VideoApi {


    @GET("videos/{last}")
    Call<Video> getVideosPagination(@Path("last") String laststamp);

    @GET("videos/")
    Call<Video> getVideos();

    @DELETE("video/{code}")
    Call<Void> deleteVideo(@Path("code") String code);

}
