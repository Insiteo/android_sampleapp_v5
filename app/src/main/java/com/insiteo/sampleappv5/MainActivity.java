package com.insiteo.sampleappv5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.insiteo.sdk_v5.Config;
import com.insiteo.sdk_v5.Insiteo;
import com.insiteo.sdk_v5.authModule.ISError;
import com.insiteo.sdk_v5.authModule.ISUserSite;
import com.insiteo.sdk_v5.authModule.OnAuthEventListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tv = findViewById(R.id.tv_siteId);
        Insiteo.getInstance().startAndUpdate(new OnAuthEventListener() {
            @Override
            public void onInitUpdate(ISError error, int current, int max) {

            }

            @Override
            public void onInitDone(ISError error, ISUserSite suggestedSite) {
                Log.d("Insiteo", suggestedSite.getId() + " | " + suggestedSite.getSiteId());
                //suggestedSite.getSites().get(0).getName()
                tv.setText("cc");
            }
        }, "", "" /**Config.API_KEY**/);
    }
}
