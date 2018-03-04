package br.com.dev42.tutti.adapters;

import android.app.Activity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import br.com.dev42.tutti.R;
import br.com.dev42.tutti.model.EscalaOpcao;

/**
 * Created by sossai on 04/06/17.
 */

public class EscalaOpcoesAdapter extends BaseAdapter {

    private List<EscalaOpcao> listaOpcoesEscala;
    private Activity activity;
    private View layout;

    public EscalaOpcoesAdapter(List<EscalaOpcao> listaOpcoesEscala, Activity activity) {
        this.listaOpcoesEscala = listaOpcoesEscala;
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EscalaOpcao opcao = listaOpcoesEscala.get(position);
        LayoutInflater inflater = activity.getLayoutInflater();
        ViewHolder holder;

        if(convertView == null){
            layout = inflater.inflate(R.layout.opcao_escala, parent, false);
            holder = new ViewHolder(layout);
            layout.setTag(holder);
        }else {
            layout = convertView;
            holder = (ViewHolder)layout.getTag();
        }

        holder.nomeOpcao.setText(opcao.getNome());

        if(opcao.isSelecionado())
            holder.selecaoOpcao.setVisibility(View.VISIBLE);
        else
            holder.selecaoOpcao.setVisibility(View.INVISIBLE);
        return layout;
    }

    @Override
    public int getCount() {
        return listaOpcoesEscala.size();
    }

    @Override
    public Object getItem(int position) {
        return listaOpcoesEscala.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        TextView nomeOpcao;
        ImageView selecaoOpcao;

        public ViewHolder(View view) {
            nomeOpcao = (TextView)view.findViewById(R.id.tv_selecao_escala);
            selecaoOpcao = (ImageView)view.findViewById(R.id.iv_mark_opcoes_escala);
        }
    }
}
