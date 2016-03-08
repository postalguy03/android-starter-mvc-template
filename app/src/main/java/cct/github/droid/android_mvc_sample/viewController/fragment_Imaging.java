package cct.github.droid.android_mvc_sample.viewController;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import cct.github.droid.android_mvc_sample.R;
import cct.github.droid.android_mvc_sample.businessObjects.FragmentRouter;
import cct.github.droid.android_mvc_sample.businessObjects.ImageFunctions;

public class fragment_Imaging  extends Fragment {

    Bundle args;
    ImageFunctions imageFunctions;

    public fragment_Imaging() {
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

        View view = inflater.inflate(R.layout.fragment_imaging, container, false);

        //Change Fragment -- not binding to button, just setting onclick listener
        view.findViewById(R.id.btn_imaging_goBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBackToMainFrag(v);
            }
        });

        // Bring our class to life
        imageFunctions = new ImageFunctions();
        // Load our sample Bitmap from a resource file (embeded in the app under "Drawable"
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.happygilmore);

        // Populate our first image - Unaltered
        ImageView img_test1 = (ImageView)view.findViewById(R.id.img_test1);
        img_test1.setImageBitmap(bm);

        // Bind and Populate our second image, this one is just scaling our original image from 200x200 to 100x100
        ImageView img_test2 = (ImageView)view.findViewById(R.id.img_test2);
        img_test2.setImageBitmap(imageFunctions.giveRoundedSquarePic(bm, 100));

        // Bind and Populate our second image, this one is just scaling our original image from 200x200 to 100x100
        ImageView img_test3 = (ImageView)view.findViewById(R.id.img_test3);
        img_test3.setImageBitmap(imageFunctions.giveCirclePic(bm, 100));

        imageFunctions = null;

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
