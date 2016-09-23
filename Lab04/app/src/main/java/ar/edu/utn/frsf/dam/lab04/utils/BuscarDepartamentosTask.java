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
        boolean precioComplies = d.getPrecio() <= b.getPrecioMaximo() && d.getPrecio() >= b.getPrecioMinimo();
        boolean ciudadComplies = d.getCiudad().equals(b.getCiudad());
        boolean fumadorComplies = d.getNoFumador() != b.getPermiteFumar();
        boolean capacidadComplies = d.getCapacidadMaxima() >= b.getHuespedes();

        return precioComplies && ciudadComplies && fumadorComplies && capacidadComplies;
    }
}
