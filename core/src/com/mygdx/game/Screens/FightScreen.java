package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Entity.*;
import com.mygdx.game.Scenes.FightHud;
import com.mygdx.game.Strategy;

import java.util.Random;

public class FightScreen extends AbstractMechanicsScreen {

    private Strategy game;
    private OrthographicCamera gameCamera;
    private Viewport gamePort;
    private FightHud hud;
    private TextureAtlas atlas1;
    private TextureAtlas atlas2;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer b2dr;

    private ShipBody shipBodyPlayer;
    private ShipBack shipBackPlayer;
    private ShipCorma shipCormaPlayer;
    private ShipSail shipSailPlayer;
    private ShipMachta shipMachtaPlayer;

    private ShipBody shipBodyEnemy;
    private ShipBack shipBackEnemy;
    private ShipCorma shipCormaEnemy;
    private ShipSail shipSailEnemy;
    private ShipMachta shipMachtaEnemy;

    private Core core;
    private boolean isFire;

    public FightScreen(Strategy strategy, int i, Screen emptyScreen) {
        super(strategy, i, emptyScreen);
        atlas1 = new TextureAtlas("new_ship_set_2.txt");
        atlas2 = new TextureAtlas("new_ship_set_2_rev.txt");
        this.game = strategy;
        gameCamera = new OrthographicCamera();
        gamePort = new StretchViewport(Strategy.V_WIDTH, Strategy.V_HEIGHT, gameCamera);
        hud = new FightHud(game.batch);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("fight.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1);

        gameCamera.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();
        shipBodyPlayer = new ShipBody(world, this, 16 * 2 - 2 , 32, 1, atlas1, "main_body");
        shipBackPlayer = new ShipBack(world, this, -7 , 48, 1, atlas1, "back_body");
        shipSailPlayer = new ShipSail(world, this,16 * 2 , 16 * 4 + 8, 1, atlas1);
        shipCormaPlayer = new ShipCorma(world, this, 16 * 5 + 4 , 16 * 3 + 4, 1, atlas1, "front_body");
        shipMachtaPlayer = new ShipMachta(world, this, 16 * 2 , 16 * 3 + 8, 1, atlas1, "wood");

        Random rnd = new Random();
        int start = 450 + (rnd.nextInt() % 450 + 450) % 450;
        shipBodyEnemy = new ShipBody(world, this, start, 32, 0, atlas2, "main_body_2_rev");
        shipBackEnemy = new ShipBack(world, this, start + 37, 48, 0, atlas2, "back_body_2_rev");
        shipSailEnemy = new ShipSail(world, this, start - 2 , 16 * 4 + 8, 0, atlas2);
        shipCormaEnemy = new ShipCorma(world, this, start - 44, 16 * 3 + 4, 0, atlas2, "front_body_2_rev");
        shipMachtaEnemy = new ShipMachta(world, this, start - 2, 16 * 3 + 8, 0, atlas2, "wood");

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        for (MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) , (rect.getY() + rect.getHeight() / 2) );

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
    }

    public TextureAtlas getAtlasPlayer() {
        return atlas1;
    }

    public TextureAtlas getAtlasEnemy() {
        return atlas2;
    }

    @Override
    public void show() {

    }

    public void handleInput(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !isFire) {
            isFire = true;
            core = new Core(world, this, shipBodyPlayer.b2body.getPosition().x, shipBodyPlayer.b2body.getPosition().y, Math.PI / 4, new TextureAtlas("core.txt"));
        }
        float move = 10f;
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && shipBodyPlayer.b2body.getLinearVelocity().x <= 100) {
            shipBodyPlayer.b2body.applyLinearImpulse(new Vector2(move, 0), shipBodyPlayer.b2body.getWorldCenter(), true);
            shipBackPlayer.b2body.applyLinearImpulse(new Vector2(move, 0), shipBackPlayer.b2body.getWorldCenter(), true);
            shipSailPlayer.b2body.applyLinearImpulse(new Vector2(move, 0), shipCormaPlayer.b2body.getWorldCenter(), true);
            shipCormaPlayer.b2body.applyLinearImpulse(new Vector2(move, 0), shipCormaPlayer.b2body.getWorldCenter(), true);
            shipMachtaPlayer.b2body.applyLinearImpulse(new Vector2(move, 0), shipMachtaPlayer.b2body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && shipBodyPlayer.b2body.getLinearVelocity().x >= -100) {
            shipBodyPlayer.b2body.applyLinearImpulse(new Vector2(-move, 0), shipBodyPlayer.b2body.getWorldCenter(), true);
            shipSailPlayer.b2body.applyLinearImpulse(new Vector2(-move, 0), shipBackPlayer.b2body.getWorldCenter(), true);
            shipBackPlayer.b2body.applyLinearImpulse(new Vector2(-move, 0), shipBackPlayer.b2body.getWorldCenter(), true);
            shipCormaPlayer.b2body.applyLinearImpulse(new Vector2(-move, 0), shipCormaPlayer.b2body.getWorldCenter(), true);
            shipMachtaPlayer.b2body.applyLinearImpulse(new Vector2(-move, 0), shipMachtaPlayer.b2body.getWorldCenter(), true);
        }
    }

    public void update(float dt) {
        handleInput(dt);
        world.step(1/60f, 6, 2);

        shipBodyPlayer.update(dt);
        shipBackPlayer.update(dt);
        shipSailPlayer.update(dt);
        shipMachtaPlayer.update(dt);
        shipCormaPlayer.update(dt);
        shipBodyEnemy.update(dt);
        shipBackEnemy.update(dt);
        shipSailEnemy.update(dt);
        shipMachtaEnemy.update(dt);
        shipCormaEnemy.update(dt);
        if (isFire) {
            gameCamera.position.x = core.b2body.getPosition().x;
            isFire = core.update(dt);
            if (isFire) {
                core.b2body.setActive(true);
            } else {
                core.b2body.setActive(false);
            }
        } else {
            gameCamera.position.x = Math.max(shipBodyPlayer.b2body.getPosition().x, gameCamera.position.x - 10);
        }
        gameCamera.update();
        renderer.setView(gameCamera);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();
        b2dr.render(world, gameCamera.combined);

        game.batch.setProjectionMatrix(gameCamera.combined);
        game.batch.begin();
        shipSailPlayer.draw(game.batch);
        shipBackPlayer.draw(game.batch);
        shipCormaPlayer.draw(game.batch);
        shipMachtaPlayer.draw(game.batch);
        shipSailPlayer.draw(game.batch);
        shipBodyPlayer.draw(game.batch);
        shipSailEnemy.draw(game.batch);
        shipBackEnemy.draw(game.batch);
        shipCormaEnemy.draw(game.batch);
        shipMachtaEnemy.draw(game.batch);
        shipSailEnemy.draw(game.batch);
        shipBodyEnemy.draw(game.batch);
        if (isFire) {
            core.draw(game.batch);
        }
        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
