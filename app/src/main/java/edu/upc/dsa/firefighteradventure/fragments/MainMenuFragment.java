package edu.upc.dsa.firefighteradventure.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;


public class MainMenuFragment extends Fragment {

    private View view;
    private MainActivity mainActivity;

    public MainMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_main_menu, container, false);
        return view;

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btnGotoPlay).setOnClickListener(this::btnGotoPlayClick);
        view.findViewById(R.id.btnGotoProfile).setOnClickListener(this::btnGotoProfileClick);
        view.findViewById(R.id.btnGotoInventory).setOnClickListener(this::btnGotoInventoryClick);
        view.findViewById(R.id.btnGotoShop).setOnClickListener(this::btnGotoShopClick);
        view.findViewById(R.id.btnLogout).setOnClickListener(this::btnLogoutClick);
        view.findViewById(R.id.btnGotoRanking).setOnClickListener(this::btnGotoRankingClick);
        view.findViewById(R.id.btnGotoConfigurationMainMenu).setOnClickListener(this::btnGotoConfigurationMainMenuClick);

        mainActivity = (MainActivity) getActivity();
        mainActivity.setBackActivated(false);

        if (!mainActivity.isNetworkConnected()) {

            Navigation.findNavController(view).navigate(R.id.noInternetConnectionFragment);
            return;

        }

        TextView tvHello = view.findViewById(R.id.tvHello);
        tvHello.setText(getString(R.string.hello_string) + " " + mainActivity.getSavedUsername() + "!");

    }

    public void btnGotoPlayClick(android.view.View u) {

        Navigation.findNavController(view).navigate(R.id.action_mainMenuFragment_to_gameFragment);

    }

    public void btnGotoProfileClick(android.view.View u) {


        Navigation.findNavController(view).navigate(R.id.action_mainMenuFragment_to_profileFragment);

    }

    public void btnGotoInventoryClick(android.view.View u) {

        Navigation.findNavController(view).navigate(R.id.action_mainMenuFragment_to_inventoryFragment);

    }

    public void btnGotoShopClick(android.view.View u) {

        Navigation.findNavController(view).navigate(R.id.action_mainMenuFragment_to_shopFragment);


    }

    public void btnGotoRankingClick(android.view.View u) {

        Navigation.findNavController(view).navigate(R.id.action_mainMenuFragment_to_rankingFragment);

    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {

            switch (which){

                case DialogInterface.BUTTON_POSITIVE:
                    break;

                case DialogInterface.BUTTON_NEGATIVE:

                    Snackbar.make(view, R.string.logged_out_string, Snackbar.LENGTH_SHORT)
                            .show();

                    mainActivity.setSavedUsername("");
                    mainActivity.setSavedPassword("");

                    Navigation.findNavController(view).navigate(R.id.loginRegisterFragment);

                    break;

            }

        }

    };

    public void btnLogoutClick(android.view.View u) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(R.string.confirm_logout_string).setPositiveButton(R.string.no_string, dialogClickListener)
                .setNegativeButton(R.string.yes_string, dialogClickListener).setTitle(R.string.logout_string).show();

    }

    public void btnGotoConfigurationMainMenuClick(android.view.View u) {

        Navigation.findNavController(view).navigate(R.id.configurationFragment);

    }

}