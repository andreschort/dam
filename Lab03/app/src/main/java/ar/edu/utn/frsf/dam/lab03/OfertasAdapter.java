package ar.edu.utn.frsf.dam.lab03;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.text.DecimalFormat;

/**
 * Created by gabriel on 9/8/2016.
 */
public class OfertasAdapter extends BaseAdapter {
    private Context context;
    private Trabajo[] jobs;

    public OfertasAdapter(Context context, Trabajo[] jobs) {
        super();
        this.context = context;
        this.jobs = jobs;
    }

    @Override
    public int getCount() {
        return this.jobs.length;
    }

    @Override
    public Object getItem(int position) {
        return this.jobs[position];
    }

    @Override
    public long getItemId(int position) {
        return this.jobs[position].getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = convertView;

        if (row == null){
            row = inflater.inflate(R.layout.oferta_item, parent, false);
        }

        TrabajoViewHolder holder = (TrabajoViewHolder) row.getTag();
        if (holder == null) {
            holder = new TrabajoViewHolder(row);
            row.setTag(holder);
        }

        DecimalFormat df = new DecimalFormat("#.##");

        Trabajo job = this.jobs[position];
        holder.category.setText(job.getCategoria().getDescripcion());
        holder.title.setText(job.getDescripcion());
        holder.horasPresupuestadas.setText(job.getHorasPresupuestadas().toString());
        holder.maxRate.setText(df.format(job.getPrecioMaximoHora()));
        holder.english.setEnabled(false);
        holder.english.setChecked(job.getRequiereIngles());

        switch (job.getMonedaPago()){
            case 1:
                holder.currency.setImageResource(R.drawable.us);
                break;
            case 2:
                holder.currency.setImageResource(R.drawable.eu);
                break;
            case 3:
                holder.currency.setImageResource(R.drawable.ar);
                break;
            case 4:
                holder.currency.setImageResource(R.drawable.uk);
                break;
            case 5:
                holder.currency.setImageResource(R.drawable.br);
                break;
        }

        return row;
    }
}
