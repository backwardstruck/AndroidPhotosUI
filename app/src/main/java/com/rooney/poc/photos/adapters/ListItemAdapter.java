package com.rooney.poc.photos.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rooney.poc.photos.R;
import com.rooney.poc.photos.models.ActivityModel;

import java.util.List;

public class ListItemAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<ActivityModel> activityModels;
    private Context context;


    public static ListItemAdapter newInstance(Context context, List<ActivityModel> items) {
        return new ListItemAdapter(context, items);
    }

    public ListItemAdapter(Context ctx, List<ActivityModel> items) {
        super();
        context = ctx;
        if(context != null){
            inflater = LayoutInflater.from(context);
        }
        activityModels = items;
    }

    @Override
    public int getCount() {
        return activityModels.size();
    }

    @Override
    public Object getItem(int position){
        try {
            return activityModels.get(position);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;
        if(convertView == null) {
            view = inflater.inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder();
            holder.userImage = (ImageView)view.findViewById(R.id.list_item_image_view);
            holder.title = (TextView)view.findViewById(R.id.list_item_title);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder)view.getTag();
        }

        //set values from data
        ActivityModel activityModel = activityModels.get(position);
        holder.userImage.setImageBitmap(activityModel.imageBitmap);
        holder.title.setText(activityModel.created_at);

        return view;
    }

    private class ViewHolder {
        public ImageView userImage;
        public TextView title, price;
    }
}
