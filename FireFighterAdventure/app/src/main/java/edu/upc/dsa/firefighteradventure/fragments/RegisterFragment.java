package edu.upc.dsa.firefighteradventure.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import edu.upc.dsa.firefighteradventure.R;
import edu.upc.dsa.firefighteradventure.models.RegisterCredentials;
import edu.upc.dsa.firefighteradventure.services.UsersService;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class RegisterFragment extends Fragment {

    View view;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_register, container, false);

        view.findViewById(R.id.btnRegister).setOnClickListener(this::btnSignupClick);

        return view;
    }

    public void btnSignupClick(android.view.View u){

        EditText etUsernameSignup = view.findViewById(R.id.etUsernameSignup);
        EditText etPwdSignup = view.findViewById(R.id.etPwdSignup);
        EditText etConfirmPwdSingup = view.findViewById(R.id.etConfirmPwdSignup);
        EditText etEmail = view.findViewById(R.id.etEmail);
        EditText etBirthdate = view.findViewById(R.id.etBirthdate);

        if (etUsernameSignup.getText().toString().equals("")) {

            Toast.makeText(getContext(), R.string.write_username_string, Toast.LENGTH_SHORT).show();
            return;

        } else if (etPwdSignup.getText().toString().equals("")) {

            Toast.makeText(getContext(), R.string.write_password_string, Toast.LENGTH_SHORT).show();
            return;

        } else if (etConfirmPwdSingup.getText().toString().equals("")) {

            Toast.makeText(getContext(), R.string.write_confirm_password_string, Toast.LENGTH_SHORT).show();
            return;

        } else if (etEmail.getText().toString().equals("")) {

            Toast.makeText(getContext(), R.string.write_email_string, Toast.LENGTH_SHORT).show();
            return;

        } else if (etBirthdate.getText().toString().equals((""))) {

            Toast.makeText(getContext(), R.string.write_birthdate_string, Toast.LENGTH_SHORT).show();
            return;

        } else if (!etConfirmPwdSingup.getText().toString().equals(etPwdSignup.getText().toString())) {

            Toast.makeText(getContext(), R.string.passwords_not_match_string, Toast.LENGTH_SHORT).show();
            return;

        }

        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBarRegister);

        progressBar.setProgress(0);
        progressBar.setVisibility(View.VISIBLE);

        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/dsaApp/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        UsersService service = retrofit.create(UsersService.class);

        Call<ResponseBody> resp = service.registerUser(new RegisterCredentials(etUsernameSignup.getText().toString(), etPwdSignup.getText().toString(), etEmail.getText().toString(), etBirthdate.getText().toString()));

        resp.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.d("DebugTag", String.valueOf(response.code()));

                if (response.code() == 600) {

                    Toast.makeText(getContext(), R.string.write_username_string, Toast.LENGTH_SHORT).show();

                } else if (response.code() == 601) {

                    Toast.makeText(getContext(), R.string.write_password_string, Toast.LENGTH_SHORT).show();

                } else if (response.code() == 201) {

                    Toast.makeText(getContext(), R.string.succesful_register_string, Toast.LENGTH_SHORT).show();

                } else if (response.code() == 250) {

                    Toast.makeText(getContext(), R.string.user_already_exists_string, Toast.LENGTH_SHORT).show();

                } else if (response.code() == 604) {

                    Toast.makeText(getContext(), R.string.username_short_long_string, Toast.LENGTH_SHORT).show();

                } else if (response.code() == 605) {

                    Toast.makeText(getContext(), R.string.password_short_long_string, Toast.LENGTH_SHORT).show();

                } else if (response.code() == 606) {

                    Toast.makeText(getContext(), R.string.email_short_long_string, Toast.LENGTH_SHORT).show();

                } else if (response.code() == 607) {

                    Toast.makeText(getContext(), R.string.too_young_string, Toast.LENGTH_SHORT).show();

                }

                ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBarRegister);
                progressBar.setVisibility(View.GONE);
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(getContext(), R.string.error_register_string, Toast.LENGTH_SHORT).show();

                ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBarRegister);
                progressBar.setVisibility(View.GONE);
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            }
        });

        Log.d("DebugTag","Usuario ha pulsado el botón para registrarse.");

    }

}