package edu.upc.dsa.firefighteradventure;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentContainerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;

import edu.upc.dsa.firefighteradventure.services.GameService;
import edu.upc.dsa.firefighteradventure.services.UserService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final boolean remote_machine = true;

    private static final String remote_ip = "147.83.7.207";
    private static final int remote_port = 8080;

    private static final String local_ip = "10.0.2.2";
    private static final int local_port = 8080;

    private static final String api_name = "dsaApp";

    private int username_min_length;
    private int username_max_length;
    private int password_min_length;
    private int password_max_length;
    private int email_min_length;
    private int email_max_length;
    private int min_age;

    private UserService userService;
    private GameService gameService;

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

        progressBar = findViewById(R.id.progressBar);
        clLoading = findViewById(R.id.clLoading);

        nhFragment = findViewById(R.id.nhFragment);

        setBackActivated(false);
        startServices();

    }

    private void startServices() {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Retrofit retrofit;

        if (remote_machine) {

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://" + remote_ip + ":" + remote_port + "/" + api_name + "/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        } else {

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://" + local_ip + ":" + local_port + "/" + api_name + "/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();

        }

        userService = retrofit.create(UserService.class);
        gameService = retrofit.create(GameService.class);

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

    public String getSavedUsername() {

        SharedPreferences prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        return prefs.getString("username", "");

    }

    public String getSavedPassword() {

        SharedPreferences prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        return prefs.getString("password", "");

    }

    public void setSavedUsername(String username) {

        SharedPreferences prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("username", username);
        editor.apply();

    }

    public void setSavedPassword(String password) {

        SharedPreferences prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("password", password);
        editor.apply();

    }

    public UserService getUserService() {

        return userService;

    }

    public GameService getGameService() {

        return gameService;

    }

    public void setLoadingData(boolean loadingData) {

        if (loadingData) {

            progressBar.setProgress(0);
            clLoading.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        } else {

            progressBar.setVisibility(View.GONE);
            clLoading.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

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

    public boolean isBackActivated() {
        return backActivated;
    }

    public void setBackActivated(boolean backActivated) {
        this.backActivated = backActivated;
    }

    public boolean isLoadingData() {
        return loadingData;
    }

    public int getUsername_min_length() {
        return username_min_length;
    }

    public void setUsername_min_length(int username_min_length) {
        this.username_min_length = username_min_length;
    }

    public int getUsername_max_length() {
        return username_max_length;
    }

    public void setUsername_max_length(int username_max_length) {
        this.username_max_length = username_max_length;
    }

    public int getPassword_min_length() {
        return password_min_length;
    }

    public void setPassword_min_length(int password_min_length) {
        this.password_min_length = password_min_length;
    }

    public int getPassword_max_length() {
        return password_max_length;
    }

    public void setPassword_max_length(int password_max_length) {
        this.password_max_length = password_max_length;
    }

    public int getEmail_min_length() {
        return email_min_length;
    }

    public void setEmail_min_length(int email_min_length) {
        this.email_min_length = email_min_length;
    }

    public int getEmail_max_length() {
        return email_max_length;
    }

    public void setEmail_max_length(int email_max_length) {
        this.email_max_length = email_max_length;
    }

    public int getMin_age() {
        return min_age;
    }

    public void setMin_age(int min_age) {
        this.min_age = min_age;
    }
}

