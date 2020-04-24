package com.insiteo.sampleappv5.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.insiteo.sampleappv5.MainDrawerActivity;
import com.insiteo.sampleappv5.R;
import com.insiteo.sdk_v5.mapModule.MapManager;

public class LoginFragment extends Fragment {

    private LoginViewModel slideshowViewModel;
    EditText loginET;
    EditText passwordET;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(LoginViewModel.class);
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        loginET = root.findViewById(R.id.loginEditText);
        passwordET = root.findViewById(R.id.passwordEditText);

        Button btn_getCountry = root.findViewById(R.id.btn_getCountry);
        btn_getCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapManager.getInstance(getContext()).initApp(loginET.getText().toString(), passwordET.getText().toString());
                changeFragment();
            }
        });

        return root;
    }

    private void changeFragment() {
        ((MainDrawerActivity)getActivity()).selectMapFragment();
    }

}