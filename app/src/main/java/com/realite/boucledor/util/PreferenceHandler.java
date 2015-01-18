package com.realite.boucledor.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.realite.boucledor.R;

public class PreferenceHandler {

    private final int defaultRetroDom;
    private final int defaultRetroCab;
    private final int defaultTax;
    private final SharedPreferences sharedPreferences;
    private final Context context;

    public PreferenceHandler(Context context) {
        this.context = context;
        defaultRetroDom = context.getResources().getInteger(R.integer.default_retro_percent_dom);
        defaultRetroCab = context.getResources().getInteger(R.integer.default_retro_percent_cab);
        defaultTax = context.getResources().getInteger(R.integer.default_tax_percent);
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.pref_file), Context.MODE_PRIVATE);
    }

    public SharedPreferences getPreferences(Context context) {
        return sharedPreferences;
    }

    public int getCabRetro() {
       return sharedPreferences != null ?
               sharedPreferences.getInt(context.getString(R.string.set_retro_percent_cab_prop), defaultRetroCab)
               : defaultRetroCab;
    }

    public int getDomRetro() {
        return sharedPreferences != null?
                sharedPreferences.getInt(context.getString(R.string.set_retro_percent_dom_prop), defaultRetroDom)
                : defaultRetroDom;
    }

    public int getTax() {
        return sharedPreferences != null?
                sharedPreferences.getInt(context.getString(R.string.set_tax_percent_prop), defaultTax)
                : defaultTax;
    }

    public void setNewSettings(int cabRetro, int domRetro, int tax) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(context.getString(R.string.set_retro_percent_cab_prop), cabRetro)
                .putInt(context.getString(R.string.set_retro_percent_dom_prop), domRetro)
                .putInt(context.getString(R.string.set_tax_percent_prop), tax)
                .apply();
    }

    public void setCabRetro(int cabRetro) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(context.getString(R.string.set_retro_percent_cab_prop), cabRetro);
        editor.apply();
    }

    public void setDomRetro(int domRetro) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(context.getString(R.string.set_retro_percent_dom_prop), domRetro);
        editor.apply();
    }

    public void setTax(int tax) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(context.getString(R.string.set_tax_percent_prop), tax);
        editor.apply();
    }

}
