package edu.upc.dsa.firefighteradventure;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

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

    private static final boolean remote_machine = false;

    private static final String remote_ip = "147.83.7.207";
    private static final int remote_port = 8080;

    private static final String local_ip = "10.0.2.2";
    private static final int local_port = 8080;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private HttpLoggingInterceptor httpLoggingInterceptor;
    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    private UsersService usersService;

    public MainActivity(){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.btn_getuserlist) {

            Call<List<User>> users = usersService.listUsers();

            users.enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                    List<User> result = response.body();
                    setContentView(R.layout.lista_tracks);
                    recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

                    recyclerView.setHasFixedSize(true);
                    // use a linear layout manager

                        layoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(layoutManager);

                    // define an adapter
                    mAdapter = new MyAdapter(result);
                    recyclerView.setAdapter(mAdapter);

                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {

                    Toast.makeText(getApplicationContext(), R.string.show_users_error_string, Toast.LENGTH_SHORT).show();

                }

            });

            return true;

        } else {

            return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutManager = new LinearLayoutManager(this);

        httpLoggingInterceptor = new HttpLoggingInterceptor();
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        if (remote_machine) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://" + remote_ip + ":" + remote_port + "/dsaApp/") //10.0.2.2
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        } else {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://" + local_ip + ":" + local_port + "/dsaApp/") //10.0.2.2
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }

        usersService = retrofit.create(UsersService.class);

    }

    public void goBack(View v){

        setContentView(R.layout.activity_main);

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

    public UsersService getUsersService() {

        return usersService;

    }
}

