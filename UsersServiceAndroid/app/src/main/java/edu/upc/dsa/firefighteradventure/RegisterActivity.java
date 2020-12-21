package edu.upc.dsa.firefighteradventure;

import androidx.appcompat.app.AppCompatActivity;
import edu.upc.dsa.firefighteradventure.models.RegisterCredentials;
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

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }
    public void btnSignupClick(android.view.View u){

        EditText etUsernameSignup = findViewById(R.id.etUsernameSignup);
        EditText etPwdSignup = findViewById(R.id.etPwdSignup);
        EditText etConfirmPwdSingup = findViewById(R.id.etConfirmPwdSignup);
        EditText etEmail = findViewById(R.id.etEmail);
        EditText etBirthdate = findViewById(R.id.etBirthdate);

        if (etUsernameSignup.getText().toString().equals("")) {

            Toast.makeText(getApplicationContext(), R.string.write_username_string, Toast.LENGTH_SHORT).show();
            return;

        } else if (etPwdSignup.getText().toString().equals("")) {

            Toast.makeText(getApplicationContext(), R.string.write_password_string, Toast.LENGTH_SHORT).show();
            return;

        } else if (etConfirmPwdSingup.getText().toString().equals("")) {

            Toast.makeText(getApplicationContext(), R.string.write_confirm_password_string, Toast.LENGTH_SHORT).show();
            return;

        } else if (etEmail.getText().toString().equals("")) {

            Toast.makeText(getApplicationContext(), R.string.write_email_string, Toast.LENGTH_SHORT).show();
            return;

        } else if (etBirthdate.getText().toString().equals((""))) {

            Toast.makeText(getApplicationContext(), R.string.write_birthdate_string, Toast.LENGTH_SHORT).show();
            return;

        } else if (!etConfirmPwdSingup.getText().toString().equals(etPwdSignup.getText().toString())) {

            Toast.makeText(getApplicationContext(), R.string.passwords_not_match_string, Toast.LENGTH_SHORT).show();
            return;

        }

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBarRegister);

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

        Call<ResponseBody> resp = service.registerUser(new RegisterCredentials(etUsernameSignup.getText().toString(), etPwdSignup.getText().toString(), etEmail.getText().toString(), etBirthdate.getText().toString()));

        resp.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.d("DebugTag", String.valueOf(response.code()));

                if (response.code() == 600) {

                    Toast.makeText(getApplicationContext(), R.string.write_username_string, Toast.LENGTH_SHORT).show();

                } else if (response.code() == 601) {

                    Toast.makeText(getApplicationContext(), R.string.write_password_string, Toast.LENGTH_SHORT).show();

                } else if (response.code() == 201) {

                    Toast.makeText(getApplicationContext(), R.string.succesful_register_string, Toast.LENGTH_SHORT).show();

                } else if (response.code() == 250) {

                    Toast.makeText(getApplicationContext(), R.string.user_already_exists_string, Toast.LENGTH_SHORT).show();

                } else if (response.code() == 604) {

                    Toast.makeText(getApplicationContext(), R.string.username_short_long_string, Toast.LENGTH_SHORT).show();

                } else if (response.code() == 605) {

                    Toast.makeText(getApplicationContext(), R.string.password_short_long_string, Toast.LENGTH_SHORT).show();

                } else if (response.code() == 606) {

                    Toast.makeText(getApplicationContext(), R.string.email_short_long_string, Toast.LENGTH_SHORT).show();

                } else if (response.code() == 607) {

                    Toast.makeText(getApplicationContext(), R.string.too_young_string, Toast.LENGTH_SHORT).show();

                }

                ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBarRegister);
                progressBar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(getApplicationContext(), R.string.error_register_string, Toast.LENGTH_SHORT).show();

                ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBarRegister);
                progressBar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            }
        });



        Log.d("DebugTag","Usuario ha pulsado el botón para registrarse.");
        // Intent intent1 = new Intent(MainActivity.this, SignupActivity.class);
        //intent.putExtra("nombre","Meri");
        // startActivity(intent1);
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