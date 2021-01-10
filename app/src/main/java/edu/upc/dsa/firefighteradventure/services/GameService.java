package edu.upc.dsa.firefighteradventure.services;

import java.util.List;

import edu.upc.dsa.firefighteradventure.models.RankingPositionResponse;
import edu.upc.dsa.firefighteradventure.models.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GameService {

    @GET("game/getTopUsers")
    Call<List<User>> getRanking();

    @GET("game/getUserPositionByUsername/{username}")
    Call<RankingPositionResponse> getUserPositionByUsername(
            @Path("username") String username
    );

}
