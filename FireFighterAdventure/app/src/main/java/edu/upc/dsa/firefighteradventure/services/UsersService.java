package edu.upc.dsa.firefighteradventure.services;

import java.util.List;

import edu.upc.dsa.firefighteradventure.models.LoginCredentials;
import edu.upc.dsa.firefighteradventure.models.RegisterCredentials;
import edu.upc.dsa.firefighteradventure.models.Users;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface UsersService {
    @GET("users")
    Call<List<Users>> listUsers();

    @POST("users/register")
    Call<ResponseBody> registerUser(
            @Body RegisterCredentials registerCredentials
    );

    @POST("users/login")
    Call<ResponseBody> loginUser(
            @Body LoginCredentials loginCredentials
    );


}
