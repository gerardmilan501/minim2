package edu.upc.dsa.firefighteradventure;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface UsersService {
    @GET("users")
    Call<List<Users>> listUsers();

    @POST("users/register/{username}/{password}")
    Call<ResponseBody> registerUser(
    @Path("username") String username,
    @Path("password") String password
    );

    @GET("users/login/{username}/{password}")
    Call<ResponseBody> loginUser(
            @Path("username") String username,
            @Path("password") String password
    );

}
