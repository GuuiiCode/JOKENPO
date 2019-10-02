package br.com.guilherme.jokempo;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends Activity implements View.OnClickListener {

    private ViewHolder mViewHolder = new ViewHolder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializaCampos();
        camposEventoClick();
        animacaoDaTela();
    }

    private void inicializaCampos() {
        mViewHolder.jogador = findViewById(R.id.img_interrogacao_jogador);
        mViewHolder.maquina = findViewById(R.id.img_iterrogacao_maquina);
        mViewHolder.pedra = findViewById(R.id.img_btn_pedra);
        mViewHolder.papel = findViewById(R.id.img_btn_papel);
        mViewHolder.tesoura = findViewById(R.id.img_btn_tesoura);

        mViewHolder.mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.alex_play);
    }

    private void camposEventoClick() {
        mViewHolder.pedra.setOnClickListener(this);
        mViewHolder.papel.setOnClickListener(this);
        mViewHolder.tesoura.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mViewHolder.jogador.setScaleX(-1f);
        switch (v.getId()) {
            case (R.id.img_btn_pedra):
                mViewHolder.jogador.setImageResource(R.drawable.pedra);
                mViewHolder.jogada1 = 1;
                break;
            case (R.id.img_btn_papel):
                mViewHolder.jogador.setImageResource(R.drawable.papel);
                mViewHolder.jogada1 = 2;
                break;
            case (R.id.img_btn_tesoura):
                mViewHolder.jogador.setImageResource(R.drawable.tesoura);
                mViewHolder.jogada1 = 3;
                break;
        }
        mViewHolder.maquina.setImageResource(R.drawable.interrogacao);
        mViewHolder.maquina.startAnimation(mViewHolder.desaparece);
    }

    private void animacaoDaTela() {
        mViewHolder.desaparece = new AlphaAnimation(1, 0);
        mViewHolder.desaparece.setDuration(1500);

        mViewHolder.aparece = new AlphaAnimation(0, 1);
        mViewHolder.aparece.setDuration(250);

        mViewHolder.desaparece.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                tocaMusica();
                mViewHolder.maquina.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mViewHolder.maquina.setVisibility(View.INVISIBLE);
                mViewHolder.maquina.startAnimation(mViewHolder.aparece);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mViewHolder.aparece.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                sorteiaJogadaInimigo();
                mViewHolder.maquina.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                verificaJogada();
                mViewHolder.maquina.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void sorteiaJogadaInimigo() {
        Random r = new Random();
        int numRandom = r.nextInt(3);
        switch (numRandom) {
            case 0:
                mViewHolder.maquina.setImageResource(R.drawable.pedra);
                mViewHolder.jogada2 = 1;
                break;
            case 1:
                mViewHolder.maquina.setImageResource(R.drawable.papel);
                mViewHolder.jogada2 = 2;
                break;
            case 2:
                mViewHolder.maquina.setImageResource(R.drawable.tesoura);
                mViewHolder.jogada2 = 3;
                break;
        }
    }

    public void verificaJogada() {
        if (mViewHolder.jogada1 == mViewHolder.jogada2) {
            Toast.makeText(this, getString(R.string.empate), Toast.LENGTH_SHORT).show();
        } else if ((mViewHolder.jogada1 == 1 && mViewHolder.jogada2 == 3) ||
                (mViewHolder.jogada1 == 2 && mViewHolder.jogada2 == 1) ||
                (mViewHolder.jogada1 == 3 && mViewHolder.jogada2 == 2)) {
            Toast.makeText(this, getString(R.string.ganhou), Toast.LENGTH_SHORT).show();
        } else if ((mViewHolder.jogada1 == 1 && mViewHolder.jogada2 == 2) ||
                (mViewHolder.jogada1 == 2 && mViewHolder.jogada2 == 3) ||
                (mViewHolder.jogada1 == 3 && mViewHolder.jogada2 == 1)) {
            Toast.makeText(this, getString(R.string.perdeu), Toast.LENGTH_SHORT).show();
        }
    }

    public void tocaMusica(){
        if(mViewHolder.mediaPlayer != null){
            mViewHolder.mediaPlayer.start();
        }
    }

    private static class ViewHolder {
        private ImageView jogador;
        private ImageView maquina;
        private ImageButton pedra;
        private ImageButton papel;
        private ImageButton tesoura;
        private Animation aparece;
        private Animation desaparece;
        private int jogada1;
        private int jogada2;
        private MediaPlayer mediaPlayer;
    }
}
