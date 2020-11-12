package net.thejuggernaut.housecam.api.stream;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsibbold.zoomage.ZoomageView;

import net.thejuggernaut.housecam.R;
import net.thejuggernaut.housecam.api.video.SetupVideoApi;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class Websocket extends WebSocketListener {


    private Activity act;
    public static boolean shared;
    public Websocket(Activity act){
        this.act = act;
    }

    @Override public void onOpen(WebSocket webSocket, Response response) {
        System.out.println("Connected");
    }
    JSONObject jsonObject = null;
    @Override public void onMessage(WebSocket webSocket, final String text) {
        ZoomageView i = (ZoomageView) act.findViewById(R.id.imageVideo);
        TextView txt = (TextView) act.findViewById(R.id.liveViewText);

        //System.out.println("MESSAGE: " + text);

        try {
            jsonObject = new JSONObject(text);
           // System.out.println("Json message has.. "+jsonObject.toString());
        } catch (JSONException e) {
            System.out.println("Error in json");
            e.printStackTrace();
        }
        act.runOnUiThread(new Runnable() {

                @Override
                public void run() {
//                    if(shared){
//                        System.out.println("Shared has asked socket to close");
//                        webSocket.cancel();
//                        return;
//                    }
                    //Handle UI here
                    if(txt!= null){
                        txt.setVisibility(View.INVISIBLE);
                    }


                    if(i == null){
                        // The image view no longer exists (rotated screen)
                        webSocket.cancel();
                        System.out.println("End websocket due to no image area to change");
                        return;
                    }
                    byte[] decodedString = new byte[0];//Base64.decode(text, Base64.DEFAULT);
                    try {
                        decodedString = Base64.decode(jsonObject.getString("Image"), Base64.DEFAULT);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                    // Get the previous zoom
                    ImageView.ScaleType zo = i.getScaleType();
                    // Update the image
                    i.setImageBitmap(decodedByte);
                    // Update the zoom
                    i.setScaleType(zo);
                }
            });

        webSocket.send("ok");

    }

    @Override public void onClosing(WebSocket webSocket, int code, String reason) {
        TextView txt = (TextView) act.findViewById(R.id.liveViewText);
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(txt!= null){
                    txt.setVisibility(View.VISIBLE);
                    txt.setText("Closed the camera connection");
                }

            }
        });
        webSocket.close(1000, null);
        System.out.println("CLOSE: " + code + " " + reason);
    }

    @Override public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        TextView txt = (TextView) act.findViewById(R.id.liveViewText);
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(txt!= null){
                    txt.setVisibility(View.VISIBLE);
                    txt.setText("I tried my best. But I failed to connect");
                }

            }
        });
        t.printStackTrace();
    }


}
