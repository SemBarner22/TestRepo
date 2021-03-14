package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Screens.AbstractMechanicsScreen;
import com.mygdx.game.Screens.ChooseGoalScreen;
import com.mygdx.game.Screens.FightScreen;
import com.mygdx.game.Strategy;

public class FightScreenContactListener implements ContactListener {

    World world;
    Strategy strategy;
    AbstractMechanicsScreen screen;
    public FightScreenContactListener(World world, Strategy strategy, FightScreen fightScreen) {
        this.world = world;
        this.strategy = strategy;
        this.screen = fightScreen;
    }

    @Override
    public void beginContact(Contact contact) {
//        Fixture fixtureA = contact.getFixtureA();
//        Fixture fixtureB = contact.getFixtureB();
//
//        if ("playerShip".equals(fixtureA.getUserData()) || "playerShip".equals(fixtureB.getUserData())) {
//            Fixture player = "playerShip".equals(fixtureA.getUserData()) ? fixtureA : fixtureB;
//            Fixture object = player == fixtureA ? fixtureB : fixtureA;
//            if (object.getUserData() != null && ((String) object.getUserData()).contains("port") && ((((String) object.getUserData()).charAt(5)) - '0') == strategy.goal) {
//                strategy.setScreen(new ChooseGoalScreen(strategy, 0, screen, (((String) object.getUserData()).charAt(5))));
//            }
//        }
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
