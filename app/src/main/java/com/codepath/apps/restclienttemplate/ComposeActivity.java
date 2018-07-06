package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {

    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        EditText editText = (EditText) findViewById(R.id.editText);
        client = TwitterApp.getRestClient(this);

    }

    public void postTweet(View v){
        EditText view = findViewById(R.id.editText);
        String s = view.getText().toString();
        client.sendTweet(s, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Tweet tweet = Tweet.fromJSON(response);
                    Intent intent = new Intent();
                    intent.putExtra("tweet", Parcels.wrap(tweet));
                    setResult(1, intent);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable error, JSONObject errorResponse) {
                error.printStackTrace();

            }
        });
    }
}
