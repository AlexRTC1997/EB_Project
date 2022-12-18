package com.example.eb_project.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eb_project.MeasurementDetailsActivity;
import com.example.eb_project.R;
import com.example.eb_project.entities.Measurement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MeasurementListAdapter extends RecyclerView.Adapter<MeasurementListAdapter.MeasurementViewHolder> {
    ArrayList<Measurement> measurementList;
    ArrayList<Measurement> measurementListAux;    // SearchView

    public MeasurementListAdapter(ArrayList<Measurement> measurementList) {
        this.measurementList = measurementList;
        // SearchView
        measurementListAux = new ArrayList<>();
        measurementListAux.addAll(measurementList);
    }

    @NonNull
    @Override
    public MeasurementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.measurement_list_item, null, false);

        return new MeasurementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MeasurementViewHolder holder, int position) {
        holder.tvMeasurementId.setText(String.valueOf(measurementList.get(position).getId()));
        holder.tvMeasurementName.setText(measurementList.get(position).getName());
        holder.tvMeasurementStatus.setText(measurementList.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return measurementList.size();
    }

    public class MeasurementViewHolder extends RecyclerView.ViewHolder {
        TextView tvMeasurementId, tvMeasurementName, tvMeasurementStatus;

        public MeasurementViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMeasurementId = itemView.findViewById(R.id.tv_measurement_list_item_id);
            tvMeasurementName = itemView.findViewById(R.id.tv_measurement_list_item_name);
            tvMeasurementStatus = itemView.findViewById(R.id.tv_measurement_list_item_status);

            // ITEM LISTENER TO VIEW DETAILS OF EACH ITEM
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent detailsIntent = new Intent(context, MeasurementDetailsActivity.class);
                    detailsIntent.putExtra("measurementId", measurementList.get(getAdapterPosition()).getId());
                    context.startActivity(detailsIntent);
                }
            });

        }
    }

    // SearchView - Filter
    public void filter (String textToSearch) {
        int length = textToSearch.length();

        if (length == 0) {
            measurementList.clear();
            measurementList.addAll(measurementListAux);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Measurement> collection = measurementList.stream().
                        filter(i -> i.getName().toLowerCase().contains(textToSearch.toLowerCase()))
                        .collect(Collectors.toList());

                measurementList.clear();
                measurementList.addAll(collection);

            } else {
                for (Measurement m: measurementListAux) {
                    if(m.getName().toLowerCase().contains(textToSearch.toLowerCase())) {
                        measurementList.add(m);
                    }
                }
            }
        }

        notifyDataSetChanged();
    }

}
