package ar.edu.utn.frsf.isi.dam.lab02;

import android.os.Environment;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    DecimalFormat f = new DecimalFormat("##.00");

    ElementoMenu selected;
    ElementoMenu[] activeList;
    boolean confirmed;
    ArrayList<ElementoMenu> pedido;

    ElementoMenu[] listaBebidas;
    ElementoMenu[] listaPlatos;
    ElementoMenu[] listaPostre;
    private ArrayList<ElementoMenu> allItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniciarListas();

        // set state
        selected = null;
        activeList = null;
        confirmed = false;
        pedido = new ArrayList<>();

        Spinner spHorario = (Spinner) this.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sp_horario, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spHorario.setAdapter(adapter);

        final TextView tvPedido = (TextView) findViewById(R.id.tvPedido);
        tvPedido.setMovementMethod(new ScrollingMovementMethod());

        final ListView lv = (ListView) findViewById(R.id.listView);

        RadioGroup radioGroup = (RadioGroup) this.findViewById(R.id.rbSeleccion);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radioButton:
                        activeList = listaPlatos;
                        break;
                    case R.id.radioButton2:
                        activeList = listaPostre;
                        break;
                    case R.id.radioButton3:
                    default:
                        activeList = listaBebidas;
                }

                ArrayAdapter<ElementoMenu> adapter = new ArrayAdapter<ElementoMenu>(MainActivity.this, android.R.layout.simple_list_item_single_choice, activeList);
                lv.setAdapter(adapter);
                selected = null;
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                selected = activeList[i];
                view.setSelected(true);
            }
        });

        final String lineSeparator = System.getProperty("line.separator");
        Button btAdd = (Button) findViewById(R.id.btAdd);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (confirmed) {
                    Toast.makeText(MainActivity.this.getApplicationContext(), R.string.msg_error_add_pedido_confirmado, Toast.LENGTH_SHORT).show();
                } else if (selected == null) {
                    Toast.makeText(MainActivity.this.getApplicationContext(), R.string.msg_error_seleccionar_elemento, Toast.LENGTH_SHORT).show();
                } else {
                    pedido.add(selected);
                    StringBuilder sb = new StringBuilder(tvPedido.getText());

                    if (sb.length() > 0) {
                        sb.append(lineSeparator);
                    }

                    sb.append(selected.getNombre());
                    tvPedido.setText(sb.toString());
                }
            }
        });


        Button btConfirm = (Button) findViewById(R.id.btConfirm);
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (confirmed) {
                    Toast.makeText(MainActivity.this.getApplicationContext(), R.string.msg_error_pedido_confirmado, Toast.LENGTH_SHORT).show();
                } else {
                    confirmed = true;
                    double precio = 0;
                    for (ElementoMenu e : pedido) {
                        precio += e.getPrecio();
                    }
                    StringBuilder sb = new StringBuilder(tvPedido.getText());
                    sb.append(lineSeparator).append(f.format(precio));
                    tvPedido.setText(sb.toString());
                }
            }
        });

        Button btReset = (Button) findViewById(R.id.btReset);
        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmed = false;
                pedido = new ArrayList<>();
                tvPedido.setText("");
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        ArrayList<Integer> ids = new ArrayList<>();
        for(ElementoMenu e : pedido) {
            ids.add(e.getId());
        }

        outState.putIntegerArrayList("pedido", ids);
        outState.putBoolean("confirmed", confirmed);

        if (selected != null) {
            outState.putInt("selected", selected.getId());
        }

        if (activeList != null) {
            outState.putInt("activeList", (activeList == listaPlatos ? 1 : (activeList == listaPostre ? 2 : 3)));
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);

        confirmed = state.getBoolean("confirmed");
        if (state.containsKey("activeList")) {
            switch (state.getInt("activeList")) {
                case 1:
                    activeList = listaPlatos;
                    break;
                case 2:
                    activeList = listaPostre;
                    break;
                case 3:
                    activeList = listaBebidas;
                    break;
            }
        }

        if (state.containsKey("selected") && activeList != null) {
            selected = findElemento(state.getInt("selected"));
        }

        ArrayList<Integer> ids = state.getIntegerArrayList("pedido");
        StringBuilder sb = new StringBuilder();
        String lineSeparator = System.getProperty("line.separator");
        for(Integer id : ids) {
            ElementoMenu item = findElemento(id);
            pedido.add(item);
            sb.append(lineSeparator).append(item.getNombre());
        }
        sb.trimToSize();

        TextView tvPedido = (TextView) findViewById(R.id.tvPedido);
        tvPedido.setText(sb.toString());
    }

    private ElementoMenu findElemento(Integer id) {
        int i = 0;
        while(i < allItems.size()) {
            if (allItems.get(i).getId().equals(id)) {
                return allItems.get(i);
            }
            i++;
        }

        return null;
    }

    private void iniciarListas() {
        // inicia lista de bebidas
        int i = 1;
        listaBebidas = new ElementoMenu[7];
        listaBebidas[0]=new ElementoMenu(i++,"Coca");
        listaBebidas[1]=new ElementoMenu(i++,"Jugo");
        listaBebidas[2]=new ElementoMenu(i++,"Agua");
        listaBebidas[3]=new ElementoMenu(i++,"Soda");
        listaBebidas[4]=new ElementoMenu(i++,"Fernet");
        listaBebidas[5]=new ElementoMenu(i++,"Vino");
        listaBebidas[6]=new ElementoMenu(i++,"Cerveza");
        // inicia lista de platos
        listaPlatos= new ElementoMenu[14];
        listaPlatos[0]=new ElementoMenu(i++,"Ravioles");
        listaPlatos[1]=new ElementoMenu(i++,"Gnocchi");
        listaPlatos[2]=new ElementoMenu(i++,"Tallarines");
        listaPlatos[3]=new ElementoMenu(i++,"Lomo");
        listaPlatos[4]=new ElementoMenu(i++,"Entrecot");
        listaPlatos[5]=new ElementoMenu(i++,"Pollo");
        listaPlatos[6]=new ElementoMenu(i++,"Pechuga");
        listaPlatos[7]=new ElementoMenu(i++,"Pizza");
        listaPlatos[8]=new ElementoMenu(i++,"Empanadas");
        listaPlatos[9]=new ElementoMenu(i++,"Milanesas");
        listaPlatos[10]=new ElementoMenu(i++,"Picada 1");
        listaPlatos[11]=new ElementoMenu(i++,"Picada 2");
        listaPlatos[12]=new ElementoMenu(i++,"Hamburguesa");
        listaPlatos[13]=new ElementoMenu(i++,"Calamares");
        // inicia lista de postres
        listaPostre= new ElementoMenu[15];
        listaPostre[0]=new ElementoMenu(i++,"Helado");
        listaPostre[1]=new ElementoMenu(i++,"Ensalada de Frutas");
        listaPostre[2]=new ElementoMenu(i++,"Macedonia");
        listaPostre[3]=new ElementoMenu(i++,"Brownie");
        listaPostre[4]=new ElementoMenu(i++,"Cheescake");
        listaPostre[5]=new ElementoMenu(i++,"Tiramisu");
        listaPostre[6]=new ElementoMenu(i++,"Mousse");
        listaPostre[7]=new ElementoMenu(i++,"Fondue");
        listaPostre[8]=new ElementoMenu(i++,"Profiterol");
        listaPostre[9]=new ElementoMenu(i++,"Selva Negra");
        listaPostre[10]=new ElementoMenu(i++,"Lemon Pie");
        listaPostre[11]=new ElementoMenu(i++,"KitKat");
        listaPostre[12]=new ElementoMenu(i++,"IceCreamSandwich");
        listaPostre[13]=new ElementoMenu(i++,"Frozen Yougurth");
        listaPostre[14]=new ElementoMenu(i++,"Queso y Batata");

        allItems = new ArrayList<ElementoMenu>();
        allItems.addAll(Arrays.asList(listaBebidas));
        allItems.addAll(Arrays.asList(listaPlatos));
        allItems.addAll(Arrays.asList(listaPostre));
    }
}
