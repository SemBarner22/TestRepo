package com.mygdx.game.Entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Strategy;

public class EnemyShipForMap extends Sprite {
    public World world;
    public Body b2body;
    public EnemyStrategy strategy;
    public float x, y;
    public boolean enabled = false;

    public EnemyShipForMap(World world, EnemyStrategy strategy, float x, float y) {
        this.world = world;
        this.strategy = strategy;
//        defineShip(x, y);
        setBounds(0, 0, 16 / Strategy.PPM, 16 / Strategy.PPM);
        setRegion(new Texture("sprites/enemy_ship.png"));
    }

    public void update(float dt) {
        if (enabled) {
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        }
    }

    public void defineShip(float x, float y) {
        BodyDef bdef = new BodyDef();
        bdef.position.set((x * 16 + 8) / Strategy.PPM, (y * 16 + 8) / Strategy.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(8 / Strategy.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData("enemyMap");
        b2body.setGravityScale(0);
        b2body.setLinearDamping(Strategy.MOVE_MUL * 1f);
    }

    public void updateTexture() {
        switch (strategy.getDirection()) {
            case L: {
                setRegion(new Texture("sprites/enemy_ship_to_right.png"));
                break;
            }
            case R: {
                setRegion(new Texture("sprites/enemy_ship_to_left.png"));
                break;
            }
            default: {
                setRegion(new Texture("sprites/enemy_ship.png"));
            }
        }
    }
}
