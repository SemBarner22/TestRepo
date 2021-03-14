package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.Entity.EmptyScreen;
import com.mygdx.game.Screens.AbstractMechanicsScreen;
import com.mygdx.game.Strategy;

public class GameOverScreen extends AbstractMechanicsScreen {

    public GameOverScreen(Strategy strategy, int i, AbstractMechanicsScreen screen) {
        super(strategy, i, screen);
    }

    @Override
    public void show() {
        super.show();
        Label label = new Label("YOU DIED!", skin);
        label.setFontScale(1.5f);
        container.add(label);

        TextButton restart = new TextButton("restart", skin);
        restart.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                stage.clear();
                ChooseGoalScreen.level = 1;
                strategy.goal = 1;
                strategy.setScreen(new MapScreen(strategy, 0, new EmptyScreen()));
                return true;
            }
        });
        container.row();
        container.add(restart).width(150).height(150);;
        TextButton exit = new TextButton("exit", skin);
        exit.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                stage.clear();
                Gdx.app.exit();
                return true;
            }
        });
        container.add(exit).width(150).height(150);;
    }

}
