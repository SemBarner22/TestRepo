package com.mygdx.game.Entity;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Strategy;

public class EnemyStrategyTransit implements EnemyStrategy {
    private final boolean right;

    public EnemyStrategyTransit(boolean right) {
        this.right = right;
    }

    @Override
    public Vector2 nextMove() {
        return new Vector2(right ? Strategy.MOVE_MUL * 1f : -Strategy.MOVE_MUL * 1f, 0);
    }
}
