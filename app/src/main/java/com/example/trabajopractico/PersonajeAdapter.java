package com.example.trabajopractico;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class PersonajeAdapter extends RecyclerView.Adapter<PersonajeAdapter.PersonajeViewHolder>{

    private List<Personaje> personajes;
    private OnItemClickListener onItemClickListener;

    public PersonajeAdapter(List<Personaje> personajes, OnItemClickListener onItemClickListener) {
        this.personajes = personajes;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public PersonajeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemPersonaje = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_personaje, parent, false);
        return new PersonajeViewHolder(itemPersonaje);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonajeViewHolder holder, int position) {
        holder.btn_item_personaje.setText(personajes.get(position).getNombreCompleto());
        holder.btn_item_personaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClickListener(personajes.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return personajes.size();
    }

    public class PersonajeViewHolder extends RecyclerView.ViewHolder {
        Button btn_item_personaje;
        public PersonajeViewHolder(@NonNull View itemView) {
            super(itemView);
            btn_item_personaje = itemView.findViewById(R.id.btn_item_personaje);
        }
    }

    interface OnItemClickListener{
        void onItemClickListener(Personaje personaje);
    }
}
