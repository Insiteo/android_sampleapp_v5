package com.insiteo.sampleappv5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.insiteo.sdk_v5.INSMain;
import com.insiteo.sdk_v5.authModule.ISError;
import com.insiteo.sdk_v5.authModule.OnAuthEventListener;
import com.insiteo.sdk_v5.mapModule.INSMapFragment;
import com.insiteo.sdk_v5.mapModule.INSMapHandlerListener;
import com.insiteo.sdk_v5.mapModule.INSModelHandlerListener;
import com.insiteo.sdk_v5.mapModule.MapManager;
import com.insiteo.sdk_v5.mapModule.entities.Building;
import com.insiteo.sdk_v5.mapModule.entities.Floor;
import com.insiteo.sdk_v5.mapModule.entities.Site;
import com.insiteo.sdk_v5.mapModule.entities.Space;
import com.insiteo.sdk_v5.mapModule.entities.SubArea;

import java.util.List;

public class MainActivity extends AppCompatActivity implements INSMapHandlerListener, INSModelHandlerListener {
    Button button;
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        final TextView tv = findViewById(R.id.tv_siteId);
        INSMain.getInstance().startAndUpdate(new OnAuthEventListener() {
            @Override
            public void onInitUpdate(ISError error, int current, int max) {

            }

            @Override
            public void onInitDone(ISError error, com.insiteo.sdk_v5.packageModule.entities.Site suggestedSite) {
                Log.d("INSMain", suggestedSite.getName());
                //suggestedSite.getSites().get(0).getName()
                tv.setText(suggestedSite.getName());
            }
        }, ""); //"", "" /**Config.API_KEY**/);

        INSMain.getInstance().getLocationModule().start(this);


        //setContentView(R.layout.activity_fragment);

        FrameLayout fl = findViewById(R.id.INSMapViewPlaceholder);
        INSMapFragment frag = MapManager.getInstance(getBaseContext()).initDisplayComponent();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(fl.getId(), frag).commit();

        button = findViewById(R.id.fl_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MapManager.getInstance(getBaseContext()).initApp();
                button.setVisibility(View.GONE);
                button2.setVisibility(View.VISIBLE);
            }
        });

        button2 = findViewById(R.id.fl_button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MapManager.getInstance(getBaseContext()).getModelManager().getCountries();
            }
        });

        MapManager.getInstance(getBaseContext()).registerMapHandler(this);
        MapManager.getInstance(getBaseContext()).registerModelHandler(this);
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
