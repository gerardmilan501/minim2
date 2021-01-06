package edu.upc.dsa.firefighteradventure.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import edu.upc.dsa.firefighteradventure.MainActivity;
import edu.upc.dsa.firefighteradventure.R;
import edu.upc.dsa.firefighteradventure.models.Credentials.LoginCredentials;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class LoginFragment extends Fragment {

    private View view;
    private EditText etUsernameLogin;
    private EditText etPasswordLogin;

    private MainActivity mainActivity;

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

        mainActivity = (MainActivity) getActivity();
        mainActivity.setBackActivated(true);

        if (!mainActivity.isNetworkConnected()) {

            Navigation.findNavController(view).navigate(R.id.noInternetConnectionFragment);
            return;

        }

        etUsernameLogin = view.findViewById(R.id.etUsernameLogin);
        etPasswordLogin = view.findViewById(R.id.etPasswordLogin);

        view.findViewById(R.id.btnLogin).setOnClickListener(this::btnLoginClick);
        view.findViewById(R.id.btnGotoForgottenPassword).setOnClickListener(this::btnGotoForgottenPasswordClick);

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

        mainActivity.setLoadingData(true);

        Call<ResponseBody> resp = mainActivity.getUsersService().login(new LoginCredentials(etUsernameLogin.getText().toString(), etPasswordLogin.getText().toString()));

        resp.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                mainActivity.setLoadingData(false);

                if (response.code() == 201) {

                    Toast.makeText(getContext(), R.string.login_succesful_string, Toast.LENGTH_SHORT).show();

                    mainActivity.setSavedUsername(etUsernameLogin.getText().toString());
                    mainActivity.setSavedPassword(etPasswordLogin.getText().toString());

                    Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_mainMenuFragment);

                } else {

                    switch (response.code()) {

                        case 250:
                            Toast.makeText(getContext(), R.string.user_not_exists_string, Toast.LENGTH_SHORT).show();
                            break;
                        case 601:
                            Toast.makeText(getContext(), R.string.write_username_string, Toast.LENGTH_SHORT).show();
                            break;
                        case 602:
                            Toast.makeText(getContext(), R.string.write_password_string, Toast.LENGTH_SHORT).show();
                            break;
                        case 603:
                            Toast.makeText(getContext(), R.string.incorrect_password_string, Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(getContext(), R.string.error_login_string, Toast.LENGTH_SHORT).show();
                            break;

                    }

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                mainActivity.setLoadingData(false);
                Navigation.findNavController(view).navigate(R.id.connectionErrorFragment);

            }

        });

    }
}