package com.mygdx.game.Entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Strategy;

public class PlayerShipForMap extends Sprite {
    public World world;
    public Body b2body;

    public PlayerShipForMap(World world) {
        this.world = world;
        defineShip();
    }

    private void defineShip() {
        BodyDef bdef = new BodyDef();
        bdef.position.set((55 * 16 + 8) / Strategy.PPM, (13 * 16 + 8) / Strategy.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / Strategy.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
        b2body.setGravityScale(0);
        b2body.setLinearDamping(8f);
    }
}
