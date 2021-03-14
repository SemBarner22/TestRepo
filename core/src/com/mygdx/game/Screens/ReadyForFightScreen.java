package com.mygdx.game.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.Entity.EmptyScreen;
import com.mygdx.game.Strategy;

public class ReadyForFightScreen extends AbstractMechanicsScreen {

    TextButton ready;

    public ReadyForFightScreen(Strategy strategy, int curPlayer, Screen previousScreen) {
        super(strategy, curPlayer, previousScreen);
    }

    @Override
    public void show() {
        super.show();
        ready = new TextButton("Enemy is approaching. Click when you are ready for battle!", skin);
        ready.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                stage.clear();
                strategy.setScreen(new FightScreen(strategy, 0, previousScreen));
                return true;
            }
        });
        container.center();
        container.addActor(ready);
    }

}
