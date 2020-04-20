package com.insiteo.testerv5.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.insiteo.sdk_v5.INSMapFragmentX;
import com.insiteo.sdk_v5.Insiteo;
import com.insiteo.sdk_v5.authModule.ISError;
import com.insiteo.sdk_v5.authModule.OnAuthEventListener;
import com.insiteo.sdk_v5.mapModule.MapManager;
import com.insiteo.sdk_v5.mapModule.UnityMapHandlerListener;
import com.insiteo.sdk_v5.mapModule.entities.Building;
import com.insiteo.sdk_v5.mapModule.entities.Floor;
import com.insiteo.sdk_v5.mapModule.entities.Site;
import com.insiteo.sdk_v5.mapModule.entities.Space;
import com.insiteo.sdk_v5.mapModule.entities.SubArea;
import com.insiteo.testerv5.R;

import java.util.List;

public class HomeFragment extends Fragment implements UnityMapHandlerListener {

    private HomeViewModel homeViewModel;
    Button button;
    Button button2;
    INSMapFragmentX frag;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        final TextView tv = root.findViewById(R.id.tv_siteId);
        Insiteo.getInstance().startAndUpdate(new OnAuthEventListener() {
            @Override
            public void onInitUpdate(ISError error, int current, int max) {

            }

            @Override
            public void onInitDone(ISError error, com.insiteo.sdk_v5.packageModule.entities.Site suggestedSite) {
                Log.d("Insiteo", suggestedSite.getName());
                //suggestedSite.getSites().get(0).getName()
                tv.setText(suggestedSite.getName());
            }
        }, ""); //"", "" /**Config.API_KEY**/);

       // Insiteo.getInstance().getLocationModule().start(this.getContext());


        //setContentView(R.layout.activity_fragment);

        if(frag == null)
            frag = MapManager.getInstance().initDisplayComponent();


        button = root.findViewById(R.id.fl_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MapManager.getInstance().initApp();
                button.setVisibility(View.GONE);
                button2.setVisibility(View.VISIBLE);
            }
        });

        button2 = root.findViewById(R.id.fl_button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MapManager.getInstance().getCountries();
            }
        });

        MapManager.getInstance().registerHandler(this);
        MapManager.getInstance().changeDisplayMode(0);

        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        FrameLayout fl = view.findViewById(R.id.mapViewLayout);


        FragmentTransaction ft = getFragmentManager().beginTransaction();

        ft.add(fl.getId(), frag, "mapViewFragment");
        ft.addToBackStack("mapViewFragment");

        ft.commit();

        Log.d("test", "cet");
    }

    @Override
    public void onDestroyView()
    {
        FragmentManager mFragmentMgr= getFragmentManager();
        FragmentTransaction mTransaction = mFragmentMgr.beginTransaction();
        if(frag != null) {
           // frag.close();
            MapManager.getInstance().reset();
            mTransaction.remove(frag);
            mTransaction.commit();
        }
        super.onDestroyView();
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
    public void onSiteReceived(List<Site> siteList) {

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

    }
}