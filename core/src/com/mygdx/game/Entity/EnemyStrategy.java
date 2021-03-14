package com.mygdx.game.Entity;

import com.badlogic.gdx.math.Vector2;

public interface EnemyStrategy {
    Vector2 nextMove();

    EnemyStrategyPatrol.Direction getDirection();
}
