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

public class ProfileFragment extends Fragment {

    private View view;
    Button btnGotoChangePassword;
    Button btnGotoChangeEmail;
    Button btnGotoDeleteAccount;

    private MainActivity mainActivity;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
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

        btnGotoChangePassword = view.findViewById(R.id.btnGotoChangePassword);
        btnGotoChangeEmail = view.findViewById(R.id.btnGotoChangeEmail);
        btnGotoDeleteAccount = view.findViewById(R.id.btnGotoDeleteAccount);

        btnGotoChangePassword.setOnClickListener(this::btnGotoChangePasswordClick);
        btnGotoChangeEmail.setOnClickListener(this::btnGotoChangeEmailClick);
        btnGotoDeleteAccount.setOnClickListener(this::btnGotoDeleteAccountClick);

    }

    public void btnGotoChangePasswordClick(android.view.View u) {

        Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_changePasswordFragment);

    }

    public void btnGotoChangeEmailClick(android.view.View u) {

        Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_changeEmailFragment);

    }

    public void btnGotoDeleteAccountClick(android.view.View u) {

        Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_deleteAccountFragment);

    }

}