package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Config;
import com.mygdx.game.Entity.EmptyScreen;
import com.mygdx.game.Entity.PlayerShipForFight;
import com.mygdx.game.Entity.PlayerShipForMap;
import com.mygdx.game.Strategy;

public class MapScreen extends AbstractMechanicsScreen {


    private Stage stage;
    private Table container;
    private int row_height;

    private Config config;
    private Batch batch;
    private Strategy game;
    Texture texture;
    private OrthographicCamera gameCamera;
    private Viewport gamePort;
    private TextureAtlas atlas;

    private ShapeRenderer sr;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private PlayerShipForMap player;
    private World world;
    private Box2DDebugRenderer b2dr;

    public MapScreen(Strategy strategy, int i, EmptyScreen emptyScreen) {
        super(strategy, i, emptyScreen);
        this.config = strategy.config;

        atlas = new TextureAtlas("ship_set.txt");

        this.game = strategy;

        gameCamera = new OrthographicCamera();

        gamePort = new FitViewport(Strategy.V_WIDTH / Strategy.PPM, Strategy.V_HEIGHT / Strategy.PPM, gameCamera);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("main_map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Strategy.PPM);
        sr = new ShapeRenderer();
        sr.setColor(Color.CYAN);
        gameCamera.position.set((gamePort.getWorldWidth() / 2) / Strategy.PPM, (gamePort.getWorldHeight() / 2) / Strategy.PPM, 0);

        row_height = Gdx.graphics.getWidth() / 12;
        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();

        player = new PlayerShipForMap(world);
        stage = new Stage();
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        Gdx.input.setInputProcessor(stage);

        container = new Table();
        stage.addActor(container);
        container.setFillParent(true);

        Table navTable = new Table();

        navTable.bottom().pad(100).defaults().expandX();
        TextButton up = new TextButton("Up", skin);
        navTable.add(up);
        up.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
            }
        });

        navTable.row();
        TextButton left = new TextButton("left", skin);
        navTable.add(left);
        left.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        TextButton ok = new TextButton("ok", skin);
        navTable.add(ok);
        ok.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        TextButton right = new TextButton("right", skin);
        navTable.add(right);
        right.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        navTable.row();
        TextButton down = new TextButton("down", skin);
        navTable.add(down);
        down.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {

            }
        });
        stage.addActor(navTable);
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }
//
//    @Override
//    public void show() {

//        table.pad(10).defaults().expandX().space(4);



//        InputListener stopToucfhDown = new InputListener() {
//            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
//                event.stop();
//                return false;
//            }
//        };
//
//        for (int i = 0; i < 10; i++) {
//            table.row();
//            table.add(new Label(i + "uno", skin)).expandX().fillX();
//
//            TextButton button = new TextButton(i + "dos", skin);
//            table.add(button);
//            button.addListener(new ClickListener() {
//                public void clicked (InputEvent event, float x, float y) {
//                    System.out.println("click " + x + ", " + y);
//                }
//            });

//            Slider slider = new Slider(0, 100, 1, false, skin);
//            slider.addListener(stopTouchDown); // Stops touchDown events from propagating to the FlickScrollPane.
//            table.add(slider);

//            table.add(new Label(i + "tres long0 long1 long2 long3 long4 long5 long6 long7 long8 long9 long10 long11 long12", skin));
//        }

//    }
    public void handleInput(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && player.b2body.getLinearVelocity().y == 0) {
            player.b2body.applyLinearImpulse(new Vector2(0, 8f), player.b2body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && player.b2body.getLinearVelocity().y == 0) {
            player.b2body.applyLinearImpulse(new Vector2(0, -8f), player.b2body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x == 0) {
            player.b2body.applyLinearImpulse(new Vector2(8f, 0), player.b2body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x == 0) {
            player.b2body.applyLinearImpulse(new Vector2(-8f, 0), player.b2body.getWorldCenter(), true);
        }
    }


    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0.36f, 0.72f, 0.96f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();
        b2dr.render(world, gameCamera.combined);
        renderer.setView(gameCamera);
        batch = game.batch;
        game.batch.setProjectionMatrix(gameCamera.combined);
        game.batch.begin();
        renderer.render(new int[]{0, 1, 2, 3, 4, 5, 6, 7});
        game.batch.end();

        b2dr.render(world, gameCamera.combined);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
        gameCamera.update();
    }

    @Override
    public void dispose() {
        texture.dispose();
        map.dispose();
        batch.dispose();
    }

    public void update(float dt) {
        handleInput(dt);
        world.step(1 / 60f, 6, 2);
        gameCamera.position.x = player.b2body.getPosition().x;
        gameCamera.position.y = player.b2body.getPosition().y;
        gameCamera.update();
        renderer.setView(gameCamera);
    }

}
