package com.example.eb_project.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eb_project.R;
import com.example.eb_project.entities.Status;

import java.util.ArrayList;

public class StatusListAdapter extends RecyclerView.Adapter<StatusListAdapter.StatusViewHolder> {
    ArrayList<Status> statusList;

    public StatusListAdapter(ArrayList<Status> statusList) {
        this.statusList = statusList;
    }


    @NonNull
    @Override
    public StatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.status_list_item, null, false);

        return new StatusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatusViewHolder holder, int position) {
        holder.tvStatusId.setText(statusList.get(position).getId());
        holder.tvStatusDescription.setText(statusList.get(position).getDescription());
        holder.tvStatusStatus.setText(statusList.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return statusList.size();
    }

    public class StatusViewHolder extends RecyclerView.ViewHolder {
        TextView tvStatusId, tvStatusDescription, tvStatusStatus;

        public StatusViewHolder(@NonNull View itemView) {
            super(itemView);

            tvStatusId = itemView.findViewById(R.id.tv_status_list_item_id);
            tvStatusDescription = itemView.findViewById(R.id.tv_status_list_item_description);
            tvStatusStatus = itemView.findViewById(R.id.tv_status_status);
        }
    }
}
