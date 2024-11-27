package com.example.anhvt86_3.presentation.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.anhvt86_3.databinding.FragmentDetailBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;


public class DetailFragment extends Fragment {
    private FragmentDetailBinding binding;
//    @Inject
//    MovieViewModel movieViewModel;
//    @Inject
//    ReminderViewModel reminderViewModel;
//    @Inject
////    FavoriteMovieDatabase favoriteMovieDatabase;
//    private int movieId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        ((MainActivity) requireActivity()).getAppComponent().inject(this);
//
//        // Lấy movieId từ arguments
//        if (getArguments() != null) {
//            movieId = getArguments().getInt("movieId");
//            Log.e("movieId",movieId+"");
//            // Quan sát LiveData
//            movieViewModel.getMovieDetails(movieId).observe(getViewLifecycleOwner(), movie -> {
//                if (movie != null) {
//                    binding.setMovie(movie); // Cập nhật dữ liệu vào binding
//                    binding.icFavorite.setImageResource(movie.isFavorite() ? R.drawable.ic_like : R.drawable.ic_dislike);
//                    Log.e("movie", movie.toString());
//                    ((MainActivity) requireActivity()).getSupportActionBar().setTitle(movie.getTitle());
//                } else {
//                    Log.e("movie", "Movie details not found");
//                }
//            });
//        }
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//        binding.recyclerViewCastAndCrew.setLayoutManager(layoutManager);
//
//        CastAndCrewAdapter adapter = new CastAndCrewAdapter(new ArrayList<>());
//        binding.recyclerViewCastAndCrew.setAdapter(adapter);
//        movieViewModel.getMovieCredits(movieId).observe(getViewLifecycleOwner(), adapter::setCastMembers);
//
//        binding.btnReminder.setOnClickListener(v -> showDateTimePicker());
//
//    }
//
//    private void showDateTimePicker() {
//        Calendar currentDate = Calendar.getInstance();
//        Calendar date = Calendar.getInstance();
//
//        new DatePickerDialog(requireContext(), (view, year, month, dayOfMonth) -> {
//            date.set(year, month, dayOfMonth);
//            new TimePickerDialog(getContext(), (view1, hourOfDay, minute) -> {
//                date.set(Calendar.HOUR_OF_DAY, hourOfDay);
//                date.set(Calendar.MINUTE, minute);
//                scheduleReminder(date.getTimeInMillis());
//            }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
//        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH)).show();
//    }
//
//    private void scheduleReminder(long reminderTime) {
//        Reminder reminder = new Reminder(reminderTime, movieId);
//        if(reminderViewModel.getReminderByMovieId(movieId) != null){
//            reminderViewModel.updateReminder(reminder);
//        }else {
//
//        reminderViewModel.insertReminder(reminder);
//        }
//
//        // Tính toán khoảng thời gian chờ
//        long delay = reminderTime - System.currentTimeMillis();
//        if (delay > 0) {
//            OneTimeWorkRequest reminderRequest = new OneTimeWorkRequest.Builder(ReminderWorker.class)
//                    .setInitialDelay(delay, TimeUnit.MILLISECONDS)
//                    .setInputData(new Data.Builder()
//                            .putString("movieTitle", binding.getMovie().getTitle()) // Truyền tiêu đề phim
//                            .putInt("notificationId", movieId) // ID thông báo duy nhất
//                            .build())
//                    .build();
//
//            WorkManager.getInstance(requireContext()).enqueue(reminderRequest);
//        }
//
//    }
}