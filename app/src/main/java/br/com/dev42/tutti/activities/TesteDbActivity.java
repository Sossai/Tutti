package br.com.dev42.tutti.activities;

import android.app.Activity;
import android.graphics.Typeface;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.dev42.tutti.R;
import br.com.dev42.tutti.dao.EscalaDao;
import br.com.dev42.tutti.helpers.DbIni;
import br.com.dev42.tutti.model.DataTsEscalas;

public class TesteDbActivity extends AppCompatActivity {

    private Activity activity = this;
    private EscalaDao dao;
    private ListView listaDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_db);

        Button btnInsertAll = (Button)findViewById(R.id.bt_insert);
        Button btnDeleteAll = (Button)findViewById(R.id.bt_delete);
        Button btnListaAll = (Button)findViewById(R.id.bt_lista_db);
        listaDb = (ListView)findViewById(R.id.lista_db_ts);

        dao = new EscalaDao(activity);

        btnInsertAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<DataTsEscalas> lista = new ArrayList<DataTsEscalas>();
                DataTsEscalas dt;

//                    opcaoid, escalaid, telaid, sequencia, imagem, som1, som2, som3

/*                dt = new DataTsEscalas(2,1,1,1,"Opcao2_Escala1_Tela1", "la_som1", "", "");
                lista.add(dt);
                dt = new DataTsEscalas(2,1,2,1,"Opcao2_Escala1_Tela2", "la_som2", "", "");
                lista.add(dt);

                dt = new DataTsEscalas(2,1,1,1,"Opcao2_Escala1_Tela1", "la_som1", "", "");
                lista.add(dt);
                dt = new DataTsEscalas(2,1,2,1,"Opcao2_Escala1_Tela2", "la_som2", "", "");
                lista.add(dt);

                dao.insertData(lista);*/

                DbIni dbIni = new DbIni(activity);
                dbIni.init();
                Toast.makeText(activity, "OK", Toast.LENGTH_LONG).show();
            }
        });

        btnDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dao.deleteAll();
            }
        });

        btnListaAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<DataTsEscalas> lista = dao.listarTodos();
                List<String> toS = new ArrayList<String>();

                for(DataTsEscalas dt:lista){
                    toS.add(dt.toString());
                    Log.e("DEV42", dt.toString());
                }

                ArrayAdapter adapter = new ArrayAdapter<String >(activity, android.R.layout.simple_list_item_1, toS);
                listaDb.setAdapter(adapter);
            }
        });


        TextView tstFont = (TextView)findViewById(R.id.tv_tst_font);
//        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Hymnus212.ttf");
//        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/sonata.ttf");
//        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Hymnus212.ttf");
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf");
        tstFont.setTypeface(font);
        tstFont.setText("LA#");
    }
}
