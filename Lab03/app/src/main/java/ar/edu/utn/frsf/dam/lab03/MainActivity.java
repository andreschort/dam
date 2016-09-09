package ar.edu.utn.frsf.dam.lab03;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OfertasAdapter adapter = new OfertasAdapter(this.getApplicationContext(), Trabajo.TRABAJOS_MOCK);
        ListView listVIew = (ListView) this.findViewById(R.id.listView);
        listVIew.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_main, menu);

        return true;
    }
}
