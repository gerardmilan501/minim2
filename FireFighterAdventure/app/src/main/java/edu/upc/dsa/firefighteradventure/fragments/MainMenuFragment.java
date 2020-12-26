package edu.upc.dsa.firefighteradventure.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import edu.upc.dsa.firefighteradventure.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MainMenuFragment extends Fragment {

    private View view;

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

    public void btnLogoutClick(android.view.View u) {

        SharedPreferences prefs = getActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("username", "");
        editor.putString("password", "");
        editor.commit();

        Navigation.findNavController(view).navigate(R.id.loginRegisterFragment);

    }
}