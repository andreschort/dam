package ar.edu.utn.frsf.dam.lab03;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by gabriel on 9/8/2016.
 */
public class TrabajoViewHolder {
    TextView category;
    TextView title;
    TextView horasPresupuestadas;
    TextView maxRate;
    TextView endDate;
    ImageView currency;
    CheckBox english;
    TextView enIngles;

    public TrabajoViewHolder(View base) {
        category = (TextView) base.findViewById(R.id.tvCategory);
        title = (TextView) base.findViewById(R.id.tvTitle);
        horasPresupuestadas = (TextView) base.findViewById(R.id.tvHorasPresupuestadas);
        maxRate = (TextView) base.findViewById(R.id.tvMaxRate);
        endDate = (TextView) base.findViewById(R.id.tvEndDate);
        currency = (ImageView) base.findViewById(R.id.ivCurrency);
        english = (CheckBox) base.findViewById(R.id.cbEnglish);
        enIngles = (TextView) base.findViewById(R.id.tvEnIngles);
    }
}
