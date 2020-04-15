package com.insiteo.samplappv5;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.insiteo.sdk_v5.INSMapFragment;
import com.insiteo.sdk_v5.mapModule.MapManager;
import com.insiteo.sdk_v5.mapModule.UnityMapHandlerListener;
import com.insiteo.sdk_v5.mapModule.entities.Building;
import com.insiteo.sdk_v5.mapModule.entities.Floor;
import com.insiteo.sdk_v5.mapModule.entities.Site;
import com.insiteo.sdk_v5.mapModule.entities.Space;
import com.insiteo.sdk_v5.mapModule.entities.SubArea;

import java.util.List;

public class MainActivity extends AppCompatActivity implements UnityMapHandlerListener {
    Button button;
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FrameLayout fl = findViewById(R.id.fl_forUnity);
        INSMapFragment frag = MapManager.getInstance().initDisplayComponent();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(fl.getId(), frag).commit();

        button = findViewById(R.id.fl_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MapManager.getInstance().initApp();
                button.setVisibility(View.GONE);
                button2.setVisibility(View.VISIBLE);
            }
        });

        button2 = findViewById(R.id.fl_button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MapManager.getInstance().getCountries();
            }
        });

        MapManager.getInstance().registerHandler(this);
        MapManager.getInstance().changeDisplayMode(0);
    }

    @Override
    public void onStartLocationEventListener() {

    }

    @Override
    public void onStopLocationEventListener() {

    }

    @Override
    public void onChangeSiteEventListener(String s) {

    }

    @Override
    public void onSiteDisplayed() {

    }

    @Override
    public void onSiteLoaded() {

    }

    @Override
    public void onDisplayModeChanged() {

    }

    @Override
    public void onSiteReceived(List<Site> list) {

    }

    @Override
    public void onBuildingsReceived(List<Building> list) {

    }

    @Override
    public void onFloorsReceived(List<Floor> list) {

    }

    @Override
    public void onSpacesReceived(List<Space> list) {

    }

    @Override
    public void onDeskReceived(List<SubArea> list) {

    }

    @Override
    public void onAssetTypeReceived(String s) {

    }

    @Override
    public void onSpaceReceived(Space space) {

    }

    @Override
    public void onCountryReceived(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}
