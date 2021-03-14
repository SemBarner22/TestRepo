package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Screens.AbstractMechanicsScreen;
import com.mygdx.game.Screens.ChooseGoalScreen;

public class WorldContactListener implements ContactListener {

    World world;
    Strategy strategy;
    AbstractMechanicsScreen screen;
    public WorldContactListener(World world, Strategy strategy, AbstractMechanicsScreen screen) {
        this.world = world;
        this.strategy = strategy;
        this.screen = screen;
    }


    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.getUserData().equals("playerMap") || fixtureB.getUserData().equals("playerMap")) {
            Fixture player = fixtureA.getUserData().equals("playerMap") ? fixtureA : fixtureB;
            Fixture object = player == fixtureA ? fixtureB : fixtureA;
            if (object.getUserData().equals("ports")) {
                strategy.setScreen(new ChooseGoalScreen(strategy, 0, screen));
            }
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
