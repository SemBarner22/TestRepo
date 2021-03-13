package com.mygdx.game.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.AbstractMechanicsScreen;
import com.mygdx.game.EmptyScreen;
import com.mygdx.game.Strategy;

public class MapScreen extends AbstractMechanicsScreen {

    private Strategy game;
    Texture texture;
    private OrthographicCamera gameCamera;
    private Viewport gamePort;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer b2dr;

    public MapScreen(Strategy strategy, int i, EmptyScreen emptyScreen) {
        super(strategy, i, emptyScreen);
        texture = new Texture("badlogic.jpg");
        gameCamera = new OrthographicCamera();
        gamePort = new ScreenViewport(gameCamera);

        mapLoader = new TmxMapLoader();
//        map = mapLoader.load();
        renderer = new OrthogonalTiledMapRenderer(map);

        gameCamera.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();

        BodyDef bdef = new BodyDef();


    }




}
