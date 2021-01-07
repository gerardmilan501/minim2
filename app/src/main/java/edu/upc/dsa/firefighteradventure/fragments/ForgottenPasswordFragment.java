package edu.upc.dsa.firefighteradventure.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import edu.upc.dsa.firefighteradventure.MainActivity;
import edu.upc.dsa.firefighteradventure.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class ForgottenPasswordFragment extends Fragment {

    private View view;
    private MainActivity mainActivity;

    private Button btnBackForgottenPassword;
    private Button btnRecoverPassword;

    private EditText etUsernameForgottenPassword;
    private EditText etEmailForgottenPassword;

    public ForgottenPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_forgotten_password, container, false);
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

        btnBackForgottenPassword = view.findViewById(R.id.btnBackForgottenPassword);
        btnRecoverPassword = view.findViewById(R.id.btnRecoverPassword);
        etUsernameForgottenPassword = view.findViewById(R.id.etUsernameForgottenPassword);
        etEmailForgottenPassword = view.findViewById(R.id.etEmailForgottenPassword);

        btnBackForgottenPassword.setOnClickListener(this::btnBackForgottenPasswordClick);
        btnRecoverPassword.setOnClickListener(this::btnRecoverPasswordClick);

    }

    public void btnBackForgottenPasswordClick(android.view.View u) {

        mainActivity.goBack();

    }

    public void btnRecoverPasswordClick(android.view.View u) {

        if (etUsernameForgottenPassword.getText().toString().equals("") && etEmailForgottenPassword.getText().toString().equals("")) {

            Toast.makeText(getContext(), R.string.either_username_email_string, Toast.LENGTH_SHORT).show();

        }

    }

}