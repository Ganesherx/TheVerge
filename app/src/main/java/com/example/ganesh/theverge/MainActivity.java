package com.example.ganesh.theverge;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getName();
    private String THE_VERGE_URL =
            " https://newsapi.org/v1/articles?";
    private TheVergeAdapter mVergeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TheVergeAsyncTask theVergeAsyncTask = new TheVergeAsyncTask();
        theVergeAsyncTask.execute(THE_VERGE_URL);

        ListView weatherListView = (ListView) findViewById(R.id.blog_item_list_view);
        mVergeAdapter = new TheVergeAdapter(this, new ArrayList<TheVergePojo>());


        weatherListView.setAdapter(mVergeAdapter);

        weatherListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                TheVergePojo currentTheVerge=mVergeAdapter.getItem(position);
                Uri theVergeUri=Uri.parse(currentTheVerge.getUrl());

                Intent websiteIntent=new Intent(Intent.ACTION_VIEW,theVergeUri);
                startActivity(websiteIntent);
            }
        });

    }

    private class TheVergeAsyncTask extends AsyncTask<String, Void, List<TheVergePojo>> {

        @Override
        protected List<TheVergePojo> doInBackground(String... url) {
            if (url.length < 1 || url[0] == null) {
                return null;
            }

            Uri baseUri = Uri.parse(THE_VERGE_URL).buildUpon()
                    .appendQueryParameter("source", "the-verge")
                    .appendQueryParameter("sortBy", "latest")
                    .appendQueryParameter("apiKey", BuildConfig.THE_VERGE_API_KEY)
                    .build();

            Log.e(LOG_TAG, "url:" + baseUri.toString());
            List<TheVergePojo> theVergePojos = QueryUtils.fetchTheVergeData(baseUri.toString());

            return theVergePojos;
        }

        @Override
        protected void onPostExecute(List<TheVergePojo> data) {
            mVergeAdapter.clear();

            if (data != null && !data.isEmpty()) {
                mVergeAdapter.addAll(data);
            }
        }
    }
}
