package kg.geektech.taskapp37;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

public class Prefs {

    private SharedPreferences preferences;

    private Prefs instance;

    public Prefs(Context context) {
        instance = this;
        preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);

    }

    public void saveLogin(String login) {
        preferences.edit().putString("isLogin", login).apply();
    }

    public String getLogin() {
        return preferences.getString("isLogin", null);
    }

    public void saveNewsText(String newsText) {
        preferences.edit().putString("isNewsText", newsText).apply();
    }

    public String getNewsText() {
        return preferences.getString("isNewsText", null);
    }


    public void saveImage(Uri image) {
        preferences.edit().putString("isGallery", image.toString()).apply(); }

    public String getImage() {
        return preferences.getString("isGallery", "");
    }

    public void saveBoardState() {
        preferences.edit().putBoolean("isShown", true).apply();
    }

    public boolean isBoardShown() {
        return preferences.getBoolean("isShown", false);
    }

    public String getImageUser(){
        return  preferences.getString("isEmptyImage","" );
    }


    public void deleteUserImage() {
        preferences.edit().remove("isEmptyImage").apply();
    }
}

