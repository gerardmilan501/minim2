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



    @GET("users/{username}")
    Call<UserCredentialsParameters> getUsuari(
            @Path("username") String username
    );


}
