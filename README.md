# Insiteo SDK V5

## Configuration
### Step 1
Import the library

    File -> New --> New Module
Select Library V5, then, add depencie to the Library

	Open Module Settings --> + --> Select the SDK
    
### Step 2
Add Permissions to the Manifest

	<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />  
	<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />  
	<uses-permission android:name="android.permission.READ_PHONE_STATE" />  
	<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />  
	<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
## Initialisation
### Step 1
Create a layout with a FrameLayout

	<androidx.constraintlayout.widget.ConstraintLayout 
		xmlns:android="http://schemas.android.com/apk/res/android"
	    xmlns:app="http://schemas.android.com/apk/res-auto"
	    xmlns:tools="http://schemas.android.com/tools"
	    android:layout_width="match_parent"
	    android:layout_height="match_parent"
	    >
	    <FrameLayout
	        android:id="@+id/mapViewLayout"
	        android:layout_width="match_parent"
	        android:layout_height="0dp"
	        app:layout_constraintBottom_toBottomOf="parent"
	        app:layout_constraintEnd_toEndOf="parent"
	        app:layout_constraintStart_toStartOf="parent"
	        app:layout_constraintTop_toTopOf="parent"></FrameLayout>
	</androidx.constraintlayout.widget.ConstraintLayout>

### Step 2
Get an INSMapFragment instance :

    if(mMapFragment == null) {
        mMapFragment = MapManager.getInstance(getContext()).initDisplayComponent();
    }

### Step 3
Add the fragment to a FrameLayout

    FrameLayout fl = view.findViewById(R.id.mapViewLayout);
    FragmentTransaction ft = getParentFragmentManager().beginTransaction();
    if(!mMapFragment.isAdded()) {
        ft.replace(fl.getId(), mMapFragment, "mapViewFragment");
        ft.addToBackStack("mapViewFragment");
        ft.commit();
    }
### Step 4
Initialize the map
        
    if(!MapManager.isStarted) {
        MapManager.getInstance(getContext()).initApp();
    }

### Step 5
Don't forget to remove the INSMapFragment when parent fragment is destroyed

    @Override
    public void onDestroyView()
    {
        if(mMapFragment != null) {
            FragmentTransaction ft = getParentFragmentManager().beginTransaction();
            if(mMapFragment.isAdded()) {
                Fragment fragment = getParentFragmentManager().findFragmentById(mMapFragment.getId());
                if (fragment != null) {
                    ft.remove(fragment);
                    ft.commit();
                }
            }
        }
        super.onDestroyView();
    }
        
 ## Configure the Location
If you want to use Location Module, you still need Library V3 (Current version is 3.6.5e )
Here is a quick configuration, if you need further informations, please report to Library V3 Documentation.

### Step 1
Import Library V3

    File -> New --> New Module
Select Library V3, then, add depencie to the Library

	Open Module Settings --> + --> Select the SDK
###   Step 2
Add MetaDatas to Android Manifest

	<meta-data  
	  android:name="com.insiteo.lbs.ApiKey"  
	  android:value="YOU_API_KEY" />  
	<meta-data  
	  android:name="com.insiteo.lbs.ISEServerType"  
	  android:value="prod" />  
	<meta-data  
	  android:name="com.insiteo.lbs.ISERenderMode"  
	  android:value="MODE_2D" />  
	<meta-data  
	  android:name="com.insiteo.lbs.AnalyticsAutoStart"  
	  android:value="true" />
### Step 3 
Implement the library listeners

	extends Fragment implements ISIInitListener, ISILocationListener
Then, import the methods.

### Step 4
Initialize the Library (in onCreate method is fine)

	Insiteo.getInstance().initialize(getContext(), this);

### Step 5
Fill up the Library V3 initialization methods : 
Note : StartAndUpdate() in onInitDone is very important.
  
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

### Step 6 
Configure MapListener Callback for Library V5

	implements INSMapHandlerListener
Then, import the methods.

### Step 7
When initDisplayComponent has been called, register the handler

	MapManager.getInstance(getContext()).registerMapHandler(this);

### Step 8
Create a link between Lib V3 and Lib V5

	@Override  
	public void onStartLocationEventListener() {  
	    // Start location  
	  startLocation();  
	}  
	  
	@Override  
	public void onStopLocationEventListener() {  
	    stopLocation();  
	}

	public void startLocation(){  
	    ISLocationProvider.getInstance().start(21, this, Insiteo.getCurrentSite().getMapRootId(), false);  
	  
	}  
	  
	private void stopLocation(){  
	    // Stop location  
	  ISLocationProvider.getInstance().stop();  
	}

Then, transfer location information from V3 to V5

	@Override  
	public void onLocationReceived(ISLocation isLocation) {  
	    int mapId = isLocation.getMapID();  
	    double coord_x = isLocation.getCoord().x ;  
	    double coord_y = isLocation.getCoord().y ;  
	    MapManager.getInstance(getContext()).sendLocation(coord_x,coord_y, mapId);  
	}
