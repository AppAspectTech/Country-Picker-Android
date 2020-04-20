package com.country.picker.Utils;


import android.content.Context;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;

public class Utils
{

    public static void hideSoftKeyboard(AppCompatActivity activity)
    {
        try
        {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        }
        catch (Exception ex)
        {
            Log.e("hideSoftKeyboard",ex.toString());
        }
    }

}
