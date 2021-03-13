package com.mygdx.game.Entity;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Screens.FightScreen;

import java.awt.geom.RectangularShape;

public class Core extends Sprite {
    public World world;
    public Body b2body;
    private TextureRegion ship;
    private boolean destroyed;

    public Core(World world, FightScreen fightScreen, float x, float y) {
        super(fightScreen.getAtlas().findRegion("back_body"));
        this.world = world;
        destroyed = false;
        defineShip(x, y);
        TextureRegion iron = new TextureRegion(getTexture(), 0, 0, 4, 4);
        setBounds(0, 0, 4, 4);
        setRegion(iron);
    }

    public void defineShip(float x, float y) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(x + 30, y);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(4);
        fdef.shape = shape;
        b2body.createFixture(fdef);
    }

    public boolean update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        if (b2body.getPosition().y < 48) {
            destroyed = true;
            return false;
        }
        return true;
    }

    public void draw(Batch batch) {
        if (!destroyed) {
            super.draw(batch);
        }
    }
}
