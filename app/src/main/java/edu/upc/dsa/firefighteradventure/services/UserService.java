package edu.upc.dsa.firefighteradventure.services;

import java.util.List;

import edu.upc.dsa.firefighteradventure.models.Credentials.ChangeEmailCredentials;
import edu.upc.dsa.firefighteradventure.models.Credentials.ChangePasswordCredentials;
import edu.upc.dsa.firefighteradventure.models.Credentials.LoginCredentials;
import edu.upc.dsa.firefighteradventure.models.Credentials.RegisterCredentials;
import edu.upc.dsa.firefighteradventure.models.ProfileResponse;
import edu.upc.dsa.firefighteradventure.models.UserCredentialsParameters;
import edu.upc.dsa.firefighteradventure.models.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

//prueba nuevo repositorio
public interface UserService {

    @GET("user/getAllUsers")
    Call<List<ProfileResponse>> getAllUsers();

    @POST("user/register")
    Call<ResponseBody> register(
            @Body RegisterCredentials registerCredentials
    );

    @POST("user/login")
    Call<ResponseBody> login(
            @Body LoginCredentials loginCredentials
    );

    @GET("user/userExists/{username}")
    Call<ResponseBody> userExists(
            @Path("username") String username
    );

    @POST("user/changePassword")
    Call<ResponseBody> changePassword(
            @Body ChangePasswordCredentials changePasswordCredentials
    );

    @POST("user/changeEmail")
    Call<ResponseBody> changeEmail(
            @Body ChangeEmailCredentials changeEmailCredentials
    );

    @GET("user/getUserCredentialsParameters")
    Call<UserCredentialsParameters> getGameParameters();

    @GET("user/getUserByUsername/{username}")
    Call<ProfileResponse> getUserByUsername(
            @Path("username") String username
    );

}
