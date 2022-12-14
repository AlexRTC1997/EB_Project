package com.example.eb_project.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eb_project.R;
import com.example.eb_project.StatusDetailsActivity;
import com.example.eb_project.entities.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class StatusListAdapter extends RecyclerView.Adapter<StatusListAdapter.StatusViewHolder> {
    ArrayList<Status> statusList;
    ArrayList<Status> statusListAux;    // SearchView

    public StatusListAdapter(ArrayList<Status> statusList) {
        this.statusList = statusList;
        // SearchView
        statusListAux = new ArrayList<>();
        statusListAux.addAll(statusList);

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

            // ITEM LISTENER TO VIEW DETAILS OF EACH ITEM
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent detailsIntent = new Intent(context, StatusDetailsActivity.class);
                    detailsIntent.putExtra("statusId", statusList.get(getAdapterPosition()).getId());
                    context.startActivity(detailsIntent);
                }
            });
        }
    }

    // SearchView - Filter
    public void filter (String textToSearch) {
        int length = textToSearch.length();

        if (length == 0) {
            statusList.clear();
            statusList.addAll(statusListAux);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Status> collection = statusList.stream().
                        filter(i -> i.getDescription().toLowerCase().contains(textToSearch.toLowerCase()))
                        .collect(Collectors.toList());

                statusList.clear();
                statusList.addAll(collection);

            } else {
                for (Status s: statusListAux) {
                    if(s.getDescription().toLowerCase().contains(textToSearch.toLowerCase())) {
                        statusList.add(s);
                    }
                }
            }
        }

        notifyDataSetChanged();
    }
}
