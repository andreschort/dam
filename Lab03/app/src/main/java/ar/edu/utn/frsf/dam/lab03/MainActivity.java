package ar.edu.utn.frsf.dam.lab03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OfertasAdapter adapter = new OfertasAdapter(this.getApplicationContext(), Trabajo.TRABAJOS_MOCK.toArray(new Trabajo[Trabajo.TRABAJOS_MOCK.size()]));
        ListView listVIew = (ListView) this.findViewById(R.id.listView);
        listVIew.setAdapter(adapter);

        registerForContextMenu(listVIew);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.MnuOpc1:
                Intent intent = new Intent(this, CrearOfertaActivity.class);
                startActivityForResult(intent, 1);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        OfertasAdapter adapter = new OfertasAdapter(this.getApplicationContext(), Trabajo.TRABAJOS_MOCK.toArray(new Trabajo[Trabajo.TRABAJOS_MOCK.size()]));
        ListView listVIew = (ListView) this.findViewById(R.id.listView);
        listVIew.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (v.getId() == R.id.listView) {
            ListView lv = (ListView) v;

            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
            Trabajo job = (Trabajo) lv.getItemAtPosition(acmi.position);
            menu.setHeaderTitle(job.getDescripcion());
            menu.add(R.string.menu_main_context_apply);
            menu.add(R.string.menu_main_context_share);
        }
    }
}
