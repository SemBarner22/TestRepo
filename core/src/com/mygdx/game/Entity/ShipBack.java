package com.mygdx.game.Entity;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Screens.FightScreen;

import java.awt.geom.RectangularShape;

public class ShipBack extends Sprite {
    public World world;
    public Body b2body;
    private TextureRegion ship;

    public ShipBack(World world, FightScreen fightScreen) {
        super(fightScreen.getAtlas().findRegion("iron"));
        this.world = world;
        defineShip();
        TextureRegion iron = new TextureRegion(getTexture(), 55, 1, 16, 16);
        setBounds(100, 100, 100, 32);
        setRegion(iron);
    }

    public void defineShip() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(100 , 100);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5);
        fdef.shape = shape;
        b2body.createFixture(fdef);
        b2body.setGravityScale(0);
    }

    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
    }
}
