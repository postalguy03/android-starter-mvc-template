package cct.github.droid.android_mvc_sample.viewController;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cct.github.droid.android_mvc_sample.R;
import cct.github.droid.android_mvc_sample.businessObjects.FragmentRouter;

public class fragment_Example1 extends Fragment {

    Bundle args;

    public fragment_Example1() {
    }

    //----------------------------------------
    //------ Android Life Cycle events -------
    //----------------------------------------
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Fragment started with arguements.. lets save them for now and
            // work with them later in the fragment life cycle!
            args = getArguments();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_example1, container, false);

        //Change Fragment -- not binding to button, just setting onclick listener
        view.findViewById(R.id.btn_BackToMainFrag).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBackToMainFrag(v);
            }
        });

        return view;
    }

    //----------------------------------------
    //------------ Click Events --------------
    //----------------------------------------
    public void btnBackToMainFrag(View v) {
      //  Bundle args = new Bundle();
      //  args.putString(Const.BundleKey.URL.toString(), myList.get(position).url);
        FragmentRouter.replace(getFragmentManager(), R.id.container, FragmentRouter.Tag.HOME_FRAGMENT, args, FragmentRouter.Animation.FADE_IN);
    }
}
