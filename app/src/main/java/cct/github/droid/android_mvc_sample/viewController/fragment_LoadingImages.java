package cct.github.droid.android_mvc_sample.viewController;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import cct.github.droid.android_mvc_sample.R;
import cct.github.droid.android_mvc_sample.businessObjects.FragmentRouter;

public class fragment_LoadingImages extends Fragment {

    Bundle args;
    String imgToLoadStr = "http://columbuscomputertraining.com/wp-content/uploads/2016/01/java.jpg";
    ImageView imgToLoad;

    public fragment_LoadingImages() {
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

        View view = inflater.inflate(R.layout.fragment_loadingimages, container, false);

        //Load Image -- not binding to button, just setting onclick listener
        view.findViewById(R.id.btn_loadImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLoadImage(v);
            }
        });

        //Change Fragment -- not binding to button, just setting onclick listener
        view.findViewById(R.id.btn_imageloading_goBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBackToMainFrag(v);
            }
        });

        //bind to the imageview which we want to load the image into
        imgToLoad = (ImageView)view.findViewById(R.id.img_targetToLoad);

        return view;
    }

    //----------------------------------------
    //------------ Click Events --------------
    //----------------------------------------
    public void btnBackToMainFrag(View v) {
        //  Bundle args = new Bundle();
        //  args.putString(Const.BundleKey.URL.toString(), object.get(position).url);
        FragmentRouter.replace(getFragmentManager(), R.id.container, FragmentRouter.Tag.HOME_FRAGMENT, args, FragmentRouter.Animation.FADE_IN);
    }
    public void btnLoadImage(View v) {

        //Convert our string with the path to an object that the code can understand
        Uri imgurl = Uri.parse(imgToLoadStr);

        //Let the Picasso library handle the rest... we can claim we did a lot of hard work here to others
        Picasso.with(getActivity()).load(imgurl).into(imgToLoad);

    }


}
