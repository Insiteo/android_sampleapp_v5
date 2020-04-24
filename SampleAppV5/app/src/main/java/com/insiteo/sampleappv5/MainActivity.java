package com.insiteo.sampleappv5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.insiteo.lbs.Insiteo;
import com.insiteo.lbs.common.auth.entities.ISUserSite;
import com.insiteo.lbs.common.init.ISEPackageType;
import com.insiteo.lbs.common.init.ISPackage;
import com.insiteo.lbs.common.init.listener.ISIInitListener;
import com.insiteo.lbs.location.ISILocationListener;
import com.insiteo.lbs.location.ISLocation;
import com.insiteo.lbs.location.ISLocationProvider;
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

public class MainActivity extends AppCompatActivity implements INSMapHandlerListener, INSModelHandlerListener, ISIInitListener, ISILocationListener {
    INSMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Insiteo.getInstance().initialize(this, this);

        if(mapFragment == null)
            mapFragment = MapManager.getInstance(this).initDisplayComponent();

        if(!MapManager.isStarted) {
            MapManager.getInstance(this).initApp();
        }

        MapManager.getInstance(this).registerMapHandler(this);
        MapManager.getInstance(this).registerModelHandler(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if(!mapFragment.isAdded()) {
            ft.add(R.id.fl_forUnity, mapFragment, "mapViewFragment");
            ft.addToBackStack("mapViewFragment");
            ft.commit();
        }
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
        Toast.makeText(getApplicationContext(), countryJson, Toast.LENGTH_SHORT).show();
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
        MapManager.getInstance(getApplicationContext()).sendLocation(coord_x,coord_y, mapId);
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
