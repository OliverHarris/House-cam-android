package net.thejuggernaut.housecam.ui.videos;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.thejuggernaut.housecam.R;
import net.thejuggernaut.housecam.api.settings.SettingApi;
import net.thejuggernaut.housecam.api.settings.SetupRetro;
import net.thejuggernaut.housecam.api.video.SetupVideoApi;
import net.thejuggernaut.housecam.api.video.Video;
import net.thejuggernaut.housecam.api.video.VideoApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Actions {

    public static void getFirst5(View root){

        LinearLayout vidLayout = (LinearLayout) root.findViewById(R.id.videoLayout);

        // Connect and display settings
        VideoApi api = SetupVideoApi.getRetro(root.getContext());
        Call<Video[]> call = api.getVideos();
        call.enqueue(new Callback<Video[]>() { @Override
        public void onResponse(Call<Video[]> call, Response<Video[]> response) {
        vidLayout.removeAllViews();
         for ( Video vid:response.body()){
             TextView txt = new TextView(root.getContext());
             txt.setText((vid.getCode()));
             vidLayout.addView(txt);
         }

        }

            @Override
            public void onFailure(Call<Video[]> call, Throwable t) {
                t.printStackTrace();
            }

        });
    }

}
