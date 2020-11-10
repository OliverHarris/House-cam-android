package net.thejuggernaut.housecam.ui.videos;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.thejuggernaut.housecam.MainActivity;
import net.thejuggernaut.housecam.R;
import net.thejuggernaut.housecam.api.settings.SettingApi;
import net.thejuggernaut.housecam.api.settings.SetupRetro;
import net.thejuggernaut.housecam.api.video.SetupVideoApi;
import net.thejuggernaut.housecam.api.video.Video;
import net.thejuggernaut.housecam.api.video.VideoApi;
import net.thejuggernaut.housecam.ui.video.DisplayVideo;

import java.net.URLEncoder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Actions {

    static int firstVideo,lastVideo;
    static View rootView;

    public static void setView(View root){
        rootView = root;
    }

    public static void getFirst5(){
        // Connect and display settings
        VideoApi api = null;
        try {
            api = SetupVideoApi.getRetro(rootView.getContext());
        } catch (Exception e) {
            // No address
            return;
        }
        Call<Video[]> call = api.getVideos();
        call.enqueue(new Callback<Video[]>() { @Override
        public void onResponse(Call<Video[]> call, Response<Video[]> response) {
            if (response.body()== null){
                // No results
                Toast.makeText(rootView.getContext(), "Nothing to load",
                        Toast.LENGTH_LONG).show();
                return;
            }
        displayVideoList(response.body(),rootView);
        }

            @Override
            public void onFailure(Call<Video[]> call, Throwable t) {
                t.printStackTrace();
            }

        });
    }

    public static void getNext(){
        Button b = (Button) rootView.findViewById(R.id.buttonPrev);
        b.setEnabled(true);
        // Connect and display settings
        VideoApi api = null;
        try {
            api = SetupVideoApi.getRetro(rootView.getContext());
        } catch (Exception e) {
            // No address
            return;
        }
        Call<Video[]> call = api.getVideosPagination(lastVideo);
        call.enqueue(new Callback<Video[]>() { @Override
        public void onResponse(Call<Video[]> call, Response<Video[]> response) {
            if (response.body()== null){
                // No results
                Toast.makeText(rootView.getContext(), "Nothing to load",
                        Toast.LENGTH_LONG).show();
                return;
            }
            displayVideoList(response.body(),rootView);
        }

            @Override
            public void onFailure(Call<Video[]> call, Throwable t) {
                t.printStackTrace();
            }

        });
    }

    public static void getPrev(){
        // Connect and display settings
        VideoApi api = null;
        try {
            api = SetupVideoApi.getRetro(rootView.getContext());
        } catch (Exception e) {
            // No address
            return;
        }
        Call<Video[]> call = api.getVideosPaginationPrev(firstVideo);
        call.enqueue(new Callback<Video[]>() { @Override
        public void onResponse(Call<Video[]> call, Response<Video[]> response) {
            System.out.println("For prev I got "+response.body());
            if (response.body()== null){
                System.out.println("No prev");
                Button b = (Button) rootView.findViewById(R.id.buttonPrev);
                b.setEnabled(false);
                // No results
                Toast.makeText(rootView.getContext(), "Nothing to load",
                        Toast.LENGTH_LONG).show();
                return;
            }
            displayVideoList(response.body(),rootView);
        }

            @Override
            public void onFailure(Call<Video[]> call, Throwable t) {
                t.printStackTrace();
            }

        });
    }

    public static void displayVideoList(Video[] vids, View root){
        LinearLayout vidLayout = (LinearLayout) root.findViewById(R.id.videoLayout);
        vidLayout.removeAllViews();
        boolean first = true;
        for ( Video vid:vids){
            if (first){
                System.out.println("Last video is "+lastVideo);
                firstVideo = lastVideo;
                first = false;
            }
            lastVideo = vid.getStamp();

            TextView title = new TextView(root.getContext());
            TextView fs = new TextView(root.getContext());
            ImageView img = new ImageView(root.getContext());
            LinearLayout l = new LinearLayout(root.getContext());
            // Load image
            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(480,270);
            img.setLayoutParams(parms);
            byte[] data = Base64.decode(vid.getImage(), Base64.DEFAULT);
            img.setImageBitmap(BitmapFactory.decodeByteArray(data, 0, data.length));

            // Set onclick to display video
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(root.getContext(), DisplayVideo.class);
                    String value = "http://192.168.1.7:8000/motion/hq/"+ URLEncoder.encode(vid.getCode());

                    myIntent.putExtra("videourl", value); //Optional parameters
                    root.getContext().startActivity(myIntent);
                }
            });


            title.setText((vid.getCode()));
            int conv = vid.getSize()/1000/1000;
            fs.setText( Integer.toString(conv)+"MB");

            l.addView(img);
            l.addView(title);
            vidLayout.addView(l);
            vidLayout.addView((fs));
        }

    }

}
