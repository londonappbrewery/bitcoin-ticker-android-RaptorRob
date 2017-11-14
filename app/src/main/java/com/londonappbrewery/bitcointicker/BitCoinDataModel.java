package com.londonappbrewery.bitcointicker;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rgehlis on 14.11.2017.
 */

public class BitCoinDataModel {
        private double averageDay;
        private double averageWeek;
        private double averageMonth;

    /**
     * Extracts BitCoin average rates from given JSONObject
     * @param jsonObject
     * @return
     */
    public static BitCoinDataModel fromJSON(JSONObject jsonObject){
        BitCoinDataModel bitCoinDataModel = new BitCoinDataModel();
        try {
            bitCoinDataModel.averageDay = jsonObject.getJSONObject("averages").getDouble("day");
            bitCoinDataModel.averageWeek = jsonObject.getJSONObject("averages").getDouble("week");
            bitCoinDataModel.averageMonth = jsonObject.getJSONObject("averages").getDouble("month");
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("BitCoin", "fromJSON: " + e);
        }

        return bitCoinDataModel;
    }

    public double getAverageDay() {
        return averageDay;
    }

    public double getAverageWeek() {
        return averageWeek;
    }

    public double getAverageMonth() {
        return averageMonth;
    }
}
