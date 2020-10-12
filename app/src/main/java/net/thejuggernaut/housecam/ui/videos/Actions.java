package net.thejuggernaut.housecam.ui.videos;

import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
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
             TextView title = new TextView(root.getContext());
             TextView fs = new TextView(root.getContext());
             ImageView img = new ImageView(root.getContext());
             LinearLayout l = new LinearLayout(root.getContext());
             // Load image
             LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(480,270);
             img.setLayoutParams(parms);
             byte[] data = Base64.decode(vid.getImage(), Base64.DEFAULT);
             img.setImageBitmap(BitmapFactory.decodeByteArray(data, 0, data.length));


             title.setText((vid.getCode()));
             int conv = vid.getSize()/1000/1000;
            fs.setText( Integer.toString(conv)+"MB");

             l.addView(img);
             l.addView(title);
             vidLayout.addView(l);
             vidLayout.addView((fs));
         }

        }

            @Override
            public void onFailure(Call<Video[]> call, Throwable t) {
                t.printStackTrace();
            }

        });
    }

}
