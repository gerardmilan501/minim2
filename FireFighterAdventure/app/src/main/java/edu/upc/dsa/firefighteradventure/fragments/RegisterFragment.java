package edu.upc.dsa.firefighteradventure.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import edu.upc.dsa.firefighteradventure.MainActivity;
import edu.upc.dsa.firefighteradventure.R;
import edu.upc.dsa.firefighteradventure.models.RegisterCredentials;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class RegisterFragment extends Fragment {

    private View view;
    private EditText etUsernameRegister;
    private EditText etPasswordRegister;
    private EditText etConfirmPasswordRegister;
    private EditText etEmailRegister;
    private EditText etBirthdateRegister;
    private ProgressBar progressBarRegister;

    MainActivity mainActivity;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_register, container, false);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        etUsernameRegister = view.findViewById(R.id.etUsernameRegister);
        etPasswordRegister = view.findViewById(R.id.etPasswordRegister);
        etConfirmPasswordRegister = view.findViewById(R.id.etConfirmPasswordRegister);
        etEmailRegister = view.findViewById(R.id.etEmailRegister);
        etBirthdateRegister = view.findViewById(R.id.etBirthdateRegister);
        progressBarRegister = view.findViewById(R.id.progressBarRegister);

        view.findViewById(R.id.btnRegister).setOnClickListener(this::btnRegisterClick);

        mainActivity = (MainActivity) getActivity();

    }

    public void btnRegisterClick(android.view.View u){


        if (etUsernameRegister.getText().toString().equals("")) {

            Toast.makeText(getContext(), R.string.write_username_string, Toast.LENGTH_SHORT).show();
            return;

        } else if (etPasswordRegister.getText().toString().equals("")) {

            Toast.makeText(getContext(), R.string.write_password_string, Toast.LENGTH_SHORT).show();
            return;

        } else if (etConfirmPasswordRegister.getText().toString().equals("")) {

            Toast.makeText(getContext(), R.string.write_confirm_password_string, Toast.LENGTH_SHORT).show();
            return;

        } else if (etEmailRegister.getText().toString().equals("")) {

            Toast.makeText(getContext(), R.string.write_email_string, Toast.LENGTH_SHORT).show();
            return;

        } else if (etBirthdateRegister.getText().toString().equals((""))) {

            Toast.makeText(getContext(), R.string.write_birthdate_string, Toast.LENGTH_SHORT).show();
            return;

        } else if (!etConfirmPasswordRegister.getText().toString().equals(etPasswordRegister.getText().toString())) {

            Toast.makeText(getContext(), R.string.passwords_not_match_string, Toast.LENGTH_SHORT).show();
            return;

        }

        progressBarRegister.setProgress(0);
        progressBarRegister.setVisibility(View.VISIBLE);

        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        Call<ResponseBody> resp = mainActivity.getUsersService().register(new RegisterCredentials(etUsernameRegister.getText().toString(), etPasswordRegister.getText().toString(), etEmailRegister.getText().toString(), etBirthdateRegister.getText().toString()));

        resp.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

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

                progressBarRegister.setVisibility(View.GONE);
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(getContext(), R.string.error_register_string, Toast.LENGTH_SHORT).show();

                progressBarRegister.setVisibility(View.GONE);
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            }
        });

    }

}