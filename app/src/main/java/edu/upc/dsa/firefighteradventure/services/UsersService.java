package edu.upc.dsa.firefighteradventure.services;

import java.util.List;

import edu.upc.dsa.firefighteradventure.models.Credentials.ChangePasswordCredentials;
import edu.upc.dsa.firefighteradventure.models.Credentials.LoginCredentials;
import edu.upc.dsa.firefighteradventure.models.Credentials.RegisterCredentials;
import edu.upc.dsa.firefighteradventure.models.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

//prueba nuevo repositorio
public interface UsersService {

    @GET("user")
    Call<List<User>> listUsers();

    @POST("user/register")
    Call<ResponseBody> register(
            @Body RegisterCredentials registerCredentials
    );

    @POST("user/login")
    Call<ResponseBody> login(
            @Body LoginCredentials loginCredentials
    );

    @POST("/user/changePassword")
    Call<ResponseBody> changePassword(
            @Body ChangePasswordCredentials changePasswordCredentials
    );

}
