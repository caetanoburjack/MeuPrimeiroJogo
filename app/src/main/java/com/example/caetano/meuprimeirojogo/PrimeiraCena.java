package com.example.caetano.meuprimeirojogo;

import com.example.caetano.meuprimeirojogo.andgraph.AGGameManager;
import com.example.caetano.meuprimeirojogo.andgraph.AGInputManager;
import com.example.caetano.meuprimeirojogo.andgraph.AGScene;
import com.example.caetano.meuprimeirojogo.andgraph.AGScreenManager;
import com.example.caetano.meuprimeirojogo.andgraph.AGSoundManager;
import com.example.caetano.meuprimeirojogo.andgraph.AGSprite;

/**
 * Created by Caetano on 10/06/2017.
 */

public class PrimeiraCena extends AGScene {
    AGSprite logo = null;
    int codigoEfeito = 0;


    PrimeiraCena(AGGameManager gerenteJogo) {
        super(gerenteJogo);
    }

    @Override
    public void init() {//metodo chamado para iniciar a cena, chamado uma unica vez quando esta cena for colocada na tela
        codigoEfeito = AGSoundManager.vrSoundEffects.loadSoundEffect("som.wav");
        logo = this.createSprite(R.drawable.to, 1, 1);//o motor faz automaticamente o corte de imagem, dessa forma esse 1, 1 significa que só eh feito
        //um corte na horizontal e outro na vertical... similar ao sprite no html
        //ou seja... se vc tem uma imagem que representa varias faces do mesmo objeto... como um image map
        logo.setScreenPercent(50, 30); //aqui é representado quanto da tela do aparelho a imagem vai ocupar (principio do design responsivo)
        //nesse caso foi definido que vai ocupar 30% da largura e 30% da altura da tela
        logo.vrPosition.setX(AGScreenManager.iScreenWidth / 2);//seta a posição da imagem na tela em relacao a largura
        logo.vrPosition.setY(AGScreenManager.iScreenHeight / 2);//seta a posição da imagem na tela em relação a altura
        setSceneBackgroundColor(1.0f, 0.0f, 0.0f);
        AGSoundManager.vrMusic.loadMusic("som.wav", true);//o segundo parametro é pra habilitar o autorepeat
        AGSoundManager.vrMusic.play();
    }

    @Override
    public void restart() {//eh chamado quando a cena volta do background

    }

    @Override
    public void stop() {//eh chamado quando a cena vai pra background

    }

    @Override
    public void loop() {//vai ficar executando pra sempre e se repetindo
//        if (AGInputManager.vrTouchEvents.screenClicked()) {
//            this.vrGameManager.setCurrentScene(1);
//        }
        if (logo.collide(AGInputManager.vrTouchEvents.getLastPosition())) {
            vrGameManager.setCurrentScene(1);
        }
    }
}
