package com.keto.optics.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by ketanvichare on 10/9/16.
 */
public class DownloadImageTask extends AsyncTask<String, Integer, Bitmap> {

    ImageView imageView;

    public DownloadImageTask(ImageView imageview){
        this.imageView = imageview;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        if(bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }
    }

    @Override
    protected Bitmap doInBackground(String... params) {

        String iconUrl = params[0];
        Bitmap mIcon1 = null;
        try {
            InputStream in = new java.net.URL(iconUrl).openStream();
            mIcon1 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon1;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }
}
