package edu.upc.dsa.firefighteradventure.services;

import java.util.List;

import edu.upc.dsa.firefighteradventure.models.ChangePasswordCredentials;
import edu.upc.dsa.firefighteradventure.models.LoginCredentials;
import edu.upc.dsa.firefighteradventure.models.RegisterCredentials;
import edu.upc.dsa.firefighteradventure.models.User;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface UsersService {
    @GET("users")
    Call<List<User>> listUsers();

    @POST("users/register")
    Call<ResponseBody> register(
            @Body RegisterCredentials registerCredentials
    );

    @POST("users/login")
    Call<ResponseBody> login(
            @Body LoginCredentials loginCredentials
    );

    @POST("/users/changePassword")
    Call<ResponseBody> changePassword(
            @Body ChangePasswordCredentials changePasswordCredentials
    );

}
