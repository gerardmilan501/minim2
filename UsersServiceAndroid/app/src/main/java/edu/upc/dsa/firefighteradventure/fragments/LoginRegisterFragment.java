package edu.upc.dsa.firefighteradventure.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import edu.upc.dsa.firefighteradventure.R;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LoginRegisterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    View view;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginRegisterFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static LoginRegisterFragment newInstance(String param1, String param2) {
        LoginRegisterFragment fragment = new LoginRegisterFragment();
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

        view = inflater.inflate(R.layout.fragment_loginregister, container, false);

        view.findViewById(R.id.btnGotoLogin).setOnClickListener(this::btnGotoLoginClick);
        view.findViewById(R.id.btnGotoRegister).setOnClickListener(this::btnGotoRegisterClick);

        return view;
    }

    public void btnGotoRegisterClick(android.view.View u){
        Log.d("DebugTag","Usuario quiere acceder al Register.");

        Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_registerFragment);


    }

    public void btnGotoLoginClick(android.view.View v){
        Log.d("DebugTag","Usuario quiere acceder al Login.");

        Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_loginFragment);


    }

}