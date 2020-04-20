package com.insiteo.testerv5.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.insiteo.sdk_v5.INSMapFragmentX;
import com.insiteo.sdk_v5.mapModule.MapManager;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<INSMapFragmentX> mapFragment;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void setMapFragment(MutableLiveData<INSMapFragmentX> mapFragment) {
        this.mapFragment = mapFragment;
    }

}