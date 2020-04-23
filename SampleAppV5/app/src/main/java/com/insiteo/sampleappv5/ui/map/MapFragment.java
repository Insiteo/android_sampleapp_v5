package com.insiteo.sampleappv5.ui.map;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.insiteo.lbs.Insiteo;
import com.insiteo.lbs.common.auth.entities.ISUserSite;
import com.insiteo.lbs.common.init.ISEPackageType;
import com.insiteo.lbs.common.init.ISPackage;
import com.insiteo.lbs.common.init.listener.ISIInitListener;
import com.insiteo.lbs.location.ISILocationListener;
import com.insiteo.lbs.location.ISLocation;
import com.insiteo.lbs.location.ISLocationProvider;
import com.insiteo.sampleappv5.R;

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
import java.util.Stack;

public class MapFragment extends Fragment implements INSMapHandlerListener, INSModelHandlerListener, ISIInitListener, ISILocationListener {

    private INSMapFragment mMapFragment;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_map, container, false);

        Insiteo.getInstance().initialize(getContext(), this);

        if(mMapFragment == null) {
            mMapFragment = MapManager.getInstance(getContext()).initDisplayComponent();
        }

        if(!MapManager.isStarted) {
            MapManager.getInstance(getContext()).initApp();
        }

        MapManager.getInstance(getContext()).registerMapHandler(this);
        MapManager.getInstance(getContext()).registerModelHandler(this);

        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.d("INS - MapFragment", "onViewCreated Begin");
        FrameLayout fl = view.findViewById(R.id.mapViewLayout);
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();

        if(!mMapFragment.isAdded()) {
            ft.replace(fl.getId(), mMapFragment, "mapViewFragment");
            ft.addToBackStack("mapViewFragment");
            ft.commit();
        }
         Log.d("INS - MapFragment", "onViewCreated End");
    }

    @Override
    public void onDestroyView()
    {
        Log.d("INS - MapFragment", "onDestroyView");
        if(mMapFragment != null) {
            FragmentTransaction ft = getParentFragmentManager().beginTransaction();
            if(mMapFragment.isAdded()) {
                Fragment fragment = getParentFragmentManager().findFragmentById(mMapFragment.getId());
                if (fragment != null)
                    ft.remove(fragment);
                    ft.commit();
            }
        }
        super.onDestroyView();
    }
    @Override
    public void onDetach() {

        super.onDetach();

    }

    @Override
    public void onStartLocationEventListener() {
        // Start location
        startLocation();
    }

    @Override
    public void onStopLocationEventListener() {
        stopLocation();
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
        Toast.makeText(getContext(), countryJson, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInitDone(com.insiteo.lbs.common.ISError isError, ISUserSite isUserSite, boolean b) {
        Log.d("InsiteoTest", "onInitDone");
        if(isError == null) {
            Log.d("InsiteoTest", "onInitDone2");
            Insiteo.getInstance().startAndUpdate(isUserSite, this);
            // The suggested site will be started
        }
    }

    @Override
    public void onStartDone(com.insiteo.lbs.common.ISError isError, Stack<ISPackage> packageToUpdate) {
        Log.d("InsiteoTest", "onStartDone");

        if(isError == null) {
            if(!packageToUpdate.isEmpty()) {
                // Package update are available. They will be downloaded.
                Log.d("InsiteoTest", "onStartDone1");
            } else {
                // No package require to be updated. The SDK is no ready to be used.
                Log.d("InsiteoTest", "onStartDone2");
            }
        }
    }

    @Override
    public void onPackageUpdateProgress(ISEPackageType isePackageType, boolean b, long l, long l1) {
        showUpdateUI();
    }

    @Override
    public void onDataUpdateDone(com.insiteo.lbs.common.ISError isError) {
        if(isError == null) {
            // Packages have been updated. The SDK is no ready to be used.
            Log.d("InsiteoTest", "onDataUpdateDone !");
        }
    }

    private void showUpdateUI() {
        Log.d("InsiteoTest", "ShowUpdateUI");
    }

    @Override
    public Location selectClosestToLocation() {
        return null;
    }

    @Override
    public void onLocationInitDone(com.insiteo.lbs.common.ISError isError) {

    }

    @Override
    public void onLocationReceived(ISLocation isLocation) {
        int mapId = isLocation.getMapID();
        Log.d("MapFragment", "MapId: " + mapId
                + " | Location x: " + isLocation.getCoord().x
                + " | y: " + isLocation.getCoord().y
                + " | accuracy: " + isLocation.getAccuracy()
                + " | source: " + isLocation.getSource() );
        double coord_x = isLocation.getCoord().x ;
        double coord_y = isLocation.getCoord().y ;
        MapManager.getInstance(getContext()).sendLocation(coord_x,coord_y, mapId);
    }

    @Override
    public void onAzimuthReceived(float v) {

    }

    @Override
    public void onCompassAccuracyTooLow() {

    }

    @Override
    public void onNeedToActivateGPS() {

    }

    @Override
    public void onLocationLost(ISLocation isLocation) {

    }

    @Override
    public void noRegisteredBeaconDetected() {

    }

    @Override
    public void onWifiActivationRequired() {

    }

    @Override
    public void onBleActivationRequired() {

    }

    public void startLocation(){
        ISLocationProvider.getInstance().start(21, this, Insiteo.getCurrentSite().getMapRootId(), false);

    }

    private void stopLocation(){
        // Stop location
        ISLocationProvider.getInstance().stop();
    }
}