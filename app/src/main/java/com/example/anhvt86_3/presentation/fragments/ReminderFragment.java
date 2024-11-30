package com.example.anhvt86_3.presentation.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.example.anhvt86_3.R;
import com.example.anhvt86_3.app.di.MyApplication;
import com.example.anhvt86_3.databinding.FragmentReminderBinding;
import com.example.anhvt86_3.domain.model.Reminder;
import com.example.anhvt86_3.presentation.adapters.ReminderAdapter;
import com.example.anhvt86_3.presentation.viewmodel.MovieViewModel;
import com.example.anhvt86_3.presentation.viewmodel.ReminderViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class ReminderFragment extends Fragment {
    private FragmentReminderBinding binding;
    ReminderAdapter adapter;
    @Inject
    ReminderViewModel reminderViewModel;
    @Inject
    MovieViewModel movieViewModel;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((MyApplication) requireContext().getApplicationContext()).appComponent.inject(this);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReminderBinding.inflate(inflater, container, false);
        movieViewModel.setProfileReminderFragment(R.id.reminderFragment);

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                NavController navController = Navigation.findNavController(binding.getRoot());
                boolean didNavigateUp = navController.popBackStack();
            }
        });
        return binding.getRoot();  }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ReminderAdapter(new ArrayList<>(), reminderViewModel, movieViewModel, false);
        adapter.setLifecycleOwner(getViewLifecycleOwner());
        binding.recyclerViewReminders.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewReminders.setAdapter(adapter);

        reminderViewModel.getAllReminders().observe(getViewLifecycleOwner(), this::updateReminders);
    }

    private void updateReminders(List<Reminder> reminders) {
        if (reminders != null) {
            adapter.setReminders(reminders);
        }
    }
}