package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Strategy;

public class MapHud {
    public Stage stage;
    private Viewport viewport;

    private Integer worldTimer;
    private String mission = "aaaaa";
    Skin skin;
    public Label missionLabel;

    public void setNewMission(String mission) {
        this.mission = mission;
    }

    public MapHud(SpriteBatch sb) {

        OrthographicCamera gameCamera = new OrthographicCamera();

        viewport = new FitViewport(Strategy.V_WIDTH / Strategy.PPM, Strategy.V_HEIGHT / Strategy.PPM, gameCamera);
        stage = new Stage(viewport, sb);

        skin = new Skin(Gdx.files.internal("uiskin.json"));
        Table table = new Table();
        table.top();
        table.setFillParent(true);

        missionLabel = new Label(mission, skin);

        table.add(missionLabel).expandX().padTop(10);

        stage.addActor(table);
    }

}
