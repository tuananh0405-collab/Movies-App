package com.example.anhvt86_3.presentation.adapters;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anhvt86_3.R;
import com.example.anhvt86_3.data.datasource.remote.response.CreditsResponse;
import com.example.anhvt86_3.databinding.ItemCastAndCrewBinding;
import com.example.anhvt86_3.domain.model.CastMember;

import java.util.List;

public class CastAndCrewAdapter extends RecyclerView.Adapter<CastAndCrewAdapter.CastAndCrewViewHolder> {

    private List<CastMember> castMembers;

    public CastAndCrewAdapter(List<CastMember> castMembers) {
        this.castMembers = castMembers;
    }

    @NonNull
    @Override
    public CastAndCrewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemCastAndCrewBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_cast_and_crew, parent, false);
        return new CastAndCrewViewHolder(binding);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setCastMembers(CreditsResponse creditsResponses) {
        this.castMembers = creditsResponses.getCast();
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull CastAndCrewViewHolder holder, int position) {
        CastMember castMember = castMembers.get(position);
        holder.bind(castMember);
    }

    @Override
    public int getItemCount() {
        return castMembers.size();
    }

    class CastAndCrewViewHolder extends RecyclerView.ViewHolder {
        private final ItemCastAndCrewBinding binding;

        public CastAndCrewViewHolder(ItemCastAndCrewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(CastMember castMember) {
            binding.setCastMember(castMember);
            binding.executePendingBindings();
        }
    }
}
