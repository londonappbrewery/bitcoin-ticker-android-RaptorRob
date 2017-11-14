package com.londonappbrewery.bitcointicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {

    // Constants:
    private final String BASE_URL = "https://apiv2.bitcoinaverage.com/indices/local/ticker/";

    private static AsyncHttpClient client;

    // Member Variables:
    TextView mPriceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPriceTextView = (TextView) findViewById(R.id.priceLabel);
        Spinner spinner = (Spinner) findViewById(R.id.currency_spinner);

        // Create an ArrayAdapter using the String array and a spinner layout
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array, R.layout.spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getBitCoinForCurrencyExchange(BASE_URL + "BTC" + adapter.getItem(position));
                Log.d("BitCoin", "onItemSelected: " + BASE_URL + "BTC"+ adapter.getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    /**
     * Gets JSONObject header information of given url-String to extract BitCoin exchange rate
     * @param url
     */
    private void getBitCoinForCurrencyExchange(String url) {
        client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                Log.d("BitCoin", "onSuccess: " + statusCode);
                //perform whatever update in app needs to be done
                updateExchangeValue(BitCoinDataModel.fromJSON(response));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse){
                Log.d("BitCoin", "onFailure: " + statusCode + " / " + e);
                Toast.makeText(MainActivity.this, "Request to BitCoing API has failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Sets Text of the Price TextView
     * @param bitCoinDataModel
     */
    protected void updateExchangeValue(BitCoinDataModel bitCoinDataModel){
        mPriceTextView.setText(String.valueOf(bitCoinDataModel.getAverageDay()));
    }


}
