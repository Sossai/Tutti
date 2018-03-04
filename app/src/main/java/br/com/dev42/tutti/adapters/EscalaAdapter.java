package br.com.dev42.tutti.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import br.com.dev42.tutti.R;
import br.com.dev42.tutti.model.Escala;

/**
 * Created by sossai on 03/06/17.
 */

public class EscalaAdapter extends BaseAdapter {

    private List<Escala> listEscala;
    private Activity activity;
    private View layout;

    public EscalaAdapter(List<Escala> listEscala, Activity activity) {
        this.listEscala = listEscala;
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Escala escala = listEscala.get(position);
        LayoutInflater inflater = activity.getLayoutInflater();
        ViewHolder holder;

        if(convertView == null){
            layout = inflater.inflate(R.layout.escala, parent, false);
            holder = new ViewHolder(layout);
            layout.setTag(holder);
        }else {
            layout = convertView;
            holder = (ViewHolder)layout.getTag();
        }


//        if(escala.getNome().contains("#")){
//            holder.escalaExtra.setVisibility(View.VISIBLE);
//            Typeface font = Typeface.createFromAsset(activity.getAssets(), "fonts/Hymnus212.ttf");
//            holder.escalaExtra.setTypeface(font);
//            holder.escalaExtra.setTextSize(30);
//            holder.escalaExtra.setText("\"");
//            holder.escalaNome.setText(escala.getNome().replace("#",""));
//        }else
            holder.escalaNome.setText(escala.getNome());

//        GradientDrawable shape = (GradientDrawable)holder.escalaFundo.getBackground();
        GradientDrawable shape = (GradientDrawable)holder.escalaFundo.getDrawable();
//        if(position %2 == 0){
        if(escala.getTipo().equals("m")){
            shape.setColor(ContextCompat.getColor(activity, R.color.colorEscala1));
            shape.setStroke(10,ContextCompat.getColor(activity, R.color.colorEscala1));
//            ((GradientDrawable) holder.escalaFundo.getBackground()).setColor(ContextCompat.getColor(activity, R.color.colorEscala1));
            holder.escalaNome.setTextColor(ContextCompat.getColor(activity, R.color.colorNomeEscala1));
        }
        else {
            shape.setColor(ContextCompat.getColor(activity, R.color.colorEscala2));
            shape.setStroke(10,ContextCompat.getColor(activity, R.color.colorEscala2));
//            ((GradientDrawable) holder.escalaFundo.getBackground()).setColor(ContextCompat.getColor(activity, R.color.colorEscala2));
            holder.escalaNome.setTextColor(ContextCompat.getColor(activity, R.color.colorNomeEscala2));
        }
//        holder.escalaFundo.setBackground(shape);

        if(escala.isSelecionado())
            shape.setStroke(6,ContextCompat.getColor(activity, R.color.colorNomeEscala2));

        holder.escalaFundo.setImageDrawable(shape);
        return layout;
    }

    @Override
    public int getCount() {
        return listEscala.size();
    }

    @Override
    public Object getItem(int position) {
        return listEscala.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

private class ViewHolder{
    TextView escalaNome;
    ImageView escalaFundo;
    TextView escalaExtra;

    public ViewHolder(View view) {
        this.escalaNome = (TextView) view.findViewById(R.id.tv_escala_btn);
        this.escalaFundo = (ImageView)view.findViewById(R.id.iv_escala_btn);
        this.escalaExtra = (TextView)view.findViewById(R.id.tv_escala_btn_extra);
    }
}
}
