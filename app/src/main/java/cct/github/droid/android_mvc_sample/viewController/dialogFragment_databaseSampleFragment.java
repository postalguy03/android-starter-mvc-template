package cct.github.droid.android_mvc_sample.viewController;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import cct.github.droid.android_mvc_sample.R;
import cct.github.droid.android_mvc_sample.data.DatabaseHandler;
import cct.github.droid.android_mvc_sample.model.listAdapters.postItemEntityAdapter;
import cct.github.droid.android_mvc_sample.model.postItemEntity;

public class dialogFragment_databaseSampleFragment extends DialogFragment {

    List<postItemEntity> posts  = new ArrayList<>();
    ListView lst_DB;
    TextView txt_DB_Status;
    DatabaseHandler db;
    postItemEntityAdapter postitemEntityAdapter;

    public static dialogFragment_databaseSampleFragment newInstance() {
        dialogFragment_databaseSampleFragment newfrag = new dialogFragment_databaseSampleFragment();
        return newfrag;
    }

    //----------------------------------------
    //------ Android Life Cycle events -------
    //----------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(STYLE_NO_TITLE);  //remove the title bar
        View view = inflater.inflate(R.layout.dialogfragment_databasesample, container);

        //bind to an existing instance (If it is available)
        db = DatabaseHandler.getmInstance(getActivity());

        //Add New Record -- not binding to button, just setting onclick listener
        view.findViewById(R.id.btn_String_Val).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { btnAddNewRecord(v); }
        });

        //Clear the DB Table -- not binding to button, just setting onclick listener
        view.findViewById(R.id.btn_DB_Clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { btnClearDB(v); }
        });

        //Populate List with DB Results - just setting onclick listener
        view.findViewById(R.id.btn_DB_Refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRefeshList(v); }
        });

        //modal close button
        view.findViewById(R.id.btn_DB_Close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { btnCloseClick(v); }
        });

        //binding listview
        lst_DB = (ListView)view.findViewById(R.id.lst_DB);
        //binding TextView
        txt_DB_Status = (TextView)view.findViewById(R.id.lbl_DB_Status);

        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //bind our list adapter
        postitemEntityAdapter = new postItemEntityAdapter(
                getActivity(),
                R.layout.adapter_postitem_list,
                posts
        );
        lst_DB.setAdapter(postitemEntityAdapter);
        btnRefeshList(null);
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
    public void btnAddNewRecord(View v) {

        //Add a new record to the database
        postItemEntity e = new postItemEntity();
        // get a random ID.. does not matter if it already exists, our database
        // logic will treat it as an update
        e.id = 5 + (int)(Math.random() * ((10 - 5) + 1));
        e.userId = 1;
        e.title = UUID.randomUUID().toString(); //not pretty but the content does not matter for this demo
        e.body = UUID.randomUUID().toString();

        db.postItem_Add(e);

        Toast.makeText(getActivity(),"Record Added",Toast.LENGTH_SHORT).show();

    }
    public void btnClearDB(View v) {
        //initiate a POST request to our server
        db.postItem_ClearAll();
        Toast.makeText(getActivity(),"Table Cleared",Toast.LENGTH_SHORT).show();
        btnRefeshList(null);
    }
    public void btnRefeshList(View v) {

        posts.clear();
        posts.addAll(db.postItem_getAll());
        txt_DB_Status.setText(posts.size() + " records");
        postitemEntityAdapter.notifyDataSetChanged();
    }
    public void btnCloseClick(View v) {
        getDialog().dismiss();
    }
}
