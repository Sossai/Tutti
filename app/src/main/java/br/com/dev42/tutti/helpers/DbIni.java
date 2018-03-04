package br.com.dev42.tutti.helpers;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.dev42.tutti.dao.EscalaDao;
import br.com.dev42.tutti.model.DataTsEscalas;

/**
 * Created by sossai on 19/06/17.
 */

public class DbIni {

    private DataTsEscalas dt;
    private EscalaDao dao;
    private Activity activity;

    public DbIni(Activity activity) {
        this.activity = activity;
    }

    public void init(){
        addTonalidades();
        addEscalas();
        addIntervalos();
        addMelodias();
        addTriades();
        addCamposHarmonicos();
        addCadencias();
        addTritonos();
    }

    private void addTonalidades(){
        dao = new EscalaDao(activity);
        List<DataTsEscalas> lista = new ArrayList<DataTsEscalas>();
        DataTsEscalas dt;

        //        opcaoid, escalaid, telaid, sequencia, imagem, som1, som2, som3
        //opcao1tonalidade1
        String nomeImagem;
        for(Integer idEscala = 1 ; idEscala <= 32 ; idEscala ++ ){
            nomeImagem = "opcao1tonalidade"+ idEscala.toString().trim();
            dt = new DataTsEscalas(1,idEscala,1,1,nomeImagem, "la_som1", "", "","","",0,0);
            lista.add(dt);
        }
        dao.insertData(lista);
    }

    private void addEscalas(){
        dao = new EscalaDao(activity);
        List<DataTsEscalas> lista = new ArrayList<DataTsEscalas>();
        DataTsEscalas dt;

        //        opcaoid, escalaid, telaid, sequencia, imagem, som1, som2, som3


        String nomeImagem;
        for(Integer idEscala = 1 ; idEscala <= 32 ; idEscala ++ ) {

            if(idEscala %2 != 0 ){  //  ** m (Escalas impares)

                //  ** Natural
                nomeImagem = "opcao2escala" + idEscala.toString().trim() + "ntela1";
                dt = new DataTsEscalas(2, idEscala, 1, 1, nomeImagem, "la_som1", "", "", "", "", 0, 0);
                lista.add(dt);

                nomeImagem = "opcao2escala" + idEscala.toString().trim() + "ntela2";
                dt = new DataTsEscalas(2, idEscala, 1, 2, nomeImagem, "la_som2", "", "", "", "", 0, 0);
                lista.add(dt);

                //  ** Harmonico
                nomeImagem = "opcao2escala" + idEscala.toString().trim() + "htela1";
                dt = new DataTsEscalas(2, idEscala, 2, 1, nomeImagem, "la_som1", "", "", "", "", 0, 0);
                lista.add(dt);

                nomeImagem = "opcao2escala" + idEscala.toString().trim() + "htela2";
                dt = new DataTsEscalas(2, idEscala, 2, 2, nomeImagem, "la_som2", "", "", "", "", 0, 0);
                lista.add(dt);

                //  ** Melodico
                nomeImagem = "opcao2escala" + idEscala.toString().trim() + "mtela1";
                dt = new DataTsEscalas(2, idEscala, 3, 1, nomeImagem, "la_som1", "", "", "", "", 0, 0);
                lista.add(dt);

                nomeImagem = "opcao2escala" + idEscala.toString().trim() + "mtela2";
                dt = new DataTsEscalas(2, idEscala, 3, 2, nomeImagem, "la_som2", "", "", "", "", 0, 0);
                lista.add(dt);

            }else{  //  ** M  (Escalas pares)

                nomeImagem = "opcao2escala" + idEscala.toString().trim() + "tela1";
                dt = new DataTsEscalas(2, idEscala, 1, 1, nomeImagem, "la_som1", "", "", "", "", 0, 0);
                lista.add(dt);

                nomeImagem = "opcao2escala" + idEscala.toString().trim() + "tela2";
                dt = new DataTsEscalas(2, idEscala, 1, 2, nomeImagem, "la_som2", "", "", "", "", 0, 0);
                lista.add(dt);
            }


        }

/*

            //  ** sequencia 1 escala m
//        String nomeImagem;
        for(Integer idEscala = 1 ; idEscala <= 32 ; idEscala ++ ){
            nomeImagem = "opcao2escala"+ idEscala.toString().trim() +"tela1";
            dt = new DataTsEscalas(2,idEscala,1,1,nomeImagem, "la_som1", "", "","","",0,0);
            lista.add(dt);

            nomeImagem = "opcao2escala"+ idEscala.toString().trim() +"tela2";
            dt = new DataTsEscalas(2,idEscala,2,1,nomeImagem, "la_som2", "", "","","",0,0);
            lista.add(dt);

        }

        //  ** sequencia 2 escala M
        //  ** ainda sem imagem, usar qquer apenas para ter o efeito
        //  ** Natural
        for(Integer idEscala = 1 ; idEscala <= 32 ; idEscala ++ ){
            nomeImagem = "opcao2escala"+ "3" +"tela1";
            dt = new DataTsEscalas(2,idEscala,1,2,nomeImagem, "la_som1", "", "","","",0,0);
            lista.add(dt);

            nomeImagem = "opcao2escala"+ "3" +"tela1";
            dt = new DataTsEscalas(2,idEscala,2,2,nomeImagem, "la_som2", "", "","","",0,0);
            lista.add(dt);
        }

        //  ** Melodico
        for(Integer idEscala = 1 ; idEscala <= 32 ; idEscala ++ ){
            nomeImagem = "opcao2escala"+ "3" +"tela1";
            dt = new DataTsEscalas(2,idEscala,3,2,nomeImagem, "la_som1", "", "","","",0,0);
            lista.add(dt);

            nomeImagem = "opcao2escala"+ "3" +"tela1";
            dt = new DataTsEscalas(2,idEscala,4,2,nomeImagem, "la_som2", "", "","","",0,0);
            lista.add(dt);
        }

        //  ** Harmonico
        for(Integer idEscala = 1 ; idEscala <= 32 ; idEscala ++ ){
            nomeImagem = "opcao2escala"+ "3" +"tela1";
            dt = new DataTsEscalas(2,idEscala,5,2,nomeImagem, "la_som1", "", "","","",0,0);
            lista.add(dt);

            nomeImagem = "opcao2escala"+ "3" +"tela1";
            dt = new DataTsEscalas(2,idEscala,6,2,nomeImagem, "la_som2", "", "","","",0,0);
            lista.add(dt);
        }*/

        dao.insertData(lista);
    }

    private void addIntervalos(){
        dao = new EscalaDao(activity);
        List<DataTsEscalas> lista = new ArrayList<DataTsEscalas>();
        DataTsEscalas dt;
        //opcao4intervalo10atela1
        String nomeImagem;
        //        opcaoid, escalaid, telaid, sequencia, imagem, som1, som2, som3
        for(Integer idEscala = 1 ; idEscala <= 32 ; idEscala ++ ){


            nomeImagem = "opcao4intervalo"+ idEscala.toString().trim() +"atela1";
            dt = new DataTsEscalas(4,idEscala,1,1,nomeImagem, "la_som1", "", "","","",0,0);
            lista.add(dt);

            nomeImagem = "opcao4intervalo"+ idEscala.toString().trim() +"btela1";
            dt = new DataTsEscalas(4,idEscala,2,1,nomeImagem, "la_som2", "", "","","",0,0);
            lista.add(dt);

            nomeImagem = "opcao4intervalo"+ idEscala.toString().trim() +"atela2";
            dt = new DataTsEscalas(4,idEscala,3,1,nomeImagem, "la_som1", "", "","","",0,0);
            lista.add(dt);

            nomeImagem = "opcao4intervalo"+ idEscala.toString().trim() +"btela2";
            dt = new DataTsEscalas(4,idEscala,4,1,nomeImagem, "la_som2", "", "","","",0,0);
            lista.add(dt);

/*            nomeImagem = "opcao4intervalo"+ idEscala.toString().trim() +"atela1";
            dt = new DataTsEscalas(4,idEscala,1,1,nomeImagem, "la_som1", "", "","");
            lista.add(dt);

            nomeImagem = "opcao4intervalo"+ idEscala.toString().trim() +"btela1";
            dt = new DataTsEscalas(4,idEscala,1,2,nomeImagem, "la_som2", "", "","");
            lista.add(dt);

            nomeImagem = "opcao4intervalo"+ idEscala.toString().trim() +"atela2";
            dt = new DataTsEscalas(4,idEscala,2,1,nomeImagem, "la_som1", "", "","");
            lista.add(dt);

            nomeImagem = "opcao4intervalo"+ idEscala.toString().trim() +"btela2";
            dt = new DataTsEscalas(4,idEscala,2,2,nomeImagem, "la_som2", "", "","");
            lista.add(dt);*/
        }
        dao.insertData(lista);
    }

    private void addMelodias(){
        dao = new EscalaDao(activity);
        List<DataTsEscalas> lista = new ArrayList<DataTsEscalas>();
        DataTsEscalas dt;

        //        opcaoid, escalaid, telaid, sequencia, imagem, som1, som2, som3

        //opcao3melodia10tela1
        String nomeImagem;
        for(Integer idEscala = 1 ; idEscala <= 32 ; idEscala ++ ){
            nomeImagem = "opcao3melodia"+ idEscala.toString().trim() +"tela1";
            dt = new DataTsEscalas(3,idEscala,1,1,nomeImagem, "la_som1", "", "","","brilha_do4",0,6670);
            lista.add(dt);

            nomeImagem = "opcao3melodia"+ idEscala.toString().trim() +"tela2";
            dt = new DataTsEscalas(3,idEscala,2,1,nomeImagem, "la_som2", "", "","","brilha_do4",6670,11293);
            lista.add(dt);

            nomeImagem = "opcao3melodia"+ idEscala.toString().trim() +"tela3";
            dt = new DataTsEscalas(3,idEscala,3,1,nomeImagem, "la_som2", "", "","","brilha_do4",11293,15466 );
            lista.add(dt);

            nomeImagem = "opcao3melodia"+ idEscala.toString().trim() +"tela4";
            dt = new DataTsEscalas(3,idEscala,4,1,nomeImagem, "la_som2", "", "","","brilha_do4",15904 ,21642  );
            lista.add(dt);

            nomeImagem = "opcao3melodia"+ idEscala.toString().trim() +"tela5";
            dt = new DataTsEscalas(3,idEscala,5,1,nomeImagem, "la_som2", "", "","","brilha_do4",21642,26264 );
            lista.add(dt);

            nomeImagem = "opcao3melodia"+ idEscala.toString().trim() +"tela6";
            dt = new DataTsEscalas(3,idEscala,6,1,nomeImagem, "la_som2", "", "","","brilha_do4",26264,34500 );
            lista.add(dt);

            nomeImagem = "opcao3melodia"+ idEscala.toString().trim() +"tela7";
            dt = new DataTsEscalas(3,idEscala,6,1,nomeImagem, "la_som2", "", "","","brilha_do4",26264,34500 );
            lista.add(dt);

            nomeImagem = "opcao3melodia"+ idEscala.toString().trim() +"tela8";
            dt = new DataTsEscalas(3,idEscala,6,1,nomeImagem, "la_som2", "", "","","brilha_do4",26264,34500 );
            lista.add(dt);

            nomeImagem = "opcao3melodia"+ idEscala.toString().trim() +"tela9";
            dt = new DataTsEscalas(3,idEscala,6,1,nomeImagem, "la_som2", "", "","","brilha_do4",26264,34500 );
            lista.add(dt);

            nomeImagem = "opcao3melodia"+ idEscala.toString().trim() +"tela10";
            dt = new DataTsEscalas(3,idEscala,6,1,nomeImagem, "la_som2", "", "","","brilha_do4",26264,34500 );
            lista.add(dt);

            nomeImagem = "opcao3melodia"+ idEscala.toString().trim() +"tela11";
            dt = new DataTsEscalas(3,idEscala,6,1,nomeImagem, "la_som2", "", "","","brilha_do4",26264,34500 );
            lista.add(dt);

            nomeImagem = "opcao3melodia"+ idEscala.toString().trim() +"tela12";
            dt = new DataTsEscalas(3,idEscala,6,1,nomeImagem, "la_som2", "", "","","brilha_do4",26264,34500 );
            lista.add(dt);

        }
        dao.insertData(lista);
    }

    private void addTriades(){
        dao = new EscalaDao(activity);
        List<DataTsEscalas> lista = new ArrayList<DataTsEscalas>();
        DataTsEscalas dt;

        //        opcaoid, escalaid, telaid, sequencia, imagem, som1, som2, som3

        //opcao3melodia10tela1
        String nomeImagem;

        for(Integer idEscala = 1 ; idEscala <= 32 ; idEscala ++ ){

            //for(Integer tela = 1; tela <= 3;tela++){ // Fundamental, 1 Inversão , 2 Inversão

            //Fundamental
                nomeImagem = "opcao5triade"+ idEscala.toString().trim() +"atelaa";
                dt = new DataTsEscalas(5,idEscala,1,1,nomeImagem, "la_som1", "", "","","",0,0);
                lista.add(dt);

                nomeImagem = "opcao5triade"+ idEscala.toString().trim() +"atelam";
                dt = new DataTsEscalas(5,idEscala,1,2,nomeImagem, "la_som1", "", "","","",0,0);
                lista.add(dt);

                nomeImagem = "opcao5triade"+ idEscala.toString().trim() +"atelamn";
                dt = new DataTsEscalas(5,idEscala,1,3,nomeImagem, "la_som1", "", "","","",0,0);
                lista.add(dt);

                nomeImagem = "opcao5triade"+ idEscala.toString().trim() +"atelad";
                dt = new DataTsEscalas(5,idEscala,1,4,nomeImagem, "la_som1", "", "","","",0,0);
                lista.add(dt);

            // 1ª Inversão
                nomeImagem = "opcao5triade"+ idEscala.toString().trim() +"btelaa";
                dt = new DataTsEscalas(5,idEscala,2,1,nomeImagem, "la_som1", "", "","","",0,0);
                lista.add(dt);

                nomeImagem = "opcao5triade"+ idEscala.toString().trim() +"btelam";
                dt = new DataTsEscalas(5,idEscala,2,2,nomeImagem, "la_som1", "", "","","",0,0);
                lista.add(dt);

                nomeImagem = "opcao5triade"+ idEscala.toString().trim() +"btelamn";
                dt = new DataTsEscalas(5,idEscala,2,3,nomeImagem, "la_som1", "", "","","",0,0);
                lista.add(dt);

                nomeImagem = "opcao5triade"+ idEscala.toString().trim() +"btelad";
                dt = new DataTsEscalas(5,idEscala,2,4,nomeImagem, "la_som1", "", "","","",0,0);
                lista.add(dt);

            // 2ª Inversão

                nomeImagem = "opcao5triade"+ idEscala.toString().trim() +"ctelaa";
                dt = new DataTsEscalas(5,idEscala,3,1,nomeImagem, "la_som1", "", "","","",0,0);
                lista.add(dt);

                nomeImagem = "opcao5triade"+ idEscala.toString().trim() +"ctelam";
                dt = new DataTsEscalas(5,idEscala,3,2,nomeImagem, "la_som1", "", "","","",0,0);
                lista.add(dt);

                nomeImagem = "opcao5triade"+ idEscala.toString().trim() +"ctelamn";
                dt = new DataTsEscalas(5,idEscala,3,3,nomeImagem, "la_som1", "", "","","",0,0);
                lista.add(dt);

                nomeImagem = "opcao5triade"+ idEscala.toString().trim() +"ctelad";
                dt = new DataTsEscalas(5,idEscala,3,4,nomeImagem, "la_som1", "", "","","",0,0);
                lista.add(dt);

            //}

        }
        dao.insertData(lista);
    }

    private void addCamposHarmonicos(){
        dao = new EscalaDao(activity);
        List<DataTsEscalas> lista = new ArrayList<DataTsEscalas>();
        DataTsEscalas dt;

        //        opcaoid, escalaid, telaid, sequencia, imagem, som1, som2, som3
        //opcao6ch1tela1
        String nomeImagem;

        for(Integer idEscala = 1 ; idEscala <= 32 ; idEscala ++ ){
            nomeImagem = "opcao6ch"+ idEscala.toString().trim() +"tela1";
            dt = new DataTsEscalas(6,idEscala,1,1,nomeImagem, "la_som1", "", "","","",0,0);
            lista.add(dt);

            nomeImagem = "opcao6ch"+ idEscala.toString().trim() +"tela2";
            dt = new DataTsEscalas(6,idEscala,2,1,nomeImagem, "la_som1", "", "","","",0,0);
            lista.add(dt);

            nomeImagem = "opcao6ch"+ idEscala.toString().trim() +"tela3";
            dt = new DataTsEscalas(6,idEscala,3,1,nomeImagem, "la_som1", "", "","","",0,0);
            lista.add(dt);
        }
        dao.insertData(lista);
    }

    private void addCadencias(){
        dao = new EscalaDao(activity);
        List<DataTsEscalas> lista = new ArrayList<DataTsEscalas>();
        DataTsEscalas dt;

        //        opcaoid, escalaid, telaid, sequencia, imagem, som1, som2, som3
        //opcao7cadencia1
        String nomeImagem;
        for(Integer idEscala = 1 ; idEscala <= 32 ; idEscala ++ ){
            nomeImagem = "opcao7cadencia"+ idEscala.toString().trim();
            dt = new DataTsEscalas(7,idEscala,1,1,nomeImagem, "la_som1", "", "","","",0,0);
            lista.add(dt);
        }
        dao.insertData(lista);
    }


    private void addTritonos(){
//        Todos os tritonos impares têm primário e secundário.
//        O secundário é o mesmo que o trítono de numeracao seguinte.

        dao = new EscalaDao(activity);
        List<DataTsEscalas> lista = new ArrayList<DataTsEscalas>();
        DataTsEscalas dt;

        //        opcaoid, escalaid, telaid, sequencia, imagem, som1, som2, som3
        //opcao8tritono1
        String nomeImagem;
        for(Integer idEscala = 1 ; idEscala <= 32 ; idEscala ++ ){

            //  ** impar
            if(idEscala %2 != 0){
                nomeImagem = "opcao8tritono"+ idEscala.toString().trim() + "p";
                dt = new DataTsEscalas(8,idEscala,1,1,nomeImagem, "la_som1", "", "","","",0,0);
                lista.add(dt);

                nomeImagem = "opcao8tritono"+ (idEscala + 1);
                dt = new DataTsEscalas(8,idEscala,1,2,nomeImagem, "la_som1", "", "","","",0,0);
                lista.add(dt);
            }else{
                nomeImagem = "opcao8tritono"+ idEscala.toString().trim();
                dt = new DataTsEscalas(8,idEscala,1,1,nomeImagem, "la_som1", "", "","","",0,0);
                lista.add(dt);
            }
        }
        dao.insertData(lista);
    }


}
