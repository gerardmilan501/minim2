package edu.upc.dsa.firefighteradventure.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import edu.upc.dsa.firefighteradventure.MainActivity;
import edu.upc.dsa.firefighteradventure.R;
import edu.upc.dsa.firefighteradventure.models.Credentials.LoginCredentials;
import edu.upc.dsa.firefighteradventure.models.UserCredentialsParameters;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class SplashScreenFragment extends Fragment {

    private View view;
    private MainActivity mainActivity;

    public SplashScreenFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_splash_screen, container, false);
        return view;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        mainActivity = (MainActivity) getActivity();
        mainActivity.setBackActivated(false);

        if (!mainActivity.isNetworkConnected()) {

            Navigation.findNavController(view).navigate(R.id.noInternetConnectionFragment);
            return;

        }

        Call<UserCredentialsParameters> gameParameters = mainActivity.getUserService().getGameParameters();

        gameParameters.enqueue(new Callback<UserCredentialsParameters>() {

            @Override
            public void onResponse(Call<UserCredentialsParameters> call, Response<UserCredentialsParameters> response) {

                if (response.code() == 201) {

                    UserCredentialsParameters result = response.body();

                    mainActivity.setUsername_min_length(result.getUsername_min_length());
                    mainActivity.setUsername_max_length(result.getUsername_max_length());

                    mainActivity.setEmail_max_length(result.getEmail_max_length());
                    mainActivity.setEmail_min_length(result.getEmail_min_length());

                    mainActivity.setPassword_max_length(result.getPassword_max_length());
                    mainActivity.setPassword_min_length(result.getPassword_min_length());

                    mainActivity.setMin_age(result.getMin_age());

                    tryLogin();

                } else {

                    Navigation.findNavController(view).navigate(R.id.connectionErrorFragment);

                }

            }

            @Override
            public void onFailure(Call<UserCredentialsParameters> call, Throwable t) {

                Navigation.findNavController(view).navigate(R.id.connectionErrorFragment);

            }

        });

    }

    public void tryLogin() {

        String username = mainActivity.getSavedUsername();
        String password = mainActivity.getSavedPassword();

        if (username.equals("")) {

            Navigation.findNavController(view).navigate(R.id.action_splashScreenFragment_to_loginRegisterFragment);

        } else {

            Call<ResponseBody> resp = mainActivity.getUserService().login(new LoginCredentials(username, password));

            resp.enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    if (response.code() == 201) {

                        Navigation.findNavController(view).navigate(R.id.action_splashScreenFragment_to_mainMenuFragment);

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

                        mainActivity.setSavedUsername("");
                        mainActivity.setSavedPassword("");

                        Navigation.findNavController(view).navigate(R.id.action_splashScreenFragment_to_loginRegisterFragment);

                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    Navigation.findNavController(view).navigate(R.id.connectionErrorFragment);

                }

            });

        }
    }

}