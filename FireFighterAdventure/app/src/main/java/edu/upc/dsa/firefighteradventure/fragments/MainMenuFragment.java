package edu.upc.dsa.firefighteradventure.fragments;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btnPlay).setOnClickListener(this::btnPlayClick);
        view.findViewById(R.id.btnProfile).setOnClickListener(this::btnProfileClick);
        view.findViewById(R.id.btnInventory).setOnClickListener(this::btnInventoryClick);
        view.findViewById(R.id.btnShop).setOnClickListener(this::btnShopClick);
        view.findViewById(R.id.btnLogout).setOnClickListener(this::btnLogoutClick);

        mainActivity = (MainActivity) getActivity();

    }

    public void btnPlayClick(android.view.View u) {

        Navigation.findNavController(view).navigate(R.id.action_mainMenuFragment_to_gameFragment);

    }

    public void btnProfileClick(android.view.View u) {


        Navigation.findNavController(view).navigate(R.id.action_mainMenuFragment_to_profileFragment);

    }

    public void btnInventoryClick(android.view.View u) {

        Navigation.findNavController(view).navigate(R.id.action_mainMenuFragment_to_inventoryFragment);


    }

    public void btnShopClick(android.view.View u) {

        Navigation.findNavController(view).navigate(R.id.action_mainMenuFragment_to_shopFragment);


    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {

            switch (which){

                case DialogInterface.BUTTON_POSITIVE:
                    break;

                case DialogInterface.BUTTON_NEGATIVE:

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
}