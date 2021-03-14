package com.mygdx.game.Entity;

import com.badlogic.gdx.graphics.g2d.Sprite;

abstract public class ShipForMap extends Sprite {
    public enum Direction {L, R, U, D}

    abstract public void updateTexture(Direction dir);
}
