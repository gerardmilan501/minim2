package edu.upc.dsa.firefighteradventure.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import edu.upc.dsa.firefighteradventure.MainActivity;
import edu.upc.dsa.firefighteradventure.R;
import edu.upc.dsa.firefighteradventure.models.UserCredentialsParameters;
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

public class ProfileGeneralFragment extends Fragment {

    private View view;
    MainActivity mainActivity;

    private Button btnBackProfileGeneral;

    private String username;

    private TextView tvProfileUsername;
    private TextView tvProfileFollowers;
    private TextView tvProfileFollowing;
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

        tvProfileUsername = view.findViewById(R.id.tvUsername);

        tvProfileFollowers = view.findViewById(R.id.tvFollowers);

        btnBackProfileGeneral = view.findViewById(R.id.btnBackProfileGeneral);

        tvProfileFollowing = view.findViewById(R.id.tvfollowing);

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

        Call<UserCredentialsParameters> resp = mainActivity.getUserService().getUsuari(getArguments().getString("user"));

        resp.enqueue(new Callback<UserCredentialsParameters>(){

            @Override
            public void onResponse(Call<UserCredentialsParameters> call, Response<UserCredentialsParameters> response) {

                mainActivity.setLoadingData(false);

                if (response.code() == 200) {

                   UserCredentialsParameters resp = response.body();
                    tvProfileFollowers.setText(String.valueOf(resp.getFollowers()));
                    tvProfileFollowing.setText(String.valueOf(resp.getFollowing()));
                    tvProfileUsername.setText(resp.getLogin());



                }

                else {

                    switch (response.code()) {

                        case 404:
                            Toast.makeText(getContext(), R.string.user_not_exists_string, Toast.LENGTH_SHORT).show();
                            break;

                        default:
                            Navigation.findNavController(view).navigate(R.id.connectionErrorFragment);
                            break;

                    }

                }

            }

            @Override
            public void onFailure(Call<UserCredentialsParameters> call, Throwable t) {

                mainActivity.setLoadingData(false);
                Navigation.findNavController(view).navigate(R.id.connectionErrorFragment);

            }



        });
    }

    public void btnBackProfileGeneralClick(android.view.View u) {

        mainActivity.goBack();

    }

}