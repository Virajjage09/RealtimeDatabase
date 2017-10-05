
package com.coderVJ.realtimedb.utils.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;


/**
 * Created by Viraj Jage on 12/09/2017.
 */


public final class Preferences {

    public final static String PREFS_NAME = "RealTimeDatabasePreference";
    public static SharedPreferences mAppPreferences;
    public static Editor mEditor;
    /**
     *
     */
    private Preferences() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Removes all SharedPreference for the
     * 
     * @param context
     */
    public static void removeAll(Context context) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
                .clear().commit();
    }

    /**
     *
     * @param context
     * @param Key
     *            to remove
     */
    public static void remove(Context context, String Key) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editor = prefs.edit();
        editor.remove(Key);
        editor.commit();
    }

    /**
     * Gets string data saved in SharedPreference
     * 
     * @param context
     * @param key
     * @return String data. Default return value is ""
     */
    public static String getStringPreference(Context context, final String key) {
        String value = null;
        /*
         * SharedPreferences preferences = PreferenceManager
         * .getDefaultSharedPreferences(context);
         */
        SharedPreferences preferences = context.getSharedPreferences(
                PREFS_NAME, Context.MODE_PRIVATE);
        value = preferences.getString(key, "");
        return value;

    }

    /**
     * Set String data in SharedPreference
     * 
     * @param context
     * @param key
     * @param value
     */
    public static void setStringPreference(Context context, final String key,
                                           final String value) {

        /*
         * SharedPreferences preferences = PreferenceManager
         * .getDefaultSharedPreferences(context);
         */
        SharedPreferences preferences = context.getSharedPreferences(
                PREFS_NAME, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * Get boolean data saved in SharedPreference.
     * 
     * @param context
     * @param key
     * @return boolean data. Default return value is "false".
     */
    public static boolean getBooleanPreference(Context context, final String key) {
        boolean value = false;
        /*
         * SharedPreferences preferences = PreferenceManager
         * .getDefaultSharedPreferences(context);
         */
        SharedPreferences preferences = context.getSharedPreferences(
                PREFS_NAME, Context.MODE_PRIVATE);
        value = preferences.getBoolean(key, value);
        return value;
    }

    /**
     * Set boolean data in SharedPreference
     * 
     * @param context
     * @param key
     * @param value
     */
    public static void setBooleanPreference(Context context, final String key,
                                            final boolean value) {

        /*
         * SharedPreferences preferences = PreferenceManager
         * .getDefaultSharedPreferences(context);
         */
        SharedPreferences preferences = context.getSharedPreferences(
                PREFS_NAME, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * Get int data from SharedPreference
     * 
     * @param context
     * @param key
     * @return int data. Default return value is "0"
     */
    public static int getIntPreference(Context context, final String key) {

        int value = 0;
        /*
         * SharedPreferences preferences = PreferenceManager
         * .getDefaultSharedPreferences(context);
         */
        SharedPreferences preferences = context.getSharedPreferences(
                PREFS_NAME, Context.MODE_PRIVATE);
        value = preferences.getInt(key, 0);
        return value;
    }

    /**
     * Set int data in SharedPreference
     * 
     * @param context
     * @param key
     * @param value
     */
    public static void setIntPreference(Context context, final String key,
                                        final int value) {

        /*
         * SharedPreferences preferences = PreferenceManager
         * .getDefaultSharedPreferences(context);
         */
        SharedPreferences preferences = context.getSharedPreferences(
                PREFS_NAME, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * Set long data in SharedPreference
     * 
     * @param context
     * @param key
     * @param value
     */
    public static void setLongPreference(Context context, final String key,
                                         final long value) {
        /*
         * SharedPreferences preferences = PreferenceManager
         * .getDefaultSharedPreferences(context);
         */
        SharedPreferences preferences = context.getSharedPreferences(
                PREFS_NAME, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * Get long data from SharedPreference
     * 
     * @param context
     * @param key
     * @return long data. Default return type is "0"
     */
    public static long getLongPreference(Context context, final String key) {
        long value = 0;
        /*
         * SharedPreferences preferences = PreferenceManager
         * .getDefaultSharedPreferences(context);
         */
        SharedPreferences preferences = context.getSharedPreferences(
                PREFS_NAME, Context.MODE_PRIVATE);
        value = preferences.getLong(key, 0);
        return value;
    }

    public static String addPreference(Context context, String pref_field,
                                       String pref_value) {
        mAppPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        mEditor = mAppPreferences.edit();
        mEditor.putString(pref_field, pref_value);
        mEditor.commit();
        return mAppPreferences.getString(pref_field, null);
    }

    public static boolean addPreferenceBoolean(Context context,
                                               String pref_field, Boolean pref_value) {
        mAppPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        mEditor = mAppPreferences.edit();
        mEditor.putBoolean(pref_field, pref_value);
        mEditor.commit();
        return mAppPreferences.getBoolean(pref_field, false);
    }

    public static String getPreference(Context context, String pref_field,
                                       String def_value) {
        mAppPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return mAppPreferences.getString(pref_field, def_value);
    }

    public static boolean getPreferenceBoolean(Context context,
                                               String pref_field, Boolean def_value) {
        mAppPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return mAppPreferences.getBoolean(pref_field, false);
    }
}
