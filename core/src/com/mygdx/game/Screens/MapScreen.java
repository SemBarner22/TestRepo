package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
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
import com.mygdx.game.Entity.*;
import com.mygdx.game.Levels.GameLevel;
import com.mygdx.game.Scenes.MapHud;
import com.mygdx.game.Strategy;
import com.mygdx.game.WorldContactListener;

import java.util.ArrayList;

public class MapScreen extends AbstractMechanicsScreen {

    public GameLevel gameLevel;
    public char goal = 'O';
    private int row_height;

//    private MapHud hud;
    private Config config;
    private Batch batch;
    private Strategy game;
    Texture texture;
    private OrthographicCamera gameCamera;
    private Viewport gamePort;
    private TextureAtlas atlas;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private PlayerShipForMap player;
    private ArrayList<EnemyShipForMap> enemies;
    private int enemiesCount = 256;
    private World world;
    private Box2DDebugRenderer b2dr;

    static String mission = "aaaaa";
    public Label missionLabel;
    public Label levelLabel;
    public Label coordinateLabel;
    private Music music;

    public GameLevel level = new GameLevel(1);

    public MapScreen(Strategy strategy, int i, Screen emptyScreen) {
        super(strategy, i, emptyScreen);
        mission = "Go to port A[ X: 56, Y: 13]";
        this.config = strategy.config;

        atlas = new TextureAtlas("ship_set.txt");

        this.game = strategy;

        gameCamera = new OrthographicCamera();

        gamePort = new FitViewport(Strategy.V_WIDTH / Strategy.PPM, Strategy.V_HEIGHT / Strategy.PPM, gameCamera);

        music = Strategy.manager.get("music/audio/mapMusic.mp3", Music.class);
        music.setLooping(true);
        music.play();
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("main_map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Strategy.PPM);
//        sr = new ShapeRenderer();
//        sr.setColor(Color.CYAN);
        gameCamera.position.set((gamePort.getWorldWidth() / 2) / Strategy.PPM, (gamePort.getWorldHeight() / 2) / Strategy.PPM, 0);

        row_height = Gdx.graphics.getWidth() / 12;
        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();

        player = new PlayerShipForMap(world);
        enemies = new ArrayList<>();
        for (int u = 0; u < enemiesCount; u++) {
//            boolean transitStrategy = Math.random() < 0.5;
//            EnemyStrategy enemyStrategy;
//            float x, y;
//            y = (float) (16 + Math.random() * 96);
//            if (transitStrategy) {
//                boolean right = Math.random() < 0.5;
//                x = right ? 0 : 128 * 16 - 1;
//                enemyStrategy = new EnemyStrategyTransit(right);
//            } else {
//                enemyStrategy = new EnemyStrategyPatrol();
//                x = (float) Math.random() * 92;
//            }
//            x = (float) Math.floor(x);
//            y = (float) Math.floor(y);

            enemies.add(new EnemyShipForMap(world, null, 256 / Strategy.PPM, 256 / Strategy.PPM));
//            System.out.format("Spawned %s enemy at %f %f%n", transitStrategy ? "transit" : "patrol", x, y);
        }

//        stage = new Stage();


//        Table table = new Table();
//        table.top();
//        table.setFillParent(true);
//
//
//        missionLabel = new Label(mission, skin);
//
//        table.add(missionLabel).expandX().padTop(10);
//
//        stage.addActor(table);
//        hud = new MapHud(game.batch);
//        hud.setNewMission("Get to Port A");

        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;


        BodyDef bdef = new BodyDef();


        for (MapObject object : map.getLayers().get("ports").getObjects().getByType(RectangleMapObject.class)) {
            int aaa = (int) object.getProperties().get("port");
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rectangle.getX() + rectangle.getWidth() / 2) / Strategy.PPM, (rectangle.getY() + rectangle.getHeight() / 2) / Strategy.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rectangle.getWidth() / 2 / Strategy.PPM, rectangle.getHeight() / 2 / Strategy.PPM);

            fdef.shape = shape;
            body.createFixture(fdef).setUserData("ports" + aaa);
        }

        for (MapLayer layer : map.getLayers()) {
            if (!layer.getName().equals("ports")) {
                for (MapObject object : layer.getObjects().getByType(RectangleMapObject.class)) {
//                    System.out.println("feegregeg");
                    Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                    bdef.type = BodyDef.BodyType.StaticBody;
                    bdef.position.set((rectangle.getX() + rectangle.getWidth() / 2) / Strategy.PPM, (rectangle.getY() + rectangle.getHeight() / 2) / Strategy.PPM);
//                    System.out.println(bdef.position.x + " " +  bdef.position.y);
                    body = world.createBody(bdef);

                    shape.setAsBox(rectangle.getWidth() / 2 / Strategy.PPM, rectangle.getHeight() / 2 / Strategy.PPM);

                    fdef.shape = shape;
                    body.createFixture(fdef);


                }
            }
        }
        world.setContactListener(new WorldContactListener(world, strategy, this));

//        for (MapLayer layer : map.getLayers()) {
//            for (MapObject object : layer.getObjects()) {
//                PolygonShape polygon = new PolygonShape();
//                float[] vertices = ((PolygonMapObject) object).getPolygon().getTransformedVertices();
//                float[] worldVertices = new float[vertices.length];
//
//                for (int u = 0; u < vertices.length; u++) {
//                    worldVertices[u] = vertices[u] / Strategy.PPM;
//                }
//
//                polygon.set(worldVertices);
//                bdef.type = BodyDef.BodyType.StaticBody;
//                body = world.createBody(bdef);
//                fdef.shape = shape;
//                body.createFixture(fdef);

//                Polygon polygon = ((PolygonMapObject) object).getPolygon();
//
//                bdef.type = BodyDef.BodyType.StaticBody;
////                bdef.position.;
//
////                bdef.position.
//                body = world.createBody(bdef);
//
////                shape.set(polygon.getVertices());
//                shape.set(polygon.);
//                fdef.shape = shape;
//                body.createFixture(fdef);
//            }
//        }
    }

    private String formatPlayerCoordinate() {
        return String.format(
                "X: %d, Y: %d",
                Math.round(player.getX()),
                Math.round(player.getY())
        );
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    @Override
    public void show() {
        super.show();

        if (!config.desktop()) {
            Table navTable = new Table();

            navTable.bottom().pad(10).defaults();
            navTable.left().pad(200).defaults();
            TextButton up = new TextButton("U", skin);
            navTable.add(up).width(150).height(150);

            up.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    if (player.b2body.getLinearVelocity().y == 0) {
                        player.b2body.applyLinearImpulse(new Vector2(0, Strategy.MOVE_MUL * 2f), player.b2body.getWorldCenter(), true);
                        player.updateTexture(PlayerShipForMap.Direction.U);
                        moveEnemies(2f);
                    }
                }
            });

            navTable.row();
            TextButton left = new TextButton("L", skin);
            navTable.add(left).width(150).height(150).padLeft(-300);
            left.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    if (player.b2body.getLinearVelocity().x == 0) {
                        player.b2body.applyLinearImpulse(new Vector2(-Strategy.MOVE_MUL * 2f, 0), player.b2body.getWorldCenter(), true);
                        player.updateTexture(PlayerShipForMap.Direction.L);
                        moveEnemies(2f);
                    }
                }
            });

//        TextButton ok = new TextButton("ok", skin);
//        navTable.add(ok);
//        ok.addListener(new ClickListener() {
//            public void clicked(InputEvent event, float x, float y) {
//
//            }
//        });

            TextButton right = new TextButton("R", skin);
            navTable.add(right).width(150).height(150);
            right.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    if (player.b2body.getLinearVelocity().x == 0) {
                        player.b2body.applyLinearImpulse(new Vector2(Strategy.MOVE_MUL * 2f, 0), player.b2body.getWorldCenter(), true);
                        player.updateTexture(PlayerShipForMap.Direction.R);
                        moveEnemies(2f);
                    }
                }
            });

            navTable.row();
            TextButton down = new TextButton("D", skin);
            navTable.add(down).width(150).height(150);
            down.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    if (player.b2body.getLinearVelocity().y == 0) {
                        player.b2body.applyLinearImpulse(new Vector2(0, -Strategy.MOVE_MUL * 2f), player.b2body.getWorldCenter(), true);
                        player.updateTexture(PlayerShipForMap.Direction.D);
                        moveEnemies(2f);
                    }
                }
            });
            stage.addActor(navTable);

        }
        Table table = new Table();
        table.top();
        table.setFillParent(true);

        missionLabel = new Label(mission, skin);
        levelLabel = new Label("Level: " + (ChooseGoalScreen.level - 1), skin);
        coordinateLabel = new Label(formatPlayerCoordinate(), skin);

        if (!config.desktop()) {
            missionLabel.setFontScale(3);
            levelLabel.setFontScale(3);
            coordinateLabel.setFontScale(3);
            table.add(missionLabel).expandX().padTop(10).height(200);
            table.add(levelLabel).expandX().padTop(10).height(200);
            table.add(coordinateLabel).expandX().padTop(10).height(200);
        } else {
            table.add(missionLabel).expandX().left().padLeft(5).top();
            table.add(levelLabel).expandX().center().top();
            table.add(coordinateLabel).expandX().right().padRight(5).top();
        }
        table.row();

        stage.addActor(table);
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
////            TextButton button = new TextButton(i + "dos", skin);
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
    private void movePlayer(Vector2 vec) {
        if (player != null) {
            player.b2body.applyLinearImpulse(vec, player.b2body.getWorldCenter(), true);
        }
    }
    public void moveEnemies() {
        for (EnemyShipForMap enemy : enemies) {
            if (!enemy.enabled) {
                continue;
            }
            enemy.b2body.applyLinearImpulse(enemy.strategy.nextMove(1f), enemy.b2body.getWorldCenter(), true);
            enemy.updateTexture();
        }
    }

    public void moveEnemies(float k) {
        for (EnemyShipForMap enemy : enemies) {
            if (!enemy.enabled) {
                continue;
            }
            enemy.b2body.applyLinearImpulse(enemy.strategy.nextMove(k), enemy.b2body.getWorldCenter(), true);
            enemy.updateTexture();
        }
    }

    public void handleInput(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            movePlayer(new Vector2(0, Strategy.MOVE_MUL * 1f));
//            player.b2body.applyLinearImpulse(new Vector2(0, Strategy.MOVE_MUL * 1f), player.b2body.getWorldCenter(), true);
            player.updateTexture(PlayerShipForMap.Direction.U);
            moveEnemies();
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            movePlayer(new Vector2(0, Strategy.MOVE_MUL * 0.125f));
//            player.b2body.applyLinearImpulse(new Vector2(0, Strategy.MOVE_MUL * 0.25f), player.b2body.getWorldCenter(), true);
            player.updateTexture(PlayerShipForMap.Direction.U);
            moveEnemies(0.125f);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            movePlayer(new Vector2(0, -Strategy.MOVE_MUL * 1f));
//            player.b2body.applyLinearImpulse(new Vector2(0, -Strategy.MOVE_MUL * 1f), player.b2body.getWorldCenter(), true);
            player.updateTexture(PlayerShipForMap.Direction.D);
            moveEnemies();
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            movePlayer(new Vector2(0, -Strategy.MOVE_MUL * 0.125f));
            player.updateTexture(PlayerShipForMap.Direction.D);
            moveEnemies(0.125f);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            movePlayer(new Vector2(Strategy.MOVE_MUL * 1f, 0));
//            player.b2body.applyLinearImpulse(new Vector2(Strategy.MOVE_MUL * 1f, 0), player.b2body.getWorldCenter(), true);
            player.updateTexture(PlayerShipForMap.Direction.R);
            moveEnemies();
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            movePlayer(new Vector2(Strategy.MOVE_MUL * 0.125f, 0));
            player.updateTexture(PlayerShipForMap.Direction.R);
            moveEnemies(0.125f);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            movePlayer(new Vector2(-Strategy.MOVE_MUL * 1f, 0));
//            player.b2body.applyLinearImpulse(new Vector2(-Strategy.MOVE_MUL * 1f, 0), player.b2body.getWorldCenter(), true);
            player.updateTexture(PlayerShipForMap.Direction.L);
            moveEnemies();
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            movePlayer(new Vector2(-Strategy.MOVE_MUL * 0.125f, 0));
            player.updateTexture(PlayerShipForMap.Direction.L);
            moveEnemies(0.125f);
        }
    }


    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0.36f, 0.72f, 0.96f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        b2dr.render(world, gameCamera.combined);

        missionLabel.setText(mission);
        coordinateLabel.setText(formatPlayerCoordinate());

        EnemyShipForMap toKill = null;
        for (EnemyShipForMap enemyShipForMap: enemies) {
            if (!enemyShipForMap.enabled) {
                continue;
            }
            if ((enemyShipForMap.b2body.getPosition().x - player.getX()) * (enemyShipForMap.b2body.getPosition().x - player.getX()) + (enemyShipForMap.b2body.getPosition().y - player.getY()) * (enemyShipForMap.b2body.getPosition().y - player.getY()) < 20) {
                toKill = enemyShipForMap;
                music.pause();
                strategy.setScreen(new ReadyForFightScreen(strategy, 0, this));
                break;
            }
        }

        if (toKill != null && enemies.contains(toKill)) {
            enemies.remove(toKill);
            toKill.b2body.setActive(false);
            world.destroyBody(toKill.b2body);
        }

//        int[] layers = new int[] {2};
        b2dr.render(world, gameCamera.combined);
        renderer.render();

        game.batch.setProjectionMatrix(gameCamera.combined);
        game.batch.begin();
        player.draw(game.batch);
        for (EnemyShipForMap enemy : enemies) {
            if (!enemy.enabled) {
                continue;
            }
            enemy.draw(game.batch);
        }

        game.batch.end();
        renderer.setView(gameCamera);
        batch = game.batch;


//        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
//        hud.stage.draw();

        stage.act();
        stage.draw();


    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
        gameCamera.update();
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        texture.dispose();
        map.dispose();
        batch.dispose();
    }

    public void update(float dt) {
        if (!music.isPlaying()) {
            music.play();
        }
        handleInput(dt);
        world.step(1 / 60f, 6, 2);
        player.update(dt);
        for (EnemyShipForMap enemy : enemies) {
            enemy.update(dt);
        }
        gameCamera.position.x = player.b2body.getPosition().x;
        gameCamera.position.y = player.b2body.getPosition().y;
        gameCamera.update();
        renderer.setView(gameCamera);
    }

    public void rearrageEnemies() {
        for (EnemyShipForMap enemy : enemies) {
            if (!enemy.enabled) {
                continue;
            }
            enemy.enabled = false;
            enemy.strategy = null;
            world.destroyBody(enemy.b2body);
        }
        int u = 0;
        for (int i = 0; i < gameLevel.patrolCount; i++, u++) {
            EnemyShipForMap enemy = enemies.get(u);
            float x = (float) Math.floor(Math.random() * 128);
            float y = (float) Math.floor(16 + Math.random() * (128 - 16 * 2));
//            enemy.teleport(x, y);
            enemy.defineShip(x, y);
            enemy.strategy = new EnemyStrategyPatrol();
            enemy.enabled = true;
            System.out.format("Spawned patrol enemy at %f %f%n", x, y);
        }
        for (GameLevel.TransitGroup group : gameLevel.transitGroups) {
            for (int i = 0; i < group.groupSize; i++, u++) {
                EnemyShipForMap enemy = enemies.get(u);
                float x = group.right ? 0 : 127;
                float y = (float) Math.floor(group.groupMeanY + Math.random() * group.groupSize / 2);
//                enemy.teleport(x, y);
                enemy.defineShip(x, y);
                enemy.strategy = new EnemyStrategyTransit(group.right);
                enemy.enabled = true;
                System.out.format("Spawned transit enemy at %f %f%n", x, y);
            }
        }
    }
}
