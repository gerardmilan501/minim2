package edu.upc.dsa.firefighteradventure;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import edu.upc.dsa.firefighteradventure.models.Users;
import edu.upc.dsa.firefighteradventure.services.UsersService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    int btnLogincount;
    int btnSignupcount;

    public MainActivity(){
        btnLogincount=0;
        btnSignupcount=0;
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

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build();

            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080/dsaApp/") //10.0.2.2
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();

            UsersService service = retrofit.create(UsersService.class);

            Call<List<Users>> users = service.listUsers();

            users.enqueue(new Callback<List<Users>>() {
                @Override
                public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                    List<Users> result = response.body();
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
                public void onFailure(Call<List<Users>> call, Throwable t) {
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

    }

    public void goBack(View v){

        setContentView(R.layout.activity_main);

    }

}

