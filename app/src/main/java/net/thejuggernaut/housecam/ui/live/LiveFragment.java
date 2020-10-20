package net.thejuggernaut.housecam.ui.live;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import net.thejuggernaut.housecam.R;
import net.thejuggernaut.housecam.api.stream.Websocket;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class LiveFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_live, container, false);
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(100,  TimeUnit.MILLISECONDS)
                .build();
        Websocket w = new Websocket(getActivity());
        Request request = new Request.Builder()
                .url("ws://192.168.1.7:8000/stream")
                .build();
        client.newWebSocket(request, w);

        // Trigger shutdown of the dispatcher's executor so this process can exit cleanly.
        client.dispatcher().executorService().shutdown();

        return root;
    }
}