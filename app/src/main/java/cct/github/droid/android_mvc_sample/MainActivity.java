package cct.github.droid.android_mvc_sample;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import de.greenrobot.event.EventBus;
import cct.github.droid.android_mvc_sample.businessObjects.FragmentRouter;
import cct.github.droid.android_mvc_sample.model.EventModel;
import cct.github.droid.android_mvc_sample.viewController.dialogFragment_JsonSample;
import cct.github.droid.android_mvc_sample.viewController.dialogFragment_databaseSampleFragment;
import cct.github.droid.android_mvc_sample.viewController.dialogFragment_settingsSampleFragment;

public class MainActivity extends ActionBarActivity {

    //Sliding Menu -  Variables
    DrawerLayout drawerLayout;
    private ListView drawerList;
    String[] drawerMenuItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Sliding Menu - Binding to objects
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerList = (ListView) findViewById(R.id.drawerList);
        drawerMenuItems = this.getResources().getStringArray(R.array.menu_items);
        drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.parts_drawer_list_item, drawerMenuItems));
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                closingMenu();
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //determine which element we selected
                String itemSelected = drawerMenuItems[position];
                //lets close the menu and then take action on this selected menu item
                closingMenu();  //workaround to bring app back forward
                drawerLayout.closeDrawer(Gravity.LEFT);  //closing the menu

                if (itemSelected.equals("Inner App Broadcast")) {
                    // EventBus - Broadcast a message to all threads listening within the app.. in this
                    // sample, we will receive the request below in this thread
                    EventBus.getDefault().post(new EventModel(EventModel.EventType.EVENT_BUS_MESSAGE_TEST));
                } else if (itemSelected.equals("JSON Examples")) {
                    // We will open a modal window to demonstrate multiple ways to request JSON (GET/POST)
                    dialogFragment_JsonSample dialog = dialogFragment_JsonSample.newInstance();
                    dialog.show(getSupportFragmentManager(), "sampleJson");
                } else if (itemSelected.equals("Database Examples")) {
                    // We will open a modal window to demonstrate multiple ways to request JSON (GET/POST)
                    dialogFragment_databaseSampleFragment dialog = dialogFragment_databaseSampleFragment.newInstance();
                    dialog.show(getSupportFragmentManager(),"sampleDb");
                } else if (itemSelected.equals("Change Fragment Example")) {
                    // We will open a new fragment and auto close this one as well as pass a parameter along with the change
                    Bundle args = new Bundle();
                    args.putString("key1", "test val");
                    FragmentRouter.replace(getSupportFragmentManager(), R.id.container, FragmentRouter.Tag.EXAMPLE_FRAGMENT, args, FragmentRouter.Animation.SLIDE_IN_BOTTOM);
                } else if (itemSelected.equals("Imaging Example")) {
                    // Opening the Imaging Fragment
                    FragmentRouter.replace(getSupportFragmentManager(), R.id.container, FragmentRouter.Tag.EXAMPLE_IMAGING, null, FragmentRouter.Animation.SLIDE_IN_BOTTOM);
                } else if (itemSelected.equals("App Settings Example")) {
                    // We will open a modal window to demonstrate how to load/save settings and preferences
                    dialogFragment_settingsSampleFragment dialog = dialogFragment_settingsSampleFragment.newInstance();
                    dialog.show(getSupportFragmentManager(),"sampleSettings");
                } else if (itemSelected.equals("Loading Images")) {
                    // We will open a new fragment to demonstrate the Picasso library
                    Bundle args = new Bundle();
                    FragmentRouter.replace(getSupportFragmentManager(), R.id.container, FragmentRouter.Tag.EXAMPLE_IMG_LOADING, args, FragmentRouter.Animation.SLIDE_IN_BOTTOM);
                }
            }
        });

        if (savedInstanceState == null) {
            FragmentRouter.replace(getSupportFragmentManager(), R.id.container, FragmentRouter.Tag.HOME_FRAGMENT, null, FragmentRouter.Animation.NON, false);
        }

    }

    //Sliding Menu - Methods
    private void openMenu() {
        //we need to bring this to front -- this is not normal behavoir, it is a work around
        //until I can resolve this issue with the Fragment manager class which ultimately places the menu in the background
        drawerLayout.bringToFront();
        drawerLayout.openDrawer(Gravity.LEFT);
    }
    private void closingMenu() {
        //if the menu is closing, lets move our sliding menu to the background
        //this is a workaround for the time being, i will have a fix in subsequent versions
        ViewGroup myViewGroup = ((ViewGroup) drawerLayout.getParent());
        int index = myViewGroup.indexOfChild(drawerLayout);
        for(int i = 0; i<index; i++)
            myViewGroup.bringChildToFront(myViewGroup.getChildAt(i));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        //Sliding Menu - if the android menu button is tapped, lets open the slideout menu (or close it)
        if (event.getKeyCode() == KeyEvent.KEYCODE_MENU) {
            if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                closingMenu();
                drawerLayout.closeDrawer(Gravity.LEFT);
            } else {
                openMenu();
            }
        }

        return super.onKeyDown(keyCode, event);
    }
}
