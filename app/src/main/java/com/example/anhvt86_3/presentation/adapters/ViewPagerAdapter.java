package com.example.anhvt86_3.presentation.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.anhvt86_3.presentation.navigations.HostAboutFragment;
import com.example.anhvt86_3.presentation.navigations.HostFavoriteFragment;
import com.example.anhvt86_3.presentation.navigations.HostListFragment;
import com.example.anhvt86_3.presentation.navigations.HostSettingsFragment;

import java.util.HashMap;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public HashMap<Integer, MutableLiveData<NavController>> navControllerMap = new HashMap<>();

    public HashMap<Integer, MutableLiveData<NavController>> getNavControllerMap() {
        return navControllerMap;
    }
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                HostListFragment listNavHostFragment = new HostListFragment();
                listNavHostFragment.getViewLifecycleOwnerLiveData().observeForever(lifecycleOwner -> {
                    if (lifecycleOwner != null) {
                        navControllerMap.put(position, listNavHostFragment.getListNavController());
                    }
                });
                return listNavHostFragment;
            case 1:
                HostFavoriteFragment favoriteNavHostFragment = new HostFavoriteFragment();
                favoriteNavHostFragment.getViewLifecycleOwnerLiveData().observeForever(lifecycleOwner -> {
                    if (lifecycleOwner != null) {
                        navControllerMap.put(position, favoriteNavHostFragment.getFavoriteNavController());
                    }
                });
                return favoriteNavHostFragment;
            case 2:
                HostSettingsFragment settingsNavHostFragment = new HostSettingsFragment();
                settingsNavHostFragment.getViewLifecycleOwnerLiveData().observeForever(lifecycleOwner -> {
                    if (lifecycleOwner != null) {
                        navControllerMap.put(position, settingsNavHostFragment.getSettingsNavController());
                    }
                });
                return settingsNavHostFragment;
            case 3:
                HostAboutFragment aboutNavHostFragment = new HostAboutFragment();
                aboutNavHostFragment.getViewLifecycleOwnerLiveData().observeForever(lifecycleOwner -> {
                    if (lifecycleOwner != null) {
                        navControllerMap.put(position, aboutNavHostFragment.getAboutNavController());
                    }
                });
                return aboutNavHostFragment;
            default:
                throw new IllegalArgumentException("Invalid position: " + position);
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
