package edu.upc.dsa.firefighteradventure.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import edu.upc.dsa.firefighteradventure.MainActivity;
import edu.upc.dsa.firefighteradventure.R;
import edu.upc.dsa.firefighteradventure.models.ProfileResponse;
import edu.upc.dsa.firefighteradventure.models.RankingPositionResponse;
import edu.upc.dsa.firefighteradventure.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private ImageView ivProfilePhoto;

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

        ivProfilePhoto = view.findViewById(R.id.ivProfilePhoto);

        ivProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("image", R.drawable.profile);
                Navigation.findNavController(view).navigate(R.id.imageViewFragment, bundle);
            }
        });

        mainActivity.setLoadingData(true);

        Call<ProfileResponse> user = mainActivity.getUserService().getUserByUsername(mainActivity.getSavedUsername());

        user.enqueue(new Callback<ProfileResponse>() {

            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {

                mainActivity.setLoadingData(false);

                switch (response.code()) {

                    case 200:
                        ProfileResponse result = response.body();

                        tvProfileUsername.setText(result.getUsername());
                        tvProfileBirthdate.setText(getString(R.string.birthdate_string) + ": " + result.getBirthdate());
                        tvProfileEmail.setText(result.getEmail());
                        tvProfileLevel.setText(getString(R.string.level_string) + ": " + result.getLevel());
                        tvProfileScore.setText(getString(R.string.score_string) + ": " + result.getScore());
                        tvProfileRankingPosition.setText(getString(R.string.ranking_position_string) + ": " + result.getRanking_position());

                        break;

                    case 404:
                        Toast.makeText(getContext(), R.string.user_not_exists_string, Toast.LENGTH_SHORT).show();
                        break;

                    case 601:
                        Toast.makeText(getContext(), R.string.write_username_string, Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        Navigation.findNavController(view).navigate(R.id.connectionErrorFragment);
                        break;

                }

            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {

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

}