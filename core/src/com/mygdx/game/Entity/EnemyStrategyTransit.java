package com.mygdx.game.Entity;

import com.badlogic.gdx.math.Vector2;

public class EnemyStrategyTransit implements EnemyStrategy {
    private final boolean right;

    public EnemyStrategyTransit(boolean right) {
        this.right = right;
    }

    @Override
    public Vector2 nextMove() {
        return new Vector2(right ? 8f : -8f, 0);
    }
}
