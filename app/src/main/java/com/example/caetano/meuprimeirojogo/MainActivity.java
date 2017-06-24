package com.example.caetano.meuprimeirojogo;

import android.os.Bundle;

import com.example.caetano.meuprimeirojogo.andgraph.AGActivityGame;

public class MainActivity extends AGActivityGame {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //Inicia o motor... o segundo parametro está relacionado ao acelerometro.. (false = desligado, true = ligado)
        init(this, true);
        PrimeiraCena cena1 = new PrimeiraCena(this.vrManager);
        SegundaCena cena2 = new SegundaCena(this.vrManager);
        CreditosCena cena3 = new CreditosCena(this.vrManager);
        CenaJogo cena4 = new CenaJogo(this.vrManager);
        vrManager.addScene(cena1);
        vrManager.addScene(cena2);
        vrManager.addScene(cena3);
        vrManager.addScene(cena4);

    }
}
