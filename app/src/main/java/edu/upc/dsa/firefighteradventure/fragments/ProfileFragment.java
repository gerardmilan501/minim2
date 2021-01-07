package edu.upc.dsa.firefighteradventure.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import edu.upc.dsa.firefighteradventure.MainActivity;
import edu.upc.dsa.firefighteradventure.R;
import edu.upc.dsa.firefighteradventure.models.Credentials.GetUserCredentials;
import edu.upc.dsa.firefighteradventure.models.RankingPositionResponse;
import edu.upc.dsa.firefighteradventure.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ProfileFragment extends Fragment {

    private View view;
    private Button btnGotoChangePassword;
    private Button btnGotoChangeEmail;
    private Button btnGotoDeleteAccount;

    private Button btnBackProfile;

    private TextView tvProfileUsername;
    private TextView tvProfileBirthdate;
    private TextView tvProfileEmail;
    private TextView tvProfileLevel;
    private TextView tvProfileScore;
    private TextView tvProfileRankingPosition;

    private MainActivity mainActivity;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
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

        btnGotoChangePassword = view.findViewById(R.id.btnGotoChangePassword);
        btnGotoChangeEmail = view.findViewById(R.id.btnGotoChangeEmail);
        btnGotoDeleteAccount = view.findViewById(R.id.btnGotoDeleteAccount);
        btnBackProfile = view.findViewById(R.id.btnBackProfile);

        btnGotoChangePassword.setOnClickListener(this::btnGotoChangePasswordClick);
        btnGotoChangeEmail.setOnClickListener(this::btnGotoChangeEmailClick);
        btnGotoDeleteAccount.setOnClickListener(this::btnGotoDeleteAccountClick);
        btnBackProfile.setOnClickListener(this::btnBackProfileClick);

        tvProfileUsername = view.findViewById(R.id.tvProfileUsername);
        tvProfileBirthdate = view.findViewById(R.id.tvProfileBirthdate);
        tvProfileEmail = view.findViewById(R.id.tvProfileEmail);
        tvProfileLevel = view.findViewById(R.id.tvProfileLevel);
        tvProfileScore = view.findViewById(R.id.tvProfileScore);
        tvProfileRankingPosition = view.findViewById(R.id.tvProfileRankingPosition);

        mainActivity.setLoadingData(true);

        Call<User> user = mainActivity.getUserService().getUserByUsername(new GetUserCredentials(mainActivity.getSavedUsername()));

        user.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                mainActivity.setLoadingData(false);

                if (response.code() == 200) {

                    User result = response.body();

                    tvProfileUsername.setText(result.getUsername());
                    tvProfileBirthdate.setText(getString(R.string.birthdate_string) + ": " + result.getBirthdate());
                    tvProfileEmail.setText(result.getEmail());
                    tvProfileLevel.setText(getString(R.string.level_string) + ": " + result.getLevel());
                    tvProfileScore.setText(getString(R.string.score_string) + ": " + result.getScore());

                    getRankingPosition();

                } else {

                    Navigation.findNavController(view).navigate(R.id.connectionErrorFragment);

                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                mainActivity.setLoadingData(false);
                Navigation.findNavController(view).navigate(R.id.connectionErrorFragment);

            }

        });

    }

    public void btnGotoChangePasswordClick(android.view.View u) {

        Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_changePasswordFragment);

    }

    public void btnGotoChangeEmailClick(android.view.View u) {

        Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_changeEmailFragment);

    }

    public void btnGotoDeleteAccountClick(android.view.View u) {

        Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_deleteAccountFragment);

    }

    public void btnBackProfileClick(android.view.View u) {

        mainActivity.goBack();

    }

    public void getRankingPosition() {

        mainActivity.setLoadingData(true);

        Call<RankingPositionResponse> rankingPos = mainActivity.getGameService().getRankingPosition(new GetUserCredentials(mainActivity.getSavedUsername()));

        rankingPos.enqueue(new Callback<RankingPositionResponse>() {

            @Override
            public void onResponse(Call<RankingPositionResponse> call, Response<RankingPositionResponse> response) {

                mainActivity.setLoadingData(false);

                if (response.code() == 201) {

                    RankingPositionResponse result = response.body();

                    tvProfileRankingPosition.setText(getString(R.string.ranking_position_string) + ": " + result.getPosition());

                } else {

                    Navigation.findNavController(view).navigate(R.id.connectionErrorFragment);

                }

            }

            @Override
            public void onFailure(Call<RankingPositionResponse> call, Throwable t) {

                mainActivity.setLoadingData(false);
                Navigation.findNavController(view).navigate(R.id.connectionErrorFragment);

            }

        });

    }

}