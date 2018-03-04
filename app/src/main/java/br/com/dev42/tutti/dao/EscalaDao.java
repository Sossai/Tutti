package br.com.dev42.tutti.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.dev42.tutti.model.DataTsEscalas;

/**
 * Created by sossai on 11/06/17.
 */

public class EscalaDao extends SQLiteOpenHelper {
    private static final int VERSAO = 1;
    private static final String TABELA = "TSEscalas";
    private static final String DATABASE = "Tutti";

    public EscalaDao(Context context) {
        super(context, DATABASE, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABELA
                + "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + "opcaoid INTEGER,"
                + "escalaid INTEGER,"
                + "telaid INTEGER,"
                + "sequencia INTEGER,"
                + "imagem STRING,"
                + "som1 STRING,"
                + "som2 STRING,"
                + "som3 STRING,"
                + "som4 STRING,"
                + "somcompleto STRING,"
                + "seekstartsomcompleto STRING,"
                + "seekendsomcompleto STRING);";
        db.execSQL(sql);
    }

    public void insertData(List<DataTsEscalas> lista){

        for(DataTsEscalas dt : lista){
            ContentValues values = new ContentValues();

//            Log.e("DEV42", dt.toString());

            values.put("opcaoid", dt.getOpcaoid());
            values.put("escalaid", dt.getEscalaid());
            values.put("telaid", dt.getTelaid());
            values.put("sequencia", dt.getSequencia());
            values.put("imagem", dt.getImagem());
            values.put("som1", dt.getSom1());
            values.put("som2", dt.getSom2());
            values.put("som3", dt.getSom3());
            values.put("som4", dt.getSom4());
            values.put("somcompleto", dt.getSomcompleto());
            values.put("seekstartsomcompleto", dt.getSeekstartsomcompleto());
            values.put("seekendsomcompleto", dt.getSeekendsomcompleto());

            getWritableDatabase().insert(TABELA,null,values);
        }
    }
    public List<DataTsEscalas> getTelas(Integer opcaoId, Integer escalaId){
        List<DataTsEscalas> listaDtEscalas = new ArrayList<>();

        String sql = "SELECT * FROM " + TABELA + " WHERE OPCAOID = " +
                        opcaoId.toString() + " and ESCALAID = " +
                        escalaId.toString() + " ORDER BY ID;";

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql,null);

        while (c.moveToNext()){
            DataTsEscalas dt = new DataTsEscalas();
            dt.setOpcaoid(c.getInt(c.getColumnIndex("opcaoid")));
            dt.setEscalaid(c.getInt(c.getColumnIndex("escalaid")));
            dt.setTelaid(c.getInt(c.getColumnIndex("telaid")));
            dt.setSequencia(c.getInt(c.getColumnIndex("sequencia")));
            dt.setImagem(c.getString(c.getColumnIndex("imagem")));
            dt.setSom1(c.getString(c.getColumnIndex("som1")));
            dt.setSom2(c.getString(c.getColumnIndex("som2")));
            dt.setSom3(c.getString(c.getColumnIndex("som3")));
            dt.setSom4(c.getString(c.getColumnIndex("som4")));
            dt.setSomcompleto(c.getString(c.getColumnIndex("somcompleto")));
            dt.setSeekstartsomcompleto(c.getInt(c.getColumnIndex("seekstartsomcompleto")));
            dt.setSeekendsomcompleto(c.getInt(c.getColumnIndex("seekendsomcompleto")));

            listaDtEscalas.add(dt);
        }
        c.close();

        return listaDtEscalas;
    }

    public DataTsEscalas getTela(Integer opcaoId, Integer escalaId, Integer telaId, Integer sequencia){
        //List<DataTsEscalas> listaDtEscalas = new ArrayList<>();

        String sql = "SELECT * FROM " + TABELA + " WHERE OPCAOID = " +
                opcaoId.toString() + " and ESCALAID = " +
                escalaId.toString() + " and TELAID = " +
                telaId.toString() + " and SEQUENCIA = " +
                sequencia.toString() + " ORDER ID;";

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql,null);

        DataTsEscalas dt = new DataTsEscalas();
        while (c.moveToNext()){
            dt.setOpcaoid(c.getInt(c.getColumnIndex("opcaoid")));
            dt.setEscalaid(c.getInt(c.getColumnIndex("escalaid")));
            dt.setTelaid(c.getInt(c.getColumnIndex("telaid")));
            dt.setSequencia(c.getInt(c.getColumnIndex("sequencia")));
            dt.setImagem(c.getString(c.getColumnIndex("imagem")));
            dt.setSom1(c.getString(c.getColumnIndex("som1")));
            dt.setSom2(c.getString(c.getColumnIndex("som2")));
            dt.setSom3(c.getString(c.getColumnIndex("som3")));
            dt.setSom4(c.getString(c.getColumnIndex("som4")));
            dt.setSomcompleto(c.getString(c.getColumnIndex("somcompleto")));
            dt.setSeekstartsomcompleto(c.getInt(c.getColumnIndex("seekstartsomcompleto")));
            dt.setSeekendsomcompleto(c.getInt(c.getColumnIndex("seekendsomcompleto")));
            //listaDtEscalas.add(dt);
        }
        c.close();

        return dt;
    }

    public List<DataTsEscalas> listarTodos(){
        List<DataTsEscalas> listaDtEscalas = new ArrayList<>();

        String sql = "SELECT * FROM " + TABELA + " ORDER BY OPCAOID, ESCALAID, TELAID;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql,null);

        while (c.moveToNext()){
            DataTsEscalas dt = new DataTsEscalas();
            dt.setOpcaoid(c.getInt(c.getColumnIndex("opcaoid")));
            dt.setEscalaid(c.getInt(c.getColumnIndex("escalaid")));
            dt.setTelaid(c.getInt(c.getColumnIndex("telaid")));
            dt.setSequencia(c.getInt(c.getColumnIndex("sequencia")));
            dt.setImagem(c.getString(c.getColumnIndex("imagem")));
            dt.setSom1(c.getString(c.getColumnIndex("som1")));
            dt.setSom2(c.getString(c.getColumnIndex("som2")));
            dt.setSom3(c.getString(c.getColumnIndex("som3")));
            dt.setSom4(c.getString(c.getColumnIndex("som4")));
            dt.setSomcompleto(c.getString(c.getColumnIndex("somcompleto")));
            dt.setSeekstartsomcompleto(c.getInt(c.getColumnIndex("seekstartsomcompleto")));
            dt.setSeekendsomcompleto(c.getInt(c.getColumnIndex("seekendsomcompleto")));

            listaDtEscalas.add(dt);
        }
        c.close();

        return listaDtEscalas;
    }

    public void deleteAll(){
        String args[] = {"0"};
        getWritableDatabase().delete(TABELA,"id>?",args);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
