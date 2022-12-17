package com.example.eb_project.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eb_project.R;
import com.example.eb_project.entities.Brand;

import java.util.ArrayList;

public class BrandListAdapter extends RecyclerView.Adapter<BrandListAdapter.BrandViewHolder> {
    ArrayList<Brand> brandList;

    public BrandListAdapter(ArrayList<Brand> brandList) {
        this.brandList = brandList;
    }

    @NonNull
    @Override
    public BrandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.brand_list_item, null, false);

        return new BrandViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BrandViewHolder holder, int position) {
        holder.tvBrandId.setText(String.valueOf(brandList.get(position).getId()));
        holder.tvBrandName.setText(brandList.get(position).getName());
        holder.tvBrandStatus.setText(brandList.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return brandList.size();
    }

    public class BrandViewHolder extends RecyclerView.ViewHolder {
        TextView tvBrandId, tvBrandName, tvBrandStatus;

        public BrandViewHolder(@NonNull View itemView) {
            super(itemView);

            tvBrandId = itemView.findViewById(R.id.tv_brand_list_item_id);
            tvBrandName = itemView.findViewById(R.id.tv_brand_list_item_name);
            tvBrandStatus = itemView.findViewById(R.id.tv_brand_list_item_status);

        }
    }

}
