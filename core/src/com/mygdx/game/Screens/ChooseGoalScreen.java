package com.mygdx.game.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Strategy;

import java.util.HashMap;
import java.util.Map;


public class ChooseGoalScreen extends AbstractMechanicsScreen {

    private char goal;
    private Map<String, String> portToCoordinate = new HashMap<>();

    public ChooseGoalScreen(Strategy strategy, int curPlayer, Screen previousScreen, char goal) {
        super(strategy, curPlayer, previousScreen);
        this.goal = goal;
        portToCoordinate.put("A", "X: 56, Y: 13");
        portToCoordinate.put("B", "X: 114, Y: 16");
        portToCoordinate.put("C", "X: 25, Y: 114");
        portToCoordinate.put("D", "X: 113, Y: 111");
    }

    @Override
    public void show() {
        super.show();
        String a = null;
        String b = null;
        if (goal == '1') {
            a = "C";
            b = "D";
        }
        if (goal == '2') {
            a = "C";
            b = "D";
        }
        if (goal == '3') {
            a = "A";
            b = "B";
        }
        if (goal == '4') {
            a = "A";
            b = "B";
        }
//        Table table = new Table();
//        stage.addActor(table);
//        table.pad(100).defaults().expandX().space(4);
        container.add(new Label("Where would you go next?", skin)).center().padLeft(100).expandX();
        container.row();
        Button investment = new TextButton(a, skin);
        container.add(investment).left().padLeft(100);
//        container.add(investment).bottom().padLeft(100);
        final String finalA = a;
        final String finalB = b;
        investment.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                strategy.goal = finalA.charAt(0) - 'A' + 1;
                MapScreen.mission = String.format(
                        "Go to port %s [%s]",
                        finalA,
                        portToCoordinate.get(finalA)
                );
                strategy.setScreen(previousScreen);
            }
        });
        Button test = new TextButton(b, skin);
        container.add(test).right().padRight(100);
//        container.add(test).bottom().padLeft(200);
        test.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                strategy.goal = finalB.charAt(0) - 'A' + 1;
                MapScreen.mission = "Go to port " + finalB;
                strategy.setScreen(previousScreen);
            }
        });
    }

    @Override
    public void render(float delta) {

        super.render(delta);
    }

}
