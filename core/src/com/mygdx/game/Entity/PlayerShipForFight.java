package com.mygdx.game.Entity;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

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
    }
}
