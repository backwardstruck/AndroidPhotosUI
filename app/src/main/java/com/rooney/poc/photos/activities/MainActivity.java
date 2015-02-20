package com.rooney.poc.photos.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.rooney.poc.photos.R;
import com.rooney.poc.photos.fragments.ItemDetailFragment;
import com.rooney.poc.photos.fragments.ItemListFragment;
import com.rooney.poc.photos.network.GsonRequest;
import com.rooney.poc.photos.network.NetworkQueue;

import java.io.ByteArrayOutputStream;

public class MainActivity extends Activity implements ItemListFragment.OnFragmentInteractionListener {

    private RequestQueue queue;
    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new ItemListFragment())
                    .commit();
        }


        //use singleton
        // Get a RequestQueue
        RequestQueue queue = NetworkQueue.getInstance(this.getApplicationContext()).
                getRequestQueue();

        getActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Create about option
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu, menu);


        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);

        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) item.getActionProvider();


        return true;
    }

    // Call to update the share intent
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_about) {
            String versionName = "";
            try {
                versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                return true;
            }
            versionName = getString(R.string.version) + " " + versionName;
            Toast.makeText(this, versionName, Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.menu_item_share) {
            //check for image to share
            ImageView deal_list_item_image_view = (ImageView) findViewById(R.id.deal_detail_image_view);
            if (deal_list_item_image_view != null) {
                //convert to bitmap
                Bitmap image = null;
                try {
                    image = ((BitmapDrawable) deal_list_item_image_view.getDrawable()).getBitmap();
                } catch (Exception e) {
                    return true;
                }
                if (image != null) {
                    sharePicture(image);
                }
                return true;
            } else {
                //share text
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.hash_tag));
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                return true;
            }
        } else if (id == android.R.id.home) {
            getFragmentManager().popBackStack();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(int index) {
        // new detail fragment.
        //provide index to detail fragment
        Fragment newFragment = ItemDetailFragment.newInstance(index);


        // add to back stack.
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.container, newFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        ft.commit();

    }


    @Override
    public void onResume() {
        super.onResume();

    }


    /**
     * Get deals from JSON
     */
    public void getDealItems(GsonRequest gsonRequest) {


        // Add a request
        NetworkQueue.getInstance(this).addToRequestQueue(gsonRequest);

    }

    /**
     * Get image from web service
     */
    public void getImage(String imageURL, ImageLoader.ImageListener listener) {


        //null check
        if (imageURL != null) {
            NetworkQueue.getInstance(this).getImageLoader().get(imageURL, listener);
        }

    }

    /**
     * Get image from web service and apply to view
     */
    public void getImage(ImageRequest listener) {

        //null check
        if (listener != null) {

            NetworkQueue.getInstance(this).addToRequestQueue(listener);

        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /*
    * Create URI from bitmap
    * */
    private Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    /*
    * Create URI from bitmap
    * */
    private void sharePicture(Bitmap image) {
        Uri uri = getImageUri(image);

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("image/jpeg");
        startActivity(Intent.createChooser(shareIntent, "Send"));
    }

}
