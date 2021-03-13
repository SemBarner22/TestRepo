package com.mygdx.game;

public class Config {
    private final boolean isDesktop;

    public Config(boolean isDesktop) {
        this.isDesktop = isDesktop;
    }

    boolean desktop() {
        return isDesktop;
    }
}
