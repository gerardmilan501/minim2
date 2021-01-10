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

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileGeneralFragment extends Fragment {

    private View view;
    MainActivity mainActivity;

    private Button btnBackProfileGeneral;

    private String username;

    private TextView tvProfileGeneralUsername;
    private TextView tvProfileGeneralBirthdate;
    private TextView tvProfileGeneralEmail;
    private TextView tvProfileGeneralLevel;
    private TextView tvProfileGeneralScore;

    private TextView tvProfileGeneralRankingPosition;

    private ImageView ivProfileGeneralPhoto;

    public ProfileGeneralFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        username = getArguments().getString("username");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile_general, container, false);
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

        tvProfileGeneralUsername = view.findViewById(R.id.tvProfileGeneralUsername);
        tvProfileGeneralBirthdate = view.findViewById(R.id.tvProfileGeneralBirthdate);
        tvProfileGeneralEmail = view.findViewById(R.id.tvProfileGeneralEmail);
        tvProfileGeneralLevel = view.findViewById(R.id.tvProfileGeneralLevel);
        tvProfileGeneralScore = view.findViewById(R.id.tvProfileGeneralScore);

        btnBackProfileGeneral = view.findViewById(R.id.btnBackProfileGeneral);

        tvProfileGeneralRankingPosition = view.findViewById(R.id.tvProfileGeneralRankingPosition);

        btnBackProfileGeneral.setOnClickListener(this::btnBackProfileGeneralClick);

        ivProfileGeneralPhoto = view.findViewById(R.id.ivProfileGeneralPhoto);

        ivProfileGeneralPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("image", R.drawable.profile);
                Navigation.findNavController(view).navigate(R.id.imageViewFragment, bundle);
            }
        });

        mainActivity.setLoadingData(true);

        Call<ProfileResponse> user = mainActivity.getUserService().getUserByUsername(username);

        user.enqueue(new Callback<ProfileResponse>() {

            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {

                mainActivity.setLoadingData(false);

                switch (response.code()) {

                    case 200:
                        ProfileResponse result = response.body();

                        tvProfileGeneralUsername.setText(result.getUsername());
                        tvProfileGeneralBirthdate.setText(getString(R.string.birthdate_string) + ": " + result.getBirthdate());
                        tvProfileGeneralEmail.setText(result.getEmail());
                        tvProfileGeneralLevel.setText(getString(R.string.level_string) + ": " + result.getLevel());
                        tvProfileGeneralScore.setText(getString(R.string.score_string) + ": " + result.getScore());
                        tvProfileGeneralRankingPosition.setText(getString(R.string.ranking_position_string) + ": " + result.getRanking_position());

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

    public void btnBackProfileGeneralClick(android.view.View u) {

        mainActivity.goBack();

    }

}