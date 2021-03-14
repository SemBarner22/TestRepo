package com.mygdx.game.Entity;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Screens.FightScreen;

import java.awt.geom.RectangularShape;

public class ShipBody extends Sprite {
    public World world;
    public Body b2body;
    private TextureRegion ship;

    public ShipBody(World world, FightScreen fightScreen, int x, int y) {
        super(fightScreen.getAtlas().findRegion("main_body"));
        this.world = world;
        defineShip(x, y);
        TextureRegion iron = new TextureRegion(getTexture(), 64, 0, 80, 32);
        setBounds(0, 0, 80, 20);
        setRegion(iron);
    }

    public void defineShip(int x, int y) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(x, y);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(8);
        fdef.shape = shape;
        b2body.createFixture(fdef);
        b2body.setGravityScale(0);
    }

    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
    }
}
