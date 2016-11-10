package ar.edu.utn.frsf.dam.lab04;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frsf.dam.lab04.modelo.Departamento;
import ar.edu.utn.frsf.dam.lab04.utils.BuscarDepartamentosTask;
import ar.edu.utn.frsf.dam.lab04.utils.BusquedaFinalizadaListener;
import ar.edu.utn.frsf.dam.lab04.utils.FormBusqueda;

public class ListaDepartamentosActivity extends AppCompatActivity implements BusquedaFinalizadaListener<Departamento> {

    private TextView tvEstadoBusqueda;
    private ListView listaAlojamientos;
    private DepartamentoAdapter departamentosAdapter;
    private List<Departamento> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alojamientos);
        lista= new ArrayList<>();
        listaAlojamientos= (ListView ) findViewById(R.id.listaAlojamientos);
        tvEstadoBusqueda = (TextView) findViewById(R.id.estadoBusqueda);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        Boolean esBusqueda = intent.getExtras().getBoolean("esBusqueda");
        if(esBusqueda){
            FormBusqueda fb = (FormBusqueda ) intent.getSerializableExtra("frmBusqueda");
            new BuscarDepartamentosTask(ListaDepartamentosActivity.this).execute(fb);
            tvEstadoBusqueda.setText("Buscando....");
            tvEstadoBusqueda.setVisibility(View.VISIBLE);
        }else{
            tvEstadoBusqueda.setVisibility(View.GONE);
            lista=Departamento.getAlojamientosDisponibles();
        }
        departamentosAdapter = new DepartamentoAdapter(ListaDepartamentosActivity.this,lista);
        listaAlojamientos.setAdapter(departamentosAdapter);
    }

    @Override
    public void busquedaFinalizada(List<Departamento> listaDepartamento) {
        departamentosAdapter.clear();
        departamentosAdapter.addAll(listaDepartamento);
        listaAlojamientos.invalidate();
        listaAlojamientos.invalidateViews();
        departamentosAdapter.notifyDataSetChanged();

        listaAlojamientos.destroyDrawingCache();
        listaAlojamientos.setVisibility(ListView.INVISIBLE);
        listaAlojamientos.setVisibility(ListView.VISIBLE);
        listaAlojamientos.refreshDrawableState();
        ((BaseAdapter) listaAlojamientos.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public void busquedaActualizada(String msg) {
        tvEstadoBusqueda.setText(" Buscando..."+msg);
    }

}
