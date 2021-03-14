package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

public class FightScreen extends AbstractMechanicsScreen {

    private Strategy game;
    private OrthographicCamera gameCamera;
    private Viewport gamePort;
    private FightHud hud;
    private TextureAtlas atlas;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer b2dr;

    private ShipBody shipBody;
    private ShipBack shipBack;
    private ShipCorma shipCorma;
    private ShipSail shipSail;
    private ShipMachta shipMachta;

    private Core core;
    private boolean isFire;

    public FightScreen(Strategy strategy, int i, EmptyScreen emptyScreen) {
        super(strategy, i, emptyScreen);
        atlas = new TextureAtlas("new_ship_set_2.txt");
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
        shipBody = new ShipBody(world, this);
        shipBack = new ShipBack(world, this);
        shipSail = new ShipSail(world, this);
        shipCorma = new ShipCorma(world, this);
        shipMachta = new ShipMachta(world, this);

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

    public TextureAtlas getAtlas() {
        return atlas;
    }

    @Override
    public void show() {

    }

    public void handleInput(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !isFire) {
            isFire = true;
            core = new Core(world, this, shipBody.b2body.getPosition().x, shipBody.b2body.getPosition().y, Math.PI / 4);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && shipBody.b2body.getLinearVelocity().x <= 10) {
            shipBody.b2body.applyLinearImpulse(new Vector2(1f, 0), shipBody.b2body.getWorldCenter(), true);
            shipBack.b2body.applyLinearImpulse(new Vector2(1f, 0), shipBack.b2body.getWorldCenter(), true);
            shipSail.b2body.applyLinearImpulse(new Vector2(1f, 0), shipCorma.b2body.getWorldCenter(), true);
            shipCorma.b2body.applyLinearImpulse(new Vector2(1f, 0), shipCorma.b2body.getWorldCenter(), true);
            shipMachta.b2body.applyLinearImpulse(new Vector2(1f, 0), shipMachta.b2body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && shipBody.b2body.getLinearVelocity().x >= -10) {
            shipBody.b2body.applyLinearImpulse(new Vector2(-1f, 0), shipBody.b2body.getWorldCenter(), true);
            shipSail.b2body.applyLinearImpulse(new Vector2(-1f, 0), shipBack.b2body.getWorldCenter(), true);
            shipBack.b2body.applyLinearImpulse(new Vector2(-1f, 0), shipBack.b2body.getWorldCenter(), true);
            shipCorma.b2body.applyLinearImpulse(new Vector2(-1f, 0), shipCorma.b2body.getWorldCenter(), true);
            shipMachta.b2body.applyLinearImpulse(new Vector2(-1f, 0), shipMachta.b2body.getWorldCenter(), true);
        }
    }

    public void update(float dt) {
        handleInput(dt);
        world.step(1/60f, 6, 2);

        shipBody.update(dt);
        shipBack.update(dt);
        shipSail.update(dt);
        shipMachta.update(dt);
        shipCorma.update(dt);
        if (isFire) {
            isFire = core.update(dt);
            if (isFire) {
                core.b2body.setActive(true);
            } else {
                core.b2body.setActive(false);
            }
        }
        gameCamera.position.x = shipBody.b2body.getPosition().x;
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
        shipSail.draw(game.batch);
        shipBack.draw(game.batch);
        shipCorma.draw(game.batch);
        shipMachta.draw(game.batch);
        shipSail.draw(game.batch);
        shipBody.draw(game.batch);
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
