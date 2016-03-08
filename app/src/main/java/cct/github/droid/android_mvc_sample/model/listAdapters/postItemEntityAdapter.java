package cct.github.droid.android_mvc_sample.model.listAdapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.codehaus.jackson.map.ObjectMapper;

import java.util.HashMap;
import java.util.List;

import cct.github.droid.android_mvc_sample.R;
import cct.github.droid.android_mvc_sample.model.postItemEntity;

public class postItemEntityAdapter  extends ArrayAdapter<postItemEntity> {

    private Activity mActivity;
    private LayoutInflater mLayoutInflater;
    private int mResource;

    //used to cast LinkedHashMap back to Entity
    ObjectMapper mapper = new ObjectMapper();

    //constructor
    public postItemEntityAdapter(Context context, int resource, List<postItemEntity> objects) {
        super(context, resource, objects);
        mActivity = (Activity) context;
        mLayoutInflater = LayoutInflater.from(context);
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = mLayoutInflater.inflate(mResource, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        postItemEntity p = mapper.convertValue(getItem(position), postItemEntity.class);

        /*Picasso.with(mActivity)
                .load(qiitaItemEntity.user.profileImageUrl)
                .resize(100, 100)
                .centerCrop()
                .into(viewHolder.mProfileImageView); */
        viewHolder.mTitleTextView.setText(p.title);

        return convertView;
    }

    static class ViewHolder {

       // ImageView mProfileImageView;
        TextView mTitleTextView;

        ViewHolder(View view) {
            mTitleTextView = (TextView)view.findViewById(R.id.lbl_postitem_title);
        }

    }
}