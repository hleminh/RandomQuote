package com.example.hoang.randomquote.networks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hoang.randomquote.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hoang on 5/16/2017.
 */

public class QuoteLoader extends AsyncTask<Void, Void, List<Object>> {
    private final String QUOTE_URL = "http://quotesondesign.com/wp-json/posts?filter[orderby]=rand";
    private final String IMAGE_URL = "https://source.unsplash.com/random/607x958";
    private TextView textView;
    private ImageView imageView;

    public QuoteLoader(TextView textView, ImageView imageView) {
        this.textView = textView;
        this.imageView = imageView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        textView.setText("");
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setImageResource(R.drawable.progress_animation);
    }

    @Override
    protected List<Object> doInBackground(Void... params) {
        List<Object> objList = new ArrayList<>();
        try {
            //1: Open connection
            URL urlQuote = new URL(QUOTE_URL);
            URL urlImage = new URL(IMAGE_URL);
            //2: Create stream
            InputStream inputStream = urlQuote.openStream();
            //3: Get data
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String content = stringBuilder.toString();
            Log.d("QuoteLoader", String.format("%s", content));
            //4: Decode data
            JSONArray jsonArray = new JSONArray(content);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            Log.d("QuoteLoader", String.format("%s", jsonObject.toString()));
            String jsonContent = jsonObject.getString("content");
            objList.add(jsonContent);
            inputStream.close();
            inputStream = urlImage.openStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            objList.add(bitmap);
            return objList;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Object> objects) {
        textView.setText(Html.fromHtml((String) objects.get(0)));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setImageBitmap((Bitmap) objects.get(1));
    }
}
