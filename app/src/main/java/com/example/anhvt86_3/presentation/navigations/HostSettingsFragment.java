package com.example.anhvt86_3.presentation.navigations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.anhvt86_3.R;
import com.example.anhvt86_3.databinding.FragmentHostSettingsBinding;

public class HostSettingsFragment extends Fragment {

private FragmentHostSettingsBinding binding;
    public MutableLiveData<NavController> settingsNavController = new MutableLiveData<>();
    private NavController navController = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHostSettingsBinding.inflate(inflater, container, false);
        NavHostFragment nestedNavHostFragment = (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.nested_nav_host_fragment_settings);
        if (nestedNavHostFragment != null) {
            navController = nestedNavHostFragment.getNavController();
        }
        settingsNavController.setValue(navController);
        return binding.getRoot();
    }

    public MutableLiveData<NavController> getSettingsNavController() {
        return settingsNavController;
    }
}