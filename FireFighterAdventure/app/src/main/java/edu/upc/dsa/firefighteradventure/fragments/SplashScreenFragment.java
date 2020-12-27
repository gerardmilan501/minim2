package edu.upc.dsa.firefighteradventure.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import edu.upc.dsa.firefighteradventure.MainActivity;
import edu.upc.dsa.firefighteradventure.R;
import edu.upc.dsa.firefighteradventure.models.LoginCredentials;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
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

        String username = mainActivity.getSavedUsername();
        String password = mainActivity.getSavedPassword();

        if (username.equals("")) {

            Navigation.findNavController(view).navigate(R.id.action_splashScreenFragment_to_loginRegisterFragment);

        } else {

            Call<ResponseBody> resp = mainActivity.getUsersService().login(new LoginCredentials(username, password));

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

                    Toast.makeText(getContext(), R.string.error_login_string, Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(view).navigate(R.id.action_splashScreenFragment_to_loginRegisterFragment);

                }

            });

        }

    }

}