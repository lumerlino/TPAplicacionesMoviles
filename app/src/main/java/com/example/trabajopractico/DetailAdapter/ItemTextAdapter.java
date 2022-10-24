package com.example.trabajopractico.DetailAdapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trabajopractico.DetailAdapter.ItemText;
import com.example.trabajopractico.ExtensionesKt;
import com.example.trabajopractico.R;

import java.util.List;

public class ItemTextAdapter extends RecyclerView.Adapter<ItemTextAdapter.ItemTextViewHolder> {

    private List<ItemText> itemTexts;
    private android.content.Context context;

    public ItemTextAdapter(android.content.Context context, List<ItemText> itemTexts ) {
        this.itemTexts = itemTexts;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemTextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemItemText = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_itemtext, parent, false);
        return new ItemTextViewHolder(itemItemText);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemTextViewHolder holder, int position) {
        if(itemTexts.get(position).getType()=="PERSONAJE"){
            ExtensionesKt.loadCharacterForTv(this.context,itemTexts.get(position).getUrl(), holder.tvItemText);
        }else if(itemTexts.get(position).getType()=="CASA"){
            ExtensionesKt.loadHouseForTv(this.context,itemTexts.get(position).getUrl(), holder.tvItemText);
        }
        /*holder.tvItemText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClickListener(itemTexts.get(holder.getAdapterPosition()));
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return itemTexts.size();
    }

    public class ItemTextViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemText;
        public ItemTextViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemText = itemView.findViewById(R.id.tvItemText);
        }
    }
}

