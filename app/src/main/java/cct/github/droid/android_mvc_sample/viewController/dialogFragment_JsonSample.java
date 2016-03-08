package cct.github.droid.android_mvc_sample.viewController;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import cct.github.droid.android_mvc_sample.R;
import cct.github.droid.android_mvc_sample.model.listAdapters.postItemEntityAdapter;
import cct.github.droid.android_mvc_sample.model.newPostModel;
import cct.github.droid.android_mvc_sample.model.postItemEntity;
import cct.github.droid.android_mvc_sample.model.postItemModel;

public class dialogFragment_JsonSample extends DialogFragment {

    //model for retrieving posts
    postItemModel pm = new postItemModel();
    //model for creating posts
    newPostModel pmn =  new newPostModel();
    //List for entities loaded into my ListView
    List<postItemEntity> list =  new ArrayList<>();
    //mechanism to take a list an apply it to my ListView
    postItemEntityAdapter postitemEntityAdapter;
    //List View in a public space so it can be called from other methods
    ListView lst_JsonSample;
    //InProgress Dialog
    private ProgressDialog dialog;

    public static dialogFragment_JsonSample newInstance() {
        dialogFragment_JsonSample newfrag = new dialogFragment_JsonSample();
        return newfrag;
    }

    //----------------------------------------
    //------ Android Life Cycle events -------
    //----------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(STYLE_NO_TITLE);  //remove the title bar
        View view = inflater.inflate(R.layout.dialogfragment_jsonsample, container);

        //Sample Broadcast -- not binding to button, just setting onclick listener
        view.findViewById(R.id.btn_JsonGET).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { btnClickJsonGet(v); }
        });

        //Json Samples -- not binding to button, just setting onclick listener
        view.findViewById(R.id.btn_JsonPOST).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { btnJsonPost(v); }
        });

        //Populate JSON to List Example - just setting onclick listener
        view.findViewById(R.id.btn_PopulateList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPopulateList(v); }
        });

        //modal close button
        view.findViewById(R.id.btn_close_JsonSamples).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { btnCloseClick(v); }
        });

        //binding listview
        lst_JsonSample = (ListView)view.findViewById(R.id.listView_Results);

        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //bind our list adapter
        postitemEntityAdapter = new postItemEntityAdapter(
                getActivity(),
                R.layout.adapter_postitem_list,
                list
        );
        lst_JsonSample.setAdapter(postitemEntityAdapter);
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
        EventBus.getDefault().register(this); // EventBus
    }
    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this); // EventBus
    }

    //----------------------------------------
    //------------ Click Events --------------
    //----------------------------------------
    public void btnClickJsonGet(View v) {
        //initiate a GET request to our server
        if (!pm.isBusy()) {
            //no action is in progress.. lets proceed
            //show a loading dialog
            openLoadingDialog();
            //this is an async process, we will recieve the completion action using EventBus
            pm.load(null);
        } else  {
            //a request is already in progress
            Toast.makeText(getActivity(), getResources().getString(R.string._txt_App_Busy_JsonRequest), Toast.LENGTH_SHORT).show();
        }
    }
    public void btnJsonPost(View v) {
        //initiate a POST request to our server
        //Here we will be sending data to the server to create a new post
        if (!pmn.isBusy()) {
            //show a loading dialog
            openLoadingDialog();
            //create a fake post to send to the server
            postItemEntity newpost = new postItemEntity();
            newpost.userId = 1;
            newpost.title = "test title";
            newpost.body = "test body";
            //send the post to the server, this runs asynchronously so we will wait for the results via eventbus
            pmn.addpost(newpost);
        } else  {
            //a request is already in progress
            Toast.makeText(getActivity(), getResources().getString(R.string._txt_App_Busy_JsonRequest), Toast.LENGTH_SHORT).show();
        }
    }
    public void btnPopulateList(View v) {
        list.clear();
        if (pm.getItemCount() > 0)
            list.addAll(pm.getItemList().subList(1,20)); //100 have been returned .. not loading all 100 in this sample
        postitemEntityAdapter.notifyDataSetChanged();
    }
    public void btnCloseClick(View v) {
        getDialog().dismiss();
    }

    //----------------------------------------
    //--------- Listener(EventBus) -----------
    //----------------------------------------
    //EventBus method for recieving events from other threads/fragments -- used in place of broadcast recievers/intents/listeners
    // ultimately this helps with creating cleaner code quicker
    public void onEventMainThread(postItemModel.PostItemLoadedEvent pthread) {

        //close the loading dialog
        closeLoadingDialog();

        if (pthread.isSuccess())
            Toast.makeText(this.getActivity(), "Successfully loaded " + pm.getItemCount()  + " items", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this.getActivity(), getResources().getString(R.string._txt_App_Error_GenericJson), Toast.LENGTH_SHORT).show();
    }
    public void onEventMainThread(newPostModel.PostItemCreatedEvent pthread) {

        //close the loading dialog
        closeLoadingDialog();

        //if it is successful then the server assigned id will be populated in the background
        if (pthread.isSuccess())
            Toast.makeText(this.getActivity(), "Successfully created post - new id: " + pmn.getPostItem().id, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this.getActivity(), getResources().getString(R.string._txt_App_Error_GenericJson), Toast.LENGTH_SHORT).show();
    }

    //------------------------------------------------------------------
    //--------- Private Methods (Support for Interface only) -----------
    //------------------------------------------------------------------
    private void openLoadingDialog() {
        dialog = new ProgressDialog(this.getActivity());
        dialog.setMessage(getResources().getString(R.string._txt_App_Loading_Message_1));
        dialog.setCancelable(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
    }
    private void closeLoadingDialog() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }
}
