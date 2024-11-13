package com.example.gimnasio.logica;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.gimnasio.Modelos.Actividad;
import com.example.gimnasio.R;

import java.util.List;

public class ActividadesAdapter extends RecyclerView.Adapter<ActividadesAdapter.ActividadViewHolder> {

    private List<Actividad> listaActividades;

    public ActividadesAdapter(List<Actividad> actividades) {
        this.listaActividades = actividades;
    }

    @Override
    public ActividadViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflar el layout para cada ítem de la lista
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_actividad, parent, false);
        return new ActividadViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ActividadViewHolder holder, int position) {
        Actividad actividad = listaActividades.get(position);
        holder.fechaInicio.setText("Fecha de Inicio: " + actividad.getFechaInicio());
        holder.fechaFin.setText("Fecha de Fin: " + actividad.getFechaFin());

    }

    @Override
    public int getItemCount() {
        return listaActividades.size();
    }

    public static class ActividadViewHolder extends RecyclerView.ViewHolder {
        TextView fechaInicio, fechaFin;

        public ActividadViewHolder(View itemView) {
            super(itemView);

            fechaInicio = itemView.findViewById(R.id.fecha_inicio);
            fechaFin = itemView.findViewById(R.id.fecha_fin);

        }
    }
}
