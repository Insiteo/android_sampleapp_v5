package com.insiteo.sampleappv5;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.insiteo.sdk_v5.Insiteo;
import com.insiteo.sdk_v5.UnityPlayerActivity;
import com.insiteo.sdk_v5.UnityPlayerFragment;
import com.insiteo.sdk_v5.authModule.ISError;
import com.insiteo.sdk_v5.authModule.OnAuthEventListener;
import com.insiteo.sdk_v5.packageModule.entities.Site;

public class MainActivity extends UnityPlayerActivity {
Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);

       // final TextView tv = findViewById(R.id.tv_siteId);
//        Insiteo.getInstance().startAndUpdate(new OnAuthEventListener() {
//            @Override
//            public void onInitUpdate(ISError error, int current, int max) {
//
//            }
//
//            @Override
//            public void onInitDone(ISError error, Site suggestedSite) {
//                Log.d("Insiteo", suggestedSite.getName() );
//                //suggestedSite.getSites().get(0).getName()
//                tv.setText("cc");
//            }
//        }, ""); //"", "" /**Config.API_KEY**/);
//
//        Insiteo.getInstance().getLocationModule().start(this);


        setContentView(R.layout.activity_fragment);

        FrameLayout fl = findViewById(R.id.fl_forUnity);
        UnityPlayerFragment frag = new UnityPlayerFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(fl.getId(), frag).commit();

        button = findViewById(R.id.fl_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "BOOM!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
