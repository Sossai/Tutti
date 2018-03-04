package br.com.dev42.tutti.fragments;


import android.app.Activity;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.List;

import br.com.dev42.tutti.R;
import br.com.dev42.tutti.interfaces.EscalaInterface;
import br.com.dev42.tutti.model.DataTsEscalas;
import br.com.dev42.tutti.model.Escala;
import okhttp3.internal.Internal;

/**
 * A simple {@link Fragment} subclass.
 */
public class TelaEscalasFragment extends Fragment {

    private String TAG = "DEV42";
    private Activity activity;
    private View v;
    private DataTsEscalas dt;
    private List<DataTsEscalas> listDts;
    private EscalaInterface escalaInterface;
    private String img;
    private Integer positionTela; //  fundamental, 1ª e 2ª inversão
    private Integer sequenciaTela;    //  A, M, m, d
    private Escala escala;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = getArguments();
//        v = inflater.inflate(R.layout.fragment_tela_escalas, container, false);
//        ImageView pagina = (ImageView)v.findViewById(R.id.iv_tela_escala);

        activity = getActivity();
        ImageView pagina;
        escalaInterface = (EscalaInterface)getActivity();

//        dt = null;
        if(bundle != null){

            Gson gson;

            Integer escalaOpcaoId = bundle.getInt("ESCALAOPCAO",0);
//            final Escala escala = (Escala) bundle.getSerializable("ESCALA");
            escala = (Escala) bundle.getSerializable("ESCALA");
            final Integer escalaId = escala.getId();
            String image;
            int resourceId;
            Type listType;


            switch (escalaOpcaoId){
                //  ** Tonalidades
                case 1:
                    v = inflater.inflate(R.layout.fragment_tela_tonalidades, container, false);
                    pagina = (ImageView)v.findViewById(R.id.iv_tela_tonalidades);

                    dt = (DataTsEscalas)bundle.getSerializable("DATATS");
                    image = dt.getImagem();

                    resourceId = getActivity().getResources().getIdentifier(image, "drawable", getActivity().getPackageName());
                    pagina.setImageResource(Integer.valueOf(resourceId));

                    Button btnTonalidade1 = (Button) v.findViewById(R.id.btn_tela_tonalidade1);
                    Button btnTonalidade2 = (Button) v.findViewById(R.id.btn_tela_tonalidade2);

                    //  ** As notas das colunas 1 e 3 sao m. As das colunas 2 e 4 sao M.
                    if(escalaId %2 == 0){
                        btnTonalidade1.setBackgroundResource(R.drawable.btn_tonalidade1);
                        btnTonalidade2.setBackgroundResource(R.drawable.btn_tonalidade_stroke2);
                    }else {
                        btnTonalidade1.setBackgroundResource(R.drawable.btn_tonalidade_stroke1);
                        btnTonalidade2.setBackgroundResource(R.drawable.btn_tonalidade2);
                    }

                    btnTonalidade1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            selecionaEscalaCorrespondente(escalaId);
                        }
                    });

                    btnTonalidade2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            selecionaEscalaCorrespondente(escalaId);
                        }
                    });

                    break;

                //  * Escalas
                case 2:
                    v = inflater.inflate(R.layout.fragment_tela_escalas, container, false);
//                    pagina = (ImageView)v.findViewById(R.id.iv_tela_escala);



                    Button btnTipoEscala = (Button)v.findViewById(R.id.btn_tipo_escala);

                    gson = new Gson();
                    listType = new TypeToken<List<DataTsEscalas>>(){}.getType();
                    final List<DataTsEscalas> listDtsEscalas = gson.fromJson(bundle.getString("DATATS"), listType);

                    //  ** M não tem opção de mudar de tela
                    if(escala.getTipo().equals('M')){
                        positionTela = 1;
                    }else{
                        if(escalaInterface.getObject() != null){
                            positionTela = (Integer)escalaInterface.getObject();
                        }else {
                            //  ** 1º item
                            positionTela = 1;
                        }
                    }




                    // ** Recebe agrupado por sequencia, logo a sequcnai padrão do grupo recebido é igual
                    sequenciaTela = listDtsEscalas.get(0).getSequencia();
                    loadEscalaDT(listDtsEscalas);

                    //  ** Chamo a inteface para que todas as telas tenham acesso a mesma variavel
//                    escalaInterface.setObject(positionTela);



                    btnTipoEscala.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switch (positionTela){
                                case 1:
                                    positionTela = 2;
                                    break;
                                case 2:
                                    positionTela = 3;
                                    break;
                                case 3:
                                    positionTela = 1;
                                    break;
                            }
                            escalaInterface.setObject(positionTela);
                            loadEscalaDT(listDtsEscalas);
                        }
                    });

/*
                    dt = (DataTsEscalas)bundle.getSerializable("DATATS");
                    image = dt.getImagem();

                    if(escala.getTipo().equals("M")){
                        switch (dt.getTelaid()){
                            case 1:
                                barraMaior_tela1.setVisibility(View.VISIBLE);
                                barraMaior_tela2.setVisibility(View.GONE);
                                break;
                            case 2:
                                barraMaior_tela1.setVisibility(View.GONE);
                                barraMaior_tela2.setVisibility(View.VISIBLE);
                                break;
                        }
                    }else{
                        switch (dt.getTelaid()){
                            case 1:
                                barra_MenorNatural_tela1.setVisibility(View.VISIBLE);
                                break;
                            case 2:
                                barra_MenorNatural_tela2.setVisibility(View.VISIBLE);
                                break;
                            case 3:
                                barra_MenorHarmonico_tela1.setVisibility(View.VISIBLE);
                                break;
                            case 4:
                                barra_MenorHarmonico_tela2.setVisibility(View.VISIBLE);
                                break;
                            case 5:
                                barra_MenorMelodico_tela1.setVisibility(View.VISIBLE);
                                break;
                            case 6:
                                barra_MenorMelodico_tela2.setVisibility(View.VISIBLE);
                                break;
                        }
                    }


                    resourceId = getActivity().getResources().getIdentifier(image, "drawable", getActivity().getPackageName());
                    pagina.setImageResource(Integer.valueOf(resourceId));
*/



//                    pagina.setBackground(getActivity().getResources().getDrawable(resourceId));

//                    pagina.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if(dt != null){
//                                escalaInterface.playSoundI();
//                                Log.e("DEV42", "CLICK : " + dt.getSom1());
//                            }
//                        }
//                    });
                    break;

                //  * Melodias
                case 3:
                    v = inflater.inflate(R.layout.fragment_tela_melodias, container, false);
                    pagina = (ImageView)v.findViewById(R.id.iv_tela_melodia);

                    dt = (DataTsEscalas)bundle.getSerializable("DATATS");
                    image = dt.getImagem();

                    resourceId = getActivity().getResources().getIdentifier(image, "drawable", getActivity().getPackageName());
                    if(resourceId != 0)
                        pagina.setImageResource(Integer.valueOf(resourceId));

//                    pagina.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if(dt != null){
//                                escalaInterface.playSoundI();
//                                Log.e("DEV42", "CLICK : " + dt.getSom1());
//                            }
//                        }
//                    });
                    break;
                //  * Intervalos
                case 4:
                    v = inflater.inflate(R.layout.fragment_tela_intervalos, container, false);

                    pagina = (ImageView)v.findViewById(R.id.iv_tela_intervalo);

                    dt = (DataTsEscalas)bundle.getSerializable("DATATS");
                    image = dt.getImagem();

                    Button btnIntervaloPrincipal = (Button) v.findViewById(R.id.btn_intervalo_principal);
                    Button btnIntervalo1 = (Button) v.findViewById(R.id.btn_tela_intervalo_1);
                    Button btnIntervalo2 = (Button) v.findViewById(R.id.btn_tela_intervalo_2);
                    Button btnIntervalo3 = (Button) v.findViewById(R.id.btn_tela_intervalo_3);
                    Button btnIntervalo4 = (Button) v.findViewById(R.id.btn_tela_intervalo_4);

                    //  ** Desliga o AllCaps
                    btnIntervalo1.setTransformationMethod(null);
                    btnIntervalo2.setTransformationMethod(null);
                    btnIntervalo3.setTransformationMethod(null);
                    btnIntervalo4.setTransformationMethod(null);

                    GradientDrawable shape = (GradientDrawable)btnIntervaloPrincipal.getBackground();
                    if(escalaId %2 == 0){   //   coluna 2 e 4
                        btnIntervaloPrincipal.setText("M");
                        shape.setColor(ContextCompat.getColor(activity, R.color.colorEscala2));
                        shape.setStroke(10, ContextCompat.getColor(activity, R.color.colorEscala2));
                        btnIntervaloPrincipal.setTextColor(ContextCompat.getColor(activity, R.color.colorNomeEscala2));

                        switch (dt.getTelaid()){
                            case 1:
                                btnIntervalo1.setText("Unis.");
                                btnIntervalo2.setText("2M");
                                btnIntervalo3.setText("3M");
                                btnIntervalo4.setText("4J");
                                break;
                            case 2:
                                btnIntervalo1.setText("5J");
                                btnIntervalo2.setText("6M");
                                btnIntervalo3.setText("7M");
                                btnIntervalo4.setText("8J");
                                break;
                            case 3:
                                btnIntervalo1.setText("Unis.");
                                btnIntervalo2.setText("2m");
                                btnIntervalo3.setText("3m");
                                btnIntervalo4.setText("4J");
                                break;
                            case 4:
                                btnIntervalo1.setText("5J");
                                btnIntervalo2.setText("6m");
                                btnIntervalo3.setText("7m");
                                btnIntervalo4.setText("8J");
                                break;
                        }
                    }else{//   coluna 1 e 3
                        btnIntervaloPrincipal.setText("m");
                        shape.setColor(ContextCompat.getColor(activity, R.color.colorEscala1));
                        shape.setStroke(10, ContextCompat.getColor(activity, R.color.colorEscala1));
                        btnIntervaloPrincipal.setTextColor(ContextCompat.getColor(activity, R.color.colorNomeEscala1));
                        switch (dt.getTelaid()){
                            case 1:
                                btnIntervalo1.setText("Unis.");
                                btnIntervalo2.setText("2M");
                                btnIntervalo3.setText("3m");
                                btnIntervalo4.setText("4J");
                                break;
                            case 2:
                                btnIntervalo1.setText("5J");
                                btnIntervalo2.setText("6m");
                                btnIntervalo3.setText("7m");
                                btnIntervalo4.setText("8J");
                                break;
                            case 3:
                                btnIntervalo1.setText("Unis.");
                                btnIntervalo2.setText("2M");
                                btnIntervalo3.setText("3M");
                                btnIntervalo4.setText("4J");
                                break;
                            case 4:
                                btnIntervalo1.setText("5J");
                                btnIntervalo2.setText("6M");
                                btnIntervalo3.setText("7m");
                                btnIntervalo4.setText("8J");
                                break;
                        }
                    }



                    resourceId = getActivity().getResources().getIdentifier(image, "drawable", getActivity().getPackageName());
                    pagina.setImageResource(Integer.valueOf(resourceId));

                    pagina.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(dt != null){
                                escalaInterface.playSoundI();
                                Log.e("DEV42", "CLICK : " + dt.getSom1());
                            }
                        }
                    });

/*                    List<DataTsEscalas> listDts = new ArrayList<>();
                    Gson gson = new Gson();

                    Type listType = new TypeToken<List<DataTsEscalas>>(){}.getType();
                    listDts = gson.fromJson(bundle.getString("DATATS"), listType);

                    int cont = 1;
                    for(DataTsEscalas dti: listDts){
                        Log.e(TAG, dti.getImagem());
                        image = dti.getImagem();

                        if(cont == 1)
                            pagina = (ImageView)v.findViewById(R.id.iv_tela_i1);
                        else
                            pagina = (ImageView)v.findViewById(R.id.iv_tela_i2);

                        resourceId = getActivity().getResources().getIdentifier(image, "drawable", getActivity().getPackageName());
                        pagina.setImageResource(Integer.valueOf(resourceId));

                        cont ++;
                    }*/
                    break;

                //  * Triades
                case 5:
                    v = inflater.inflate(R.layout.fragment_tela_triades, container, false);
//                    pagina = (ImageView)v.findViewById(R.id.iv_tela_triade);

                    Button btnPosicao = (Button) v.findViewById(R.id.btn_tela_triade_1);
                    Button btnA = (Button) v.findViewById(R.id.btn_tela_triade_2);
                    Button btnM = (Button) v.findViewById(R.id.btn_tela_triade_3);
                    Button btnMN = (Button) v.findViewById(R.id.btn_tela_triade_4);
                    Button btnD = (Button) v.findViewById(R.id.btn_tela_triade_5);

                    //  ** Desliga o AllCaps
//                    btnPosicao.setTransformationMethod(null);
//                    btnA.setTransformationMethod(null);
//                    btnM.setTransformationMethod(null);
//                    btnMN.setTransformationMethod(null);
//                    btnD.setTransformationMethod(null);

                    gson = new Gson();
                    listType = new TypeToken<List<DataTsEscalas>>(){}.getType();
                    final List<DataTsEscalas> listDts = gson.fromJson(bundle.getString("DATATS"), listType);

                    //  ** 1º item
                    positionTela = 1;
                    //  ** As notas das colunas 1 e 3 sao m. As das colunas 2 e 4 sao M.
                    sequenciaTela = (escalaId %2 == 0) ? 2 : 3;

                    loadTriadeDt(listDts);

                    btnPosicao.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            positionTela = (positionTela < 3) ? positionTela += 1 : 1;
                            loadTriadeDt(listDts);
                        }
                    });

                    btnA.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            sequenciaTela = 1;
                            loadTriadeDt(listDts);

//                            escalaInterface.mudaEscalaI(7);

                        }
                    });

//                    GradientDrawable shape = (GradientDrawable)btnM.getBackground();
//                    btnM.setBackground(R.drawable.btn_campo_harmonico_stroke1);

//                    Drawable d = getResources().getDrawable(R.drawable.ic_launcher);
                    btnM.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            sequenciaTela = 2;
                            loadTriadeDt(listDts);
                            selecionaEscalaCorrespondente(escalaId);
                        }
                    });

                    btnMN.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            sequenciaTela = 3;
                            loadTriadeDt(listDts);
                            selecionaEscalaCorrespondente(escalaId);
                        }
                    });

                    btnD.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            sequenciaTela = 4;
                            loadTriadeDt(listDts);
                        }
                    });

                    break;

                //  ** Campos Harmonicos
                case 6:
                    v = inflater.inflate(R.layout.fragment_tela_campos_harmonicos, container, false);
                    gson = new Gson();
                    Type listTypech = new TypeToken<List<DataTsEscalas>>(){}.getType();
                    final List<DataTsEscalas> listDtCH = gson.fromJson(bundle.getString("DATATS"), listTypech);

                    Button btnPrincipalMaior = (Button)v.findViewById(R.id.btn_principal_maior_ch_barra);
                    Button btnPrincipalMenorNatural = (Button)v.findViewById(R.id.btn_principal_menor_natural_ch_barra);
                    Button btnPrincipalMenorMH = (Button)v.findViewById(R.id.btn_principal_menor_hm_ch_barra);

                    Button btnMaiorLinha2 = (Button)v.findViewById(R.id.btn_maior_linha2);
                    Button btnMenorNaturalLinha2 = (Button)v.findViewById(R.id.btn_menor_natural_linha2);
                    Button btnMenorMHLinha2 = (Button)v.findViewById(R.id.btn_menor_mh_linha2);

                    final View barraMaior = (View) v.findViewById(R.id.barra_ch_maior);
                    final View barraMenorNatural = (View) v.findViewById(R.id.barra_ch_menor_natural);
                    final View barraMenorHarmonicoMelodico = (View) v.findViewById(R.id.barra_ch_menor_hm);
                    final ImageView barraBakcEscala1 = (ImageView) v.findViewById(R.id.barra_roxa_escala1_back);


                    //  ** 1º item
                    positionTela = 1;
                    sequenciaTela = 1;
                    barraBakcEscala1.setVisibility(View.GONE);  //  ** Barra de retaguarda tela 1 sempre some
                    loadCampoHarmonicoDt(listDtCH);

                    //  **  Maior -> Menor Nat
                    btnPrincipalMaior.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            positionTela = 2;
                            sequenciaTela = 1;
                            barraMaior.setVisibility(View.GONE);
                            barraMenorNatural.setVisibility(View.VISIBLE);
                            barraMenorHarmonicoMelodico.setVisibility(View.GONE);
                            barraBakcEscala1.setVisibility(View.VISIBLE);
                            loadCampoHarmonicoDt(listDtCH);
                        }
                    });

                    //  **  Menor Nat -> Maior
                    btnPrincipalMenorNatural.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            positionTela = 1;
                            sequenciaTela = 1;
                            barraMaior.setVisibility(View.VISIBLE);
                            barraMenorNatural.setVisibility(View.GONE);
                            barraMenorHarmonicoMelodico.setVisibility(View.GONE);
                            barraBakcEscala1.setVisibility(View.GONE);  //  ** Barra de retaguarda tela 1 sempre some
                            loadCampoHarmonicoDt(listDtCH);
                        }
                    });

                    //  **  Menor -> Maior
                    btnPrincipalMenorMH.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            positionTela = 1;
                            sequenciaTela = 1;
                            barraMaior.setVisibility(View.VISIBLE);
                            barraMenorNatural.setVisibility(View.GONE);
                            barraMenorHarmonicoMelodico.setVisibility(View.GONE);
                            barraBakcEscala1.setVisibility(View.GONE);  //  ** Barra de retaguarda tela 1 sempre some
                            loadCampoHarmonicoDt(listDtCH);
                        }
                    });

                    //  **  Maior -> Harm + Mel
                    btnMaiorLinha2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            positionTela = 3;
                            sequenciaTela = 1;
                            barraMaior.setVisibility(View.GONE);
                            barraMenorNatural.setVisibility(View.GONE);
                            barraMenorHarmonicoMelodico.setVisibility(View.VISIBLE);
                            barraBakcEscala1.setVisibility(View.VISIBLE);
                            loadCampoHarmonicoDt(listDtCH);
                        }
                    });

                    //  **  Menor Nat -> Menor Harm + Mel
                    btnMenorNaturalLinha2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            positionTela = 3;
                            sequenciaTela = 1;
                            barraMaior.setVisibility(View.GONE);
                            barraMenorNatural.setVisibility(View.GONE);
                            barraMenorHarmonicoMelodico.setVisibility(View.VISIBLE);
                            loadCampoHarmonicoDt(listDtCH);
                        }
                    });

                    //  ** Menor Harm + Mel -> Menor Nat
                    btnMenorMHLinha2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            positionTela = 2;
                            sequenciaTela = 1;
                            barraMaior.setVisibility(View.GONE);
                            barraMenorNatural.setVisibility(View.VISIBLE);
                            barraMenorHarmonicoMelodico.setVisibility(View.GONE);
                            barraBakcEscala1.setVisibility(View.VISIBLE);
                            loadCampoHarmonicoDt(listDtCH);
                        }
                    });

                    break;

                //  ** Cadencias
                case 7:
                    v = inflater.inflate(R.layout.fragment_tela_cadencias, container, false);
                    pagina = (ImageView)v.findViewById(R.id.iv_tela_cadencia);

                    dt = (DataTsEscalas)bundle.getSerializable("DATATS");
                    image = dt.getImagem();

                    resourceId = getActivity().getResources().getIdentifier(image, "drawable", getActivity().getPackageName());
                    pagina.setImageResource(Integer.valueOf(resourceId));
                    break;

                //  ** Tritonos
                case 8:
                    v = inflater.inflate(R.layout.fragment_tela_tritonos, container, false);
                    pagina = (ImageView)v.findViewById(R.id.iv_tela_tritono);

                    dt = (DataTsEscalas)bundle.getSerializable("DATATS");
                    image = dt.getImagem();

                    resourceId = getActivity().getResources().getIdentifier(image, "drawable", getActivity().getPackageName());
                    pagina.setImageResource(Integer.valueOf(resourceId));

                    Button btnPrimario = (Button)v.findViewById(R.id.btn_tela_tritono_1);
                    Button btnSecundario = (Button)v.findViewById(R.id.btn_tela_tritono_1_secundario);

                    final View barraPrimario = (View)v.findViewById(R.id.barra_tritono_primario);
                    final View barraSecundario = (View)v.findViewById(R.id.barra_tritono_secundario);
                    final View barraSem = (View)v.findViewById(R.id.barra_tritono_sem);

                    if(escalaId %2 == 0){
                        barraSem.setVisibility(View.VISIBLE);
                        barraPrimario.setVisibility(View.GONE);
                        barraSecundario.setVisibility(View.GONE);
                    }
                    else {
                        barraSem.setVisibility(View.GONE);
                        barraPrimario.setVisibility(View.VISIBLE);
                        barraSecundario.setVisibility(View.GONE);
                    }

                    btnPrimario.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            barraPrimario.setVisibility(View.GONE);
                            barraSecundario.setVisibility(View.VISIBLE);
                            //  ** Falta o load das imagens e sons que nao tenho do secundario **
                        }
                    });

                    btnSecundario.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            barraPrimario.setVisibility(View.VISIBLE);
                            barraSecundario.setVisibility(View.GONE);
                            //  ** Falta o load das imagens e sons que nao tenho do secundario **
                        }
                    });

                    break;
            }
        }
        return v;
    }


    private void loadCampoHarmonicoDt(List<DataTsEscalas> listDts){
        ImageView pagina = (ImageView)v.findViewById(R.id.iv_tela_campo_hamonico);

        DataTsEscalas dti = extractDt(listDts, positionTela, sequenciaTela);
        String image = dti.getImagem();
        int resourceId = getActivity().getResources().getIdentifier(image, "drawable", getActivity().getPackageName());
        pagina.setImageResource(Integer.valueOf(resourceId));

        // Falta mudar as cores dos botoes selecionados

    }

    private void loadTriadeDt(List<DataTsEscalas> listDts){
        ImageView pagina = (ImageView)v.findViewById(R.id.iv_tela_triade);

//        final List<DataTsEscalas> listDtsAux = listDts;
//
//        DataTsEscalas dti = listDts.get(positionTriadeTela);
//        String image = dti.getImagem();
//        int resourceId = getActivity().getResources().getIdentifier(image, "drawable", getActivity().getPackageName());
//        pagina.setImageResource(Integer.valueOf(resourceId));


        DataTsEscalas dti = extractDt(listDts, positionTela, sequenciaTela);
        String image = dti.getImagem();
        int resourceId = getActivity().getResources().getIdentifier(image, "drawable", getActivity().getPackageName());
        pagina.setImageResource(Integer.valueOf(resourceId));

//        if(extractDt(listDts, positionTriadeTela, sequenciaTriadeTela) != null){
//            dti = extractDt(listDts, positionTriadeTela, sequenciaTriadeTela);
//        }

        Button btnTriade1 = (Button) v.findViewById(R.id.btn_tela_triade_1);

        Button btnA = (Button) v.findViewById(R.id.btn_tela_triade_2);
        Button btnM = (Button) v.findViewById(R.id.btn_tela_triade_3);
        Button btnMN = (Button) v.findViewById(R.id.btn_tela_triade_4);
        Button btnD = (Button) v.findViewById(R.id.btn_tela_triade_5);

        //  ** Desliga o AllCaps
//        btnTriade1.setTransformationMethod(null);
//        btnTriade2.setTransformationMethod(null);
//        btnTriade3.setTransformationMethod(null);
//        btnTriade4.setTransformationMethod(null);
//        btnTriade5.setTransformationMethod(null);

//        Log.e(TAG, positionTriadeTela.toString());



        switch (positionTela){
            case 1:
                btnTriade1.setText("Posição Fundamental");
                break;
            case 2:
                btnTriade1.setText("1ª Inversão");
                break;
            case 3:
                btnTriade1.setText("2ª Inversão");
                break;
        }

        switch (sequenciaTela){
            case 1:
                btnA.setBackgroundResource(R.drawable.btn_campo_harmonico_stroke1);
//                btnM.setBackgroundResource(R.drawable.btn_campo_harmonico2);
                btnM.setBackgroundResource(R.drawable.btn_quadrado_cor_coluna2);
//                btnMN.setBackgroundResource(R.drawable.btn_campo_harmonico1);
                btnMN.setBackgroundResource(R.drawable.btn_quadrado_cor_coluna1);
                btnD.setBackgroundResource(R.drawable.btn_campo_harmonico2);
                break;
            case 2:
                btnA.setBackgroundResource(R.drawable.btn_campo_harmonico1);
//                btnM.setBackgroundResource(R.drawable.btn_campo_harmonico_stroke2);
//                btnMN.setBackgroundResource(R.drawable.btn_campo_harmonico1);
                btnM.setBackgroundResource(R.drawable.btn_quadrado_cor_coluna2_seleciondado);
                btnMN.setBackgroundResource(R.drawable.btn_quadrado_cor_coluna1);
                btnD.setBackgroundResource(R.drawable.btn_campo_harmonico2);
                break;
            case 3:
                btnA.setBackgroundResource(R.drawable.btn_campo_harmonico1);
//                btnM.setBackgroundResource(R.drawable.btn_campo_harmonico2);
//                btnMN.setBackgroundResource(R.drawable.btn_campo_harmonico_stroke1);
                btnM.setBackgroundResource(R.drawable.btn_quadrado_cor_coluna2);
                btnMN.setBackgroundResource(R.drawable.btn_quadrado_cor_coluna1_selecionado);
                btnD.setBackgroundResource(R.drawable.btn_campo_harmonico2);
                break;
            case 4:
                btnA.setBackgroundResource(R.drawable.btn_campo_harmonico1);
//                btnM.setBackgroundResource(R.drawable.btn_campo_harmonico2);
//                btnMN.setBackgroundResource(R.drawable.btn_campo_harmonico1);
                btnM.setBackgroundResource(R.drawable.btn_quadrado_cor_coluna2);
                btnMN.setBackgroundResource(R.drawable.btn_quadrado_cor_coluna1);
                btnD.setBackgroundResource(R.drawable.btn_campo_harmonico_stroke2);
                break;
        }

    }

    private void loadEscalaDT(List<DataTsEscalas> listDts){
        View barraMaior_tela1 = (View)v.findViewById(R.id.fl_escalas_barra_maior_tela1);
        View barraMaior_tela2 = (View)v.findViewById(R.id.fl_escalas_barra_maior_tela2);

        View barra_MenorNatural_tela1 = (View)v.findViewById(R.id.fl_escalas_barra_menor_natural);
        View barra_MenorNatural_tela2 = (View)v.findViewById(R.id.fl_escalas_barra_menor_natural_tela2);

        View barra_MenorHarmonico_tela1 = (View)v.findViewById(R.id.fl_escalas_barra_menor_harmonica);
        View barra_MenorHarmonico_tela2 = (View)v.findViewById(R.id.fl_escalas_barra_menor_harmonica_tela2);

        View barra_MenorMelodico_tela1 = (View)v.findViewById(R.id.fl_escalas_barra_menor_melodica);
        View barra_MenorMelodico_tela2 = (View)v.findViewById(R.id.fl_escalas_barra_menor_melodica_tela2);

        Button btnTipoEscala = (Button)v.findViewById(R.id.btn_tipo_escala);
        DataTsEscalas dti = extractDt(listDts, positionTela, sequenciaTela);

        ImageView pagina = (ImageView)v.findViewById(R.id.iv_tela_escala);
        String image = dti.getImagem();
        int resourceId = getActivity().getResources().getIdentifier(image, "drawable", getActivity().getPackageName());
        pagina.setImageResource(Integer.valueOf(resourceId));



        if(escala.getTipo().equals("M")){
            btnTipoEscala.setVisibility(View.GONE);
            switch (dti.getSequencia()){
                case 1:
                    barraMaior_tela1.setVisibility(View.VISIBLE);
                    barraMaior_tela2.setVisibility(View.GONE);
                    break;
                case 2:
                    barraMaior_tela1.setVisibility(View.GONE);
                    barraMaior_tela2.setVisibility(View.VISIBLE);
                    break;
            }
        }else{
            if(dti.getSequencia() == 1){
                btnTipoEscala.setVisibility(View.VISIBLE);
            }else
                btnTipoEscala.setVisibility(View.GONE);

//            barra_MenorNatural_tela1.setVisibility(View.GONE);
//            barra_MenorNatural_tela2.setVisibility(View.GONE);
//            barra_MenorHarmonico_tela1.setVisibility(View.GONE);
//            barra_MenorHarmonico_tela2.setVisibility(View.GONE);
//            barra_MenorMelodico_tela1.setVisibility(View.GONE);
//            barra_MenorMelodico_tela2.setVisibility(View.GONE);

//            Log.e(TAG, "positionTela : " + positionTela.toString() + " dti.getSequencia() :" + dti.getSequencia().toString());

            switch (positionTela){
                case 1:
                    btnTipoEscala.setText("Natural");
                    switch (dti.getSequencia()) {
                        case 1:
                            barra_MenorNatural_tela1.setVisibility(View.VISIBLE);
                            break;
                        case 2:
                            barra_MenorNatural_tela2.setVisibility(View.VISIBLE);
                            break;
                    }
                    break;
                case 2:
                    btnTipoEscala.setText("Harmônico");
                    switch (dti.getSequencia()) {
                        case 1:
                            barra_MenorHarmonico_tela1.setVisibility(View.VISIBLE);
                            break;
                        case 2:
                            barra_MenorHarmonico_tela2.setVisibility(View.VISIBLE);
                            break;
                    }
                    break;
                case 3:
                    btnTipoEscala.setText("Melódico");
                    switch (dti.getSequencia()) {
                        case 1:
                            barra_MenorMelodico_tela1.setVisibility(View.VISIBLE);
                            break;
                        case 2:
                            barra_MenorMelodico_tela2.setVisibility(View.VISIBLE);
                            break;
                    }
                    break;
            }

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)btnTipoEscala.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            btnTipoEscala.setLayoutParams(params); //causes layout update


//            switch (dti.getSequencia()){
//                case 1:
//                    barra_MenorNatural_tela1.setVisibility(View.VISIBLE);
//                    break;
//                case 2:
//                    barra_MenorNatural_tela2.setVisibility(View.VISIBLE);
//                    break;
//                case 3:
//                    barra_MenorHarmonico_tela1.setVisibility(View.VISIBLE);
//                    break;
//                case 4:
//                    barra_MenorHarmonico_tela2.setVisibility(View.VISIBLE);
//                    break;
//                case 5:
//                    barra_MenorMelodico_tela1.setVisibility(View.VISIBLE);
//                    break;
//                case 6:
//                    barra_MenorMelodico_tela2.setVisibility(View.VISIBLE);
//                    break;
//            }
        }


        resourceId = getActivity().getResources().getIdentifier(image, "drawable", getActivity().getPackageName());
        pagina.setImageResource(Integer.valueOf(resourceId));




    }

    private DataTsEscalas extractDt(List<DataTsEscalas> listaDt, int tela, int sequencia){

        for(DataTsEscalas dti:listaDt){
            if(dti.getTelaid() == tela && dti.getSequencia() == sequencia){
                return dti;
            }
        }
        return null;
    }

    private void selecionaEscalaCorrespondente(Integer idEscala){

        switch (idEscala){
            case 1:
                escalaInterface.mudaEscalaI(16);
                break;
            case 2:
                escalaInterface.mudaEscalaI(13);
                break;
            case 3:
                escalaInterface.mudaEscalaI(16);
                break;
            case 4:
                escalaInterface.mudaEscalaI(13);
                break;
            case 5:
                escalaInterface.mudaEscalaI(12);
                break;
            case 6:
                escalaInterface.mudaEscalaI(17);
                break;
            case 7:
                escalaInterface.mudaEscalaI(20);
                break;
            case 8:
                escalaInterface.mudaEscalaI(9);
                break;
            case 9:
                escalaInterface.mudaEscalaI(8);
                break;
            case 10:
                escalaInterface.mudaEscalaI(21);
                break;
            case 11:
                escalaInterface.mudaEscalaI(24);
                break;
            case 12:
                escalaInterface.mudaEscalaI(5);
                break;
            case 13:
                escalaInterface.mudaEscalaI(4);
                break;
            case 14:
                escalaInterface.mudaEscalaI(25);
                break;
            case 15:
                escalaInterface.mudaEscalaI(28);
                break;
            case 16:
                escalaInterface.mudaEscalaI(1);
                break;
            case 17:
                escalaInterface.mudaEscalaI(6);
                break;
            case 18:
                escalaInterface.mudaEscalaI(29);
                break;
            case 19:
                escalaInterface.mudaEscalaI(32);
                break;
            case 20:
                escalaInterface.mudaEscalaI(7);
                break;
            case 21:
                escalaInterface.mudaEscalaI(10);
                break;
            case 22:
                escalaInterface.mudaEscalaI(32);
                Toast.makeText(activity,"Réb m não faz parte do Círculo das Quintas\r\nClique em Do# m, tonalidade de mesmo som que Réb m",Toast.LENGTH_LONG).show();
                break;
            case 23:
                escalaInterface.mudaEscalaI(18);
                Toast.makeText(getActivity(),"Sol# M não faz parte no Círculo das Quintas.\n\rAcesse Lab M, tonalidade enarmônica de Sol# M.",Toast.LENGTH_LONG).show();
                break;
            case 24:
                escalaInterface.mudaEscalaI(11);
                break;
            case 25:
                escalaInterface.mudaEscalaI(14);
                break;
            case 26:
                escalaInterface.mudaEscalaI(15);
                Toast.makeText(getActivity(),"Solb m não faz parte do Círculo das Quintas.\n\rClique em Fa# m, tonalidade de mesmo som que Solb m.",Toast.LENGTH_LONG).show();
                break;
            case 27:
                escalaInterface.mudaEscalaI(14);
                Toast.makeText(getActivity(),"Ré# M não faz parte no Círculo das Quintas.\n\rAcesse Dob M, tonalidade enarmônica de Ré# M.",Toast.LENGTH_LONG).show();
                break;
            case 28:
                escalaInterface.mudaEscalaI(15);
                break;
            case 29:
                escalaInterface.mudaEscalaI(18);
                break;
            case 30:
                escalaInterface.mudaEscalaI(11);
                Toast.makeText(getActivity(),"Dob m não faz parte do Círculo das Quintas.\n\rClique em Si m, tonalidade de mesmo som que Dob m.",Toast.LENGTH_LONG).show();
                break;
            case 31:
                escalaInterface.mudaEscalaI(10);
                Toast.makeText(getActivity(),"La# M não faz parte no Círculo das Quintas.\n\rAcesse Sib M, tonalidade enarmônica de La# M.",Toast.LENGTH_LONG).show();
                break;
            case 32:
                escalaInterface.mudaEscalaI(19);
                break;
        }
    }

}
