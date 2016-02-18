package com.csns.gopi.learnvolley;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import constants.WebConstants;
import volley.CustomJSONObjectRequest;
import volley.CustomVolleyRequestQueue;

public class MainActivity extends AppCompatActivity implements Response.Listener, Response.ErrorListener {

    public static final String REQUEST_TAG = "MainActivity";

    private TextView mTextView;
    private Button mButton,button1;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.textView);
        mButton = (Button) findViewById(R.id.button);
        button1 = (Button) findViewById(R.id.button1);

        Toast.makeText(MainActivity.this, "Test", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        mQueue = CustomVolleyRequestQueue.getInstance(this.getApplicationContext()).getRequestQueue();

        final CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(Request.Method.GET,
                                                                                WebConstants.URL+WebConstants.GET_CATEGORY,
                                                                                new JSONObject(),
                                                                                this,
                                                                                this);

        jsonRequest.setTag(REQUEST_TAG);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("gopi","Send Request clicked");
                mQueue.add(jsonRequest);
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(MainActivity.this,SubCategoryActivity.class);
                //startActivity(i);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mQueue != null) {
            mQueue.cancelAll(REQUEST_TAG);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        mTextView.setText(error.getMessage());
    }

    @Override
    public void onResponse(Object response) {

        try {

            JSONObject obj = new JSONObject(response.toString());
            Log.e("Response",""+response.toString());


            if(obj.getString("success").equals("200")){

                String message = obj.getString("message");

                JSONArray data = obj.getJSONArray("Data");

            }


        } catch (JSONException e) {

            e.printStackTrace();
        }
    }
}
