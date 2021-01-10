package edu.upc.dsa.firefighteradventure.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.upc.dsa.firefighteradventure.MainActivity;
import edu.upc.dsa.firefighteradventure.MyAdapter;
import edu.upc.dsa.firefighteradventure.R;
import edu.upc.dsa.firefighteradventure.models.RankingPositionResponse;
import edu.upc.dsa.firefighteradventure.models.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class RankingFragment extends Fragment {

    private View view;

    private MainActivity mainActivity;

    private Button btnBackRanking;
    private Button btnSearchUsernameRanking;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private TextView tvYourPositionIs;
    private EditText etSearchUsernameRanking;

    public RankingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ranking, container, false);
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

        btnBackRanking = view.findViewById(R.id.btnBackRanking);
        btnSearchUsernameRanking = view.findViewById(R.id.btnSearchUsernameRanking);
        tvYourPositionIs = view.findViewById(R.id.tvYourPositionIs);
        etSearchUsernameRanking = view.findViewById(R.id.etSearchUsernameRanking);

        etSearchUsernameRanking.setFilters(new InputFilter[] { mainActivity.spaceFilter });
        btnBackRanking.setOnClickListener(this::btnBackRankingClick);
        btnSearchUsernameRanking.setOnClickListener(this::btnSearchUsernameRankingClick);

        mainActivity.setLoadingData(true);

        Call<List<User>> users = mainActivity.getGameService().getRanking();

        users.enqueue(new Callback<List<User>>() {

            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                mainActivity.setLoadingData(false);

                if (response.code() == 201) {

                    List<User> result = response.body();
                    recyclerView = view.findViewById(R.id.my_recycler_view);

                    recyclerView.setHasFixedSize(true);

                    layoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(layoutManager);

                    // define an adapter
                    mAdapter = new MyAdapter(result);
                    recyclerView.setAdapter(mAdapter);

                    getRankingPosition();

                } else {

                    Navigation.findNavController(view).navigate(R.id.connectionErrorFragment);

                }

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

                mainActivity.setLoadingData(false);
                Navigation.findNavController(view).navigate(R.id.connectionErrorFragment);

            }

        });

    }

    public void getRankingPosition() {

        mainActivity.setLoadingData(true);

        Call<RankingPositionResponse> rankingPos = mainActivity.getGameService().getUserPositionByUsername(mainActivity.getSavedUsername());

        rankingPos.enqueue(new Callback<RankingPositionResponse>() {

            @Override
            public void onResponse(Call<RankingPositionResponse> call, Response<RankingPositionResponse> response) {

                mainActivity.setLoadingData(false);

                switch(response.code()) {

                    case 201:
                        RankingPositionResponse result = response.body();
                        tvYourPositionIs.setText(tvYourPositionIs.getText().toString() + " " + result.getPosition());
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
            public void onFailure(Call<RankingPositionResponse> call, Throwable t) {

                mainActivity.setLoadingData(false);
                Navigation.findNavController(view).navigate(R.id.connectionErrorFragment);

            }

        });

    }

    public void btnSearchUsernameRankingClick(android.view.View u) {

        if (etSearchUsernameRanking.getText().toString().equals("")) {

            Toast.makeText(getContext(), R.string.write_username_string, Toast.LENGTH_SHORT).show();
            return;

        }

        mainActivity.setLoadingData(true);

        Call<ResponseBody> user = mainActivity.getUserService().userExists(etSearchUsernameRanking.getText().toString());

        user.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                mainActivity.setLoadingData(false);

                switch (response.code()) {

                    case 201:
                        Bundle bundle = new Bundle();
                        bundle.putString("username", etSearchUsernameRanking.getText().toString());
                        Navigation.findNavController(view).navigate(R.id.profileGeneralFragment, bundle);
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
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                mainActivity.setLoadingData(false);
                Navigation.findNavController(view).navigate(R.id.connectionErrorFragment);

            }

        });

    }

    public void btnBackRankingClick(android.view.View u) {

        mainActivity.goBack();

    }

}