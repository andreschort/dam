package ar.edu.utn.frsf.dam.lab03;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class CrearOfertaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_oferta);

        Spinner spCategorias = (Spinner) this.findViewById(R.id.spCategoria);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this.getApplicationContext(), android.R.layout.simple_dropdown_item_1line, Categoria.CATEGORIAS_MOCK);
        spCategorias.setAdapter(adapter);
    }
}
