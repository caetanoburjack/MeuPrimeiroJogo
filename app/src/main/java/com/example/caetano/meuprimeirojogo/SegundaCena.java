package com.example.caetano.meuprimeirojogo;

import com.example.caetano.meuprimeirojogo.andgraph.AGGameManager;
import com.example.caetano.meuprimeirojogo.andgraph.AGInputManager;
import com.example.caetano.meuprimeirojogo.andgraph.AGScene;
import com.example.caetano.meuprimeirojogo.andgraph.AGScreenManager;
import com.example.caetano.meuprimeirojogo.andgraph.AGSprite;
import com.example.caetano.meuprimeirojogo.andgraph.AGTimeManager;
import com.example.caetano.meuprimeirojogo.andgraph.AGTimer;

/**
 * Created by Caetano on 10/06/2017.
 */

public class SegundaCena extends AGScene {
    AGSprite bulldog = null;
    AGSprite briga = null;
    AGSprite gato = null;
    AGSprite fonte = null;
    int indiceAnimacao;
    boolean pausa;
    AGTimer tempo;

    SegundaCena(AGGameManager gerenteJogo) {
        super(gerenteJogo);
    }


    @Override
    public void init() {//metodo chamado para iniciar a cena, chamado uma unica vez quando esta cena for colocada na tela

        tempo = new AGTimer(3000);

        setSceneBackgroundColor(1.0f, 1.0f, 1.0f);

        gato = createSprite(R.drawable.gato, 8, 8);
        gato.setScreenPercent(20, 40);
        gato.vrPosition.setX(gato.getSpriteWidth() / 2);
        gato.vrPosition.setY(AGScreenManager.iScreenHeight / 2);
        gato.addAnimation(10, true, 0, 15);//gatinho pula
        gato.addAnimation(10, true, 16, 28);//gatinho espera
        gato.addAnimation(15, true, 29, 40);//gatinho caminha
        gato.setCurrentAnimation(2);

        briga = createSprite(R.drawable.briga, 8, 4);
        briga.setScreenPercent(20, 40);
        briga.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        briga.vrPosition.setY(AGScreenManager.iScreenHeight / 2);
        briga.addAnimation(15, true, 0, 23);
        briga.bVisible = false;


        bulldog = createSprite(R.drawable.buldogue, 4, 4);
        bulldog.setScreenPercent(20, 40);
        bulldog.vrPosition.setX(AGScreenManager.iScreenWidth - bulldog.getSpriteWidth() / 2);
        bulldog.vrPosition.setY(AGScreenManager.iScreenHeight / 2);
        bulldog.addAnimation(10, true, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
        bulldog.iMirror = AGSprite.HORIZONTAL;
    }

    @Override
    public void restart() {//eh chamado quando a cena volta do background

    }

    @Override
    public void stop() {//eh chamado quando a cena vai pra background

    }

    @Override
    public void loop() {//vai ficar executando pra sempre e se repetindo
        tempo.update();
        if (tempo.isTimeEnded()) {
            this.vrGameManager.setCurrentScene(0);
        }
//        if (AGInputManager.vrTouchEvents.screenClicked()) {
//            indiceAnimacao = (indiceAnimacao == 2) ? 0 : ++indiceAnimacao;
//        }
        if (AGInputManager.vrTouchEvents.screenClicked()) {
            pausa = !pausa;
        }
        if (!pausa) {
            logicaJogo();
        }


    }

    private void logicaJogo() {
        gato.vrPosition.fX += 20;
        bulldog.vrPosition.fX -= 20;
        if (bulldog.collide(gato)) {
            bulldog.bVisible = false;
            gato.bVisible = false;
            briga.bVisible = true;
        }
    }
}
