package br.com.dev42.tutti.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.nfc.Tag;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.text.Html;
import android.widget.TextView;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.security.auth.login.LoginException;

import br.com.dev42.tutti.R;
import br.com.dev42.tutti.adapters.EscalaAdapter;
import br.com.dev42.tutti.adapters.EscalaOpcoesAdapter;
import br.com.dev42.tutti.adapters.FragmentListAdapter;
import br.com.dev42.tutti.dao.EscalaDao;
import br.com.dev42.tutti.fragments.TelaEscalasFragment;
import br.com.dev42.tutti.interfaces.EscalaInterface;
import br.com.dev42.tutti.model.DataTsEscalas;
import br.com.dev42.tutti.model.Escala;
import br.com.dev42.tutti.model.EscalaOpcao;

public class MainActivity extends AppCompatActivity implements EscalaInterface {

    private String TAG = "DEV42";
    private List<Escala> listaEscala;
    private List<EscalaOpcao> listaOpcoesEscala;
    private Activity activity = this;
    private MediaPlayer mPlayer;
    private EscalaOpcao escalaOpcaoSelecionada;
    private Escala escalaSelecionada;
    private Integer paginaSelecionada;

    private EscalaAdapter adapterEscala;
    private EscalaOpcoesAdapter escalaOpcoesAdapter;
    private List<Fragment> listaFragmentsTela;
    private PagerAdapter pagerAdapter;
    private ViewPager viewPager;

    private EscalaDao dao;

    private Toolbar mToolBar;
    private Integer trackTeste;
    private Integer paginaVisivel;

    private Integer positionTela; // ** Qual tela esta ativa quando tem mais de 1 opção na Escala

//    private Integer heightTela;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolBar = (Toolbar)findViewById(R.id.tb_main);
        mToolBar.setTitle("");

        // Set the padding to match the Status Bar height
        mToolBar.setPadding(0, getStatusBarHeight(), 0, 0);
        setSupportActionBar(mToolBar);


        viewPager = (ViewPager)findViewById(R.id.vp_main);
//        listaFragmentsTela = new ArrayList<>();


        //  ** DEBUG DATABASE   **
        Button btnABC = (Button)findViewById(R.id.btn_abc);
        btnABC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TesteDbActivity.class);
                startActivity(intent);

//                playSoundComplete();
            }
        });
        //  ** DEBUG DATABASE   **


        //  ** Tamanho padrao da tela
//        heightTela = viewPager.getLayoutParams().height;



        dao = new EscalaDao(activity);

        GridView gvEscala = (GridView)findViewById(R.id.gv_escalas);
        ListView lvEscalaOpcoes = (ListView)findViewById(R.id.lv_escala_opcoes);

        montaEscala();
        adapterEscala = new EscalaAdapter(listaEscala, this);
        gvEscala.setAdapter(adapterEscala);

        montaEscalaOpcoes();

        escalaOpcoesAdapter = new EscalaOpcoesAdapter(listaOpcoesEscala,this);
        lvEscalaOpcoes.setAdapter(escalaOpcoesAdapter);


        Button testSom = (Button)findViewById(R.id.test_som);
        testSom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSoundComplete();
            }
        });
        
        gvEscala.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //  ** Se não for o mesmo botão **
                if(escalaSelecionada == null || escalaSelecionada.getId() != listaEscala.get(position).getId()){
//                    mudaEscalaSelecionado(position);
                    mudaEscalaSelecionadoPorId(listaEscala.get(position).getId());
                    mudaTelaEscala();
                    paginaSelecionada = getPaginaAtual();

                    //playSoundComplete();
                }else{
                    if(mPlayer != null && mPlayer.isPlaying()){
                        mPlayer.stop();
                    }
                }



/*                if(mPlayer != null && mPlayer.isPlaying()){
                    mPlayer.stop();
                }

                else {
//                    switch (escalaClicada.getEscala().getId()){
                    switch (escalaSelecionada.getId()){
                        case 1:
//                            mPlayer = MediaPlayer.create(activity, R.raw.sample);
//                            mPlayer.start();
//
//                            mPlayer.getCurrentPosition()

                            break;
                        case 2:
                            break;
                    }
                }*/

            }
        });

        lvEscalaOpcoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mudaEscalaOpcaoSelecionada(position);
            }
        });

        //  **  Click no viwerPage deve ser dentro do adapter
        //  **  https://stackoverflow.com/questions/16350987/viewpager-onitemclicklistener


        //  ** Nao uso aqui pq o scroll aciona o touch, click deve estar no opbejto   **
        //  ** Marco qual pagina estou **

/*        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

//                switch (event.getAction()){
//                    case MotionEvent.ACTION_UP:
//                        Integer pos = viewPager.getCurrentItem() + 1;
//                        Log.e(TAG, pos.toString());
//                        paginaSelecionada = viewPager.getCurrentItem() + 1;
//
//                        paginaSelecionada = getPaginaAtual();
//                        playSound();
//
//                        break;
////                    case MotionEvent.ACTION_UP:
////                        pos = viewPager.getCurrentItem() + 1;
////                        Log.e(TAG, "action move :" + pos.toString() );
////                        paginaSelecionada = viewPager.getCurrentItem() + 1;
////                        break;
//                }


                return false;
            }
        });*/

        listaFragmentsTela = new ArrayList<>();
        pagerAdapter = new FragmentListAdapter(this.getSupportFragmentManager(), listaFragmentsTela);
        viewPager.setAdapter(pagerAdapter);


        //** Sempre Inicia com a 4 escala selecionada
//        iniciaEscala(3);
        iniciaEscala(4);
    }

//    private void iniciaEscala(int posEscala){
    private void iniciaEscala(int escalaId){
//        mudaEscalaSelecionado(posEscala);
        mudaEscalaSelecionadoPorId(escalaId);
        mudaTelaEscala();
        paginaSelecionada = getPaginaAtual();
    }
    // A method to find height of the status bar
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void mudaTelaEscala(){

        Animation contrair, expandir, scapeLeft, ScapeRight;
        contrair = AnimationUtils.loadAnimation(this,R.anim.anim_contrair);
        expandir = AnimationUtils.loadAnimation(this,R.anim.anim_expandir);
        scapeLeft = AnimationUtils.loadAnimation(this, R.anim.scape_left);

        // ** Sempre que mudar a escala volta para opcão 1 do tipo
        positionTela = 1;
        viewPager.setVisibility(View.VISIBLE);

//        listaFragmentsTela = new ArrayList<>();
        listaFragmentsTela.clear();
//        listaFragmentsTela = new ArrayList<>();
//        pagerAdapter = new FragmentListAdapter(this.getSupportFragmentManager(), listaFragmentsTela);


//        ViewGroup.LayoutParams params = viewPager.getLayoutParams();
//        params.height = heightTela;

        Bundle bundle;
        List<DataTsEscalas> listaDt;
        Gson gson;

        // Menu escala selecionado
        switch (escalaOpcaoSelecionada.getId()){

            //  ** Tonalidades   **
            case 1:
                listaDt = dao.getTelas(escalaOpcaoSelecionada.getId(), escalaSelecionada.getId());
                for(DataTsEscalas dt:listaDt){

                    bundle = new Bundle();
                    bundle.putInt("ESCALAOPCAO", escalaOpcaoSelecionada.getId() );
                    bundle.putSerializable("ESCALA", escalaSelecionada);
                    bundle.putSerializable("DATATS", dt);
                    listaFragmentsTela.add(Fragment.instantiate(this, TelaEscalasFragment.class.getName(), bundle));
                }
                viewPager.getAdapter().notifyDataSetChanged();
                viewPager.setCurrentItem(0);
                break;

            //  ** Escalas   **
            case 2:

//                viewPager.setLayoutParams(params);

                List<DataTsEscalas> listaDtSequencia1 = new ArrayList<>();
                List<DataTsEscalas> listaDtSequencia2 = new ArrayList<>();
                listaDt = dao.getTelas(escalaOpcaoSelecionada.getId(), escalaSelecionada.getId());
                for(DataTsEscalas dt:listaDt){

//                    bundle = new Bundle();
//                    bundle.putInt("ESCALAOPCAO", escalaOpcaoSelecionada.getId() );
//                    bundle.putSerializable("ESCALA", escalaSelecionada);

                    switch (dt.getSequencia()){
                        case 1:
                            listaDtSequencia1.add(dt);
                            break;
                        case 2:
                            listaDtSequencia2.add(dt);
                            break;
                    }
 /*
//                   Log.e(TAG, escalaSelecionada.getId().toString() + escalaSelecionada.getTipo());
                    //  ** Separa o M do m, cada um tem sua sequencia de tela
                    switch (escalaSelecionada.getTipo()){
                        case "M":
                            if(dt.getSequencia() == 1) {
//                                Log.e(TAG, "M");
                                bundle.putSerializable("DATATS", dt);
                                listaFragmentsTela.add(Fragment.instantiate(this, TelaEscalasFragment.class.getName(), bundle));
                            }
                            break;
                        case "m":
                            if(dt.getSequencia() == 2) {
//                                Log.e(TAG, "m");
                                bundle.putSerializable("DATATS", dt);
                                listaFragmentsTela.add(Fragment.instantiate(this, TelaEscalasFragment.class.getName(), bundle));
                            }

                            break;
                    }*/

//                    listaFragmentsTela.add(Fragment.instantiate(this, TelaEscalasFragment.class.getName(), bundle));

//                    listaFragmentsTela.add(newInstance(dt));
                }


                gson = new Gson();
                bundle = new Bundle();
                bundle.putInt("ESCALAOPCAO", escalaOpcaoSelecionada.getId() );
                bundle.putSerializable("ESCALA", escalaSelecionada);

                bundle.putString("DATATS", gson.toJson(listaDtSequencia1));
                listaFragmentsTela.add(Fragment.instantiate(this, TelaEscalasFragment.class.getName(), bundle));

                gson = new Gson();
                bundle = new Bundle();
                bundle.putInt("ESCALAOPCAO", escalaOpcaoSelecionada.getId() );
                bundle.putSerializable("ESCALA", escalaSelecionada);

                bundle.putString("DATATS", gson.toJson(listaDtSequencia2));
                listaFragmentsTela.add(Fragment.instantiate(this, TelaEscalasFragment.class.getName(), bundle));

                //pagerAdapter = new FragmentListAdapter(this.getSupportFragmentManager(), listaFragmentsTela);

//                pagerAdapter.notifyDataSetChanged();
                viewPager.getAdapter().notifyDataSetChanged();
                viewPager.setCurrentItem(0);
//                viewPager.startAnimation(scapeLeft);


                break;

            //  ** Melodias   **
            case 3:

//                params.height = heightTela + 10;
//                viewPager.setLayoutParams(params);

                listaDt = dao.getTelas(escalaOpcaoSelecionada.getId(), escalaSelecionada.getId());
                List<Integer> soundParts = new ArrayList<>();
                String soundName = "";

                for(DataTsEscalas dt:listaDt){

                    bundle = new Bundle();
                    bundle.putInt("ESCALAOPCAO", escalaOpcaoSelecionada.getId() );
//                    bundle.putInt("ESCALA", escalaSelecionada.getId() );
                    bundle.putSerializable("ESCALA", escalaSelecionada);
                    bundle.putSerializable("DATATS", dt);

                    listaFragmentsTela.add(Fragment.instantiate(this, TelaEscalasFragment.class.getName(), bundle));

                    soundParts.add(dt.getSeekendsomcompleto());
                    soundName = dt.getSomcompleto();
                }

                viewPager.getAdapter().notifyDataSetChanged();
                viewPager.setCurrentItem(0);

                playSoundTimeout(soundName, soundParts);

                break;

            //  ** Intervalos   **
            case 4:

//                params.height = heightTela + 30;
//                viewPager.setLayoutParams(params);

                listaDt = dao.getTelas(escalaOpcaoSelecionada.getId(), escalaSelecionada.getId());
                for(DataTsEscalas dt:listaDt){

                    bundle = new Bundle();
                    bundle.putInt("ESCALAOPCAO", escalaOpcaoSelecionada.getId() );
//                    bundle.putInt("ESCALA", escalaSelecionada.getId() );
                    bundle.putSerializable("ESCALA", escalaSelecionada);
                    bundle.putSerializable("DATATS", dt);

                    listaFragmentsTela.add(Fragment.instantiate(this, TelaEscalasFragment.class.getName(), bundle));
                }


/*                listaDt = dao.getTelas(escalaOpcaoSelecionada.getId(), escalaSelecionada.getId());
                List<DataTsEscalas> listDts = new ArrayList<>();
                Integer telaId = 0;
                Integer countTelas = 0;
                for(DataTsEscalas dt:listaDt){
                    if(telaId != dt.getTelaid()) {
                        countTelas++;
                        telaId = dt.getTelaid();
                    }
                }

                for(int c = 1; c <= countTelas; c++ ){
                    for(DataTsEscalas dt:listaDt){
                        if(dt.getTelaid() == c){
                            listDts.add(dt);
                        }
                    }
                    gson = new Gson();
                    bundle = new Bundle();
                    bundle.putInt("ESCALAOPCAO", escalaOpcaoSelecionada.getId() );
                    bundle.putString("DATATS", gson.toJson(listDts));

                    listaFragmentsTela.add(Fragment.instantiate(this, TelaEscalasFragment.class.getName(), bundle));
                    listDts.clear();
                }*/
                viewPager.getAdapter().notifyDataSetChanged();
                viewPager.setCurrentItem(0);

                break;

            //** Triades
            case 5:

                listaDt = dao.getTelas(escalaOpcaoSelecionada.getId(), escalaSelecionada.getId());

                gson = new Gson();
                bundle = new Bundle();
                bundle.putInt("ESCALAOPCAO", escalaOpcaoSelecionada.getId() );
//                bundle.putInt("ESCALA", escalaSelecionada.getId() );
                bundle.putSerializable("ESCALA", escalaSelecionada);
                bundle.putString("DATATS", gson.toJson(listaDt));

                listaFragmentsTela.add(Fragment.instantiate(this, TelaEscalasFragment.class.getName(), bundle));

                viewPager.getAdapter().notifyDataSetChanged();
                viewPager.setCurrentItem(0);
                break;

            //  ** Campos Harmonicos
            case 6:
                listaDt = dao.getTelas(escalaOpcaoSelecionada.getId(), escalaSelecionada.getId());

                gson = new Gson();
                bundle = new Bundle();
                bundle.putInt("ESCALAOPCAO", escalaOpcaoSelecionada.getId() );
//                bundle.putInt("ESCALA", escalaSelecionada.getId() );
                bundle.putSerializable("ESCALA", escalaSelecionada);
                bundle.putString("DATATS", gson.toJson(listaDt));

                listaFragmentsTela.add(Fragment.instantiate(this, TelaEscalasFragment.class.getName(), bundle));

                viewPager.getAdapter().notifyDataSetChanged();
                viewPager.setCurrentItem(0);
                break;

            //  ** Cadencia   **
            case 7:
                listaDt = dao.getTelas(escalaOpcaoSelecionada.getId(), escalaSelecionada.getId());
                for(DataTsEscalas dt:listaDt){

                    bundle = new Bundle();
                    bundle.putInt("ESCALAOPCAO", escalaOpcaoSelecionada.getId() );
//                    bundle.putInt("ESCALA", escalaSelecionada.getId() );
                    bundle.putSerializable("ESCALA", escalaSelecionada);
                    bundle.putSerializable("DATATS", dt);

                    listaFragmentsTela.add(Fragment.instantiate(this, TelaEscalasFragment.class.getName(), bundle));
                }
                viewPager.getAdapter().notifyDataSetChanged();
                viewPager.setCurrentItem(0);
                break;

            //  ** Tritonos   **
            case 8:
                listaDt = dao.getTelas(escalaOpcaoSelecionada.getId(), escalaSelecionada.getId());
                for(DataTsEscalas dt:listaDt){

                    bundle = new Bundle();
                    bundle.putInt("ESCALAOPCAO", escalaOpcaoSelecionada.getId() );
//                    bundle.putInt("ESCALA", escalaSelecionada.getId() );
                    bundle.putSerializable("ESCALA", escalaSelecionada);
                    bundle.putSerializable("DATATS", dt);

                    listaFragmentsTela.add(Fragment.instantiate(this, TelaEscalasFragment.class.getName(), bundle));
                }
                viewPager.getAdapter().notifyDataSetChanged();
                viewPager.setCurrentItem(0);

                break;

        }
    }

/*    public TelaEscalasFragment newInstance(DataTsEscalas dt) {
        TelaEscalasFragment myFragment = new TelaEscalasFragment();

        Bundle args = new Bundle();
        args.putSerializable("DATATS", dt);
        myFragment.setArguments(args);

        return myFragment;
    }*/

    private void mudaEscalaOpcaoSelecionada(Integer position){

        viewPager.setVisibility(View.INVISIBLE);

        //  ** Pego a posicao do selecionado anterior
        //  ** removo e insiro novamente selecionado
        Integer indexOE = listaOpcoesEscala.indexOf(escalaOpcaoSelecionada);
        escalaOpcaoSelecionada.setSelecionado(false);
        if(indexOE != -1){
            listaOpcoesEscala.remove(indexOE);
            listaOpcoesEscala.set(indexOE,escalaOpcaoSelecionada);

            //  ** Sempre terá um marcado por isso dentro do if
            //  ** Marco o novo selecionado
            EscalaOpcao e = listaOpcoesEscala.get(position);
            indexOE = listaOpcoesEscala.indexOf(e);
            e.setSelecionado(true);
            if(indexOE != -1){
                listaOpcoesEscala.remove(indexOE);
                listaOpcoesEscala.set(indexOE,e);

                escalaOpcaoSelecionada = e;
                escalaOpcoesAdapter.notifyDataSetChanged();
            }
        }

        //limpaEscalaSelecionado();
        mudaTelaEscala();
    }

    private void mudaEscalaSelecionadoPorId(Integer escalaId){
        //  ** Desmarca
        Integer indexE = listaEscala.indexOf(escalaSelecionada);
        if(indexE != -1){
            listaEscala.remove(indexE);
            escalaSelecionada.setSelecionado(false);
            listaEscala.set(indexE, escalaSelecionada);
        }

        for(Escala e:listaEscala){
            if(e.getId() == escalaId){

                indexE = listaEscala.indexOf(e);
                e.setSelecionado(true);
                if(indexE != -1){
                    listaEscala.remove(indexE);
                    listaEscala.set(indexE,e);

                    escalaSelecionada = e;

                }
                adapterEscala.notifyDataSetChanged();
                break;
            }

        }
    }
/*
    private void mudaEscalaSelecionado(Integer position){

        //  ** Desmarca
        Integer indexE = listaEscala.indexOf(escalaSelecionada);
        if(indexE != -1){
            listaEscala.remove(indexE);
            escalaSelecionada.setSelecionado(false);
            listaEscala.set(indexE, escalaSelecionada);
        }

        //  ** Marca
        Escala e = listaEscala.get(position);
        indexE = listaEscala.indexOf(e);
        e.setSelecionado(true);
        if(indexE != -1){
            listaEscala.remove(indexE);
            listaEscala.set(indexE,e);

            escalaSelecionada = e;

        }
        //paginaSelecionada = 1;

//        Log.e(TAG, getPaginaAtual().toString());

        adapterEscala.notifyDataSetChanged();
    }
*/
    private Integer getPaginaAtual(){
        return viewPager.getCurrentItem() + 1;
    }

    private void playSoundComplete(){
        //List<DataTsEscalas> listaDt = dao.getTelas(escalaOpcaoSelecionada.getId(), escalaSelecionada.getId());

        final int[] tracks = new int[10];

        if(mPlayer != null && mPlayer.isPlaying()){
            Integer cur_pos = mPlayer.getCurrentPosition();
            Log.d(TAG, cur_pos.toString());
            mPlayer.stop();
//            mPlayer.seekTo();
        }else
        {
            tracks[0] = R.raw.sample;
//        tracks[0] = R.raw.oboe_c4_025_forte_normal;
//        tracks[1] = R.raw.oboe_c4_025_forte_normal;
//        tracks[2] = R.raw.oboe_g4_05_forte_normal;
//        tracks[3] = R.raw.oboe_a4_05_forte_normal;
//        tracks[4] = R.raw.oboe_a4_05_forte_normal;
//        tracks[5] = R.raw.oboe_g4_05_forte_normal;

            trackTeste = 0;
            mPlayer = MediaPlayer.create(activity, tracks[trackTeste]);

            Log.d(TAG, "Tempo Som:" + mPlayer.getDuration());
            mPlayer.seekTo(2300);
            mPlayer.start();
//        Log.e(TAG, "SOM 1");

            timeSoundThread();

            mPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                @Override
                public void onSeekComplete(MediaPlayer mp) {
                    Integer cur_pos = mPlayer.getCurrentPosition();
                    Log.d(TAG, "OPA:"+ cur_pos.toString());
                }
            });

        }

/*        try
        {
            AssetFileDescriptor descriptor = getAssets().openFd("0906.ogg");
            mPlayer = new MediaPlayer();
            mPlayer.reset();
            mPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            mPlayer.prepare();
            mPlayer.start();

        }catch (Exception ex)
        {
            Log.e(TAG, ex.getMessage());
        }*/

//        final int[] tracks = new int[10];
//        C4 – C4 – G4 –G4 – A4 – A4 – G4


//        tracks[0] = R.raw.oboe_A4_05_forte_normal;
//        tracks[1] = R.raw.oboe_A4_025_forte_normal;

//        tracks[0] = R.raw.sample;
////        tracks[0] = R.raw.oboe_c4_025_forte_normal;
////        tracks[1] = R.raw.oboe_c4_025_forte_normal;
////        tracks[2] = R.raw.oboe_g4_05_forte_normal;
////        tracks[3] = R.raw.oboe_a4_05_forte_normal;
////        tracks[4] = R.raw.oboe_a4_05_forte_normal;
////        tracks[5] = R.raw.oboe_g4_05_forte_normal;
//
//        trackTeste = 0;
//        mPlayer = MediaPlayer.create(activity, tracks[trackTeste]);
//
//        Log.d(TAG, "Tempo Som:" + mPlayer.getDuration());
//        mPlayer.seekTo(2300);
//        mPlayer.start();
////        Log.e(TAG, "SOM 1");
//
//        mPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
//            @Override
//            public void onSeekComplete(MediaPlayer mp) {
//                Integer cur_pos = mPlayer.getCurrentPosition();
//                Log.d(TAG, cur_pos.toString());
//            }
//        });


        /*
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                trackTeste += 1;
                mPlayer = mp.create(activity, tracks[trackTeste]);
                mPlayer.start();
//                Log.e(TAG, "SOM TRACK:" + trackTeste.toString());

                if(trackTeste < 5)
                    mPlayer.setOnCompletionListener(this);
//                else
//                    Log.e(TAG, "FIM");
            }
        });*/
    }

    private void playSoundTimeout(String soundName, List<Integer> sounfPartsTimeoutParm ){
        final Handler handler = new Handler();
        final List<Integer> sounfPartsTimeout = sounfPartsTimeoutParm;
        final Integer lastTimeout = sounfPartsTimeoutParm.get(sounfPartsTimeoutParm.size()- 1);

        final TextView sound_count = (TextView)findViewById(R.id.count_sound_temp);
//        sound_count.setText("0/0");

        if(mPlayer != null && mPlayer.isPlaying()){
            mPlayer.stop();
        }
        //else
        {
            //  ** Inicia o som **
            int resourceSoundId = getResources().getIdentifier(soundName, "raw", getPackageName());

            trackTeste = 0;
            mPlayer = MediaPlayer.create(activity, resourceSoundId);
            mPlayer.start();

            //  ** Thread dividindo o tempo **
            new Thread(new Runnable() {
                @Override
                public void run() {
                    paginaVisivel = 0;
                    Integer cur_pos = 0;

                    try{

                        while (mPlayer.isPlaying()){
                            SystemClock.sleep(10);
                            cur_pos = mPlayer.getCurrentPosition();
                            //Log.d(TAG, "th:"+ cur_pos.toString() + "/" + mPlayer.getDuration());

                            //  ** Notificador de tempo na tela TEMP    **
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    sound_count.setText(mPlayer.getCurrentPosition() + "/" + mPlayer.getDuration());
                                }
                            });


                            //  ** Acabou a musica  **
                          if(cur_pos >= mPlayer.getDuration()- 10){
                                break;
                            }

                            if(cur_pos >= sounfPartsTimeout.get(paginaVisivel) && paginaVisivel < sounfPartsTimeout.size() - 1){

                                paginaVisivel += 1;
                                //Log.e(TAG, "Mudei par apagina :" + paginaVisivel.toString());
                                handler.post(new Runnable() {

                                    @Override
                                    public void run() {
                                        viewPager.setCurrentItem(paginaVisivel);
                                        Log.e(TAG, "Mudei par apagina :" + paginaVisivel.toString());
                                    }
                                });
                            }else if(cur_pos >=lastTimeout){
                                break;
                            }

                        }
                        Thread.currentThread().interrupt();
                        mPlayer.stop();
                        //mPlayer.release();
                        viewPager.setCurrentItem(0);
                        Log.e(TAG, "PArou 1 :");

                    }catch (Exception ex){
                        Log.e(TAG, ex.getMessage());
                        Thread.currentThread().interrupt();
                        mPlayer.stop();
                        viewPager.setCurrentItem(0);
                        //mPlayer.release();
                        Log.e(TAG, "PArou 2 :");
                    }

                }
            }).start();

        }

    }
/*
    private void threadMudaPaginaTimeout(Integer pagina){
        class MudaPagina implements Runnable{
            Integer pagina;
            MudaPagina(Integer paginaParm){
                pagina = paginaParm;
            };
            @Override
            public void run() {
                viewPager.setCurrentItem(pagina);
            }
        }
        Thread t = new Thread(new MudaPagina(1));
        t.start();
    }*/

    private void timeSoundThread(){
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int pageTeste = 0;
                while (mPlayer.isPlaying()){
                    SystemClock.sleep(10);
                    Integer cur_pos = mPlayer.getCurrentPosition();
                    Log.d(TAG, "th:"+ cur_pos.toString() + "/" + mPlayer.getDuration());

                    if(cur_pos >= 5000){
                        //Log.e(TAG, "Thread parou");
                        Thread.currentThread().interrupt();
//                        mPlayer.stop();

//                        pageTeste += 1;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                viewPager.setCurrentItem(1);
                            }
                        });



                        break;
                    }
                }

            }
        }).start();
    }

    private void playSound(){
        paginaSelecionada = getPaginaAtual();
        DataTsEscalas dt = dao.getTela(escalaOpcaoSelecionada.getId(), escalaSelecionada.getId(), paginaSelecionada, 1);

        Log.e(TAG, escalaOpcaoSelecionada.getId().toString() + " " +
                escalaSelecionada.getId().toString() + " " +
                paginaSelecionada.toString());

        /*
            int resourceId = getActivity().getResources().getIdentifier(image, "drawable", getActivity().getPackageName());
            pagina.setImageResource(Integer.valueOf(resourceId));
        */
        String som = dt.getSom1();
        int resourceId = this.getResources().getIdentifier(som, "raw", this.getPackageName());

        if(mPlayer != null && mPlayer.isPlaying()){
            mPlayer.stop();
        }else {
            mPlayer = MediaPlayer.create(activity, Integer.valueOf(resourceId));
            mPlayer.start();
        }

    }

    private void limpaEscalaSelecionado(){

        //  ** Desmarca
        Integer indexE = listaEscala.indexOf(escalaSelecionada);
        if(indexE != -1){
            listaEscala.remove(indexE);
            escalaSelecionada.setSelecionado(false);
            listaEscala.set(indexE, escalaSelecionada);
        }

        adapterEscala.notifyDataSetChanged();
        escalaSelecionada = null;
    }

    private void montaEscala(){
        listaEscala = new ArrayList<>();

        Escala escala = new Escala(1,"LA",false, "m");
        listaEscala.add(escala);

        escala = new Escala(2,"DO",false,"M");
        listaEscala.add(escala);

        escala = new Escala(3,"LA",false, "m");
        listaEscala.add(escala);

        escala = new Escala(4,"DO",false,"M");
        listaEscala.add(escala);
//        escalaSelecionada = escala;

        escala = new Escala(5,"RÉ",false, "m");
        listaEscala.add(escala);

        escala = new Escala(6,"FA",false, "M");
        listaEscala.add(escala);

        escala = new Escala(7,"MI",false, "m");
        listaEscala.add(escala);

        escala = new Escala(8,"SOL",false, "M");
        listaEscala.add(escala);

        escala = new Escala(9,"SOL",false,"m");
        listaEscala.add(escala);

//        if (Build.VERSION.SDK_INT >= 24)
//            escala = new Escala(10,Html.fromHtml("SI&#9837;",Html.FROM_HTML_MODE_LEGACY).toString(),false, "M");
//        else
//            escala = new Escala(10,Html.fromHtml("SI&#9837;").toString(),false, "M");


        escala = new Escala(10,"SIb",false,"M");
        listaEscala.add(escala);

        escala = new Escala(11,"SI",false,"m");
        listaEscala.add(escala);

        escala = new Escala(12,"RÉ",false, "M");
        listaEscala.add(escala);

        escala = new Escala(13,"DO",false, "m");
        listaEscala.add(escala);

        escala = new Escala(14,"MIb",false, "M");
//        if (Build.VERSION.SDK_INT >= 24)
//            escala = new Escala(14,Html.fromHtml("MI&#9837;",Html.FROM_HTML_MODE_LEGACY).toString(),false,"M");
//        else
//            escala = new Escala(14,Html.fromHtml("MI&#9837;").toString(),false,"M");

        listaEscala.add(escala);

        escala = new Escala(15,"FA#",false,"m");
//        if (Build.VERSION.SDK_INT >= 24)
//            escala = new Escala(15,Html.fromHtml("FA&#9839;",Html.FROM_HTML_MODE_LEGACY).toString(),false, "m");
//        else
//            escala = new Escala(15,Html.fromHtml("FA&#9839;").toString(),false, "m");
        listaEscala.add(escala);

        escala = new Escala(16,"LA",false, "M");
        listaEscala.add(escala);

        escala = new Escala(17,"FA",false, "m");
        listaEscala.add(escala);

        escala = new Escala(18,"LAb",false, "M");
//        if (Build.VERSION.SDK_INT >= 24)
//            escala = new Escala(18,Html.fromHtml("LA&#9837;",Html.FROM_HTML_MODE_LEGACY).toString(),false, "M");
//        else
//            escala = new Escala(18,Html.fromHtml("LA&#9837;").toString(),false,"M");
        listaEscala.add(escala);

        escala = new Escala(19,"DO#",false, "m");
//        if (Build.VERSION.SDK_INT >= 24)
//            escala = new Escala(19,Html.fromHtml("DO&#9839;",Html.FROM_HTML_MODE_LEGACY).toString(),false, "m");
//        else
//            escala = new Escala(19,Html.fromHtml("DO&#9839;").toString(),false, "m");
        listaEscala.add(escala);

        escala = new Escala(20,"MI",false, "M");
        listaEscala.add(escala);

        escala = new Escala(21,"SIb",false, "m");
//        if (Build.VERSION.SDK_INT >= 24)
//            escala = new Escala(21,Html.fromHtml("SI&#9837;",Html.FROM_HTML_MODE_LEGACY).toString(),false, "m");
//        else
//            escala = new Escala(21,Html.fromHtml("SI&#9837;").toString(),false, "m");
        listaEscala.add(escala);

        escala = new Escala(22,"RÉb",false,"M");
//        if (Build.VERSION.SDK_INT >= 24)
//            escala = new Escala(22,Html.fromHtml("RÉ&#9837;",Html.FROM_HTML_MODE_LEGACY).toString(),false, "M");
//        else
//            escala = new Escala(22,Html.fromHtml("RÉ&#9837;").toString(),false, "M");
        listaEscala.add(escala);

        escala = new Escala(23,"SOL#",false,"m");
//        if (Build.VERSION.SDK_INT >= 24)
//            escala = new Escala(23,Html.fromHtml("SOL&#9839;",Html.FROM_HTML_MODE_LEGACY).toString(),false, "m");
//        else
//            escala = new Escala(23,Html.fromHtml("SOL&#9839;").toString(),false, "m");
        listaEscala.add(escala);

        escala = new Escala(24,"SI",false, "M");
        listaEscala.add(escala);

        escala = new Escala(25,"MIb",false, "m");
//        if (Build.VERSION.SDK_INT >= 24)
//            escala = new Escala(25,Html.fromHtml("MI&#9837;",Html.FROM_HTML_MODE_LEGACY).toString(),false, "m");
//        else
//            escala = new Escala(25,Html.fromHtml("MI&#9837;").toString(),false, "m");
        listaEscala.add(escala);

        escala = new Escala(26,"SOLb",false, "M");
//        if (Build.VERSION.SDK_INT >= 24)
//            escala = new Escala(26,Html.fromHtml("SOL&#9837;",Html.FROM_HTML_MODE_LEGACY).toString(),false, "M");
//        else
//            escala = new Escala(26,Html.fromHtml("SOL&#9837;").toString(),false, "M");
        listaEscala.add(escala);

        escala = new Escala(27,"RÉ#",false,"m");
//        if (Build.VERSION.SDK_INT >= 24)
//            escala = new Escala(27,Html.fromHtml("RÉ&#9839;",Html.FROM_HTML_MODE_LEGACY).toString(),false, "m");
//        else
//            escala = new Escala(27,Html.fromHtml("RÉ&#9839;").toString(),false, "m");
        listaEscala.add(escala);

        escala = new Escala(28,"FA#",false,"M");
//        if (Build.VERSION.SDK_INT >= 24)
//            escala = new Escala(28,Html.fromHtml("FA&#9839;",Html.FROM_HTML_MODE_LEGACY).toString(),false, "M");
//        else
//            escala = new Escala(28,Html.fromHtml("FA&#9839;").toString(),false, "M");
        listaEscala.add(escala);

        escala = new Escala(29,"LAb",false, "m");
//        if (Build.VERSION.SDK_INT >= 24)
//            escala = new Escala(29,Html.fromHtml("LA&#9837;",Html.FROM_HTML_MODE_LEGACY).toString(),false, "m");
//        else
//            escala = new Escala(29,Html.fromHtml("LA&#9837;").toString(),false, "m");
        listaEscala.add(escala);

        escala = new Escala(30,"DOb",false, "M");
//        if (Build.VERSION.SDK_INT >= 24)
//            escala = new Escala(30,Html.fromHtml("DO&#9837;",Html.FROM_HTML_MODE_LEGACY).toString(),false, "M");
//        else
//            escala = new Escala(30,Html.fromHtml("DO&#9837;").toString(),false, "M");
        listaEscala.add(escala);

        escala = new Escala(31,"LA#",false, "m");
//        if (Build.VERSION.SDK_INT >= 24)
//            escala = new Escala(31,Html.fromHtml("LA&#9839;",Html.FROM_HTML_MODE_LEGACY).toString(),false, "m");
//        else
//            escala = new Escala(31,Html.fromHtml("LA&#9839;").toString(),false, "m");
        listaEscala.add(escala);

        escala = new Escala(32,"DO#",false, "M");
//        if (Build.VERSION.SDK_INT >= 24)
//            escala = new Escala(32,Html.fromHtml("DO&#9839;",Html.FROM_HTML_MODE_LEGACY).toString(),false, "M");
//        else
//            escala = new Escala(32,Html.fromHtml("DO&#9839;").toString(),false, "M");
        listaEscala.add(escala);
    }

    private void montaEscalaOpcoes(){
        listaOpcoesEscala = new ArrayList<>();
        EscalaOpcao escalaOpcao = new EscalaOpcao(1,"Tonalidades", true );
        listaOpcoesEscala.add(escalaOpcao);
        escalaOpcaoSelecionada = escalaOpcao;

        escalaOpcao = new EscalaOpcao(2,"Escalas +", false );
        listaOpcoesEscala.add(escalaOpcao);
//        escalaOpcaoSelecionada = escalaOpcao;

        escalaOpcao = new EscalaOpcao(3,"Melodias +", false );
        listaOpcoesEscala.add(escalaOpcao);

        escalaOpcao = new EscalaOpcao(4,"Intervalos", false );
        listaOpcoesEscala.add(escalaOpcao);

        escalaOpcao = new EscalaOpcao(5,"Tríades", false );
        listaOpcoesEscala.add(escalaOpcao);

        escalaOpcao = new EscalaOpcao(6,"Campo Harmônico", false );
        listaOpcoesEscala.add(escalaOpcao);

        escalaOpcao = new EscalaOpcao(7,"Cadência +", false );
        listaOpcoesEscala.add(escalaOpcao);

        escalaOpcao = new EscalaOpcao(8,"Trítono", false );
        listaOpcoesEscala.add(escalaOpcao);

    }

    @Override
    public void playSoundI() {
        playSound();
    }

    @Override
    public void mudaEscalaI(Integer escalaId) {
//        mudaEscalaSelecionado(position);
        mudaEscalaSelecionadoPorId(escalaId);
        mudaTelaEscala();
        paginaSelecionada = getPaginaAtual();
    }

    //  ** Salvo a posição da tela que devo repassar para todos os fragments do viewpage
    @Override
    public void setObject(Object ob) {
        positionTela = (Integer)ob;
        viewPager.getAdapter().notifyDataSetChanged();
    }

    @Override
    public Object getObject() {
        return positionTela;
    }
}
