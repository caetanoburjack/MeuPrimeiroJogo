package com.example.caetano.meuprimeirojogo;

import com.example.caetano.meuprimeirojogo.andgraph.AGGameManager;
import com.example.caetano.meuprimeirojogo.andgraph.AGInputManager;
import com.example.caetano.meuprimeirojogo.andgraph.AGScene;
import com.example.caetano.meuprimeirojogo.andgraph.AGScreenManager;
import com.example.caetano.meuprimeirojogo.andgraph.AGSoundEffect;
import com.example.caetano.meuprimeirojogo.andgraph.AGSoundManager;
import com.example.caetano.meuprimeirojogo.andgraph.AGSprite;
import com.example.caetano.meuprimeirojogo.andgraph.AGTimer;

import java.util.ArrayList;

/**
 * Created by Caetano on 22/06/2017.
 */

public class CenaJogo extends AGScene {

    private int efeitoCatraca = 0; //som do canhao
    private int efeitoExplosao = 0; //som da explosao
    private int multiplicador = 1; //multiplicador para o placar
    private int pontuacao = 0; //multiplicador para o placar
    private int tempPontuacao = 0; //multiplicador para o placar

    private AGTimer tempoCanhao = null; //Tempo para controle do movimento do canhao
    private AGTimer tempoBala = null; //Tempo para controle das balas

    private AGSprite btnVoltar = null;
    private AGSprite planoFundo = null;
    private AGSprite canhao = null;
    private AGSprite barraSuperior = null;

    ArrayList<AGSprite> vetorTiros = null;
    ArrayList<AGSprite> vetorExplosoes = null;
    AGSprite[] navios = new AGSprite[3];
    AGSprite[] placar = new AGSprite[6];

    boolean bPausa = false;

    public CenaJogo(AGGameManager vrManager) {
        super(vrManager);
    }

    @Override
    public void init() {
        setSceneBackgroundColor(1, 1, 1);
        tempoCanhao = new AGTimer(100);
        tempoBala = new AGTimer(150);
        vetorTiros = new ArrayList<AGSprite>();
        vetorExplosoes = new ArrayList<AGSprite>();

        efeitoCatraca = AGSoundManager.vrSoundEffects.loadSoundEffect("toc.wav");
        efeitoExplosao = AGSoundManager.vrSoundEffects.loadSoundEffect("explodenavio.wav");

        // Carrega a imagem de fundo 100x100 centro da tela
        planoFundo = createSprite(R.drawable.textmar, 1, 1);
        planoFundo.setScreenPercent(100, 100);
        planoFundo.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        planoFundo.vrPosition.setY(AGScreenManager.iScreenHeight / 2);

        // Carrega a imagem do canhao na base da tela
        canhao = createSprite(R.drawable.canhao, 1, 1);
        canhao.setScreenPercent(12, 20);
        canhao.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        canhao.vrPosition.setY(canhao.getSpriteHeight() / 2);

        barraSuperior = createSprite(R.drawable.barrasuperior, 1, 1);
        barraSuperior.setScreenPercent(100, 10);
        barraSuperior.vrPosition.fX = AGScreenManager.iScreenWidth / 2;
        barraSuperior.vrPosition.fY = AGScreenManager.iScreenHeight - barraSuperior.getSpriteHeight() / 2;
        barraSuperior.bAutoRender = false;

        //DEFININDO OS NAVIOS
        navios[0] = createSprite(R.drawable.navio, 1, 1);
        navios[0].setScreenPercent(20, 12);
        navios[0].iMirror = AGSprite.HORIZONTAL;
        navios[0].vrDirection.fX = 1;
        navios[0].vrPosition.fX = -navios[0].getSpriteWidth() / 2;
        navios[0].vrPosition.fY = AGScreenManager.iScreenHeight - navios[0].getSpriteHeight() / 2 - barraSuperior.getSpriteHeight();

        navios[1] = createSprite(R.drawable.navio, 1, 1);
        navios[1].setScreenPercent(20, 12);
        navios[1].vrDirection.fX = -1;
        navios[1].vrPosition.fX = AGScreenManager.iScreenWidth + navios[1].getSpriteWidth() / 2;
        navios[1].vrPosition.fY = navios[0].vrPosition.fY - navios[1].getSpriteHeight();

        navios[2] = createSprite(R.drawable.navio, 1, 1);
        navios[2].setScreenPercent(20, 12);
        navios[2].iMirror = AGSprite.HORIZONTAL;
        navios[2].vrDirection.fX = 1;
        navios[2].vrPosition.fX = AGScreenManager.iScreenWidth + navios[2].getSpriteWidth() / 2;
        navios[2].vrPosition.fY = navios[1].vrPosition.fY - navios[2].getSpriteHeight();

        for (int pos = 0; pos < placar.length; pos++) {
            placar[pos] = createSprite(R.drawable.fonte, 4, 4);
            placar[pos].setScreenPercent(8, 8);
            placar[pos].vrPosition.fY = barraSuperior.vrPosition.fY;
            placar[pos].vrPosition.fX = 20 + multiplicador * placar[pos].getSpriteWidth();
            placar[pos].bAutoRender = false;
            multiplicador++;
            for (int i = 0; i < 10; i++) {
                placar[pos].addAnimation(1, false, i);
            }
        }
    }

    public void render() {
        super.render();
        barraSuperior.render();
        for (AGSprite digito : placar) {
            digito.render();
        }
    }

    @Override
    public void restart() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void loop() {
        if (AGInputManager.vrTouchEvents.backButtonClicked()) {
            bPausa = !bPausa;


// vrGameManager.setCurrentScene(0);
//            return;
        }

        if (bPausa == false) {
            atualizaMovimentoCanhao();
            atualizaBalas();
            criaTiro();
            atualizaNavios();
            verificaColisaoBalasNavios();
            atualizaExplosoes();
            atualizaPlacar();
        }
    }

    private void atualizaPlacar() {
        if (tempPontuacao > 0) {
//            for (AGSprite digito : placar) {
//                digito.bVisible = !digito.bVisible;
//            }
            tempPontuacao--;
            pontuacao++;
        } else {
//            for (AGSprite digito : placar) {
//                digito.bVisible = true;
//            }
        }
        placar[5].setCurrentAnimation(pontuacao % 10);
        placar[4].setCurrentAnimation((pontuacao % 100) / 10);
        placar[3].setCurrentAnimation((pontuacao % 1000) / 100);
        placar[2].setCurrentAnimation((pontuacao % 10000) / 1000);
        placar[1].setCurrentAnimation((pontuacao % 100000) / 10000);
        placar[0].setCurrentAnimation((pontuacao % 1000000) / 100000);
    }

    private void atualizaNavios() {
        for (AGSprite navio : navios) {
            navio.vrPosition.fX += 5 * navio.vrDirection.fX;
            if (navio.vrDirection.fX == 1) {
                if (navio.vrPosition.fX >= AGScreenManager.iScreenWidth + navio.getSpriteWidth() / 2) {
                    navio.iMirror = AGSprite.NONE;
                    navio.vrDirection.fX = -1;
                }
            } else {
                if (navio.vrPosition.fX <= -navio.getSpriteWidth() / 2) {
                    navio.iMirror = AGSprite.HORIZONTAL;
                    navio.vrDirection.fX = 1;
                }
            }
        }
    }

    private void verificaColisaoBalasNavios() {
        for (AGSprite bala : vetorTiros) {
            if (bala.bRecycled)
                continue;
            for (AGSprite navio : navios) {
                if (bala.collide(navio)) {
                    tempPontuacao += 50;
                    criaExplosao(navio.vrPosition.fX, navio.vrPosition.fY);
                    bala.bRecycled = true;
                    bala.bVisible = false;
                    AGSoundManager.vrSoundEffects.play(efeitoExplosao);
                    if (navio.vrDirection.fX == 1) {
                        navio.vrDirection.fX = -1;
                        navio.iMirror = AGSprite.NONE;
                        navio.vrPosition.fX = AGScreenManager.iScreenWidth + navio.getSpriteWidth() / 2;
                    } else {
                        navio.vrDirection.fX = 1;
                        navio.iMirror = AGSprite.HORIZONTAL;
                        navio.vrPosition.fX = -navio.getSpriteWidth() / 2;
                    }
                }
            }
        }
    }

    // Coloca uma bala no vetor de tiros
    private void criaTiro() {
        tempoBala.update();

        // Tenta reciclar uma bala criada anteriormente
        if (AGInputManager.vrTouchEvents.screenClicked()) {
            if (!tempoBala.isTimeEnded()) {
                return;
            }

            tempoBala.restart();

            for (AGSprite bala : vetorTiros) {
                if (bala.bRecycled) {
                    bala.bRecycled = false;
                    bala.bVisible = true;
                    bala.vrPosition.fX = canhao.vrPosition.fX;
                    bala.vrPosition.fY = canhao.getSpriteHeight() + bala.getSpriteHeight() / 2;
                    return;
                }
            }

            AGSprite novaBala = createSprite(R.drawable.bala, 1, 1);
            novaBala.setScreenPercent(8, 5);
            novaBala.vrPosition.fX = canhao.vrPosition.fX;
            novaBala.vrPosition.fY = canhao.getSpriteHeight() + novaBala.getSpriteHeight() / 2;
            vetorTiros.add(novaBala);
        }
    }

    // metodo para atualizar o movimento das balas
    private void atualizaBalas() {
        for (AGSprite bala : vetorTiros) {
            bala.vrPosition.fY += 10;

            if (bala.vrPosition.fY > AGScreenManager.iScreenHeight + bala.getSpriteHeight() / 2) {
                bala.bRecycled = true;
                bala.bVisible = false;
            }
        }
    }


    private void criaExplosao(float x, float y) {
        for (AGSprite explosao : vetorExplosoes) {
            if (explosao.bRecycled) {
                explosao.bRecycled = false;
                explosao.getCurrentAnimation().restart();
                explosao.vrPosition.fX = x;
                explosao.vrPosition.fY = y;
                return;
            }
        }
        AGSprite novaExplosao = createSprite(R.drawable.explosao, 4, 2);
        novaExplosao.setScreenPercent(20, 12);
        novaExplosao.addAnimation(10, false, 0, 7);
        novaExplosao.vrPosition.fX = x;
        novaExplosao.vrPosition.fY = y;
        vetorExplosoes.add(novaExplosao);
    }

    private void atualizaExplosoes() {
        for (AGSprite explosao : vetorExplosoes) {
            if (explosao.getCurrentAnimation().isAnimationEnded()) {
                explosao.bRecycled = true;
            }
        }
    }

    // Metodo criado para movimentar o canhao
    private void atualizaMovimentoCanhao() {
        tempoCanhao.update();
        if (tempoCanhao.isTimeEnded()) {
            tempoCanhao.restart();
            if (AGInputManager.vrAccelerometer.getAccelX() > 2.0f) {
                if (canhao.vrPosition.getX() <= AGScreenManager.iScreenWidth - canhao.getSpriteWidth() / 2) {
                    AGSoundManager.vrSoundEffects.play(efeitoCatraca);
                    canhao.vrPosition.setX(canhao.vrPosition.getX() + 10);
                }
            } else if (AGInputManager.vrAccelerometer.getAccelX() < -2.0f) {
                if (canhao.vrPosition.getX() > 0 + canhao.getSpriteWidth() / 2) {
                    AGSoundManager.vrSoundEffects.play(efeitoCatraca);
                    canhao.vrPosition.setX(canhao.vrPosition.getX() - 10);
                }
            }
        }
    }

}
