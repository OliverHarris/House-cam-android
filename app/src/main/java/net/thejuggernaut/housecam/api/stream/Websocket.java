package net.thejuggernaut.housecam.api.stream;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ImageView;

import net.thejuggernaut.housecam.R;
import net.thejuggernaut.housecam.api.video.SetupVideoApi;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class Websocket extends WebSocketListener {


    private Activity act;
    public Websocket(Activity act){
        this.act = act;
    }

    @Override public void onOpen(WebSocket webSocket, Response response) {
        System.out.println("Connected");
    }
    JSONObject jsonObject = null;
    @Override public void onMessage(WebSocket webSocket, final String text) {
        System.out.println("MESSAGE: " + text);

        try {
            jsonObject = new JSONObject(text);
            System.out.println("Json message has.. "+jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        act.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //Handle UI here
                    ImageView i = (ImageView) act.findViewById(R.id.imageVideo);
                    byte[] decodedString = new byte[0];//Base64.decode(text, Base64.DEFAULT);
                    try {
                        decodedString = Base64.decode(jsonObject.getString("Image"), Base64.DEFAULT);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    i.setImageBitmap(decodedByte);
                }
            });

        webSocket.send("ok");

    }

    @Override public void onClosing(WebSocket webSocket, int code, String reason) {
        webSocket.close(1000, null);
        System.out.println("CLOSE: " + code + " " + reason);
    }

    @Override public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        t.printStackTrace();
    }


}
