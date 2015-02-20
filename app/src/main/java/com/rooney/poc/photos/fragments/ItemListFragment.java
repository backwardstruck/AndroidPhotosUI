package com.rooney.poc.photos.fragments;

import android.app.Activity;
import android.app.ListFragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.rooney.poc.photos.activities.MainActivity;
import com.rooney.poc.photos.adapters.ListItemAdapter;
import com.rooney.poc.photos.models.ActivityModel;
import com.rooney.poc.photos.models.ItemContent;
import com.rooney.poc.photos.models.ResponseObject;
import com.rooney.poc.photos.network.GsonRequest;

import java.util.ArrayList;

public class ItemListFragment extends ListFragment implements AbsListView.OnScrollListener{

    private OnFragmentInteractionListener mListener;
    private int mOffset = 0;
    private boolean mNotLoading = false;

    public static ItemListFragment newInstance() {
        ItemListFragment fragment = new ItemListFragment();
        return fragment;
    }

    public ItemListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retain this fragment across configuration changes.
        setRetainInstance(true);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (null != mListener) {
            mListener.onFragmentInteraction(position);
        }
    }

    public interface OnFragmentInteractionListener {
        // send index to detail fragment
        public void onFragmentInteraction(int index);
    }

    @Override
    public void onResume(){
        super.onResume();

        if(mOffset == 0){
            loadMoreData();
            getListView().setOnScrollListener(this);
            ItemContent.ITEMS = new ArrayList<ActivityModel>();
        }

    }

    /**
     * Set deals from JSON
     */
    private void setDealContent(ResponseObject response){
        if((response == null) || (response.activities == null)){
            return;
        }
        ActivityModel[] itemArray = response.activities;
        for (int i = 0; i < itemArray.length; i++){
            ItemContent.ITEMS.add(itemArray[i]);
        }

        if(getListAdapter() == null){
            setListAdapter(new ListItemAdapter(getActivity(), ItemContent.ITEMS,this));
        } else {
            ((ListItemAdapter)getListAdapter()).notifyDataSetChanged();
        }

        mNotLoading = true;

        clearHiddenBitmaps();
        clearHiddenItems();
    }

    private void callToast(String message){
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    /**
     * Retrieve the image
     */
    public void getItemImage(final int index, String imageURL) {

        //proceed with the network request
        ImageLoader.ImageListener listener = new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                //check the image
                Bitmap image = null;
                image = response.getBitmap();
                if (image != null) {
                    if(ItemContent.ITEMS.get(index) != null){
                        ItemContent.ITEMS.get(index).imageBitmap = image;
                        //Successfully loaded image
                        ((ListItemAdapter) getListAdapter()).notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void onErrorResponse(VolleyError error) {
                //something went wrong
                callToast(error.getMessage());
            }
        };

        try {
            ((MainActivity) getActivity()).getImage(imageURL, listener);
        } catch (NullPointerException e) {
            return;
        }


    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem,    int visibleItemCount,    int totalItemCount) {
        if (getListAdapter() != null && firstVisibleItem + visibleItemCount == ItemContent.ITEMS.size()) {
            if(mNotLoading){
                mNotLoading = false;
                //Load more
                loadMoreData();
            }

        }
        if(ItemContent.ITEMS.size() - firstVisibleItem == 80){
            for(int i = 0; i < 40; i ++){
                ItemContent.ITEMS.remove(ItemContent.ITEMS.size()-1);
                ((ListItemAdapter) getListAdapter()).notifyDataSetChanged();
            }
        }
    }

    private void loadMoreData(){
        mOffset += 20;
        String url ="http://ndev-coreapi.citymaps.com/v2/activity/user/8ea239c4-c648-4009-a252-a220e018dc4b/images?offset=" + mOffset + "&limit=20";

        // Request a string response from the provided URL.
        GsonRequest gsonRequest = new GsonRequest(url, ResponseObject.class, null,
                new Response.Listener<ResponseObject>() {

                    @Override
                    public void onResponse(ResponseObject response){
                        setDealContent(response);
                    }



                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                callToast(error.getMessage());
            }
        });


        ((MainActivity)getActivity()).getDealItems(gsonRequest);
    }

    private void clearHiddenBitmaps(){
        try {
            if(ItemContent.ITEMS.size() >= 40){
                for(int i = ItemContent.ITEMS.size() - 40; i < ItemContent.ITEMS.size() - 20; i++){
                    ItemContent.ITEMS.get(i).imageBitmap = null;
                }
                ((ListItemAdapter) getListAdapter()).notifyDataSetChanged();
            }
        } catch (Exception e) {
            return;
        }
    }

    private void clearHiddenItems() {
        if (ItemContent.ITEMS.size() > 80) {
            for(int i = ItemContent.ITEMS.size() - 80; i < ItemContent.ITEMS.size() - 40; i++){
                ItemContent.ITEMS.remove(i);
                ItemContent.ITEMS.add(i, null);
                ((ListItemAdapter) getListAdapter()).notifyDataSetChanged();
            }
        }
    }
}