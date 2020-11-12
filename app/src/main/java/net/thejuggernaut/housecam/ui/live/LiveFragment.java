package net.thejuggernaut.housecam.ui.live;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import net.thejuggernaut.housecam.R;
import net.thejuggernaut.housecam.api.general.Info;
import net.thejuggernaut.housecam.api.stream.Websocket;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class LiveFragment extends Fragment {

    OkHttpClient client = null;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_live, container, false);
        // Ensure title bar is hidden
       // ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        createStream();
        return root;
    }

    public void createStream(){
        if(Info.serverAddr == ""){
            // No address
            return;
        }
        Websocket.shared = false;
        String url = "ws://"+ Info.serverAddr+"/stream";
        System.out.println("Url.. "+url);
        client = new OkHttpClient.Builder()
                .readTimeout(100,  TimeUnit.MILLISECONDS)
                .build();
        Websocket w = new Websocket(getActivity());
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newWebSocket(request, w);

        // Trigger shutdown of the dispatcher's executor so this process can exit cleanly.
        client.dispatcher().executorService().shutdown();

    }

    public void onPause () {
        Websocket.shared = true;
        System.out.println("Pause!");
        super.onPause();
    }
}