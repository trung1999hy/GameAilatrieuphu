package com.example.myapplication.dhbatchu.utils;

import android.content.res.Resources;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.myapplication.Constants;
import com.example.myapplication.R;
import com.example.myapplication.dhbatchu.ui.ItemValue;
import com.example.myapplication.dhbatchu.ui.ResultValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Utils {

    private static final String ALLOWED_CHARACTERS = "qwertyuiopasdfghjklzxcvbnm";

    public static ResultValue getResultValue(String value) {
        ResultValue resultValue = null;
        String[] results = value.split("\\^");
        if (results.length == 3) {
            resultValue = new ResultValue(Integer.parseInt(results[0]), results[1], results[2]);
        }
        return resultValue;
    }


    public static List<String> getListResultDf(String value) {
        List<Character> chars = value.chars()
                .mapToObj(e -> (char) e).collect(Collectors.toList());
        List<String> results = new ArrayList<>();
        for (Character character : chars) {
            if (character.toString().equals("-"))
                results.add(character.toString());
            else results.add("");
        }
        Log.e("getListResult", results.toString());
        return results;
    }


    public static List<String> getListResult(String value) {
        List<Character> chars = value.chars()
                .mapToObj(e -> (char) e).collect(Collectors.toList());
        List<String> results = new ArrayList<>();
        for (Character character : chars) {
            results.add(character.toString());
        }
        Log.e("getListResult", results.toString());
        return results;
    }

    private static String getRandomString(int sizeOfRandomString) {
        final Random random = new Random();
        final StringBuilder sb = new StringBuilder(sizeOfRandomString);
        for (int i = 0; i < sizeOfRandomString; ++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }

    public static List<ItemValue> setListValue(String value) {
        String newValue = value.replace("-", "") + getRandomString(4);
        List<Character> chars = newValue.chars()
                .mapToObj(e -> (char) e).collect(Collectors.toList());
        List<ItemValue> results = new ArrayList<>();
        for (Character character : chars) {
            results.add(new ItemValue(character.toString(), false));
        }
        Collections.shuffle(results);
        return results;
    }

    public static int getCoinFromKey(String coin) {
        switch (coin) {
            case Constants.KEY_10_COIN:
                return 5;
            case Constants.KEY_20_COIN:
                return 10;
            case Constants.KEY_50_COIN:
                return 20;
            case Constants.KEY_100_COIN:
                return 30;
            case Constants.KEY_150_COIN:
                return 100;
            case Constants.KEY_200_COIN:
                return 150;
            default:
                return 0;
        }
    }

    public static String setTitleValue(Resources resources, String productId, String price) {
        switch (productId) {
            case Constants.KEY_10_COIN:
                return String.format(resources.getString(R.string.message_purchase_one), price + "/5 vàng");
            case Constants.KEY_20_COIN:
                return String.format(resources.getString(R.string.message_purchase_one), price + "/10 vàng");
            case Constants.KEY_50_COIN:
                return String.format(resources.getString(R.string.message_purchase_one), price + "/20 vàng");
            case Constants.KEY_100_COIN:
                return String.format(resources.getString(R.string.message_purchase_one), price + "/30 vàng");
            case Constants.KEY_150_COIN:
                return String.format(resources.getString(R.string.message_purchase_one), price + "/100 vàng");
            case Constants.KEY_200_COIN:
                return String.format(resources.getString(R.string.message_purchase_one), price + "/150 vàng");
            default:
                return String.format(resources.getString(R.string.message_purchase_one), "/0 vàng");
        }
    }
}
