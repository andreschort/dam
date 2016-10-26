package ar.edu.utn.frsf.dam.lab03;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CrearOfertaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_oferta);

        final Spinner spCategorias = (Spinner) this.findViewById(R.id.spCategoria);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this.getApplicationContext(), android.R.layout.simple_dropdown_item_1line, Categoria.CATEGORIAS_MOCK);
        spCategorias.setAdapter(adapter);

        final EditText etDescripcion = (EditText)this.findViewById(R.id.etTitulo);
        final EditText etHours = (EditText)this.findViewById(R.id.etHoras);
        final EditText etRate = (EditText)this.findViewById(R.id.etRate);
        final RadioGroup rgCurrency = (RadioGroup)this.findViewById(R.id.rgCurrency);
        final EditText etFechaFin = (EditText)this.findViewById(R.id.etFechaFin);
        final CheckBox cbEnIngles = (CheckBox)this.findViewById(R.id.cbEnIngles);

        Button button = (Button)this.findViewById(R.id.btGuardar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Trabajo job = new Trabajo();
                job.setDescripcion(etDescripcion.getText().toString());
                job.setHorasPresupuestadas(Integer.parseInt(etHours.getText().toString()));
                job.setPrecioMaximoHora(Double.parseDouble(etRate.getText().toString()));

                DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                try {
                    job.setFechaEntrega(format.parse(etFechaFin.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                job.setRequiereIngles(cbEnIngles.isChecked());

                switch (rgCurrency.getCheckedRadioButtonId()) {
                    case R.id.rbUs:
                        job.setMonedaPago(1);
                        break;
                    case R.id.rbEu:
                        job.setMonedaPago(2);
                        break;
                    case R.id.rbAr:
                        job.setMonedaPago(3);
                        break;
                    case R.id.rbUk:
                        job.setMonedaPago(4);
                        break;
                    case R.id.rbBr:
                        job.setMonedaPago(5);
                        break;
                }

                Trabajo.TRABAJOS_MOCK.add(job);
                finish();
            }
        });
    }
}
