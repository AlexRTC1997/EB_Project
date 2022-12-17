package com.example.eb_project.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eb_project.R;
import com.example.eb_project.entities.Measurement;

import java.util.ArrayList;

public class MeasurementListAdapter extends RecyclerView.Adapter<MeasurementListAdapter.MeasurementViewHolder> {
    ArrayList<Measurement> measurementList;

    public MeasurementListAdapter(ArrayList<Measurement> measurementList) {
        this.measurementList = measurementList;
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

        }
    }

}
