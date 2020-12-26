package edu.upc.dsa.firefighteradventure.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import edu.upc.dsa.firefighteradventure.MainActivity;
import edu.upc.dsa.firefighteradventure.R;
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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class LoginFragment extends Fragment {

    private View view;
    private EditText etUsernameLogin;
    private EditText etPasswordLogin;
    ProgressBar progressBarLogin;

    MainActivity mainActivity;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        etUsernameLogin = view.findViewById(R.id.etUsernameLogin);
        etPasswordLogin = view.findViewById(R.id.etPasswordLogin);
        progressBarLogin = view.findViewById(R.id.progressBarLogin);

        view.findViewById(R.id.btnLogin).setOnClickListener(this::btnLoginClick);
        view.findViewById(R.id.btnGotoForgottenPassword).setOnClickListener(this::btnGotoForgottenPasswordClick);

        mainActivity = (MainActivity) getActivity();

    }

    public void btnGotoForgottenPasswordClick(android.view.View u) {

        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_forgottenPasswordFragment);

    }

    public void btnLoginClick(android.view.View u) {

        if (etUsernameLogin.getText().toString().equals("")) {

            Toast.makeText(getContext(), R.string.write_username_string, Toast.LENGTH_SHORT).show();
            return;

        } else if (etPasswordLogin.getText().toString().equals("")) {

            Toast.makeText(getContext(), R.string.write_password_string, Toast.LENGTH_SHORT).show();
            return;

        }

        progressBarLogin.setProgress(0);
        progressBarLogin.setVisibility(View.VISIBLE);

        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        Call<ResponseBody> resp = mainActivity.getUsersService().login(new LoginCredentials(etUsernameLogin.getText().toString(), etPasswordLogin.getText().toString()));

        resp.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.code() == 601) {

                    Toast.makeText(getContext(), R.string.write_username_string, Toast.LENGTH_SHORT).show();

                } else if (response.code() == 602) {

                    Toast.makeText(getContext(), R.string.write_password_string, Toast.LENGTH_SHORT).show();

                } else if (response.code() == 201) {

                    Toast.makeText(getContext(), R.string.login_succesful_string, Toast.LENGTH_SHORT).show();

                    ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBarLogin);
                    progressBar.setVisibility(View.GONE);
                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    SharedPreferences prefs = getActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("username", etUsernameLogin.getText().toString());
                    editor.putString("password", etPasswordLogin.getText().toString());
                    editor.commit();

                    Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_mainMenuFragment);

                } else if (response.code() == 603) {

                    Toast.makeText(getContext(), R.string.incorrect_password_string, Toast.LENGTH_SHORT).show();

                } else if (response.code() == 250) {

                    Toast.makeText(getContext(), R.string.user_not_exists_string, Toast.LENGTH_SHORT).show();

                }

                progressBarLogin.setVisibility(View.GONE);
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(getContext(), R.string.error_login_string, Toast.LENGTH_SHORT).show();

                progressBarLogin.setVisibility(View.GONE);
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            }

        });

    }
}