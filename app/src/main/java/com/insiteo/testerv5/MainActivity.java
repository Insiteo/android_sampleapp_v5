package com.insiteo.testerv5;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import com.insiteo.sdk_v5.INSMapActivity;
import com.insiteo.sdk_v5.INSMapFragmentX;
import com.insiteo.sdk_v5.Insiteo;
import com.insiteo.sdk_v5.authModule.ISError;
import com.insiteo.sdk_v5.authModule.OnAuthEventListener;
import com.insiteo.sdk_v5.mapModule.MapManager;
import com.insiteo.sdk_v5.mapModule.UnityMapHandlerListener;
import com.insiteo.sdk_v5.mapModule.entities.Building;
import com.insiteo.sdk_v5.mapModule.entities.Floor;
import com.insiteo.sdk_v5.mapModule.entities.Space;
import com.insiteo.sdk_v5.mapModule.entities.SubArea;
import com.insiteo.sdk_v5.packageModule.entities.Site;

import java.util.List;

public class MainActivity extends AppCompatActivity implements UnityMapHandlerListener {
    Button button;
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        final TextView tv = findViewById(R.id.tv_siteId);
        Insiteo.getInstance().startAndUpdate(new OnAuthEventListener() {
            @Override
            public void onInitUpdate(ISError error, int current, int max) {

            }

            @Override
            public void onInitDone(ISError error, Site suggestedSite) {
                Log.d("Insiteo", suggestedSite.getName());
                //suggestedSite.getSites().get(0).getName()
                tv.setText(suggestedSite.getName());
            }
        }, ""); //"", "" /**Config.API_KEY**/);

        Insiteo.getInstance().getLocationModule().start(this);


        //setContentView(R.layout.activity_fragment);

        FrameLayout fl = findViewById(R.id.INSMapViewPlaceholder);
        INSMapFragmentX frag = MapManager.getInstance().initDisplayComponent();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
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
    public void onChangeSiteEventListener(String apiKey) {

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
    public void onSiteReceived(List<com.insiteo.sdk_v5.mapModule.entities.Site> siteList) {

    }

    @Override
    public void onBuildingsReceived(List<Building> buildingList) {

    }

    @Override
    public void onFloorsReceived(List<Floor> floorList) {

    }

    @Override
    public void onSpacesReceived(List<Space> spaceList) {

    }

    @Override
    public void onDeskReceived(List<SubArea> deskList) {

    }

    @Override
    public void onAssetTypeReceived(String assetType) {

    }

    @Override
    public void onSpaceReceived(Space space) {

    }

    @Override
    public void onCountryReceived(String countryJson) {
        Toast.makeText(this, countryJson, Toast.LENGTH_SHORT).show();
    }
}
