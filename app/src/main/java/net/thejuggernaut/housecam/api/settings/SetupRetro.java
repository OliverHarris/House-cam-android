package net.thejuggernaut.housecam.api.settings;

import android.content.Context;
import android.content.res.Resources;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.thejuggernaut.housecam.R;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SetupRetro {

    public static SettingApi getRetro(Context c){
        Gson gson = new GsonBuilder()
                .setLenient()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .create();
       // SharedPreferences pref = c.getSharedPreferences("AccountInfo",MODE_PRIVATE);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        //.addHeader("Authorization", "Bearer " + pref.getString("Token",""))
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.7:8000")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        SettingApi api = retrofit.create(SettingApi.class);

        return api;
    }
}
