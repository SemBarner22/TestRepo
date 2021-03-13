package com.mygdx.game.Scenes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Strategy;

public class FightHud {
    public Stage stage;
    private Viewport viewport;

    private Integer worldTimer;

    public FightHud(SpriteBatch sb) {
        viewport = new FitViewport(Strategy.V_WIDTH, Strategy.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

    }
}
