package com.mygdx.game.Entity;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Screens.FightScreen;

import java.awt.geom.RectangularShape;

public class ShipSail extends Sprite {
    public World world;
    public Body b2body;
    private TextureRegion ship;

    public ShipSail(World world, FightScreen fightScreen, int x, int y, int player, TextureAtlas atlas) {
        super(atlas.findRegion("sail"));
        this.world = world;
        defineShip(x, y, player);
        TextureRegion iron;
        if (player == 1) {
            iron = new TextureRegion(getTexture(), 176, 0, 47, 32);
        } else {
            iron = new TextureRegion(getTexture(), 177, 0, 47, 32);
        }
        setBounds(0, 0, 16 * 4, 32);
        setRegion(iron);
    }

    public void defineShip(int x, int y, int player) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(x, y);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(8);
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData("ship " + player);
        b2body.setGravityScale(0);
    }

    public void update(float dt, int gr) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        b2body.setGravityScale(gr);
    }
}
