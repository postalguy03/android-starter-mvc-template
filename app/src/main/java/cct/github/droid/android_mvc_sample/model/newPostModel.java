package cct.github.droid.android_mvc_sample.model;

import android.os.AsyncTask;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import de.greenrobot.event.EventBus;
import cct.github.droid.android_mvc_sample.data.JSONParser;
import cct.github.droid.android_mvc_sample.businessObjects.Const;

public class newPostModel {

    private postItemEntity mPostItem = new postItemEntity();
    private boolean mIsBusy = false;

    //used to cast LinkedHashMap back to Entity
    ObjectMapper mapper = new ObjectMapper();

    public newPostModel() {
    }

    public postItemEntity getPostItem() {
        return mPostItem;
    }

    public boolean isBusy() { return mIsBusy; }

    public void addpost(postItemEntity _mPostItem) {

        //the new entity we want to send to the server
        mPostItem = _mPostItem;

        //prepare parameters we are sending to the server
        final JSONObject params = new JSONObject();
        try {
            params.put("title", mPostItem.title);
            params.put("body", mPostItem.body);
            params.put("userId", mPostItem.userId);
        } catch (Exception ex) {
            //unable to create our parameters.. don't do anything else
            EventBus.getDefault().post(new EventModel(EventModel.EventType.EVENT_BUS_MESSAGE_FAILED_JSON_REQUEST));
            return;
        }

        //It is not nothing if busy
        if (mIsBusy) {
            return;
        }
        // Issue the API asynchronously
        new AsyncTask<Void, Void, postItemEntity>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // To the busy state
                mIsBusy = true;
            }

            @Override
            protected postItemEntity doInBackground(Void... voids) {
                JSONParser jsonParser = new JSONParser();
                jsonParser.addParameters(params);
                return mapper.convertValue(jsonParser.getPostObjFromUrl(Const.APP_POSTS), postItemEntity.class);
            }

            @Override
            protected void onPostExecute(postItemEntity result) {
                super.onPostExecute(result);

                if (result != null) {
                    // append the new ID to this entity -- ID assigned by the server
                    mPostItem.id = result.id;
                    // And releasing the busy state
                    mIsBusy = false;
                    // EventBus - It will send a completion notification in
                    EventBus.getDefault().post(new PostItemCreatedEvent(true));
                } else
                    EventBus.getDefault().post(new PostItemCreatedEvent(false));
            }
        }.execute();
    }

    // You can throw it packed a value to the event ( this time success / failure flag only )
    public static class PostItemCreatedEvent {

        private boolean isSuccess;

        private PostItemCreatedEvent(boolean isSuccess) {
            this.isSuccess = isSuccess;
        }

        public boolean isSuccess() {
            return isSuccess;
        }
    }
}
