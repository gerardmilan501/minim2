package edu.upc.dsa.firefighteradventure;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentContainerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.Locale;

import androidx.preference.PreferenceManager;

import edu.upc.dsa.firefighteradventure.services.UserService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {



    private UserService userService;


    private ProgressBar progressBar;
    private ConstraintLayout clLoading;

    private FragmentContainerView nhFragment;

    private boolean backActivated;
    private boolean loadingData;

    public MainActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getChooseAppLanguage()) {

            Locale locale = new Locale(getSavedLanguage());
            setLocale(locale);

        } else {

            setLocale(new Locale(Locale.getDefault().getDisplayLanguage()));

        }

        progressBar = findViewById(R.id.progressBar);
        clLoading = findViewById(R.id.clLoading);

        nhFragment = findViewById(R.id.nhFragment);

        setBackActivated(false);
        startServices();

    }

    public void setLocale(Locale locale) {

        Locale.setDefault(locale);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = locale;
        res.updateConfiguration(conf, dm);

    }

    private void startServices() {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Retrofit retrofit;



        retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.github.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();



        userService = retrofit.create(UserService.class);


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        View v = getCurrentFocus();

        if (v != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) &&
                v instanceof EditText &&
                !v.getClass().getName().startsWith("android.webkit.")) {

            int[] sourceCoordinates = new int[2];
            v.getLocationOnScreen(sourceCoordinates);
            float x = ev.getRawX() + v.getLeft() - sourceCoordinates[0];
            float y = ev.getRawY() + v.getTop() - sourceCoordinates[1];

            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom()) {

                hideKeyboard(this);

            }

        }

        return super.dispatchTouchEvent(ev);

    }

    private void hideKeyboard(Activity activity) {

        if (activity != null && activity.getWindow() != null) {

            activity.getWindow().getDecorView();
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

            if (imm != null) {

                imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);

            }

        }

    }

    public String getSavedLanguage() {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        return prefs.getString("savedLanguage", Locale.getDefault().getDisplayLanguage());

    }

    public void setSavedLanguage(String language) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("savedLanguage", language);
        editor.apply();

    }

    public boolean getChooseAppLanguage() {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        return prefs.getBoolean("chooseAppLanguage", true);

    }

    public void setChooseAppLanguage(boolean useAndroidSystemLanguage) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("chooseAppLanguage", useAndroidSystemLanguage);
        editor.apply();

    }

    public String getSavedUsername() {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        return prefs.getString("username", "");

    }

    public String getSavedPassword() {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        return prefs.getString("password", "");

    }


    public void setSavedUsername(String username) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("username", username);
        editor.apply();

    }

    public void setSavedPassword(String password) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("password", password);
        editor.apply();

    }

    public UserService getUserService() {

        return userService;

    }



    public void setLoadingData(boolean loadingData) {

        if (loadingData) {

            this.loadingData = true;

            progressBar.setProgress(0);
            clLoading.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        } else {

            progressBar.setVisibility(View.GONE);
            clLoading.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            this.loadingData = false;

        }

    }



    @Override
    public void onBackPressed() {

        if (isBackActivated() && !isLoadingData()) {

            super.onBackPressed();

        }

    }

    public void goBack() {

        super.onBackPressed();

    }

    public boolean isNetworkConnected() {

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();

    }

    public void restart(){

        Intent intent = getIntent();
        this.finish();
        startActivity(intent);

    }

    public InputFilter spaceFilter = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; i++) {
                if (Character.isWhitespace(source.charAt(i))) {
                    return "";
                }
            }
            return null;
        }
    };

    public boolean isBackActivated() {
        return backActivated;
    }

    public void setBackActivated(boolean backActivated) {
        this.backActivated = backActivated;
    }

    public boolean isLoadingData() {
        return loadingData;
    }


}

