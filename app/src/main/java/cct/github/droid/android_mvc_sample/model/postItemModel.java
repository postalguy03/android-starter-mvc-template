package cct.github.droid.android_mvc_sample.model;


import android.os.AsyncTask;

import org.apache.http.NameValuePair;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import cct.github.droid.android_mvc_sample.data.JSONParser;
import cct.github.droid.android_mvc_sample.businessObjects.Const;

public class postItemModel {

    private final List<postItemEntity> mPostItemList = new ArrayList<>();
    private int mItemCount = 0;
    private boolean mIsBusy = false;

    public postItemModel() {
    }

    public List<postItemEntity> getItemList() {
        return mPostItemList;
    }

    public int getItemCount() {
        return mItemCount;
    }

    public boolean isBusy() { return mIsBusy; }

    public void load(final ArrayList<NameValuePair> pairs) {

        //It is not nothing if busy
        if (mIsBusy) {
            return;
        }
        // Issue the API asynchronously
        new AsyncTask<Void, Void, List<postItemEntity>>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // To the busy state
                mIsBusy = true;
            }

            @Override
            protected List<postItemEntity> doInBackground(Void... voids) {
                JSONParser jsonParser = new JSONParser();
                return jsonParser.getGetObjFromUrl(Const.APP_POSTS);
            }

            @Override
            protected void onPostExecute(List<postItemEntity> result) {
                super.onPostExecute(result);
                // And update the list in obtaining the results (see the maintenance )
                mPostItemList.clear();
                if (result != null) {
                    mPostItemList.addAll(result);
                    // Update number
                    mItemCount = result.size();
                } else
                    mItemCount = 0;
                // And releasing the busy state
                mIsBusy = false;
                // EventBus - It will send a completion notification in
                EventBus.getDefault().post(new PostItemLoadedEvent(true));
            }
        }.execute();
    }

    // You can throw it packed a value to the event ( this time success / failure flag only )
    public static class PostItemLoadedEvent {

        private boolean isSuccess;

        private PostItemLoadedEvent(boolean isSuccess) {
            this.isSuccess = isSuccess;
        }

        public boolean isSuccess() {
            return isSuccess;
        }
    }
}