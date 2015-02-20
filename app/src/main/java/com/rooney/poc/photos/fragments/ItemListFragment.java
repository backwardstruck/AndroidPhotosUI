package com.rooney.poc.photos.fragments;

import android.app.Activity;
import android.app.ListFragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.rooney.poc.photos.activities.MainActivity;
import com.rooney.poc.photos.adapters.ListItemAdapter;
import com.rooney.poc.photos.models.ItemContent;
import com.rooney.poc.photos.models.ItemModel;
import com.rooney.poc.photos.models.ResponseObject;
import com.rooney.poc.photos.network.GsonRequest;

import java.util.ArrayList;

public class ItemListFragment extends ListFragment {

    private OnFragmentInteractionListener mListener;

    public static ItemListFragment newInstance() {
        ItemListFragment fragment = new ItemListFragment();
        return fragment;
    }

    public ItemListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


        String url ="https://api.myjson.com/bins/3ld7j";




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

    /**
     * Set deals from JSON
     */
    private void setDealContent(ResponseObject response){
        ArrayList<ItemModel> items = new ArrayList<ItemModel>();
        if((response == null) || (response.data == null)){
            return;
        }
        ItemModel[] itemArray = response.data;
        for (int i = 0; i < itemArray.length; i++){
            items.add(itemArray[i]);
        }
        ItemContent.ITEMS = items;

        setListAdapter(new ListItemAdapter(getActivity(), ItemContent.ITEMS));

        //get the images
        getDealItemImages();

    }

    private void callToast(String message){
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    /**
     * Retrieve the thumbnails
     */
    private void getDealItemImages(){
        String imageURL = null;
        if(ItemContent.ITEMS != null){
            for(int i = 0; i < ItemContent.ITEMS.size(); i++){
                if(ItemContent.ITEMS.get(i) != null){
                    if(ItemContent.ITEMS.get(i).image != null){
                        imageURL = ItemContent.ITEMS.get(i).image;

                        //proceed with the network request
                        final int index = i;
                        ImageLoader.ImageListener listener = new ImageLoader.ImageListener() {
                            @Override
                            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                                //check the image
                                Bitmap image = null;
                                image = response.getBitmap();
                                if(image != null){
                                    ItemContent.ITEMS.get(index).imageBitmap = image;
                                    //Successfully loaded image
                                    ((ListItemAdapter)getListAdapter()).notifyDataSetChanged();
                                }

                            }

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //something went wrong
                                callToast(error.getMessage());
                            }
                        };

                        try {
                            ((MainActivity)getActivity()).getImage(imageURL, listener);
                        } catch (NullPointerException e) {
                            return;
                        }

                    }
                }
            }

        }





    }


}