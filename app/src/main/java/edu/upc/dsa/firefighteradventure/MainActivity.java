package edu.upc.dsa.firefighteradventure;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import edu.upc.dsa.firefighteradventure.models.User;
import edu.upc.dsa.firefighteradventure.services.UsersService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final boolean remote_machine = true;

    private static final String remote_ip = "147.83.7.207";
    private static final int remote_port = 8080;

    private static final String local_ip = "10.0.2.2";
    private static final int local_port = 8080;

    private static final String api_name = "dsaApp";

    private UsersService usersService;

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

        usersService = retrofit.create(UsersService.class);

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

    public UsersService getUsersService() {

        return usersService;

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
}

