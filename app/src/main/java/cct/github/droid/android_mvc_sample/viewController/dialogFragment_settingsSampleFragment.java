package cct.github.droid.android_mvc_sample.viewController;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import cct.github.droid.android_mvc_sample.R;
import cct.github.droid.android_mvc_sample.businessObjects.SharedPrefManager;

public class dialogFragment_settingsSampleFragment extends DialogFragment {

    //extracting this from the onCreate method so we can reuse it later
    View view;
    SharedPrefManager manager;

    public static dialogFragment_settingsSampleFragment newInstance() {
        dialogFragment_settingsSampleFragment newfrag = new dialogFragment_settingsSampleFragment();
        return newfrag;
    }
    private void loadSettings() {

        //load String Value
        EditText txtStr = (EditText)view.findViewById(R.id.txt_StringVal);
        txtStr.setText(manager.getSettingString(getActivity(), "STRINGVAL"));

        //load Int Value
        EditText txtInt = (EditText)view.findViewById(R.id.txt_IntVal);
        txtInt.setText(Integer.toString(manager.getSettingsInt(getActivity(),"INTVAL")));

    }

    //----------------------------------------
    //------ Android Life Cycle events -------
    //----------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(STYLE_NO_TITLE);  //remove the title bar
        view = inflater.inflate(R.layout.dialogfragment_settingssample, container);

        //initialize our preference manager - send in our application string for preference storage
        manager = new SharedPrefManager(getResources().getString(R.string.app_settings_name));

        //Update your String Value -- not binding to button, just setting onclick listener
        view.findViewById(R.id.btn_String_Val).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { btnUpdateStringValue(v); }
        });

        //Update your Integer Value -- not binding to button, just setting onclick listener
        view.findViewById(R.id.btn_IntVal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { btnUpdateIntValue(v); }
        });

        //modal close button
        view.findViewById(R.id.btn_DB_Close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCloseClick(v);
            }
        });

        loadSettings();

        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    @Override
    public void onStart() {
        super.onStart();

        // safety check
        if (getDialog() == null) {
            return;
        }
        //size the dialog fragment properly on the phone
        getDialog().getWindow().setLayout(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
    }

    //----------------------------------------
    //------------ Click Events --------------
    //----------------------------------------
    public void btnUpdateStringValue(View v) {

        EditText txtStr = (EditText)view.findViewById(R.id.txt_StringVal);
        manager.saveSettingString(this.getActivity(), "STRINGVAL", txtStr.getText().toString());
        Toast.makeText(getActivity(),"Preference Updated",Toast.LENGTH_SHORT).show();
    }
    public void btnUpdateIntValue(View v) {

        EditText txtInt = (EditText)view.findViewById(R.id.txt_IntVal);

        int myNum = -1;

        try {
            myNum = Integer.parseInt(txtInt.getText().toString());
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }

        manager.saveSettingsInt(this.getActivity(), "INTVAL", myNum);
        Toast.makeText(getActivity(),"Preference Updated",Toast.LENGTH_SHORT).show();
    }
    public void btnCloseClick(View v) {
        getDialog().dismiss();
    }
}
