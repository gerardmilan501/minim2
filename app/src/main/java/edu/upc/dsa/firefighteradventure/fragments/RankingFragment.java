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

import java.util.List;


public class RankingFragment extends Fragment {

    private View view;

    private MainActivity mainActivity;

    private Button btnBackRanking;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private TextView tvYourPositionIs;


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
        tvYourPositionIs = view.findViewById(R.id.tvYourPositionIs);

        btnBackRanking.setOnClickListener(this::btnBackRankingClick);

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

        Call<RankingPositionResponse> rankingPos = mainActivity.getGameService().getRankingPosition(new GetUserCredentials(mainActivity.getSavedUsername()));

        rankingPos.enqueue(new Callback<RankingPositionResponse>() {

            @Override
            public void onResponse(Call<RankingPositionResponse> call, Response<RankingPositionResponse> response) {

                mainActivity.setLoadingData(false);

                if (response.code() == 201) {

                    RankingPositionResponse result = response.body();

                    tvYourPositionIs.setText(tvYourPositionIs.getText().toString() + " " + result.getPosition());

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

    public void btnBackRankingClick(android.view.View u) {

        mainActivity.goBack();

    }

}