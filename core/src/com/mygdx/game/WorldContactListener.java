package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Levels.GameLevel;
import com.mygdx.game.Screens.AbstractMechanicsScreen;
import com.mygdx.game.Screens.ChooseGoalScreen;
import com.mygdx.game.Screens.WinScreen;

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

        if ("playerMap".equals(fixtureA.getUserData()) || "playerMap".equals(fixtureB.getUserData())) {
            Fixture player = "playerMap".equals(fixtureA.getUserData()) ? fixtureA : fixtureB;
            Fixture object = player == fixtureA ? fixtureB : fixtureA;
            if (object.getUserData() != null && ((String) object.getUserData()).contains("port") && ((((String) object.getUserData()).charAt(5)) - '0') == strategy.goal) {
                if (ChooseGoalScreen.level == GameLevel.finalLevel) {
                    strategy.setScreen(new WinScreen(strategy, 0, screen));
                } else {
                    strategy.setScreen(new ChooseGoalScreen(strategy, 0, screen, (((String) object.getUserData()).charAt(5))));
                }
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
