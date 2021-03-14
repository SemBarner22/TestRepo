package com.mygdx.game.Entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Strategy;

public class PlayerShipForMap extends Sprite {
    public World world;
    public Body b2body;
//    public static Sprite sprite = new Sprite(new Texture("sprites/player_ship.png"));

    public PlayerShipForMap(World world) {
        this.world = world;
        defineShip();
        setBounds(0, 0, 16 / Strategy.PPM, 16 / Strategy.PPM);
        setRegion(new Texture("sprites/player_ship.png"));
    }

    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
    }

    private void defineShip() {
        BodyDef bdef = new BodyDef();
        bdef.position.set((25 * 16 + 8) / Strategy.PPM, ((128 - 15) * 16 + 8) / Strategy.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(8 / Strategy.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData("playerMap");
        b2body.setGravityScale(1);
        b2body.setLinearDamping(Strategy.MOVE_MUL * 1f);
    }
}
