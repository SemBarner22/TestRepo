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

    public ShipBack(World world, FightScreen fightScreen, int x, int y, int player, TextureAtlas atlas, String name) {
        super(atlas.findRegion(name));
        this.world = world;
        defineShip(x, y, player);
        TextureRegion iron;
        if (player == 1) {
            iron = new TextureRegion(getTexture(), 0, 0, 32, 30);
        } else {
            iron = new TextureRegion(getTexture(), 0, 0, 32, 30);
        }
        setBounds(0, 0, 20, 16);
        setRegion(iron);
    }

    public void defineShip(int x, int y, int player) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(x, y);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(4);
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData("ship " + player);
        b2body.setGravityScale(0);
    }

    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
    }
}
