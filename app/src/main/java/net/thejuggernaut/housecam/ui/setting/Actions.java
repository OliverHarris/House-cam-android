package net.thejuggernaut.housecam.ui.setting;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.chip.Chip;

import net.thejuggernaut.housecam.R;
import net.thejuggernaut.housecam.api.settings.Setting;
import net.thejuggernaut.housecam.api.settings.SettingApi;
import net.thejuggernaut.housecam.api.settings.SetupRetro;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Actions {

    public static void getSettings(final View root){
        // Get the settings and update the view
        final EditText textRTSP = (EditText) root.findViewById(R.id.textRTSP);
        final EditText textFPS = (EditText) root.findViewById(R.id.textFPS);
        final EditText textMin = (EditText) root.findViewById(R.id.textMinCount);
        final EditText textBlur = (EditText) root.findViewById(R.id.textBlur);
        final EditText textBufferBefore = (EditText) root.findViewById(R.id.textBufferBefore);
        final EditText textBufferAfter = (EditText) root.findViewById(R.id.textBufferAfter);
        final EditText textNoMove = (EditText) root.findViewById(R.id.textNoMove);
        final Switch debug = (Switch) root.findViewById(R.id.switchDebug);
        final Switch motion = (Switch) root.findViewById(R.id.switchMotion);


        // Connect and display settings
        SettingApi api = SetupRetro.getRetro(root.getContext());
        Call<Setting> call = api.getSetting();
        call.enqueue(new Callback<Setting>() { @Override
        public void onResponse(Call<Setting> call, Response<Setting> response) {
            if(response.isSuccessful()) {
            Setting s = response.body();
            textRTSP.setText(s.getConnection());
            System.out.println("Number is "+s.getFps()+" now is "+Integer.toString(s.getFps()));
            textFPS.setText(Integer.toString(s.getFps()));
            textMin.setText(Integer.toString(s.getMinCount()));
            textBlur.setText(Integer.toString(s.getBlur()));
            textBufferBefore.setText(Integer.toString(s.getBufferBefore()));
            textBufferAfter.setText(Integer.toString(s.getBufferAfter()));
            textNoMove.setText(Integer.toString(s.getNoMoveRefreshCount()));
            debug.setChecked(s.isDebug());
            motion.setChecked(s.isMotion());

            } else {
                alert(root.getContext(),"Internal error","Couldn't get settings");
            }
        }


            @Override
            public void onFailure(Call<Setting> call, Throwable t) {
                alert(root.getContext(),"Android error","Couldn't get settings");
                t.printStackTrace();
            }

        });
    }


    public static void updateSettings(final View root){
        // Get the settings and update the view
        final EditText textRTSP = (EditText) root.findViewById(R.id.textRTSP);
        final EditText textFPS = (EditText) root.findViewById(R.id.textFPS);
        final EditText textMin = (EditText) root.findViewById(R.id.textMinCount);
        final EditText textBlur = (EditText) root.findViewById(R.id.textBlur);
        final EditText textBufferBefore = (EditText) root.findViewById(R.id.textBufferBefore);
        final EditText textBufferAfter = (EditText) root.findViewById(R.id.textBufferAfter);
        final EditText textNoMove = (EditText) root.findViewById(R.id.textNoMove);
        final Switch debug = (Switch) root.findViewById(R.id.switchDebug);
        final Switch motion = (Switch) root.findViewById(R.id.switchMotion);

        Setting setting = new Setting();
        setting.setConnection(textRTSP.getText().toString());
        setting.setFps(Integer.parseInt(textFPS.getText().toString()));
        setting.setMinCount(Integer.parseInt(textMin.getText().toString()));
        setting.setBlur(Integer.parseInt(textBlur.getText().toString()));
        setting.setBufferAfter(Integer.parseInt(textBufferAfter.getText().toString()));
        setting.setBufferBefore(Integer.parseInt(textBufferBefore.getText().toString()));
        setting.setNoMoveRefreshCount(Integer.parseInt(textNoMove.getText().toString()));
        setting.setDebug(debug.isChecked());
        setting.setMotion(motion.isChecked());

        // Connect and display settings
        SettingApi api = SetupRetro.getRetro(root.getContext());
        Call<Void> call = api.updateSetting(setting);
        call.enqueue(new Callback<Void>() { @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            if(response.isSuccessful()) {
                alert(root.getContext(),"Updated","");
            } else {
                alert(root.getContext(),"Internal error","Couldn't update settings");
            }
        }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                alert(root.getContext(),"Android error","Couldn't update settings");
                t.printStackTrace();
            }

        });
    }


    private static void alert(Context c, String title, String message){
        new AlertDialog.Builder(c)
                .setTitle(title)
                .setMessage(message)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setNegativeButton(android.R.string.ok, null)
                .show();
    }

}
