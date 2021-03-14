package com.mygdx.game.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.Screens.AbstractMechanicsScreen;
import com.mygdx.game.Strategy;

public class GameOverScreen extends AbstractMechanicsScreen {



    public GameOverScreen(Strategy strategy, int i, AbstractMechanicsScreen screen) {
        super(strategy, i, screen);
    }

    @Override
    public void show() {
        super.show();
        container.add(new Label("YOU DIED!", skin));
    }

}
