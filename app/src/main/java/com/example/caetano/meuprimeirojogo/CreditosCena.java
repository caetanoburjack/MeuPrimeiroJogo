package com.example.caetano.meuprimeirojogo;

import com.example.caetano.meuprimeirojogo.andgraph.AGGameManager;
import com.example.caetano.meuprimeirojogo.andgraph.AGScene;
import com.example.caetano.meuprimeirojogo.andgraph.AGTimer;

/**
 * Created by Caetano on 11/06/2017.
 */

public class CreditosCena extends AGScene {
    AGTimer tempo;

    CreditosCena(AGGameManager gerenteJogo) {
        super(gerenteJogo);
    }

    @Override
    public void init() {
        tempo = new AGTimer(2000);
        setSceneBackgroundColor(0.0f, 0.0f, 1.0f);
    }

    @Override
    public void restart() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void loop() {
        tempo.update();
        if (tempo.isTimeEnded()) {
            this.vrGameManager.setCurrentScene(0);
        }
    }
}
