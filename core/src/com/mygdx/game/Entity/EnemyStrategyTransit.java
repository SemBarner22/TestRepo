package com.mygdx.game.Entity;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Strategy;

public class EnemyStrategyTransit implements EnemyStrategy {
    private final boolean right;

    public EnemyStrategyTransit(boolean right) {
        this.right = right;
    }

    @Override
    public Vector2 nextMove(float k) {
        return new Vector2(right ? Strategy.MOVE_MUL * k : -Strategy.MOVE_MUL * k, 0);
    }

    @Override
    public EnemyStrategyPatrol.Direction getDirection() {
        return right ? EnemyStrategyPatrol.Direction.R : EnemyStrategyPatrol.Direction.L;
    }
}
