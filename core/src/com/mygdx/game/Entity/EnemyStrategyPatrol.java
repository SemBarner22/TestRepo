package com.mygdx.game.Entity;

import com.badlogic.gdx.math.Vector2;

public class EnemyStrategyPatrol implements EnemyStrategy {
    enum Direction {
        R, U, L, D;
    }

    private final int range = 4;
    private Direction cur = Direction.R;
    private int steps = 0;

    @Override
    public Vector2 nextMove() {
        Vector2 ret = null;
        switch (cur) {
            case R: {
                ret = new Vector2(8f, 0);
                steps++;
                if (steps == range) {
                    steps = 0;
                    cur = Direction.U;
                }
                break;
            }
            case U: {
                ret = new Vector2(0, 8f);
                steps++;
                if (steps == range) {
                    steps = 0;
                    cur = Direction.L;
                }
                break;
            }
            case L: {
                ret = new Vector2(-8f, 0);
                steps++;
                if (steps == range) {
                    steps = 0;
                    cur = Direction.D;
                }
                break;
            }
            case D: {
                ret = new Vector2(0, -8f);
                steps++;
                if (steps == range) {
                    steps = 0;
                    cur = Direction.R;
                }
                break;
            }
        }
        return ret;
    }
}
