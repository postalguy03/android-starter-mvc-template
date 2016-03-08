package cct.github.droid.android_mvc_sample;

import android.app.Application;

import cct.github.droid.android_mvc_sample.businessObjects.FragmentRouter;
import cct.github.droid.android_mvc_sample.viewController.fragment_Example1;
import cct.github.droid.android_mvc_sample.viewController.fragment_Home;
import cct.github.droid.android_mvc_sample.viewController.fragment_Imaging;
import cct.github.droid.android_mvc_sample.viewController.fragment_LoadingImages;

public class MainApplication extends Application {

    //modeled after https://github.com/hkusu/android-mvc-sample

    @Override
    public void onCreate() {
        super.onCreate();

        // FragmentManager Register the class name of each Fragment to (do not instantiate here )
        FragmentRouter.register(FragmentRouter.Tag.HOME_FRAGMENT, fragment_Home.class );
        FragmentRouter.register(FragmentRouter.Tag.EXAMPLE_FRAGMENT, fragment_Example1.class);
        FragmentRouter.register(FragmentRouter.Tag.EXAMPLE_IMAGING, fragment_Imaging.class);
        FragmentRouter.register(FragmentRouter.Tag.EXAMPLE_IMG_LOADING, fragment_LoadingImages.class);

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
