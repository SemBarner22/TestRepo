package com.mygdx.game.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Strategy;

import java.util.ArrayList;
import java.util.List;

public class ChooseGoalScreen extends AbstractMechanicsScreen {
    public ChooseGoalScreen(Strategy strategy, int curPlayer, Screen previousScreen) {
        super(strategy, curPlayer, previousScreen);
    }

    @Override
    public void show() {
        super.show();
        Table table = new Table();
        final ScrollPane scroll = new ScrollPane(table, skin);
        //TODO
        String[] namesGet = new String[3];
        namesGet[0] = "GDP ";
        namesGet[1] = "Stock ";
        namesGet[2] = "Population ";
        for (int i = 0; i < 3; ++i) {
            table.add(new Label(namesGet[i], skin));
            if (i % 2 == 1) {
                table.row();
            }
        }
        table.pad(100).defaults().expandX().space(4);
        container.add(scroll).expand().fill().colspan(4);
        container.row();
        container.add(backButton).bottom().left();
        Button investment = new TextButton("Invest", skin);
        container.add(investment).bottom().padLeft(100);
        investment.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
            }
        });
        Button test = new TextButton("test", skin);
        container.add(test).bottom().padLeft(200);
        test.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
            }
        });
    }

    @Override
    public void render(float delta) {

        super.render(delta);
    }

}
