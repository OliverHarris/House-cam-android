package net.thejuggernaut.housecam;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import net.thejuggernaut.housecam.ui.setting.Actions;

import static net.thejuggernaut.housecam.ui.videos.Actions.getFirst5;
import static net.thejuggernaut.housecam.ui.videos.Actions.getNext;
import static net.thejuggernaut.housecam.ui.videos.Actions.getPrev;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }


    public void settingOnSaveClick(View v){
        Actions.updateSettings(v.getRootView());
    }

    public void videoNext(View v){
        getNext();
    }

    public void videoPrev(View v){
        getPrev();
    }

    public void videoListReset(View v){
        getFirst5();
    }

}