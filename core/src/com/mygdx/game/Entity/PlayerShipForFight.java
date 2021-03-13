package com.mygdx.game.Entity;

import com.badlogic.gdx.physics.box2d.*;

public class PlayerShipForFight {
    public World world;
    public Body b2body;

    public PlayerShipForFight(World world) {
        this.world = world;
        defineShip();
    }

    public void defineShip() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32, 32);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}