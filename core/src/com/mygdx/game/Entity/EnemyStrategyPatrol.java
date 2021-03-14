package com.mygdx.game.Entity;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Strategy;

import java.util.Arrays;
import java.util.Collections;

public class EnemyStrategyPatrol implements EnemyStrategy {
    enum Direction {
        R, U, L, D;
    }

    private final int range = 4;
    private Direction cur = Direction.R;
    private int steps = 0;

    public EnemyStrategyPatrol() {
        steps = (int) (Math.random() * range);
        cur = Collections.unmodifiableList(Arrays.asList(Direction.values())).get((int) (Math.random() * (4 - 1e-5)));
    }

    @Override
    public Vector2 nextMove(float k) {
        Vector2 ret = null;
        switch (cur) {
            case R: {
                ret = new Vector2(Strategy.MOVE_MUL * k, 0);
                steps++;
                if (steps == range) {
                    steps = 0;
                    cur = Direction.U;
                }
                break;
            }
            case U: {
                ret = new Vector2(0, Strategy.MOVE_MUL * k);
                steps++;
                if (steps == range) {
                    steps = 0;
                    cur = Direction.L;
                }
                break;
            }
            case L: {
                ret = new Vector2(-Strategy.MOVE_MUL * k, 0);
                steps++;
                if (steps == range) {
                    steps = 0;
                    cur = Direction.D;
                }
                break;
            }
            case D: {
                ret = new Vector2(0, -Strategy.MOVE_MUL * k);
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

    @Override
    public Direction getDirection() {
        return cur;
    }
}
