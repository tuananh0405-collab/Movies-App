package com.example.anhvt86_3.presentation.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.example.anhvt86_3.databinding.FragmentReminderBinding;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class ReminderFragment extends Fragment {
    private FragmentReminderBinding binding;
//    ReminderAdapter adapter;
//    @Inject
//    ReminderViewModel reminderViewModel;
//    @Inject
//    MovieViewModel movieViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReminderBinding.inflate(inflater, container, false);

        return binding.getRoot();  }
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        ((MainActivity) getActivity()).getAppComponent().inject(this);
//        // Set up RecyclerView
//        adapter = new ReminderAdapter(new ArrayList<>(), reminderViewModel, movieViewModel, false);
//        adapter.setLifecycleOwner(getViewLifecycleOwner());
//        binding.recyclerViewReminders.setLayoutManager(new LinearLayoutManager(getContext()));
//        binding.recyclerViewReminders.setAdapter(adapter);
//
//        reminderViewModel.getAllReminders().observe(getViewLifecycleOwner(), this::updateReminders);
//
//
//    }
//
//    private void updateReminders(List<Reminder> reminders) {
//        if (reminders != null) {
//            adapter.setReminders(reminders);
//        }
//    }
}