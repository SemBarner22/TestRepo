package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Screens.AbstractMechanicsScreen;
import com.mygdx.game.Screens.ChooseGoalScreen;
import com.mygdx.game.Screens.FightScreen;
import com.mygdx.game.Strategy;

public class FightScreenContactListener implements ContactListener {

    World world;
    Strategy strategy;
    AbstractMechanicsScreen screen;
    Screen screen2;
    public FightScreenContactListener(World world, Strategy strategy, FightScreen fightScreen, Screen emptyScreen) {
        this.world = world;
        this.strategy = strategy;
        this.screen = fightScreen;
        this.screen2 = emptyScreen;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        if (fixtureA.getUserData() == null || fixtureB.getUserData() == null) {
            System.out.println("null");
        } else if (fixtureA.getUserData() != null && fixtureA.getUserData().equals(fixtureB.getUserData())) {
            System.out.println("lol");
        } else if (("core 1".equals(fixtureA.getUserData()) && "ship 0".equals(fixtureB.getUserData())) ||
                ("core 1".equals(fixtureB.getUserData()) && "ship 0".equals(fixtureA.getUserData()))) {
            System.out.println("1 win");
            strategy.setScreen(screen2);
        } else if (("core 1".equals(fixtureA.getUserData()) && "core -1".equals(fixtureB.getUserData())) ||
                ("core 1".equals(fixtureB.getUserData()) && "core -1".equals(fixtureA.getUserData()))) {
            System.out.println("no win");
        } else {
            System.out.println("2 win");
            //strategy.setScreen(new ChooseGoalScreen(strategy, 0, screen, (((String) object.getUserData()).charAt(5))));
        }
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
