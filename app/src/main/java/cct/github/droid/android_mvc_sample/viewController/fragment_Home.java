package cct.github.droid.android_mvc_sample.viewController;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import de.greenrobot.event.EventBus;
import cct.github.droid.android_mvc_sample.R;
import cct.github.droid.android_mvc_sample.businessObjects.FragmentRouter;
import cct.github.droid.android_mvc_sample.model.EventModel;


public class fragment_Home extends Fragment {

    View view;

    public fragment_Home() {
    }

    //----------------------------------------
    //------ Android Life Cycle events -------
    //----------------------------------------
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //Fragment started with arguements.. lets do someting with them!
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        //Sample Broadcast -- not binding to button, just setting onclick listener
        view.findViewById(R.id.btn_Sample_Broadcast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { btnClickBroadCastSample(v); }
        });

        //Json Samples -- not binding to button, just setting onclick listener
        view.findViewById(R.id.btn_Json_Calls).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { btnClickJsonCallsSample(v); }
        });

        //Database Samples -- not binding to button, just setting onclick listener
        view.findViewById(R.id.btn_DatabaseSamples).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { btnClickDatabaseCallsSample(v); }
        });

        //Change Fragment Sample -- not binding to button, just setting onclick listener
        view.findViewById(R.id.btn_ChangeFragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { btnClickChangeFragmentSample(v); }
        });

        //Change Imaging Fragment Sample -- not binding to button, just setting onclick listener
        view.findViewById(R.id.btn_ChangeFragmentImaging).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { btnChangeFragmentImaging(v); }
        });

        //Settings/Preferences Sample -- not binding to button, just setting onclick listener
        view.findViewById(R.id.btn_SettingsSample).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { btnSettingsExample(v); }
        });

        //Loading Images Sample -- not binding to button, just setting onclick listener
        view.findViewById(R.id.btn_LoadImgSample).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { btnLoadingImagesExample(v); }
        });

        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    @Override
    public void onStart() {
        super.onStart();
    }
    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this); // EventBus
    }
    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this); // EventBus
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    //----------------------------------------
    //------------ Click Events --------------
    //----------------------------------------
    public void btnClickBroadCastSample(View v) {
        // EventBus - Broadcast a message to all threads listening within the app.. in this
        // sample, we will receive the request below in this thread
        EventBus.getDefault().post(new EventModel(EventModel.EventType.EVENT_BUS_MESSAGE_TEST));
    }
    public void btnClickJsonCallsSample(View v) {
        // We will open a modal window to demonstrate multiple ways to request JSON (GET/POST)
        dialogFragment_JsonSample dialog = dialogFragment_JsonSample.newInstance();
        dialog.show(getChildFragmentManager(), "sampleJson");
    }
    public void btnClickDatabaseCallsSample(View v) {
        // We will open a modal window to demonstrate multiple ways to request JSON (GET/POST)
        dialogFragment_databaseSampleFragment dialog = dialogFragment_databaseSampleFragment.newInstance();
        dialog.show(getChildFragmentManager(),"sampleDb");
    }
    public void btnClickChangeFragmentSample(View v) {
        // We will open a new fragment and auto close this one as well as pass a parameter along with the change
        Bundle args = new Bundle();
        args.putString("key1", "test val");
        FragmentRouter.replace(getFragmentManager(), R.id.container, FragmentRouter.Tag.EXAMPLE_FRAGMENT, args, FragmentRouter.Animation.SLIDE_IN_BOTTOM);
    }
    public void btnChangeFragmentImaging(View v) {
        // Opening the Imaging Fragment
        FragmentRouter.replace(getFragmentManager(), R.id.container, FragmentRouter.Tag.EXAMPLE_IMAGING, null, FragmentRouter.Animation.SLIDE_IN_BOTTOM);
    }
    public void btnSettingsExample(View v) {
        // We will open a modal window to demonstrate how to load/save settings and preferences
        dialogFragment_settingsSampleFragment dialog = dialogFragment_settingsSampleFragment.newInstance();
        dialog.show(getFragmentManager(), "sampleSettings");
    }
    public void btnLoadingImagesExample(View v) {
        // We will open a new fragment to demonstrate the Picasso library
        Bundle args = new Bundle();
        FragmentRouter.replace(getFragmentManager(), R.id.container, FragmentRouter.Tag.EXAMPLE_IMG_LOADING, args, FragmentRouter.Animation.SLIDE_IN_BOTTOM);
    }

    //----------------------------------------
    //--------- Listener(EventBus) -----------
    //----------------------------------------
    //EventBus method for recieving events from other threads/fragments -- used in place of broadcast recievers/intents/listeners
    // ultimately this helps with creating cleaner code quicker
    public void onEventMainThread(EventModel event) {
        if (event.getEventType() == EventModel.EventType.EVENT_BUS_MESSAGE_TEST) {
            Toast.makeText(this.getActivity(), getResources().getString(R.string._txt_EventBus_Sample_Message), Toast.LENGTH_SHORT).show();
        }
    }
}
