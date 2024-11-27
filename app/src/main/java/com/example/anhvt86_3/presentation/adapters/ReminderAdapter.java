package com.example.anhvt86_3.presentation.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anhvt86_3.R;
import com.example.anhvt86_3.databinding.ItemReminderDetailBinding;
import com.example.anhvt86_3.domain.model.Movie;
import com.example.anhvt86_3.domain.model.Reminder;
import com.example.anhvt86_3.presentation.viewmodel.MovieViewModel;
import com.example.anhvt86_3.presentation.viewmodel.ReminderViewModel;

import java.util.List;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder> {
    private static final String TAG = "TAGTAGTAG";
    private List<Reminder> reminders;
    private ReminderViewModel reminderViewModel;
    private MovieViewModel moviesViewModel;
    private LifecycleOwner lifecycleOwner;
    private boolean isForNavHeader; // Cờ để kiểm tra

    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner;
    }

    public void setReminders(List<Reminder> reminders) {
        this.reminders = reminders;
        notifyDataSetChanged();
    }
    public void setRemindersNavHeader(List<Reminder> reminders) {
        this.reminders =reminders.size() > 3 ? reminders.subList(0, 3) : reminders;
        notifyDataSetChanged();
    }

    public ReminderAdapter(List<Reminder> reminders, ReminderViewModel viewModel, MovieViewModel moviesViewModel, boolean isForNavHeader) {
        this.reminders = reminders;
        this.reminderViewModel = viewModel;
        this.moviesViewModel = moviesViewModel;
        this.isForNavHeader = isForNavHeader;
    }

    @NonNull
    @Override
    public ReminderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemReminderDetailBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.item_reminder_detail, parent, false);
        return new ReminderViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderViewHolder holder, int position) {
        Reminder reminder = reminders.get(position);
        holder.binding.setReminder(reminder);
        // Ẩn nút xóa nếu ở NavHeader
        if (isForNavHeader) {
            holder.binding.imageDelete.setVisibility(View.GONE);
        } else {
            holder.binding.imageDelete.setVisibility(View.VISIBLE);
            holder.binding.imageDelete.setOnClickListener(view -> {
                reminderViewModel.deleteReminderByMovieId(reminder.getMovieId());
            });
        }
        Log.d(TAG, "onBindViewHolder: " + reminder.getMovieId());
        moviesViewModel.getMovieDetails(reminder.getMovieId()).observe(lifecycleOwner, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movie) {
                holder.binding.setMovie(movie);
            }
        });

        moviesViewModel.getMovieDetails(reminder.getMovieId());
    }

    @Override
    public int getItemCount() {
        return reminders.size();
    }

    class ReminderViewHolder extends RecyclerView.ViewHolder {
        final ItemReminderDetailBinding binding;

        ReminderViewHolder(ItemReminderDetailBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
