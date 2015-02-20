package com.rooney.poc.photos.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rooney.poc.photos.R;
import com.rooney.poc.photos.fragments.ItemListFragment;
import com.rooney.poc.photos.models.ActivityModel;
import com.rooney.poc.photos.models.ItemContent;

import java.util.List;

public class ListItemAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<ActivityModel> activityModels;
    private Context context;
    private ItemListFragment fragment;


    public static ListItemAdapter newInstance(Context context, List<ActivityModel> items, ItemListFragment fragment) {
        return new ListItemAdapter(context, items, fragment);
    }

    public ListItemAdapter(Context ctx, List<ActivityModel> items, ItemListFragment fragment) {
        super();
        context = ctx;
        if(context != null){
            inflater = LayoutInflater.from(context);
        }
        activityModels = items;
        this.fragment = fragment;
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
            holder.name = (TextView)view.findViewById(R.id.list_item_name);
            holder.progressBar1 = (ProgressBar)view.findViewById(R.id.progressBar1);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder)view.getTag();
        }

        //set values from data
        ActivityModel activityModel = activityModels.get(position);
        if(activityModel.imageBitmap != null){
            holder.userImage.setImageBitmap(activityModel.imageBitmap);
            holder.progressBar1.setVisibility(View.GONE);
            holder.userImage.setVisibility(View.VISIBLE);

            if(holder.userImage.getTag() == null){
                Animation animationFadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in);
                holder.userImage.startAnimation(animationFadeIn);
                holder.userImage.setTag(new Object());
            }


        } else {
            holder.userImage.setTag(null);
            holder.userImage.setVisibility(View.GONE);
            holder.progressBar1.setVisibility(View.VISIBLE);
            if(ItemContent.ITEMS.get(position) != null) {
                if (ItemContent.ITEMS.get(position).image_url[0] != null) {
                    String imageURL = null;
                    imageURL = ItemContent.ITEMS.get(position).image_url[0];
                    fragment.getItemImage(position, imageURL);
                }
            }
        }

        if(activityModel.direct_object.name != null){
            holder.title.setText(activityModel.direct_object.name);
        }

        if(activityModel.user != null){
            holder.name.setText(activityModel.user.username);
        }

        return view;
    }

    private class ViewHolder {
        public ImageView userImage;
        public TextView title, name;
        public ProgressBar progressBar1;
    }
}
