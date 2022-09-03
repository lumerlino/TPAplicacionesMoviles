package com.example.trabajopractico;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CasaAdapter extends RecyclerView.Adapter<CasaAdapter.CasaViewHolder> {

    private List<Casa> casas;
    private OnItemClickListener onItemClickListener;

    public CasaAdapter(List<Casa> casas, OnItemClickListener onItemClickListener) {
        this.casas = casas;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public CasaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemCasa = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_casa, parent, false);
        return new CasaViewHolder(itemCasa);
    }

    @Override
    public void onBindViewHolder(@NonNull CasaViewHolder holder, int position) {
        holder.btn_item_casa.setText(casas.get(position).getNombre());
        holder.btn_item_casa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClickListener(casas.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return casas.size();
    }

    public class CasaViewHolder extends RecyclerView.ViewHolder {
        Button btn_item_casa;
        public CasaViewHolder(@NonNull View itemView) {
            super(itemView);
            btn_item_casa = itemView.findViewById(R.id.btn_item_casa);
        }
    }

    interface OnItemClickListener{
        void onItemClickListener(Casa casa);
    }
}
