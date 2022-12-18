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
import com.example.eb_project.BrandDetailsActivity;
import com.example.eb_project.entities.Brand;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BrandListAdapter extends RecyclerView.Adapter<BrandListAdapter.BrandViewHolder> {
    ArrayList<Brand> brandList;
    ArrayList<Brand> brandListAux;      // Search View

    public BrandListAdapter(ArrayList<Brand> brandList) {

        this.brandList = brandList;
        // SearchView
        brandListAux = new ArrayList<>();
        brandListAux.addAll(brandList);
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

            // ITEM LISTENER TO VIEW DETAILS OF EACH ITEM
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent detailsIntent = new Intent(context, BrandDetailsActivity.class);
                    detailsIntent.putExtra("brandId", brandList.get(getAdapterPosition()).getId());
                    context.startActivity(detailsIntent);
                }
            });


        }
    }

    // SearchView - Filter
    public void filter (String textToSearch) {
        int length = textToSearch.length();

        if (length == 0) {
            brandList.clear();
            brandList.addAll(brandListAux);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Brand> collection = brandList.stream().
                        filter(i -> i.getName().toLowerCase().contains(textToSearch.toLowerCase()))
                        .collect(Collectors.toList());

                brandList.clear();
                brandList.addAll(collection);

            } else {
                for (Brand b: brandListAux) {
                    if(b.getName().toLowerCase().contains(textToSearch.toLowerCase())) {
                        brandList.add(b);
                    }
                }
            }
        }

        notifyDataSetChanged();
    }

}
