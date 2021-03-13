package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;

public class WorldContactListener implements ContactListener {



    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.getUserData().equals("playerMap") || fixtureB.getUserData().equals("playerMap")) {
            Fixture player = fixtureA.getUserData().equals("playerMap") ? fixtureA : fixtureB;
            Fixture object = player == fixtureA ? fixtureB : fixtureA;


        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}