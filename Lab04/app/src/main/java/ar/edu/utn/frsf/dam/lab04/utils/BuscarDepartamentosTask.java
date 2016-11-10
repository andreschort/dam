package ar.edu.utn.frsf.dam.lab04.utils;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frsf.dam.lab04.modelo.Ciudad;
import ar.edu.utn.frsf.dam.lab04.modelo.Departamento;

/**
 * Created by martdominguez on 22/09/2016.
 */
public class BuscarDepartamentosTask extends AsyncTask<FormBusqueda,Integer,List<Departamento>> {

    private BusquedaFinalizadaListener<Departamento> listener;

    public BuscarDepartamentosTask(BusquedaFinalizadaListener<Departamento> dListener){
        this.listener = dListener;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(List<Departamento> departamentos) {
        listener.busquedaFinalizada(departamentos);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        listener.busquedaActualizada("departamento "+values[0]);

    }

    @Override
    protected List<Departamento> doInBackground(FormBusqueda... busqueda) {
        List<Departamento> todos = Departamento.getAlojamientosDisponibles();
        List<Departamento> resultado = new ArrayList<Departamento>();

        for (Departamento d : todos) {
            boolean c;
            int i = 0;
            do {
                c = complies(d, busqueda[i]);
                i++;
            } while (c && i < busqueda.length);

            if (c && i == busqueda.length) {
                resultado.add(d);
            }
        }

        return resultado;
    }

    private boolean complies(Departamento d, FormBusqueda b) {
        if (b.getPrecioMaximo() != null && d.getPrecio() > b.getPrecioMaximo())
            return false;

        if (b.getPrecioMinimo() != null && d.getPrecio() < b.getPrecioMinimo())
            return false;

        if (b.getCiudad() != null){
            if (d.getCiudad() == null) {
                return false;
            }

            if (!b.getCiudad().getId().equals(d.getCiudad().getId())) {
                return false;
            }
        }

        if (b.getPermiteFumar() && d.getNoFumador())
            return false;

        if (b.getHuespedes() != null && d.getCapacidadMaxima() < b.getHuespedes())
            return false;

        return true;
    }
}
