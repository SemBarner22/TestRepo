package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.Entity.EmptyScreen;
import com.mygdx.game.Strategy;

public class WinScreen extends AbstractMechanicsScreen {

    public WinScreen(Strategy strategy, int i, AbstractMechanicsScreen screen) {
        super(strategy, i, screen);
    }

    @Override
    public void show() {
        super.show();
        container.add(new Label("Great job, soldier! Now it's a matter of time until we win this war!", skin));
        TextButton restart = new TextButton("restart", skin);
        restart.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                stage.clear();
                ChooseGoalScreen.level = 1;
                strategy.setScreen(new MapScreen(strategy, 0, new EmptyScreen()));
                return true;
            }
        });
        container.row();
        container.add(restart);
        TextButton exit = new TextButton("exit", skin);
        exit.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                stage.clear();
                Gdx.app.exit();
                return true;
            }
        });
        container.add(exit);
    }
}
