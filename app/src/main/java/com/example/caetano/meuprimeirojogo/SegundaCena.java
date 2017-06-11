package com.example.caetano.meuprimeirojogo;

import com.example.caetano.meuprimeirojogo.andgraph.AGGameManager;
import com.example.caetano.meuprimeirojogo.andgraph.AGInputManager;
import com.example.caetano.meuprimeirojogo.andgraph.AGScene;
import com.example.caetano.meuprimeirojogo.andgraph.AGTimeManager;
import com.example.caetano.meuprimeirojogo.andgraph.AGTimer;

/**
 * Created by Caetano on 10/06/2017.
 */

public class SegundaCena extends AGScene {
    SegundaCena(AGGameManager gerenteJogo) {
        super(gerenteJogo);
    }

    AGTimer tempo;

    @Override
    public void init() {//metodo chamado para iniciar a cena, chamado uma unica vez quando esta cena for colocada na tela

        tempo = new AGTimer(3000);

        setSceneBackgroundColor(1.0f, 1.0f, 0.0f);
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
    }
}
