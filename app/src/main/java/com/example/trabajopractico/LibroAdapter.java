package com.example.trabajopractico;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LibroAdapter extends RecyclerView.Adapter<LibroAdapter.LibroViewHolder> {

    private List<Libro> libros;
    private OnItemClickListener onItemClickListener;

    public LibroAdapter(List<Libro> libros, OnItemClickListener onItemClickListener) {
        this.libros = libros;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public LibroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLibro = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_libro, parent, false);
        return new LibroViewHolder(itemLibro);
    }

    @Override
    public void onBindViewHolder(@NonNull LibroViewHolder holder, int position) {
        holder.btn_item_libro.setText(libros.get(position).getNombre());
        holder.btn_item_libro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClickListener(libros.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return libros.size();
    }

    public class LibroViewHolder extends RecyclerView.ViewHolder {
        Button btn_item_libro;
        public LibroViewHolder(@NonNull View itemView) {
            super(itemView);
            btn_item_libro = itemView.findViewById(R.id.btn_item_libro);
        }
    }

    interface OnItemClickListener{
        void onItemClickListener(Libro libro);
    }
}
