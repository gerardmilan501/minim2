package edu.upc.dsa.firefighteradventure.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import edu.upc.dsa.firefighteradventure.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MainMenuFragment extends Fragment {

    View view;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainMenuFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MainMenuFragment newInstance(String param1, String param2) {
        MainMenuFragment fragment = new MainMenuFragment();
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
        view = inflater.inflate(R.layout.fragment_main_menu, container, false);

        view.findViewById(R.id.btnPlay).setOnClickListener(this::btnPlayClick);
        view.findViewById(R.id.btnProfile).setOnClickListener(this::btnProfileClick);
        view.findViewById(R.id.btnInventory).setOnClickListener(this::btnInventoryClick);
        view.findViewById(R.id.btnShop).setOnClickListener(this::btnShopClick);
        view.findViewById(R.id.btnLogout).setOnClickListener(this::btnLogoutClick);

        return view;
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