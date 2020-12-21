package edu.upc.dsa.firefighteradventure;

import androidx.appcompat.app.AppCompatActivity;
import edu.upc.dsa.firefighteradventure.models.LoginCredentials;
import edu.upc.dsa.firefighteradventure.services.UsersService;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "edu.upc.dsa.LoginSignupMeri.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void btnLoginClick(android.view.View u){

        Log.d("DebugTag","Usuario ha pulsado el botón para logearse.");

        EditText etUsername = (EditText) findViewById(R.id.etUsername);
        EditText etPassword = (EditText) findViewById(R.id.etPwd);

        if (etUsername.getText().toString().equals("")) {

            Toast.makeText(getApplicationContext(), R.string.write_username_string, Toast.LENGTH_SHORT).show();
            return;

        } else if (etPassword.getText().toString().equals("")) {

            Toast.makeText(getApplicationContext(), R.string.write_password_string, Toast.LENGTH_SHORT).show();
            return;

        }

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBarLogin);

        progressBar.setProgress(0);
        progressBar.setVisibility(View.VISIBLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/dsaApp/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        UsersService service = retrofit.create(UsersService.class);

        Call<ResponseBody> resp = service.loginUser(new LoginCredentials(etUsername.getText().toString(), etPassword.getText().toString()));

        resp.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.code() == 601) {

                    Toast.makeText(getApplicationContext(), R.string.write_username_string, Toast.LENGTH_SHORT).show();

                } else if (response.code() == 602) {

                    Toast.makeText(getApplicationContext(), R.string.write_password_string, Toast.LENGTH_SHORT).show();

                } else if (response.code() == 201) {

                    Toast.makeText(getApplicationContext(), R.string.login_succesful_string, Toast.LENGTH_SHORT).show();

                } else if (response.code() == 603) {

                    Toast.makeText(getApplicationContext(), R.string.incorrect_password_string, Toast.LENGTH_SHORT).show();

                } else if (response.code() == 250) {

                    Toast.makeText(getApplicationContext(), R.string.user_not_exists_string, Toast.LENGTH_SHORT).show();

                }

                ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBarLogin);
                progressBar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(getApplicationContext(), R.string.error_login_string, Toast.LENGTH_SHORT).show();

                ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBarLogin);
                progressBar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            }


        });


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

}