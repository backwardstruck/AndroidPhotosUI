package com.rooney.poc.photos.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rooney.poc.photos.R;
import com.rooney.poc.photos.models.ItemModel;

import java.util.List;

public class ListItemAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<ItemModel> itemModels;
    private Context context;


    public static ListItemAdapter newInstance(Context context, List<ItemModel> items) {
        return new ListItemAdapter(context, items);
    }

    public ListItemAdapter(Context ctx, List<ItemModel> items) {
        super();
        context = ctx;
        if(context != null){
            inflater = LayoutInflater.from(context);
        }
        itemModels = items;
    }

    @Override
    public int getCount() {
        return itemModels.size();
    }

    @Override
    public Object getItem(int position){
        try {
            return itemModels.get(position);
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
            holder.productImage = (ImageView)view.findViewById(R.id.deal_list_item_image_view);
            holder.title = (TextView)view.findViewById(R.id.deal_list_item_title);
            holder.price = (TextView)view.findViewById(R.id.deal_list_item_price);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder)view.getTag();
        }

        //set values from data
        ItemModel itemModel = itemModels.get(position);
        holder.productImage.setImageBitmap(itemModel.imageBitmap);
        holder.title.setText(itemModel.title);
        String price = null;
        if(itemModel.salePrice != null){
            price = itemModel.salePrice;
        } else {
            price = itemModel.price;
        }
        if(price != null){
            holder.price.setText(price);
        }

        return view;
    }

    private class ViewHolder {
        public ImageView productImage;
        public TextView title, price;
    }
}
