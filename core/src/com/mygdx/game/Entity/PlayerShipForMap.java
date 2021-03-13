package com.mygdx.game.Entity;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Screens.FightScreen;
import com.mygdx.game.Screens.MapScreen;

import java.awt.geom.RectangularShape;

public class PlayerShipForMap extends Sprite {
    public World world;
    public Body b2body;
    private TextureRegion ship;

    public PlayerShipForMap(World world, MapScreen fightScreen) {
        super(fightScreen.getAtlas().findRegion("iron"));
        this.world = world;
        defineShip();
        ship = new TextureRegion(getTexture(), 55, 1, 16, 16);
        setBounds(0, 0, 16, 16);
        setRegion(ship);
    }

    public void defineShip() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 , 32);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5);
        fdef.shape = shape;
        b2body.createFixture(fdef);
    }

    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
    }
}
